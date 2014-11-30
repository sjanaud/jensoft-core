package com.jensoft.core.plugin.metrics.manager;

import java.awt.Font;

import com.jensoft.core.palette.InputFonts;

public class TestDecimalFormat {

	
	public static void main(String[] args) {
//		DecimalFormat f1 = new DecimalFormat();
//		
//		DecimalFormat ffr = (DecimalFormat)NumberFormat.getInstance(Locale.FRANCE);
//		System.out.println(ffr.format(05678.7896d));
//		
//		DecimalFormat fus = (DecimalFormat)NumberFormat.getInstance(Locale.US);
//		System.out.println(fus.format(05678.7896d));
//		
//		double b = Math.pow(10, 0);
//		System.out.println("b = "+b);
//		
//		BigDecimal bd = new BigDecimal("2278.0067589970");
//		System.out.println("scale : "+bd.scale());
//		
//		DecimalFormat format = new DecimalFormat();
//		System.out.println("max : "+format.getMaximumFractionDigits());
//		System.out.println("min : "+format.getMinimumFractionDigits());
//		//format.setRoundingMode(RoundingMode.UNNECESSARY);
//		format.setMinimumFractionDigits(10);
//		format.setMaximumFractionDigits(10);
//		System.out.println("1:"+format.format(bd.doubleValue()));
//		
//		BigDecimal bdd = new BigDecimal("1E-5");
//		System.out.println("big decimal : "+bdd);
//		
//		BigDecimal bdd2 = new BigDecimal("1E+5");
//		System.out.println("big decimal : "+bdd2);
//		
//		DecimalFormat f = new DecimalFormat();
//		
//		f.setMinimumFractionDigits(6);
//		f.setMaximumFractionDigits(6);
//		System.out.println("exp-6:"+f.format(Math.pow(10, -6)));
//		
//		
//		System.out.println("exp-5:"+Math.pow(10, -5));
//		System.out.println("exp-4:"+Math.pow(10, -4));
//		System.out.println("exp-3:"+Math.pow(10, -3));
		
		Font f = InputFonts.getSansation(12);
		System.out.println(f.getSize());
		
		
	}
}
