/**
 * JenScript PiePlugin Namespace
 * @namespace
 */
JenScript.PiePlugin = {};


/**
 * Pie Abstract Label 
 */
JenScript.PiePlugin.AbstractLabel = function() {
	this.text='label';
	this.textColor=JenScript.Core.colorUtil.getColor(203,71,52,255);
	this.font='nomove';
	this.textPaddingX=4;
	this.textPaddingY=2;
	this.outlineRound=12;
	this.outlineColor=JenScript.Core.colorUtil.getColor(203,71,52,255);
	this.outlineStroke;
	this.fillColor;
	this.shader;
	this.style='both';
};

/*
 * Abstract Pie Label method
 */
JenScript.PiePlugin.AbstractLabel.prototype =  {
                                          
	getAbstractX2D : function(){
		var x2dBuilder = JenScript.Core.x2dBuilder;
		var	abstractX2DLabel = 	x2dBuilder.createElement('text',this.text)+
								x2dBuilder.getX2D('text-color',this.textColor)+
								x2dBuilder.createElement('font',this.font)+
								x2dBuilder.createElement('text-padding-x',this.textPaddingX)+
								x2dBuilder.createElement('text-padding-y',this.textPaddingY)+
								x2dBuilder.createElement('outline-round',this.outlineRound)+
								x2dBuilder.getX2D('outline-color',this.outlineColor)+
								x2dBuilder.getX2D('outline-stroke',this.outlineStroke)+
								x2dBuilder.getX2D('fill-color',this.fillColor)+
								x2dBuilder.getX2D('shader',this.shader)+
								x2dBuilder.createElement('style',this.style);
		return abstractX2DLabel;
	},
                                          
	setText : function(text) {
		this.text = text;
	},
	
	getText : function() {
		return this.text;
	},
	
	setTextColor : function(textColor) {
		this.textColor = textColor;
	},
	
	getTextColor : function() {
		return this.textColor;
	},
	
	setFont : function(font) {
		this.font = font;
	},
	
	getFont : function() {
		return this.font;
	},
	
	setTextPaddingX : function(textPaddingX) {
		this.textPaddingX = textPaddingX;
	},
	
	getTextPaddingX : function() {
		return this.textPaddingX;
	},
	
	setTextPaddingY : function(textPaddingY) {
		this.textPaddingY = textPaddingY;
	},
	
	getTextPaddingY : function() {
		return this.textPaddingY;
	},
	
	setOutlineRound : function(outlineRound) {
		this.outlineRound = outlineRound;
	},
	
	getOutlineRound : function() {
		return this.outlineRound;
	},
	
	setOutlineColor : function(outlineColor) {
		this.outlineColor = outlineColor;
	},
	
	getOutlineColor : function() {
		return this.outlineColor;
	},
	
	setOutlineStroke : function(outlineStroke) {
		this.outlineStroke = outlineStroke;
	},
	
	getOutlineStroke : function() {
		return this.outlineStroke;
	},
	
	setFillColor : function(fillColor) {
		this.fillColor = fillColor;
	},
	
	getFillColor : function() {
		return this.fillColor;
	},
	
	setShader : function(shader) {
		this.shader = shader;
	},
	
	getShader : function() {
		return this.shader;
	},
	
	setStyle : function(style) {
		this.style = style;
	},
	
	getStyle : function() {
		return this.style;
	}	
};


/**
 * Pie Bound Label
 */
JenScript.PiePlugin.PieBoundLabel = function() {
	JenScript.PiePlugin.AbstractLabel.apply(this,[]);
};

/*
 * Pie Bound Label methods
 */
JenScript.PiePlugin.PieBoundLabel.prototype =  {                                       
	getX2D : function(){
		var x2dBuilder = JenScript.Core.x2dBuilder;
		var	x2dLabel =  x2dBuilder.openType('label','PieBoundLabel')+
						this.getAbstractX2D()+
						x2dBuilder.closeElement('label');
		return x2dLabel;
	}
};

//extends AbstractLabel
JenScript.Core.extend(JenScript.PiePlugin.PieBoundLabel, JenScript.PiePlugin.AbstractLabel);


/**
 * Pie Path Label
 */
JenScript.PiePlugin.PiePathLabel = function(text) {	
	this.pathSide='above';//above, over, below;
	this.textPosition='left';//left, middle, right;
	this.divergence=10;
	this.textShader;
	this.pathName='arc';//arc,start,end
	JenScript.PiePlugin.AbstractLabel.apply(this,[]);
};

/*
 * Path Label methods
 */
JenScript.PiePlugin.PiePathLabel.prototype = {
	
	setDivergence : function(divergence) {
		this.divergence = divergence;
	},

	getDivergence : function() {
		return this.divergence;
	},
	
	setTextShader : function(textShader) {
		this.textShader = textShader;
	},

	getTextShader : function() {
		return this.textShader;
	},
	
	setPathSide : function(pathSide) {
		this.pathSide = pathSide;
	},

	getPathSide : function() {
		return this.pathSide;
	},
	
	setTextPosition : function(textPosition) {
		this.textPosition = textPosition;
	},

	getTextPosition : function() {
		return this.textPosition;
	},
	
	setPathName : function(pathName) {
		this.pathName = pathName;
	},

	getPathName : function() {
		return this.pathName;
	},
	
	/**
	 * get pie path label as X2D XML
	 */
	getX2D : function(){
		var x2dBuilder = JenScript.Core.x2dBuilder;
		var	x2dLabel =  x2dBuilder.openType('label','PiePathLabel')+
						this.getAbstractX2D()+
						x2dBuilder.createElement('text-divergence',this.divergence)+
						x2dBuilder.createElement('text-position',this.textPosition)+
						x2dBuilder.createElement('text-side',this.pathSide)+
						x2dBuilder.createElement('segment-path',this.pathName)+
						x2dBuilder.getX2D('text-shader',this.textShader)+
						x2dBuilder.closeElement('label');
		return x2dLabel;
	}
};
//extends AbstractLabel
JenScript.Core.extend(JenScript.PiePlugin.PiePathLabel, JenScript.PiePlugin.AbstractLabel);
/**
 * PieBorderLabel
 */
JenScript.PiePlugin.PieBorderLabel = function(text) {
	
	this.margin = 40;
	this.linkEnable=true;
	this.linkStyle='quad';
	this.linkColor = new JenScript.Color(0,0,0,255);
	this.linkStroke= new JenScript.Stroke(1);
	this.linkExtends = 20;
	
	this.linkMarkerEnable;
	this.linkMarkerDrawColor;
	this.linkMarkerFillColor;
	
	JenScript.PiePlugin.AbstractLabel.apply(this,[]);
};

/*
 * Pie Border Label Methods
 */
JenScript.PiePlugin.PieBorderLabel.prototype = {
			
	setMargin : function(margin) {
		this.margin = margin;
	},

	getMargin : function() {
		return this.margin;
	},
	
	setLinkExtends : function(linkExtends) {
		this.linkExtends = linkExtends;
	},

	getLinkExtends : function() {
		return this.linkExtends;
	},
	
	setLinkEnable : function(linkEnable) {
		this.linkEnable = linkEnable;
	},

	getLinkEnable : function() {
		return this.linkEnable;
	},
	
	setLinkStyle : function(linkStyle) {
		this.linkStyle = linkStyle;
	},

	getLinkStyle : function() {
		return this.linkStyle;
	},
	
	setLinkStroke : function(linkStroke) {
		this.linkStroke = linkStroke;
	},

	getLinkStroke : function() {
		return this.linkStroke;
	},
	
	setLinkColor : function(linkColor) {
		this.linkColor = linkColor;
	},

	getLinkColor : function() {
		return this.linkColor;
	},
	
	setLinkMarkerEnable : function(linkMarkerEnable) {
		this.linkMarkerEnable = linkMarkerEnable;
	},

	getLinkMarkerEnable : function() {
		return this.linkMarkerEnable;
	},
	
	setLinkMarkerDrawColor : function(linkMarkerDrawColor) {
		this.linkMarkerDrawColor = linkMarkerDrawColor;
	},

	getLinkMarkerDrawColor : function() {
		return this.linkMarkerDrawColor;
	},
	
	setLinkMarkerFillColor : function(linkMarkerFillColor) {
		this.linkMarkerFillColor = linkMarkerFillColor;
	},

	getLinkMarkerFillColor : function() {
		return this.linkMarkerFillColor;
	},
	
	/**
	 * get border label as X2D XML
	 * @return {String} x2d XML
	 */	
	getX2D : function(){
		var x2dBuilder = JenScript.Core.x2dBuilder;
		var	x2dLabel =  x2dBuilder.openType('label','PieBorderLabel')+
						this.getAbstractX2D()+
						x2dBuilder.createElement('label-margin',this.margin)+
						x2dBuilder.createElement('link-enable',this.linkEnable)+
						x2dBuilder.createElement('link-style',this.linkStyle)+
						x2dBuilder.getX2D('link-color',this.linkColor)+						
						x2dBuilder.getX2D('link-stroke',this.linkStroke)+
						x2dBuilder.createElement('link-extends',this.linkExtends)+
						x2dBuilder.createElement('marker-enable',this.linkMarkerEnable)+
						x2dBuilder.createElement('marker-draw',this.linkMarkerDrawColor)+
						x2dBuilder.createElement('marker-fill',this.linkMarkerFillColor)+						
						x2dBuilder.closeElement('label');
		return x2dLabel;
	}
	
	
};
//extends AbstractLabel
JenScript.Core.extend(JenScript.PiePlugin.PieBorderLabel, JenScript.PiePlugin.AbstractLabel);
/**
 * Pie radial Label
 */
JenScript.PiePlugin.PieRadialLabel = function() {
	this.offsetRadius = 30;
	JenScript.PiePlugin.AbstractLabel.apply(this,[]);
};

/*
 * Pie radial label methods
 */
JenScript.PiePlugin.PieRadialLabel.prototype = {
		
	setOffsetRadius : function(offsetRadius) {
		this.offsetRadius = offsetRadius;
	},

	getOffsetRadius : function() {
		return this.offsetRadius;
	},
	
	/**
	 * get radial label as X2D XML
	 * @return {String} x2d XML
	 */
	getX2D : function(){
		var x2dBuilder = JenScript.Core.x2dBuilder;
		var	x2dLabel =  x2dBuilder.openType('label','PieRadialLabel')+
						this.getAbstractX2D()+
						x2dBuilder.createElement('offset-radius',this.offsetRadius)+
						x2dBuilder.closeElement('label');
		return x2dLabel;
	}
};

//extends AbstractLabel
JenScript.Core.extend(JenScript.PiePlugin.PieRadialLabel, JenScript.PiePlugin.AbstractLabel);

JenScript.PiePlugin.PieLinearEffect = function() {	
	this.incidence = 120;
	this.offset = 6;
	this.shader;
};


JenScript.PiePlugin.PieLinearEffect.prototype =   {
	
	setIncidence : function(incidence) {
		this.incidence = incidence;
	},

	getIncidence : function() {
		return this.incidence;
	},
	
	setOffserRadius : function(offset) {
		this.offset = offset;
	},

	getOffsetRadius : function() {
		return this.offset;
	},
	
	setShader : function(shader) {
		this.shader = shader;
	},

	getShader : function() {
		return this.shader;
	},
	

	getX2D : function(){
		var x2dBuilder = JenScript.Core.x2dBuilder;
		var x2dLabel =  x2dBuilder.openType('effect','PieLinearEffect')+
						x2dBuilder.createElement('incidence-angle',this.incidence)+
						x2dBuilder.createElement('offset-radius',this.offset)+
						x2dBuilder.getX2D('shader',this.shader)+
						x2dBuilder.closeElement('effect');
		
		return x2dLabel;
	}
};


JenScript.PiePlugin.PieRadialEffect = function() {
	this.focusAngle=0;
	this.focusRadius=0;
	this.offsetRadius=0
};

JenScript.PiePlugin.PieRadialEffect.prototype =  {
	
	setFocusAngle : function(focusAngle) {
		this.focusAngle = focusAngle;
	},

	getFocusAngle : function() {
		return this.focusAngle;
	},
	
	setFocusRadius : function(focusRadius) {
		this.focusRadius = focusRadius;
	},

	getFocusRadius : function() {
		return this.focusRadius;
	},
	
	setOffsetRadius : function(offsetRadius) {
		this.offsetRadius = offsetRadius;
	},
	
	getOffsetRadius : function() {
		return this.offsetRadius;
	},
	
	getX2D : function(){
		var x2dBuilder = JenScript.Core.x2dBuilder;
		var x2dLabel =  x2dBuilder.openType('effect','PieRadialEffect')+
						x2dBuilder.createElement('focus-angle',this.focusAngle)+
						x2dBuilder.createElement('focus-radius',this.focusRadius)+
						x2dBuilder.createElement('offset-radius',this.offsetRadius)+
						x2dBuilder.closeElement('effect');
		
		return x2dLabel;
	}
	
};


/**
 * Create default Pie reflection effect
 */
JenScript.PiePlugin.PieReflectionEffect = function() {
	this.blurEnabled = true;
	this.maskOpacity = 0.35;
	this.reflectLength=0.4;
};
/*
 * Pie Reflection Effect methods
 */
JenScript.PiePlugin.PieReflectionEffect.prototype =  {
		
	setBlurEnable : function(blurEnabled) {
		this.blurEnabled = blurEnabled;
	},
	
	getBlurEnable : function() {
		return this.offsetRadius;
	},
	
	setMaskOpacity : function(maskOpacity) {
		this.maskOpacity = maskOpacity;
	},
	
	getMaskOpacity : function() {
		return this.maskOpacity;
	},
	
	setReflectLength : function(reflectLength) {
		this.reflectLength = reflectLength;
	},
	
	getReflectLength : function() {
		return this.reflectLength;
	},
	
	getX2D : function(){
		var x2dBuilder = JenScript.Core.x2dBuilder;
		var x2dLabel =  x2dBuilder.openType('effect','PieReflectionEffect')+
						x2dBuilder.createElement('blur-enable',this.blurEnabled)+
						x2dBuilder.createElement('mask-opacity',this.maskOpacity)+
						x2dBuilder.createElement('reflect-length',this.reflectLength)+
						x2dBuilder.closeElement('effect');
		
		return x2dLabel;
	}
};

JenScript.PiePlugin.PieCompoundEffect = function() {
	this.fxs = [];
};

/*
 * Pie Compound effects
 */
JenScript.PiePlugin.PieCompoundEffect.prototype = {
		/**
		 * add effect to this compound
		 * @param {JenScript.Pie.AbstractPieEffect} pie
		 */
		addEffect : function(fx){
			this.fxs[this.fxs.length] = fx;
		},
		
		getX2D : function(){
			var x2dBuilder = JenScript.Core.x2dBuilder;
			var x2dCompound =  x2dBuilder.openType('effect','PieCompoundEffect');			
			
			for(var i=0; i<this.fxs.length; i++) {
				var p = this.fxs[i];
				x2dCompound = x2dCompound + p.getX2D();
			}
			x2dCompound = x2dCompound+x2dBuilder.closeElement('effect');
			
			return x2dCompound;
		}
};

/**
 * Pie Slice
 * @param {int} the slice value
 */
JenScript.PiePlugin.Slice = function(value,themeColor) {	
	this.name = 'slice';	
	this.value = value;
	this.themeColor = themeColor;	
	this.divergence = 0;
	this.sliceLabel;
	this.percent;
};

/*
 * Pie Slice methods
 */
JenScript.PiePlugin.Slice.prototype = {
		
	setName : function(name) {
		name = name;
	},

	getName : function() {
		return this.name;
	},
	
	setValue : function(value) {
		this.value = value;
	},

	getValue : function() {
		return this.value;
	},
	

	setPercent : function(percent) {
		this.percent = percent;
	},

	getPercent : function() {
		return this.percent;
	},
	
	setSliceLabel : function(sliceLabel) {
		this.sliceLabel = sliceLabel;
	},

	getSliceLabel : function() {
		return this.sliceLabel;
	},
	
	setThemeColor : function(themeColor) {
		this.themeColor = themeColor;
	},
	
	getThemeColor : function() {
		return this.themeColor;
	},
	
	setDivergence : function(divergence) {
		this.divergence = divergence;
	},

	getDivergence : function() {
		return this.divergence;
	},
	
	getX2D : function (){
		var x2dBuilder = JenScript.Core.x2dBuilder;
		var	x2dSlice =  x2dBuilder.openElement('slice')+
						x2dBuilder.createElement('name',this.name)+
						x2dBuilder.createElement('value',this.value)+
						x2dBuilder.createElement('divergence',this.divergence)+
						x2dBuilder.getX2D('slice-color',this.themeColor)+						
						x2dBuilder.getX2D(undefined,this.sliceLabel)+
						x2dBuilder.closeElement('slice');
		return x2dSlice;
	}
		
};


/**
 * Pie plugin
 */
JenScript.PiePlugin.Plugin = function() {
	this.pies = [];
	JenScript.AbstractPlugin.apply(this,[]);
	this.setClassName('com.jensoft.sw2d.core.plugin.pie.PiePlugin');
}

/*
 * PiePlugin methods
 */
JenScript.PiePlugin.Plugin.prototype = { 
		
		/**
		 * paint the outline in canvas
		 */
		paint : function(g2d,windowPart){
			if(windowPart == JenScript.Window2D.Part.Device2D){
				//alert("paint pie plugin in device");
				for(var i=0; i<this.pies.length; i++) {
					var pie = this.pies[i];
					pie.build(g2d);
				}
			}
		},
		
		/**
		 * add a pie to this pie plugin
		 * @param {JenScript.Pie.Pie} pie
		 */
		addPie : function(pie){
			pie.host = this;
			this.pies[this.pies.length] = pie;
		},
        
		/**
		 * get all pies that have been registered in this plugin
		 */
		getPies : function() {
			return this.pies;
		},
		
		getX2DPies : function(){
			var x2dPies ='';
			for(var i=0; i<this.pies.length; i++) {
				var p = this.pies[i];
				x2dPies = x2dPies + p.getX2D();
			}
			return x2dPies;
		},
	
		/**
		 * Get Pie Plugin X2D
		 */
		getX2D : function(){
			var x2dBuilder = JenScript.Core.x2dBuilder;
			var pluginXML = x2dBuilder.openType('plugin','PiePlugin')+
							x2dBuilder.createElement('id',this.id)+
							x2dBuilder.createElement('name',this.name)+
							x2dBuilder.createElement('class',this.className)+				
							this.getX2DPies()+
							x2dBuilder.closeElement('plugin');
			return pluginXML;
	}
}
JenScript.Core.extend(JenScript.PiePlugin.Plugin, JenScript.AbstractPlugin);

/**
 * Pie
 */
JenScript.PiePlugin.Pie = function() {	
	this.name='pie';
	this.x=0;
	this.y=0;
	this.radius = 60;
	this.startAngleDegree = 0;
	this.nature='user';
	this.effect= new JenScript.PiePlugin.PieLinearEffect();	
	this.slices = [];
	this.host;
};

/*
 * Pie methods 
 */
JenScript.PiePlugin.Pie.prototype = {
	
	setName : function(name) {
		this.name = name;
	},

	getName : function() {
		return this.name;
	},
	
	
	setX : function(x) {
		this.x = x;
	},

	getX : function() {
		return this.x;
	},
	
	setY : function(y) {
		this.y = y;
	},

	getY : function() {
		return this.y;
	},
	
	setRadius : function(radius) {
		this.radius = radius;
	},

	getRadius : function() {
		return this.radius;
	},
	
	setNatue : function(nature) {
		this.nature = nature;
	},

	getNature : function() {
		return this.nature;
	},

	setStartAngleDegree : function(startAngleDegree) {
		this.startAngleDegree = startAngleDegree;
	},

	getStartAngleDegree : function() {
		return this.startAngleDegree;
	},

	setEffect : function(effect) {
		this.effect = effect;
	},

	getEffect : function() {
		return this.effect;
	},
		
	addSlice : function(slice){
		this.slices[this.slices.length] = slice;
	},
	
	/**
	 * build slice
	 * 
	 * @param pieslice
	 *            the pie slice to build
	 */
	buildSlice : function (g2d,slice) {
		//alert("build slice ");
		var deltaDegree = slice.getPercent() * 360;

		alert("start degree : "+this.startAngleDegree +" delta degree : "+deltaDegree);
		
		if (this.startAngleDegree > 360)
			this.startAngleDegree = this.startAngleDegree - 360;

		var medianDegree = this.startAngleDegree + deltaDegree / 2;
		if (medianDegree > 360)
			medianDegree = medianDegree - 360;

//		Point2D c = null;
//		if (pieNature == PieNature.PieUser) {
//			c = getHostPlugin().getWindow2D().userToPixel(
//					new Point2D.Double(centerX, centerY));
//		}
//		if (pieNature == PieNature.PieDevice) {
//			c = new Point2D.Double(centerX, centerY);
//		}
		//alert("center x : "+this.centerX);
		var c = this.host.getWindow2D().userToPixel({x:this.x,y:this.y});
		
		//alert("c : "+c);
		//context.arc(x, y, radius, startAngle, endAngle, antiClockwise);
		g2d.beginPath();
		g2d.arc(c.x,c.y,this.radius,this.startAngleDegree*Math.PI/180,(this.startAngleDegree + deltaDegree)*Math.PI/180,false);
		g2d.lineTo(c.x,c.y);
		g2d.closePath();
		g2d.fillStyle = slice.themeColor.toStringRGBA();
		g2d.fill();
		
		path = new Path();
		alert("path : "+path);
		
		
//		double sliceCenterX = c.getX() + slice.getDivergence()
//				* Math.cos(Math.toRadians(medianDegree));
//		double sliceCenterY = c.getY() - slice.getDivergence()
//				* Math.sin(Math.toRadians(medianDegree));

//		double cornerExternalX = sliceCenterX - radius;
//		double cornerExternalY = sliceCenterY - radius;
//		Arc2D piePath = new Arc2D.Double(cornerExternalX, cornerExternalY,
//				2 * radius, 2 * radius, startAngleDegree, deltaDegree,
//				Arc2D.PIE);
//
//		slice.setSlicePath(new GeneralPath(piePath));
//
//		Arc2D externalPath = new Arc2D.Double(cornerExternalX, cornerExternalY,
//				2 * radius, 2 * radius, startAngleDegree, deltaDegree,
//				Arc2D.OPEN);
//
//		slice.setExternalArc(externalPath);
//
//		Line2D lineStart = new Line2D.Double(new Point2D.Double(sliceCenterX,
//				sliceCenterY), externalPath.getStartPoint());
//		Line2D lineEnd = new Line2D.Double(externalPath.getEndPoint(),
//				new Point2D.Double(sliceCenterX, sliceCenterY));
//		slice.setLineStart(lineStart);
//		slice.setLineEnd(lineEnd);
//
//		slice.setStartAngleDegree(startAngleDegree);
//		slice.setEndAngleDegree(startAngleDegree + deltaDegree);
		this.startAngleDegree = this.startAngleDegree + deltaDegree;
	},
	
	/**
	 * build pie
	 */
	build : function(g2d){
		this.makeSum();
		for (var i = 0; i < this.slices.length; i++) {
			var s = this.slices[i];
			this.buildSlice(g2d,s);
		}
	},
	
	/**
	 * make normalization of slice value
	 */
	makeSum : function(){
		var sum = 0;
		for (var i = 0; i < this.slices.length; i++) {
			var s = this.slices[i];
			sum = sum + s.getValue();
		}

		for (var i = 0; i < this.slices.length; i++) {
			var s = this.slices[i];
			var percent = s.getValue() / sum;
			s.setPercent(percent);
		}
	},
	
	/**
	 * Get Pie as X2D XML 
	 */
	getX2DSlices : function(){
		var x2dSlices = '';
		for(var i=0; i<this.slices.length; i++) {
			var s = this.slices[i];
			s.setName('slice'+i);				
			x2dSlices = x2dSlices + s.getX2D();
		}
		return x2dSlices;
	},

	/**
	 * Get Pie as X2D XML 
	 */
	getX2D : function(){
		var x2dBuilder = JenScript.Core.x2dBuilder;
		
		var pluginXML = x2dBuilder.openElement('pie') +
						x2dBuilder.createElement('name',this.name) +
						x2dBuilder.createElement('x',this.x) +
						x2dBuilder.createElement('y',this.y) +
						x2dBuilder.createElement('radius',this.radius) + 
						x2dBuilder.createElement('start-angle',this.startAngleDegree) +
						x2dBuilder.createElement('nature',this.nature) +
						x2dBuilder.getX2D(undefined,this.effect)+
						this.getX2DSlices()+
						x2dBuilder.closeElement('pie');
		return pluginXML;							
	}

};