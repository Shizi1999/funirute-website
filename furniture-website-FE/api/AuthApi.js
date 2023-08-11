import { getApiUrl } from '../utils/Utils.js';
function AuthApi(app) {
    app.factory('AuthApi', function ($http) {
        return {
            login(formLogin) {
                return $http.post(getApiUrl('/auth/login'), formLogin);
            },
            register(formRegister) {
                return $http.post(getApiUrl('/auth/register'), formRegister);
            },
            registerWithAvatar(formData) {
                return $http.post(getApiUrl('/auth/registerWithAvatar'), formData, {
                    headers: {
                        'Content-Type': undefined
                    }
                });
            }
        };
    });
}

export default AuthApi;
