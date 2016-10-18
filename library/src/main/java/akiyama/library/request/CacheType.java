package akiyama.library.request;

/**
 * 缓存的几种模式
 */
public class CacheType {
    /**
     * 无效缓存
     */
    public static final int INVALID_TYPE = -1;
    /**
     * 只读取缓存
     */
    public static final int ONLY_CACHE = 0;
    /**
     * 只读取网络
     */
    public static final int ONLY_NETWORK = 1;
    /**
     * 读取缓存，如果缓存不存在则读取网络
     */
    public static final int CACHE_ELSE_NETWORK= 2;
    /**
     * 先读取网络，如果网络请求失败则读取缓存
     */
    public static final int NETWORK_ELSE_CACHE = 3;
}
