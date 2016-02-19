package org.tahom.web.link;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.tahom.web.model.ActiveReplaceModel;

public class BookmarkableModelPageLink<T> extends BookmarkablePageLink<T> {

    private static final long serialVersionUID = 1L;

    public <C extends Page> BookmarkableModelPageLink(String id, Class<C> pageClass) {
        this(id, pageClass, null, null);
    }

    public <C extends Page> BookmarkableModelPageLink(String id, Class<C> pageClass, Boolean active) {
        this(id, pageClass, null, active);
    }

    public <C extends Page> BookmarkableModelPageLink(String id, Class<C> pageClass, PageParameters parameters,
            Boolean active) {
        super(id, pageClass, parameters);
        setBody(new ResourceModel(id));
        setVisible(false);
        if (active != null) {
            add(new AttributeModifier("class", new ActiveReplaceModel(active)));
        }
    }
}
