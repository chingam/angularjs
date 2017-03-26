var app=angular.module('mtrak', ['ngTable', 'ngSanitize', 'ngCsv', 'chart.js']);
var dddd;
app.factory('myService', function() {
	 var savedData = {}
	 function set(log) {
		 console.log(log);
		 dddd = log;
	 }
	 function get() {
		 console.log(savedData);
	  return savedData;
	 }

	 return {
	  set: set,
	  get: get
	 }

	});


app.controller('iniController', function ($scope, $filter, $http, ngTableParams, myService) {
	
	//###################################################
	
	$scope.refresh = function() {
		console.log("reset Call..");
		getClear();
		$scope.resetForm();
	};
	
	var getClear = function () {
		$http.get('/fetch/clear')
        .success(function (data, status, headers, config) {
            $scope.message = data;
            $("#code").prop('disabled', false);
        })
        .error(function (data, status, header, config) {
            $scope.ResponseDetails = data
            });
    };
    
    
   
    
    getClear();
    modify();
    
    function modify(){
    	$http.get('/getCache')
        .success(function (data, status, headers, config) {
        	console.log(data.message);
        	if(data.message=='fail'){
        		
        	}else{

        		$http.get('/fetch/system/'+data.message)
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

                })
                .error(function (data, status, header, config) {
                    $scope.ResponseDetails = "Data: " + data +
                        "<br />status: " + status +
                        "<br />headers: " + jsonFilter(header) +
                        "<br />config: " + jsonFilter(config);
                });
        	
        		
        	}
        })
        .error(function (data, status, header, config) {
            $scope.ResponseDetails = "Data: " + data +
                "<br />status: " + status +
                "<br />headers: " + jsonFilter(header) +
                "<br />config: " + jsonFilter(config);
        });
    }
	
    
    
    
    
	$scope.resetForm=function(){
		$scope.items="";
		$scope.searchModel="";
		$scope.logo="";
		$scope.versionFileUrl="";
		$scope.code="";
		$scope.description="";
		$scope.type="";
		$scope.email="";
		$scope.uploadURL="";
		$scope.uploadPath="";
		$scope.processURL="";
		$scope.dataCheckInterval="";
		$scope.blockScanLimit="";
		$scope.warningScanLimit="";
		$scope.maxUploadSize="";
		$scope.uploadSleepInterval="";
		$scope.jobCreate=false;
		$scope.deliveryTimeDuration="",
		$scope.validateMessenger=false;
		$scope.gPSEnableData=false;
		
		$scope.typeModal="";
		$scope.codeModal="";
		$scope.descriptionModal="";
		$scope.signatureModel=false;
		$scope.additionalTextModel=false;
		$scope.LbAdditionalTextModel="";
		$scope.mnAdditionalTextModel=false;
		$scope.rqShelfmarkModel=false;
		$('#btnAdd').prop('title', 'save');
		$('.nav-tabs a[href="#SystemTableOne"]').tab('show');
	}
	
	$scope.searching=function(){
		$http.get('/fetch/system/'+$scope.searchModel)
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

        })
        .error(function (data, status, header, config) {
            $scope.ResponseDetails = "Data: " + data +
                "<br />status: " + status +
                "<br />headers: " + jsonFilter(header) +
                "<br />config: " + jsonFilter(config);
        });
	}
	
	
	$scope.SaveData=function(){
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
				jobCreate: ($scope.jobCreate===true?"Y":"N"),
				deliveryTimeDuration: $scope.deliveryTimeDuration,
				validateMessenger: ($scope.validateMessenger===true?"Y":"N"),
				gPSEnable: ($scope.gPSEnableData===true?"Y":"N")
	            };
			gData=JSON.stringify(gData);
			var config = {
	                headers : {
	                    'Content-Type': 'application/json;charset=utf-8;'
	                }
	            }
			
			
			$http.post('/generalConfig', gData, config)
	        .success(function (data, status, headers, config) {
	            $scope.PostDataResponse = data;
	            alert(data.message);
	            $("#code").prop('disabled', false);
	            $scope.resetForm();
	            getClear();
	            
	        })
	        .error(function (data, status, header, config) {
	            $scope.ResponseDetails = data;
	            $scope.resetForm();
	        });
			
	}
	
	$scope.addNew=function(){
		$scope.typeModal="";
		$scope.codeModal="";
		$scope.descriptionModal="";
		$scope.signatureModel=false;
		$scope.additionalTextModel=false;
		$scope.LbAdditionalTextModel="";
		$scope.mnAdditionalTextModel=false;
		$scope.rqShelfmarkModel=false;
		$('#btnAdd').prop('title', 'save');
		$('#btnAdd').text('Add');
	}
	
	$scope.add=function() {
		var eData = {
			type: $scope.type,
			code: $scope.codeModal,
			systemCode: "tt",
			description: $scope.descriptionModal,
			rqSignature: ($scope.signatureModel===true?"Y":"N"),
			rqAdditionalText: ($scope.additionalTextModel===true?"Y":"N"),
			rqShelfmark: ($scope.rqShelfmarkModel===true?"Y":"N"),
			lbAdditionalText: ($scope.additionalTextModel===true?$scope.LbAdditionalTextModel:""),
			mnAdditionalText: ($scope.mnAdditionalTextModel===true?"Y":"N")
            };
		eData=JSON.stringify(eData);
		var config = {
		                headers : {
		                    'Content-Type': 'application/json;charset=utf-8;'
		                }
            }
		
			if ($('#btnAdd').attr("title") === "Update") {
			
			$http.post('/eventConfigu/update', eData, config)
	        .success(function (data, status, headers, config) {
	            $scope.PostDataResponse = data;
	            
	            $('#myModalHorizontal').modal('hide');
	            $scope.GetAllData();
	        })
	        .error(function (data, status, header, config) {
	            $scope.ResponseDetails = data;
	        });
			
		}else{
			$http.post('/eventConfig2', eData, config)
	        .success(function (data, status, headers, config) {
	            $scope.PostDataResponse = data;
	            
	            $('#myModalHorizontal').modal('hide');
	            $scope.GetAllData();
	        })
	        .error(function (data, status, header, config) {
	            $scope.ResponseDetails = data;
	        });
			
		}
		
		$scope.typeModal="";
		$scope.codeModal="";
		$scope.descriptionModal="";
		$scope.signatureModel=false;
		$scope.additionalTextModel=false;
		$scope.mnAdditionalTextModel=false;
		$scope.rqShelfmarkModel=false;
		$scope.LbAdditionalTextModel="";
		
	}
	
	
	$scope.edit = function(log){
		$scope.typeModal=log.type;
		$scope.codeModal=log.code;
		$scope.descriptionModal=log.description;
		$scope.signatureModel=(log.rqSignature==="Y"?true:false);
		$scope.additionalTextModel=(log.rqAdditionalText==="Y"?true:false);
		$scope.LbAdditionalTextModel=log.lbAdditionalText;
		$scope.mnAdditionalTextModel=(log.mnAdditionalText==="Y"?true:false);
		$scope.rqShelfmarkModel=(log.rqShelfmark==="Y"?true:false);
		$('#btnAdd').prop('title', 'Update');
		$('#btnAdd').text('Update');
	    $("#myModalHorizontal").modal();
	  };
	
	  $scope.deleteData = function(log){
			//  /delete/{systemCode}/{code}
			  $http.get('/delete2/'+log.code)
		        .success(function (data, status, headers, config) {
		            $scope.message = data;
		        })
		        .error(function (data, status, header, config) {
		            $scope.ResponseDetails = data
		            });
			  
			  $scope.GetAllData();
			    
			  };
	
	
	

	$scope.reset = function() {
						$scope.resetForm();
					};

	

	
	
	
	$scope.GetAllData = function () {
        $http.get('/fetch/all')
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
	
	
});


app.controller('sitesController', function ($scope, $filter, $http, ngTableParams, myService) {
	var dt = new Date();
	dt.setDate(1); // setting first day to avoid complexity on leap year and month duration issues
	$scope.current = dt;
	
	$scope.chart_labels = [];
	$scope.chart_data = [];
	
	$scope.edit=function(log){
		$scope.getCacheData(log.code);
		
//		alert($scope.resData);
		
		
		/*var resData=getCall('/cache/'+log.code);
		if(resData.message==='success'){
			window.location.href = '/iniconfig6.html';
		}else{
			alert("server down");
		}
		*/
		
		
	}
	
	$scope.getCacheData = function (log) {
        $http.get('/cache/'+log)
        .success(function (data, status, headers, config) {
        	if(data.message==="success"){
    			window.location.href = '/iniconfig6.html';
    		}
        })
        .error(function (data, status, header, config) {
            $scope.ResponseDetails = "Data: " + data +
                "<br />status: " + status +
                "<br />headers: " + jsonFilter(header) +
                "<br />config: " + jsonFilter(config);
        });
    };
	
	
	$scope.fetching=function(log){
		$http.get('/site/system/'+log.code)
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
	
	
	$scope.deleteData=function(log){
		$http.get('/site/delete/'+log.code)
        .success(function (data, status, headers, config) {
        	$scope.reload();
        })
        .error(function (data, status, header, config) {
            $scope.ResponseDetails = "Data: " + data +
                "<br />status: " + status +
                "<br />headers: " + jsonFilter(header) +
                "<br />config: " + jsonFilter(config);
        });
	}
	
	
	$scope.SaveData=function(){
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
				jobCreate: ($scope.jobCreate===true?"Y":"N"),
				deliveryTimeDuration: $scope.deliveryTimeDuration,
				validateMessenger: ($scope.validateMessenger===true?"Y":"N"),
				gPSEnable: ($scope.gPSEnableData===true?"Y":"N")
	            };
			gData=JSON.stringify(gData);
			var config = {
	                headers : {
	                    'Content-Type': 'application/json;charset=utf-8;'
	                }
	            }
			
			
			$http.post('/sites/copy', gData, config)
	        .success(function (data, status, headers, config) {
	            $scope.PostDataResponse = data;
	            if(data.message!=="success"){
	            	alert(data.message);
	            	$('#myModalHorizontal').modal('show');
	            }else{
	            	$scope.reload();
		            $('#myModalHorizontal').modal('hide');
	            }
	            
	            
	        })
	        .error(function (data, status, header, config) {
	            $scope.ResponseDetails = data;
	            $scope.resetForm();
	        });
			
	}
	
	
	
	load(function(){
		$scope.logsTable = new ngTableParams({
			page : 1,
			count : 10
		}, {
			counts: [], // hide page counts control
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

