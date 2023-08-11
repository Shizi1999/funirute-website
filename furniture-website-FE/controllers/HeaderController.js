function HeaderController(app) {
    const defaultOptions = {
        placement: 'bottom',
        triggerType: 'click',
        offsetSkidding: 0,
        offsetDistance: 10,
        delay: 300
    };
    app.run(function ($rootScope) {
        $rootScope.initDropDown = (target, trigger, options) => {
            const $targetEl = document.querySelector(target);
            if ($targetEl) {
                const $triggerEl = document.querySelector(trigger);
                const dropDown = new Dropdown($targetEl, $triggerEl, {
                    ...defaultOptions,
                    ...options
                });
                return dropDown;
            }
            return null;
        };
    });
    app.controller('HeaderController', function ($scope, $timeout, $rootScope, CartService) {
        $timeout(function () {
            $scope.dropDown = $rootScope.initDropDown('#userDropDown', '#trigger');
        });

        $scope.toggleDropDown = () => {
            if ($scope.dropDown.isVisible()) {
                $scope.dropDown.show();
            } else {
                $scope.dropDown.hide();
            }
        };

        $scope.avatar = $scope.isDarkTheme = false;
        if (
            localStorage.getItem('color-theme') === 'dark' ||
            (!('color-theme' in localStorage) && window.matchMedia('(prefers-color-scheme: dark)').matches)
        ) {
            $scope.isDarkTheme = true;
        } else {
            $scope.isDarkTheme = false;
        }

        $scope.toggleTheme = () => {
            $scope.isDarkTheme = !$scope.isDarkTheme;
            // if set via local storage previously
            if (localStorage.getItem('color-theme')) {
                if (localStorage.getItem('color-theme') === 'light') {
                    document.documentElement.classList.add('dark');
                    localStorage.setItem('color-theme', 'dark');
                } else {
                    document.documentElement.classList.remove('dark');
                    localStorage.setItem('color-theme', 'light');
                }

                // if NOT set via local storage previously
            } else {
                if (document.documentElement.classList.contains('dark')) {
                    document.documentElement.classList.remove('dark');
                    localStorage.setItem('color-theme', 'light');
                } else {
                    document.documentElement.classList.add('dark');
                    localStorage.setItem('color-theme', 'dark');
                }
            }
        };

        $scope.signOut = () => {
            $rootScope.loading = true;
            $scope.dropDown.hide();
            localStorage.removeItem('access_token');
            localStorage.removeItem('user');
            $rootScope.user = null;
            window.open('#!/', '_self');
            $timeout(function () {
                $rootScope.loading = false;
            }, 1000);
        };

        $rootScope.carts = CartService.getCartFromLS();
        $rootScope.getTotalQuantity = () => {
            return $rootScope.carts.reduce((acc, curr) => acc + curr.quantity, 0);
        };
    });
}

export default HeaderController;
