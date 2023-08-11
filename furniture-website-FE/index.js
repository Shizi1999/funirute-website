import controller from './controllers/Controller.js';
import service from './services/Service.js';
import api from './api/Api.js';
import { getApiUrl } from './utils/Utils.js';

const app = angular.module('app', ['ngRoute']);

for (const key in api) {
    api[key](app);
}
// setup service
for (const key in service) {
    service[key](app);
}
// set up controller
for (const key in controller) {
    controller[key](app);
}

app.run(function ($rootScope, $http) {
    $rootScope.darkModeColor = 'dark:text-gray-200 dark:bg-gray-900';
    $rootScope.isAdmin = false;
    let user = JSON.parse(localStorage.getItem('user'));
    if (user) {
        $rootScope.isAdmin = user.role === 'ROLE_ADMIN';
    }
    //before init
    // $http.get(getApiUrl('/public/categories')).then((res) => {
    //     $rootScope.categories = res.data.data;
    // });

    $rootScope.initModal = (selector, options) => {
        const $targetEl = document.querySelector(selector);
        const modal = new Modal($targetEl, {
            ...defaultOptions,
            ...options
        });
        return modal;
    };

    $rootScope.user = JSON.parse(localStorage.getItem('user')) || null;
});

app.config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: '/pages/home.html',
            controller: 'HomeController'
        })
        .when('/shop', {
            templateUrl: '/pages/shop.html',
            controller: 'ShopController'
        })
        .when('/login', {
            templateUrl: '/pages/login.html',
            controller: 'AuthController'
        })
        .when('/cart', {
            templateUrl: '/pages/cart.html',
            controller: 'CartController'
        })
        .when('/order', {
            templateUrl: '/pages/order.html',
            controller: 'OrderController'
        })
        .when('/product-manage', {
            templateUrl: '/pages/manage-product.html',
            controller: 'AdminProductController'
        })
        .when('/category-manage', {
            templateUrl: '/pages/manage-category.html',
            controller: 'AdminCategoryController'
        })
        .otherwise({
            templateUrl: 'views/notFound.html'
        });
});

const defaultOptions = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses: 'bg-gray-900 bg-opacity-50 dark:bg-opacity-80 fixed inset-0 z-40',
    closable: true
};

app.directive('customOnChange', function () {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var onChangeHandler = scope.$eval(attrs.customOnChange);
            element.on('change', onChangeHandler);
            element.on('$destroy', function () {
                element.off();
            });
        }
    };
});
export default app;
