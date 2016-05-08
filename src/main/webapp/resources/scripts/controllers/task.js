angular.module('activitiApp').controller("TasksCtrl", function ($scope, $rootScope, $location, ValidateUserService,
                                                                TasksService, FormDataService, moment, $modal,
                                                                TasksModalService, ProcessDefinitionService, GroupService,TasksSubmitService ) {

    /**
     * involved
     * owned
     * assigned
     *
     * @type {string}
     */
    $scope.tasksType = "assignee";

    $scope.isDisabled=false;


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
    };


    /**
     * Finish tasks
     * */
    $scope.finish = function (detailedTask, item,success) {
        $scope.isDisabled=true;
        if (typeof detailedTask.propertyForSaving != "undefined") {

            if(item!="undefined"){
                detailedTask.propertyForSaving[item.id].value=success;
            }

            var objectToSave = extractDataFromForm(detailedTask);

            detailedTask.properties= objectToSave.properties;
            detailedTask.assignee=$rootScope.UserId;

            var saveForm = new TasksSubmitService(detailedTask);
            saveForm.$save({"taskId": detailedTask.id},function () {
                emitRefresh();
                $scope.isDisabled=false;
               // $modalInstance.dismiss('cancel');
            }, function(){
                $scope.isDisabled=false;
                $rootScope.error = {};
                $rootScope.error.isErr = true;
                $rootScope.error.name = "Connect Error";
                $rootScope.error.desc = "Error Approving tasks. Please contact Support";
                alert("Error Approving tasks. Please contact Support");
            });
            /*var saveForm = new FormDataService(objectToSave);
             saveForm.$save(function () {
             emitRefresh();
             $modalInstance.dismiss('cancel');
             });*/
        } else {

            var action = new TasksService();
            action.action = "complete";
            action.$save({"taskId": detailedTask.id}, function () {
                emitRefresh();
                $scope.isDisabled=false;
              //  $modalInstance.dismiss('cancel');
            });
        }

    };

    function extractDataFromForm(objectOfReference) {
        var objectToSave = {
            "taskId": objectOfReference.id,
            properties: []
        };
        for (var key in objectOfReference.propertyForSaving) {
            var forObject = objectOfReference.propertyForSaving[key];

            if (!forObject.writable) {//if this is not writeable property do not use it
                continue;
            }

            if (forObject.value != null) {
                var elem = {
                    "id": forObject.id,
                    "value": forObject.value
                };
                if (typeof forObject.datePattern != 'undefined') {//format
                    var date = new Date(forObject.value);
                    elem.value = moment(date).format(forObject.datePattern.toUpperCase());
                }
                objectToSave.properties.push(elem);
            }
        }

        return objectToSave;
    }
    /**
     * Loads the tasks
     */
    $scope.loadTasks = function () {
        //$scope.tasks = TasksService.get(getTasksQuery());
        loadTasks(getTasksQuery());
    };

    var loadFormProperties = function(id,index) {
        FormDataService.get({"taskId": id}, function (data) {
            $scope.tasks.data[index] = extractForm($scope.tasks.data[index], data);
        });
    };

    var loadTasks = function (params) {

         TasksService.get(params, function (tasks) {

            for (var i =0;i< tasks.data.length;i++) {
                loadFormProperties(tasks.data[i].id,i);
                tasks.data[i].createTime=moment(new Date(tasks.data[i].createTime)).fromNow();
            }
             $scope.tasks=tasks;

        });

        console.log($scope.tasks);
    };


    function extractForm(task, data) {
        var propertyForSaving = {};

        for (var i = 0; i < data.formProperties.length; i++) {
            var elem = data.formProperties[i];
            propertyForSaving[elem.id] = {
                "value": elem.value,
                "id": elem.id,
                "writable": elem.writable
            };

            if (elem.datePattern != null) {//if date
                propertyForSaving[elem.id].opened = false; //for date picker
                propertyForSaving[elem.id].datePattern = elem.datePattern;
            }

            if (elem.required == true && elem.type == "boolean") {
                if (elem.value == null) {
                    propertyForSaving[elem.id].value = false;
                }
            }

            if (elem.type == "user") {
                elem.enumValues = UserService.get();
            }
        }

        task.form = data;
        task.propertyForSaving = propertyForSaving;
        return task;

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
    };

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
    };

    $scope.loadTasksGroups = function (group) {
        console.log(group);
        loadTasks({"size": 1000, "candidateGroup": group.id});
    };

    var emitRefresh = function () {
        $rootScope.$emit("refreshData", {});
    };

    $rootScope.validateUser.then(function () {
            if (!$rootScope.loggedin)
                $location.path('/login');
            else {
                $scope.loadUserGroups();
                $scope.loadTasks();
                $scope.loadDefinitions();
            }


        }, function () {
            console.log("in task then fail");
            $location.path('/login');

        }
    );
});
