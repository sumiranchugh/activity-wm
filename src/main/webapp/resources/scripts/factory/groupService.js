angular.module('activitiApp').factory('GroupService', function ($resource) {
    var data = $resource('identity/groups/:group', {group: "@group"});
    return data;
});
