/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author august
 *
 */
@Controller
@RequestMapping("/content")
public class StaticContentController {

	@RequestMapping(value = "/service/{id}/{seoPath}")
	public String service() {
		return "service";
	}

	@RequestMapping(value = "/tip/{id}/{seoPath}")
	public String tip() {
		return "tip";
	}
}
