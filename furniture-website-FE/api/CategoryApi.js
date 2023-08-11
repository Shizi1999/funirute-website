import { getApiUrl } from '../utils/Utils.js';
function CategoryApi(app) {
    app.factory('CategoryApi', function ($http) {
        return {
            filter(params) {
                return $http.get(getApiUrl('/public/categories/filter'), {
                    params
                });
            },
            getExistCategory() {
                return $http.get(getApiUrl('/public/categories-exist'));
            },
            createCategory(category) {
                return $http.post(getApiUrl('/admin/categories'), category, {
                    headers: {
                        Authorization: 'Bearer ' + localStorage.getItem('access_token')
                    }
                });
            },
            updateCategory(category) {
                return $http.put(getApiUrl('/admin/categories'), category, {
                    headers: {
                        Authorization: 'Bearer ' + localStorage.getItem('access_token')
                    }
                });
            },
            deleteCategory(id) {
                return $http.delete(getApiUrl('/admin/categories/' + id), {
                    headers: {
                        Authorization: 'Bearer ' + localStorage.getItem('access_token')
                    }
                });
            }
        };
    });
}

export default CategoryApi;
