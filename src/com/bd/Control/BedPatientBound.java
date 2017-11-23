/**
 * 床位病人绑定
 */
package com.bd.Control;

import java.util.HashSet;

import com.bd.Control.InterfaceAndEnum.IBedPatientBound;
import com.bd.Control.Util.ArrayMap;
import com.bd.objects.PatientInfo;

public class BedPatientBound {

    private static ArrayMap<String, PatientInfo> bedPatientMap = new ArrayMap<String, PatientInfo>();
    private static HashSet<IBedPatientBound> bpListeners = new HashSet<IBedPatientBound>();

    public synchronized static void addBedPatientBoundListener(IBedPatientBound ibp) {
        bpListeners.add(ibp);
    }

    // *验证病床号和病人都未被添加
    public synchronized static boolean notContains(String num, PatientInfo patient) {
        return !bedPatientMap.containsKey(num) && !bedPatientMap.containsValue(patient);
    }

    // *验证病床号未被添加
    public synchronized static boolean notContainBed(String num) {
        return !bedPatientMap.containsKey(num);
    }

    // *判断是否包含绑定关系
    public synchronized static boolean contain(String num) {
        return bedPatientMap.containsKey(num);
    }

    // *增加床位和病人信息的绑定
    public synchronized static boolean addBedPatientBound(String bedNo, PatientInfo patient) {
        if (!notContains(bedNo, patient)) {
            return false;
        }
        bedPatientMap.put(bedNo, patient);

        notifyUpdate(bedNo);
        return true;
    }

    // *移除病床和病人绑定
    public synchronized static boolean removeBedPatientBound(String bedNo) {

        if (bedPatientMap.remove(bedNo) != null) {
            notifyRemove(bedNo);
        }
        return false;
    }

    // *移除病床上病人信息的显示，不删除绑定
    public static void removedBedpatientDisplay(String bedNum) {
        notifyRemove(bedNum);
    }

    // *通过病床号获取病人信息
    public static PatientInfo getPatientByBedNo(String bedNo) {
        return bedPatientMap.get(bedNo);
    }

    private static void notifyUpdate(String bedNo) {
        for (IBedPatientBound bound : bpListeners) {
            bound.update(bedNo);
        }
    }

    private static void notifyRemove(String bedNo) {
        for (IBedPatientBound bound : bpListeners) {
            bound.remove(bedNo);
        }
    }

    // *获取病床病人对应Map
    public static ArrayMap<String, PatientInfo> getBedPatientBound() {
        return bedPatientMap;
    }

    //value的记录
    public synchronized static boolean containValue(PatientInfo pi) {
        return bedPatientMap.containsValue(pi);
    }

    // 通过value删除记录
    public synchronized static boolean removepi(PatientInfo pi) {
        if (containValue(pi)) {
            bedPatientMap.removeValue(pi);
            return true;
        }
        return false;
    }

}
