angular.module('activitiApp').factory('TasksService', function ($resource) {
    var data = $resource('runtime/tasks/:taskId', {taskId: "@taskId"},{
        update: {method: 'PUT', params: {taskId: "@taskId"}},
        candidateTasks:{method: 'GET', params:{candidateUser:"@candidateUser"}},
        assignedTasks:{method: 'GET', params:{assignee:"@assignee"}},
        tasksforprocess:{method:'GET', params:{processInstanceId: "@processInstanceId"}}
    });
    return data;
});

angular.module('activitiApp').factory('TasksIdentityService', function ($resource) {
    var data = $resource('runtime/tasks/:taskId/identitylinks', {taskId: "@taskId"});
    return data;
});


angular.module('activitiApp').factory('TasksSubmitService', function ($resource) {
    var data = $resource('runtime/tasks/submit/:taskId', {taskId: "@taskId"});
    return data;
});

