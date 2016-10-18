package akiyama.library.interceptor;

import android.util.Log;



import java.io.IOException;

import akiyama.library.request.CacheType;
import akiyama.library.request.HttpManagerHelper;
import akiyama.library.strategy.CacheNetworkStrategy;
import akiyama.library.strategy.CacheStrategy;
import akiyama.library.strategy.NetworkCacheStrategy;
import akiyama.library.strategy.NetworkStrategy;
import akiyama.library.strategy.RequestStrategy;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 缓存数据拦截器
 *
 * @author zhiwu_yan
 * @version 1.0
 * @since 2016-10-09  15:50
 */
public class CacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        RequestStrategy requestStrategy = new RequestStrategy();
        String cacheTypeHeader = chain.request().headers().get(HttpManagerHelper.REQUEST_CACHE_TYPE_HEAD);
        if (cacheTypeHeader != null) {
            int cacheType = Integer.parseInt(cacheTypeHeader);
            Log.e("111", "请求tag:" + cacheType + " 请求url:" + chain.request().url().toString());
            switch (cacheType) {
                case CacheType.ONLY_CACHE:
                    requestStrategy.setBaseRequestStrategy(new CacheStrategy());
                    break;
                case CacheType.ONLY_NETWORK:
                    requestStrategy.setBaseRequestStrategy(new NetworkStrategy());
                    break;
                case CacheType.CACHE_ELSE_NETWORK:
                    requestStrategy.setBaseRequestStrategy(new CacheNetworkStrategy());
                    break;
                case CacheType.NETWORK_ELSE_CACHE:
                    requestStrategy.setBaseRequestStrategy(new NetworkCacheStrategy());
                    break;
                case CacheType.INVALID_TYPE:
                    break;
                default:
                    break;
            }
        }
        return requestStrategy.request(chain);
    }
}
