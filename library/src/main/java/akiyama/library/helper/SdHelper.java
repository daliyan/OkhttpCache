package akiyama.library.helper;

import android.content.Context;
import android.os.Environment;

import akiyama.library.AppContext;

/**
 * Created by Administrator on 2016/10/17.
 */
public class SdHelper {

    public static String getDiskCacheDir(){
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = AppContext.getContext().getExternalCacheDir().getPath();
        } else {
            cachePath = AppContext.getContext().getCacheDir().getPath();
        }
        return cachePath;
    }
}
