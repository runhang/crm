package com.chen.util;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public class EhCacheUtil {

	private static CacheManager cacheManager;
	
	static{
		cacheManager = new CacheManager();
	}
	
	private Ehcache cache;
	public EhCacheUtil(String cacheName) {
		cache = cacheManager.getEhcache(cacheName);
	}
	
	public void put(String key,Object value) {
		Element element = new Element(key,value);
		cache.put(element);
	}
	
	public Object get(String key) {
		Element ele = cache.get(key);
		if(ele == null) {
			return null;
		} else {
			return ele.getObjectValue();
		}
	}
	
	public void remove(String key) {
		cache.remove(key);
	}
	
}
