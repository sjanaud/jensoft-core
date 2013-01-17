registerNS("com.jensoft.jenscript");

com.jensoft.jenscript.ViewBackground = function(shader,outlineColor) {
	this.outlineRound = 20;
	this.paddingX = 5;
	this.paddingY = 5;
	this.shader=shader;
	this.outlineColor=outlineColor;
	this.outlineStroke;
	
	this.setOutlineRound = function(outlineRound) {
		this.outlineRound = outlineRound;
	};

	this.getOutlineRound = function() {
		return this.outlineRound;
		valueOf = function(){
			return this.outlineRound;
		};
	};
	
	this.setOutlineColor = function(outlineColor) {
		this.outlineColor = outlineColor;
	};

	this.getOutlineColor = function() {
		return this.outlineColor;
		valueOf = function(){
			return this.outlineColor;
		};
	};
	
	this.setOutlineStroke = function(outlineStroke) {
		this.outlineStroke = outlineStroke;
	};

	this.getOutlineStroke = function() {
		return this.outlineStroke;
		valueOf = function(){
			return this.outlineStroke;
		};
	};
	
	this.setPaddingX = function(paddingX) {
		this.paddingX = paddingX;
	};

	this.getPaddingX = function() {
		return this.paddingX;
		valueOf = function(){
			return this.paddingX;
		};
	};
	
	this.setPaddingY = function(paddingY) {
		this.paddingY = paddingY;
	};

	this.getPaddingY = function() {
		return this.paddingY;
		valueOf = function(){
			return this.paddingY;
		};
	};
	
	this.setShader = function(shader) {
		this.shader = shader;
	};

	this.getShader= function() {
		return this.shader;
		valueOf = function(){
			return this.shader;
		};
	};
	
	
	
	this.getViewBackgroundXML = function(){		
		var colorutil = new com.jensoft.jenscript.ColorUtil();
		var backgroundXML ='<background>'+
								'<outline-round>'+this.outlineRound+'</outline-round>'+
								'<padding-x>'+this.paddingX+'</padding-x>'+
								'<padding-y>'+this.paddingY+'</padding-y>'+
								'<outline-color>'+colorutil.format(this.outlineColor)+'</outline-color>';							
		if(typeof this.outlineStroke != 'undefined'){
			backgroundXML = backgroundXML + this.outlineStroke.getStrokeXML();
		}
		if(typeof this.shader != 'undefined'){
			backgroundXML = backgroundXML + this.shader.getShaderXML();
		}
		
		backgroundXML = backgroundXML + '</background>';
		return backgroundXML;
	};
	
};

com.jensoft.jenscript.View2D = function(width,height,viewkey) {
	
	this.width=width;
	this.height=height;
	this.placeHolderWest=40;
	this.placeHolderEast=40;
	this.placeHolderNorth=40;
	this.placeHolderSouth=40;
	this.background;
	this.window2Ds =[];	
	this.apikey;
	this.backgroundPainter;
	
	this.setBackgroundPainter = function(backgroundPainter) {
		this.backgroundPainter = backgroundPainter;
	};

	this.getBackgroundPainter = function() {
		return this.backgroundPainter;
		valueOf = function(){
			return this.backgroundPainter;
		};
	};

	this.setApiKey = function(apikey) {
		this.apikey = apikey;
	};

	this.getApiKey = function() {
		return this.apikey;
		valueOf = function(){
			return this.apikey;
		};
	};

	this.setWidth = function(width) {
		this.width = width;
	};

	this.getWidth = function() {
		return this.width;
		valueOf = function(){
			return this.width;
		};
	};
	
	
	this.setHeight = function(height) {
		this.height = height;
	};

	this.getHeight = function() {
		return this.height;
		valueOf = function(){
			return this.height;
		};
	};

	this.setPlaceHolderNorth = function(placeHolderNorth) {
		this.placeHolderNorth = placeHolderNorth;
	};

	this.getPlaceHolderNorth = function() {
		return this.placeHolderNorth;
		valueOf = function(){
			return this.placeHolderNorth;
		};
	};
	
	this.setPlaceHolderSouth = function(placeHolderSouth) {
		this.placeHolderSouth = placeHolderSouth;
	};

	this.getPlaceHolderSouth = function() {
		return this.placeHolderSouth;
		valueOf = function(){
			return this.placeHolderSouth;
		};
	};
	
	this.setPlaceHolderWest = function(placeHolderWest) {
		this.placeHolderWest = placeHolderWest;
	};

	this.getPlaceHolderWest = function() {
		return this.placeHolderWest;
		valueOf = function(){
			return this.placeHolderWest;
		};
	};
	
	this.setPlaceHolderEast = function(placeHolderEast) {
		this.placeHolderEast = placeHolderEast;
	};

	this.getPlaceHolderEast = function() {
		return this.placeHolderEast;
		valueOf = function(){
			return this.placeHolderEast;
		};
	};
	
	this.setBackground = function(background) {
		this.background = background;
	};

	this.getBackground = function() {
		return this.background;
		valueOf = function(){
			return this.background;
		};
	};
	
	this.registerWindow2D = function(window2D) {
		this.window2Ds[this.window2Ds.length] = window2D;
	};
	
	
	
	this.getViewXML = function(){		
		var colorutil = new com.jensoft.jenscript.ColorUtil();
		var xmlView ='<view2d>'+
		'<view-key>'+viewkey+'</view-key>'+
		'<api-key>'+jenscriptAPIKey+'</api-key>'+		
		'<width>'+this.width+'</width>'+
		'<height>'+this.height+'</height>'+
		'<west>'+this.placeHolderWest+'</west>'+
		'<south>'+this.placeHolderSouth+'</south>'+
		'<east>'+this.placeHolderEast+'</east>'+
		'<north>'+this.placeHolderNorth+'</north>';	
		if(typeof this.backgroundPainter != 'undefined'){
			xmlView = xmlView + this.backgroundPainter.getViewBackgroundXML();
		}
		
		for(var i=0; i<this.window2Ds.length; i++) {			
			var w2d = this.window2Ds[i];
			w2d.setID('w'+i);
			if(w2d instanceof com.jensoft.jenscript.Window2D){					
				xmlView = xmlView + w2d.getWindowXML();
			}			 
		}	
		xmlView = xmlView+'</view2d>';		
		return xmlView;
	};
	
	this.getBody = function(){
		var body = 'apikey='+jenscriptAPIKey+'&view='+this.getViewXML();
		return body;
	};
	
	this.requestView = function(){	
        new Ajax.Request(serverURL, {
            method: 'post',
            contentType :'application/x-www-form-urlencoded',
            postBody : this.getBody(),           
            onComplete: function(transport){            
               var serverResponse = transport.responseXML;              
               var validateList =  serverResponse.getElementsByTagName("jenscript");            
               var viewkey = validateList[0].childNodes[0].nodeValue;              
               var viewURL = serverURL+'?viewkey='+viewkey+'&apikey='+jenscriptAPIKey;
               document.getElementById(viewID).setAttribute('src',viewURL);
            },
            onFailure: function (xhr){
             // HTTP response != 2xx
              //alert('Failure : '+ xhr.status + ' :' + xhr.statusText );
            },
            onException: function (xhr, exception){           
              //alert( 'Exception : ' + exception);
            }
        });
		
	};
	
};