function ShopService(app) {
    app.factory('ShopService', function (ProductApi, $timeout, $rootScope) {
        const defaultParams = {
            defaultSearchParams: { page: 0, pageSize: 8 },
            defaultTagParams: {
                Category: 'All',
                From: 0,
                To: 100,
            },
            priceGap: 20,
        };

        const getProduct = async (searchParams) => {
            $rootScope.loading = true;
            try {
                const res = await ProductApi.filter(searchParams);
                const { totalPage, totalItems, datas } = res.data.data;
                const getTotalPage = () => {
                    return Array.from({ length: totalPage }, (_, index) => index + 1);
                };

                $timeout(function () {
                    $rootScope.loading = false;
                }, 500);

                return {
                    getTotalPage,
                    totalPage,
                    totalItems,
                    datas,
                };
            } catch (error) {
                Promise.reject(error);
                $rootScope.loading = false;
            }
        };

        const getMinMax = async () => {
            $rootScope.loading = true;
            try {
                const res = await ProductApi.minMax();
                const { min, max } = res.data.data;

                const tagParams = {
                    ...defaultParams.defaultTagParams,
                    To: max,
                };
                defaultParams.defaultTagParams.To = max;

                $timeout(function () {
                    $rootScope.loading = false;
                }, 500);

                return { min, max, tagParams };
            } catch (error) {
                Promise.reject(error);
                $rootScope.loading = false;
            }
        };

        const getSearch = (searchParams, tagParams, search) => {
            if (search) {
                searchParams = {
                    ...searchParams,
                    page: 0,
                    keyword: search,
                };
                tagParams = { ...tagParams, Search: '" ' + search + ' "' };
            } else {
                delete searchParams.keyword;
                delete tagParams.Search;
            }

            return {
                searchParams,
                tagParams,
            };
        };

        const handlePage = (searchParams, totalPage) => {
            const nextPage = () => {
                if (searchParams.page < totalPage - 1) {
                    searchParams = {
                        ...searchParams,
                        page: (searchParams.page += 1),
                    };
                }
                return searchParams;
            };
            const prevPage = () => {
                if (searchParams.page > 0) {
                    searchParams = {
                        ...searchParams,
                        page: (searchParams.page -= 1),
                    };
                }
                return searchParams;
            };
            const changePage = (index) => {
                return {
                    ...searchParams,
                    page: index - 1,
                };
            };
            return {
                nextPage,
                prevPage,
                changePage,
            };
        };

        const getProductByPriceRange = (tagParams, searchParams, min, max) => {
            tagParams = {
                ...tagParams,
                From: min,
                To: max,
            };
            searchParams = {
                ...searchParams,
                minPrice: min,
                maxPrice: max,
            };
            return {
                tagParams,
                searchParams,
            };
        };

        const getProductByCategory_Id = (tagParams, searchParams, id, name, search) => {
            if (search) {
                searchParams = { ...defaultParams.defaultSearchParams, keyword: search, categoryId: id };
                tagParams = { ...defaultParams.defaultTagParams, Search: '" ' + search + ' "', Category: name };
            } else {
                searchParams = { ...defaultParams.defaultSearchParams, categoryId: id };
                tagParams = { ...defaultParams.defaultTagParams, Category: name };
            }
            return { tagParams, searchParams };
        };

        return {
            defaultParams,
            getProduct,
            getMinMax,
            getSearch,
            handlePage,
            getProductByPriceRange,
            getProductByCategory_Id,
        };
    });
}

export default ShopService;
