package akiyama.library.strategy;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * HTTP请求策略
 *
 * @author zhiwu_yan
 * @version 1.0
 * @since 2016-09-28  10:08
 */
public class RequestStrategy {

    private BaseRequestStrategy mBaseRequestStrategy;

    public RequestStrategy(){

    }

    public RequestStrategy(BaseRequestStrategy baseRequestStrategy){
        this.mBaseRequestStrategy = baseRequestStrategy;
    }

    public void setBaseRequestStrategy(BaseRequestStrategy baseRequestStrategy) {
        mBaseRequestStrategy = baseRequestStrategy;
    }

    public Response request(Interceptor.Chain chain) throws IOException {
        Response response = null;
        if(mBaseRequestStrategy!=null){
            response = mBaseRequestStrategy.request(chain);
        }
        if(response==null){
            //确认response不能返回NULL 否则会抛出空指针异常,这里做一个处理
            Request request = chain.request();
            response = chain.proceed(request);
        }
        return response;
    }
}
