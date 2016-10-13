'use strict';

var wishingWell = angular.module('wishingWell', [ 'ngRoute', 'ngVisgraph' ]);

wishingWell.config(function($routeProvider, $httpProvider) {

    $routeProvider.when('/', {
        templateUrl : 'home.html',
        controller : 'home',
        controllerAs : 'controller'
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
        controller: 'profile'
    }).otherwise('/');

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

});

var config = {
    headers : {
        'Content-type': 'application/json'
    }
};


wishingWell.controller('menu', function($rootScope, $http, $location) {
    var jsonResponse;
    var jsonString;
    var vm = this;
    var currentuser = undefined;

    vm.userpage = function() {
        $http.get('user2').then(function (response) {
            jsonString = JSON.stringify(response.data);
            jsonResponse = JSON.parse(jsonString);

            currentuser = jsonResponse.msg;
            $location.path("/profile/" + currentuser);
        }, function () {
            console.log("error");
        });
    }

    vm.logout = function() {
        $http.post('logout',{}).finally(function() {
            $rootScope.authenticated = false;
            $location.path("/");
        });
    }
});



wishingWell.controller('profile', function($rootScope, $routeParams, $http, $scope) {

    $scope.topten = [];
    var username = $routeParams.user;

    $http.get('display-top-wishes').then(function (response) {
        $scope.topten = response.data;
        console.log($scope.topten);
    }, function () {
        console.log("error");
    });
});

wishingWell.controller('home', function($http) {

    var jsonResponse;
    var jsonString;
    var self = this;
    self.username = undefined;

    //list to be fed to vis.js
    var wishList = [];

    //gets wish list from db
    $http.get('getwishesJSON').then(function (response) {
        wishList = response.data;
    }, function () {
        console.log("error");
    });



    $http.get('user2').then(function (response) {
        jsonString = JSON.stringify(response.data);
        jsonResponse = JSON.parse(jsonString);

        self.username = jsonResponse.msg;
    }, function () {
        console.log("error");
    });
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

/** controller for vis network graph */
wishingWell.controller('visGraph', ['$scope', '$window', 'appService', function($scope, $window, appService) {
    var get = function() {
        appService.get().then(function(promise) {
            if (angular.isDefined(promise.error) && promise.error === 0) {
                $scope.graph = {error: promise.error, data: {nodes: promise.nodes, edges: promise.edges}, options: promise.options};
            }
        }, function(promise) {
            console.error('appService.promise.error', promise);
           });
    };

    $scope.callbackFunction = function(params) {
        console.log(angular.toJson(params));
    };

    get();
    }]);

    wishingWell.factory('appService', ['$q', '$http', function($q, $http) {
        return {
            get: function(method, url) {
                    var deferred = $q.defer();

                    /** NOTE: we can replace 'data.json' with our data source */
                    $http.get('../data.json')
                        .success(function(response) {
                            deferred.resolve(response);
                 })
                 .error(function() {
                    deferred.reject("Error! @wishingWell.appService");
                 });

            return deferred.promise;
            }
        };
    }]);




