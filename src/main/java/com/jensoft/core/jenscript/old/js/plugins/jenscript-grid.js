registerNS('com.jensoft.jenscript.plugin.grid');

com.jensoft.jenscript.plugin.grid.StaticGridManager = function(orientation,gridColor,count) {
	this.orientation = orientation;
	this.gridColor = gridColor;
	this.count=count;	
	this.stroke;
	
	this.setOrientation = function(orientation) {
		this.orientation = orientation;
	};

	this.getOrientation = function() {
		return this.orientation;
		valueOf = function(){
			return this.orientation;
		};
	};
	
	this.setCount = function(count) {
		this.count = count;
	};

	this.getCount = function() {
		return this.count;
		valueOf = function(){
			return this.count;
		};
	};
	
	this.setStroke = function(stroke) {
		this.stroke = stroke;
	};

	this.getStroke = function() {
		return this.stroke;
		valueOf = function(){
			return this.stroke;
		};
	};
	
	this.setGridColor = function(gridColor) {
		this.gridColor = gridColor;
	};

	this.getGridColor = function() {
		return this.gridColor;
		valueOf = function(){
			return this.gridColor;
		};
	};
	
	
	this.getManagerXML = function() {
		var colorutil = new com.jensoft.jenscript.ColorUtil();
		
		var managerXML = '<manager>'+
							'<type>'+'static'+'</type>'+
							'<orientation>'+this.orientation+'</orientation>'+
							'<themecolor>'+colorutil.format(this.gridColor)+'</themecolor>'+
							'<count>'+this.count+'</count>';
		if(typeof this.stroke != 'undefined'){
			managerXML = managerXML + this.stroke.getStrokeXML();
		}
							
		managerXML = managerXML + '</manager>';
	
		return managerXML;
		
	};
	
};

com.jensoft.jenscript.plugin.grid.DynamicGridManager = function(orientation,gridColor,ref,interval) {
	this.orientation = orientation;
	this.gridColor = gridColor;
	this.ref=ref;
	this.interval=interval;
	this.stroke;
	
	this.setOrientation = function(orientation) {
		this.orientation = orientation;
	};

	this.getOrientation = function() {
		return this.orientation;
		valueOf = function(){
			return this.orientation;
		};
	};
	
	this.setRef = function(ref) {
		this.ref = ref;
	};

	this.getRef = function() {
		return this.ref;
		valueOf = function(){
			return this.ref;
		};
	};
	
	this.setStroke = function(stroke) {
		this.stroke = stroke;
	};

	this.getStroke = function() {
		return this.stroke;
		valueOf = function(){
			return this.stroke;
		};
	};
	
	this.setGridColor = function(gridColor) {
		this.gridColor = gridColor;
	};

	this.getGridColor = function() {
		return this.gridColor;
		valueOf = function(){
			return this.gridColor;
		};
	};
	
	this.setInterval = function(interval) {
		this.interval = interval;
	};

	this.getInterval = function() {
		return this.interval;
		valueOf = function(){
			return this.interval;
		};
	};

	this.getManagerXML = function() {
		var colorutil = new com.jensoft.jenscript.ColorUtil();
		
		var managerXML = '<manager>'+
							'<type>'+'dynamic'+'</type>'+
							'<orientation>'+this.orientation+'</orientation>'+
							'<themecolor>'+colorutil.format(this.gridColor)+'</themecolor>'+
							'<ref>'+this.ref+'</ref>'+
							'<interval>'+this.interval+'</interval>';
							
		if(typeof this.stroke != 'undefined'){
			managerXML = managerXML + this.stroke.getStrokeXML();
		}
							
		managerXML = managerXML + '</manager>';
		
		return managerXML;
		
	};
	
};

com.jensoft.jenscript.plugin.grid.FlowGridManager = function(orientation,gridColor,startGrid,endGrid,interval) {
	
	this.orientation = orientation;
	this.gridColor = gridColor;
	this.startGrid=startGrid;
	this.endGrid=endGrid;
	this.interval=interval;
	this.stroke;
	
	this.setOrientation = function(orientation) {
		this.orientation = orientation;
	};

	this.getOrientation = function() {
		return this.orientation;
		valueOf = function(){
			return this.orientation;
		};
	};
	
	this.setGridColor = function(gridColor) {
		this.gridColor = gridColor;
	};

	this.getGridColor = function() {
		return this.gridColor;
		valueOf = function(){
			return this.gridColor;
		};
	};
	
	this.setStroke = function(stroke) {
		this.stroke = stroke;
	};

	this.getStroke = function() {
		return this.stroke;
		valueOf = function(){
			return this.stroke;
		};
	};
	
	this.setStartGrid = function(startGrid) {
		this.startGrid = startGrid;
	};

	this.getStartGrid = function() {
		return this.startGrid;
		valueOf = function(){
			return this.startGrid;
		};
	};
	
	this.setEndGrid = function(endGrid) {
		this.endGrid = endGrid;
	};

	this.getEndGrid = function() {
		return this.endGrid;
		valueOf = function(){
			return this.endGrid;
		};
	};
	
	this.setInterval = function(interval) {
		this.interval = interval;
	};

	this.getInterval = function() {
		return this.interval;
		valueOf = function(){
			return this.interval;
		};
	};
	
	this.getManagerXML = function() {
		var colorutil = new com.jensoft.jenscript.ColorUtil();
		
		var managerXML = '<manager>'+
							'<type>'+'flow'+'</type>'+
							'<orientation>'+this.orientation+'</orientation>'+
							'<themecolor>'+colorutil.format(this.gridColor)+'</themecolor>'+
							'<startgrid>'+this.startGrid+'</startgrid>'+
							'<endgrid>'+this.endGrid+'</endgrid>'+
							'<interval>'+this.interval+'</interval>';
		
		if(typeof this.stroke != 'undefined'){
			managerXML = managerXML + this.stroke.getStrokeXML();
		}
							
		managerXML = managerXML + '</manager>';
		
		return managerXML;
		
	};
	
};


com.jensoft.jenscript.plugin.grid.FreeGridManager = function(orientation,gridColor) {
	
	this.orientation = orientation;
	this.gridColor = gridColor;
	this.stroke;
	this.grids = [];	
	
	this.setOrientation = function(orientation) {
		this.orientation = orientation;
	};

	this.getOrientation = function() {
		return this.orientation;
		valueOf = function(){
			return this.orientation;
		};
	};
	
	this.setGridColor = function(gridColor) {
		this.gridColor = gridColor;
	};

	this.getGridColor = function() {
		return this.gridColor;
		valueOf = function(){
			return this.gridColor;
		};
	};
	
	this.setStroke = function(stroke) {
		this.stroke = stroke;
	};

	this.getStroke = function() {
		return this.stroke;
		valueOf = function(){
			return this.stroke;
		};
	};
	
	this.addGrid = function(grid){
		this.grids[this.grids.length] = grid;
	};
	
	
	this.getManagerXML = function() {
		var colorutil = new com.jensoft.jenscript.ColorUtil();
		
		var managerXML = '<manager>'+
							'<type>'+'free'+'</type>'+
							'<orientation>'+this.orientation+'</orientation>'+
							'<themecolor>'+colorutil.format(this.gridColor)+'</themecolor>';
		
		if(typeof this.stroke != 'undefined'){
			managerXML = managerXML + this.stroke.getStrokeXML();
		}
		
		managerXML = managerXML + '<grids>';
		
		for(var i=0; i<this.grids.length; i++) {
			var g = this.grids[i];
			managerXML = managerXML + g.getGridXML();
		}
		
		managerXML = managerXML	+'</grids></manager>';
		
		return managerXML;
		
	};
	
};


com.jensoft.jenscript.plugin.grid.Grid = function(value) {	
	
	this.value=value;
	this.text;
	this.fraction;
	this.gridColor;
	this.stroke;
	
	
	this.setGridColor = function(gridColor) {
		this.gridColor = gridColor;
	};

	this.getGridColor = function() {
		return this.gridColor;
		valueOf = function(){
			return this.gridColor;
		};
	};
	
	this.setStroke = function(stroke) {
		this.stroke = stroke;
	};

	this.getStroke = function() {
		return this.stroke;
		valueOf = function(){
			return this.stroke;
		};
	};
	
	
	
	this.getGridXML = function() {
		var colorutil = new com.jensoft.jenscript.ColorUtil();
		
		
		var gridXML = '<grid>'+
							'<value>'+this.value+'</value>'+
							'<text>'+this.text+'</text>'+
							'<fraction>'+this.fraction+'</fraction>'+
							'<themecolor>'+colorutil.format(this.gridColor)+'</themecolor>';
		
		if(typeof this.stroke != 'undefined'){
			gridXML = gridXML + this.stroke.getStrokeXML();
		}
		gridXML = gridXML +  '</grid>';
		
		return gridXML;
		
	};
	
};

com.jensoft.jenscript.plugin.grid.GridPlugin = function() {

	this.name;
	this.id;
	this.window2D;
	this.managers = [];	
	
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
	
	this.addManager = function(manager){
		this.managers[this.managers.length] = manager;	
	};
	
	this.getPluginXML = function(){
		
		var pluginXML = '<plugin>'+
						'<id>'+this.id+'</id>'+
						'<name>'+this.name+'</name>'+
						'<class>'+'com.jensoft.sw2d.core.plugin.grid.GeneralGridPlugin'+'</class>'+
						'<params>'+
							'<managers>';
						
			for(var i=0; i<this.managers.length; i++) {
				var m = this.managers[i];
				pluginXML = pluginXML + m.getManagerXML();
			}

		pluginXML = pluginXML + '</managers></params></plugin>';
			
		return pluginXML;
							
	};
	
	
};