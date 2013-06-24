package org.toursys.web.finder;

import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.locator.ResourceStreamLocator;

public class CustomResourceStreamLocator extends ResourceStreamLocator {

    @Override
    protected IResourceStream locateByClassLoader(Class<?> clazz, final String path) {
        String newPath = path;
        if (newPath.startsWith("org/toursys") && newPath.endsWith(".properties")) {
            newPath = path.substring(0, path.lastIndexOf("/")) + "/properties" + path.substring(path.lastIndexOf("/"));
        }
        return super.locateByClassLoader(clazz, newPath);
    }

}
