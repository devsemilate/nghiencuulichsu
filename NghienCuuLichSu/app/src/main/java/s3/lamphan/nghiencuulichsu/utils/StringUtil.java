package s3.lamphan.nghiencuulichsu.utils;

import android.net.Uri;
import android.util.Log;

/**
 * Created by lam.phan on 11/4/2016.
 */
public class StringUtil {
    public static boolean isStringEmpty(String input){
        return (input == null || input.trim().length() == 0);
    }

    private static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
    public static String makeUnicodeUrl(String originLink)
    {
        String unicodeLink = Uri.encode(originLink, ALLOWED_URI_CHARS);
        return unicodeLink;
    }
}
