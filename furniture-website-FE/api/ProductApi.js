import { getApiUrl } from '../utils/Utils.js';
function ProductApi(app) {
    app.factory('ProductApi', function ($http) {
        return {
            filter(params) {
                return $http.get(getApiUrl('/public/products/filter'), {
                    params,
                });
            },
            minMax() {
                return $http.get(getApiUrl('/public/product/min-max'));
            },
        };
    });
}
export default ProductApi;
