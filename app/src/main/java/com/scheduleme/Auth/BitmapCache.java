package com.scheduleme.Auth;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by mauricio on 11/8/16.
 */

public class BitmapCache {
    private LruCache<String, Bitmap> mMemoryCache;
    static BitmapCache instance = null;
    protected BitmapCache(){
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize);
    }

    static BitmapCache getInstance(){
        if (instance == null) {
            instance = new BitmapCache();
        }
        return instance;
    }

    public void add(String key, Bitmap bitmap){
        mMemoryCache.put(key, bitmap);
    }

    public Bitmap get(String key){
        Bitmap bitmap = mMemoryCache.get(key);
        return bitmap;
    }
}
