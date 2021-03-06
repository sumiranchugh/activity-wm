angular.module('activitiApp').factory('TasksModalService', function ($modal, FormDataService,TasksSubmitService, TasksService, $rootScope,UserService,ProcessInstanceService,ProcessInstancesService) {

    var ModalInstanceCtrl = function ($scope, $modalInstance, moment, taskDetailed, id, success, parent) {
        $scope.taskDetailed = taskDetailed;


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


        function extractDataFromFormForProcess(objectOfReference) {
            var objectToSave = {
                "processDefinitionId": objectOfReference.id,
                variables: [],
                "businessKey":"",
                "returnVariables":true
            };

            for (var key in objectOfReference.propertyForSaving) {
                var forObject = objectOfReference.propertyForSaving[key];

                if (!forObject.writable) {//if this is not writeable property do not use it
                    continue;
                }
                if(forObject.id==='businessKey'){
                    objectToSave.businessKey=forObject.value;
                }
                if (forObject.value != null) {

                    console.log(forObject);
                    var elem = {
                        "name": forObject.id,
                        "value": forObject.value
                    };
                    if (typeof forObject.datePattern != 'undefined' && forObject.datePattern!=null) {//format
                        var date = new Date(forObject.value);

                        elem.value = moment(date).utc();
                    }
                    objectToSave.variables.push(elem);
                }
            }
            var elem = {"name": "initiator", "value": $rootScope.UserId};
            objectToSave.variables.push(elem);
            objectToSave.returnVariables =true;

            return objectToSave;
        }

        $scope.finish = function (detailedTask) {
          //  $scope.assignMe(detailedTask);
            if (typeof detailedTask.propertyForSaving != "undefined") {
                detailedTask.propertyForSaving[id].value = success;
                var objectToSave = extractDataFromForm(detailedTask);

                taskDetailed.properties= objectToSave.properties;
                taskDetailed.assignee=$rootScope.UserId;

                var saveForm = new TasksSubmitService(taskDetailed);
                saveForm.$save({"taskId": detailedTask.id},function () {
                    emitRefresh();
                    $modalInstance.dismiss(parent);
                    parent.isDisabled = false;

                }, function () {
                    $rootScope.error = {};
                    $rootScope.error.isErr = true;
                    $rootScope.error.name = "Server Error";
                    $rootScope.error.desc = "Error Processing Task, Please contact Support";
                    parent.isDisabled = false;
                    $modalInstance.dismiss(parent);
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
                    $modalInstance.dismiss(parent);
                });
            }

        };

        $scope.startProcess = function (detailedTask) {

            if (typeof detailedTask.propertyForSaving != "undefined") {
                var objectToSave = extractDataFromFormForProcess(detailedTask);

                var saveForm = new ProcessInstancesService(objectToSave);
                saveForm.$save(function () {
                    emitRefresh();
                    $modalInstance.dismiss('cancel');
                });
            } else {
                var action = new TasksService();
                action.action = "complete";
                action.$save({"taskId": detailedTask.id}, function () {
                    emitRefresh();
                    $modalInstance.dismiss('cancel');
                });
            }

        };


        $scope.assignMe = function (detailedTask) {
            var taskToEdit = new TasksService({"assignee": $rootScope.UserId});
            taskToEdit.$update({"taskId": detailedTask.id}, function () {
                emitRefresh();
            });

        };

        $scope.takeOwnerShip = function (detailedTask) {
            var taskToEdit = new TasksService({"owner": $rootScope.username});
            taskToEdit.$update({"taskId": detailedTask.id}, function () {
                emitRefresh();
            });
        };

        $scope.openDatePicker = function (obj, $event) {
            $event.preventDefault();
            $event.stopPropagation();

            obj.opened = true;
        };

        $scope.setFormEnum = function (enumm, item,showText ) {
            item.value = enumm.id;
            if(typeof showText== 'undefined') {
                item.name = enumm.name;
            }else{
                item.name = showText;
            }
        };

        var emitRefresh = function () {
            $rootScope.$emit("refreshData", {});
        };

        $scope.cancel = function (taskDetailed) {
            parent.isDisabled = false;
            $modalInstance.dismiss('cancel');
        };

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

            if(elem.type=="user")
            {
                elem.enumValues  =  UserService.get();
            }
        }

        task.form = data;
        task.propertyForSaving = propertyForSaving;

    }

    var loadTaskForm = function (task) {
        console.log(task);
        FormDataService.get({"taskId": task.id}, function (data) {
            extractForm(task, data);
        }, function (data) {

            if (data.data.statusCode == 404) {
                alert("there was an error");
            }

        });
    };

    var loadCommentForm = function (task) {
        /*     console.log(task);
         FormDataService.get({"taskId": task.id}, function (data) {
         extractForm(task, data);
         }, function (data) {

         if (data.data.statusCode == 404) {
         alert("there was an error");
         }

         });*/
    };

    var loadProcessForm = function (processDefinition) {
        FormDataService.get({"processDefinitionId": processDefinition.id}, function (data) {
            extractForm(processDefinition, data)
        }, function (data) {

            if (data.data.statusCode == 404) {
                alert("there was an error");
            }

        });
    };


    var factory = {
        loadTaskForm: function (task) {
            var modalInstance = $modal.open({
                templateUrl: 'views/modals/taskForm.html',
                controller: ModalInstanceCtrl,

                resolve: {
                    taskDetailed: function () {
                        return task;
                    }
                }
            });

            modalInstance.result.then(function (taskDetailed) {

            }, function () {

            });
            loadTaskForm(task);
        },
        loadCommentForm: function (task, item, success, parent) {
            var modalInstance = $modal.open({
                templateUrl: 'views/modals/commentForm.html',
                controller: ModalInstanceCtrl,
                backdrop: true,

                resolve: {
                    taskDetailed: function () {
                        return task;
                    }, id: function () {
                        return item.id;
                    }, success: function () {
                        return success;
                    },
                    parent: function () {
                        return parent;
                    }
                }
            });

            modalInstance.result.then(function (cancel) {
                parent.isDisabled = false;
            }, function () {
                parent.isDisabled = false;
            });
            loadCommentForm(task);
        },
        loadProcessForm: function (processDefinition) {
            var modalInstance = $modal.open({
                templateUrl: 'views/modals/processDefinitionForm.html',
                controller: ModalInstanceCtrl,

                resolve: {
                    taskDetailed: function () {
                        return processDefinition;
                    }
                }
            });

            modalInstance.result.then(function (taskDetailed) {

            }, function () {

            });
            loadProcessForm(processDefinition);
        }
    };
    return factory;
});