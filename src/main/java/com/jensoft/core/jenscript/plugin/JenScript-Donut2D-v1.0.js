/**
 * JenScript Donut2DPlugin Namespace
 * @namespace
 */
JenScript.Donut2DPlugin = {};



/**
 * Donut2D Abstract Fill 
 */
JenScript.Donut2DPlugin.AbstractDonut2DFill = function() {
};

/**
 * Donut2D Default Fill
 */
JenScript.Donut2DPlugin.Donut2DDefaultFill = function() {	
	JenScript.Donut2DPlugin.AbstractDonut2DFill.apply(this,[]);
};

JenScript.Donut2DPlugin.Donut2DDefaultFill.prototype =  {
	
		/**
		 * get donut2D default fill as X2D XML
		 */
		getX2D : function(){
			var x2dBuilder = JenScript.Core.x2dBuilder;
			var	x2dLabel =  x2dBuilder.openType('fill','Donut2DDefaultFill')+
							x2dBuilder.closeElement('fill');
			return x2dLabel;
		}
};
//extends AbstractDonut2DFill
JenScript.Core.extend(JenScript.Donut2DPlugin.Donut2DDefaultFill, JenScript.Donut2DPlugin.AbstractDonut2DFill);

/**
 * Donut2D Radial Fill
 */
JenScript.Donut2DPlugin.Donut2DRadialFill = function(gradientType) {	
	this.gradientType=gradientType;//B_D_B,B_SC_B,D_SC_D,D_B_D,SC_B_SC,SC_D_SC
	JenScript.Donut2DPlugin.AbstractDonut2DFill.apply(this,[]);
};

JenScript.Donut2DPlugin.Donut2DRadialFill.prototype =  {
		
		setGradientType : function(gradientType) {
			this.gradientType = gradientType;
		},
		
		getGradientType : function() {
			return this.gradientType;
		},
		
		/**
		 * get donut2D radial fill as X2D XML
		 */
		getX2D : function(){
			var x2dBuilder = JenScript.Core.x2dBuilder;
			var	x2dLabel =  x2dBuilder.openType('fill','Donut2DRadialFill')+
							x2dBuilder.createElement('gradient-type',this.gradientType)+
							x2dBuilder.closeElement('fill');
			return x2dLabel;
		}
};
//extends AbstractDonut2DFill
JenScript.Core.extend(JenScript.Donut2DPlugin.Donut2DRadialFill, JenScript.Donut2DPlugin.AbstractDonut2DFill);

/**
 * Donut2D Abstract Label 
 */
JenScript.Donut2DPlugin.AbstractLabel = function() {
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
 * Abstract Donut2D Label method
 */
JenScript.Donut2DPlugin.AbstractLabel.prototype =  {
                                          
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
 * Donut2D Path Label
 */
JenScript.Donut2DPlugin.Donut2DPathLabel = function(text) {	
	this.pathSide='above';//above, over, below;
	this.textPosition='left';//left, middle, right;
	this.divergence=10;
	this.textShader;
	this.pathName='arc';//arc,start,end
	JenScript.Donut2DPlugin.AbstractLabel.apply(this,[]);
};

/*
 * Path Label methods
 */
JenScript.Donut2DPlugin.Donut2DPathLabel.prototype = {
	
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
	 * get donut2D path label as X2D XML
	 */
	getX2D : function(){
		var x2dBuilder = JenScript.Core.x2dBuilder;
		var	x2dLabel =  x2dBuilder.openType('label','Donut2DPathLabel')+
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
JenScript.Core.extend(JenScript.Donut2DPlugin.Donut2DPathLabel, JenScript.Donut2DPlugin.AbstractLabel);
/**
 * Donut2DBorderLabel
 */
JenScript.Donut2DPlugin.Donut2DBorderLabel = function(text) {
	
	this.margin = 40;
	this.linkEnable=true;
	this.linkStyle='quad';
	this.linkColor = new JenScript.Color(0,0,0,255);
	this.linkStroke= new JenScript.Stroke(1);
	this.linkExtends = 20;
	
	this.linkMarkerEnable;
	this.linkMarkerDrawColor;
	this.linkMarkerFillColor;
	
	JenScript.Donut2DPlugin.AbstractLabel.apply(this,[]);
};

/*
 * Donut2D Border Label Methods
 */
JenScript.Donut2DPlugin.Donut2DBorderLabel.prototype = {
			
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
		var	x2dLabel =  x2dBuilder.openType('label','Donut2DBorderLabel')+
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
JenScript.Core.extend(JenScript.Donut2DPlugin.Donut2DBorderLabel, JenScript.Donut2DPlugin.AbstractLabel);
/**
 * Donut2D radial Label
 */
JenScript.Donut2DPlugin.Donut2DRadialLabel = function() {
	this.offsetRadius = 30;
	JenScript.Donut2DPlugin.AbstractLabel.apply(this,[]);
};

/*
 * Donut2D radial label methods
 */
JenScript.Donut2DPlugin.Donut2DRadialLabel.prototype = {
		
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
		var	x2dLabel =  x2dBuilder.openType('label','Donut2DRadialLabel')+
						this.getAbstractX2D()+
						x2dBuilder.createElement('offset-radius',this.offsetRadius)+
						x2dBuilder.closeElement('label');
		return x2dLabel;
	}
};

//extends AbstractLabel
JenScript.Core.extend(JenScript.Donut2DPlugin.Donut2DRadialLabel, JenScript.Donut2DPlugin.AbstractLabel);

JenScript.Donut2DPlugin.Donut2DLinearEffect = function() {	
	this.incidence = 120;
	this.offset = 3;
	this.shader;
};


JenScript.Donut2DPlugin.Donut2DLinearEffect.prototype =   {
	
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
		var x2dLabel =  x2dBuilder.openType('effect','Donut2DLinearEffect')+
						x2dBuilder.createElement('incidence-angle',this.incidence)+
						x2dBuilder.createElement('offset-radius',this.offset)+
						x2dBuilder.getX2D('shader',this.shader)+
						x2dBuilder.closeElement('effect');
		
		return x2dLabel;
	}
};



/**
 * Create default Donut2D reflection effect
 */
JenScript.Donut2DPlugin.Donut2DReflectionEffect = function() {
	this.blurEnabled = true;
	this.maskOpacity = 0.35;
	this.reflectLength=0.4;
};
/*
 * Donut2D Reflection Effect methods
 */
JenScript.Donut2DPlugin.Donut2DReflectionEffect.prototype =  {
		
	setBlurEnable : function(blurEnabled) {
		this.blurEnabled = blurEnabled;
	},
	
	getBlurEnable : function() {
		return this.blurEnabled;
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
		var x2dLabel =  x2dBuilder.openType('effect','Donut2DReflectionEffect')+
						x2dBuilder.createElement('blur-enable',this.blurEnabled)+
						x2dBuilder.createElement('mask-opacity',this.maskOpacity)+
						x2dBuilder.createElement('reflect-length',this.reflectLength)+
						x2dBuilder.closeElement('effect');
		
		return x2dLabel;
	}
};

JenScript.Donut2DPlugin.Donut2DCompoundEffect = function() {
	this.fxs = [];
};

/*
 * Donut2D Compound effects
 */
JenScript.Donut2DPlugin.Donut2DCompoundEffect.prototype = {
		/**
		 * add effect to this compound
		 * @param {JenScript.Donut2DPlugin.AbstractDonut2DEffect} fx
		 */
		addEffect : function(fx){
			this.fxs[this.fxs.length] = fx;
		},
		
		getX2D : function(){
			var x2dBuilder = JenScript.Core.x2dBuilder;
			var x2dCompound =  x2dBuilder.openType('effect','Donut2DCompoundEffect');			
			
			for(var i=0; i<this.fxs.length; i++) {
				var p = this.fxs[i];
				x2dCompound = x2dCompound + p.getX2D();
			}
			x2dCompound = x2dCompound+x2dBuilder.closeElement('effect');
			
			return x2dCompound;
		}
};

/**
 * Donut2D Slice
 * @param {int} the slice value
 */
JenScript.Donut2DPlugin.Slice = function(value,themeColor) {	
	this.name = 'slice';	
	this.value = value;
	this.themeColor = themeColor;	
	this.divergence = 0;
	this.sliceLabel;
	this.percent;
};

/*
 * Donut2D Slice methods
 */
JenScript.Donut2DPlugin.Slice.prototype = {
		
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
 * Donut2D plugin
 */
JenScript.Donut2DPlugin.Plugin = function() {
	this.donut2Ds = [];
	JenScript.AbstractPlugin.apply(this,[]);
	this.setClassName('com.jensoft.sw2d.core.plugin.donut2d.Donut2DPlugin');
}

/*
 * Donut2DPlugin methods
 */
JenScript.Donut2DPlugin.Plugin.prototype = { 
		
		/**
		 * paint the donut2Ds in canvas
		 */
		paint : function(g2d,windowPart){
			if(windowPart == JenScript.Window2D.Part.Device2D){
				for(var i=0; i<this.donut2Ds.length; i++) {
					var donut2D = this.donut2Ds[i];
					donut2D.build(g2d);
				}
			}
		},
		
		/**
		 * add a donut2D to this donut2D plugin
		 * @param {JenScript.Donut2DPlugin.Donut2D} donut2D
		 */
		addDonut2D : function(donut2D){
			donut2D.host = this;
			this.donut2Ds[this.donut2Ds.length] = donut2D;
		},
        
		/**
		 * get all donut2D that have been registered in this plugin
		 */
		getDonut2Ds : function() {
			return this.donut2Ds;
		},
		
		getX2DDonut2Ds : function(){
			var x2dDonuts ='';
			for(var i=0; i<this.donut2Ds.length; i++) {
				var d = this.donut2Ds[i];
				x2dDonuts = x2dDonuts + d.getX2D();
			}
			return x2dDonuts;
		},
	
		/**
		 * Get Donut2D Plugin as XML X2D
		 */
		getX2D : function(){
			var x2dBuilder = JenScript.Core.x2dBuilder;
			var pluginXML = x2dBuilder.openType('plugin','Donut2DPlugin')+
							x2dBuilder.createElement('id',this.id)+
							x2dBuilder.createElement('name',this.name)+
							x2dBuilder.createElement('class',this.className)+				
							this.getX2DDonut2Ds()+
							x2dBuilder.closeElement('plugin');
			return pluginXML;
	}
}
JenScript.Core.extend(JenScript.Donut2DPlugin.Plugin, JenScript.AbstractPlugin);

/**
 * Donut2D
 */
JenScript.Donut2DPlugin.Donut2D = function() {	
	this.name='donut2D';
	this.x=0;
	this.y=0;
	this.innerRadius = 30;
	this.outerRadius = 60;
	this.startAngleDegree = 0;
	this.nature='user';
	this.draw;
	this.fill;
	this.effect;
	this.slices = [];
	this.host;
};

/*
 * Donut2D methods 
 */
JenScript.Donut2DPlugin.Donut2D.prototype = {
	
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
	
	setInnerRadius : function(innerRadius) {
		this.innerRadius = innerRadius;
	},

	getInnerRadius : function() {
		return this.innerRadius;
	},
	
	setOuterRadius : function(outerRadius) {
		this.outerRadius = outerRadius;
	},

	getOuterRadius : function() {
		return this.outerRadius;
	},
	
	setNature : function(nature) {
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
	
	setDraw : function(draw) {
		this.draw = draw;
	},

	getDraw : function() {
		return this.draw;
	},

	setFill : function(fill) {
		this.fill = fill;
	},

	getFill : function() {
		return this.fill;
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
	 * Get All Donut2D Slices as X2D XML 
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
	 * Get Donut2D as X2D XML 
	 */
	getX2D : function(){
		var x2dBuilder = JenScript.Core.x2dBuilder;
		
		var pluginXML = x2dBuilder.openElement('donut2d') +
						x2dBuilder.createElement('name',this.name) +
						x2dBuilder.createElement('x',this.x) +
						x2dBuilder.createElement('y',this.y) +
						x2dBuilder.createElement('inner-radius',this.innerRadius) +
						x2dBuilder.createElement('outer-radius',this.outerRadius) + 
						x2dBuilder.createElement('start-angle',this.startAngleDegree) +
						x2dBuilder.createElement('nature',this.nature) +
						x2dBuilder.getX2D(undefined,this.fill)+
						x2dBuilder.getX2D(undefined,this.effect)+
						this.getX2DSlices()+
						x2dBuilder.closeElement('donut2d');
		return pluginXML;							
	}

};