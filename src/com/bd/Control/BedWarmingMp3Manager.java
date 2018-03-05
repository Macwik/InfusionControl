package com.bd.Control;

import com.bd.Control.Util.Mp3Utils;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @description：
 * @author: zhangc
 * @date：2018/3/5 22:59
 */
public class BedWarmingMp3Manager {

    public static final HashSet<String> set = new HashSet<>();

    public static void  addWarming(String bedNo){
        if (!set.contains(bedNo)){
            set.add(bedNo);
            Mp3Utils.playWarmMp3();
        }
    }

    public static void  removeWarming(String bedNo){
        if (set.contains(bedNo)){
            set.remove(bedNo);
            Mp3Utils.removePlayWarmMp3();
        }
    }
}
