'use strict';
angular.module('activitiApp').controller('RootCtrl', function ($scope, $http,LogoutService, UserService, Base64, $rootScope, $location, ValidateUserService) {
    $scope.logout = function () {

        LogoutService.get().$promise.then(function(){
            $rootScope.reset();
          //  $location.path('/login');
        });
        //  $location.path('/login');
    }
    $scope.changeView = function (view) {
        $location.path(view);
    };
    $rootScope.any = false;
    $rootScope.redirect = window.location.href;
    $rootScope.reset = function () {
        $rootScope.loggedUser = {};
        $scope.username = "activiti";
        $scope.password = "activiti";
        $rootScope.loggedin = false;
        $rootScope.UserId="";
        $rootScope.redirectFlag=false;
        $rootScope.redirect="";
        //    $location.path('/login');
        return false;
    };
    $rootScope.validateUser =
        //  $location.path('/login');
         ValidateUserService.get(function (data, response) {
            if (typeof response()['redirect'] != 'undefined' && response()['redirect'] != "") {
                $rootScope.redirect = response()['redirect'];
                $rootScope.redirectFlag=true;
              //  window.location.href = $rootScope.redirect;
            }
            else if (typeof data != 'undefined' && data != "" && typeof data.username != 'undefined') {

                $rootScope.loggedin = true;
                $rootScope.loggedUser = data;
                $rootScope.username = data.username;
                $rootScope.password = data.password;
                if (typeof data.User != 'undefined' && data.User != "")
                    $rootScope.UserId = data.User.UserId;
                $rootScope.any= typeof data.scope== 'undefined'? false: data.scope;
                //  $location.path('/dashboard');
               // return true;
            }
            else {
              //  return $rootScope.reset();
            }

        }, function () {
           // return $rootScope.reset();
        }).$promise;

});