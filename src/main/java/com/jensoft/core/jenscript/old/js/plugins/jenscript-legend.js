registerNS('com.jensoft.jenscript.plugin.legend');

com.jensoft.jenscript.plugin.legend.LegendPlugin = function(text) {
	
	this.name;
	this.id;
	this.window2D;
	this.text=text;
	this.startColor;
	this.endColor;
	
	this.position='south'; //south,north,east,west
	this.alignment='right';//left,middle,right
	this.depth= 0.5; // [0,1]
	
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
	
	this.setText = function(text) {
		this.text = text;
	};

	this.getText = function() {
		return this.text;
		valueOf = function(){
			return this.text;
		};
	};
	
	this.setColor = function(color) {
		this.startColor = color;
		this.endColor = color;
	};
	
	this.setStartColor = function(startColor) {
		this.startColor = startColor;
	};

	this.getStartColor = function() {
		return this.startColor;
		valueOf = function(){
			return this.startColor;
		};
	};
	
	this.setEndColor = function(endColor) {
		this.endColor = endColor;
	};

	this.getEndColor = function() {
		return this.endColor;
		valueOf = function(){
			return this.endColor;
		};
	};
	
	
	
	this.setPosition = function(position) {
		this.position = position;
	};

	this.getPosition = function() {
		return this.position;
		valueOf = function(){
			return this.position;
		};
	};
	
	this.setAlignment = function(alignment) {
		this.alignment = alignment;
	};

	this.getAlignment = function() {
		return this.alignment;
		valueOf = function(){
			return this.alignment;
		};
	};
	
	this.setDepth = function(depth) {
		this.depth = depth;
	};

	this.getDepth = function() {
		return this.depth;
		valueOf = function(){
			return this.depth;
		};
	};
	
	this.getPluginXML = function(){
		
		var pluginXML = '<plugin>'+
						'<id>'+this.id+'</id>'+
						'<name>'+this.name+'</name>'+
						'<class>'+'com.jensoft.sw2d.core.plugin.legend.LegendPlugin'+'</class>'+
						'<params>'+
							'<legends>';
						
			
			pluginXML = pluginXML + this.getLegendXML();
			
				

		pluginXML = pluginXML + '</legends></params></plugin>';
			
		return pluginXML;
							
	};
	
	this.getLegendXML = function() {
		var colorutil = new com.jensoft.jenscript.ColorUtil();
		
		var legendXML = '<legend>'+
							'<text>'+this.text+'</text>'+
							'<startcolor>'+colorutil.format(this.startColor)+'</startcolor>'+
							'<endcolor>'+colorutil.format(this.endColor)+'</endcolor>'+
							'<position>'+this.position+'</position>'+
							'<alignment>'+this.alignment+'</alignment>'+
							'<depth>'+this.depth+'</depth>';							
		legendXML = legendXML + '</legend>';
	
		return legendXML;
		
	};
};