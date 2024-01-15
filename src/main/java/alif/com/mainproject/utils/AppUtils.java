package alif.com.mainproject.utils;

import java.util.Random;

public class AppUtils {

    public static String generateCode(){
        return String.valueOf(new Random().nextInt(0,999999));
    }
}
