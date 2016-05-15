angular.module('activitiApp').directive('aaForm', function () {
    return {
        restrict: 'E',

        templateUrl: "views/directives/form.html"

    };
});
angular.module('activitiApp').directive('aaComment', function () {
    return {
        restrict: 'E',

        templateUrl: "views/directives/comment.html"

    };
});