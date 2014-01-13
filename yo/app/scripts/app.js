'use strict';

angular.module('sportTracker', [ 'ngRoute' ]).config(function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : 'views/main.html',
		controller : 'MainCtrl'
	}).when('/register', {
		templateUrl : 'views/register.html',
		controller : 'UserCtrl'
	}).when('/login', {
		templateUrl : 'views/login.html',
		controller : 'UserCtrl'
	}).when('/about', {
		templateUrl : 'views/about.html',
		controller : 'MainCtrl'
	}).otherwise({
		redirectTo : '/'
	});
});

//angular.module('sportTracker').factory('dataFactory',
//		[ '$http', function($http) {
//
//			var urlBase = '/rest/user';
//			var dataFactory = {};
//
//			dataFactory.createUser = function() {
//				return $http.post(urlBase + 'create', 'e', 'z');
//			};
//			;
//
//			return dataFactory;
//		} ]);
