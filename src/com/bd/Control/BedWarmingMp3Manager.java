package com.bd.Control;

import java.util.HashSet;

import com.bd.Control.Util.Mp3Utils;

/**
 * @description：
 * 
 * @author: zhangc @date：2018/3/5 22:59
 */
public class BedWarmingMp3Manager {

	public static final HashSet<String> warmingSet = new HashSet<>();
	// public static final Set<String> quitSet = new HashSet<>();

	public static void addWarming(String bedNo) {
		if (!warmingSet.contains(bedNo)) {
			warmingSet.add(bedNo);
			Mp3Utils.playWarmMp3();
		}
	}

	// public static void addQuit(String bedNo) {
	// if (!quitSet.contains(bedNo)) {
	// quitSet.add(bedNo);
	// Mp3Utils.addQuit();
	// }
	// }

	public static void removeWarming(String bedNo) {
		if (warmingSet.contains(bedNo)) {
			warmingSet.remove(bedNo);
			Mp3Utils.removePlayWarmMp3();
		}
	}

	// public static void removeQuit(String bedNo) {
	// if (quitSet.contains(bedNo)) {
	// quitSet.remove(bedNo);
	// Mp3Utils.removeQuit();
	// }
	// }
}
