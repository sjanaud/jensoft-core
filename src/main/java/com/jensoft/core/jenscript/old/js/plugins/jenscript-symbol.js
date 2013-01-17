registerNS('com.jensoft.jenscript.plugin.symbol');
registerNS('com.jensoft.jenscript.plugin.symbol.effect');
registerNS('com.jensoft.jenscript.plugin.symbol.label');



com.jensoft.jenscript.plugin.symbol.BarGroup = function(text) {

	this.name;
	this.symbol;
	this.thickness;
	this.symbolBase;	
	this.morpheStyle = 'round';
	this.morpheRound = 3;
	this.barDraw;//color
	this.barFill;//default,fill1,fill2
	this.barEffect;//fx1,fx2,fx3,fx4
	this.axisLabel;
	this.symbols = [];
	this.themeColor;
	
	this.setName = function(name) {
		this.name = name;
	};	

	this.getName = function() {
		return this.name;
		valueOf = function(){
			return this.name;
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
	
	this.setThickness = function(thickness) {
		this.thickness = thickness;
	};	

	this.getThickness = function() {
		return this.thickness;
		valueOf = function(){
			return this.thickness;
		};
	};
	
	this.setSymbolBase = function(symbolBase) {
		this.symbolBase = symbolBase;
	};	

	this.getSymbolBase = function() {
		return this.symbolBase;
		valueOf = function(){
			return this.symbolBase;
		};
	};
	

	this.setMorpheRound = function(morpheRound) {
		this.morpheRound = morpheRound;
	};	

	this.getMorpheRound = function() {
		return this.morpheRound;
		valueOf = function(){
			return this.morpheRound;
		};
	};
	
	this.setBarDraw = function(barDraw) {
		this.barDraw = barDraw;
	};	

	this.getBarDraw = function() {
		return this.barDraw;
		valueOf = function(){
			return this.barDraw;
		};
	};
	
	this.setBarFill = function(barFill) {
		this.barFill = barFill;
	};	

	this.getBarFill = function() {
		return this.barFill;
		valueOf = function(){
			return this.barFill;
		};
	};
	
	this.setBarEffect = function(barEffect) {
		this.barEffect = barEffect;
	};	

	this.getBarEffect = function() {
		return this.barEffect;
		valueOf = function(){
			return this.barEffect;
		};
	};
	
	this.setAxisLabel = function(axisLabel) {
		this.axisLabel = axisLabel;
	};
	
	this.getAxisLabel = function() {
		return this.axisLabel;
		valueOf = function(){
			return this.axisLabel;
		};
	};
	
	this.addSymbol = function(symbol){
		this.symbols[this.symbols.length] = symbol;
	};
	

	this.getSymbolXML = function() {	
		var colorutil = new com.jensoft.jenscript.ColorUtil();
		var symbolXML = '<symbol>'+
							'<type>'+'group'+'</type>'+
							'<name>'+this.name+'</name>'+
							'<thickness>'+this.thickness+'</thickness>'+
							'<base>'+this.symbolBase+'</base>'+
							'<morphestyle>'+this.morpheStyle+'</morphestyle>'+
							'<morpheround>'+this.morpheRound+'</morpheround>'+
							'<draw>'+colorutil.format(this.barDraw)+'</draw>'+
							'<fill>'+this.barFill+'</fill>'+
							'<effect>'+this.barEffect+'</effect>';
		if(typeof this.axisLabel != 'undefined'){			
			symbolXML = symbolXML + this.axisLabel.getLabelXML();			
		}
		symbolXML = symbolXML + '<children>';	
		
		for(var i=0; i<this.symbols.length; i++) {
				var s = this.symbols[i];
				if(typeof s.getName() == 'undefined')
					s.setName('symbol'+i);
					symbolXML = symbolXML + s.getSymbolXML();
		}
		
		symbolXML = symbolXML + '</children></symbol>';
							
		return symbolXML;
	};


};

com.jensoft.jenscript.plugin.symbol.label.AxisLabel = function(text) {
	this.text=text;
	this.textColor;
	this.drawColor;
	this.fillColor;
	this.outlineRound=6;
	this.alpha=1;
	this.offsetX = 0;
	this.offsetY = 0;
	this.textPaddingX = 5;	
	this.textPaddingY = 2;
	this.outlineStroke;
	this.shader;
	
	this.setShader = function(shader) {
		this.shader = shader;
	};

	this.getShader = function() {
		return this.shader;
		valueOf = function(){
			return this.shader;
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
	
	this.setOutlineStroke = function(outlineStroke) {
		this.outlineStroke = outlineStroke;
	};

	this.getOutlineStroke = function() {
		return this.outlineStroke;
		valueOf = function(){
			return this.outlineStroke;
		};
	};
	
	this.setOffsetX = function(offsetX) {
		this.offsetX = offsetX;
	};

	this.getOffsetX = function() {
		return this.offsetX;
		valueOf = function(){
			return this.offsetX;
		};
	};
	
	this.setTextPaddingX = function(textPaddingX) {
		this.textPaddingX = textPaddingX;
	};

	this.getTextPaddingX = function() {
		return this.textPaddingX;
		valueOf = function(){
			return this.textPaddingX;
		};
	};
	
	this.setOffsetY = function(offsetY) {
		this.offsetY = offsetY;
	};

	this.getOffsetY = function() {
		return this.offsetY;
		valueOf = function(){
			return this.offsetY;
		};
	};
	
	this.setTextPaddingY = function(textPaddingY) {
		this.textPaddingY = textPaddingY;
	};

	this.getTextPaddingY = function() {
		return this.textPaddingY;
		valueOf = function(){
			return this.textPaddingY;
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
	
	this.setDrawColor = function(drawColor) {
		this.drawColor = drawColor;
	};

	this.getDrawColor = function() {
		return this.drawColor;
		valueOf = function(){
			return this.drawColor;
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
	
	this.getLabelXML = function(){
		var colorutil = new com.jensoft.jenscript.ColorUtil();
		var labelXML = '<label>'+
						'<type>'+'axis'+'</type>'+
						'<text>'+this.text+'</text>'+
						'<textcolor>'+colorutil.format(this.textColor)+'</textcolor>'+
						'<drawcolor>'+colorutil.format(this.drawColor)+'</drawcolor>'+
						'<fillcolor>'+colorutil.format(this.fillColor)+'</fillcolor>'+
						'<outlineround>'+this.outlineRound+'</outlineround>'+
						'<offsetX>'+this.offsetX+'</offsetX>'+
						'<offsetY>'+this.offsetY+'</offsetY>'+
						'<textpaddingX>'+this.textPaddingX+'</textpaddingX>'+
						'<textpaddingY>'+this.textPaddingY+'</textpaddingY>'+
						'<alpha>'+this.alpha+'</alpha>';
		if(typeof this.outlineStroke != 'undefined'){
			labelXML = labelXML + this.outlineStroke.getStrokeXML();
		}
		if(typeof this.shader != 'undefined'){
			labelXML = labelXML + this.shader.getShaderXML();
		}
			
		labelXML =labelXML + '</label>';
		return labelXML;
	};
}


	
com.jensoft.jenscript.plugin.symbol.label.RelativeLabel = function(text) {
	this.text=text;
	this.verticalAlignment='NorthTop';//NorthTop,NorthAcross,NorthBottom,Middle,SouthTop,SouthAcross,SouthBottom;
	this.horizontalAlignment='Middle';//WestLeft,WestAcross,WestRight,Middle,EastLeft,EastAcross,EastRight;
	this.textColor;
	this.drawColor;
	this.fillColor;
	this.outlineRound=6;
	this.outlineStroke;
	this.alpha=1;
	this.offsetX = 5;
	this.offsetY = 5;
	this.textPaddingX = 5;	
	this.textPaddingY = 2;
	this.shader;
	
	
	
	this.setText = function(text) {
		this.text = text;
	};

	this.getText = function() {
		return this.text;
		valueOf = function(){
			return this.text;
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
	
	this.setOutlineStroke = function(outlineStroke) {
		this.outlineStroke = outlineStroke;
	};

	this.getOutlineStroke = function() {
		return this.outlineStroke;
		valueOf = function(){
			return this.outlineStroke;
		};
	};
	
	this.setOffsetX = function(offsetX) {
		this.offsetX = offsetX;
	};

	this.getOffsetX = function() {
		return this.offsetX;
		valueOf = function(){
			return this.offsetX;
		};
	};
	
	this.setTextPaddingX = function(textPaddingX) {
		this.textPaddingX = textPaddingX;
	};

	this.getTextPaddingX = function() {
		return this.textPaddingX;
		valueOf = function(){
			return this.textPaddingX;
		};
	};
	
	this.setOffsetY = function(offsetY) {
		this.offsetY = offsetY;
	};

	this.getOffsetY = function() {
		return this.offsetY;
		valueOf = function(){
			return this.offsetY;
		};
	};
	
	this.setTextPaddingY = function(textPaddingY) {
		this.textPaddingY = textPaddingY;
	};

	this.getTextPaddingY = function() {
		return this.textPaddingY;
		valueOf = function(){
			return this.textPaddingY;
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
	
	this.setDrawColor = function(drawColor) {
		this.drawColor = drawColor;
	};

	this.getDrawColor = function() {
		return this.drawColor;
		valueOf = function(){
			return this.drawColor;
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
	
	this.setVerticalAlign = function(verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	};

	this.getVerticalAlign = function() {
		return this.verticalAlignment;
		valueOf = function(){
			return this.verticalAlignment;
		};
	};
	
	this.setHorizontalAlign = function(horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
	};

	this.getHorizontalAlign = function() {
		return this.horizontalAlignment;
		valueOf = function(){
			return this.horizontalAlignment;
		};
	};
	
	
	this.getLabelXML = function(){
		var colorutil = new com.jensoft.jenscript.ColorUtil();
		var labelXML = '<label>'+
						'<type>'+'relative'+'</type>'+
						'<valign>'+this.verticalAlignment+'</valign>'+
						'<halign>'+this.horizontalAlignment+'</halign>'+
						'<text>'+this.text+'</text>'+
						'<textcolor>'+colorutil.format(this.textColor)+'</textcolor>'+
						'<drawcolor>'+colorutil.format(this.drawColor)+'</drawcolor>'+
						'<fillcolor>'+colorutil.format(this.fillColor)+'</fillcolor>'+
						'<outlineround>'+this.outlineRound+'</outlineround>'+
						'<offsetX>'+this.offsetX+'</offsetX>'+
						'<offsetY>'+this.offsetY+'</offsetY>'+
						'<textpaddingX>'+this.textPaddingX+'</textpaddingX>'+
						'<textpaddingY>'+this.textPaddingY+'</textpaddingY>'+
						'<alpha>'+this.alpha+'</alpha>';
						
		if(typeof this.outlineStroke != 'undefined'){
			labelXML = labelXML + this.outlineStroke.getStrokeXML();
		}
		if(typeof this.shader != 'undefined'){
			labelXML = labelXML + this.shader.getShaderXML();
		}
			
		labelXML =labelXML + '</label>';
		return labelXML;
	};
	
}

com.jensoft.jenscript.plugin.symbol.BarSymbolLayer = function() {	
	this.symbols = [];
	
	this.addSymbol = function(symbol){
		this.symbols[this.symbols.length] = symbol;
	};
	
	this.getLayerXML = function(){
		var layerXML = '<layer>'+
					   '<type>'+'BarSymbolLayer'+'</type>'+
					   '<symbols>';
			for(var i=0; i<this.symbols.length; i++) {
				var s = this.symbols[i];
				if(typeof s.getName() == 'undefined')
					s.setName('symbol'+i);
				layerXML = layerXML + s.getSymbolXML();
			}		
		layerXML = layerXML + '</symbols></layer>';
		return layerXML;
	}
};

com.jensoft.jenscript.plugin.symbol.PointSymbolLayer = function() {
	
};

com.jensoft.jenscript.plugin.symbol.PointSymbol = function() {
	
};

com.jensoft.jenscript.plugin.symbol.SymbolPlugin = function() {
	
	this.id;
	this.name;
	this.nature = 'vertical';
	this.min;
	this.max;	
	this.window2D;
	this.layers = [];
	
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
	

	this.setNature = function(nature) {
		this.nature = nature;
	};	

	this.getNature = function() {
		return this.nature;
		valueOf = function(){
			return this.nature;
		};
	};
	
	this.setMin = function(min) {
		this.min = min;
	};	

	this.getMin = function() {
		return this.min;
		valueOf = function(){
			return this.min;
		};
	};
	
	this.setMax = function(max) {
		this.max = max;
	};	

	this.getMax = function() {
		return this.max;
		valueOf = function(){
			return this.max;
		};
	};
	
	this.addLayer = function(layer){
		this.layers[this.layers.length] = layer;
	};
	
	this.getPluginXML = function(){		
		var pluginXML = '<plugin>'+
						'<id>'+this.id+'</id>'+
						'<name>'+this.name+'</name>'+
						'<class>'+'com.jensoft.sw2d.core.plugin.symbol.SymbolPlugin'+'</class>'+
						'<params>'+
							'<nature>'+this.nature+'</nature>'+
							'<min>'+this.min+'</min>'+
							'<max>'+this.max+'</max>';
		pluginXML = pluginXML + '<layers>';		
		for(var i=0; i<this.layers.length; i++) {
				var l = this.layers[i];
				pluginXML = pluginXML + l.getLayerXML();
		}		
		pluginXML = pluginXML + '</layers></params></plugin>';
		return pluginXML;
	};
	
};

com.jensoft.jenscript.plugin.symbol.Bar = function() {
	
	this.name;
	this.themeColor;
	this.thickness;
	this.symbolBase;
	this.value;
	this.symbol;
	this.morpheStyle = 'round';
	this.morpheRound = 3;
	this.barDraw;//color
	this.barFill;//default,fill1,fill2
	this.barEffect;//fx1,fx2,fx3,fx4
	
	this.ascent = false;
	this.descent = false;
	
	this.label;
	this.axisLabel;
	
	this.setName = function(name) {
		this.name = name;
	};	

	this.getName = function() {
		return this.name;
		valueOf = function(){
			return this.name;
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
	
	this.setThickness = function(thickness) {
		this.thickness = thickness;
	};	

	this.getThickness = function() {
		return this.thickness;
		valueOf = function(){
			return this.thickness;
		};
	};
	
	this.setSymbolBase = function(symbolBase) {
		this.symbolBase = symbolBase;
	};	

	this.getSymbolBase = function() {
		return this.symbolBase;
		valueOf = function(){
			return this.symbolBase;
		};
	};
	
	this.setAscentValue = function(value) {
		this.ascent = true;
		this.descent = false;
		this.value = value;
	};
	
	this.setDescentValue = function(value) {
		this.ascent = false;
		this.descent = true;
		this.value = value;
	};
	
	this.getValue = function() {
		return this.value;
		valueOf = function(){
			return this.value;
		};
	};
	
	
	this.getAscent = function() {
		return this.ascent;
		valueOf = function(){
			return this.ascent;
		};
	};
	
	this.getDescent = function() {
		return this.descent;
		valueOf = function(){
			return this.descent;
		};
	};
	
	this.getInflate = function() {
		if(this.getAscent())
			return 'ascent';
		else if(this.getDescent())
			return 'descent';
	};
	
	this.setMorpheRound = function(morpheRound) {
		this.morpheRound = morpheRound;
	};	

	this.getMorpheRound = function() {
		return this.morpheRound;
		valueOf = function(){
			return this.morpheRound;
		};
	};
	
	this.setBarDraw = function(barDraw) {
		this.barDraw = barDraw;
	};	

	this.getBarDraw = function() {
		return this.barDraw;
		valueOf = function(){
			return this.barDraw;
		};
	};
	
	this.setBarFill = function(barFill) {
		this.barFill = barFill;
	};	

	this.getBarFill = function() {
		return this.barFill;
		valueOf = function(){
			return this.barFill;
		};
	};
	
	this.setBarEffect = function(barEffect) {
		this.barEffect = barEffect;
	};	

	this.getBarEffect = function() {
		return this.barEffect;
		valueOf = function(){
			return this.barEffect;
		};
	};
	
	this.setLabel = function(label) {
		this.label = label;
	};
	
	this.getLabel = function() {
		return this.label;
		valueOf = function(){
			return this.label;
		};
	};
	this.setAxisLabel = function(axisLabel) {
		this.axisLabel = axisLabel;
	};
	
	this.getAxisLabel = function() {
		return this.axisLabel;
		valueOf = function(){
			return this.axisLabel;
		};
	};

	this.getSymbolXML = function() {
		var colorutil = new com.jensoft.jenscript.ColorUtil();
		var symbolXML = '<symbol>'+
							'<type>'+'bar'+'</type>'+
							'<name>'+this.name+'</name>'+
							'<themecolor>'+colorutil.format(this.themeColor)+'</themecolor>'+
							'<thickness>'+this.thickness+'</thickness>'+
							'<base>'+this.symbolBase+'</base>'+
							'<value>'+this.value+'</value>'+
							'<inflate>'+this.getInflate()+'</inflate>'+
							'<morphestyle>'+this.morpheStyle+'</morphestyle>'+
							'<morpheround>'+this.morpheRound+'</morpheround>'+
							'<draw>'+colorutil.format(this.barDraw)+'</draw>'+
							'<fill>'+this.barFill+'</fill>'+
							'<effect>'+this.barEffect+'</effect>';
		
		if(typeof this.label != 'undefined'){
			symbolXML = symbolXML + this.label.getLabelXML();
		}
		if(typeof this.axisLabel != 'undefined'){
			symbolXML = symbolXML + this.axisLabel.getLabelXML();
		}
		symbolXML = symbolXML + '</symbol>';
							
		return symbolXML;
	};
	
};

com.jensoft.jenscript.plugin.symbol.Glue = function() {
	this.name;
	
	this.setName = function(name) {
		this.name = name;
	};	

	this.getName = function() {
		return this.name;
		valueOf = function(){
			return this.name;
		};
	};
	
	this.getSymbolXML = function() {
		var symbolXML = '<symbol>'+
							'<type>'+'glue'+'</type>'+
							'<name>'+this.name+'</name>'+
						'</symbol>';
			return symbolXML;
	};
};

com.jensoft.jenscript.plugin.symbol.Strut = function(strut) {
	this.name;
	this.strut = strut;
	
	this.setName = function(name) {
		this.name = name;
	};	

	this.getName = function() {
		return this.name;
		valueOf = function(){
			return this.name;
		};
	};
	
	this.setStrut = function(strut) {
		this.strut = strut;
		
	};
	
	this.getStrut = function() {
		return this.strut;
		valueOf = function(){
			return this.strut;
		};
	};
	
	this.getSymbolXML = function() {
		var symbolXML = '<symbol>'+
							'<type>'+'strut'+'</type>'+
							'<name>'+this.name+'</name>'+
							'<value>'+this.strut+'</value>'+
						'</symbol>';
		return symbolXML;
	};
};

com.jensoft.jenscript.plugin.symbol.StackedBar = function() {
	
	this.name;
	this.themeColor;
	this.thickness;
	this.symbolBase;
	this.value;
	this.name;
	this.symbol;
	this.morpheStyle = 'round';
	this.morpheRound = 3;
	this.barDraw;
	this.barFill;
	this.barEffect;
	this.stacks = [];
	
	this.ascent = false;
	this.descent = false;
	
	this.label;
	this.axisLabel;
	
	this.setName = function(name) {
		this.name = name;
	};	

	this.getName = function() {
		return this.name;
		valueOf = function(){
			return this.name;
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
	
	this.setThickness = function(thickness) {
		this.thickness = thickness;
	};	

	this.getThickness = function() {
		return this.thickness;
		valueOf = function(){
			return this.thickness;
		};
	};
	
	this.setSymbolBase = function(symbolBase) {
		this.symbolBase = symbolBase;
	};	

	this.getSymbolBase = function() {
		return this.symbolBase;
		valueOf = function(){
			return this.symbolBase;
		};
	};
	
	this.setAscentValue = function(value) {
		this.ascent = true;
		this.descent = false;
		this.value = value;
	};
	
	this.setDescentValue = function(value) {
		this.ascent = false;
		this.descent = true;
		this.value = value;
	};
	
	this.getValue = function() {
		return this.value;
		valueOf = function(){
			return this.value;
		};
	};
	
	
	this.getAscent = function() {
		return this.ascent;
		valueOf = function(){
			return this.ascent;
		};
	};
	
	this.getDescent = function() {
		return this.descent;
		valueOf = function(){
			return this.descent;
		};
	};
	
	this.getInflate = function() {
		if(this.getAscent())
			return 'ascent';
		else if(this.getDescent())
			return 'descent';
	};
	
		this.setBarDraw = function(barDraw) {
		this.barDraw = barDraw;
	};	

	this.getBarDraw = function() {
		return this.barDraw;
		valueOf = function(){
			return this.barDraw;
		};
	};
	
	this.setBarFill = function(barFill) {
		this.barFill = barFill;
	};	

	this.getBarFill = function() {
		return this.barFill;
		valueOf = function(){
			return this.barFill;
		};
	};
	
	this.setBarEffect = function(barEffect) {
		this.barEffect = barEffect;
	};	

	this.getBarEffect = function() {
		return this.barEffect;
		valueOf = function(){
			return this.barEffect;
		};
	};
	
	this.setMorpheRound = function(morpheRound) {
		this.morpheRound = morpheRound;
	};	

	this.getMorpheRound = function() {
		return this.morpheRound;
		valueOf = function(){
			return this.morpheRound;
		};
	};
	
	this.setLabel = function(label) {
		this.label = label;
	};
	
	this.getLabel = function() {
		return this.label;
		valueOf = function(){
			return this.label;
		};
	};
	
	this.setAxisLabel = function(axisLabel) {
		this.axisLabel = axisLabel;
	};
	
	this.getAxisLabel = function() {
		return this.axisLabel;
		valueOf = function(){
			return this.axisLabel;
		};
	};
	
	this.addStack = function(stack){
		this.stacks[this.stacks.length] = stack;
	};
	
	this.getSymbolXML = function() {
		var colorutil = new com.jensoft.jenscript.ColorUtil();
		var symbolXML = '<symbol>'+
							'<type>'+'stackedbar'+'</type>'+
							'<name>'+this.name+'</name>'+
							'<themecolor>'+colorutil.format(this.themeColor)+'</themecolor>'+
							'<thickness>'+this.thickness+'</thickness>'+
							'<base>'+this.symbolBase+'</base>'+
							'<value>'+this.value+'</value>'+
							'<inflate>'+this.getInflate()+'</inflate>'+
							'<morphestyle>'+this.morpheStyle+'</morphestyle>'+
							'<morpheround>'+this.morpheRound+'</morpheround>'+
							'<draw>'+colorutil.format(this.barDraw)+'</draw>'+
							'<fill>'+this.barFill+'</fill>'+
							'<effect>'+this.barEffect+'</effect>';
		if(typeof this.label != 'undefined'){
			symbolXML = symbolXML + this.label.getLabelXML();
		}
		if(typeof this.axisLabel != 'undefined'){
			symbolXML = symbolXML + this.axisLabel.getLabelXML();
		}
		symbolXML = symbolXML + '<stacks>';		
							
		for(var i=0; i<this.stacks.length; i++) {
				var s = this.stacks[i];
				symbolXML = symbolXML + s.getStackXML();
		}						
			symbolXML = symbolXML + '</stacks></symbol>';
		return symbolXML;
	};
	
	
};

com.jensoft.jenscript.plugin.symbol.Stack = function(name,themeColor,value) {
	this.name = name;
	this.themeColor = themeColor;
	this.value = value;
	this.label;
	
	this.setName = function(name) {
		this.name = name;
	};	

	this.getName = function() {
		return this.name;
		valueOf = function(){
			return this.name;
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
	
	this.setValue = function(value) {
		this.value = value;
	};
	
	this.getValue = function() {
		return this.value;
		valueOf = function(){
			return this.value;
		};
	};
	
	this.setLabel = function(label) {
		this.label = label;
	};
	
	this.getLabel = function() {
		return this.label;
		valueOf = function(){
			return this.label;
		};
	};
	
	this.getStackXML = function() {
		var colorutil = new com.jensoft.jenscript.ColorUtil();
		var stackXML = '<stack>'+
							'<name>'+this.name+'</name>'+
							'<themecolor>'+colorutil.format(this.themeColor)+'</themecolor>'+
							'<value>'+this.value+'</value>';
		if(typeof this.label != 'undefined'){
			stackXML = stackXML+this.label.getLabelXML();
		}
		stackXML = stackXML+'</stack>';
		return stackXML;
	};
	
};




