/**
 * JenScript Donut3DPlugin Namespace
 * @namespace
 */
JenScript.Donut3DPlugin = {};

/**
 * Donut3D Abstract Paint 
 */
JenScript.Donut3DPlugin.AbstractDonut3DPaint = function() {
};


/**
 * Donut3D Default Paint
 */
JenScript.Donut3DPlugin.Donut3DDefaultPaint = function() {	
	this.incidenceEffectAngle=90;
	this.topEffectEnable=true;
	this.innerEffectEnable=true;
	this.outerEffectEnable=true;
	this.sliceFillAlpha=0.7;
	this.topEffectAlpha=0.8;
	this.innerEffectAlpha=1;
	this.outerEffectAlpha=1;
	JenScript.Donut3DPlugin.AbstractDonut3DPaint.apply(this,[]);
};

/*
 * Path Label methods
 */
JenScript.Donut3DPlugin.Donut3DDefaultPaint.prototype = {
		
		/**
		 * get donut3D default paint as X2D XML
		 */
		getX2D : function(){
			var x2dBuilder = JenScript.Core.x2dBuilder;
			var	x2dLabel =  x2dBuilder.openType('paint','Donut3DDefaultPaint')+
							x2dBuilder.createElement('incidence-effect-angle',this.incidenceEffectAngle)+
							x2dBuilder.createElement('top-effect-enable',this.topEffectEnable)+
							x2dBuilder.createElement('inner-effect-enable',this.innerEffectEnable)+
							x2dBuilder.createElement('outer-effect-enable',this.outerEffectEnable)+
							x2dBuilder.createElement('slice-fill-alpha',this.sliceFillAlpha)+
							x2dBuilder.createElement('top-effect-alpha',this.topEffectAlpha)+
							x2dBuilder.createElement('inner-effect-alpha',this.innerEffectAlpha)+
							x2dBuilder.createElement('outer-effect-alpha',this.outerEffectAlpha)+
							x2dBuilder.closeElement('paint');
			return x2dLabel;
		}
};

/**
 * Donut3D Abstract Label 
 */
JenScript.Donut3DPlugin.AbstractLabel = function() {
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
 * Abstract Donut3D Label method
 */
JenScript.Donut3DPlugin.AbstractLabel.prototype =  {
                                          
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
 * Donut3D Path Label
 */
JenScript.Donut3DPlugin.Donut3DPathLabel = function(text) {	
	this.pathSide='above';//above, over, below;
	this.textPosition='left';//left, middle, right;
	this.divergence=10;
	this.textShader;
	this.pathName='arc';//arc,start,end
	JenScript.Donut3DPlugin.AbstractLabel.apply(this,[]);
};

/*
 * Path Label methods
 */
JenScript.Donut3DPlugin.Donut3DPathLabel.prototype = {
	
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
	 * get donut3D path label as X2D XML
	 */
	getX2D : function(){
		var x2dBuilder = JenScript.Core.x2dBuilder;
		var	x2dLabel =  x2dBuilder.openType('label','Donut3DPathLabel')+
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
JenScript.Core.extend(JenScript.Donut3DPlugin.Donut3DPathLabel, JenScript.Donut3DPlugin.AbstractLabel);
/**
 * Donut3DBorderLabel
 */
JenScript.Donut3DPlugin.Donut3DBorderLabel = function(text) {
	
	this.margin = 40;
	this.linkEnable=true;
	this.linkStyle='quad';
	this.linkColor = new JenScript.Color(0,0,0,255);
	this.linkStroke= new JenScript.Stroke(1);
	this.linkExtends = 20;
	
	this.linkMarkerEnable;
	this.linkMarkerDrawColor;
	this.linkMarkerFillColor;
	
	JenScript.Donut3DPlugin.AbstractLabel.apply(this,[]);
};

/*
 * Donut3D Border Label Methods
 */
JenScript.Donut3DPlugin.Donut3DBorderLabel.prototype = {
			
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
		var	x2dLabel =  x2dBuilder.openType('label','Donut3DBorderLabel')+
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
JenScript.Core.extend(JenScript.Donut3DPlugin.Donut3DBorderLabel, JenScript.Donut3DPlugin.AbstractLabel);
/**
 * Donut3D radial Label
 */
JenScript.Donut3DPlugin.Donut3DRadialLabel = function() {
	this.offsetRadius = 30;
	JenScript.Donut3DPlugin.AbstractLabel.apply(this,[]);
};

/*
 * Donut3D radial label methods
 */
JenScript.Donut3DPlugin.Donut3DRadialLabel.prototype = {
		
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
		var	x2dLabel =  x2dBuilder.openType('label','Donut3DRadialLabel')+
						this.getAbstractX2D()+
						x2dBuilder.createElement('offset-radius',this.offsetRadius)+
						x2dBuilder.closeElement('label');
		return x2dLabel;
	}
};

//extends AbstractLabel
JenScript.Core.extend(JenScript.Donut3DPlugin.Donut3DRadialLabel, JenScript.Donut3DPlugin.AbstractLabel);

/**
 * Donut3D Slice
 * @param {int} the slice value
 */
JenScript.Donut3DPlugin.Slice = function(value,themeColor) {	
	this.name = 'slice';	
	this.value = value;
	this.themeColor = themeColor;	
	this.divergence = 0;
	this.sliceLabel;
	this.percent;
};

/*
 * Donut3D Slice methods
 */
JenScript.Donut3DPlugin.Slice.prototype = {
		
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
 * Donut3D plugin
 */
JenScript.Donut3DPlugin.Plugin = function() {
	this.donut3Ds = [];
	JenScript.AbstractPlugin.apply(this,[]);
	this.setClassName('com.jensoft.sw2d.core.plugin.donut3d.Donut3DPlugin');
}

/*
 * Donut3DPlugin methods
 */
JenScript.Donut3DPlugin.Plugin.prototype = { 
		
				
		/**
		 * add a donut3D to this donut3D plugin
		 * @param {JenScript.Donut3DPlugin.Donut3D} donut3D
		 */
		addDonut3D : function(donut3D){
			donut3D.host = this;
			this.donut3Ds[this.donut3Ds.length] = donut3D;
		},
        
		/**
		 * get all donut3D that have been registered in this plugin
		 */
		getDonut3Ds : function() {
			return this.donut3Ds;
		},
		
		getX2DDonut3Ds : function(){
			var x2dDonuts ='';
			for(var i=0; i<this.donut3Ds.length; i++) {
				var d = this.donut3Ds[i];
				x2dDonuts = x2dDonuts + d.getX2D();
			}
			return x2dDonuts;
		},
	
		/**
		 * Get Donut3D Plugin as XML X2D
		 */
		getX2D : function(){
			var x2dBuilder = JenScript.Core.x2dBuilder;
			var pluginXML = x2dBuilder.openType('plugin','Donut3DPlugin')+
							x2dBuilder.createElement('id',this.id)+
							x2dBuilder.createElement('name',this.name)+
							x2dBuilder.createElement('class',this.className)+				
							this.getX2DDonut3Ds()+
							x2dBuilder.closeElement('plugin');
			return pluginXML;
	}
}
JenScript.Core.extend(JenScript.Donut3DPlugin.Plugin, JenScript.AbstractPlugin);

/**
 * Donut3D
 */
JenScript.Donut3DPlugin.Donut3D = function() {	
	this.name='donut3D';
	this.x=0;
	this.y=0;
	this.innerRadius = 30;
	this.outerRadius = 60;
	this.startAngleDegree = 0;
	this.thickness = 60;
	this.tilt = 40;
	this.nature='user';
	this.paint= new JenScript.Donut3DPlugin.Donut3DDefaultPaint();
	this.slices = [];
	this.host;
};

/*
 * Donut3D methods 
 */
JenScript.Donut3DPlugin.Donut3D.prototype = {
	
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
	
	setThickness : function(thickness) {
		this.thickness = thickness;
	},

	getThickness : function() {
		return this.thickness;
	},
	
	setTilt : function(tilt) {
		this.tilt = tilt;
	},

	getTilt : function() {
		return this.tilt;
	},
	
	setPaint : function(paint) {
		this.paint = paint;
	},

	getPaint : function() {
		return this.paint;
	},	
			
	addSlice : function(slice){
		this.slices[this.slices.length] = slice;
	},
	

	
	/**
	 * Get All Donut3D Slices as X2D XML 
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
	 * Get Donut3D as X2D XML 
	 */
	getX2D : function(){
		var x2dBuilder = JenScript.Core.x2dBuilder;
		
		var x2d = x2dBuilder.openElement('donut3d') +
						x2dBuilder.createElement('name',this.name) +
						x2dBuilder.createElement('x',this.x) +
						x2dBuilder.createElement('y',this.y) +
						x2dBuilder.createElement('inner-radius',this.innerRadius) +
						x2dBuilder.createElement('outer-radius',this.outerRadius) +
						x2dBuilder.createElement('thickness',this.thickness) + 
						x2dBuilder.createElement('tilt',this.tilt) + 
						x2dBuilder.createElement('start-angle',this.startAngleDegree) +
						x2dBuilder.createElement('nature',this.nature) +
						x2dBuilder.getX2D(undefined,this.paint)+						
						this.getX2DSlices()+
						x2dBuilder.closeElement('donut3d');
		return x2d;							
	}

};