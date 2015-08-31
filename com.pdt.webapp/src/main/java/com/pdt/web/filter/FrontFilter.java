/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pdt.cms.ui.util.CacheHelper;
import com.pdt.core.util.ServiceResolver;
import com.pdt.service.PdtTourService;
import com.pdt.util.Static;
import com.pdt.webapp.ui.model.MenuItemModel;

/**
 * @author hungpx
 *
 */
public class FrontFilter extends HandlerInterceptorAdapter {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		MenuItemModel menuItemModel = null;
		Cache topMenuCache = CacheHelper.getCache("topMenuCache");
		Element cachedMenuElement = topMenuCache.get(Static.TOP_MENU_CACHE_ELEMENT);
		if (cachedMenuElement != null)
			menuItemModel = (MenuItemModel) cachedMenuElement.getObjectValue();
		if (menuItemModel != null) {
			request.setAttribute(Static.REQUEST_TOP_MENUBAR, menuItemModel);
		} else {
			topMenuCache.put(new Element(Static.TOP_MENU_CACHE_ELEMENT, getMenuItems(request)));
		}

		return true;
	}

	private MenuItemModel getMenuItems(HttpServletRequest request) {
		PdtTourService pdtTourService = ServiceResolver.findService(PdtTourService.class);
		MenuItemModel menuitems = pdtTourService.getMenuItemModel();
		request.setAttribute(Static.REQUEST_TOP_MENUBAR, menuitems);
		return menuitems;
	}
}
