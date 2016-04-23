angular.module('activitiApp').factory('GroupUserService', function ($resource) {
    var data = $resource('identity/groups/:group/members/:userId', {group: "@group",userId:"@userUrlId"});
    return data;
});
