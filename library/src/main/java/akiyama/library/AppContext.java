package akiyama.library;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/10/17.
 */
public class AppContext extends Application{

    private static AppContext mContext;
    public AppContext() {
        mContext = this;
    }

    /**
     * Gets the application context.
     *
     * @return the application context
     */
    public static Context getContext() {
        return mContext;
    }
}
