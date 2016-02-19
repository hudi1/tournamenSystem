package org.tahom.web.link;

import java.io.File;

import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.util.encoding.UrlEncoder;
import org.apache.wicket.util.file.Files;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownloadModelLink extends DownloadLink {

    private static final long serialVersionUID = 1L;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public DownloadModelLink(String id, IModel<File> model) {
        super(id, model);
        setBody(new ResourceModel(id));
    }

    @Override
    public void onClick() {
        final File file = getModelObject();
        if (file != null) {

            String fileName = file.getName();
            final String fileNameEncoded = UrlEncoder.QUERY_INSTANCE.encode(fileName, getRequest().getCharset());

            IResourceStream resourceStream = new FileResourceStream(new org.apache.wicket.util.file.File(file));
            getRequestCycle().scheduleRequestHandlerAfterCurrent(
                    new ResourceStreamRequestHandler(resourceStream) {
                        @Override
                        public void respond(IRequestCycle requestCycle) {
                            super.respond(requestCycle);
                            boolean deleted = Files.remove(file);
                            if (deleted) {
                                logger.debug("File " + fileNameEncoded + " was succesfully deleted");
                            } else {
                                logger.debug("File " + fileNameEncoded + " was not deleted");
                            }
                        }
                    }.setFileName(fileNameEncoded).setContentDisposition(ContentDisposition.ATTACHMENT)
                            .setCacheDuration(Duration.NONE));
        }
    }

}
