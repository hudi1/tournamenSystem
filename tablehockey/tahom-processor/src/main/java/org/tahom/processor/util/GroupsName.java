package org.tahom.processor.util;

import java.util.LinkedList;

public class GroupsName extends LinkedList<String> {

	private static final long serialVersionUID = 1L;

	private boolean skip;

	public GroupsName(boolean skip) {
		this.skip = skip;
		add("A");
		add("A2");
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

	public GroupsName() {
		this(true);
	}

	public String getNext(String groupName) {
		int idx = indexOf(groupName);
		if (idx < 0 || idx + 1 == size())
			return "";
		if (skip && idx == 0) {
			idx++;
		}

		return get(idx + 1);
	}

	public String getPrevious(String groupName) {
		int idx = indexOf(groupName);
		if (idx <= 0)
			return "";
		if (skip && idx == 2) {
			idx--;
		}

		return get(idx - 1);
	}

}
