function OrderController(app) {
    const defaultOptions = {
        placement: 'top',
        triggerType: 'hover',
        offset: 10,
    };
    app.run(function ($rootScope) {
        $rootScope.initPopover = (target, trigger, options) => {
            if ($targetEl) {
                const popover = new Popover(target, trigger, {
                    ...defaultOptions,
                    ...options,
                });
                return popover;
            }
            return null;
        };
    });
    app.controller('OrderController', function ($scope, $rootScope, $http, $timeout) {
        $scope.cancelOrderID = null;
        $scope.modal = $rootScope.initModal('#cancelOrder');

        $rootScope.showModalOrder = (id) => {
            $scope.modal.show();
            $scope.cancelOrderID = id;
        };

        $scope.hideModalOrder = () => {
            $scope.modal.hide();
        };
        $scope.cancelOrder = () => {
            $rootScope.loading = true;
            $http
                .delete('http://localhost:8080/api/order/' + $scope.cancelOrderID, {
                    headers: {
                        Authorization: 'Bearer ' + localStorage.getItem('access_token') || '',
                    },
                })
                .then((res) => {
                    $scope.orders = $scope.orders.filter((item) => item.id !== $scope.cancelOrderID);
                })
                .catch((err) => {
                    Promise.reject(err);
                })
                .finally(() => {
                    $scope.hideModalOrder();
                    $timeout(function () {
                        $rootScope.loading = false;
                    }, 500);
                });
        };

        $http
            .get('http://localhost:8080/api/order', {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('access_token') || '',
                },
            })
            .then((res) => {
                $scope.orders = res.data.data;
                console.log(res.data.data);
            })
            .catch((err) => {
                Promise.reject(err);
            });
    });
}
export default OrderController;
