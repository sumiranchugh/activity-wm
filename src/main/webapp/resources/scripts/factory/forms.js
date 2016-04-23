angular.module('activitiApp').factory('FormDataService', function ($resource) {
    var data = $resource('form/form-data', {}, {
        startTask: {method:'GET',  params: {processDefinitionId: "@processDefinitionId"}}
    });
    return data;
});