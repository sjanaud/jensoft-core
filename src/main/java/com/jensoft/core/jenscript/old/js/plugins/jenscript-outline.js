registerNS('com.jensoft.jenscript.plugin.outline');

com.jensoft.jenscript.plugin.outline.OutlinePlugin = function() {
	


	this.outlineColor;
	this.name;
	this.window2D;
	
	this.id;	
	
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
	
	this.setOutlineColor = function(outlineColor) {
		this.outlineColor = outlineColor;
	};

	this.getOutlineColor  = function() {
		return this.outlineColor;
	};
	
	this.getPluginXML = function(){
		var colorutil = new com.jensoft.jenscript.ColorUtil();
		var pluginXML = '<plugin xsi:type="OutlinePlugin">'+
						'<id>'+this.id+'</id>'+
						'<name>'+this.name+'</name>'+
						'<class>com.jensoft.sw2d.core.plugin.outline.OutlinePlugin</class>'+
						'<params>'+
							'<outline-color>'+colorutil.format(this.outlineColor)+'</outline-color>'+
						'</params>'+
						'</plugin>';
			return pluginXML;
	};
	
};