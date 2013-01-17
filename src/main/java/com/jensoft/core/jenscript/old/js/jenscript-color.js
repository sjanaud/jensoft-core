
com.jensoft.jenscript.ColorUtil = function() {

	this.rgbToHex = function(r,g,b) {
		return toHex(r)+toHex(g)+toHex(b);
		valueOf = function(){
			return valueOf = function(){
				return toHex(r)+toHex(g)+toHex(b);
			};
		};
	};

	this.format = function(color) {
		if(typeof color == 'undefined')
			return 'undefined';
		return color.getR()+'.'+color.getG()+'.'+color.getB()+'.'+color.getA();
	};

	function toHex(n) {
		 n = parseInt(n,10);
		 if (isNaN(n)) return "00";
		 n = Math.max(0,Math.min(n,255));
		 return "0123456789ABCDEF".charAt((n-n%16)/16)
		      + "0123456789ABCDEF".charAt(n%16);
	};


	this.hexToR = function(h) {
		return parseInt((cutHex(h)).substring(0,2),16);
		valueOf = function(){
			return valueOf = function(){
				return parseInt((cutHex(h)).substring(0,2),16);
			};
		};
	};


	this.hexToG = function(h) {
		return parseInt((cutHex(h)).substring(2,4),16);
		valueOf = function(){
			return valueOf = function(){
				return parseInt((cutHex(h)).substring(2,4),16);
			};
		};
	};



	this.hexToB = function(h) {
		return parseInt((cutHex(h)).substring(4,6),16);
		valueOf = function(){
			return valueOf = function(){
				return parseInt((cutHex(h)).substring(4,6),16);
			};
		};
	};


	function cutHex(h) {
		return (h.charAt(0)=="#") ? h.substring(1,7):h;
	};

	this.getHexColor = function(ColorName) {
		var ColorValue = " ";
	    if (ColorName == 'aliceblue')       ColorValue = "#F0F8FF";
	    if (ColorName == 'antiquewhite')  ColorValue = "#FAEBD7";
	    if (ColorName == 'aqua')       ColorValue = "#00FFFF";
	    if (ColorName == 'aquamarine')  ColorValue = "#7FFFD4";
	    if (ColorName == 'azure')  ColorValue = "#F0FFFF";
	    if (ColorName == 'beige')  ColorValue = "#F5F5DC";
	    if (ColorName == 'bisque')  ColorValue = "#FFE4C4";
	    if (ColorName == 'black')  ColorValue = "#000000";
	    if (ColorName == 'blanchedalmond')  ColorValue = "#FFEBCD";
	    if (ColorName == 'blue')  ColorValue = "#0000FF";
	    if (ColorName == 'blueviolet')  ColorValue = "#8A2BE2";
	    if (ColorName == 'brown')  ColorValue = "#A52A2A";
	    if (ColorName == 'burlywood')  ColorValue = "#DEB887";
	    if (ColorName == 'cadetblue')  ColorValue = "#5F9EA0";
	    if (ColorName == 'chartreuse')  ColorValue = "#7FFF00";
	    if (ColorName == 'chocolate')  ColorValue = "#D2691E";
	    if (ColorName == 'coral')  ColorValue = "#FF7F50";
	    if (ColorName == 'cornflowerblue')  ColorValue = "#6495ED";
	    if (ColorName == 'cornsilk')  ColorValue = "#FFF8DC";
	    if (ColorName == 'crimson')  ColorValue = "#DC143C";
	    if (ColorName == 'cyan')  ColorValue = "#00FFFF";
	    if (ColorName == 'darkblue')  ColorValue = "#00008B";
	    if (ColorName == 'darkcyan')  ColorValue = "#008B8B";
	    if (ColorName == 'darkgoldenr0d')  ColorValue = "#B8860B";
	    if (ColorName == 'darkgray')  ColorValue = "#A9A9A9";
	    if (ColorName == 'darkgreen')  ColorValue = "#006400";
	    if (ColorName == 'darkkhaki')  ColorValue = "#BDB76B";
	    if (ColorName == 'darkmagenta')  ColorValue = "#8B008B";
	    if (ColorName == 'darkolivegreen')  ColorValue = "#556B2F";
	    if (ColorName == 'darkorange')  ColorValue = "#FF8C00";
	    if (ColorName == 'darkorchid')  ColorValue = "#9932CC";
	    if (ColorName == 'darkred')  ColorValue = "#8B0000";
	    if (ColorName == 'darksalmon')  ColorValue = "#E9967A";
	    if (ColorName == 'darkseagreen')  ColorValue = "#8FBC8F";
	    if (ColorName == 'darkslateblue')  ColorValue = "#483D8B";
	    if (ColorName == 'darkslategray')  ColorValue = "#2F4F4F";
	    if (ColorName == 'darkturquoise')  ColorValue = "#00CED1";
	    if (ColorName == 'darkviolet')  ColorValue = "#9400D3";
	    if (ColorName == 'deeppink')  ColorValue = "#FF1493";
	    if (ColorName == 'deepskyblue')  ColorValue = "#00BFFF";
	    if (ColorName == 'dimgray')  ColorValue = "#696969";
	    if (ColorName == 'dodgerblue')  ColorValue = "#1E90FF";
	    if (ColorName == 'firebrick')  ColorValue = "#B22222";
	    if (ColorName == 'floralwhite')  ColorValue = "#FFFAF0";
	    if (ColorName == 'forestgreen')  ColorValue = "#228B22";
	    if (ColorName == 'fuchsia')  ColorValue = "#FF00FF";
	    if (ColorName == 'gainsboro')  ColorValue = "#DCDCDC";
	    if (ColorName == 'ghostwhite')  ColorValue = "#F8F8FF";
	    if (ColorName == 'gold')  ColorValue = "#FFD700";
	    if (ColorName == 'goldenrod')  ColorValue = "#DAA520";
	    if (ColorName == 'gray')  ColorValue = "#808080";
	    if (ColorName == 'green')  ColorValue = "#008000";
	    if (ColorName == 'greenyellow')  ColorValue = "#ADFF2F";
	    if (ColorName == 'honeydew')  ColorValue = "#F0FFF0";
	    if (ColorName == 'hotpink')  ColorValue = "#FF69B4";
	    if (ColorName == 'indianred')  ColorValue = "#CD5C5C";
	    if (ColorName == 'indigo')  ColorValue = "#4B0082";
	    if (ColorName == 'ivory')  ColorValue = "#FFFFF0";
	    if (ColorName == 'khaki')  ColorValue = "#F0E68C";
	    if (ColorName == 'lavender')  ColorValue = "#E6E6FA";
	    if (ColorName == 'lavenderblush')  ColorValue = "#FFF0F5";
	    if (ColorName == 'lawngreen')  ColorValue = "#7CFC00";
	    if (ColorName == 'lemonchiffon')  ColorValue = "#FFFACD";
	    if (ColorName == 'lightblue')  ColorValue = "#ADD8E6";
	    if (ColorName == 'lightcoral')  ColorValue = "#F08080";
	    if (ColorName == 'lightcyan')  ColorValue = "#E0FFFF";
	    if (ColorName == 'lightgoldenrodyellow')  ColorValue = "#FAFAD2";
	    if (ColorName == 'lightgreen')  ColorValue = "#90EE90";
	    if (ColorName == 'lightgrey')  ColorValue = "#D3D3D3";
	    if (ColorName == 'lightpink')  ColorValue = "#FFB6C1";
	    if (ColorName == 'lightsalmon')  ColorValue = "#FFA07A";
	    if (ColorName == 'lightseagreen')  ColorValue = "#20B2AA";
	    if (ColorName == 'lightskyblue')  ColorValue = "#87CEFA";
	    if (ColorName == 'lightslategray')  ColorValue = "#778899";
	    if (ColorName == 'lightsteelblue')  ColorValue = "#B0C4DE";
	    if (ColorName == 'lightyellow')  ColorValue = "#FFFFE0";
	    if (ColorName == 'lime')  ColorValue = "#00FF00";
	    if (ColorName == 'limegreen')  ColorValue = "#32CD32";
	    if (ColorName == 'linen')  ColorValue = "#FAF0E6";
	    if (ColorName == 'magenta')  ColorValue = "#FF00FF";
	    if (ColorName == 'maroon')  ColorValue = "#800000";
	    if (ColorName == 'mediumaquamarine')  ColorValue = "#66CDAA";
	    if (ColorName == 'mediumblue')  ColorValue = "#0000CD";
	    if (ColorName == 'mediumorchid')  ColorValue = "#BA55D3";
	    if (ColorName == 'mediumpurple')  ColorValue = "#9370DB";
	    if (ColorName == 'mediumseagreen')  ColorValue = "#3CB371";
	    if (ColorName == 'mediumslateblue')  ColorValue = "#7B68EE";
	    if (ColorName == 'mediumspringgreen')  ColorValue = "#00FA9A";
	    if (ColorName == 'mediumturquoise')  ColorValue = "#48D1CC";
	    if (ColorName == 'mediumvioletred')  ColorValue = "#C71585";
	    if (ColorName == 'midnightblue')  ColorValue = "#191970";
	    if (ColorName == 'mintcream')  ColorValue = "#F5FFFA";
	    if (ColorName == 'mistyrose')  ColorValue = "#FFE4E1";
	    if (ColorName == 'moccasin')  ColorValue = "#FFE4B5";
	    if (ColorName == 'navajowhite')  ColorValue = "#FFDEAD";
	    if (ColorName == 'navy')  ColorValue = "#000080";
	    if (ColorName == 'oldlace')  ColorValue = "#FDF5E6";
	    if (ColorName == 'olive')  ColorValue = "#808000";
	    if (ColorName == 'olivedrab')  ColorValue = "#6B8E23";
	    if (ColorName == 'orange')  ColorValue = "#FFA500";
	    if (ColorName == 'orangered')  ColorValue = "#FF4500";
	    if (ColorName == 'orchid')  ColorValue = "#DA70D6";
	    if (ColorName == 'palegoldenrod')  ColorValue = "#EEE8AA";
	    if (ColorName == 'palegreen')  ColorValue = "#98FB98";
	    if (ColorName == 'paleturquoise')  ColorValue = "#AFEEEE";
	    if (ColorName == 'palevioletred')  ColorValue = "#DB7093";
	    if (ColorName == 'papayawhip')  ColorValue = "#FFEFD5";
	    if (ColorName == 'peachpuff')  ColorValue = "#FFDAB9";
	    if (ColorName == 'peru')  ColorValue = "#CD853F";
	    if (ColorName == 'pink')  ColorValue = "#FFC0CB";
	    if (ColorName == 'plum')  ColorValue = "#DDA0DD";
	    if (ColorName == 'powderblue')  ColorValue = "#B0E0E6";
	    if (ColorName == 'purple')  ColorValue = "#800080";
	    if (ColorName == 'red')  ColorValue = "#FF0000";
	    if (ColorName == 'rosybrown')  ColorValue = "#BC8F8F";
	    if (ColorName == 'royalblue')  ColorValue = "#4169E1";
	    if (ColorName == 'saddlebrown')  ColorValue = "#8B4513";
	    if (ColorName == 'salmon')  ColorValue = "#FA8072";
	    if (ColorName == 'sandybrown')  ColorValue = "#F4A460";
	    if (ColorName == 'seagreen')  ColorValue = "#2E8B57";
	    if (ColorName == 'seashell')  ColorValue = "#FFF5EE";
	    if (ColorName == 'sienna')  ColorValue = "#A0522D";
	    if (ColorName == 'silver')  ColorValue = "#C0C0C0";
	    if (ColorName == 'skyblue')  ColorValue = "#87CEEB";
	    if (ColorName == 'slateblue')  ColorValue = "#6A5ACD";
	    if (ColorName == 'slategray')  ColorValue = "#708090";
	    if (ColorName == 'snow')  ColorValue = "#FFFAFA";
	    if (ColorName == 'springgreen')  ColorValue = "#00FF7F";
	    if (ColorName == 'steelblue')  ColorValue = "#4682B4";
	    if (ColorName == 'tan')  ColorValue = "#D2B48C";
	    if (ColorName == 'teal')  ColorValue = "#008080";
	    if (ColorName == 'thistle')  ColorValue = "#D8BFD8";
	    if (ColorName == 'tomato')  ColorValue = "#FF6347";
	    if (ColorName == 'turquoise')  ColorValue = "#40E0D0";
	    if (ColorName == 'violet')  ColorValue = "#EE82EE";
	    if (ColorName == 'wheat')  ColorValue = "#F5DEB3";
	    if (ColorName == 'white')  ColorValue = "#FFFFFF";
	    if (ColorName == 'whitesmoke')  ColorValue = "#F5F5F5";
	    if (ColorName == 'yellow')  ColorValue = "#FFFF00";
	    if (ColorName == 'yellowgreen')  ColorValue = "#9ACD32";
	   
	    return ColorValue;
		valueOf = function(){
			return valueOf = function(){
				return ColorValue;
			};
		};
	};	
	
	this.getColor = function(r,g,b,a) {
		return new com.jensoft.jenscript.Color(r,g,b,a);
	};
};



com.jensoft.jenscript.Color = function(red,green,blue,alpha) {	

	this.r = red;
	this.g = green;
	this.b = blue;
	this.a = alpha;	
	
	this.toString = function() {		
		return +this.getR()+'.'+this.getG()+'.'+this.getB()+'.'+this.getA();
	};
	
	this.getR = function() {
		return this.r;
		valueOf = function(){
			return valueOf = function(){
				return this.r;
			};
		};
	};
	
	this.getG = function() {
		return this.g;
		valueOf = function(){
			return valueOf = function(){
				return this.g;
			};
		};
	};
	
	this.getB = function() {
		return this.b;
		valueOf = function(){
			return valueOf = function(){
				return this.b;
			};
		};
	};
	
	this.getA = function() {
		return this.a;
		valueOf = function(){
			return valueOf = function(){
				return this.a;
			};
		};
	};

	
};