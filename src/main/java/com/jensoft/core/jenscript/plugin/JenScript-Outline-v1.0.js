/**
 * JenScript OutlinePlugin Namespace
 * @namespace
 */
JenScript.OutlinePlugin = {};

/**
 * JenScript Outline Plugin
 * draw rectangle outline around device
 * 
 */
JenScript.OutlinePlugin.Plugin = function() {	
	this.outlineColor;	
	JenScript.AbstractPlugin.apply(this,[]);
	this.setClassName('com.jensoft.sw2d.core.plugin.outline.OutlinePlugin');
};

/*
 * Jenscript outline method
 */
JenScript.OutlinePlugin.Plugin.prototype =  {
          
	/**
	 * set the outline color
	 * @params {Color} the outline color
	 */
	setOutlineColor : function(outlineColor) {
		this.outlineColor = outlineColor;
	},

	/**
	 * get the outline color
	 * @return {Color} the outline color 
	 */
	getOutlineColor : function() {
		return this.outlineColor;
	},
	
	/**
	 * paint the outline in canvas
	 */
	paint : function(g2d,windowPart){
		//alert("OutlinePlugin - paint HTML5 outline.")
		//alert("paint outline part : "+windowPart);
		if(windowPart == JenScript.Window2D.Part.Device2D){
		var w2d = this.getWindow2D();
		
		var up1 = {x:w2d.minX,y:w2d.minY};
		var up2 = {x:w2d.minX,y:w2d.maxY};
		var up3 = {x:w2d.maxX,y:w2d.maxY};
		var up4 = {x:w2d.maxX,y:w2d.minY};
		
		var dp1 = w2d.userToPixel(up1);
		var dp2 = w2d.userToPixel(up2);
		var dp3 = w2d.userToPixel(up3);
		var dp4 = w2d.userToPixel(up4);
		
		g2d.beginPath();
		g2d.moveTo(dp1.x, dp1.y);
		g2d.lineTo(dp2.x, dp2.y);
		g2d.lineTo(dp3.x, dp3.y);
		g2d.lineTo(dp4.x, dp4.y);
		g2d.closePath();
		g2d.lineWidth = 1.2;
		g2d.strokeStyle = "#90EE90";
		g2d.stroke();
		}
		
	},
	
	/**
	 * get the outline plugin as X2D XML 
	 */
	getX2D : function(){
		var x2dBuilder = JenScript.Core.x2dBuilder;
		var x2dOutline = x2dBuilder.openType('plugin','OutlinePlugin')+
						x2dBuilder.createElement('id',this.id)+
						x2dBuilder.createElement('name',this.name)+
						x2dBuilder.createElement('class',this.className)+
						x2dBuilder.getX2D('outline-color',this.outlineColor)+
						x2dBuilder.closeElement('plugin');
		return x2dOutline;
	}
	
};

JenScript.Core.extend(JenScript.OutlinePlugin.Plugin, JenScript.AbstractPlugin);