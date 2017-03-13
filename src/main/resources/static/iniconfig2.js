angular.module('mtrak', ['ngTable', 'ngSanitize', 'ngCsv', 'chart.js'])
.controller('iniController', function ($scope, $filter, $http, ngTableParams) {
	var dt = new Date();
	dt.setDate(1); // setting first day to avoid complexity on leap year and month duration issues
	$scope.current = dt;
	
	$scope.chart_labels = [];
	$scope.chart_data = [];
	
	
	//################################################
	var list = [];
	jQuery(function($) {
        var panelList2 = $('#draggablePanelList2');

        panelList2.sortable({
            // Only make the .panel-heading child elements support dragging.
            // Omit this to make then entire <li>...</li> draggable.
            handle: '.thumbnail', 
            update: function() {
            	var list = [];
                $('.col-xs-18', panelList2).each(function(index, elem) {
                     var $listItem = $(elem),
                         newIndex = $listItem.index();
			
			

			//var id = elem.item.attr("[id=codem]").val();
			
			
			var str = $(this).find("[id=codem]").text();
			var res = str.split("=");
			res=res[1];
			console.log(res.trim());
			console.log(index);
			
			
			list.push({ code: res.trim(), id: index+1});
			
                     // Persist the new indices.
                });
                //requ
                
                var jsData=JSON.stringify(list);
    			var config = {
    	                headers : {
    	                    'Content-Type': 'application/json;charset=utf-8;'
    	                }
    	            }
    			
    			
    			$http.post('/sortingUpdate', jsData, config)
    	        .success(function (data, status, headers, config) {
    	            $scope.PostDataResponse = data;
    	            
    	        })
    	        .error(function (data, status, header, config) {
    	            $scope.ResponseDetails = data;
    	        });
    			
    			//##############
            }
        });
    });
	
	//###################################################
	
	
	
	
	
	
	
	
	
	
	
	
	
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
		$scope.jobCreate="";
		$scope.deliveryTimeDuration="",
		$scope.validateMessenger="";
		$scope.gPSEnable="";
		
		$scope.typeModal="";
		$scope.codeModal="";
		$scope.descriptionModal="";
		$scope.signatureModel="";
		$scope.additionalTextModel="";
		$scope.LbAdditionalTextModel="";
		$scope.mnAdditionalTextModel="";
		$scope.rqShelfmarkModel="";
		$('#btnAdd').prop('title', 'save');
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
    		$scope.jobCreate=data.gData.jobCreate;
    		$scope.deliveryTimeDuration=data.gData.deliveryTimeDuration,
    		$scope.validateMessenger=data.gData.validateMessenger;
    		$scope.gPSEnable=data.gData.gPSEnable;
    		
            $scope.items = data.eventList;
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
				jobCreate: $scope.jobCreate,
				deliveryTimeDuration: $scope.deliveryTimeDuration,
				validateMessenger: $scope.validateMessenger,
				gPSEnable: $scope.gPSEnable
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
	            $scope.resetForm();
	            
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
		$scope.signatureModel="";
		$scope.additionalTextModel="";
		$scope.LbAdditionalTextModel="";
		$scope.mnAdditionalTextModel="";
		$scope.rqShelfmarkModel="";
		$('#btnAdd').prop('title', 'save');
		$('#btnAdd').text('Add');
	}
	
	$scope.add=function() {
		var eData = {
			type: $scope.typeModal,
			code: $scope.codeModal,
			systemCode: "tt",
			description: $scope.descriptionModal,
			rqSignature: $scope.signatureModel,
			rqAdditionalText: $scope.additionalTextModel,
			rqShelfmark: $scope.rqShelfmarkModel,
			lbAdditionalText: $scope.LbAdditionalTextModel,
			mnAdditionalText: $scope.mnAdditionalTextModel
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
		$scope.signatureModel="";
		$scope.additionalTextModel="";
		$scope.mnAdditionalTextModel="";
		$scope.rqShelfmarkModel="";
		$scope.LbAdditionalTextModel="";
		
	}
	
	
	$scope.edit = function(log){
		$scope.typeModal=log.type;
		$scope.codeModal=log.code;
		$scope.descriptionModal=log.description;
		$scope.signatureModel=log.rqSignature;;
		$scope.additionalTextModel=log.rqAdditionalText;
		$scope.LbAdditionalTextModel=log.lbAdditionalText;
		$scope.mnAdditionalTextModel=log.mnAdditionalText;
		$scope.rqShelfmarkModel=log.rqShelfmark;
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
