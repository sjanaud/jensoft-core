registerNS('com.jensoft.jenscript.plugin.scatter');



com.jensoft.jenscript.plugin.scatter.ScatterCurve = function(serie,themeColor) {
	this.serie = serie;
	this.themeColor=themeColor;
	this.morphe='ellipse'; //ellipse,polygon,qinverse,rectangle,star
	
	this.setMorphe = function(morphe) {
		this.morphe = morphe;
	};

	this.getMorphe = function() {
		return this.morphe;
		valueOf = function(){
			return this.morphe;
		};
	};
	
	this.getCurveXML = function(){
		var colorutil = new com.jensoft.jenscript.ColorUtil();
		var curveXML = '<curve>'+
					   '<themecolor>'+colorutil.format(this.themeColor)+'</themecolor>'+
					   '<morphe>'+this.morphe+'</morphe>';
		curveXML = curveXML + this.serie.getSerieXML();		
		curveXML = curveXML + '</curve>';
		return curveXML;
	};
};

com.jensoft.jenscript.plugin.scatter.ScatterCurvePlugin = function() {
	
	this.name;
	this.id;
	this.window2D;
	this.curves = [];
	
	this.setID = function(id) {
		this.id = id;
	};

	this.getID = function() {
		return this.id;
		valueOf = function(){
			return this.id;
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
	
	

	this.addCurve = function(curve){
		this.curves[this.curves.length] = curve;
	};
	
	this.getPluginXML = function(){
		var pluginXML = '<plugin>'+
		'<id>'+this.id+'</id>'+
		'<name>'+this.name+'</name>'+
		'<class>'+'com.jensoft.sw2d.core.plugin.scatter.ScatterCurvePlugin'+'</class>'+
		'<params><curves>';
		
		for(var i=0; i<this.curves.length; i++) {
			var c = this.curves[i];
			pluginXML = pluginXML + c.getCurveXML();
		}
		
		pluginXML = pluginXML + '</curves></params></plugin>';
			
		return pluginXML;
	};
	
};