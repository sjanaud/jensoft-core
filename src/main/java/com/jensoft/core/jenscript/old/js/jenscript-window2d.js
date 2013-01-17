registerNS("com.jensoft.jenscript");

com.jensoft.jenscript.Window2D = function(name,minX,maxX,minY,maxY) {
	
	this.id;
	this.name = name;
	this.minX = minX;
	this.maxX = maxX;
	this.minY = minY;
	this.maxY = maxY;
	this.themeColor;
	this.plugins =[];	
		
	
	this.setID = function(id) {
		this.id = id;
	};
	
	this.getID = function() {
		return this.id;
		valueOf = function(){
			return this.id;
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
	
	this.registerPlugin = function(plugin) {
		plugin.setWindow2D(this);
		this.plugins[this.plugins.length] = plugin;
	};
	
	this.getWindowXML  = function() {
		var colorutil = new com.jensoft.jenscript.ColorUtil();
		var windowXML = '<window2d>'+
						'<id>'+this.id+'</id>'+
						'<name>'+this.name+'</name>'+
						'<theme-color>'+colorutil.format(this.themeColor)+'</theme-color>'+
						'<min-x>'+this.minX+'</min-x>'+
						'<max-x>'+this.maxX+'</max-x>'+
						'<min-y>'+this.minY+'</min-y>'+
						'<max-y>'+this.maxY+'</max-y>';
		
			for(var i=0; i<this.plugins.length; i++) {
				var plugin = this.plugins[i];
				plugin.setName(this.id+'p'+i);
				plugin.setID(this.id+'p'+i);				
				windowXML = windowXML+plugin.getPluginXML();				
			}
		windowXML = windowXML + '</window2d>';
			
		return windowXML;

	};	
	
};