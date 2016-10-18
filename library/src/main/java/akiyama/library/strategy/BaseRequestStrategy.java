package akiyama.library.strategy;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * FIXME
 *
 * @author zhiwu_yan
 * @version 1.0
 * @since 2016-09-28  10:09
 */
public interface BaseRequestStrategy {
    /**
     * 请求策略
     * @param chain
     * @return
     */
   Response request(Interceptor.Chain chain) throws IOException;
}
