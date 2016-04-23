angular.module('activitiApp').controller('DashboardCtrl', function ($scope, ValidateUserService, $rootScope, $location,TasksService) {

    if((typeof $rootScope.loggedin== 'undefined' ||$rootScope.loggedin==false) ){
        $rootScope.validateUser.then(function(){
            if(!$rootScope.loggedin)
                $location.path('/login');
        },$location.path('/login'));

    }





    $scope.tasks = TasksService.get({"size": 1000, "candidateUser": $rootScope.UserId});
 //   $scope.tasks.push(TasksService.assignedTasks({"size": 1000, "assignee": $rootScope.UserId}))



});