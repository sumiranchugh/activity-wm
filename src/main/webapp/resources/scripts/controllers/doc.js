/**
 * Created by schug2 on 4/1/2016.
 */
angular.module('activitiApp').controller('DocCtrl', function($scope) {

    $scope.pdfName = 'Relativity: The Special and General Theory by Albert Einstein';
    $scope.pdfUrl = 'https://file.wikileaks.org/file/creating-a-unique-id-for-every-resident-in-india-2009.pdf';
    $scope.scroll = 0;
    $scope.loading = 'loading';



    $scope.getNavStyle = function(scroll) {
        if(scroll > 100) return 'pdf-controls fixed';
        else return 'pdf-controls';
    }

    $scope.onError = function(error) {
        console.log(error);
    }

    $scope.onLoad = function() {
        $scope.loading = '';
    }

    $scope.onProgress = function(progress) {
        console.log(progress);
    }





});
