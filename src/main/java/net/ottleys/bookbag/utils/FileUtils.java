package net.ottleys.bookbag.utils;

import java.io.File;
import java.io.IOException;

import org.apache.tika.Tika;


public class FileUtils {

    private static final Tika tika = new Tika();
    
    private FileUtils(){
        //NOOP
    }

    public static String getMimetype(File file) {
        String mimetype = null;

        try {
            mimetype = tika.detect(file);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mimetype;

    }
    
}
