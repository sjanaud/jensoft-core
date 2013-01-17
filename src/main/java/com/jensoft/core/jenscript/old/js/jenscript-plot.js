registerNS("com.jensoft.jenscript.plot");


com.jensoft.jenscript.plot.glyph = function(xValue,text) {
	this.xValue=xValue;
	this.textStartColor;
	this.textEndColor;
	this.textColor;
	this.text=text;
	this.stylePosition = 'default';//default,tangent,radial
	this.divergence = 10;
	this.marker;
	
	this.setTextStartColor = function(textStartColor) {
		this.textStartColor = textStartColor;
	};

	this.getTextStartColor = function() {
		return this.textStartColor;
		valueOf = function(){
			return this.textStartColor;
		};
	};
	
	this.setTextEndColor = function(textEndColor) {
		this.textEndColor = textEndColor;
	};

	this.getTextEndColor = function() {
		return this.textEndColor;
		valueOf = function(){
			return this.textEndColor;
		};
	};

	this.setTextColor = function(textColor) {
		this.textStartColor = textColor;
		this.textEndColor = textColor;
	};
	
	this.setStylePosition = function(stylePosition) {
		this.stylePosition = stylePosition;
	};

	this.getStylePosition = function() {
		return this.stylePosition;
		valueOf = function(){
			return this.stylePosition;
		};
	};
	
	this.setDivergence = function(divergence) {
		this.divergence = divergence;
	};

	this.getDivergence = function() {
		return this.divergence;
		valueOf = function(){
			return this.divergence;
		};
	};
	
	this.setXValue = function(xValue) {
		this.xValue = xValue;
	};

	this.getXValue = function() {
		return this.xValue;
		valueOf = function(){
			return this.xValue;
		};
	};
	
	this.setMarker = function(marker) {
		this.marker = marker;
	};

	this.getMarker = function() {
		return this.marker;
		valueOf = function(){
			return this.marker;
		};
	};
	
	this.getGlyphXML = function(){
		var colorutil = new com.jensoft.jenscript.ColorUtil();
		var glyphXML = '<glyph>'+
							'<xval>'+this.xValue+'</xval>'+
							'<text>'+this.text+'</text>'+
							'<tstartcolor>'+colorutil.format(this.textStartColor)+'</tstartcolor>'+
							'<tendcolor>'+colorutil.format(this.textEndColor)+'</tendcolor>'+
							'<style>'+this.stylePosition+'</style>'+
							'<divergence>'+this.divergence+'</divergence>';
					
		if(typeof this.marker != 'undefined'){
			glyphXML = glyphXML + this.marker.getMarkerXML();
		}
		glyphXML = glyphXML + '</glyph>';
		return glyphXML;	
	};
};

com.jensoft.jenscript.plot.TicTacMarker  = function(themeColor) {
	
	this.themeColor = themeColor;
	this.divergenceRight = 5;
	this.divergenceLeft  = 5;
	this.size = 4;
	
	this.setSize = function(size) {
		this.size = size;
	};
	
	this.setDivergenceLeft = function(divergenceLeft) {
		this.divergenceLeft = divergenceLeft;
	};
	
	this.setDivergenceRight = function(divergenceRight) {
		this.divergenceRight = divergenceRight;
	};

	
	
	this.getMarkerXML = function(){
		var markerXML = '<marker>'+
							'<type>'+'tictac'+'</type>'+
							'<themecolor>'+colorutil.format(this.themeColor)+'</themecolor>'+
							'<divright>'+this.divergenceRight+'</divright>'+
							'<divleft>'+this.divergenceLeft+'</divleft>'+
							'<size>'+this.size+'</size>'+
						'</marker>';
		return markerXML;
	};
};

com.jensoft.jenscript.plot.RoundMarker  = function(fillColor,drawColor,radius) {
	this.fillColor 	= fillColor;
	this.drawColor 	= drawColor;
	this.radius 	= radius;
	
	this.getMarkerXML = function(){
		var markerXML = '<marker>'+
							'<type>'+'round'+'</type>'+
							'<fillcolor>'+colorutil.format(this.fillColor)+'</fillcolor>'+
							'<drawcolor>'+colorutil.format(this.drawColor)+'</drawcolor>'+
							'<radius>'+this.size+'</radius>'+
						'</marker>';
		return markerXML;
	};
};

com.jensoft.jenscript.plot.Serie2D = function(xValues,yValues) {
	this.xValues = xValues;
	this.yValues = yValues;
	
	this.getSerieXML = function(){
		var serie2d = '<serie2d><type>linear</type>';
		serie2d = serie2d + '<xvalues>';
		for(var i=0; i<this.xValues.length; i++) {
			var v = this.xValues[i];
			serie2d = serie2d + v+',';
		}
		serie2d = serie2d + '</xvalues>';
		serie2d = serie2d + '<yvalues>';
		for(var i=0; i<this.yValues.length; i++) {
			var v = this.yValues[i];
			serie2d = serie2d + v+',';
		}
		serie2d = serie2d + '</yvalues></serie2d>';
		return serie2d;
	};
};

com.jensoft.jenscript.plot.InterpolateSerie2D = function(xValues,yValues,delta) {
	this.xValues = xValues;
	this.yValues = yValues;
	this.delta=delta;
	
	this.getSerieXML = function(){
		var serie2d = '<serie2d><type>spline</type><delta>'+this.delta+'</delta>';
		serie2d = serie2d + '<xvalues>';
		for(var i=0; i<this.xValues.length; i++) {
			var v = this.xValues[i];
			serie2d = serie2d + v+',';
		}
		serie2d = serie2d + '</xvalues>';
		serie2d = serie2d + '<yvalues>';
		for(var i=0; i<this.yValues.length; i++) {
			var v = this.yValues[i];
			serie2d = serie2d + v+',';
		}
		serie2d = serie2d + '</yvalues></serie2d>';
		return serie2d;
	};
};