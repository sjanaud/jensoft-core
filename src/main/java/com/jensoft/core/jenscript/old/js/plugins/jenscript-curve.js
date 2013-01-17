registerNS('com.jensoft.jenscript.plugin.curve');



com.jensoft.jenscript.plugin.curve.Curve = function(serie,themeColor) {
	this.serie = serie;
	this.themeColor=themeColor;
	this.stroke;
	this.glyphs = [];
	
	this.setStroke = function(stroke) {
		this.stroke = stroke;
	};

	this.getStroke = function() {
		return this.stroke;
		valueOf = function(){
			return this.stroke;
		};
	};
	
	this.addGlyph = function(glyph) {
		this.glyphs[this.glyphs.length] = glyph;
	};
	
	
	
	this.getCurveXML = function(){
		var colorutil = new com.jensoft.jenscript.ColorUtil();
		var curveXML = '<curve>'+
					   '<themecolor>'+colorutil.format(this.themeColor)+'</themecolor>';
		curveXML = curveXML + this.serie.getSerieXML();		
		if(typeof this.stroke != 'undefined'){
			curveXML = curveXML + this.stroke.getStrokeXML();
		}
		curveXML = curveXML + '<glyphs>';
		for(var i=0; i<this.glyphs.length; i++) {			
			var g = this.glyphs[i];
			curveXML = curveXML + g.getGlyphXML();		 
		}
		curveXML = curveXML + '</glyphs></curve>';
		return curveXML;
	};
};

com.jensoft.jenscript.plugin.curve.CurvePlugin = function() {
	
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
		'<class>'+'com.jensoft.sw2d.core.plugin.curve.CurvePlugin'+'</class>'+
		'<params><curves>';
		
		for(var i=0; i<this.curves.length; i++) {
			var c = this.curves[i];
			pluginXML = pluginXML + c.getCurveXML();
		}
		
		pluginXML = pluginXML + '</curves></params></plugin>';
			
		return pluginXML;
	};
	
};