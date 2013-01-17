package com.jensoft.core.window;

import java.util.Calendar;


public class Window2DTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
//       System.out.println(new Date());
//       Window2D.TimeX txw = new Window2D.TimeX(new Date(new Date().getTime() - 60*10*1000), new Date(new Date().getTime() + 60*10*1000), 0, 100);
//       DecimalFormat myFormatter = new DecimalFormat();
//       System.out.println(myFormatter.format(txw.getMinX()));
//       System.out.println(myFormatter.format(txw.getMaxX()));
//       System.out.println(txw.getMinXAsDate());
//       System.out.println(txw.getMaxXAsDate());
//       
//       System.out.println(txw.getMinXAsDayOfMonth());
//       System.out.println(txw.getMaxXAsDayOfMonth());
//       System.out.println(txw.getMinXAsDayOfWeek());
//       System.out.println(txw.getMaxXAsDayOfWeek());
       
       Calendar cal = Calendar.getInstance();
       cal.setFirstDayOfWeek(Calendar.MONDAY);
      
       System.out.println(cal.get(Calendar.DATE));
       System.out.println(cal.get(Calendar.THURSDAY));
       
    }

}
