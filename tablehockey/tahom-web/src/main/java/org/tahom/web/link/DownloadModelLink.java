package org.tahom.web.link;

import java.io.File;

import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

public class DownloadModelLink extends DownloadLink {

	private static final long serialVersionUID = 1L;

	public DownloadModelLink(String id, IModel<File> model) {
		super(id, model);
		setBody(new ResourceModel(id));
	}

	public DownloadModelLink(String id, File file) {
		super(id, file);
		setBody(new ResourceModel(id));
	}

}
