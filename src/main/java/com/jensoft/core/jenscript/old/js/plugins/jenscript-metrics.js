registerNS('com.jensoft.jenscript.plugin.metrics');

com.jensoft.jenscript.plugin.metrics.MilliMetrics = function() {

	this.id;
	this.window2D;
	this.name;
	this.axis;
	this.ref;
	this.major;
	this.median;
	this.minor;	
	this.metricscolor;
	this.paintAxisBaseLine=true;
	
	
	this.setID = function(id) {
		this.id = id;
	};

	this.getID = function() {
		return this.id;
		valueOf = function(){
			return this.id;
		};
	};
	
	this.setPaintAxisBaseLine = function(paintAxisBaseLine) {
		this.paintAxisBaseLine = paintAxisBaseLine;
	};

	this.getPaintAxisBaseLine = function() {
		return this.paintAxisBaseLine;
		valueOf = function(){
			return this.paintAxisBaseLine;
		};
	};
	
	
	this.setName = function(name) {
		this.name = name;
	};

	this.getName = function() {
		return this.name;
		valueOf = function(){
			return this.name;
		};
	};
	
	this.setWindow2D = function(window2D) {
		this.window2D = window2D;
	};

	this.getWindow2D = function() {
		return this.window2D;
		valueOf = function(){
			return this.window2D;
		};
	};
	
	this.setAxis = function(axis) {
		this.axis = axis;
	};
	
	this.getAxis  = function() {
		return this.axis;
	};
	
	this.setRef= function(ref) {
		this.ref = ref;
	};
	
	this.getRef  = function() {
		return this.ref;
	};
	
	this.setMajor= function(major) {
		this.major = major;
	};
	
	this.getMajor  = function() {
		return this.major;
	};
	
	this.setMedian= function(median) {
		this.median = median;
	};
	
	this.getMedian  = function() {
		return this.median;
	};
	
	this.setMinor= function(minor) {
		this.minor = minor;
	};
	
	this.getMinor  = function() {
		return this.minor;
	};
	
	this.setMetricsColor = function(metricscolor) {
		this.metricscolor = metricscolor;
	};
	
	this.getMetricsColor  = function() {
		return this.metricscolor;
	};
	
	this.getPluginXML = function(){
		var pluginXML = '<plugin>'+
							'<id>'+this.id+'</id>'+
							'<name>'+this.name+'</name>'+
							'<class>'+'com.jensoft.sw2d.core.plugin.metrics.axis.AxisMilliMetrics'+'</class>'+
							'<params>'+
								'<axis>'+this.axis+'</axis>'+
								'<ref>'+this.ref+'</ref>'+
								'<major>'+this.major+'</major>'+
								'<median>'+this.median+'</median>'+
								'<minor>'+this.minor+'</minor>'+
								'<paintAxis>'+this.paintAxisBaseLine+'</paintAxis>'+
							'</params>'+
						'</plugin>';
			return pluginXML;
	};
	
	
};