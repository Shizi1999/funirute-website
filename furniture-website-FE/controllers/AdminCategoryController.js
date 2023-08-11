function AdminCategoryController(app) {
    app.controller('AdminCategoryController', function ($scope, $timeout, $rootScope, CategoryService) {
        $scope.categories = [];
        $scope.current = null;
        $scope.params = { page: 0, pageSize: 5, sortFiled: 'id', orderBy: 'desc' };

        $scope.$watch('params', function () {
            $scope.getData();
        });

        $scope.getData = async () => {
            try {
                const res = await CategoryService.filter($scope.params);
                const { totalPage, totalItems, datas } = res.data.data;
                $scope.categories = [...datas];
                $scope.totalPage = totalPage;
                $scope.totalItems = totalItems;
                $scope.getTotalPage = () => {
                    return Array.from({ length: totalPage }, (_, index) => index + 1);
                };
            } catch (error) {
                Promise.reject(error);
            }
        };

        $scope.$watch('search', function () {
            if ($scope.search) {
                $scope.params = { ...$scope.params, page: 0, keyword: $scope.search };
            } else {
                delete $scope.params.keyword;
                $scope.params = { ...$scope.params };
            }
        });

        $scope.removeSearch = () => {
            $scope.search = '';
        };

        $scope.nextPage = () => {
            if ($scope.params.page < $scope.totalPage - 1) {
                $scope.params = { ...$scope.params, page: ++$scope.params.page };
            }
        };

        $scope.prevPage = () => {
            if ($scope.params.page > 0) {
                $scope.params = { ...$scope.params, page: --$scope.params.page };
            }
        };

        $scope.changePage = (index) => {
            $scope.params = { ...$scope.params, page: --index };
        };

        $scope.sortFiled = (sortFiled) => {
            if (sortFiled == $scope.params.sortFiled) {
                if ($scope.params.orderBy === 'desc') {
                    $scope.params = { ...$scope.params, orderBy: 'asc' };
                } else {
                    $scope.params = { ...$scope.params, orderBy: 'desc' };
                }
            } else {
                $scope.params = { ...$scope.params, sortFiled: sortFiled };
            }
        };

        $scope.modal = $rootScope.initModal('#deleteModal');

        $scope.showModal = () => {
            $scope.modal.show();
        };

        $scope.hideModal = () => {
            $scope.modal.hide();
        };

        $scope.setRemoveCategory = (category) => {
            $scope.showModal();
            $scope.current = category;
        };

        $scope.removeCurrentCategory = async () => {
            $scope.modal.hide();
            try {
                const res = await CategoryService.deleteCategory($scope.current.id);
                if (res?.data?.success) {
                    $scope.getData();
                    $timeout(function () {
                        showSuccessToast(`Delete Category 
                        <span class="dark:text-red-300 text-red-500">${$scope.current.name}</span>
                        Successful`);
                    }, 700);
                } else {
                    alert(res?.data?.message || 'Server interval');
                }
            } catch (error) {
                Promise.reject(error);
            }
        };

        $scope.modalCategory = $rootScope.initModal('#category-modal');

        $scope.showModalCategory = () => {
            $scope.modalCategory.show();
        };

        $scope.hideModalCategory = () => {
            $scope.modalCategory.hide();
        };

        $scope.showModalForEdit = (category) => {
            $scope.current = category;
            $scope.categoryForm = { name: category.name };
            $scope.showModalCategory();
        };

        $scope.showModalForCreate = () => {
            $scope.current = null;
            $scope.categoryForm = { name: '' };
            $scope.showModalCategory();
        };

        $scope.categoryForm = {
            name: ''
        };

        $scope.submit = async () => {
            if ($scope.current) {
                const res = await CategoryService.updateCategory({
                    id: $scope.current.id,
                    name: $scope.categoryForm.name
                });
                if (res?.data?.success) {
                    $scope.getData();
                    $timeout(function () {
                        showSuccessToast(
                            `Update Category 
                            <span class="dark:text-blue-300 text-blue-500">${$scope.current.name}</span>
                            to Category 
                            <span class="dark:text-green-300 text-green-500">${$scope.categoryForm.name}</span>
                            Successful`
                        );
                    }, 700);
                } else {
                    alert(res?.data?.message || 'Server interval');
                }
                $scope.getData();
            } else {
                const res = await CategoryService.createCategory({ name: $scope.categoryForm.name });
                if (res?.data?.success) {
                    $scope.getData();
                    $timeout(function () {
                        showSuccessToast(`Create Category 
                        <span class="dark:text-green-300 text-green-500">${$scope.categoryForm.name}</span>
                        Successful`);
                    }, 700);
                } else {
                    alert(res?.data?.message || 'Server interval');
                }
                $scope.getData();
            }
            $scope.hideModalCategory();
        };
    });
}

export default AdminCategoryController;
