package com.pdt.util;

import com.pdt.core.resources.LanguageType;

/**
 * Define global variables as properties of application.
 *
 * @author hungpx
 *
 */
public class Static {
	/** Language option for CMS system */
	public static final LanguageType CMS_LANGUAGE = LanguageType.EN;
	/** Language option for front end */
	public static final LanguageType FE_LANGUAGE = LanguageType.FR;

	public static final String REQUEST_TOP_MENUBAR = "TOP_MENUBAR";

	/** Match name to cache top menu in ehcache.xml */
	public static final String TOP_MENU_CACHE = "topMenuCache";
	public static final String TOP_MENU_CACHE_ELEMENT = "TOP_MENU_CACHE";

	/** Match name to cache tour list in ehcache.xml */
	public static final String TOUR_LIST_CACHE = "tourListCache";

	/* Predefine album names that should be match with the name in database */
	/** Album contain images slide in the home page */
	public static final String HOME_PAGE_ALBUM = "HOME_PAGE_ALBUM";

	/** Uri path map to resources folder that contains thumbnail images */
	public static final String UPLOAD_THUMBNAIL_DISK_PATH = "/resources/assets/uploads/tours/thumbnail";
	public static final String UPLOAD_THUMBNAIL_URI_PATH = "/resources/uploads/tours/thumbnail/";
	public static final String UPLOAD_TOUR_PHOTO_DISK_PATH = "/resources/assets/uploads/tours/photo";
	public static final String UPLOAD_TOUR_PHOTO_URI_PATH = "/resources/uploads/tours/photo/";
	public static final String UPLOAD_SLIDE_IMG_DISK_PATH = "/resources/assets/uploads/home/slide";
	public static final String UPLOAD_SLIDE_IMG_URI_PATH = "/resources/uploads/home/slide/";

	/** Max element in a list of front end view. This is for paging */
	public static final int PAGE_SIZE = 6;

	public static final String ERROR_PAGE_NOTFOUND = "notfound";

}
