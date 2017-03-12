angular.module('mtrak', ['ngTable', 'ngSanitize', 'ngCsv', 'chart.js'])
.controller('logController', function ($scope, $filter, $http, ngTableParams) {
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