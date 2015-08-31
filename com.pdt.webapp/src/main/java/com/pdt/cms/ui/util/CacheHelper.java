/**
 *<p>
 *</p>
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**
 * @author august
 *
 */
public class CacheHelper {
	/**
	 * Get cache that was stored in ehcache.xml by cache manager.
	 * 
	 * @param cacheName
	 *            name of cache, match the name in ehcache.xml
	 * @return cache object
	 */
	public static Cache getCache(String cacheName) {
		return CacheManager.getInstance().getCache(cacheName);
	}
}
