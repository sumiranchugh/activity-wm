angular.module('activitiApp').controller('LoginCtrl', function ($scope, $http, UserService, Base64, $rootScope, $location,ValidateUserService) {

    if((typeof $rootScope.loggedin== 'undefined' ||$rootScope.loggedin==false) ){
        $rootScope.validateUser.then(function() {
            if ($rootScope.loggedin)
                $location.path('/dashboard');
            /*else
            if($rootScope.redirectFlag)
                window.location.href = $rootScope.redirect;*/
        });

    }
    else $location.path('/dashboard');


    $scope.logins = function () {
        $http.defaults.headers.common['Authorization'] = 'Basic ' + Base64.encode($scope.username + ":" + $scope.password);

        UserService.get({user: $scope.username}, function (data) {
          // data = JSON.parse(data);

            $rootScope.loggedin = true;
            $rootScope.loggedUser = data;
            $rootScope.username = $scope.username;
            $rootScope.password = $scope.password;
            $location.path('/dashboard');
        }, function(data, responseHeaders){
                if(typeof responseHeaders != 'undefined' &&  typeof responseHeaders.location!= 'undefined'){
                    $location(responseHeaders.location)
                }

            }
        );
    };

    $scope.login =function() {
        $http(
        {method :'GET',
            url : 'getLoggedInUser',
            headers :{ 'name' :$scope.username ,'pass' : $scope.password}
        }
            ).
            success(function (data, status, headers, config) {
                // this callback will be called asynchronously
                // when the response is available
                /*if(status===205){
                    var redirect = headers()['redirect'];
                    window.location.href=redirect;
                }*/
                if(typeof data != 'undefined' && data != "") {
                    $rootScope.loggedin = true;
                    $rootScope.loggedUser = data;
                    $rootScope.username = $scope.username;
                    $rootScope.password = $scope.password;
                    if(typeof data.User != 'undefined'&& data.User != "")
                        $rootScope.UserId=data.User.UserId;
                    $rootScope.any= typeof data.scope== 'undefined'? false: data.scope;
                    $location.path('/dashboard');
                }
                else
                $scope.error=true;
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                if (typeof headers != 'undefined' && typeof headers.location != 'undefined') {
                    $location(headers.location)
                }else
                $scope.error=true;

            });
    };
});
