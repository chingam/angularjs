angular.module('mtrak', ['ngTable', 'ngSanitize', 'ngCsv', 'chart.js'])
.controller('sitesController', function ($scope, $filter, $http, ngTableParams) {
	var dt = new Date();
	dt.setDate(1); // setting first day to avoid complexity on leap year and month duration issues
	$scope.current = dt;
	
	$scope.chart_labels = [];
	$scope.chart_data = [];
	
	$scope.edit=function(log){
		$http.get('/fetch/system/'+log.code)
        .success(function (data, status, headers, config) {
        	$scope.logo=data.gData.logo;
    		$scope.versionFileUrl=data.gData.versionFileUrl;
    		$scope.code=data.gData.code;
    		$scope.description=data.gData.description;
    		$scope.type=data.gData.type;
    		$scope.email=data.gData.email;
    		$scope.uploadURL=data.gData.uploadURL;
    		$scope.uploadPath=data.gData.uploadPath;
    		$scope.processURL=data.gData.processURL;
    		$scope.dataCheckInterval=data.gData.dataCheckInterval;
    		$scope.blockScanLimit=data.gData.blockScanLimit;
    		$scope.warningScanLimit=data.gData.warningScanLimit;
    		$scope.maxUploadSize=data.gData.maxUploadSize;
    		$scope.uploadSleepInterval=data.gData.uploadSleepInterval;
    		$scope.jobCreate=(data.gData.jobCreate==="Y"?true:false);
    		$scope.deliveryTimeDuration=data.gData.deliveryTimeDuration;
    		$scope.validateMessenger=(data.gData.validateMessenger=="Y"?true:false);
    		$scope.gPSEnableData=(data.gData.gPSEnable==="Y"?true:false);
    		
            	$scope.items = data.eventList;
            	$("#code").prop('disabled', true);
            	$('.nav-tabs a[href="#SystemTableOne"]').tab('show');
            	window.location.href = '/iniconfig6.html';

        })
        .error(function (data, status, header, config) {
            $scope.ResponseDetails = "Data: " + data +
                "<br />status: " + status +
                "<br />headers: " + jsonFilter(header) +
                "<br />config: " + jsonFilter(config);
        });
	}
	
	
	$scope.fetching=function(log){
		$http.get('/fetch/system/'+log.code)
        .success(function (data, status, headers, config) {
        	$scope.logo=data.gData.logo;
    		$scope.versionFileUrl=data.gData.versionFileUrl;
    		$scope.code=data.gData.code+"-Copy";
    		$scope.description=data.gData.description;
    		$scope.type=data.gData.type;
    		$scope.email=data.gData.email;
    		$scope.uploadURL=data.gData.uploadURL;
    		$scope.uploadPath=data.gData.uploadPath;
    		$scope.processURL=data.gData.processURL;
    		$scope.dataCheckInterval=data.gData.dataCheckInterval;
    		$scope.blockScanLimit=data.gData.blockScanLimit;
    		$scope.warningScanLimit=data.gData.warningScanLimit;
    		$scope.maxUploadSize=data.gData.maxUploadSize;
    		$scope.uploadSleepInterval=data.gData.uploadSleepInterval;
    		$scope.jobCreate=(data.gData.jobCreate==="Y"?true:false);
    		$scope.deliveryTimeDuration=data.gData.deliveryTimeDuration;
    		$scope.validateMessenger=(data.gData.validateMessenger=="Y"?true:false);
    		$scope.gPSEnableData=(data.gData.gPSEnable==="Y"?true:false);
    			
    			$scope.items = data.eventList;
    			console.log($scope.items.length);
            	$('.nav-tabs a[href="#home"]').tab('show');

        })
        .error(function (data, status, header, config) {
            $scope.ResponseDetails = "Data: " + data +
                "<br />status: " + status +
                "<br />headers: " + jsonFilter(header) +
                "<br />config: " + jsonFilter(config);
        });
	}
	
	
	
	load(function(){
		$scope.logsTable = new ngTableParams({
			page : 1,
			count : 10
		}, {
			total : $scope.logs.length,
			getData : function($defer, params) {
				var dttosw = params.sorting() ? $filter('orderBy')($scope.logs, params.orderBy()) : $scope.logs;
				if ($scope.selectedSite !== "") {
					params.filter().site = $scope.selectedSite;
				} else if (params.filter().hasOwnProperty('site')) {
					delete params.filter().site;
				}
				dttosw = params.filter() ? $filter('filter')(dttosw, params.filter()) : dttosw;
				$scope.data = dttosw.slice((params.page() - 1) * params.count(), params.page() * params.count());
				params.total(dttosw.length);
				$scope.dttosw = dttosw;
				$defer.resolve($scope.data);
				
			}
		});
	});

	function load(callback) {
		$http.get('/sites/it5555')
        .success(function (data, status, headers, config) {
        	$scope.logs = data;
        	if (callback) callback();
        })
        .error(function (data, status, header, config) {
            $scope.ResponseDetails = data
            });
		
	}

	$scope.reload = function() {
		load(function(){
			$scope.logsTable.page(1);
			$scope.logsTable.total($scope.logs.length);
			$scope.logsTable.reload();
		});
	}

	$scope.showNext = function() {
		var thm = new Date();
		return ($scope.current.getFullYear() < thm.getFullYear() || $scope.current.getMonth() < thm.getMonth());
	}
	
	$scope.loadMonth = function(mnth) {
		$scope.current.setMonth($scope.current.getMonth() + mnth);
		$scope.reload();
	}
	
	function populateChart() {
		var lbls = [], dts = [];
		angular.forEach($scope.dttosw, function(it){
			lbls.push(it.site + " : " + it.device);
			dts.push(it.count);
		});
		$scope.chart_labels = lbls;
		$scope.chart_data = dts;
	}
	
	var $chart;
	$scope.$on("create", function (event, chart) {
		if (typeof $chart !== "undefined") {
			$chart.destroy();
		}
		$chart = chart;
	});
	

	$scope.chart_options = {
		tooltipTemplate : function(label) {
			return label.label + ' : ' + label.value;
		}
	}
	
	$scope.csv_headers = ["Site Code", "Site Name", "Device ID", "Last Used", "Last Used By", "Count"];
	
	$scope.filterSite = function() {
		$scope.logsTable.reload();
	}
	
	$scope.selectedSite = "";
});