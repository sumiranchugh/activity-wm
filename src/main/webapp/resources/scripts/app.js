'use strict';

angular.module('activitiApp', [ 'ngResource', 'ui.bootstrap', "ngRoute",'angularMoment'])

    // Temporary until we have a login page: always log in with kermit:kermit
//    .config(['$httpProvider', function ($httpProvider) {
//        $httpProvider.defaults.headers.common['Authorization'] = 'Basic a2VybWl0Omtlcm1pdA==';
//    }])



    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/', {
                name:'Login',
                templateUrl: 'views/login.html',
                controller: 'LoginCtrl'
            }) .when('/dashboard', {
                name:'Dashboard',
                templateUrl: 'views/dashboard.html',
                controller: 'DashboardCtrl'
            }).when('/users', {
                name:'Users',
                templateUrl: 'views/users.html',
                controller: 'UsersCtrl'
            }).when('/groups', {
                name:'Groups',
                templateUrl: 'views/groups.html',
                controller: 'GroupsCtrl'
            }).when('/tasks', {
                name:'Task List',
                templateUrl: 'views/tasks.html',
                controller: 'TasksCtrl'
            }).when('/processes', {
                name:'Processes',
                templateUrl: 'views/processes.html',
                controller: 'ProcessesCtrl'
            }).when('/instances', {
                name:'Instances',
                templateUrl: 'views/instances.html',
                controller: 'InstancesCtrl'
            }).when('/logout', {
                name:'Logout',
                templateUrl: 'views/login.html',
                controller: 'LoginCtrl'
            })

//            .when('/tasks', {
//                templateUrl: 'views/tasks.html',
//                controller: 'TaskCtrl'
//            })
            .otherwise({
                redirectTo: '/'
            });
    }]);



