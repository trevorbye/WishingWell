var wishingWell = angular.module('wishingWell', [ 'ngRoute' ]);

wishingWell.config(function($routeProvider, $httpProvider) {

    $routeProvider.when('/', {
        templateUrl : 'home.html',
        controller : 'home',
        controllerAs: 'controller'
    }).when('/login', {
        templateUrl : 'login.html',
        controller : 'navigation',
        controllerAs: 'controller'
    }).when('/sign-up', {
        templateUrl : 'sign-up.html',
        controller : 'register',
        controllerAs: 'controller'
    }).otherwise('/');

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

});

wishingWell.controller('home', function($scope, $http) {});




wishingWell.controller('register', function($http, $location, $rootScope) {

    var noerrors = false;
    var self = this;
    self.user = {};
    self.msg = undefined;


    if (self.user.username != null && self.user.username.length <= 20 && self.user.password != null) {
        noerrors = true;
    }

    self.register = function() {

        if (noerrors) {
            $http.post('register', user).success(function (response) {

                if (response.data.code == "200") {
                    $rootScope.authenticated = true;
                    $location.path("/user-home");
                } else {
                    msg = response.data.msg;
                    $location.path("/sign-up");
                }

            });
        } else {
            $location.path("/sign-up");
        }
    };
});



wishingWell.controller('navigation',

    function($rootScope, $http, $location) {

        var self = this;

        var authenticate = function(credentials, callback) {

            var headers = credentials ? {authorization : "Basic "
            + btoa(credentials.username + ":" + credentials.password)
            } : {};

            $http.get('user', {headers : headers}).then(function(response) {
                if (response.data.name) {
                    $rootScope.authenticated = true;
                } else {
                    $rootScope.authenticated = false;
                }
                callback && callback();
            }, function() {
                $rootScope.authenticated = false;
                callback && callback();
            });

        }

        authenticate();
        self.credentials = {};
        self.login = function() {
            authenticate(self.credentials, function() {
                if ($rootScope.authenticated) {
                    $location.path("/");
                    self.error = false;
                } else {
                    $location.path("/login");
                    self.error = true;
                }
            });
        };
    });



