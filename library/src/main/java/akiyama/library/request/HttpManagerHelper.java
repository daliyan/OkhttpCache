package akiyama.library.request;

import android.net.Uri;
import android.text.TextUtils;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

import akiyama.library.exception.NetworkException;
import akiyama.library.helper.NetworkHelper;
import akiyama.library.helper.SdHelper;
import akiyama.library.interceptor.CacheInterceptor;
import okhttp3.Cache;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 使用Okhttp实现网络层访问
 * Created by wenhao on 16-1-28.
 */
public class HttpManagerHelper implements IGetRequest{
    public static final String REQUEST_CACHE_TYPE_HEAD = "requestCacheType";//请求缓存类型
    private static final HttpManagerHelper INSTANCE = new HttpManagerHelper();

    private final OkHttpClient mOkHttpClient;
    private HttpManagerHelper(){
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new CacheInterceptor())
                .cache(getCache())
                .build();

    }

    private Cache getCache(){
        File httpCacheDirectory = new File(SdHelper.getDiskCacheDir(), "httpCache");
        int cacheSize = 10 * 1024 * 1024;//确定10M大小的缓存
        return new Cache(httpCacheDirectory, cacheSize);
    }

    public static HttpManagerHelper getInstance(){
        return INSTANCE;
    }


    //同步Get请求
    @Override
    public String getRequest(String url, List<NameValuePair> params) throws NetworkException {
        return getRequest(url, params, false,null, CacheType.ONLY_NETWORK);
    }

    @Override
    public String getRequest(String url, List<NameValuePair> params, List<NameValuePair> headers) throws NetworkException{
        return getRequest(url, params, false, headers,CacheType.ONLY_NETWORK);
    }

    @Override
    public String getRequest(String url, List<NameValuePair> params, boolean isAcceptGzipResponse) throws NetworkException{
        return getRequest(url,params,isAcceptGzipResponse,null,CacheType.ONLY_NETWORK);
    }

    @Override
    public String getRequestByCache(String url, List<NameValuePair> params, int cacheType) throws NetworkException {
        return getRequest(url,params,false,null,cacheType);
    }

    @Override
    public String getRequestByCache(String url, List<NameValuePair> params, List<NameValuePair> headers, int cacheType) throws NetworkException {
        return getRequest(url,params,false,headers,cacheType);
    }

    @Override
    public String getRequestByCache(String url, List<NameValuePair> params, boolean isAcceptGzipResponse,int cacheType) throws NetworkException {
        return getRequest(url,params,isAcceptGzipResponse,null,cacheType);
    }

    @Override
    public String getRequestByCache(String url, List<NameValuePair> params, boolean isAcceptGzipResponse, List<NameValuePair> headers, int cacheType) throws NetworkException {
        return getRequest(url,params,isAcceptGzipResponse,null,cacheType);
    }

    @Override
    public Response okHttpGet(String url, List<NameValuePair> params) throws IOException, NetworkException{
        return okHttpGet(url,params,null,CacheType.ONLY_NETWORK);
    }



    @Override
    public String getRequest(String url, List<NameValuePair> params, boolean isAcceptGzipResponse, List<NameValuePair> headers,int cacheType) throws NetworkException{
        String result = "";
        Response response = null;
        try {
            response = okHttpGet(url, params,headers,cacheType);
            if(response.isSuccessful()){
                if("gzip".equalsIgnoreCase(response.header("Content-Encoding")) || isAcceptGzipResponse){
                    InputStream inputStream = response.body().byteStream();
                    BufferedInputStream bis = new BufferedInputStream(inputStream);
                    inputStream = new GZIPInputStream(bis);
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader br = new BufferedReader(inputStreamReader);
                    StringBuffer sb = new StringBuffer("");
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                        sb.append("\n");
                    }
                    inputStreamReader.close();
                    br.close();
                    result = sb.toString();
                }else{
                    //没进行压缩
                    result = response.body().string();
                }
            }else{
                NetworkException e = new NetworkException("服务器不稳定或发生错误,响应错误(响应代码为"+response.code()+")"+",请重试!");
                throw e;
            }
        } catch (ConnectException e){
            throw new NetworkException("网络错误，请检查网络！", e);
        } catch (SocketTimeoutException e){
            throw new NetworkException("链接超时", e);
        } catch (UnknownHostException e){
            throw new NetworkException("获取服务端信息失败", e);
        } catch (IOException e) {
            throw new NetworkException("网络获取服务端信息异常",e);
        }
        return result;
    }


    /**
     * HTTP GET最终调用的方法
     * @param url
     * @param params
     * @param headers
     * @return
     * @throws IOException
     * @throws NetworkException
     */
    private Response okHttpGet(String url, List<NameValuePair> params, List<NameValuePair> headers,int cacheType) throws IOException, NetworkException{
        if(params == null){
            params = new ArrayList<>();
        }
        int currentCacheType;
        //网络无效的话指定读取缓存策略
        if(!NetworkHelper.isConnect()){
            currentCacheType = CacheType.ONLY_CACHE;
        } else {
            currentCacheType = cacheType;
        }
        String parameters = getRequestParameters(params);
        Uri uri = Uri.parse(url);
        if(TextUtils.isEmpty(uri.getQuery())){
            url = url + "?" + parameters;
        }else{
            url = url + "&" + parameters;
        }
        Request.Builder builder = new Request.Builder();
        if(headers!=null && !headers.isEmpty()){
            builder.headers(getRequestHeaders(headers));
        }
        final Request request = builder.url(url).addHeader(REQUEST_CACHE_TYPE_HEAD,String.valueOf(currentCacheType)).build();
        return mOkHttpClient.newCall(request).execute();
    }

    private String getRequestUrl(String url, List<NameValuePair> params){
        StringBuilder urlSb = new StringBuilder(url);
        urlSb.append('?');
        for(NameValuePair nameValuePair : params){
            urlSb.append(nameValuePair.key).append('=').append(nameValuePair.value).append("&");
        }
        return urlSb.substring(0, urlSb.length()-1);
    }

    /**
     * GET请求参数拼接，使用UTF编码*/
    public static String getRequestParameters(List<NameValuePair> params) throws NetworkException{
        String requestParameters = "";
        try {
            StringBuilder paramSb = new StringBuilder();
            for(NameValuePair nameValuePair : params){
                if(nameValuePair.value == null){
                    nameValuePair.value = "";
                }
                paramSb.append(nameValuePair.key).append("=").append(URLEncoder.encode(nameValuePair.value, "UTF-8")).append("&");
            }
            if(paramSb.length() > 0) {
                requestParameters = paramSb.substring(0, paramSb.length() - 1);
            }
        }catch (UnsupportedEncodingException e){
            throw new NetworkException("客户端请求参数编码异常",e);
        }
        return requestParameters;
    }

    private Headers getRequestHeaders(List<NameValuePair> headers){
        Headers.Builder headerBuilder = new Headers.Builder();
        for(NameValuePair header : headers){
            headerBuilder.add(header.key, header.value);
        }
        return headerBuilder.build();
    }


}
