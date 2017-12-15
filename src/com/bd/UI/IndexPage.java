package com.bd.UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.bd.Control.BedPatientBound;
import com.bd.Control.DeviceBedBound;
import com.bd.Control.PhoneBedBound;
import com.bd.Control.PhoneBound;
import com.bd.Control.SystemConfig;
import com.bd.Control.InterfaceAndEnum.IBedPatientBound;
import com.bd.Control.InterfaceAndEnum.IDeviceBedBoundListener;
import com.bd.Control.InterfaceAndEnum.IPhoneBedBoundListener;
import com.bd.Control.InterfaceAndEnum.IPhoneBound;
import com.bd.Control.Util.StringUtil;
import com.bd.SecondPage.VerifyExitPage;
import com.bd.UI.Util.CompositeUtil;
import com.bd.UI.Util.WindowSize;
import com.bd.server.nettyHandler;
import com.bd.server.nettyServer;

public class IndexPage {
	private static final int width;
	private static final int height;
	private static final int start;
	static {
		if (SystemConfig.SCREEN_SIZE_1920) {
			width = 153;
			height = 126;
			start = 0;
		} else if (SystemConfig.SCREEN_SIZE_1600) {
			width = 125;
			height = 100;
			start = 3;
		} else {
			width = 108;
			height = 85;
			start = 0;
		}
	}

	private Display display;
	protected static Shell IndexPageShell;

	private BedPanel[] allPanel;
	private ImgPhonePanel[] imgPhone;
	private ClockPanel cp;
	private nettyServer netty_Server;
	private SystemMenu sm;

	public IndexPage() throws Exception {
		display = Display.getDefault();

		// SystemConfig.phoneBedBoundDao.DropTable("phoneBedBoundTable");
		// SystemConfig.infusionEventDao.DropTable("InfusionEventTable");
		// SystemConfig.bedPatientBoundDao.DropTable("BedPatientBoundTable");
		// SystemConfig.patientDao.DropTable("PatientTable");

		// SystemConfig.phoneBedBoundDao.CreateTable(
		// "create table if not exists phoneBedBoundTable(phoneID varchar(10)
		// PRIMARY KEY,imei varchar(20),bedNo varchar(200),isOnline Boolean)");

		// for (int i = 1; i < 10; ++i)
		// SystemConfig.phoneBedBoundDao.addBedPatientBound("0" + i, "", "");
		// SystemConfig.phoneBedBoundDao.addBedPatientBound("10", "", "");

		// SystemConfig.infusionEventDao.CreateTable(
		// "create table if not exists InfusionEventTable(id INTEGER PRIMARY KEY
		// AUTOINCREMENT,patientID varchar(10),date varchar(20),time
		// varchar(20), event varchar(20))");
		// SystemConfig.bedPatientBoundDao.CreateTable(
		// "create table if not exists BedPatientBoundTable(patientID
		// varchar(10)PRIMARY KEY,BedNo varchar(10),isInHospital Boolean)");
		// SystemConfig.patientDao.CreateTable(
		// "create table if not exists PatientTable(patientID varchar(10)
		// PRIMARY KEY,patientName varchar(20),"
		// + "patientSex varchar(10),patientAge varchar(10),patientDisease
		// varchar(20))");
		// *开启服务器
		netty_Server = new nettyServer();

		netty_Server.start(new nettyHandler());

		IndexPageShell = new Shell();
		IndexPageShell.setImage(SWTResourceManager.getImage(IndexPage.class, "/resource/youdao.png"));
		PhoneBound.addPhoneBoundListener(new IPhoneBound() {
			@Override
			public void update(String phoneNo) {
				int ii = StringUtil.getNum(phoneNo);
				imgPhone[ii - 1].setPhoneVisable(true);
			}

			@Override
			public void remove(String phoneNo) {
				int ii = StringUtil.getNum(phoneNo);
				imgPhone[ii - 1].setPhoneVisable(false);
			}
		});

		BedPatientBound.addBedPatientBoundListener(new IBedPatientBound() {
			@Override
			public void update(String bedNo) {
				bedNo = bedNo.trim();
				if (bedNo != null && StringUtil.isBedID(bedNo)) {
					int bn = StringUtil.getNum(bedNo);
					allPanel[bn - 1].setBedPatientInfo(BedPatientBound.getPatientByBedNo(bedNo));
				}
			}

			@Override
			public void remove(String bedNo) {
				if (bedNo != null && StringUtil.isBedID(bedNo.trim())) {
					int bn = StringUtil.getNum(bedNo);
					allPanel[bn - 1].clearBedPatientInfo();
				}
			}
		});

		DeviceBedBound.addDeviceBedBoundListener(new IDeviceBedBoundListener() {
			@Override
			public void update(String bedNo) {
				String bedNum = bedNo.trim();
				if (bedNum != null && StringUtil.isBedID(bedNum)) {
					int bn = StringUtil.getNum(bedNum);
					if (DeviceBedBound.containsBed(bedNum)) {
						if (DeviceBedBound.getDeviceInfoByBedNo(bedNum) == null) {
							System.out.println();
						}
						allPanel[bn - 1].setBedDeviceInfo(DeviceBedBound.getDeviceInfoByBedNo(bedNum));
						allPanel[bn - 1].setBgColor(DeviceBedBound.getDeviceInfoByBedNo(bedNum));
					}
				}
			}

			@Override
			public void remove(String bedNo) {
				if (bedNo != null && StringUtil.isBedID(bedNo.trim())) {
					int bn = StringUtil.getNum(bedNo);
					allPanel[bn - 1].clearBedDeviceInfo();
				}
			}
		});
		PhoneBedBound.addPhoneBedBoundListener(new IPhoneBedBoundListener() {

			@Override
			public void update(String phoneNo) {
				String pn = phoneNo.trim();
				if (StringUtil.isPhoneNum(pn)) {
					int num = StringUtil.getNum(pn);
					if (PhoneBedBound.getPhoneRegi(pn))
						imgPhone[num - 1].setBgColorGreen();
					else
						imgPhone[num - 1].setBgColorGray();
				}
			}
		});

		IndexPageShell.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.keyCode) {
				// *D
				case 100:
					sm.setKeyDdown();
					break;
				// T
				case 116:
					sm.setKeyTdown();
					break;
				// B
				case 98:
					sm.setKeyBdown();
					break;
				// M
				case 109:
					sm.setKeyMdown();
					break;
				// R
				case 114:
					sm.setKeyRdown();
					break;
				// A
				case 97:
					sm.setKeyAdown();
				default:
					break;
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		IndexPageShell.setText("智能输液监控系统");
		IndexPageShell.setLocation(0, 0);
		IndexPageShell.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
		new WindowSize(display).setSize(IndexPageShell);
		// IndexPageShell.setSize(1366, 730);

		sm = new SystemMenu(IndexPageShell);

		BedPanel[] mp1 = new BedPanel[12];
		BedPanel[] mp2 = new BedPanel[12];
		BedPanel[] mp3 = new BedPanel[12];
		BedPanel[] mp4 = new BedPanel[12];
		BedPanel[] mp5 = new BedPanel[12];
		BedPanel[] mp6 = new BedPanel[12];

		allPanel = new BedPanel[72];
		if (SystemConfig.SCREEN_SIZE_1920) {
			CompositeUtil.createDivideLine(IndexPageShell, 5, 5, 1893, 25, "床 位 列 表");
			mp1 = CompositeUtil.createLineComposite(IndexPageShell, 1, start, 35, width, height);
			mp2 = CompositeUtil.createLineComposite(IndexPageShell, 13, start, 166, width, height);
			mp3 = CompositeUtil.createLineComposite(IndexPageShell, 25, start, 296, width, height);
			mp4 = CompositeUtil.createLineComposite(IndexPageShell, 37, start, 426, width, height);
			mp5 = CompositeUtil.createLineComposite(IndexPageShell, 49, start, 556, width, height);
			mp6 = CompositeUtil.createLineComposite(IndexPageShell, 61, start, 686, width, height);
			CompositeUtil.createDivideLine(IndexPageShell, 5, 820, 1893, 25, "护 士 报 警 终 端 列 表");
		} else if (SystemConfig.SCREEN_SIZE_1600) {
			CompositeUtil.createDivideLine(IndexPageShell, 8, 5, 1566, 21, "床 位 列 表");
			mp1 = CompositeUtil.createLineComposite(IndexPageShell, 1, start, 32, width, height);
			mp2 = CompositeUtil.createLineComposite(IndexPageShell, 13, start, 139, width, height);
			mp3 = CompositeUtil.createLineComposite(IndexPageShell, 25, start, 246, width, height);
			mp4 = CompositeUtil.createLineComposite(IndexPageShell, 37, start, 353, width, height);
			mp5 = CompositeUtil.createLineComposite(IndexPageShell, 49, start, 460, width, height);
			mp6 = CompositeUtil.createLineComposite(IndexPageShell, 61, start, 567, width, height);
			CompositeUtil.createDivideLine(IndexPageShell, 8, 674, 1566, 21, "护 士 报 警 终 端 列 表");
		} else {
			CompositeUtil.createDivideLine(IndexPageShell, 4, 5, 1341, 15, "床 位 列 表");
			mp1 = CompositeUtil.createLineComposite(IndexPageShell, 1, start, 25, width, height);
			mp2 = CompositeUtil.createLineComposite(IndexPageShell, 13, start, 115, width, height);
			mp3 = CompositeUtil.createLineComposite(IndexPageShell, 25, start, 205, width, height);
			mp4 = CompositeUtil.createLineComposite(IndexPageShell, 37, start, 295, width, height);
			mp5 = CompositeUtil.createLineComposite(IndexPageShell, 49, start, 385, width, height);
			mp6 = CompositeUtil.createLineComposite(IndexPageShell, 61, start, 475, width, height);
			CompositeUtil.createDivideLine(IndexPageShell, 5, 565, 1341, 15, "护 士 报 警 终 端 列 表");
		}
		allPanel = getAllPanel(mp1, mp2, mp3, mp4, mp5, mp6);

		imgPhone = new ImgPhonePanel[10];

		if (SystemConfig.SCREEN_SIZE_1920) {
			imgPhone = CompositeUtil.createLineImgPhone(IndexPageShell, 18, 865, 54, 97);
		} else if (SystemConfig.SCREEN_SIZE_1600) {
			imgPhone = CompositeUtil.createLineImgPhone(IndexPageShell, 35, 702, 52, 93);
		} else {
			imgPhone = CompositeUtil.createLineImgPhone(IndexPageShell, 35, 585, 50, 80);
		}

		cp = new ClockPanel(IndexPageShell, SWT.NONE);

		if (SystemConfig.SCREEN_SIZE_1920) {
			cp.setBounds(1680, 864, 300, 130);
		} else if (SystemConfig.SCREEN_SIZE_1600) {
			cp.setBounds(1370, 690, 160, 90);
		} else {
			cp.setBounds(1160, 570, 160, 90);
		}

		cp = new ClockPanel(IndexPageShell, SWT.NONE);
		getDBBedPatienttoDisplay(); // *显示病床病人绑定

		HashMap<String, String> phoneImeiMap = SystemConfig.phoneBedBoundDao.getPhoneImeiBoundMap();
		HashMap<String, ArrayList<String>> phoneBedMap = SystemConfig.phoneBedBoundDao.getPhoneBedBoundMap();

		// *添加到手机IMEI绑定表
		Iterator<String> iter = phoneImeiMap.keySet().iterator();
		while (iter.hasNext()) {
			String phoneId = iter.next();
			String imei = phoneImeiMap.get(phoneId);
			if (PhoneBound.notContains(phoneId, imei))
				PhoneBound.addPhoneBound(phoneId, imei);
		}
		// *添加到手机病床绑定表
		Iterator<String> itr = phoneBedMap.keySet().iterator();
		while (itr.hasNext()) {
			String phoneId = itr.next();
			ArrayList<String> bedArr = phoneBedMap.get(phoneId);
			if (PhoneBedBound.notContains(phoneId))
				PhoneBedBound.addPhoneBedBound(phoneId, bedArr);
		}

		IndexPageShell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				@SuppressWarnings("unused")
				VerifyExitPage ve = new VerifyExitPage(IndexPage.this, e);
			}
		});
	}

	public void open() {
		try {
			IndexPageShell.open();
			while (!IndexPageShell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// * 获得全部床位面板
	private BedPanel[] getAllPanel(BedPanel[] mp1, BedPanel[] mp2, BedPanel[] mp3, BedPanel[] mp4, BedPanel[] mp5,
			BedPanel[] mp6) {
		BedPanel[] all = new BedPanel[72];
		for (int i = 0; i < 12; i++) {
			all[i] = mp1[i];
		}
		for (int i = 12; i < 24; i++) {
			all[i] = mp2[i - 12];
		}
		for (int i = 24; i < 36; i++) {
			all[i] = mp3[i - 24];
		}
		for (int i = 36; i < 48; i++) {
			all[i] = mp4[i - 36];
		}
		for (int i = 48; i < 60; i++) {
			all[i] = mp5[i - 48];
		}
		for (int i = 60; i < 72; i++) {
			all[i] = mp6[i - 60];
		}
		return all;
	}

	public static void getDBBedPatienttoDisplay() {

		HashMap<String, String> hm = SystemConfig.bedPatientBoundDao.getBedPatientInfoMap();
		Iterator<String> it = hm.keySet().iterator();
		while (it.hasNext()) {
			String patientID = it.next();
			BedPatientBound.addBedPatientBound(hm.get(patientID), SystemConfig.patientDao.getPatientInfo(patientID));
		}
	}

	public nettyServer getnettyServer() {
		return netty_Server;
	}

}
