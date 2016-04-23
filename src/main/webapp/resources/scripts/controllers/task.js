angular.module('activitiApp').controller("TasksCtrl", function ($scope, $rootScope, $location, ValidateUserService,
                                                                TasksService, FormDataService, moment, $modal,
                                                                TasksModalService, ProcessDefinitionService, GroupService) {

    /**
     * involved
     * owned
     * assigned
     *
     * @type {string}
     */
    $scope.tasksType = "assignee";

    function getTasksQuery() {
        if ($scope.tasksType == "involved") {
            return {"size": 1000, "involvedUser": $rootScope.UserId};
        } else if ($scope.tasksType == "owned") {
            return {"size": 1000, "owner": $rootScope.UserId};
        } else if ($scope.tasksType == "unassigned") {
            return {"size": 1000, "unassigned": true};
        } else if ($scope.tasksType == "assignee") {
            return {"size": 1000, "assignee": $rootScope.UserId};
        } else {//candidate
            return {"size": 1000, "candidateUser": $rootScope.UserId};
        }
    }


    /**
     * Performs the load of the tasks and sets the tasksType
     * @param tasksType
     */
    $scope.loadTasksType = function (tasksType) {
        $scope.tasksType = tasksType;
        $scope.loadTasks();
    }

    /**
     * Loads the tasks
     */
    $scope.loadTasks = function () {
        //$scope.tasks = TasksService.get(getTasksQuery());
        loadTasks(getTasksQuery());
    }

    var loadTasks = function (params) {
        $scope.tasks = TasksService.get(params);
    }


    $scope.loadTask = function (task) {
        TasksModalService.loadTaskForm(task);
    };


    $rootScope.$on('refreshData', function (event, data) {
        //$scope.deta
        $scope.loadTasks();

    });

    /**
     * Load definitions
     */
    $scope.loadDefinitions = function () {
        $scope.processes = ProcessDefinitionService.get({latest: "true"});
    }

    /**
     * starts the process
     * @param processDefinition
     */
    $scope.startTheProcess = function (processDefinition) {

        TasksModalService.loadProcessForm(processDefinition);

        var formService = new FormDataService({processDefinitionId: processDefinition.id});
        formService.$startTask(function (data) {
            console.log(data);
        });
    };

    $scope.loadUserGroups = function () {
        $scope.userGroups = GroupService.get({"member": $rootScope.username});
    }

    $scope.loadTasksGroups = function (group) {
        console.log(group);
        loadTasks({"size": 1000, "candidateGroup": group.id});
    }

    $rootScope.validateUser.then(function(){
            if(!$rootScope.loggedin)
                $location.path('/login');
           else {
                $scope.loadUserGroups();
                $scope.loadTasks();
                $scope.loadDefinitions();
            }


        }, function(){
            console.log("in task then fail");
            $location.path('/login');

        }
        );
});