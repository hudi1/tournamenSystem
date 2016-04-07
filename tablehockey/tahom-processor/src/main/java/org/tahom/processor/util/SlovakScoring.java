package org.tahom.processor.util;

import java.util.ArrayList;

public class SlovakScoring extends ArrayList<Integer> {

	private static final long serialVersionUID = 1L;

	public SlovakScoring() {
		add(100);
		add(93);
		add(87);
		add(82);
		add(78);
		add(72);
		add(66);
		add(60);
		add(54);
		add(52);
		add(50);
		add(48);
		add(46);
		add(44);
		add(42);
		add(40);
		add(38);
		add(36);
		add(34);
		add(32);
		add(30);
		add(29);
		add(28);
		add(27);
		add(26);
		add(25);
		add(24);
		add(23);
		add(22);
		add(21);
		add(20);
		add(19);
		add(18);
		add(17);
		add(16);
		add(15);
		add(14);
		add(13);
		add(12);
		add(11);
		add(10);
		add(9);
		add(8);
		add(7);
		add(6);
		add(5);
		add(4);
		add(3);
		add(2);
		add(1);
	}

	@Override
	public Integer get(int rank) {
		int index = rank - 1;
		try {
			return super.get(index);
		} catch (IndexOutOfBoundsException e) {
			return 0;
		}
	}

}
