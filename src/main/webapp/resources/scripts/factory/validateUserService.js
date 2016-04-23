angular.module('activitiApp').factory('ValidateUserService', function ($resource,$location) {
    var pname= {};
    for( properties in $location.search() ){
        pname[properties] = $location.search()[properties];
    }
    var data = $resource('getLoggedInUser', pname);
    return data;
});
