package org.tahom.processor.util;

import java.util.ArrayList;

public class CzechScoring extends ArrayList<Integer> {

	private static final long serialVersionUID = 1L;

	public CzechScoring() {
		add(160);
		add(152);
		add(146);
		add(141);
		add(137);
		add(134);
		add(132);
		add(131);
		add(128);
		add(127);
		add(126);
		add(125);
		add(124);
		add(123);
		add(122);
		add(121);
		add(119);
		add(118);
		add(117);
		add(116);
		add(115);
		add(114);
		add(113);
		add(112);
		add(111);
		add(109);
		add(107);
		add(106);
		add(105);
		add(104);
		add(103);
		add(102);
		add(100);
		add(99);
		add(98);
		add(97);
		add(96);
		add(95);
		add(94);
		add(93);
		add(92);
		add(91);
		add(89);
		add(88);
		add(86);
		add(84);
		add(83);
		add(82);
		add(81);
		add(80);
	}

	@Override
	public Integer get(int rank) {
		int index = rank - 1;
		try {
			return super.get(index);
		} catch (IndexOutOfBoundsException e) {
			if (rank > 130) {
				return 0;
			} else {
				return 80 + 51 - rank;
			}
		}
	}

}
