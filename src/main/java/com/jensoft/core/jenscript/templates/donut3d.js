//*******************************
// Donut 3D JenScript Templates
// 
//*******************************

function createDonut3D(){
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

	var donut3DPlugin = new JenScript.Donut3DPlugin.Plugin();
	w1.registerPlugin(donut3DPlugin);

	var donut3d = new JenScript.Donut3DPlugin.Donut3D();
	donut3d.setX(0);
	donut3d.setY(0);
	donut3d.setInnerRadius(90);
	donut3d.setOuterRadius(120);
	donut3d.setThickness(60);
	donut3d.setTilt(40);
	

	donut3DPlugin.addDonut3D(donut3d);

	var s1 = new JenScript.Donut3DPlugin.Slice(45, colorutil.getColor(240,240,240,255));
	var s2 = new JenScript.Donut3DPlugin.Slice(5, colorutil.getColor(249,235,113,255));
	var s3 = new JenScript.Donut3DPlugin.Slice(30, colorutil.getColor(176,224,88,255));
	var s4 = new JenScript.Donut3DPlugin.Slice(20, colorutil.getColor(143,177,214,255));

	donut3d.addSlice(s1);
	donut3d.addSlice(s2);
	donut3d.addSlice(s3);
	donut3d.addSlice(s4);

	
	var op = new JenScript.OutlinePlugin.Plugin();
	w1.registerPlugin(op);

	var x2d = view.getX2D();
	alert(x2d);
	view.requestView();
}

function createDonut3DBorderLabel(){
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

	var donut3DPlugin = new JenScript.Donut3DPlugin.Plugin();
	w1.registerPlugin(donut3DPlugin);

	var donut3d = new JenScript.Donut3DPlugin.Donut3D();
	donut3d.setX(0);
	donut3d.setY(0);
	donut3d.setInnerRadius(90);
	donut3d.setOuterRadius(120);
	donut3d.setThickness(60);
	donut3d.setTilt(40);
	

	donut3DPlugin.addDonut3D(donut3d);

	var s1 = new JenScript.Donut3DPlugin.Slice(45, colorutil.getColor(240,240,240,255));
	var s2 = new JenScript.Donut3DPlugin.Slice(5, colorutil.getColor(249,235,113,255));
	var s3 = new JenScript.Donut3DPlugin.Slice(30, colorutil.getColor(176,224,88,255));
	var s4 = new JenScript.Donut3DPlugin.Slice(20, colorutil.getColor(143,177,214,255));

	donut3d.addSlice(s1);
	donut3d.addSlice(s2);
	donut3d.addSlice(s3);
	donut3d.addSlice(s4);

	var stroke1 = new JenScript.Stroke(2);
	
	var borderLabel= new JenScript.Donut3DPlugin.Donut3DBorderLabel();
	borderLabel.setMargin(60);
	borderLabel.setTextColor(white);
	borderLabel.setShader(shader);
	borderLabel.setOutlineRound(16);
	borderLabel.setOutlineStroke(stroke1);
	s1.setSliceLabel(borderLabel);

	var borderLabel2= new JenScript.Donut3DPlugin.Donut3DBorderLabel();
	borderLabel2.setMargin(60);
	borderLabel2.setTextColor(white);
	borderLabel2.setShader(shader);
	borderLabel2.setOutlineRound(16);
	borderLabel2.setOutlineStroke(stroke1);
	s2.setSliceLabel(borderLabel2);
	
	var borderLabel3 = new JenScript.Donut3DPlugin.Donut3DBorderLabel();
	borderLabel3.setMargin(60);
	borderLabel3.setTextColor(white);
	borderLabel3.setShader(shader);
	borderLabel3.setOutlineRound(16);
	borderLabel3.setOutlineStroke(stroke1);
	s3.setSliceLabel(borderLabel3);
	
	var borderLabel4= new JenScript.Donut3DPlugin.Donut3DBorderLabel();
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
	alert(x2d);
	view.requestView();
}

function createDonut3DRadialLabel(){
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

	var donut3DPlugin = new JenScript.Donut3DPlugin.Plugin();
	w1.registerPlugin(donut3DPlugin);

	var donut3d = new JenScript.Donut3DPlugin.Donut3D();
	donut3d.setX(0);
	donut3d.setY(0);
	donut3d.setInnerRadius(90);
	donut3d.setOuterRadius(120);
	donut3d.setThickness(60);
	donut3d.setTilt(40);
	

	donut3DPlugin.addDonut3D(donut3d);

	var s1 = new JenScript.Donut3DPlugin.Slice(45, colorutil.getColor(240,240,240,255));
	var s2 = new JenScript.Donut3DPlugin.Slice(5, colorutil.getColor(249,235,113,255));
	var s3 = new JenScript.Donut3DPlugin.Slice(30, colorutil.getColor(176,224,88,255));
	var s4 = new JenScript.Donut3DPlugin.Slice(20, colorutil.getColor(143,177,214,255));

	donut3d.addSlice(s1);
	donut3d.addSlice(s2);
	donut3d.addSlice(s3);
	donut3d.addSlice(s4);

	var stroke1 = new JenScript.Stroke(2);

	var radialLabel= new JenScript.Donut3DPlugin.Donut3DRadialLabel();
	radialLabel.setOffsetRadius(60);
	radialLabel.setTextColor(white);
	radialLabel.setShader(shader);
	radialLabel.setOutlineRound(16);
	radialLabel.setOutlineStroke(stroke1);
	s1.setSliceLabel(radialLabel);

	var radialLabel2= new JenScript.Donut3DPlugin.Donut3DRadialLabel();
	radialLabel2.setOffsetRadius(60);
	radialLabel2.setTextColor(white);
	radialLabel2.setShader(shader);
	radialLabel2.setOutlineRound(16);
	radialLabel2.setOutlineStroke(stroke1);
	s2.setSliceLabel(radialLabel2);
	
	var radialLabel3 = new JenScript.Donut3DPlugin.Donut3DRadialLabel();
	radialLabel3.setOffsetRadius(60);
	radialLabel3.setTextColor(white);
	radialLabel3.setShader(shader);
	radialLabel3.setOutlineRound(16);
	radialLabel3.setOutlineStroke(stroke1);
	s3.setSliceLabel(radialLabel3);
	
	var radialLabel4= new JenScript.Donut3DPlugin.Donut3DRadialLabel();
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
	alert(x2d);
	view.requestView();
}

function createDonut3DPathLabel(){
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
	var textShader = new JenScript.Shader(fractions, textColors);
	var shader = new JenScript.Shader(fractions, colors);
	var stroke2 = new JenScript.Stroke(2);

	var view = new JenScript.View2D(800,600,'container');

	var w1 = new JenScript.Window2D('w1',-1,1,-1,1);
	view.registerWindow2D(w1);

	var donut3DPlugin = new JenScript.Donut3DPlugin.Plugin();
	w1.registerPlugin(donut3DPlugin);

	var donut3d = new JenScript.Donut3DPlugin.Donut3D();
	donut3d.setX(0);
	donut3d.setY(0);
	donut3d.setInnerRadius(70);
	donut3d.setOuterRadius(120);
	donut3d.setThickness(60);
	donut3d.setTilt(40);
	

	donut3DPlugin.addDonut3D(donut3d);

	var s1 = new JenScript.Donut3DPlugin.Slice(45, colorutil.getColor(240,240,240,255));
	var s2 = new JenScript.Donut3DPlugin.Slice(5, colorutil.getColor(249,235,113,255));
	var s3 = new JenScript.Donut3DPlugin.Slice(30, colorutil.getColor(176,224,88,255));
	var s4 = new JenScript.Donut3DPlugin.Slice(20, colorutil.getColor(143,177,214,255));

	donut3d.addSlice(s1);
	donut3d.addSlice(s2);
	donut3d.addSlice(s3);
	donut3d.addSlice(s4);

	var stroke1 = new JenScript.Stroke(2);

	var pathLabel = new JenScript.Donut3DPlugin.Donut3DPathLabel();
	pathLabel.setTextPosition('right');
	pathLabel.setText("JenSoft API");
	pathLabel.setPathSide('below');
	pathLabel.setTextShader(textShader);
	s1.setSliceLabel(pathLabel);
	
	var op = new JenScript.OutlinePlugin.Plugin();
	w1.registerPlugin(op);

	var x2d = view.getX2D();
	alert(x2d);
	view.requestView();
}