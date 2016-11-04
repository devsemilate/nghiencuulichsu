package s3.lamphan.nghiencuulichsu.utils;

/**
 * Created by lam.phan on 11/4/2016.
 */
public class StringUtil {
    public static boolean isStringEmpty(String input){
        return (input == null || input.trim().length() == 0);
    }
}
