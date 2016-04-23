angular.module('activitiApp').factory('ProcessDefinitionService', function ($resource) {
    var data = $resource('repository/process-definitions/:processDefinitionId', {processDefinitionId: "@processDefinitionId"},{
            update: {method: 'PUT', params: {processDefinitionId: "@processDefinitionId"}}
        }
    );
    return data;
});
