package klep.wehere.utils;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindString;
import klep.wehere.R;

/**
 * Created by klep.io on 31.01.16.
 */
public class ErrorCode {
    private static Map<Integer, Integer> CODE_ERROR = new HashMap<>();

    static {
        CODE_ERROR.put(0, R.string.error_0);
        CODE_ERROR.put(1, R.string.error_1);
        CODE_ERROR.put(2, R.string.error_2);
        CODE_ERROR.put(3, R.string.error_3);
        CODE_ERROR.put(4, R.string.error_4);
        CODE_ERROR.put(5, R.string.error_5);
        CODE_ERROR.put(6, R.string.error_6);
        CODE_ERROR.put(7, R.string.error_7);
    }


    public static String getCodeError(Context context,int errorCode){

        int code = CODE_ERROR.get(errorCode);

        return context.getResources().getString(code);
    }

}
