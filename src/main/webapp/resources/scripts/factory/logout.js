/**
 * Created by schug2 on 4/1/2016.
 */
angular.module('activitiApp').factory('LogoutService', function ($resource) {

    var data = $resource('logout');
    return data;
});

