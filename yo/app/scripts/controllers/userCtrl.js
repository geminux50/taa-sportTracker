//this is used to parse the profile
//function url_base64_decode(str) {
//	var output = str.replace('-', '+').replace('_', '/');
//	switch (output.length % 4) {
//	case 0:
//		break;
//	case 2:
//		output += '==';
//		break;
//	case 3:
//		output += '=';
//		break;
//	default:
//		throw 'Illegal base64url string!';
//	}
//	return window.atob(output); // polifyll
//	// https://github.com/davidchambers/Base64.js
//}

angular.module('sportTracker').controller(
		'UserCtrl',
		function($scope, $http, $window) {
			$scope.isAuthenticated = false;
			$scope.welcome = '';
			$scope.message = '';
			var urlBase = '/rest/user';
			
			// create a new user
			$scope.register = function() {
				$http.post(urlBase + '/create', $scope.user).success(
						function(data, status) {
							$scope.status = status;
							$scope.data = data;
						}).error(function(data, status) {
							$scope.data = data || "Request failed";
							$scope.status = status;
				});
			};

			$scope.login = function() {
				$http.post(urlBase + '/login', $scope.user).success(
						function(data, status) {
							$scope.status = status;
							$scope.data = data;
//							$window.sessionStorage.token = data.token;
//							$scope.isAuthenticated = true;
//							var encodedProfile = data.token.split('.')[1];
//							var profile = JSON
//									.parse(url_base64_decode(encodedProfile));
//							$scope.welcome = 'Welcome ' + profile.first_name
//									+ ' ' + profile.last_name;
						}).error(function(data, status) {
							$scope.data = data || "Request failed";
							$scope.status = status;
					// Erase the token if the user fails to log in
//					delete $window.sessionStorage.token;
//					$scope.isAuthenticated = false;

					// Handle login errors here
//					$scope.error = 'Error: Invalid user or password';
//					$scope.welcome = '';
				});
			};
//
//			$scope.logout = function() {
//				$scope.welcome = '';
//				$scope.message = '';
//				$scope.isAuthenticated = false;
//				delete $window.sessionStorage.token;
//			};
//
//			$scope.callRestricted = function() {
//				$http({
//					url : '/rest/user/getFriends',
//					method : 'GET'
//				}).success(function(data, status, headers, config) {
//					$scope.message = $scope.message + ' ' + data.name; // Should
//					// log
//					// 'foo'
//				}).error(function(data, status, headers, config) {
//					alert(data);
//				});
//			};
//
		});
//
//sportTracker.factory('authInterceptor', function($rootScope, $q, $window) {
//	return {
//		request : function(config) {
//			config.headers = config.headers || {};
//			if ($window.sessionStorage.token) {
//				config.headers.Authorization = 'Bearer '
//						+ $window.sessionStorage.token;
//			}
//			return config;
//		},
//		response : function(response) {
//			if (response.status === 401) {
//				// handle the case where the user is not authenticated
//			}
//			return response || $q.when(response);
//		}
//	};
//});
//
//sportTracker.config(function($httpProvider) {
//	$httpProvider.interceptors.push('authInterceptor');
//});
