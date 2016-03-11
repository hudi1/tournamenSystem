package org.tahom.web.mask;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.ajax.markup.html.AjaxIndicatorAppender;
import org.apache.wicket.request.Response;

/**
 * <b>WicketAjaxBusyIndicatingMask</b>
 * 
 * @author valentine.wu
 * @since January 10, 2009
 */

public class BusyIndicatingMaskAppender extends AjaxIndicatorAppender {
	private static final long serialVersionUID = 1L;

	private final String maskKey;

	public BusyIndicatingMaskAppender() {
		this("maskText");
	}

	public BusyIndicatingMaskAppender(final String maskKey) {
		this.maskKey = maskKey;
	}

	@Override
	public void afterRender(Component component) {
		final Response r = component.getResponse();

		r.write("<div style=\"display:none; text-align: center;\" class=\"");
		r.write(getSpanClass());
		r.write("\" ");
		r.write("id=\"");
		r.write(getMarkupId());
		r.write("\">");
		r.write("<img src=\"");
		r.write(getIndicatorUrl());
		r.write("\" alt=\"\"/></span>");
		r.write(component.getString(maskKey));
		r.write("</div>");
	}

}