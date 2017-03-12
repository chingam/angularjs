angular.module('mtrak', ['ngTable', 'ngSanitize', 'ngCsv', 'chart.js'])
.controller('iniController', function ($scope, $filter, $http, ngTableParams) {
	var dt = new Date();
	dt.setDate(1); // setting first day to avoid complexity on leap year and month duration issues
	$scope.current = dt;
	
	$scope.chart_labels = [];
	$scope.chart_data = [];
	
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
				populateChart();
			}
		});
	});


	var resetForm = function() {
		$('#txtTypem').val('');
		$('#txtCodem').val('');
		$('#txtDescriptionm').val('');
		$('#txtRqSignature').val('');
		$('#txtRqAdditionalText').val('');
		$('#txtRqShelfmark').val('');
		$('#txtLbAdditionalText').val('');
		$('#txtMnAdditionalText').val('');
		$scope.typeModal="";
		$scope.codeModal="";
		$scope.descriptionModal="";
		$scope.rqSignature="";
		$scope.rqAdditionalText="";
		$scope.rqShelfmark="";
		$scope.lbAdditionalText="";
		$scope.mnAdditionalText="";
	}




	function load(callback) {
		var mnth = $filter('date')($scope.current, "yyyy-MM");
		$http.get("logs/" + mnth).success(function(rs){
			rs = rs ? rs : [];
			var sites = [];
			angular.forEach(rs, function(it){
				it.lastUsed = $filter('date')(it.lastUsed, "yyyy-MM-dd HH:mm:ss Z");
				if (sites.filter(function(st){return st === it.site.toLowerCase()}).length === 0)
					sites.push(it.site.toLowerCase());
			});
			$scope.logs = rs;
			$scope.sites = sites;
			if (callback) callback();
		});
	}

	
	
	
	$scope.GetAllData = function () {
        $http.get('/fetch/'+$scope.code)
        .success(function (data, status, headers, config) {
            $scope.items = data;
        })
        .error(function (data, status, header, config) {
            $scope.ResponseDetails = "Data: " + data +
                "<br />status: " + status +
                "<br />headers: " + jsonFilter(header) +
                "<br />config: " + jsonFilter(config);
        });
    };
	
	$scope.edit = function(log){

		$scope.typeModal=log.type;
		$scope.codeModal=log.code;
		$scope.descriptionModal=log.descriptionModal;
		$scope.rqSignature=log.rqSignature;
		$scope.rqAdditionalText=log.rqAdditionalText;
		$scope.rqShelfmark=log.rqShelfmark;
		$scope.lbAdditionalText=log.lbAdditionalText;
		$scope.mnAdditionalText=log.mnAdditionalText;
		
		$('#txtTypem').val(log.type);
		$('#txtCodem').val(log.code);
		$('#txtDescriptionm').val(log.description);
		$('#txtRqSignature').val(log.rqSignature);
		$('#txtRqAdditionalText').val(log.rqAdditionalText);
		$('#txtRqShelfmark').val(log.rqShelfmark);
		$('#txtLbAdditionalText').val(log.lbAdditionalText);
		$('#txtMnAdditionalText').val(log.mnAdditionalText);
	    $("#myModalHorizontal").modal();

		console.log($scope.typeModal);
	    
	  };
	
	  $scope.deleteData = function(log){
		//  /delete/{systemCode}/{code}
		  $http.get('/delete/'+$scope.code+'/'+log.code)
	        .success(function (data, status, headers, config) {
	            $scope.message = data;
	        })
	        .error(function (data, status, header, config) {
	            $scope.ResponseDetails = data
	            });
		  
		  $scope.GetAllData();
		    
		  };
	
	
	
	$scope.add=function() {
		var gData = {
			logo: $scope.logo,
			versionFileUrl: $scope.versionFileUrl,
			code: $scope.code,
			description: $scope.description,
			type: $scope.type,
			email: $scope.email,
			uploadURL: $scope.uploadURL,
			uploadPath: $scope.uploadPath,
			processURL: $scope.processURL,
			dataCheckInterval: $scope.dataCheckInterval,
			blockScanLimit: $scope.blockScanLimit,
			warningScanLimit: $scope.warningScanLimit,
			maxUploadSize: $scope.maxUploadSize,
			uploadSleepInterval: $scope.uploadSleepInterval,
			jobCreate: $scope.jobCreate,
			deliveryTimeDuration: $scope.deliveryTimeDuration,
			validateMessenger: $scope.validateMessenger,
			gPSEnable: $scope.gPSEnable
            };
		gData=JSON.stringify(gData);
		
		var eData = {
			type: $scope.typeModal,
			code: $scope.codeModal,
			systemCode: $scope.code,
			description: $scope.descriptionModal,
			rqSignature: $scope.rqSignature,
			rqAdditionalText: $scope.rqAdditionalText,
			rqShelfmark: $scope.rqShelfmark,
			lbAdditionalText: $scope.lbAdditionalText,
			mnAdditionalText: $scope.mnAdditionalText
            };
		eData=JSON.stringify(eData);
		var config = {
                headers : {
                    'Content-Type': 'application/json;charset=utf-8;'
                }
            }
		
		
		$http.post('/generalConfig', gData, config)
        .success(function (data, status, headers, config) {
            $scope.PostDataResponse = data;
            
        })
        .error(function (data, status, header, config) {
            $scope.ResponseDetails = data;
        });
		
		$http.post('/eventConfig', eData, config)
        .success(function (data, status, headers, config) {
            $scope.PostDataResponse = data;
            resetForm();
            $('#myModalHorizontal').modal('hide');
            $('#logTableId').show();
            $scope.GetAllData();
        })
        .error(function (data, status, header, config) {
            $scope.ResponseDetails = data;
        });
		
		console.log(gData);
		console.log(eData);
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
