registerNS('com.jensoft.jenscript.plugin.area');



com.jensoft.jenscript.plugin.area.Area = function(serie) {
	this.serie = serie;
	this.themeColor;
	this.areaBase;
	this.drawColor;
	this.drawStroke;
	this.bColor;
	this.bStroke;
	this.fillStartColor;
	this.fillEndColor;

	this.glyphs = [];
	
	this.setAreaBase = function(areaBase) {
		this.areaBase = areaBase;
	};

	this.getAreaBase = function() {
		return this.areaBase;
		valueOf = function(){
			return this.areaBase;
		};
	};
	
	this.setThemeColor = function(themeColor) {
		this.themeColor = themeColor;
	};

	this.getThemeColor = function() {
		return this.themeColor;
		valueOf = function(){
			return this.themeColor;
		};
	};
	
	this.setFillStartColor = function(fillStartColor) {
		this.fillStartColor = fillStartColor;
	};

	this.getFillStartColor = function() {
		return this.fillStartColor;
		valueOf = function(){
			return this.fillStartColor;
		};
	};
	
	this.setFillEndColor = function(fillEndColor) {
		this.fillEndColor = fillEndColor;
	};

	this.getFillEndColor = function() {
		return this.fillEndColor;
		valueOf = function(){
			return this.fillEndColor;
		};
	};
	
	this.setFillColor = function(fillColor) {
		this.fillStartColor = fillColor;	
		this.fillEndColor 	= fillColor;
	};
	
	this.setDrawColor = function(drawColor) {
		this.drawColor = drawColor;
	};

	this.getDrawColor = function() {
		return this.drawColor;
		valueOf = function(){
			return this.drawColor;
		};
	};
	
	this.setDrawStroke = function(drawStroke) {
		this.drawStroke = drawStroke;
	};

	this.getDrawStroke = function() {
		return this.drawStroke;
		valueOf = function(){
			return this.drawStroke;
		};
	};
	
	this.setBaseColor = function(baseColor) {
		this.bColor = baseColor;
	};

	this.getBaseColor = function() {
		return this.bColor;
		valueOf = function(){
			return this.bColor;
		};
	};
	
	this.setBaseStroke = function(baseStroke) {
		this.bStroke = baseStroke;
	};

	this.getBaseStroke = function() {
		return this.bStroke;
		valueOf = function(){
			return this.bStroke;
		};
	};
	
	this.addGlyph = function(glyph) {
		this.glyphs[this.glyphs.length] = glyph;
	};
	
	
	
	this.getAreaXML = function(){
		var colorutil = new com.jensoft.jenscript.ColorUtil();
		var areaXML = '<area>';
		areaXML = areaXML + this.serie.getSerieXML();
		areaXML = areaXML +
						'<themecolor>'+colorutil.format(this.themeColor)+'</themecolor>'+
						'<base>'+this.areaBase+'</base>'+
		   				'<fillstart>'+colorutil.format(this.fillStartColor)+'</fillstart>'+
		   				'<fillend>'+colorutil.format(this.fillEndColor)+'</fillend>';
		areaXML = areaXML +'<draw>'+
					   '<drawcolor>'+colorutil.format(this.drawColor)+'</drawcolor>'+
					   '<basecolor>'+colorutil.format(this.bColor)+'</basecolor>';				
		if(typeof this.drawStroke != 'undefined'){
			areaXML = areaXML +'<base>'+this.drawStroke.getStrokeXML()+'</base>';
		}
		if(typeof this.bStroke != 'undefined'){
			areaXML = areaXML +'<curve>'+this.bStroke.getStrokeXML()+'</curve>';
		}
		areaXML = areaXML +'</draw>';
		
		areaXML = areaXML + '<glyphs>';
		for(var i=0; i<this.glyphs.length; i++) {			
			var g = this.glyphs[i];
			areaXML = areaXML + g.getGlyphXML();		 
		}
		areaXML = areaXML + '</glyphs></area>';
		return areaXML;
	};
};

com.jensoft.jenscript.plugin.area.AreaPlugin = function() {
	
	this.name;
	this.id;
	this.window2D;
	this.areas = [];
	
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
	
	

	this.addArea = function(area){
		this.areas[this.areas.length] = area;
	};
	
	this.getPluginXML = function(){
		var pluginXML = '<plugin>'+
		'<id>'+this.id+'</id>'+
		'<name>'+this.name+'</name>'+
		'<class>'+'com.jensoft.sw2d.core.plugin.area.AreaPlugin'+'</class>'+
		'<params><areas>';
		
		for(var i=0; i<this.areas.length; i++) {
			var c = this.areas[i];
			pluginXML = pluginXML + c.getAreaXML();
		}
		
		pluginXML = pluginXML + '</areas></params></plugin>';
			
		return pluginXML;
	};
	
};