function registerNS(ns) {
	alert("register ns : "+ns);
	var nsParts = ns.split('.');
	var root = window;
	for ( var i = 0; i < nsParts.length; i++) {
		if (typeof root[nsParts[i]] == 'undefined')
			root[nsParts[i]] = new Object();
		root = root[nsParts[i]];
	}
}

function registerPart(partURL){	
	document.write('<script type="text/javascript" src="'+ partURL +'"></script>');
};

//Script URL
var scriptURL='http://localhost:8888/js/';
//var scriptURL='http://www.jensoft.org/jensoft/jenscript/';

//Server URL
var serverURL='http://localhost:8888/JenScript';
//var serverURL='http://www.jensoft.org/jensoft/JenScript';

//API KEY
var jenscriptAPIKey;
function setJenscriptAPIKey(apiKey){
	jenscriptAPIKey=apiKey;
};
registerNS('com.jensoft.jenscript');
registerPart(scriptURL+'prototype.js');
registerPart(scriptURL+'jenscript-window2d.js');
registerPart(scriptURL+'jenscript-view2d.js');
registerPart(scriptURL+'jenscript-graphics.js');
registerPart(scriptURL+'jenscript-color.js');
registerPart(scriptURL+'jenscript-plot.js');
registerPart(scriptURL+'plugins/jenscript-legend.js');
registerPart(scriptURL+'plugins/jenscript-outline.js');
registerPart(scriptURL+'plugins/jenscript-metrics.js');
registerPart(scriptURL+'plugins/jenscript-pie.js');
registerPart(scriptURL+'plugins/jenscript-donut3d.js');
registerPart(scriptURL+'plugins/jenscript-symbol.js');
registerPart(scriptURL+'plugins/jenscript-grid.js');
registerPart(scriptURL+'plugins/jenscript-band.js');
registerPart(scriptURL+'plugins/jenscript-scatter.js');
registerPart(scriptURL+'plugins/jenscript-curve.js');
registerPart(scriptURL+'plugins/jenscript-area.js');
registerPart(scriptURL+'plugins/jenscript-bubble.js');
