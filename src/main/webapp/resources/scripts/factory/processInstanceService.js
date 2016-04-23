angular.module('activitiApp').factory('ProcessInstanceService', function ($resource) {
    var data = $resource('process-instance/:processInstance', {processInstance: "@processInstance"});
    return data;
});

angular.module('activitiApp').factory('ProcessInstanceInfoService', function ($resource) {
    var data = $resource('info/process-instance/:processInstance', {processInstance: "@processInstance"});
    return data;
});