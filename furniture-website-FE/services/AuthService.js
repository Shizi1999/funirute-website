function AuthService(app) {
    app.factory('AuthService', function ($rootScope, $timeout, AuthApi) {
        const login = async (formLogin) => {
            $rootScope.loading = true;
            try {
                $timeout(function () {
                    $rootScope.loading = false;
                }, 500);
                return AuthApi.login(formLogin);
            } catch (error) {
                Promise.reject(error);
                $rootScope.loading = false;
            }
        };

        const register = async (formRegister) => {
            $rootScope.loading = true;
            try {
                $timeout(function () {
                    $rootScope.loading = false;
                }, 500);
                return AuthApi.register(formRegister);
            } catch (error) {
                Promise.reject(error);
                $rootScope.loading = false;
            }
        };

        const registerWithAvatar = async (formData) => {
            $rootScope.loading = true;
            try {
                $timeout(function () {
                    $rootScope.loading = false;
                }, 2000);
                return AuthApi.registerWithAvatar(formData);
            } catch (error) {
                Promise.reject(error);
                $rootScope.loading = false;
            }
        };

        return {
            login,
            register,
            registerWithAvatar
        };
    });
}

export default AuthService;
