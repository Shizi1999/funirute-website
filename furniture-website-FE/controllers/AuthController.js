import { convertToBlob } from '../utils/Utils.js';
function AuthController(app) {
    app.controller('AuthController', function ($scope, $timeout, $rootScope, AuthService) {
        $scope.activeTab = 1;
        $scope.image =
            'https://media.istockphoto.com/id/1222357475/vector/image-preview-icon-picture-placeholder-for-website-or-ui-ux-design-vector-illustration.jpg?s=612x612&w=0&k=20&c=KuCo-dRBYV7nz2gbk4J9w1WtTAgpTdznHu55W9FjimE=';
        $scope.imageFile = null;
        var emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        $scope.changeTab = (tab) => {
            $scope.activeTab = tab;
        };
        $scope.errorMessageRegister = {
            email: '',
            password: '',
            confirmPassword: ''
        };
        $scope.errorMessageLogin = {
            email: '',
            password: ''
        };
        $scope.handleChangeFormLogin = () => {
            if ($scope.errorMessageLogin.email || $scope.errorMessageLogin.password) {
                $scope.errorMessageLogin = {
                    email: '',
                    password: ''
                };
            }
        };

        $scope.handleChangeFormRegister = () => {
            if (
                $scope.errorMessageRegister.email ||
                $scope.errorMessageRegister.password ||
                $scope.errorMessageRegister.confirmPassword
            ) {
                $scope.errorMessageRegister = {
                    email: '',
                    password: '',
                    confirmPassword: ''
                };
            }
        };

        $scope.formLogin = {
            email: '',
            password: ''
        };
        $scope.formRegister = {
            email: '',
            password: '',
            confirmPassword: ''
        };

        $scope.login = async () => {
            if (!$scope.formLogin.email) {
                $scope.errorMessageLogin.email = 'Email must not be empty ';
            }

            if (!$scope.formLogin.password) {
                $scope.errorMessageLogin.password = 'Password must not be empty ';
            } else {
                const res = await AuthService.login($scope.formLogin);
                console.log(res);
                if (res?.data?.success) {
                    const { accessToken, accountDto } = res.data.data;
                    accountDto.avatar =
                        accountDto.avatar == null
                            ? 'https://media.istockphoto.com/id/1222357475/vector/image-preview-icon-picture-placeholder-for-website-or-ui-ux-design-vector-illustration.jpg?s=612x612&w=0&k=20&c=KuCo-dRBYV7nz2gbk4J9w1WtTAgpTdznHu55W9FjimE='
                            : accountDto.avatar;
                    localStorage.setItem('access_token', accessToken);
                    localStorage.setItem('user', JSON.stringify(accountDto));
                    $rootScope.isAdmin = accountDto.role === 'ROLE_ADMIN';
                    $rootScope.$apply();
                    $rootScope.user = accountDto;
                    window.open('#!/', '_self');
                } else {
                    alert(res?.data?.message || 'Server interval');
                }
            }
        };

        $scope.handleUploadImage = (e) => {
            if (e.target.files.length) {
                $scope.image = URL.createObjectURL(e.target.files[0]);
                $scope.$apply();
            }
        };

        $scope.register = async () => {
            if (!$scope.formRegister.email) {
                $scope.errorMessageRegister.email = 'Email must not be empty ';
            } else if (!emailRegex.test($scope.formRegister.email)) {
                $scope.errorMessageRegister.email = 'Email not valid ';
            }

            if (!$scope.formRegister.password) {
                $scope.errorMessageRegister.password = 'Password must not be empty ';
            } else if ($scope.formRegister.password.length < 6) {
                $scope.errorMessageRegister.password = 'Password must be at least 6 characters ';
            }

            if (!$scope.formRegister.confirmPassword) {
                $scope.errorMessageRegister.confirmPassword = 'Confirm Password must not be empty ';
            } else if ($scope.formRegister.password !== $scope.formRegister.confirmPassword) {
                $scope.errorMessageRegister.confirmPassword = 'Confirm password does not match ';
            } else if (document.getElementById('avatar').files[0]) {
                const formData = new FormData();
                formData.append(
                    'account',
                    convertToBlob({ email: $scope.formRegister.email, password: $scope.formRegister.password })
                );
                formData.append('file', document.getElementById('avatar').files[0]);
                try {
                    const res = await AuthService.registerWithAvatar(formData);
                    if (res?.data?.success) {
                        $timeout(function () {
                            showSuccessToast('Your account have been Register Successful');
                        }, 700);
                        $scope.activeTab = 1;
                        $scope.$apply();
                    }
                } catch (error) {
                    Promise.reject(error);
                }
            } else {
                try {
                    const res = await AuthService.register($scope.formRegister);
                    if (res?.data?.success) {
                        $timeout(function () {
                            showSuccessToast('Your account have been Register Successful');
                        }, 700);
                        $scope.activeTab = 1;
                        $scope.$apply();
                    }
                } catch (error) {
                    Promise.reject(error);
                }
            }
        };
    });
}

export default AuthController;
