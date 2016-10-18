package akiyama.library.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import akiyama.library.AppContext;

/**
 * Created by Administrator on 2016/10/17.
 */
public class NetworkHelper {

    public static boolean isConnect(){
        ConnectivityManager connectivityManager = (ConnectivityManager) AppContext.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null){
            return networkInfo.isAvailable();
        }
        return false;
    }
}
