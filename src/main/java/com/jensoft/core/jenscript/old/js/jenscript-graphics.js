registerNS('com.jensoft.jenscript.graphics');

com.jensoft.jenscript.graphics.Stroke = function(width) {
	this.width= width;//float
	this.join;//string : mitter,round,bevel
	this.cap;//string : butt,round,square
	this.miterlimit;//float > 1
    this.dash;//float array
    this.dashPhase=0;//float
    

    this.setWidth = function(width) {
		this.width = width;
	};

	this.getWidth = function() {
		return this.width;
		valueOf = function(){
			return this.width;
		};
	};
	
	 
    this.setJoin = function(join) {
		this.join = join;
	};

	this.getJoin = function() {
		return this.join;
		valueOf = function(){
			return this.join;
		};
	};
	
	this.setCap = function(cap) {
		this.cap = cap;
	};

	this.getCap = function() {
		return this.cap;
		valueOf = function(){
			return this.cap;
		};
	};
	
	this.setMiterLimit = function(miterlimit) {
		this.miterlimit = miterlimit;
	};

	this.getMiterLimit = function() {
		return this.miterlimit;
		valueOf = function(){
			return this.miterlimit;
		};
	};
	
	this.setDash = function(dash) {
		this.dash = dash;
	};

	this.getDash = function() {
		return this.dash;
		valueOf = function(){
			return this.dash;
		};
	};
	
	this.setDashPhase = function(dashPhase) {
		this.dashPhase = dashPhase;
	};

	this.getDashPhase = function() {
		return this.dashPhase;
		valueOf = function(){
			return this.dashPhase;
		};
	};

	this.getStrokeXML = function(){
	
		var strokeXML='';
		var d='undefined';
		if(typeof this.dash != 'undefined'){			
			for(var n=0; n<dash.length; n++) {
				if(n == 0){
					d = dash[n];
				}else{
					d = d+','+dash[n];
				}
			}			
		}
		strokeXML = '<stroke>'+
							'<width>'+this.width+'</width>'+
							'<join>'+this.join+'</join>'+
							'<cap>'+this.cap+'</cap>'+
							'<miterlimit>'+this.miterlimit+'</miterlimit>'+
							'<dash>'+d+'</dash>'+
							'<dashphase>'+this.dash_phase+'</dashphase>'+
					'</stroke>';
		return strokeXML;
	};
};


com.jensoft.jenscript.graphics.Shader = function(fractions,colors) {
	this.fractions = fractions;
	this.colors = colors;
	
	this.setFractions = function(fractions) {
		this.fractions = fractions;
	};

	this.getFractions = function() {
		return this.fractions;
		valueOf = function(){
			return this.fractions;
		};
	};
	
	this.setColors = function(colors) {
		this.colors = colors;
	};

	this.getColors = function() {
		return this.colors;
		valueOf = function(){
			return this.colors;
		};
	};
	

	
	this.getShaderXML = function(){
		var colorutil = new com.jensoft.jenscript.ColorUtil();
		var shaderXML='';
		
		if(typeof fractions != 'undefined' && typeof colors != 'undefined' && fractions.length == colors.length){
			var fracs='';
			var cols='';
			for(var n=0; n<fractions.length; n++) {
				if(n == 0){
					fracs = fractions[n];
					cols = colorutil.format(colors[n]);
				}else{
					fracs = fracs+','+fractions[n];
					cols = cols+','+colorutil.format(colors[n]);
				}
			}
			
			shaderXML = '<shader>'+
							'<fractions>'+fracs+'</fractions>'+
							'<colors>'+cols+'</colors>'+
						'</shader>';
			
		}
		return shaderXML;
	};
	
	
}