package org.tahom.web.finder;

import org.apache.wicket.core.util.resource.locator.ResourceStreamLocator;
import org.apache.wicket.util.resource.IResourceStream;

public class CustomResourceStreamLocator extends ResourceStreamLocator {

    @Override
    public IResourceStream locate(Class<?> clazz, final String path) {
        String newPath = path;
        if (newPath.startsWith("org/toursys") && newPath.endsWith(".properties")) {
            newPath = path.substring(0, path.lastIndexOf("/")) + "/properties" + path.substring(path.lastIndexOf("/"));
        }
        return super.locate(clazz, newPath);
    }

}
