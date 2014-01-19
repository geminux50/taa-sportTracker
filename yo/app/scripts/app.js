'use strict';

var globalDebug = false;

angular.module('sportTracker', [ 'ngRoute' ,'ngResource']).config(function($routeProvider, $locationProvider) {
	
	$routeProvider.when('/', {
		templateUrl : 'views/main.html',
		controller : 'UserCtrl'
	}).when('/register', {
		templateUrl : 'views/register.html',
		controller : 'UserCtrl'
	}).when('/login', {
		templateUrl : 'views/login.html',
		controller : 'UserCtrl'
	}).when('/about', {
		templateUrl : 'views/about.html',
		controller : 'UserCtrl'
	}).when('/myFriends', {
		templateUrl : 'views/myFriends.html',
		controller : 'UserCtrl'
	}).otherwise({
		redirectTo : '/'
	});
	
	
    // enable html5Mode for pushstate ('#'-less URLs)
    $locationProvider.html5Mode(true);
    $locationProvider.hashPrefix('!');
});

//intercept all request and add the custom authentication header
angular.module('sportTracker').factory('authInterceptor', function($rootScope, $q, $window) {
	return {
		request : function(config) {
			config.headers = config.headers || {};
			
			var loggedUser = $.parseJSON($window.sessionStorage.getItem('loggedUser'));
			if(loggedUser != null) {
				if (loggedUser.token != null) {
					config.headers['x-sportTracker-tokenKey'] = loggedUser.token;
				}
			}
			
			return config;
		},
		response : function(response) {
			if (response.status === 401) {
				// handle the case where the user is not authenticated
			}
			return response || $q.when(response);
		}
	};
});

angular.module('sportTracker').config(function($httpProvider) {
	$httpProvider.interceptors.push('authInterceptor');
});
