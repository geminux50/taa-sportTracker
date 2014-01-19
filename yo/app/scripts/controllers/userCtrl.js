angular.module('sportTracker').controller(
		'UserCtrl', ['$route', '$scope', '$http', '$window', '$location',
		function($route ,$scope, $http, $window, $location) {
			  
			$scope.debug = globalDebug;
			var urlBase = '/rest/user';
			
			// create a new user
			$scope.register = function() {
				$http.post(urlBase + '/create', $scope.user).success(
						function(data, status, headers, config) {
							if (data.hasOwnProperty("success")) {
								$scope.isRegistered = true;
								//$location.path("/login");
								} else {
									$scope.isRegistered = false;
									$scope.error_msg = data.error;
								}
							// debug
							$scope.status = status;
							$scope.data = data;
						}).error(function(data, status, headers, config) {
							$scope.isRegistered = false;
							$scope.error_msg = data.error;
							// debug
							$scope.data = data || "Request failed";
							$scope.status = status;
				});
			};

			$scope.login = function() {
				$http.post(urlBase + '/login', $scope.user).success(
						function(data, status, headers, config) {
							
							function User(pseudo, mail, token) {
								this.pseudo = pseudo;
								this.mail = mail;
								this.token = token;
							}
							if (data.hasOwnProperty("token")) {

								var loggedUser = new User($scope.user.pseudo, $scope.user.mail, data.token);
								$window.sessionStorage.setItem('loggedUser', JSON.stringify(loggedUser));
								$location.path("/myFriends");
								} else {
									$window.sessionStorage.removeItem('loggedUser');
									$scope.error_msg = data.error;
								}
							$scope.data = data;
							$scope.status = status;
						}).error(function(data, status, headers, config) {
							$window.sessionStorage.removeItem('loggedUser');
							$scope.data = data || "Request failed";
							$scope.status = status;
							$scope.error_msg = data.error;

				});
			};
			
			$scope.isAuthenticated = function() {
				var status = false;
				var loggedUser = $.parseJSON($window.sessionStorage.getItem('loggedUser'));
				if(loggedUser != null) {
					if (loggedUser.token != null) {
						status = true;
					}
				}
				return status;
			};
			// Logout a user
			// Will allways pass on the client side. May not remove token on the
			// server side if request failed but, anyway a new token will be
			// generated
			// on next login success
			$scope.logout = function() {
				if ($scope.isAuthenticated()){
					var loggedUser = $.parseJSON($window.sessionStorage.getItem('loggedUser'));
					if(loggedUser != null) {
						$http.post(urlBase + '/logout', loggedUser).success(
								function(data, status, headers, config) {
									if (data.hasOwnProperty("status")) {
										if (data.status) {
											// Succeed and Token removed from
											// server persistance
											// this is the clean way to do it
											$window.sessionStorage.removeItem('loggedUser');
										} else {
											// Failed on server side but still
											// logout client side
											// Anyway, a new token will be
											// generated on next login
											// Ugly way (server and client are
											// not sync)...but secure
											$window.sessionStorage.removeItem('loggedUser');
										}
										// In both case we redirect to the main
										// page
										$location.path("/");
										// debug
										$scope.status = status;
										$scope.data = data;
									}
								}).error(function(data, status, headers, config) {
									// Failed on server side but still logout
									// client side
									// Anyway, a new token will be generated on
									// next login
									// Ugly way (server and client are not
									// sync)...but secure
									$window.sessionStorage.removeItem('loggedUser');
									// debug
									$scope.data = data || "Request failed";
									$scope.status = status;
								});
					}
				}
			};
			
			$scope.getFriends = function() {
				if ($scope.isAuthenticated()){
					var loggedUser = $.parseJSON($window.sessionStorage.getItem('loggedUser'));
					if(loggedUser != null) {
						$http.get(urlBase + '/'+ loggedUser.pseudo +'/getFriends', loggedUser).success(
								function(data, status, headers, config) {
									$scope.friends = data;
									// debug
									$scope.status = status;
									$scope.data = data;
								}).error(function(data, status, headers, config) {
									// debug
									$scope.data = data || "Request failed";
									$scope.status = status;
								});
					}
				}
			};
			
			$scope.addFriend = function() {
				if ($scope.isAuthenticated()){
					var loggedUser = $.parseJSON($window.sessionStorage.getItem('loggedUser'));
					if(loggedUser != null) {
						$http.post(urlBase + '/'+ loggedUser.pseudo +'/addFriend/' + $scope.newfriend.pseudo).success(
								
								function(data, status, headers, config) {
									if (data.hasOwnProperty("message")) {
										$scope.addFriendStatusMessage = data.message;
										$route.reload();
										} else {
											$scope.addFriendStatusMessage = data.message;
										}
									// debug
									$scope.status = status;
									$scope.data = data;
								}).error(function(data, status, headers, config) {
									// debug
									$scope.data = data || "Request failed";
									$scope.status = status;
									$scope.addFriendStatusMessage = data.message;
								});
					}
				}
			};
			
			$scope.delFriend = function(oldFriend) {
				if ($scope.isAuthenticated()){
					var loggedUser = $.parseJSON($window.sessionStorage.getItem('loggedUser'));
					if(loggedUser != null) {
						$http.post(urlBase + '/'+ loggedUser.pseudo +'/delFriend/' + oldFriend.pseudo).success(
								
								function(data, status, headers, config) {
									if (data.hasOwnProperty("message")) {
										//$scope.addFriendStatusMessage = data.message;
										$route.reload();
										} else {
											$scope.addFriendStatusMessage = data.message;
										}
									// debug
									$scope.status = status;
									$scope.data = data;
								}).error(function(data, status, headers, config) {
									// debug
									$scope.data = data || "Request failed";
									$scope.status = status;
									//$scope.addFriendStatusMessage = data.message;
								});
					}
				}
			};
			


		}]);


