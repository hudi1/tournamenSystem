package org.tahom.processor.util;

import java.util.LinkedList;

public class GroupsName extends LinkedList<String> {

    private static final long serialVersionUID = 1L;

    public GroupsName() {
        add("A");
        add("B");
        add("C");
        add("D");
        add("E");
        add("F");
        add("G");
        add("H");
        add("I");
        add("J");
        add("K");
        add("L");
    }

    public String getNext(String groupName) {
        int idx = indexOf(groupName);
        if (idx < 0 || idx + 1 == size())
            return "";
        return get(idx + 1);
    }

    public String getPrevious(String groupName) {
        int idx = indexOf(groupName);
        if (idx <= 0)
            return "";
        return get(idx - 1);
    }

}
