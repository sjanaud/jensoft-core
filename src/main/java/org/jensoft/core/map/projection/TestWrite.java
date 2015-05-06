package org.jensoft.core.map.projection;

import java.io.File;


public class TestWrite {

    /**
     * @param args
     */
    public static void main(String[] args) {
       File f= new File("c:/usr/testdir");
       System.out.println(f.mkdirs());
    }

}
