function CategoryService(app) {
    app.factory('CategoryService', function ($rootScope, $timeout, CategoryApi) {
        return {
            async filter(params) {
                $rootScope.loading = true;
                $timeout(function () {
                    $rootScope.loading = false;
                }, 500);
                return CategoryApi.filter(params);
            },

            async getExistCategory() {
                $rootScope.loading = true;
                $timeout(function () {
                    $rootScope.loading = false;
                }, 500);
                return CategoryApi.getExistCategory();
            },

            async createCategory(category) {
                $rootScope.loading = true;
                $timeout(function () {
                    $rootScope.loading = false;
                }, 500);
                return CategoryApi.createCategory(category);
            },

            async updateCategory(category) {
                $rootScope.loading = true;
                $timeout(function () {
                    $rootScope.loading = false;
                }, 500);
                return CategoryApi.updateCategory(category);
            },

            async deleteCategory(id) {
                $rootScope.loading = true;
                $timeout(function () {
                    $rootScope.loading = false;
                }, 500);
                return CategoryApi.deleteCategory(id);
            }
        };
    });
}

export default CategoryService;
