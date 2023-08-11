import { getApiUrl, convertToBlob } from '../utils/Utils.js';

function AdminProductController(app) {
    app.controller('AdminProductController', function ($scope, $http, $rootScope) {
        $scope.image =
            'https://media.istockphoto.com/id/1222357475/vector/image-preview-icon-picture-placeholder-for-website-or-ui-ux-design-vector-illustration.jpg?s=612x612&w=0&k=20&c=KuCo-dRBYV7nz2gbk4J9w1WtTAgpTdznHu55W9FjimE=';
        $scope.products = [];
        $scope.current = null;
        $scope.getData = () => {
            $http.get(getApiUrl('/public/products/filter')).then(
                (res) => {
                    if (res?.data?.success) {
                        $scope.products = res.data.data.datas;
                    }
                },
                (error) => {}
            );
        };
        $scope.getData();
        $scope.product = {
            name: '',
            stock: 0,
            price: 0,
            category: null
        };
        $scope.imageFile = null;
        $scope.reset = () => {
            $scope.product = {
                name: '',
                stock: 0,
                price: 0,
                category: {
                    id: null
                }
            };
            $scope.image =
                'https://media.istockphoto.com/id/1222357475/vector/image-preview-icon-picture-placeholder-for-website-or-ui-ux-design-vector-illustration.jpg?s=612x612&w=0&k=20&c=KuCo-dRBYV7nz2gbk4J9w1WtTAgpTdznHu55W9FjimE=';
            $scope.imageFile = null;
        };
        $scope.submitProduct = function () {
            const formData = new FormData();
            if ($scope.current) {
                formData.append('product', convertToBlob({ ...$scope.product, id: $scope.current.id }));
                if ($scope.imageFile) {
                    formData.append('image', $scope.imageFile);
                }
                $rootScope.loading = true;
                $http
                    .put(getApiUrl('/admin/products'), formData, {
                        headers: {
                            'Content-Type': undefined,
                            Authorization: 'Bearer ' + localStorage.getItem('access_token')
                        }
                    })
                    .then(function (response) {
                        alert(response.data.message);
                        $scope.current = null;
                        $scope.imageFile = null;
                        $scope.imagePreviewUrl = null;
                        $scope.productForm.$setPristine();
                        $scope.productForm.$setUntouched();
                        $scope.getData();
                        $rootScope.loading = false;
                        $scope.reset();
                    })
                    .catch(function (error) {
                        $rootScope.loading = false;
                        console.error('Error creating product:', error);
                    });
            } else {
                $rootScope.loading = true;
                formData.append('product', convertToBlob($scope.product));
                formData.append('image', $scope.imageFile);
                $http
                    .post(getApiUrl('/admin/products'), formData, {
                        headers: {
                            'Content-Type': undefined,
                            Authorization: 'Bearer ' + localStorage.getItem('access_token')
                        }
                    })
                    .then(function (res) {
                        alert(res?.data?.message);
                        if (res?.data.success) {
                            $scope.reset;
                            $scope.imageFile = null;
                            $scope.imagePreviewUrl = null;
                            $scope.productForm.$setPristine();
                            $scope.productForm.$setUntouched();
                            $scope.getData();
                            $rootScope.loading = false;
                        }
                    })
                    .catch(function (error) {
                        $rootScope.loading = false;
                        console.error('Error creating product:', error);
                    });
            }
        };

        $scope.handleChangeImage = (e) => {
            if (e?.target.files.length) {
                const src = URL.createObjectURL(e.target.files[0]);
                $scope.imageFile = e.target.files[0];
                document.getElementById('previewImage').src = src;
                $scope.image = src;
            }
        };

        $scope.edit = (product) => {
            window.scrollTo({
                top: 0,
                behavior: 'smooth' // This triggers the smooth scrolling
            });
            $scope.current = product;
            $scope.product = {
                ...product,
                category: $rootScope.categories?.find((item) => item.id === product.category.id) || null
            };
        };
        $scope.delete = (product) => {
            $rootScope.loading = true;
            $http
                .delete(getApiUrl('/admin/products/' + product.id), {
                    headers: {
                        Authorization: 'Bearer ' + localStorage.getItem('access_token')
                    }
                })
                .then(
                    (res) => {
                        $rootScope.loading = false;
                        if (res.data.success) {
                            $scope.getData();
                        } else {
                            alert(res?.data?.message || 'Some thing went wrong!');
                        }
                    },
                    (error) => {
                        Promise.reject(error);
                        alert('Some thing went wrong!');
                    }
                );
        };
    });
}
export default AdminProductController;
