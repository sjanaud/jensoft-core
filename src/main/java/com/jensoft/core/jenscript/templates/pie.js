function createLinearPieEffect(){				

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

	var piePlugin = new JenScript.PiePlugin.Plugin();
	w1.registerPlugin(piePlugin);

	var pie = new JenScript.PiePlugin.Pie();
	pie.setX(0);
	pie.setY(0);
	pie.setRadius(120);

	var fx = new JenScript.PiePlugin.PieLinearEffect();
	pie.setEffect(fx);


	piePlugin.addPie(pie);

	var s1 = new JenScript.PiePlugin.Slice(45, colorutil.getColor(240,240,240,255));
	var s2 = new JenScript.PiePlugin.Slice(5, colorutil.getColor(249,235,113,255));
	var s3 = new JenScript.PiePlugin.Slice(30, colorutil.getColor(176,224,88,255));
	var s4 = new JenScript.PiePlugin.Slice(20, colorutil.getColor(143,177,214,255));

	

	pie.addSlice(s1);
	pie.addSlice(s2);
	pie.addSlice(s3);
	pie.addSlice(s4);

	
	var op = new JenScript.OutlinePlugin.Plugin();
	w1.registerPlugin(op);

	var x2d = view.getX2D();
	//alert(x2d);
	view.requestView();
}

function createPieBoundLabel(){				

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


	var night 	= colorutil.getColor(32,39,55,200);
	var black = colorutil.getColor(0, 0, 0, 255);
	var fractions = [0,1];
	var colors = [night,black];
	var shader = new JenScript.Shader(fractions, colors);


	var vb = new JenScript.ViewBackground();
	vb.setOutlineStroke(stroke2);
	vb.setOutlineRound(20);
	//view.setBackgroundPainter(vb);

	var w1 = new JenScript.Window2D('w1',-1,1,-1,1);
	view.registerWindow2D(w1);

	var piePlugin = new JenScript.PiePlugin.Plugin();
	w1.registerPlugin(piePlugin);

	var pie = new JenScript.PiePlugin.Pie();
	pie.setX(0);
	pie.setY(0);
	pie.setRadius(120);

	var fx = new JenScript.PiePlugin.PieLinearEffect();
	pie.setEffect(fx);


	piePlugin.addPie(pie);

	var s1 = new JenScript.PiePlugin.Slice(45, colorutil.getColor(240,240,240,255));
	var s2 = new JenScript.PiePlugin.Slice(5, colorutil.getColor(249,235,113,255));
	var s3 = new JenScript.PiePlugin.Slice(30, colorutil.getColor(176,224,88,255));
	var s4 = new JenScript.PiePlugin.Slice(20, colorutil.getColor(143,177,214,255));

	

	pie.addSlice(s1);
	pie.addSlice(s2);
	pie.addSlice(s3);
	pie.addSlice(s4);

	var stroke1 = new JenScript.Stroke(2);
	
	var borderLabel= new JenScript.PiePlugin.PieBoundLabel();
	
	borderLabel.setTextColor(white);
	borderLabel.setShader(shader);
	borderLabel.setOutlineRound(16);
	borderLabel.setOutlineStroke(stroke1);
	s1.setSliceLabel(borderLabel);

	var borderLabel2= new JenScript.PiePlugin.PieBoundLabel();
	
	borderLabel2.setTextColor(white);
	borderLabel2.setShader(shader);
	borderLabel2.setOutlineRound(16);
	borderLabel2.setOutlineStroke(stroke1);
	s2.setSliceLabel(borderLabel2);
	
	var borderLabel3 = new JenScript.PiePlugin.PieBoundLabel();
	
	borderLabel3.setTextColor(white);
	borderLabel3.setShader(shader);
	borderLabel3.setOutlineRound(16);
	borderLabel3.setOutlineStroke(stroke1);
	s3.setSliceLabel(borderLabel3);
	
	var borderLabel4= new JenScript.PiePlugin.PieBoundLabel();
	
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


function createPieRadialLabel(){				

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


	var night 	= colorutil.getColor(32,39,55,200);
	var black = colorutil.getColor(0, 0, 0, 255);
	var fractions = [0,1];
	var colors = [night,black];
	var shader = new JenScript.Shader(fractions, colors);


	var vb = new JenScript.ViewBackground();
	vb.setOutlineStroke(stroke2);
	vb.setOutlineRound(20);
	//view.setBackgroundPainter(vb);

	var w1 = new JenScript.Window2D('w1',-1,1,-1,1);
	view.registerWindow2D(w1);

	var piePlugin = new JenScript.PiePlugin.Plugin();
	w1.registerPlugin(piePlugin);

	var pie = new JenScript.PiePlugin.Pie();
	pie.setX(0);
	pie.setY(0);
	pie.setRadius(120);

	var fx = new JenScript.PiePlugin.PieLinearEffect();
	pie.setEffect(fx);


	piePlugin.addPie(pie);

	var s1 = new JenScript.PiePlugin.Slice(45, colorutil.getColor(240,240,240,255));
	var s2 = new JenScript.PiePlugin.Slice(5, colorutil.getColor(249,235,113,255));
	var s3 = new JenScript.PiePlugin.Slice(30, colorutil.getColor(176,224,88,255));
	var s4 = new JenScript.PiePlugin.Slice(20, colorutil.getColor(143,177,214,255));

	

	pie.addSlice(s1);
	pie.addSlice(s2);
	pie.addSlice(s3);
	pie.addSlice(s4);

	var stroke1 = new JenScript.Stroke(2);
	
	var borderLabel= new JenScript.PiePlugin.PieRadialLabel();
	borderLabel.setOffsetRadius(60);
	borderLabel.setTextColor(white);
	borderLabel.setShader(shader);
	borderLabel.setOutlineRound(16);
	borderLabel.setOutlineStroke(stroke1);
	s1.setSliceLabel(borderLabel);

	var borderLabel2= new JenScript.PiePlugin.PieRadialLabel();
	borderLabel2.setOffsetRadius(60);
	borderLabel2.setTextColor(white);
	borderLabel2.setShader(shader);
	borderLabel2.setOutlineRound(16);
	borderLabel2.setOutlineStroke(stroke1);
	s2.setSliceLabel(borderLabel2);
	
	var borderLabel3 = new JenScript.PiePlugin.PieRadialLabel();
	borderLabel3.setOffsetRadius(60);
	borderLabel3.setTextColor(white);
	borderLabel3.setShader(shader);
	borderLabel3.setOutlineRound(16);
	borderLabel3.setOutlineStroke(stroke1);
	s3.setSliceLabel(borderLabel3);
	
	var borderLabel4= new JenScript.PiePlugin.PieRadialLabel();
	borderLabel4.setOffsetRadius(60);
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

function createRadialPieEffect(){

	JenScript.Core.jetServer = 'http://localhost:8888/JenScript'
	var colorutil = new JenScript.ColorUtil();
	var black = colorutil.getColor(0, 0, 0, 255);
	var black2 = colorutil.getColor(0, 0, 0, 80);
	var white = colorutil.getColor(255, 255, 255, 255);
	var white2 = colorutil.getColor(240, 240, 240, 255);
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


	var night 	= colorutil.getColor(32,39,55,200);
	var black = colorutil.getColor(0, 0, 0, 255);
	var fractions = [0,1];
	var colors = [night,black];
	var shader = new JenScript.Shader(fractions, colors);


	var vb = new JenScript.ViewBackground();
	vb.setOutlineStroke(stroke2);
	vb.setOutlineRound(20);
	//view.setBackgroundPainter(vb);

	var w1 = new JenScript.Window2D('w1',-1,1,-1,1);
	view.registerWindow2D(w1);

	var piePlugin = new JenScript.PiePlugin.Plugin();
	w1.registerPlugin(piePlugin);

	var pie = new JenScript.PiePlugin.Pie();
	pie.setX(0);
	pie.setY(0);
	pie.setRadius(120);

	var fx = new JenScript.PiePlugin.PieRadialEffect();
	fx.setFocusAngle(320);
	fx.setFocusRadius(110);
	fx.setOffsetRadius(0);
	pie.setEffect(fx);


	piePlugin.addPie(pie);


	var s1 = new JenScript.PiePlugin.Slice(20,white2);
	var s2 = new JenScript.PiePlugin.Slice(30,lime);
	var s3 = new JenScript.PiePlugin.Slice(5,redwood);
	var s4 = new JenScript.PiePlugin.Slice(18,cobalt);

	pie.addSlice(s1);
	pie.addSlice(s2);
	pie.addSlice(s3);
	pie.addSlice(s4);

	var op = new JenScript.OutlinePlugin.Plugin();
	w1.registerPlugin(op);

	var x2d = view.getX2D();
	//alert(x2d);
	view.requestView();
}

function createPieBorderLabel(){				

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


	var night 	= colorutil.getColor(32,39,55,200);
	var black = colorutil.getColor(0, 0, 0, 255);
	var fractions = [0,1];
	var colors = [night,black];
	var shader = new JenScript.Shader(fractions, colors);


	var vb = new JenScript.ViewBackground();
	vb.setOutlineStroke(stroke2);
	vb.setOutlineRound(20);
	//view.setBackgroundPainter(vb);

	var w1 = new JenScript.Window2D('w1',-1,1,-1,1);
	view.registerWindow2D(w1);

	var piePlugin = new JenScript.PiePlugin.Plugin();
	w1.registerPlugin(piePlugin);

	var pie = new JenScript.PiePlugin.Pie();
	pie.setX(0);
	pie.setY(0);
	pie.setRadius(120);

	var fx = new JenScript.PiePlugin.PieLinearEffect(260);
	pie.setEffect(fx);
	fx.setIncidence(280);

	piePlugin.addPie(pie);

	var s1 = new JenScript.PiePlugin.Slice(45, colorutil.getColor(240,240,240,255));
	var s2 = new JenScript.PiePlugin.Slice(5, colorutil.getColor(249,235,113,255));
	var s3 = new JenScript.PiePlugin.Slice(30, colorutil.getColor(176,224,88,255));
	var s4 = new JenScript.PiePlugin.Slice(20, colorutil.getColor(143,177,214,255));

	pie.setStartAngleDegree(-20);

	pie.addSlice(s1);
	pie.addSlice(s2);
	pie.addSlice(s3);
	pie.addSlice(s4);

	var stroke1 = new JenScript.Stroke(2);
	
	
	var borderLabel= new JenScript.PiePlugin.PieBorderLabel();
	borderLabel.setMargin(60);
	borderLabel.setTextColor(white);
	borderLabel.setShader(shader);
	borderLabel.setOutlineRound(16);
	borderLabel.setOutlineStroke(stroke1);
	s1.setSliceLabel(borderLabel);

	var borderLabel2= new JenScript.PiePlugin.PieBorderLabel();
	borderLabel2.setMargin(60);
	borderLabel2.setTextColor(white);
	borderLabel2.setShader(shader);
	borderLabel2.setOutlineRound(16);
	borderLabel2.setOutlineStroke(stroke1);
	s2.setSliceLabel(borderLabel2);
	
	var borderLabel3 = new JenScript.PiePlugin.PieBorderLabel();
	borderLabel3.setMargin(60);
	borderLabel3.setTextColor(white);
	borderLabel3.setShader(shader);
	borderLabel3.setOutlineRound(16);
	borderLabel3.setOutlineStroke(stroke1);
	s3.setSliceLabel(borderLabel3);
	
	var borderLabel4= new JenScript.PiePlugin.PieBorderLabel();
	borderLabel4.setMargin(60);
	borderLabel4.setTextColor(white);
	borderLabel4.setShader(shader);
	borderLabel4.setOutlineRound(16);
	borderLabel4.setOutlineStroke(stroke1);
	borderLabel4.setText("aie ouille");
	s4.setSliceLabel(borderLabel4);
	
	
//
//	var radialLabel = new JenScript.PiePlugin.PieRadialLabel();
//	radialLabel.setOffsetRadius(30);
//	s3.setSliceLabel(radialLabel);
//
//	var pathLabel = new JenScript.PiePlugin.PiePathLabel();
//	pathLabel.setTextPosition('left');
//	s4.setSliceLabel(pathLabel);

	var op = new JenScript.OutlinePlugin.Plugin();
	w1.registerPlugin(op);

	var x2d = view.getX2D();
	//alert(x2d);
	view.requestView();
}

function createPiePathLabel(){				

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
	var view = new JenScript.View2D(800,600,'container');


	var night 	= colorutil.getColor(32,39,55,200);
	var black = colorutil.getColor(0, 0, 0, 255);
	var fractions = [0,1];
	var colors = [night,black];
	var shader = new JenScript.Shader(fractions, colors);


	var vb = new JenScript.ViewBackground();
	vb.setOutlineStroke(stroke2);
	vb.setOutlineRound(20);
	//view.setBackgroundPainter(vb);

	var w1 = new JenScript.Window2D('w1',-1,1,-1,1);
	view.registerWindow2D(w1);

	var piePlugin = new JenScript.PiePlugin.Plugin();
	w1.registerPlugin(piePlugin);

	var pie = new JenScript.PiePlugin.Pie();
	pie.setX(0);
	pie.setY(0);
	pie.setRadius(120);

	var fx = new JenScript.PiePlugin.PieLinearEffect(260);
	pie.setEffect(fx);
	

	piePlugin.addPie(pie);

	var s1 = new JenScript.PiePlugin.Slice(45, colorutil.getColor(240,240,240,255));
	var s2 = new JenScript.PiePlugin.Slice(5, colorutil.getColor(249,235,113,255));
	var s3 = new JenScript.PiePlugin.Slice(30, colorutil.getColor(176,224,88,255));
	var s4 = new JenScript.PiePlugin.Slice(20, colorutil.getColor(143,177,214,255));

	
	pie.addSlice(s1);
	pie.addSlice(s2);
	pie.addSlice(s3);
	pie.addSlice(s4);

	var pathLabel = new JenScript.PiePlugin.PiePathLabel();
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


function createCompoundPieEffect(){

	JenScript.Core.jetServer = 'http://localhost:8888/JenScript'
	var colorutil = new JenScript.ColorUtil();
	var black = colorutil.getColor(0, 0, 0, 255);
	var black2 = colorutil.getColor(0, 0, 0, 80);
	var white = colorutil.getColor(255, 255, 255, 255);
	var white2 = colorutil.getColor(240, 240, 240, 255);
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


	var night 	= colorutil.getColor(32,39,55,200);
	var black = colorutil.getColor(0, 0, 0, 255);
	var fractions = [0,1];
	var colors = [night,black];
	var shader = new JenScript.Shader(fractions, colors);


	var vb = new JenScript.ViewBackground();
	vb.setOutlineStroke(stroke2);
	vb.setOutlineRound(20);
	//view.setBackgroundPainter(vb);

	var w1 = new JenScript.Window2D('w1',-1,1,-1,1);
	view.registerWindow2D(w1);

	var piePlugin = new JenScript.PiePlugin.Plugin();
	w1.registerPlugin(piePlugin);

	var pie = new JenScript.PiePlugin.Pie();
	pie.setX(0);
	pie.setY(0);
	pie.setRadius(120);

	var fx1 = new JenScript.PiePlugin.PieRadialEffect();
	fx1.setFocusAngle(300);
	fx1.setFocusRadius(80);
	fx1.setOffsetRadius(10);
	
	var fx2 = new JenScript.PiePlugin.PieLinearEffect();
	
	var fxCompound = new JenScript.PiePlugin.PieCompoundEffect();
	fxCompound.addEffect(fx1);
	fxCompound.addEffect(fx2);
	pie.setEffect(fxCompound);


	piePlugin.addPie(pie);


	var s1 = new JenScript.PiePlugin.Slice(45, colorutil.getColor(240,240,240,255));
	var s2 = new JenScript.PiePlugin.Slice(5, colorutil.getColor(249,235,113,255));
	var s3 = new JenScript.PiePlugin.Slice(30, colorutil.getColor(176,224,88,255));
	var s4 = new JenScript.PiePlugin.Slice(20, colorutil.getColor(143,177,214,255));

	pie.addSlice(s1);
	pie.addSlice(s2);
	pie.addSlice(s3);
	pie.addSlice(s4);

	var op = new JenScript.OutlinePlugin.Plugin();
	w1.registerPlugin(op);

	var x2d = view.getX2D();
	alert(x2d);
	view.requestView();
}


function createReflectionPieEffect(){

	JenScript.Core.jetServer = 'http://localhost:8888/JenScript'
	var colorutil = new JenScript.ColorUtil();
	var black = colorutil.getColor(0, 0, 0, 255);
	var black2 = colorutil.getColor(0, 0, 0, 80);
	var white = colorutil.getColor(255, 255, 255, 255);
	var white2 = colorutil.getColor(240, 240, 240, 255);
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


	var night 	= colorutil.getColor(32,39,55,200);
	var black = colorutil.getColor(0, 0, 0, 255);
	var fractions = [0,1];
	var colors = [night,black];
	var shader = new JenScript.Shader(fractions, colors);


	var vb = new JenScript.ViewBackground();
	vb.setOutlineStroke(stroke2);
	vb.setOutlineColor(black);
	vb.setShader( new JenScript.Shader(fractions, [black,black]));
	
	vb.setOutlineRound(20);
	view.setBackgroundPainter(vb);

	var w1 = new JenScript.Window2D('w1',-1,1,-1,1);
	view.registerWindow2D(w1);

	var piePlugin = new JenScript.PiePlugin.Plugin();
	w1.registerPlugin(piePlugin);

	var pie = new JenScript.PiePlugin.Pie();
	pie.setX(0);
	pie.setY(0);
	pie.setRadius(120);

	var fx1 = new JenScript.PiePlugin.PieRadialEffect();
	fx1.setFocusAngle(320);
	fx1.setFocusRadius(70);
	fx1.setOffsetRadius(10);
	
	var fx2 = new JenScript.PiePlugin.PieLinearEffect();
	
	var fx3 = new JenScript.PiePlugin.PieReflectionEffect();
	
	
	var fxCompound = new JenScript.PiePlugin.PieCompoundEffect();
	fxCompound.addEffect(fx1);
	fxCompound.addEffect(fx2);
	fxCompound.addEffect(fx3);
	pie.setEffect(fxCompound);


	piePlugin.addPie(pie);


	var s1 = new JenScript.PiePlugin.Slice(45, colorutil.getColor(240,240,240,255));
	var s2 = new JenScript.PiePlugin.Slice(5, colorutil.getColor(249,235,113,255));
	var s3 = new JenScript.PiePlugin.Slice(30, colorutil.getColor(176,224,88,255));
	var s4 = new JenScript.PiePlugin.Slice(20, colorutil.getColor(143,177,214,255));

	pie.addSlice(s1);
	pie.addSlice(s2);
	pie.addSlice(s3);
	pie.addSlice(s4);

	var op = new JenScript.OutlinePlugin.Plugin();
	//w1.registerPlugin(op);

	var x2d = view.getX2D();
	alert(x2d);
	view.requestView();
}

