angular.module('activitiApp').factory('ProcessInstancesService', function ($resource) {
    var data = $resource('runtime/process-instances/:processInstance', {processInstance: "@processInstance"});
    return data;
});

angular.module('activitiApp').factory('ProcessInstancesQueryService', function ($resource) {
    var data = $resource('query/process-instances/', {processInstance: "@processInstance"});
    return data;
});