function ShopController(app) {
    app.controller('ShopController', function ($scope, $rootScope, CartService, ShopService) {
        //init max, tagParam
        $scope.getMinMax = async () => {
            const { max, tagParams } = await ShopService.getMinMax();
            //
            $scope.maxValue = max;
            $scope.min = 0;
            $scope.max = max;
            $scope.tagParams = tagParams;
        };
        $scope.getMinMax();

        const { defaultSearchParams, defaultTagParams, priceGap } = ShopService.defaultParams;
        //Reset default
        $scope.searchParams = { ...defaultSearchParams };
        $scope.resetProduct = () => {
            $scope.searchParams = { ...defaultSearchParams };
            $scope.tagParams = { ...defaultTagParams, To: $scope.max };
            $scope.search = '';
        };

        //filter product & handlePage
        let handlePage;
        $scope.getFilterProduct = async () => {
            const { getTotalPage, totalPage, totalItems, datas } = await ShopService.getProduct($scope.searchParams);
            //
            $scope.products = [...datas];
            $scope.totalPage = totalPage;
            $scope.totalItems = totalItems;
            $scope.getTotalPage = getTotalPage;

            //
            handlePage = ShopService.handlePage($scope.searchParams, $scope.totalPage);
        };

        // TagParam
        $scope.deleteTag = (key) => {
            switch (key) {
                case 'Search':
                    $scope.search = '';
                    break;
                case 'Category':
                    $scope.tagParams['Category'] = 'All';
                    $scope.searchParams = { ..._.omit($scope.searchParams, 'categoryId') };
                    break;
                case 'To':
                    $scope.max = $scope.maxValue;
                    $scope.getProductByPriceRange();
                    break;
                case 'From':
                    $scope.min = 0;
                    $scope.getProductByPriceRange();
                    break;
                default:
                    console.log('key not exist');
                    break;
            }
        };

        $scope.$watch('searchParams', function () {
            $scope.getFilterProduct();
        });

        $scope.$watch('search', function () {
            if (!!($scope.tagParams || $scope.search)) {
                const { searchParams, tagParams } = ShopService.getSearch($scope.searchParams, $scope.tagParams, $scope.search);
                //
                $scope.searchParams = { ...searchParams };
                $scope.tagParams = { ...tagParams };
            }
        });

        $scope.$watch('max', function () {
            $scope.max = $scope.max - $scope.min < priceGap ? $scope.min + priceGap : $scope.max;
            $scope.RightValue = 100 - ($scope.max / $scope.maxValue) * 100 + '%';
        });

        $scope.$watch('min', function () {
            $scope.min = $scope.max - $scope.min < priceGap ? $scope.max - priceGap : $scope.min;
            $scope.LeftValue = ($scope.min / $scope.maxValue) * 100 + '%';
        });

        $scope.nextPage = () => {
            $scope.searchParams = { ...handlePage.nextPage() };
        };

        $scope.prevPage = () => {
            $scope.searchParams = { ...handlePage.prevPage() };
        };

        $scope.changePage = (index) => {
            $scope.searchParams = { ...handlePage.changePage(index) };
        };

        $scope.getProductByCategory_Id = (id, name) => {
            const getProductByCategory_Id = ShopService.getProductByCategory_Id($scope.tagParams, $scope.searchParams, id, name, $scope.search);
            //
            $scope.searchParams = { ...getProductByCategory_Id.searchParams };
            $scope.tagParams = { ...getProductByCategory_Id.tagParams };
        };

        $scope.getProductByPriceRange = () => {
            const getProductByPriceRange = ShopService.getProductByPriceRange($scope.tagParams, $scope.searchParams, $scope.min, $scope.max);
            //
            $scope.tagParams = { ...getProductByPriceRange.tagParams };
            $scope.searchParams = { ...getProductByPriceRange.searchParams };
        };

        $scope.addToCart = (product) => {
            CartService.addToCart(product);
            $rootScope.carts = CartService.getCartFromLS();
        };
    });
}

export default ShopController;
