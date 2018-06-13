package com.hy.wanandroid.net.cookie;

import okhttp3.CookieJar;

/**
 * author: huyin
 * date: 2018/6/13
 */
public interface ClearableCookieJar extends CookieJar {

    /**
     * Clear all the session cookies while maintaining the persisted ones.
     */
    void clearSession();

    /**
     * Clear all the cookies from persistence and from the cache.
     */
    void clear();
}
