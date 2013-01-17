registerNS('com.jensoft.jenscript.plugin.donut3d');
registerNS('com.jensoft.jenscript.plugin.donut3d.effect');
registerNS('com.jensoft.jenscript.plugin.donut3d.label');



com.jensoft.jenscript.plugin.donut3d.label.RadialLabel = function(text) {
	
	this.name = "radial";
	this.text = text;
	this.textColor;
	this.style;
	this.strokeColor;
	this.shader;
	this.offsetRadius = 30;
	this.fillColor;
	this.outlineRound = 5;
	this.stroke;
	
	
	this.getName = function() {
		return this.name;
		valueOf = function(){
			return this.name;
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
	
	this.setStroke = function(stroke) {
		this.stroke = stroke;
	};
	
	this.getStroke = function() {
		return this.stroke;
		valueOf = function(){
			return this.stroke;
		};
	};
	
	this.setTextColor = function(textColor) {
		this.textColor = textColor;
	};

	this.getTextColor = function() {
		return this.textColor;
		valueOf = function(){
			return this.textColor;
		};
	};
	
	this.setOffsetRadius = function(offsetRadius) {
		this.offsetRadius = offsetRadius;
	};

	this.getOffsetRadius = function() {
		return this.offsetRadius;
		valueOf = function(){
			return this.offsetRadius;
		};
	};
	
	
	this.setOutlineRound = function(outlineRound) {
		this.outlineRound = outlineRound;
	};

	this.getOutlineRound = function() {
		return this.outlineRound;
		valueOf = function(){
			return this.outlineRound;
		};
	};
	
	
	
	this.setStyle = function(style) {
		this.style = style;
	};

	this.getStyle = function() {
		return this.style;
		valueOf = function(){
			return this.style;
		};
	};
	
	this.setStrokeColor = function(strokeColor) {
		this.strokeColor = strokeColor;
	};

	this.getStrokeColor = function() {
		return this.strokeColor;
		valueOf = function(){
			return this.strokeColor;
		};
	};
	
	this.setFillColor = function(fillColor) {
		this.fillColor = fillColor;
	};

	this.getFillColor = function() {
		return this.fillColor;
		valueOf = function(){
			return this.fillColor;
		};
	};
	

	
	this.setShader = function(shader) {
		this.shader = shader;
	};

	this.getShader = function() {
		return this.shader;
		valueOf = function(){
			return this.shader;
		};
	};
	
	this.getLabelXML = function(){
		
		var labelXML = '<label>'+
							'<name>'+'radial'+'</name>'+
							'<text>'+this.text+'</text>'+
							'<textcolor>'+this.textColor+'</textcolor>'+
							'<style>'+this.style+'</style>'+
							'<strokecolor>'+this.strokeColor+'</strokecolor>'+
							'<fillcolor>'+this.fillColor+'</fillcolor>'+
							'<offsetradius>'+this.offsetRadius+'</offsetradius>'+
							'<outlineround>'+this.outlineRound+'</outlineround>';						
		
		if(typeof this.shader != 'undefined'){
			labelXML = labelXML	+this.shader.getShaderXML();
		}
		if(typeof this.stroke != 'undefined'){
			labelXML = labelXML	+this.stroke.getStrokeXML();
		}
		
		labelXML = labelXML	+'</label>';
		return labelXML;
	};
};


com.jensoft.jenscript.plugin.donut3d.label.BorderLabel = function(text) {
	
	this.name = 'border';
	this.text = text;
	this.textColor;
	this.style='both';
	this.strokeColor;
	this.fillColor;
	this.linkExtends = 20;
	this.linkColor;
	this.outlineRound = 5;
	this.shader;
	this.margin = 30;
	this.stroke;
	
	this.getName = function() {
		return this.name;
		valueOf = function(){
			return this.name;
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
	
	this.setStroke = function(stroke) {
		this.stroke = stroke;
	};
	
	this.getStroke = function() {
		return this.stroke;
		valueOf = function(){
			return this.stroke;
		};
	};
	
	this.setMargin = function(margin) {
		this.margin = margin;
	};

	this.getMargin = function() {
		return this.margin;
		valueOf = function(){
			return this.margin;
		};
	};
	
	this.setOutlineRound = function(outlineRound) {
		this.outlineRound = outlineRound;
	};

	this.getOutlineRound = function() {
		return this.outlineRound;
		valueOf = function(){
			return this.outlineRound;
		};
	};
	
	this.setTextColor = function(textColor) {
		this.textColor = textColor;
	};

	this.getTextColor = function() {
		return this.textColor;
		valueOf = function(){
			return this.textColor;
		};
	};
	
	this.setStyle = function(style) {
		this.style = style;
	};

	this.getStyle = function() {
		return this.style;
		valueOf = function(){
			return this.style;
		};
	};
	
	this.setStrokeColor = function(strokeColor) {
		this.strokeColor = strokeColor;
	};

	this.getStrokeColor = function() {
		return this.strokeColor;
		valueOf = function(){
			return this.strokeColor;
		};
	};
	
	this.setFillColor = function(fillColor) {
		this.fillColor = fillColor;
	};

	this.getFillColor = function() {
		return this.fillColor;
		valueOf = function(){
			return this.fillColor;
		};
	};
	
	this.setLinkExtends = function(linkExtends) {
		this.linkExtends = linkExtends;
	};

	this.getLinkExtends = function() {
		return this.linkExtends;
		valueOf = function(){
			return this.linkExtends;
		};
	};
	
	this.setLinkColor = function(linkColor) {
		this.linkColor = linkColor;
	};

	this.getLinkColor = function() {
		return this.linkColor;
		valueOf = function(){
			return this.linkColor;
		};
	};
	
	this.setShader = function(shader) {
		this.shader = shader;
	};

	this.getShader = function() {
		return this.shader;
		valueOf = function(){
			return this.shader;
		};
	};
	
	this.getLabelXML = function(){
		var	labelXML = '<label>'+
							'<name>'+'border'+'</name>'+
							'<text>'+this.text+'</text>'+
							'<textcolor>'+this.textColor+'</textcolor>'+
							'<style>'+this.style+'</style>'+
							'<strokecolor>'+this.strokeColor+'</strokecolor>'+
							'<fillcolor>'+this.fillColor+'</fillcolor>'+
							'<linkextends>'+this.linkExtends+'</linkextends>'+
							'<linkcolor>'+this.linkColor+'</linkcolor>'+
							'<outlineround>'+this.outlineRound+'</outlineround>'+
							'<margin>'+this.margin+'</margin>';
								
		if(typeof this.shader != 'undefined'){
			labelXML = labelXML	+this.shader.getShaderXML();
		}
		if(typeof this.stroke != 'undefined'){
			labelXML = labelXML	+this.stroke.getStrokeXML();
		}
		labelXML = labelXML	+'</label>';
		return labelXML;
	};
	
	
};

com.jensoft.jenscript.plugin.donut3d.effect.Fx1 = function() {
	
	this.name = "fx1";
	this.incidence = 90;
	this.paintTopEffect    = true;
	this.paintInnerEffect  = true;
	this.paintOuterEffect  = true;
	this.alphaTop = 0.8;
	this.alphaInner = 1;
	this.alphaOuter = 1;
	this.alphaFill = 0.7;
	
	this.getName = function() {
		return this.name;
		valueOf = function(){
			return this.name;
		};
	};
	
	this.setIncidence = function(incidence) {
		this.incidence = incidence;
	};

	this.getIncidence = function() {
		return this.incidence;
		valueOf = function(){
			return this.incidence;
		};
	};
	
	this.setPaintTopEffect = function(paintTopEffect) {
		this.paintTopEffect = paintTopEffect;
	};

	this.getPaintTopEffect = function() {
		return this.paintTopEffect;
		valueOf = function(){
			return this.paintTopEffect;
		};
	};
	
	this.setPaintInnerEffect = function(paintInnerEffect) {
		this.paintInnerEffect = paintInnerEffect;
	};

	this.getPaintInnerEffect = function() {
		return this.paintInnerEffect;
		valueOf = function(){
			return this.paintInnerEffect;
		};
	};
	
	this.setPaintOuterEffect = function(paintOuterEffect) {
		this.paintOuterEffect = paintOuterEffect;
	};

	this.getPaintOuterEffect = function() {
		return this.paintOuterEffect;
		valueOf = function(){
			return this.paintOuterEffect;
		};
	};
	
	this.setAlphaTop = function(alphaTop) {
		this.alphaTop = alphaTop;
	};

	this.getAlphaTop = function() {
		return this.alphaTop;
		valueOf = function(){
			return this.alphaTop;
		};
	};
	
	this.setAlphaInner = function(alphaInner) {
		this.alphaInner = alphaInner;
	};

	this.getAlphaInner = function() {
		return this.alphaInner;
		valueOf = function(){
			return this.alphaInner;
		};
	};
	
	this.setAlphaOuter = function(alphaOuter) {
		this.alphaOuter = alphaOuter;
	};

	this.getAlphaOuter = function() {
		return this.alphaOuter;
		valueOf = function(){
			return this.alphaOuter;
		};
	};
	
	this.setAlphaFill = function(alphaFill) {
		this.alphaFill = alphaFill;
	};

	this.getAlphaFill = function() {
		return this.alphaFill;
		valueOf = function(){
			return this.alphaFill;
		};
	};


};



com.jensoft.jenscript.plugin.donut3d.Slice = function(value,themeColor) {
	
	this.name = "slice";	
	this.value = value;
	this.themeColor = themeColor;	
	this.divergence = 0;
	this.sliceLabel;
	
	this.setName = function(name) {
		this.name = name;
	};

	this.getName = function() {
		return this.name;
		valueOf = function(){
			return this.name;
		};
	};
	
	this.setValue = function(value) {
		this.value = value;
	};

	this.getValue = function() {
		return this.value;
		valueOf = function(){
			return this.value;
		};
	};
	
	this.setSliceLabel = function(sliceLabel) {
		this.sliceLabel = sliceLabel;
	};

	this.getSliceLabel = function() {
		return this.sliceLabel;
		valueOf = function(){
			return this.label;
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
	
	this.setDivergence = function(divergence) {
		this.divergence = divergence;
	};

	this.getDivergence = function() {
		return this.divergence;
		valueOf = function(){
			return this.divergence;
		};
	};
	
	
};

com.jensoft.jenscript.plugin.donut3d.Donut3DPlugin = function() {

	this.id;
	this.name;
	this.x=0;
	this.y=0;
	this.innerRadius = 60;
	this.outerRadius = 100;
	this.startAngleDegree = 0;
	this.thickness = 40;
	this.tilt = 40;	
	this.effect;	
	this.slices = [];	
	this.window2D;
	
	
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
	
	this.setX = function(x) {
		this.x = x;
	};

	this.getX = function() {
		return this.x;
		valueOf = function(){
			return this.x;
		};
	};
	
	this.setY = function(y) {
		this.y = y;
	};

	this.getY = function() {
		return this.y;
		valueOf = function(){
			return this.y;
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
	
	
	
	this.setDebug = function(debug) {
		this.debug = debug;
	};

	this.getDebug = function() {
		return this.debug;
		valueOf = function(){
			return this.debug;
		};
	};
	
	
	

	

	this.setInnerRadius = function(innerRadius) {
		this.innerRadius = innerRadius;
	};

	this.getInnerRadius = function() {
		return this.innerRadius;
		valueOf = function(){
			return this.innerRadius;
		};
	};
	
	this.setOuterRadius = function(outerRadius) {
		this.outerRadius = outerRadius;
	};

	this.getOuterRadius = function() {
		return this.outerRadius;
		valueOf = function(){
			return this.outerRadius;
		};
	};
	
	this.setThickness = function(thickness) {
		this.thickness = thickness;
	};

	this.getThickness = function() {
		return this.thickness;
		valueOf = function(){
			return this.thickness;
		};
	};
	

	this.setTilt = function(tilt) {
		this.tilt = tilt;
	};

	this.getTilt = function() {
		return this.tilt;
		valueOf = function(){
			return this.tilt;
		};
	};
	

	this.setStartAngleDegree = function(startAngleDegree) {
		this.startAngleDegree = startAngleDegree;
	};

	this.getStartAngleDegree = function() {
		return this.startAngleDegree;
		valueOf = function(){
			return this.startAngleDegree;
		};
	};

	this.setEffect = function(effect) {
		this.effect = effect;
	};

	this.getEffect = function() {
		return this.effect;
	};
	
	this.addSlice = function(value,themecolor) {
		var slice = new com.jensoft.jenscript.plugin.donut3d.Slice(value, themecolor);
		this.slices[this.slices.length] = slice;
	};
	
	this.addSlice = function(slice){
		this.slices[this.slices.length] = slice;
	};
	
	
	this.getPluginXML = function(){
		var colorutil = new com.jensoft.jenscript.ColorUtil();
		var pluginXML = '<plugin>'+
		'<id>'+this.id+'</id>'+
		'<name>'+this.name+'</name>'+
		'<class>'+'com.jensoft.sw2d.core.plugin.donut3D.Donut3DPlugin'+'</class>'+
		'<params>'+
			'<x>'+this.x+'</x>'+
			'<y>'+this.y+'</y>'+
			'<innerradius>'+this.innerRadius+'</innerradius>'+
			'<outerradius>'+this.outerRadius+'</outerradius>'+
			'<tilt>'+this.tilt+'</tilt>'+
			'<thickness>'+this.thickness+'</thickness>'+
			'<startangledegree>'+this.startAngleDegree+'</startangledegree>';
			
			if (typeof this.effect != 'undefined'){
				if (this.effect instanceof com.jensoft.jenscript.plugin.donut3d.effect.Fx1){
			
					pluginXML = pluginXML + 
								'<effect>'+
									'<name>'+'fx1'+'</name>'+
									'<incidence>'+this.effect.getIncidence()+'</incidence>'+
									'<alphafill>'+this.effect.getAlphaFill()+'</alphafill>'+
									'<painttop>'+this.effect.getPaintTopEffect()+'</painttop>'+
									'<paintinner>'+this.effect.getPaintInnerEffect()+'</paintinner>'+
									'<paintouter>'+this.effect.getPaintOuterEffect()+'</paintouter>'+
									'<alphatop>'+this.effect.getAlphaTop()+'</alphatop>'+
									'<alphainner>'+this.effect.getAlphaInner()+'</alphainner>'+
									'<alphaouter>'+this.effect.getAlphaOuter()+'</alphaouter>'+
								'</effect>';
								
					
					
					
					
					
				}
			}
			pluginXML = pluginXML + '<slices>';
			for(var i=0; i<this.slices.length; i++) {
				var s = this.slices[i];
				s.setName(this.name+'_s'+i);
								
				var	sliceXML =  '<slice>'+
									'<name>'+s.getName()+'</name>'+
									'<value>'+s.getValue()+'</value>'+
									'<themecolor>'+colorutil.format(s.getThemeColor())+'</themecolor>'+
									'<divergence>'+s.getDivergence()+'</divergence>';		
							
				if(typeof s.getSliceLabel() != 'undefined'){
					sliceXML = sliceXML + s.getSliceLabel().getLabelXML();
				}
				sliceXML = sliceXML + '</slice>';
				pluginXML = pluginXML + sliceXML;
			}
			
			pluginXML = pluginXML + '</slices></params></plugin>';
			
		return pluginXML;	
	};
	

};