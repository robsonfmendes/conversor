'use strict';

var app = angular.module(
  'app', 
  [ 'ngAnimate', 
    'ui.bootstrap',
    'ngResource',
    'ngFileUpload' ]);

app.factory('Model', function($resource) {
  return $resource('api/upload');
});

app.controller('ConversorDeVideoController', function ($scope, Upload, $timeout) {
	
	  $scope.urlVideo = "";	  
	  $scope.existeUrlVideo = 0;
	  
	  $scope.uploadVideo = function(file) {
		  $("#popupCarregando").show();
		  file.upload = Upload.upload({
		    url: 'api/upload/file',
		    data: {file: file },
		  });
		  
		  file.upload.then(function (response) {
		    $timeout(function () {
		    	$("#popupCarregando").hide();
		    	if(response.data == ""){
		    		$scope.errorMsg = "Server Error!"
		    	}else{
				    file.result = response.data;
				    $scope.urlVideo = response.data;
				    $scope.existeUrlVideo = 1;
		    	}
		    });
		  }, 		  
		  function (response) {
		    if (response.status > 0)
		    $scope.errorMsg = "Server Error! ("+response.data+")";
		    $("#popupCarregando").hide();

		  });
	  }
});
