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
    }).when('/profile/:user', {
        templateUrl: 'user-home.html',
        controller: 'profile',
        controllerAs: 'controller'
    }).otherwise('/');

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

});

var config = {
    headers : {
        'Content-type': 'application/json'
    }
};


wishingWell.controller('menu', function($rootScope, $http, $location, $scope) {
    var jsonResponse;
    var jsonString;
    var self = this;

    $http.get('user2').then(function(response) {
        jsonString = JSON.stringify(response.data);
        jsonResponse = JSON.parse(jsonString);

        $scope.name = jsonResponse.msg;
    }, function() {
        console.log("error");
    });

    self.logout = function() {
        $http.post('logout',{}).finally(function() {
            $rootScope.authenticated = false;
            $location.path("/");
        });
    }
});


wishingWell.controller('home', function() {

});


wishingWell.controller('register', function($http, $location, $rootScope) {

    var jsonResponse;
    var jsonString;
    var noerrors = false;
    var self = this;
    self.user = {};
    self.msg = undefined;


    self.register = function() {

        if (!(self.user.username == undefined) && self.user.username.length <= 20 && !(self.user.password == undefined)) {
            noerrors = true;
        }

        if (noerrors) {
            $http.post('registeruser', JSON.stringify(self.user), config).then(function(response) {

                jsonString = JSON.stringify(response.data);
                jsonResponse = JSON.parse(jsonString);

                if (jsonResponse.code == "200") {
                    $rootScope.authenticated = true;
                    $location.path("/user-home");
                } else {
                    $location.path("/sign-up");
                    self.msg = jsonResponse.msg;
                    noerrors = false;
                    self.user = {};
                }

            });
        } else {
            $location.path("/sign-up");
        }
    };
});



wishingWell.controller('navigation',

    function($rootScope, $http, $location) {
        var jsonString;
        var jsonResponse;
        var self = this;

        var authenticate = function(credentials, callback) {

            var headers = credentials ? {authorization : "Basic "
            + btoa(credentials.username + ":" + credentials.password)
            } : {};

            $http.get('user', {headers : headers}).then(function(response) {

                jsonString = JSON.stringify(response.data);
                jsonResponse = JSON.parse(jsonString);

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



