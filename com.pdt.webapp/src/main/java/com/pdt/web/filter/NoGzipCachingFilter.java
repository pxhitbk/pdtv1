/**
 *<p>
 *</p>
 * @author hungpx
 * @since
 */
package com.pdt.web.filter;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.constructs.web.AlreadyGzippedException;
import net.sf.ehcache.constructs.web.GenericResponseWrapper;
import net.sf.ehcache.constructs.web.Header;
import net.sf.ehcache.constructs.web.PageInfo;
import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;

/**
 * <p>
 * A caching filter with no gzipping to delegate the task to {@link net.sf.ehcache.constructs.web.filter.GzipFilter}.
 * </p>
 */
public class NoGzipCachingFilter extends SimplePageCachingFilter {
	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.ehcache.constructs.web.filter.CachingFilter#buildPage(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected PageInfo buildPage(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws AlreadyGzippedException, Exception {
		// Invoke the next entity in the chain
		final ByteArrayOutputStream outstr = new ByteArrayOutputStream();
		final GenericResponseWrapper wrapper = new GenericResponseWrapper(response, outstr);
		chain.doFilter(request, wrapper);
		wrapper.flush();

		long timeToLiveSeconds = blockingCache.getCacheConfiguration().getTimeToLiveSeconds();

		// Return the page info
		// Only one different comparing to super method is: PageInfo.storeGzipped = false
		boolean storeGzipped = isGzipped(wrapper.getAllHeaders());
		return new PageInfo(wrapper.getStatus(), wrapper.getContentType(), wrapper.getCookies(), outstr.toByteArray(),
				storeGzipped, timeToLiveSeconds, wrapper.getAllHeaders());
	}

	private boolean isGzipped(final Collection<Header<? extends Serializable>> headers) {
		for (final Header<? extends Serializable> header : headers)
			if ("gzip".equals(header.getValue())) //$NON-NLS-1$
				return true;
		return false;
	}

	@Override
	protected boolean acceptsGzipEncoding(final HttpServletRequest request) {
		return false; // fake it as if client doesn't support gzip
	}
}
