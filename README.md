# Okhttp缓存模式

-----
## DEMO

 解析知乎专栏API，读取专栏数据
 
 <img src="https://github.com/daliyan/OkhttpCache/blob/master/pic/S61018-224906.jpg" width = "405" height = "720"/>
 <img src="https://github.com/daliyan/OkhttpCache/blob/master/pic/S61018-224921.jpg" width = "405" height = "720"/>

 
## 使用方法
参考：
[使用例子MainActivity](https://github.com/daliyan/OkhttpCache/blob/master/sample/src/main/java/akiyama/okhttpcache/MainActivity.java)

```java

 String response = HttpManagerHelper.getInstance().getRequestByCache(ZHIHU_ZHUANLAN_API, nameValuePairs, CacheType.NETWORK_ELSE_CACHE);

```

提供四种缓存模式：

```java
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

```

## 已知问题

 - 目前Okhttp只是支持get请求缓存，后续考虑支持post缓存模式
