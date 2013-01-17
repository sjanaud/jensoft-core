///////////////////////////////////////////////////////////////////////
//  JenScript
//  Author Jensoft API sébastien Janaud
///////////////////////////////////////////////////////////////////////
/**
 * JenScript Namespace
 * @namespace
 */
var JenScript = {};

/**
 * JenScript Core
 * @property {Object} Core
 */
JenScript.Core = {
    views: [],
    idCounter: 0,
    x2dNS: 'x2d',
    isX2dNS: false,
    x2dServer:'http://localhost:8888/JenScript',
    tempNodes: [],
    animations: [],
    animIdCounter: 0,
    dragTimeInterval: 0,
    maxDragTimeInterval: 20,
    x2dBuilder : {
    	openViewDocument : function(){	
    		if(JenScript.Core.isX2dNS === true)
    			return '<'+JenScript.Core.x2dNS+':'+'view2d'+' '+'xmlns:'+JenScript.Core.x2dNS+'="http://www.jensoft.org/jensoft/schema/x2d"'+' '+'xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"'+ '>';
    		if(JenScript.Core.isX2dNS === false)
    			return '<'+'view2d'+' '+'xmlns="http://www.jensoft.org/jensoft/schema/x2d"'+' '+'xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"'+ '>';
    	},
    	openElement : function(eName){	
    		if(JenScript.Core.isX2dNS === true)
    			return '<'+JenScript.Core.x2dNS+':'+eName+'>';
    		if(JenScript.Core.isX2dNS === false)
    			return '<'+eName+'>';
    	},
    	openType : function(eName,eType){	
    		if(JenScript.Core.isX2dNS === true)
    			return '<'+JenScript.Core.x2dNS+':'+eName+' xsi:type="'+JenScript.Core.x2dNS+':'+eType+'"'+'>';
    		if(JenScript.Core.isX2dNS === false)
    			return '<'+eName+' xsi:type="'+eType+'"'+'>';
    	},
    	closeElement : function(eName){
    		if(JenScript.Core.isX2dNS === true)
    			return '</'+JenScript.Core.x2dNS+':'+eName+'>';
    		if(JenScript.Core.isX2dNS === false)
    			return '</'+eName+'>';
    	},
    	closeViewDocument : function(){
    		return this.closeElement('view2d');
    	},
    	createElement : function(eName,eValue){
    		if(eValue === undefined)
    			return '';
    		else
    			return this.openElement(eName) + eValue + this.closeElement(eName);
    	},
    	getX2D : function (x2dRootName,x2dEntity){
    		if(x2dEntity !== undefined){
    			if(x2dRootName !== undefined)
    				return x2dEntity.getX2D(x2dRootName);
    			else
    				return x2dEntity.getX2D();
    		}else{
    			return '';
    		}
    	}
    	
    	
    
    },
    frame: {
        time: 0,
        timeDiff: 0,
        lastTime: 0
    },
    drag: {
        moving: false,
        node: undefined,
        offset: {
            x: 0,
            y: 0
        },
        lastDrawTime: 0
    },
    extend: function(obj1, obj2) {
        for(var key in obj2.prototype) {
            if(obj2.prototype.hasOwnProperty(key) && obj1.prototype[key] === undefined) {
                obj1.prototype[key] = obj2.prototype[key];
            }
        }
    },

    _addAnimation: function(anim) {
        anim.id = this.animIdCounter++;
        this.animations.push(anim);
    },
    _removeAnimation: function(anim) {
        var id = anim.id;
        var animations = this.animations;
        for(var n = 0; n < animations.length; n++) {
            if(animations[n].id === id) {
                this.animations.splice(n, 1);
                return false;
            }
        }
    },
    _runFrames: function() {
        var nodes = {};
        for(var n = 0; n < this.animations.length; n++) {
            var anim = this.animations[n];
            if(anim.node && anim.node._id !== undefined) {
                nodes[anim.node._id] = anim.node;
            }
            anim.func(this.frame);
        }

        for(var key in nodes) {
            nodes[key].draw();
        }
    },
    _updateFrameObject: function() {
        var date = new Date();
        var time = date.getTime();
        if(this.frame.lastTime === 0) {
            this.frame.lastTime = time;
        }
        else {
            this.frame.timeDiff = time - this.frame.lastTime;
            this.frame.lastTime = time;
            this.frame.time += this.frame.timeDiff;
        }
    },
    _animationLoop: function() {
        if(this.animations.length > 0) {
            this._updateFrameObject();
            this._runFrames();
            var that = this;
            requestAnimFrame(function() {
                that._animationLoop();
            });
        }
        else {
            this.frame.lastTime = 0;
        }
    },
    _handleAnimation: function() {
        var that = this;
        if(this.animations.length > 0) {
            that._animationLoop();
        }
        else {
            this.frame.lastTime = 0;
        }
    },
    _isElement: function(obj) {
        return !!(obj && obj.nodeType == 1);
    },
    _isFunction: function(obj) {
        return !!(obj && obj.constructor && obj.call && obj.apply);
    },
    _getPoint: function(arg) {

        if(arg.length === 1) {
            return arg[0];
        }
        else {
            return {
                x: arg[0],
                y: arg[1]
            }
        }
    }
};

window.requestAnimFrame = (function(callback) {
    return window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame ||
    function(callback) {
        window.setTimeout(callback, 1000 / 60);
    };
})();


String.prototype.trim = function(){
	return (this.replace(/^[\s\xA0]+/, "").replace(/[\s\xA0]+$/, ""));
};

String.prototype.startsWith = function(str){
	return (this.match("^"+str)==str);
}

String.prototype.endsWith = function(str){
	return (this.match(str+"$")==str);
}
///////////////////////////////////////////////////////////////////////
//ViewBackground
///////////////////////////////////////////////////////////////////////
/**
* ViewBackground constructor.&nbsp; ViewBackground is the background view
* @constructor
*/
JenScript.ViewBackground = function() {	
	this.outlineRound = 20;
	this.outlineColor=new JenScript.Color(255,39,90,255);
	this.outlineStroke = new JenScript.Stroke(4);	
	var night = new JenScript.Color(32,39,90,200);
	var black = new JenScript.Color(0, 0, 0, 255);
	var fractions = [0,1];
	var colors = [night,black];
	var s = new JenScript.Shader(fractions, colors);	
	this.shader = s;
};


/*
 * ViewBackground methods
 */
JenScript.ViewBackground.prototype = {
		
		/**
		 * set outline round
		 * @param {int} the outline round to set 
		 */
		setOutlineRound : function(outlineRound) {
			this.outlineRound = outlineRound;
		},
		
		/**
		 * get outline round
		 * @returns {int} round  value
		 */
		getOutlineRound : function() {
			return this.outlineRound;
		},
		
		/**
		 * set outline color
		 * @param {Object} view background outline color to set
		 */
		setOutlineColor : function(outlineColor) {
			this.outlineColor = outlineColor;
		},

		/**
		 * get outline color
		 * @returns {Color} the outline color 
		 */
		getOutlineColor : function() {
			return this.outlineColor;
		},
		
		/**
		 * set outline stroke
		 * @param {Stroke} the view background outline stroke
		 */
		setOutlineStroke : function(outlineStroke) {
			this.outlineStroke = outlineStroke;
		},

		/**
		 * get outline stroke
		 * @returns {Stroke} the view background outline stroke
		 */
		getOutlineStroke : function() {
			return this.outlineStroke;
		},
		
		setShader : function(shader) {
			this.shader = shader;
		},

		getShader : function() {
			return this.shader;
		},
		
		/**
		 * Get the view background as X2D XML  
		 */
		getX2D : function(){		
			var x2dBuilder = JenScript.Core.x2dBuilder;
			var backgroundXML = x2dBuilder.openElement('background')+
								x2dBuilder.createElement('outline-round',this.outlineRound)+
								x2dBuilder.getX2D('outline-color',this.outlineColor)+
								x2dBuilder.getX2D('outline-stroke',this.outlineStroke)+
								x2dBuilder.getX2D('background-shader',this.shader)+
								x2dBuilder.closeElement('background');
			return backgroundXML;
		}
		
};


/**
 * Create window2D with specified user space bound.
 * 
 * @param name the view name
 * @param minX the user minimum x value on x axis
 * @param maxX the user maximum x value on x axis
 * @param minY the user minimum y value on y axis
 * @param maxY the user maximum y value on y axis
 *  
 */
JenScript.Window2D = function(name,minX,maxX,minY,maxY) {
	
	this.id = undefined;
	this.name = name;
	
	this.initialMinX = minX;
	this.initialMaxX = maxX;
	this.initialMinY = minY;
	this.initialMaxY = maxY;
	
	this.minX = minX;
	this.maxX = maxX;
	this.minY = minY;
	this.maxY = maxY;
	
	this.themeColor;
	this.view2D = undefined; 
	this.plugins =[];
	this.projection = JenScript.Window2D.Projection.Linear;
};		

JenScript.Window2D.Projection = {Linear :'linear', LogX :'logX', LogY : 'logY', Log:'log'};
JenScript.Window2D.Part = {Device2D :'device2D', South :'south', North : 'north', East:'east', West:'west'};

/**
 * Window2D method
 */
JenScript.Window2D.prototype = {
	
	/**
	 * set window ID
	 * @param {String} the window id to set
	 */
	setID : function(id) {
		this.id = id;
	},
	
	/**
	 * get window ID
	 * @returns {String} the window ID
	 */
	getID : function() {
		return this.id;
	},
	
	/**
	 * set window name
	 * @param {String} the window name to set
	 */
	setName : function(name) {
		this.name = name;
	},
	
	/**
	 * get window name
	 * @returns {String} the window ID
	 */
	getName : function() {
		return this.name;
	},
	
	/**
	 * set window wiew2D
	 * @param {View2D} the window view2D to set
	 */
	setView2D : function(view2D) {
		this.view2D = view2D;
	},
	
	/**
	 * get window view2D
	 * @returns {String} the window ID
	 */
	getView2D : function() {
		return this.view2D;
	},
	
	/**
	 * set theme color
	 * @param {Color} the window theme color to set
	 */
	setThemeColor : function(themeColor) {
		this.themeColor = themeColor;
	},

	/**
	 * get window theme color
	 * @returns {Color} the theme color
	 */
	getThemeColor : function() {
		return this.themeColor;
	},
	
	/**
	 * register specified plugin in this window
	 * @param {AbstractPlugin} plugin
	 */
	registerPlugin : function(plugin) {
		plugin.setWindow2D(this);
		this.plugins[this.plugins.length] = plugin;
	},
	
	getPlugins : function(){
		return this.plugins;
	},
	
	/**
	 * get the window width
	 * 
	 * @return window width
	 */
	getWidth : function() {
		return this.maxX - this.minX;
	},

	/**
	 * get the window height
	 * 
	 * @return window height
	 */
	getHeight : function() {
		return this.maxY - this.minY;
	},
	
	/**
	 * return the transform coordinate from user system coordinate to pixel
	 * @param {Point} point array
	 */
	userToPixel : function(point){		
		if(this.projection.valueOf() == JenScript.Window2D.Projection.Linear.valueOf()){
			return this.userToDevice(point);
		}
		else if(this.projection.valueOf() == JenScript.Window2D.Projection.LogX.valueOf()){
			return this.userToDeviceLogX(point);
		}
		else if(this.projection.valueOf() == JenScript.Window2D.Projection.LogY.valueOf()){
			return this.userToDeviceLogY(point);
		}
		else if(this.projection.valueOf() == JenScript.Window2D.Projection.Log.valueOf()){
			return this.userToDeviceLogLog(point);
		}
	},
	
	/**
	 * return the transform coordinate from user to pixel
	 */
	userToDevice : function (userPoint){
		var view = this.getView2D();
		var deviceWidth = view.width   - view.west - view.east;
		var deviceHeight = view.height - view.north - view.south;		
		var scalex = deviceWidth/this.getWidth();
		var scaley = deviceHeight/this.getHeight();		
		var px = userPoint.x - this.minX;
		var py = -(userPoint.y - this.maxY);		
		return {x:scalex*px,y:scaley*py};
	},
	
	/**
	 * return the transform coordinate from user log x, linear y system coordinate to pixel
	 */
	userToDeviceLogX : function (point){
		
	},
	
	/**
	 * return the transform coordinate from user linear x, log y system coordinate to pixel
	 */
	userToDeviceLogY : function (point){
		
	},
	
	/**
	 * return the transform coordinate from user log x log y system coordinate to pixel
	 */
	userToDeviceLogLog : function (point){
		
	},
	
	/**
	 * paint this window 2D
	 */
	paint: function() {
		//alert("paint window2D :"+this.name+" with ID: "+this.id);	
		for(var i=0; i<this.plugins.length; i++) {
			var plugin = this.plugins[i];
			plugin.setName('plugin'+i);
			plugin.setID('p'+i);
			plugin.paint(this.view2D.device2DCanvas.getContext('2d'),JenScript.Window2D.Part.Device2D);
			plugin.paint(this.view2D.southCanvas.getContext('2d'),JenScript.Window2D.Part.South);
			plugin.paint(this.view2D.westCanvas.getContext('2d'),JenScript.Window2D.Part.West);
			plugin.paint(this.view2D.eastCanvas.getContext('2d'),JenScript.Window2D.Part.East);
			plugin.paint(this.view2D.northCanvas.getContext('2d'),JenScript.Window2D.Part.North);
		}
	},
	
	/**
	 * Get window as X2D XML
	 */
	getX2D  : function() {
		var x2dBuilder = JenScript.Core.x2dBuilder;
		var windowX2D = x2dBuilder.openElement('window2d')+
						x2dBuilder.createElement('id',this.id)+
						x2dBuilder.createElement('name',this.name)+
						x2dBuilder.getX2D('theme-color',this.themeColor)+
						x2dBuilder.createElement('min-x',this.minX)+
						x2dBuilder.createElement('max-x',this.maxX)+
						x2dBuilder.createElement('min-y',this.minY)+
						x2dBuilder.createElement('max-y',this.maxY);
						
		
			for(var i=0; i<this.plugins.length; i++) {
				var plugin = this.plugins[i];
				plugin.setName('plugin'+i);
				plugin.setID('p'+i);				
				windowX2D = windowX2D+plugin.getX2D();				
			}
		windowX2D = windowX2D + '</window2d>';
			
		return windowX2D;

	}		
};

/**
 * AbstractPlugin
 */
JenScript.AbstractPlugin = function() {
	this.id = undefined;
	this.name = undefined;
	this.className = undefined;
	this.window2D = undefined;
	
	
	this.canvas = document.createElement('canvas');
    this.graphics2D = this.canvas.getContext('2d');
    this.canvas.style.position = 'absolute';
    
    //base for plugin listening
    this.onClickListening = false;
    this.onPressListening = false;
    this.onReleasedListening = false;
    this.onMoveListening = false;
    this.onDragListening = false;
}

/**
 * AbstractPlugin methods
 */
JenScript.AbstractPlugin.prototype =  {		
		
		setID : function(id) {
			this.id = id;
		},

		getID : function() {
			return this.id;
		},
		
		setName : function(name) {
			this.name = name;
		},

		getName : function() {
			return this.name;
		},
		
		setClassName : function(className) {
			this.className = className;
		},

		getClassName : function() {
			return this.className;
		},


		getCanvas : function() {
			return this.canvas;
		},

		
		getWindow2D : function(){
			return this.window2D;
		},
		
		setWindow2D : function(window2D){
			this.window2D = window2D;
		},

		 /**
	     * bind events to plugin.  supports mouseover, mousemove,
	     * mouseout, mousedown, mouseup, click, dblclick, touchstart, touchmove,
	     * touchend, dbltap, dragstart, dragmove, and dragend.  Pass in a string
	     * of event types delimmited by a space to bind multiple events at once
	     * such as 'mousedown mouseup mousemove'. include a namespace to bind an
	     * event by name such as 'click.foobar'.
	     * @param {String} typesStr
	     * @param {function} handler
	     */
	    on: function(typesStr, handler) {
	        var types = typesStr.split(' ');
	        /*
	         * loop through types and attach event listeners to
	         * each one.  eg. 'click mouseover.namespace mouseout'
	         * will create three event bindings
	         */
	        for(var n = 0; n < types.length; n++) {
	            var type = types[n];
	            var event = (type.indexOf('touch') === -1) ? 'on' + type : type;
	            var parts = event.split('.');
	            var baseEvent = parts[0];
	            var name = parts.length > 1 ? parts[1] : '';

	            if(!this.eventListeners[baseEvent]) {
	                this.eventListeners[baseEvent] = [];
	            }

	            this.eventListeners[baseEvent].push({
	                name: name,
	                handler: handler
	            });
	        }
	    },
	    /**
	     * remove event bindings from the node.  Pass in a string of
	     * event types delimmited by a space to remove multiple event
	     * bindings at once such as 'mousedown mouseup mousemove'.
	     * include a namespace to remove an event binding by name
	     * such as 'click.foobar'.
	     * @param {String} typesStr
	     */
	    off: function(typesStr) {
	        var types = typesStr.split(' ');

	        for(var n = 0; n < types.length; n++) {
	            var type = types[n];
	            var event = (type.indexOf('touch') === -1) ? 'on' + type : type;
	            var parts = event.split('.');
	            var baseEvent = parts[0];

	            if(this.eventListeners[baseEvent] && parts.length > 1) {
	                var name = parts[1];

	                for(var i = 0; i < this.eventListeners[baseEvent].length; i++) {
	                    if(this.eventListeners[baseEvent][i].name === name) {
	                        this.eventListeners[baseEvent].splice(i, 1);
	                        if(this.eventListeners[baseEvent].length === 0) {
	                            this.eventListeners[baseEvent] = undefined;
	                        }
	                        break;
	                    }
	                }
	            }
	            else {
	                this.eventListeners[baseEvent] = undefined;
	            }
	        }
	    }
}

/**
 * View 2D
 * @param width
 * @param height
 * @param viewkey 
 */
JenScript.View2D = function(width,height,containerName) {	
	

	this.width=width;
	this.height=height;
	this.container=document.getElementById(containerName);
	
	this.viewKey= 'view-key-to-set';
	this.apiKey = 'a996d69d-97b9-4dc6-9815-405dbba71f3b';
	
	this.west=40;
	this.east=40;
	this.north=40;
	this.south=40;
	
	this.backgroundPainter;
	
	this.window2Ds =[];	
	
	//content create in container
	this.content = document.createElement('div');	
	
	//img for x2d connector for hosted mode 
	this.img = document.createElement('img');
	this.img.setAttribute('id', this.viewKey);	
	
	//html canvas (incubator)
	this.view2DContent = document.createElement('div');
	this.view2DCanvas = document.createElement('canvas');
	this.view2DCanvas.setAttribute('id', "view2DCanvas");
	
	this._createDomView();
	this._listen();   
};

JenScript.WindowPartComponent = function(windowPart,x,y,width,height) {
	this.windowPart = windowPart;
	this.x=x;
	this.y=y;
	this.width=width;
	this.height=height;	
	this.view2D;
};

JenScript.WindowPartComponent.prototype = {
	
	
	setView2D : function (view2D){
		this.view2D = view2D;
	},

	getView2D : function (){
		return this.view2D;
	},

	createPart : function(){
		
		this.partContent = document.createElement('div');
		this.partCanvas = document.createElement('canvas');
		this.partCanvas.setAttribute('id', this.windowPart);
		
		//add part content in view2D content
        this.partContent.style.position = 'absolute';
        this.partContent.style.display = 'block';
        
        this.view2D.view2DContent.appendChild(this.partContent);
        //alert('create part : '+this.windowPart +' x:'+this.x+' y:'+this.y+' width:'+this.width+' height:'+this.height);
		this.partContent.style.marginTop = this.y + 'px';
		this.partContent.style.marginLeft = this.x + 'px';
		this.partContent.style.marginBottom = this.view2D.height - this.y - this.height  + 'px';
		this.partContent.style.marginRight = this.view2D.width - this.x - this.width + 'px';
		this.partContent.style.width = this.view2D.width  + 'px';
		this.partContent.style.height = this.view2D.height + 'px';
     
        this.partCanvas.style.position = 'absolute';
        this.partCanvas.style.display = 'block';
    	this.partContent.appendChild(this.partCanvas);
    	
        this.partCanvas.width = this.width;
        this.partCanvas.height = this.height;
        
        this.g2d = this.partCanvas.getContext('2d');
	},

	/**
	 * paint part component
	 */
	paintComponent : function (){
		//alert("paintComponent:"+this.windowPart);		
		var w2ds = this.view2D.getWindow2Ds();
    	for(var i=0; i<w2ds.length; i++) {			
		var w2d = w2ds[i];		
			if(w2d instanceof JenScript.Window2D){					
				var plugins = w2d.getPlugins();
				for(var p=0; p<plugins.length; p++) {
					var plugin = plugins[p].paint(this.g2d,this.windowPart);
				}
			}			 
    	}	
	}
};



JenScript.Device2DPartComponent = function() {
	JenScript.WindowPartComponent.apply(this,[JenScript.Window2D.Part.Device2D]);
};


JenScript.Core.extend(JenScript.Device2DPartComponent, JenScript.WindowPartComponent);

JenScript.View2D.prototype = {
		
	/**
	 * get the container HTML element
	 */
	getViewContainer : function() {
		return this.container;
	},
	
	/**
     * get content DOM element
     */
    getContent: function() {
        return this.content;
    },
    
    /**
     * begin listening for events by adding event handlers
     * to the container
     */
    _listen: function() {
        var that = this;

        // desktop events
        this.content.addEventListener('mousedown', function(evt) {
            that.mouseDown = true;
            //that._handleStageEvent(evt);
        }, false);

        this.content.addEventListener('mousemove', function(evt) {
            that.mouseUp = false;
            that.mouseDown = false;
            //that._handleStageEvent(evt);
        }, false);

        this.content.addEventListener('mouseup', function(evt) {
            that.mouseUp = true;
            that.mouseDown = false;
            //that._handleStageEvent(evt);

            that.clickStart = false;
        }, false);

        this.content.addEventListener('mouseover', function(evt) {
          //  that._handleStageEvent(evt);
        }, false);

        this.content.addEventListener('mouseout', function(evt) {
            // if there's a current target shape, run mouseout handlers
            //var targetShape = that.targetShape;
            //if(targetShape) {
                //targetShape._handleEvents('onmouseout', evt);
                //that.targetShape = undefined;
            //}
            that.mousePos = undefined;
        }, false);
        // mobile events
        this.content.addEventListener('touchstart', function(evt) {
            evt.preventDefault();
            that.touchStart = true;
            //that._handleStageEvent(evt);
        }, false);

        this.content.addEventListener('touchmove', function(evt) {
            evt.preventDefault();
            //that._handleStageEvent(evt);
        }, false);

        this.content.addEventListener('touchend', function(evt) {
            evt.preventDefault();
            that.touchEnd = true;
            //that._handleStageEvent(evt);
        }, false);
    },
    
    /**
     * bind event listener to container DOM element
     * @param {String} typesStr
     * @param {function} handler
     */
    _onContent: function(typesStr, handler) {
        var types = typesStr.split(' ');
        for(var n = 0; n < types.length; n++) {
            var baseEvent = types[n];
            this.content.addEventListener(baseEvent, handler, false);
        }
    },
    
    /**
     * set mouse positon for desktop apps
     * @param {Event} evt
     */
    _setMousePosition: function(evt) {
        var mouseX = evt.offsetX || (evt.clientX - this._getContentPosition().left + window.pageXOffset);
        var mouseY = evt.offsetY || (evt.clientY - this._getContentPosition().top + window.pageYOffset);
        this.mousePos = {
            x: mouseX,
            y: mouseY
        };
    },
    
    /**
     * get container position
     */
    _getContentPosition: function() {
        var obj = this.content;
        var top = 0;
        var left = 0;
        while(obj && obj.tagName !== 'BODY') {
            top += obj.offsetTop - obj.scrollTop;
            left += obj.offsetLeft - obj.scrollLeft;
            obj = obj.offsetParent;
        }
        return {
            top: top,
            left: left
        };
    },
    
    /**
     * create dom image content
     */
    _createDomImageContent: function() {
    	 // add image in content
        this.img.style.position = 'absolute';
        this.img.style.display = 'block';
    	this.content.appendChild(this.img); 
    	
    	//image size
    	this.img.style.width = this.width + 'px';
        this.img.style.height = this.height + 'px';
    },
    
    /**
     * create view 2D content and canvas
     */
    _createDomView2DContent: function() {
    	//add view content in content
        this.view2DContent.style.position = 'absolute';
        this.view2DContent.style.display = 'block';
        this.content.appendChild(this.view2DContent);        
        
        this.view2DContent.style.width = this.width + 'px';
        this.view2DContent.style.height = this.height + 'px';
        
        
        //add a canvas to view content
        this.view2DCanvas.style.position = 'absolute';
        this.view2DCanvas.style.display = 'block';
    	this.view2DContent.appendChild(this.view2DCanvas);
    	
    	this.view2DCanvas.width = this.width;
        this.view2DCanvas.height = this.height;
        
        //view parts
        this.southPart = new JenScript.WindowPartComponent(JenScript.Window2D.Part.South,0,this.height - this.south, this.width, this.south);
        this.northPart = new JenScript.WindowPartComponent(JenScript.Window2D.Part.North,0,0,this.width,this.north);
        this.westPart = new JenScript.WindowPartComponent(JenScript.Window2D.Part.West, 0, this.north, this.west, this.height - this.north - this.south);
        this.eastPart = new JenScript.WindowPartComponent(JenScript.Window2D.Part.East, this.width-this.east, this.north, this.east, this.height - this.north - this.south);
        
        this.device2DPart = new JenScript.WindowPartComponent(JenScript.Window2D.Part.Device2D, this.west, this.north, this.width - this.west - this.east, this.height - this.north - this.south);
        
        this.southPart.setView2D(this);
        this.northPart.setView2D(this);
        this.westPart.setView2D(this);
        this.eastPart.setView2D(this);
        this.device2DPart.setView2D(this);
        
        this.southPart.createPart();
        this.northPart.createPart();
        this.westPart.createPart();
        this.eastPart.createPart();
        this.device2DPart.createPart();
    },
    

	/**
     * create dom root figure
     */
    _createDomView: function() {
        
    	// add content in container
        this.content.style.position = 'relative';
        this.content.style.display = 'block';
        this.container.appendChild(this.content);
        
        //content size
        this.content.style.width = this.width + 'px';
        this.content.style.height = this.height + 'px';
        

        //this._createDomView2DContent();
        this._createDomImageContent();
    },
    
    /**
     * paint view component
     */
    paintComponent : function(){
    	
    	var g2d = this.view2DCanvas.getContext('2d');
    	
		
		if(this.backgroundPainter !== undefined){
			
			var bg = this.backgroundPainter;
			var or = bg.outlineRound;
			var oc = bg.outlineColor;
			var os = bg.outlineStroke;
			var lw =3
			
			var startX = lw/2;
			var startY = lw/2;
			
			var width = this.width;
			var height = this.height;
			g2d.beginPath();
			g2d.moveTo(startX,startY+or);
			g2d.quadraticCurveTo(startX,startY,or+startX,startY);
			g2d.lineTo(width-or-startY,startY);
			g2d.quadraticCurveTo(width-startX,startY,width-startX,or+startY);
			g2d.lineTo(width-startX,height-or-startY);
			g2d.quadraticCurveTo(width-startY,height-startY,width-or-startX,height-startY);
			g2d.lineTo(or+startX,height-startY);
			g2d.quadraticCurveTo(startX,height-startY,startX,height-or-startY);
			g2d.closePath();
			
			var shader = bg.shader;
			if(shader !== undefined){
				var fractions = shader.fractions;
				var colors =shader.colors;
				
				
				var grd = g2d.createLinearGradient(0, 0, 0, this.height);
				var colorutil = JenScript.Core.colorUtil;
				for(var i=0 ;i<fractions.length;i++){
					grd.addColorStop(fractions[i], colors[i].toStringRGBA());
				}
		        g2d.fillStyle = grd;
		        g2d.fill();
			}
			if(oc !== undefined){
				g2d.strokeStyle = oc.toStringRGBA();
				if(os !== undefined){
					g2d.lineWidth = os.width;
				}
				g2d.stroke();
			}
		}
		
		
    },
    
    /**
     * paint view children
     */
    paintChildren : function(){
    	//alert("view paint children");
    	this.device2DPart.paintComponent();
       this.southPart.paintComponent();
       this.northPart.paintComponent();
       this.westPart.paintComponent();
       this.eastPart.paintComponent();
    },
    
    /**
     * paint
     */
	paint: function() {		
		
		this.paintChildren();
		this.paintComponent();
	},

	setBackgroundPainter : function(backgroundPainter) {
		this.backgroundPainter = backgroundPainter;
	},

	getBackgroundPainter : function() {
		return this.backgroundPainter;
	},

	setViewKey : function(viewKey) {
		this.viewKey = viewKey;
	},

	getViewKey : function() {
		return this.viewKey;
	},
	
	setApiKey : function(apiKey) {
		this.apiKey = apiKey;
	},

	getApiKey : function() {
		return this.apiKey;
	},
	

	setWidth : function(width) {
		this.width = width;
	},

	getWidth : function() {
		return this.width;
	},

	setHeight : function(height) {
		this.height = height;
	},

	getHeight : function() {
		return this.height;
	},

	setNorth : function(north) {
		this.north = north;
	},

	getNorth : function() {
		return this.north;
	},
	
	setSouth : function(south) {
		this.south = south;
	},

	getSouth : function() {
		return this.south;
	},
	
	setWest : function(west) {
		this.west = west;
	},

	getWest : function() {
		return this.west;
	},
	
	setEast : function(east) {
		this.east = east;
	},

	getEast : function() {
		return this.east;
	},
	
	
	/**
	 * register window 2D
	 * @param {Window2D}
	 */
	registerWindow2D : function(window2D) {
		window2D.setView2D(this);
		this.window2Ds[this.window2Ds.length] = window2D;
	},
	
	getWindow2Ds : function(){
		return this.window2Ds;
	},
	
	/**
	 * get window block as X2D XML
	 * 
	 **/
	getX2DWindows : function(){
		var x2dWindows ='';
		for(var i=0; i<this.window2Ds.length; i++) {			
			var w2d = this.window2Ds[i];
			w2d.setID('window'+i);			
			if(w2d instanceof JenScript.Window2D){					
				x2dWindows = x2dWindows + w2d.getX2D();
			}			 
		}	
		return x2dWindows;
	},
		
	/**
	 * get view as X2D XML
	 * 
	 **/
	getX2D : function(){		
		var x2db = JenScript.Core.x2dBuilder;
		var x2dView = x2db.openViewDocument()+
					  x2db.createElement('view-key', this.viewKey)+
					  x2db.createElement('api-key', this.apiKey)+
					  x2db.createElement('width',this.width)+
					  x2db.createElement('height',this.height)+
					  x2db.createElement('west',this.west)+
					  x2db.createElement('east',this.east)+
					  x2db.createElement('north',this.north)+
					  x2db.createElement('south',this.south)+					  
					  x2db.getX2D(undefined,this.backgroundPainter)+
					  this.getX2DWindows()+
					  x2db.closeViewDocument();				
		return x2dView;
	},
	
	
	getBody : function(){
		var body = 'apikey='+this.apiKey+'&viewkey='+this.viewKey+'&view='+this.getX2D();
		return body;
	},
	
	getXMLHttpRequest : function () {
		var xhr = null;		
		if (window.XMLHttpRequest || window.ActiveXObject) {
			if (window.ActiveXObject) {
				try {
					xhr = new ActiveXObject("Msxml2.XMLHTTP");
				} catch(e) {
					xhr = new ActiveXObject("Microsoft.XMLHTTP");
				}
			} else {
				xhr = new XMLHttpRequest(); 
			}
		} else {
			//alert("Browser does not support XMLHTTPRequest.");
			return null;
		}		
		return xhr;
	},
	
	/**
	 * post x2d request to x2d server
	 */
	requestView : function(){	
		var xhr = this.getXMLHttpRequest();
		var vk = this.getViewKey();
		var vc = this.view2DCanvas;
		var w = this.width;
		var h = this.height;	
		
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) {
	          document.getElementById(vk).setAttribute('src','data:image/png;base64,'+xhr.responseText);
			}
			else if (xhr.readyState == 4 && xhr.status == 400) {				
				if(xhr.responseXML !== undefined){				
					var doc = xhr.responseXML;
					var x2dErrors = doc.getElementsByTagName('x2d-error');
					alert("size : "+x2dErrors.length);
					for(var i=0; i<x2dErrors.length; i++) {
						var element = x2dErrors.item(i);
						alert("erreur "+i+" : "+element.childNodes[0].data);
					}					
				}
			}
			else{			
			}
		};
		xhr.open("POST", JenScript.Core.x2dServer, true);
		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		xhr.send(this.getBody());
	}
	
};


/**
 * Stroke
 */

JenScript.Stroke = function(width) {
	this.width= width;//float
	this.join;//string : mitter,round,bevel
	this.cap;//string : butt,round,square
	this.miterlimit;//float > 1
    this.dash;//float array
    this.dashPhase= 0;//float 
};

JenScript.Stroke.prototype = {

    setWidth : function(width) {
		this.width = width;
	},

	getWidth : function() {
		return this.width;
	},
	
	 
    setJoin : function(join) {
		this.join = join;
	},

	getJoin : function() {
		return this.join;
	},
	
	setCap : function(cap) {
		this.cap = cap;
	},

	getCap : function() {
		return this.cap;
	},
	
	setMiterLimit : function(miterlimit) {
		this.miterlimit = miterlimit;
	},

	getMiterLimit : function() {
		return this.miterlimit;
	},
	
	setDash : function(dash) {
		this.dash = dash;
	},

	getDash : function() {
		return this.dash;
	},
	
	setDashPhase : function(dashPhase) {
		this.dashPhase = dashPhase;
	},

	getDashPhase : function() {
		return this.dashPhase;
	},

	/**
	 * get stroke as X2D XML
	 */
	getX2D : function(strokeName){	
		if(strokeName === undefined){
			strokeName = 'stroke';
		}
		var x2dBuilder = JenScript.Core.x2dBuilder;
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
		strokeXML = x2dBuilder.openElement(strokeName)+
					x2dBuilder.createElement('width',this.width)+
					x2dBuilder.createElement('join',this.join)+
					x2dBuilder.createElement('cap',this.cap)+
					x2dBuilder.createElement('miterlimit',this.miterlimit)+
					x2dBuilder.createElement('dash',this.dash)+
					x2dBuilder.createElement('dashphase',this.dashphase)+
					x2dBuilder.closeElement(strokeName);
		return strokeXML;
	}
};


/**
 * Color util
 */
JenScript.ColorUtil = function() {

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
		return new JenScript.Color(r,g,b,a);
	};
};


JenScript.Core.colorUtil = new JenScript.ColorUtil();

/**
 * Color
 */
JenScript.Color = function(red,green,blue,alpha) {
	this.r = red;
	this.g = green;
	this.b = blue;
	this.a = alpha;	
};

/*
 * Color methods
 */
JenScript.Color.prototype =  {
		
	toStringRGBA : function(){
		return 'rgba('+this.r+','+this.g+','+this.b+','+this.a/255+')';
	},
		
	toHex : function (n) {
		 n = parseInt(n,10);
		 if (isNaN(n)) return "00";
		 n = Math.max(0,Math.min(n,255));
		 return "0123456789ABCDEF".charAt((n-n%16)/16)
		      + "0123456789ABCDEF".charAt(n%16);
	},
	
	// convert RGB color data to hex
	rgb2hex : function() {	    
	    return '#'+this.toHex(this.r)+this.toHex(this.g)+this.toHex(this.b);
	},
		
	// convert RGBA color data to hex
	rgba2hex : function() {	    
	    return '#'+(256 + this.r).toString(16).substr(1) +((1 << 24) + (this.g << 16) | (this.b << 8) | this.a).toString(16).substr(1);
	},

		
	getX2D : function(colorName) {
		var x2dBuilder = JenScript.Core.x2dBuilder;
		
		if(colorName === undefined){
			colorName= 'color';
		}
		
		var x2dColor ='';
		if(typeof this.r != undefined && typeof this.g != undefined && typeof this.b != undefined && typeof this.a != undefined){
			x2dColor = 	x2dBuilder.openElement(colorName)+
						x2dBuilder.createElement('r',this.r)+
						x2dBuilder.createElement('g',this.g)+
						x2dBuilder.createElement('b',this.b)+
						x2dBuilder.createElement('a',this.a)+
						x2dBuilder.closeElement(colorName);
		}
		return x2dColor;
	},
	
	getR : function() {
		return this.r;
	},
	
	getG : function() {
		return this.g;
	},
	
	getB : function() {
		return this.b;
	},
	
	getA : function() {
		return this.a;
	}
	
};



/**
 * Create shader
 * @param {Array} float fraction array
 */
JenScript.Shader = function(fractions,colors) {
	this.fractions = fractions;
	this.colors = colors;
};

/**
 * Shader methods
 */
JenScript.Shader.prototype =  {
                              
	/**
	 * get shader as X2D XML
	 */
	getX2D : function(shaderName){
		
		if(shaderName === undefined){
			shaderName = 'shader';
		}
		
		var colorutil = JenScript.Core.colorUtil;
		var x2dBuilder = JenScript.Core.x2dBuilder;
		
		var shaderXML='';		
		if(this.fractions !== undefined && this.colors !== undefined && this.fractions.length == this.colors.length){
			shaderXML = x2dBuilder.openElement(shaderName);
			for(var n=0; n<this.fractions.length; n++) {
				shaderXML =shaderXML+
							x2dBuilder.openElement('shaderEntry')+
							x2dBuilder.createElement('f', this.fractions[n])+
							x2dBuilder.getX2D('c', this.colors[n])+
							x2dBuilder.closeElement('shaderEntry');
			}
			shaderXML = shaderXML + x2dBuilder.closeElement(shaderName);
		}
		return shaderXML;
	},
	
	
	
	/**
	 * set fractions
	 */
	setFractions : function(fractions) {
		this.fractions = fractions;
	},

	/**
	 * get fractions
	 */
	getFractions : function() {
		return this.fractions;
	},
	
	/**
	 * set colors
	 */
	setColors : function(colors) {
		this.colors = colors;
	},

	/**
	 * get colors
	 */
	getColors : function() {
		return this.colors;
	}
                              
};
	

