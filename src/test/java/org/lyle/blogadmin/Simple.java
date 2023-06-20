package org.lyle.blogadmin;

import cn.hutool.core.util.RandomUtil;

public class Simple {
	public static void main(String[] args) {


		int[] thumbSize = {1290, 800, 700, 1140, 1366, 600, 788, 600, 800, 589, 658};

		int r1 = RandomUtil.randomInt(0, 10);
		int r2 = RandomUtil.randomInt(0, 10);

		System.out.println(thumbSize[r1]);
		System.out.println(thumbSize[r2]);

	}
}
