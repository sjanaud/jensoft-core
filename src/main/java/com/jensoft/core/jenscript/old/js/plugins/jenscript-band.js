registerNS('com.jensoft.jenscript.plugin.band');


com.jensoft.jenscript.plugin.band.DynamicBandManager = function(orientation,ref,interval) {
	this.orientation = orientation;
	this.ref=ref;
	this.interval=interval;
	this.palette;
	
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
	
	
	this.setPalette = function(palette) {
		this.palette = palette;
	};

	this.getPalette = function() {
		return this.palette;
		valueOf = function(){
			return this.palette;
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
		var managerXML='';
		if(typeof this.palette != 'undefined'){			
			managerXML = '<manager>'+
								'<type>'+'dynamic'+'</type>'+
								'<orientation>'+this.orientation+'</orientation>'+
								'<ref>'+this.ref+'</ref>'+
								'<interval>'+this.interval+'</interval>'+
								this.palette.getPaletteXML();
						
			managerXML = managerXML + '</manager>';
		}
		return managerXML;
		
	};
	
};

com.jensoft.jenscript.plugin.band.FlowBandManager = function(orientation,startBand,endBand,interval) {
	
	this.orientation = orientation;
	this.startBand=startBand;
	this.endBand=endBand;
	this.interval=interval;
	this.palette;
	
	this.setOrientation = function(orientation) {
		this.orientation = orientation;
	};

	this.getOrientation = function() {
		return this.orientation;
		valueOf = function(){
			return this.orientation;
		};
	};
	
	this.setPalette = function(palette) {
		this.palette = palette;
	};

	this.getPalette = function() {
		return this.palette;
		valueOf = function(){
			return this.palette;
		};
	};
	
	
	
	this.setStartBand = function(startBand) {
		this.startBand = startBand;
	};

	this.getStartBand = function() {
		return this.startBand;
		valueOf = function(){
			return this.startBand;
		};
	};
	
	this.setEndBand = function(endBand) {
		this.endBand = endBand;
	};

	this.getEndBand = function() {
		return this.endBand;
		valueOf = function(){
			return this.endBand;
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
		var managerXML='';
		if(typeof this.palette != 'undefined'){			
			var managerXML = '<manager>'+
								'<type>'+'flow'+'</type>'+
								'<orientation>'+this.orientation+'</orientation>'+
								'<startband>'+this.startBand+'</startband>'+
								'<endband>'+this.endBand+'</endband>'+
								'<interval>'+this.interval+'</interval>'+
								this.palette.getPaletteXML();
								
			managerXML = managerXML + '</manager>';		
		}
		return managerXML;		
	};
	
};


com.jensoft.jenscript.plugin.band.FreeBandManager = function(orientation) {
	
	this.orientation = orientation;
	this.bands = [];	
	
	this.setOrientation = function(orientation) {
		this.orientation = orientation;
	};

	this.getOrientation = function() {
		return this.orientation;
		valueOf = function(){
			return this.orientation;
		};
	};
	
		
	this.addBand = function(band){
		this.bands[this.bands.length] = band;
	};
	
	
	this.getManagerXML = function() {
	
		
		var managerXML = '<manager>'+
							'<type>'+'free'+'</type>'+
							'<orientation>'+this.orientation+'</orientation>';

		managerXML = managerXML + '<bands>';
		
		for(var i=0; i<this.bands.length; i++) {
			var b = this.bands[i];
			managerXML = managerXML + b.getBandXML();
		}
		
		managerXML = managerXML	+'</bands></manager>';
		
		return managerXML;
		
	};
	
};


com.jensoft.jenscript.plugin.band.Band = function(startBand,endBand,color) {	

	this.startBand=startBand;
	this.endBand=endBand;
	this.color=color;
	
	this.setStartBand = function(startBand) {
		this.startBand = startBand;
	};

	this.getStartBand = function() {
		return this.startBand;
		valueOf = function(){
			return this.startBand;
		};
	};
	
	this.setEndBand = function(endBand) {
		this.endBand = endBand;
	};

	this.getEndBand = function() {
		return this.endBand;
		valueOf = function(){
			return this.endBand;
		};
	};
	
	this.setColor = function(color) {
		this.color = color;
	};

	this.getColor = function() {
		return this.color;
		valueOf = function(){
			return this.color;
		};
	};

	this.getBandXML = function() {
		var colorutil = new com.jensoft.jenscript.ColorUtil();
		var bandXML = '<band>'+
							'<start>'+this.startBand+'</start>'+
							'<end>'+this.endBand+'</end>'+
							'<color>'+colorutil.format(this.color)+'</color>';
		bandXML = bandXML + '</band>';
		
		return bandXML;
		
	};
	
};


com.jensoft.jenscript.plugin.band.BandPalette = function() {
	this.colors = [];
	
	this.addColor = function(color){
		this.colors[this.colors.length] = color;
	};
	
	this.getPaletteXML = function() {
		var colorutil = new com.jensoft.jenscript.ColorUtil();		
		var paletteXML = '<palette>';
		
		for(var i=0; i<this.colors.length; i++) {
			var c = this.colors[i];
			paletteXML = paletteXML + 
							'<c>'+colorutil.format(c)+'</c>';
		}				
					
		paletteXML = paletteXML + '</palette>';
		
		return paletteXML;
		
	};
};



com.jensoft.jenscript.plugin.band.BandPlugin = function() {

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
						'<class>'+'com.jensoft.sw2d.core.plugin.band.BandPlugin'+'</class>'+
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