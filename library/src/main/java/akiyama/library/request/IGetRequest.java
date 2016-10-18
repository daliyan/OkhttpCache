package akiyama.library.request;


import java.io.IOException;
import java.util.List;

import akiyama.library.exception.NetworkException;
import okhttp3.Response;

/**
 * GET请求接口
 *
 * @author zhiwu_yan
 * @version 1.0
 * @since 2016-10-08  14:45
 */
public interface IGetRequest {
    String getRequest(String url, List<NameValuePair> params) throws NetworkException;
    String getRequest(String url, List<NameValuePair> params, List<NameValuePair> headers) throws NetworkException;
    String getRequest(String url, List<NameValuePair> params, boolean isAcceptGzipResponse) throws NetworkException;
    String getRequest(String url, List<NameValuePair> params, boolean isAcceptGzipResponse, List<NameValuePair> headers, int cacheType) throws NetworkException;

    /**
     * {@link CacheType#ONLY_CACHE}<br>
     * {@link CacheType#ONLY_NETWORK}<br>
     * {@link CacheType#NETWORK_ELSE_CACHE}<br>
     * {@link CacheType#CACHE_ELSE_NETWORK}<br>
     * @param url
     * @param params
     * @param isAcceptGzipResponse
     * @param cacheType
     * @return
     * @throws NetworkException
     */
    String getRequestByCache(String url, List<NameValuePair> params, boolean isAcceptGzipResponse, int cacheType) throws NetworkException;

    /**
     * {@link CacheType#ONLY_CACHE}<br>
     * {@link CacheType#ONLY_NETWORK}<br>
     * {@link CacheType#NETWORK_ELSE_CACHE}<br>
     * {@link CacheType#CACHE_ELSE_NETWORK}<br>
     * @param url
     * @param params
     * @param headers
     * @param cacheType
     * @return
     * @throws NetworkException
     */
    String getRequestByCache(String url, List<NameValuePair> params, List<NameValuePair> headers, int cacheType) throws NetworkException;

    /**
     * 4种缓存模式<br>
     * {@link CacheType#ONLY_CACHE}<br>
     * {@link CacheType#ONLY_NETWORK}<br>
     * {@link CacheType#NETWORK_ELSE_CACHE}<br>
     * {@link CacheType#CACHE_ELSE_NETWORK}<br>
     * @param url
     * @param params
     * @param cacheType {@link CacheType}
     * @return
     * @throws NetworkException
     */
    String getRequestByCache(String url, List<NameValuePair> params, int cacheType) throws NetworkException;

    /**
     * {@link CacheType#ONLY_CACHE}<br>
     * {@link CacheType#ONLY_NETWORK}<br>
     * {@link CacheType#NETWORK_ELSE_CACHE}<br>
     * {@link CacheType#CACHE_ELSE_NETWORK}<br>
     * @param url
     * @param params
     * @param isAcceptGzipResponse
     * @param headers
     * @param cacheType
     * @return
     * @throws NetworkException
     */
    String getRequestByCache(String url, List<NameValuePair> params, boolean isAcceptGzipResponse, List<NameValuePair> headers, int cacheType) throws NetworkException;
    Response okHttpGet(String url, List<NameValuePair> params) throws IOException, NetworkException;
}
