//*******************************
// Donut 2D JenScript Templates
// 
//*******************************

function createDonut2DPlainFill(){
	JenScript.Core.jetServer = 'http://localhost:8888/JenScript'
	var colorutil = new JenScript.ColorUtil();
	var black = colorutil.getColor(0, 0, 0, 255);
	var black2 = colorutil.getColor(0, 0, 0, 80);
	var white = colorutil.getColor(255, 255, 255, 255);
	var redwood 	= colorutil.getColor(203,71,52,255);
	var redwoodAlpha 	= colorutil.getColor(203,71,52,100);
	var lime 		= colorutil.getColor(197,208,89,255);
	var cobalt 		= colorutil.getColor(35,56,158,255);
	var emerald 	= colorutil.getColor(62,142,78,255);
	var night 	= colorutil.getColor(32,39,55,200);
	var fractions = [0,1];
	var colors = [night,black];
	var shader = new JenScript.Shader(fractions, colors);
	var stroke2 = new JenScript.Stroke(2);


	var view = new JenScript.View2D(800,600,'container');

	var w1 = new JenScript.Window2D('w1',-1,1,-1,1);
	view.registerWindow2D(w1);

	var donut2DPlugin = new JenScript.Donut2DPlugin.Plugin();
	w1.registerPlugin(donut2DPlugin);

	var donut2d = new JenScript.Donut2DPlugin.Donut2D();
	donut2d.setX(0);
	donut2d.setY(0);
	donut2d.setInnerRadius(90);
	donut2d.setOuterRadius(120);


	donut2DPlugin.addDonut2D(donut2d);

	var s1 = new JenScript.Donut2DPlugin.Slice(45, colorutil.getColor(240,240,240,255));
	var s2 = new JenScript.Donut2DPlugin.Slice(5, colorutil.getColor(249,235,113,255));
	var s3 = new JenScript.Donut2DPlugin.Slice(30, colorutil.getColor(176,224,88,255));
	var s4 = new JenScript.Donut2DPlugin.Slice(20, colorutil.getColor(143,177,214,255));

	donut2d.addSlice(s1);
	donut2d.addSlice(s2);
	donut2d.addSlice(s3);
	donut2d.addSlice(s4);

	
	var op = new JenScript.OutlinePlugin.Plugin();
	w1.registerPlugin(op);

	var x2d = view.getX2D();
	
	view.requestView();
}

function createDonut2DRadialFill(){
	JenScript.Core.jetServer = 'http://localhost:8888/JenScript'
	var colorutil = new JenScript.ColorUtil();
	var black = colorutil.getColor(0, 0, 0, 255);
	var black2 = colorutil.getColor(0, 0, 0, 80);
	var white = colorutil.getColor(255, 255, 255, 255);
	var redwood 	= colorutil.getColor(203,71,52,255);
	var redwoodAlpha 	= colorutil.getColor(203,71,52,100);
	var lime 		= colorutil.getColor(197,208,89,255);
	var cobalt 		= colorutil.getColor(35,56,158,255);
	var emerald 	= colorutil.getColor(62,142,78,255);
	var night 	= colorutil.getColor(32,39,55,200);
	var fractions = [0,1];
	var colors = [night,black];
	var textColors = [emerald,cobalt];
	var shader = new JenScript.Shader(fractions, colors);
	var stroke2 = new JenScript.Stroke(2);

	var textShader = new JenScript.Shader(fractions, textColors);
	var shader = new JenScript.Shader(fractions, colors);
	var stroke2 = new JenScript.Stroke(2);


	var view = new JenScript.View2D(800,600,'container');

	var w1 = new JenScript.Window2D('w1',-1,1,-1,1);
	view.registerWindow2D(w1);

	var donut2DPlugin = new JenScript.Donut2DPlugin.Plugin();
	w1.registerPlugin(donut2DPlugin);

	var donut2d = new JenScript.Donut2DPlugin.Donut2D();
	donut2d.setX(0);
	donut2d.setY(0);
	donut2d.setInnerRadius(90);
	donut2d.setOuterRadius(120);


	donut2DPlugin.addDonut2D(donut2d);

	var s1 = new JenScript.Donut2DPlugin.Slice(45, colorutil.getColor(240,240,240,255));
	var s2 = new JenScript.Donut2DPlugin.Slice(5, colorutil.getColor(249,235,113,255));
	var s3 = new JenScript.Donut2DPlugin.Slice(30, colorutil.getColor(176,224,88,255));
	var s4 = new JenScript.Donut2DPlugin.Slice(20, colorutil.getColor(143,177,214,255));

	

	donut2d.addSlice(s1);
	donut2d.addSlice(s2);
	donut2d.addSlice(s3);
	donut2d.addSlice(s4);

	var stroke1 = new JenScript.Stroke(2);
	
	var radialFill = new JenScript.Donut2DPlugin.Donut2DRadialFill();
	radialFill.setGradientType('SC_D_SC');
	donut2d.setFill(radialFill);

	var op = new JenScript.OutlinePlugin.Plugin();
	w1.registerPlugin(op);

	var x2d = view.getX2D();
	//alert(x2d);
	view.requestView();
}


function createDonut2DRadialLabel(){
	JenScript.Core.jetServer = 'http://localhost:8888/JenScript'
	var colorutil = new JenScript.ColorUtil();
	var black = colorutil.getColor(0, 0, 0, 255);
	var black2 = colorutil.getColor(0, 0, 0, 80);
	var white = colorutil.getColor(255, 255, 255, 255);
	var redwood 	= colorutil.getColor(203,71,52,255);
	var redwoodAlpha 	= colorutil.getColor(203,71,52,100);
	var lime 		= colorutil.getColor(197,208,89,255);
	var cobalt 		= colorutil.getColor(35,56,158,255);
	var emerald 	= colorutil.getColor(62,142,78,255);
	var night 	= colorutil.getColor(32,39,55,200);
	var fractions = [0,1];
	var colors = [night,black];
	var shader = new JenScript.Shader(fractions, colors);
	var stroke2 = new JenScript.Stroke(2);


	var view = new JenScript.View2D(800,600,'container');

	var w1 = new JenScript.Window2D('w1',-1,1,-1,1);
	view.registerWindow2D(w1);

	var donut2DPlugin = new JenScript.Donut2DPlugin.Plugin();
	w1.registerPlugin(donut2DPlugin);

	var donut2d = new JenScript.Donut2DPlugin.Donut2D();
	donut2d.setX(0);
	donut2d.setY(0);
	donut2d.setInnerRadius(90);
	donut2d.setOuterRadius(120);


	donut2DPlugin.addDonut2D(donut2d);

	var s1 = new JenScript.Donut2DPlugin.Slice(45, colorutil.getColor(240,240,240,255));
	var s2 = new JenScript.Donut2DPlugin.Slice(5, colorutil.getColor(249,235,113,255));
	var s3 = new JenScript.Donut2DPlugin.Slice(30, colorutil.getColor(176,224,88,255));
	var s4 = new JenScript.Donut2DPlugin.Slice(20, colorutil.getColor(143,177,214,255));

	

	donut2d.addSlice(s1);
	donut2d.addSlice(s2);
	donut2d.addSlice(s3);
	donut2d.addSlice(s4);

	var stroke1 = new JenScript.Stroke(2);
	
	var radialLabel= new JenScript.Donut2DPlugin.Donut2DRadialLabel();
	radialLabel.setOffsetRadius(60);
	radialLabel.setTextColor(white);
	radialLabel.setShader(shader);
	radialLabel.setOutlineRound(16);
	radialLabel.setOutlineStroke(stroke1);
	s1.setSliceLabel(radialLabel);

	var radialLabel2= new JenScript.Donut2DPlugin.Donut2DRadialLabel();
	radialLabel2.setOffsetRadius(60);
	radialLabel2.setTextColor(white);
	radialLabel2.setShader(shader);
	radialLabel2.setOutlineRound(16);
	radialLabel2.setOutlineStroke(stroke1);
	s2.setSliceLabel(radialLabel2);
	
	var radialLabel3 = new JenScript.Donut2DPlugin.Donut2DRadialLabel();
	radialLabel3.setOffsetRadius(60);
	radialLabel3.setTextColor(white);
	radialLabel3.setShader(shader);
	radialLabel3.setOutlineRound(16);
	radialLabel3.setOutlineStroke(stroke1);
	s3.setSliceLabel(radialLabel3);
	
	var radialLabel4= new JenScript.Donut2DPlugin.Donut2DRadialLabel();
	radialLabel4.setOffsetRadius(60);
	radialLabel4.setTextColor(white);
	radialLabel4.setShader(shader);
	radialLabel4.setOutlineRound(16);
	radialLabel4.setOutlineStroke(stroke1);
	radialLabel4.setText("aie ouille");
	s4.setSliceLabel(radialLabel4);


	var op = new JenScript.OutlinePlugin.Plugin();
	w1.registerPlugin(op);

	var x2d = view.getX2D();
	//alert(x2d);
	view.requestView();
}

function createDonut2DBorderLabel(){
	JenScript.Core.jetServer = 'http://localhost:8888/JenScript'
	var colorutil = new JenScript.ColorUtil();
	var black = colorutil.getColor(0, 0, 0, 255);
	var black2 = colorutil.getColor(0, 0, 0, 80);
	var white = colorutil.getColor(255, 255, 255, 255);
	var redwood 	= colorutil.getColor(203,71,52,255);
	var redwoodAlpha 	= colorutil.getColor(203,71,52,100);
	var lime 		= colorutil.getColor(197,208,89,255);
	var cobalt 		= colorutil.getColor(35,56,158,255);
	var emerald 	= colorutil.getColor(62,142,78,255);
	var night 	= colorutil.getColor(32,39,55,200);
	var fractions = [0,1];
	var colors = [night,black];
	var shader = new JenScript.Shader(fractions, colors);
	var stroke2 = new JenScript.Stroke(2);


	var view = new JenScript.View2D(800,600,'container');

	var w1 = new JenScript.Window2D('w1',-1,1,-1,1);
	view.registerWindow2D(w1);

	var donut2DPlugin = new JenScript.Donut2DPlugin.Plugin();
	w1.registerPlugin(donut2DPlugin);

	var donut2d = new JenScript.Donut2DPlugin.Donut2D();
	donut2d.setX(0);
	donut2d.setY(0);
	donut2d.setInnerRadius(90);
	donut2d.setOuterRadius(120);


	donut2DPlugin.addDonut2D(donut2d);

	var s1 = new JenScript.Donut2DPlugin.Slice(45, colorutil.getColor(240,240,240,255));
	var s2 = new JenScript.Donut2DPlugin.Slice(5, colorutil.getColor(249,235,113,255));
	var s3 = new JenScript.Donut2DPlugin.Slice(30, colorutil.getColor(176,224,88,255));
	var s4 = new JenScript.Donut2DPlugin.Slice(20, colorutil.getColor(143,177,214,255));

	

	donut2d.addSlice(s1);
	donut2d.addSlice(s2);
	donut2d.addSlice(s3);
	donut2d.addSlice(s4);

	var stroke1 = new JenScript.Stroke(2);
	
	var borderLabel= new JenScript.Donut2DPlugin.Donut2DBorderLabel();
	borderLabel.setMargin(60);
	borderLabel.setTextColor(white);
	borderLabel.setShader(shader);
	borderLabel.setOutlineRound(16);
	borderLabel.setOutlineStroke(stroke1);
	s1.setSliceLabel(borderLabel);

	var borderLabel2= new JenScript.Donut2DPlugin.Donut2DBorderLabel();
	borderLabel2.setMargin(60);
	borderLabel2.setTextColor(white);
	borderLabel2.setShader(shader);
	borderLabel2.setOutlineRound(16);
	borderLabel2.setOutlineStroke(stroke1);
	s2.setSliceLabel(borderLabel2);
	
	var borderLabel3 = new JenScript.Donut2DPlugin.Donut2DBorderLabel();
	borderLabel3.setMargin(60);
	borderLabel3.setTextColor(white);
	borderLabel3.setShader(shader);
	borderLabel3.setOutlineRound(16);
	borderLabel3.setOutlineStroke(stroke1);
	s3.setSliceLabel(borderLabel3);
	
	var borderLabel4= new JenScript.Donut2DPlugin.Donut2DBorderLabel();
	borderLabel4.setMargin(60);
	borderLabel4.setTextColor(white);
	borderLabel4.setShader(shader);
	borderLabel4.setOutlineRound(16);
	borderLabel4.setOutlineStroke(stroke1);
	borderLabel4.setText("aie ouille");
	s4.setSliceLabel(borderLabel4);


	var op = new JenScript.OutlinePlugin.Plugin();
	w1.registerPlugin(op);

	var x2d = view.getX2D();
	//alert(x2d);
	view.requestView();
}

function createDonut2DPathLabel(){
	
	JenScript.Core.jetServer = 'http://localhost:8888/JenScript'
	var colorutil = new JenScript.ColorUtil();
	var black = colorutil.getColor(0, 0, 0, 255);
	var black2 = colorutil.getColor(0, 0, 0, 80);
	var white = colorutil.getColor(255, 255, 255, 255);
	var redwood 	= colorutil.getColor(203,71,52,255);
	var redwoodAlpha 	= colorutil.getColor(203,71,52,100);
	var lime 		= colorutil.getColor(197,208,89,255);
	var cobalt 		= colorutil.getColor(35,56,158,255);
	var emerald 	= colorutil.getColor(62,142,78,255);
	var night 	= colorutil.getColor(32,39,55,200);
	var fractions = [0,1];
	var colors = [night,black];
	var textColors = [emerald,cobalt];
	var shader = new JenScript.Shader(fractions, colors);
	var stroke2 = new JenScript.Stroke(2);

	var textShader = new JenScript.Shader(fractions, textColors);
	var shader = new JenScript.Shader(fractions, colors);
	var stroke2 = new JenScript.Stroke(2);


	var view = new JenScript.View2D(800,600,'container');

	var w1 = new JenScript.Window2D('w1',-1,1,-1,1);
	view.registerWindow2D(w1);

	var donut2DPlugin = new JenScript.Donut2DPlugin.Plugin();
	w1.registerPlugin(donut2DPlugin);

	var donut2d = new JenScript.Donut2DPlugin.Donut2D();
	donut2d.setX(0);
	donut2d.setY(0);
	donut2d.setInnerRadius(90);
	donut2d.setOuterRadius(120);


	donut2DPlugin.addDonut2D(donut2d);

	var s1 = new JenScript.Donut2DPlugin.Slice(45, colorutil.getColor(240,240,240,255));
	var s2 = new JenScript.Donut2DPlugin.Slice(5, colorutil.getColor(249,235,113,255));
	var s3 = new JenScript.Donut2DPlugin.Slice(30, colorutil.getColor(176,224,88,255));
	var s4 = new JenScript.Donut2DPlugin.Slice(20, colorutil.getColor(143,177,214,255));

	

	donut2d.addSlice(s1);
	donut2d.addSlice(s2);
	donut2d.addSlice(s3);
	donut2d.addSlice(s4);

	var stroke1 = new JenScript.Stroke(2);
	
	var pathLabel = new JenScript.Donut2DPlugin.Donut2DPathLabel();
	pathLabel.setTextPosition('right');
	pathLabel.setText("JenSoft API");
	pathLabel.setPathSide('below');
	pathLabel.setTextShader(textShader);
	s1.setSliceLabel(pathLabel);


	var op = new JenScript.OutlinePlugin.Plugin();
	w1.registerPlugin(op);

	var x2d = view.getX2D();
	//alert(x2d);
	view.requestView();	
}




function createDonut2DLinearEffect(){
	JenScript.Core.jetServer = 'http://localhost:8888/JenScript'
	var colorutil = new JenScript.ColorUtil();
	var black = colorutil.getColor(0, 0, 0, 255);
	var black2 = colorutil.getColor(0, 0, 0, 80);
	var white = colorutil.getColor(255, 255, 255, 255);
	var redwood 	= colorutil.getColor(203,71,52,255);
	var redwoodAlpha 	= colorutil.getColor(203,71,52,100);
	var lime 		= colorutil.getColor(197,208,89,255);
	var cobalt 		= colorutil.getColor(35,56,158,255);
	var emerald 	= colorutil.getColor(62,142,78,255);
	var night 	= colorutil.getColor(32,39,55,200);
	var fractions = [0,1];
	var colors = [night,black];
	var textColors = [emerald,cobalt];
	var shader = new JenScript.Shader(fractions, colors);
	var stroke2 = new JenScript.Stroke(2);

	var textShader = new JenScript.Shader(fractions, textColors);
	var shader = new JenScript.Shader(fractions, colors);
	var stroke2 = new JenScript.Stroke(2);


	var view = new JenScript.View2D(800,600,'container');

	var w1 = new JenScript.Window2D('w1',-1,1,-1,1);
	view.registerWindow2D(w1);

	var donut2DPlugin = new JenScript.Donut2DPlugin.Plugin();
	w1.registerPlugin(donut2DPlugin);

	var donut2d = new JenScript.Donut2DPlugin.Donut2D();
	donut2d.setX(0);
	donut2d.setY(0);
	donut2d.setInnerRadius(90);
	donut2d.setOuterRadius(120);

	donut2DPlugin.addDonut2D(donut2d);

	var s1 = new JenScript.Donut2DPlugin.Slice(45, colorutil.getColor(240,240,240,255));
	var s2 = new JenScript.Donut2DPlugin.Slice(5, colorutil.getColor(249,235,113,255));
	var s3 = new JenScript.Donut2DPlugin.Slice(30, colorutil.getColor(176,224,88,255));
	var s4 = new JenScript.Donut2DPlugin.Slice(20, colorutil.getColor(143,177,214,255));	

	donut2d.addSlice(s1);
	donut2d.addSlice(s2);
	donut2d.addSlice(s3);
	donut2d.addSlice(s4);

	var stroke1 = new JenScript.Stroke(2);
	
	var radialFill = new JenScript.Donut2DPlugin.Donut2DRadialFill();
	radialFill.setGradientType('SC_D_SC');
	donut2d.setFill(radialFill);
	
	var fx = new JenScript.Donut2DPlugin.Donut2DLinearEffect();
	donut2d.setEffect(fx);

	var op = new JenScript.OutlinePlugin.Plugin();
	w1.registerPlugin(op);

	var x2d = view.getX2D();
	//alert(x2d);
	view.requestView();
}

function createDonut2DReflectionEffect(){
	JenScript.Core.jetServer = 'http://localhost:8888/JenScript'
	var colorutil = new JenScript.ColorUtil();
	var black = colorutil.getColor(0, 0, 0, 255);
	var black2 = colorutil.getColor(0, 0, 0, 80);
	var white = colorutil.getColor(255, 255, 255, 255);
	var redwood 	= colorutil.getColor(203,71,52,255);
	var redwoodAlpha 	= colorutil.getColor(203,71,52,100);
	var lime 		= colorutil.getColor(197,208,89,255);
	var cobalt 		= colorutil.getColor(35,56,158,255);
	var emerald 	= colorutil.getColor(62,142,78,255);
	var night 	= colorutil.getColor(32,39,55,200);
	var fractions = [0,1];
	var colors = [night,black];
	var textColors = [emerald,cobalt];
	var shader = new JenScript.Shader(fractions, colors);
	var stroke2 = new JenScript.Stroke(2);

	var textShader = new JenScript.Shader(fractions, textColors);
	var shader = new JenScript.Shader(fractions, colors);
	var stroke2 = new JenScript.Stroke(2);


	var view = new JenScript.View2D(800,600,'container');

	var w1 = new JenScript.Window2D('w1',-1,1,-1,1);
	view.registerWindow2D(w1);

	var donut2DPlugin = new JenScript.Donut2DPlugin.Plugin();
	w1.registerPlugin(donut2DPlugin);

	var donut2d = new JenScript.Donut2DPlugin.Donut2D();
	donut2d.setX(0);
	donut2d.setY(0);
	donut2d.setInnerRadius(90);
	donut2d.setOuterRadius(120);

	donut2DPlugin.addDonut2D(donut2d);

	var s1 = new JenScript.Donut2DPlugin.Slice(45, colorutil.getColor(240,240,240,255));
	var s2 = new JenScript.Donut2DPlugin.Slice(5, colorutil.getColor(249,235,113,255));
	var s3 = new JenScript.Donut2DPlugin.Slice(30, colorutil.getColor(176,224,88,255));
	var s4 = new JenScript.Donut2DPlugin.Slice(20, colorutil.getColor(143,177,214,255));	

	donut2d.addSlice(s1);
	donut2d.addSlice(s2);
	donut2d.addSlice(s3);
	donut2d.addSlice(s4);

	var stroke1 = new JenScript.Stroke(2);
	
	var radialFill = new JenScript.Donut2DPlugin.Donut2DRadialFill();
	radialFill.setGradientType('SC_D_SC');
	donut2d.setFill(radialFill);
	
	//var fx = new JenScript.Donut2DPlugin.Donut2DLinearEffect();
	//donut2d.setEffect(fx);
	var fx = new JenScript.Donut2DPlugin.Donut2DReflectionEffect();
	fx.setBlurEnable(false);
	donut2d.setEffect(fx);

	var op = new JenScript.OutlinePlugin.Plugin();
	w1.registerPlugin(op);

	var x2d = view.getX2D();
	//alert(x2d);
	view.requestView();
}

function createDonut2DCompoundEffect(){
	JenScript.Core.jetServer = 'http://localhost:8888/JenScript'
	var colorutil = new JenScript.ColorUtil();
	var black = colorutil.getColor(0, 0, 0, 255);
	var black2 = colorutil.getColor(0, 0, 0, 80);
	var white = colorutil.getColor(255, 255, 255, 255);
	var redwood 	= colorutil.getColor(203,71,52,255);
	var redwoodAlpha 	= colorutil.getColor(203,71,52,100);
	var lime 		= colorutil.getColor(197,208,89,255);
	var cobalt 		= colorutil.getColor(35,56,158,255);
	var emerald 	= colorutil.getColor(62,142,78,255);
	var night 	= colorutil.getColor(32,39,55,200);
	var fractions = [0,1];
	var colors = [night,black];
	var textColors = [emerald,cobalt];
	var shader = new JenScript.Shader(fractions, colors);
	var stroke2 = new JenScript.Stroke(2);

	var textShader = new JenScript.Shader(fractions, textColors);
	var shader = new JenScript.Shader(fractions, colors);
	var stroke2 = new JenScript.Stroke(2);


	var view = new JenScript.View2D(800,600,'container');

	var w1 = new JenScript.Window2D('w1',-1,1,-1,1);
	view.registerWindow2D(w1);

	var donut2DPlugin = new JenScript.Donut2DPlugin.Plugin();
	w1.registerPlugin(donut2DPlugin);

	var donut2d = new JenScript.Donut2DPlugin.Donut2D();
	donut2d.setX(0);
	donut2d.setY(0);
	donut2d.setInnerRadius(90);
	donut2d.setOuterRadius(120);

	donut2DPlugin.addDonut2D(donut2d);

	var s1 = new JenScript.Donut2DPlugin.Slice(45, colorutil.getColor(240,240,240,255));
	var s2 = new JenScript.Donut2DPlugin.Slice(5, colorutil.getColor(249,235,113,255));
	var s3 = new JenScript.Donut2DPlugin.Slice(30, colorutil.getColor(176,224,88,255));
	var s4 = new JenScript.Donut2DPlugin.Slice(20, colorutil.getColor(143,177,214,255));	

	donut2d.addSlice(s1);
	donut2d.addSlice(s2);
	donut2d.addSlice(s3);
	donut2d.addSlice(s4);

	var stroke1 = new JenScript.Stroke(2);
	
	var radialFill = new JenScript.Donut2DPlugin.Donut2DRadialFill();
	radialFill.setGradientType('SC_D_SC');
	donut2d.setFill(radialFill);
	
	var fx1 = new JenScript.Donut2DPlugin.Donut2DLinearEffect();
	
	var fx2 = new JenScript.Donut2DPlugin.Donut2DReflectionEffect();
	fx2.setBlurEnable(false);
	
	var fxCompound = new JenScript.Donut2DPlugin.Donut2DCompoundEffect();
	fxCompound.addEffect(fx1);
	fxCompound.addEffect(fx2);
	donut2d.setEffect(fxCompound);

	var op = new JenScript.OutlinePlugin.Plugin();
	w1.registerPlugin(op);

	var x2d = view.getX2D();
	//alert(x2d);
	view.requestView();
}