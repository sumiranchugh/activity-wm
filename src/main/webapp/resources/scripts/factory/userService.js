angular.module('activitiApp').factory('UserService', function ($resource) {
    var data = $resource('identity/users/:user', {user: "@user", size: 100});
    return data;
});
