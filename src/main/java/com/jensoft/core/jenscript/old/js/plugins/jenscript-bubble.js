registerNS('com.jensoft.jenscript.plugin.bubble');

com.jensoft.jenscript.plugin.bubble.Bubble = function(x,y,radius) {
	this.x=x;
	this.y=y;
	this.radius=radius;
	this.drawColor;
	this.fillColor;
	this.effect;	
	
	this.setX = function(x) {
		this.x = x;
	};
	this.setY = function(y) {
		this.y = y;
	};
	this.setRadius = function(radius) {
		this.radius = radius;
	};
	this.setDrawColor = function(drawColor) {
		this.drawColor = drawColor;
	};
	this.setFillColor = function(fillColor) {
		this.fillColor = fillColor;
	};
	this.setEffect = function(effect) {
		this.effect = effect;
	};
	
	this.getBubbleXML = function(){
		
		var bubbleXML = '<bubble>'+
						'<x>'+this.x+'</x>'+
						'<y>'+this.y+'</y>'+
						'<radius>'+this.radius+'</radius>'+
						'<drawcolor>'+this.drawColor+'</drawcolor>'+
						'<fillcolor>'+this.fillColor+'</fillcolor>'+
						'<effect>'+this.effect+'</effect>'+
						'</bubble>';
		return bubbleXML;
							
	};
	
};

com.jensoft.jenscript.plugin.bubble.BubblePlugin = function() {

	this.name;
	this.id;
	this.window2D;
	this.bubbles = [];	
	
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
	
	this.addBubble = function(bubble){
		this.bubbles[this.bubbles.length] = bubble;	
	};
	
	this.getPluginXML = function(){
		
		var pluginXML = '<plugin>'+
						'<id>'+this.id+'</id>'+
						'<name>'+this.name+'</name>'+
						'<class>'+'com.jensoft.sw2d.core.plugin.bubble.BubblePlugin'+'</class>'+
						'<params>'+
							'<bubbles>';
						
		for(var i=0; i<this.bubbles.length; i++) {
			var b = this.bubbles[i];
			pluginXML = pluginXML + b.getBubbleXML();
		}

		pluginXML = pluginXML + '</bubbles></params></plugin>';			
		return pluginXML;
	};
	
	
};