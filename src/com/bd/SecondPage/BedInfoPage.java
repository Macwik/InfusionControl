package com.bd.SecondPage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.bd.Control.BedPatientBound;
import com.bd.Control.DeviceBedBound;
import com.bd.Control.PhoneBedBound;
import com.bd.Control.SystemConfig;
import com.bd.Control.InterfaceAndEnum.EDeviceStatusColorEnum;
import com.bd.Control.Util.StringUtil;
import com.bd.UI.BedPanel;
import com.bd.server.VolStatus;
import com.bd.server.nettyHandler;

public class BedInfoPage {
	protected Shell BedInfoShell;
	private Button btnSetBaseSpeed;
	private Button btnPatientDischarge;// 病人出院
	private Label lblCurrentSpeed;
	private Label lblCurrentStandard;
	private Label lblPatientInfo;
	private Label lblBedNum;
	private Label lblDutyNurse;
	private Text textSetBase;
	private BedPanel bedPanel;
	private Label lblConfirm;
	private static final int PANEL_FONT_NUM;
	static {
		if (SystemConfig.SCREEN_SIZE_1920) {
			PANEL_FONT_NUM = 15;
		} else if (SystemConfig.SCREEN_SIZE_1600) {
			PANEL_FONT_NUM = 13;
		} else {
			PANEL_FONT_NUM = 12;
		}
	}

	public BedInfoPage(BedPanel bedPanel) {
		Display display = Display.getDefault();
		this.bedPanel = bedPanel;
		createContents();
		String bedNo = this.bedPanel.getLblPanelID();

		lblBedNum.setText(bedNo);

		setPatientInfo(bedPanel.getPatientInfo());

		if (DeviceBedBound.containsBed(bedNo))
			setCurrentSpeed(DeviceBedBound.getDeviceInfoByBedNo(bedNo).getCurrentSpd());
		else
			setCurrentSpeed("0");

		if (DeviceBedBound.containsBed(bedNo))
			setCurrentStandard(DeviceBedBound.getDeviceInfoByBedNo(bedNo).getBaseSpd());
		else
			setCurrentStandard("0");

		setLblDutyNurse(PhoneBedBound.getBoundPhoneString(bedNo));

		String phoneNO = PhoneBedBound.getConfirmPhone(bedNo);
		if (phoneNO != null)
			lblConfirm.setText(phoneNO);

		String text = bedPanel.getPatientInfo().trim();
		if (text.equals("") || text == null || !(DeviceBedBound.containsBed(bedNo))) {
			if (!BedPatientBound.contain(bedNo))
				btnPatientDischarge.setEnabled(false);
			btnSetBaseSpeed.setEnabled(false);
		} else {
			if (DeviceBedBound.getDeviceInfoByBedNo(bedNo).getStatusColor() != EDeviceStatusColorEnum.WAIT)
				btnPatientDischarge.setEnabled(false);

		}
		if (DeviceBedBound.containsBed(bedNo)) {
			String deviceID = DeviceBedBound.getDeviceInfoByBedNo(bedNo).getDeviceID();
			if (StringUtil.isDeviceID(deviceID)) {
				if (nettyHandler.vol_status.containsKey(deviceID))
					if (nettyHandler.vol_status.get(deviceID).equals(VolStatus.HandOpera))
						btnSetBaseSpeed.setEnabled(false);
			}
		}

		BedInfoShell.open();
		BedInfoShell.layout();
		while (!BedInfoShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		BedInfoShell = new Shell(SWT.CLOSE);
		BedInfoShell.setImage(SWTResourceManager.getImage(BedInfoPage.class, "/resource/youdao.png"));
		BedInfoShell.setSize(539, 395);
		BedInfoShell.setText("床位信息");
		BedInfoShell.setLocation(
				Display.getCurrent().getClientArea().width / 2 - BedInfoShell.getShell().getSize().x / 2,
				Display.getCurrent().getClientArea().height / 2 - BedInfoShell.getSize().y / 2);

		Label lblBed = new Label(BedInfoShell, SWT.NONE);
		lblBed.setFont(SWTResourceManager.getFont("微软雅黑", 25, SWT.BOLD));
		lblBed.setBounds(57, 26, 125, 46);
		lblBed.setText("床位号：");

		Label lblCurrent = new Label(BedInfoShell, SWT.NONE);
		lblCurrent.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblCurrent.setBounds(57, 102, 75, 21);
		lblCurrent.setText("当前基准：");

		lblCurrentStandard = new Label(BedInfoShell, SWT.NONE);
		lblCurrentStandard.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblCurrentStandard.setBounds(150, 102, 127, 21);

		Label label = new Label(BedInfoShell, SWT.NONE);
		label.setText("当前滴速：");
		label.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		label.setBounds(283, 102, 75, 21);

		lblCurrentSpeed = new Label(BedInfoShell, SWT.NONE);
		lblCurrentSpeed.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblCurrentSpeed.setBounds(364, 102, 144, 21);

		Label lblNewLabel_2 = new Label(BedInfoShell, SWT.NONE);
		lblNewLabel_2.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel_2.setBounds(57, 136, 112, 21);
		lblNewLabel_2.setText("新设基准滴速：");

		textSetBase = new Text(BedInfoShell, SWT.BORDER);
		textSetBase.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		textSetBase.setBounds(170, 134, 50, 23);
		// TODO 演示 不可见
		// textSetBase.setVisible(false);
		textSetBase.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == SWT.CR) {
					btnSetBaseSpeed.setFocus();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		Label lblNewLabel_3 = new Label(BedInfoShell, SWT.NONE);
		lblNewLabel_3.setFont(SWTResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		lblNewLabel_3.setBounds(231, 136, 61, 21);
		lblNewLabel_3.setText("滴/分钟");

		btnSetBaseSpeed = new Button(BedInfoShell, SWT.NONE);
		btnSetBaseSpeed.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Display.getCurrent().asyncExec(new Runnable() {

					@Override
					public void run() {
						String spd = textSetBase.getText().trim();
						if (StringUtil.isSpeed(spd)) {
							String bedNo = bedPanel.getLblPanelID();
							if (DeviceBedBound.containsBed(bedNo)) {
								String deviceID = DeviceBedBound.getDeviceInfoByBedNo(bedNo).getDeviceID();
								if (StringUtil.isDeviceID(deviceID)) {
									textSetBase.setText("");
									MessageBox dialog = new MessageBox(BedInfoShell, SWT.OK | SWT.ICON_INFORMATION);
									dialog.setText("基准更新");
									if (com.bd.server.nettyHandler.SetSpd(deviceID, spd)) {
										lblCurrentStandard.setText(spd + "滴/分钟");
										dialog.setMessage("基准滴速已经更新！");
										dialog.open();
									} else {
										dialog.setMessage("网络繁忙，基准滴速更新失败，请重试");
										dialog.open();
									}
								}
							}

						} else {
							MessageBox dialog = new MessageBox(BedInfoShell, SWT.OK | SWT.ICON_INFORMATION);
							dialog.setText("错误");
							dialog.setMessage("请正确输入要输入的基准滴速！");
							dialog.open();
						}
					}
				});

			}
		});
		btnSetBaseSpeed.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		btnSetBaseSpeed.setBounds(355, 129, 153, 27);
		btnSetBaseSpeed.setText("基准滴速设置");
		// TODO 演示
		// btnSetBaseSpeed.setVisible(false);

		Label lblNewLabel_4 = new Label(BedInfoShell, SWT.NONE);
		lblNewLabel_4.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel_4.setBounds(57, 178, 75, 21);
		lblNewLabel_4.setText("责任护士：");

		lblDutyNurse = new Label(BedInfoShell, SWT.NONE);
		lblDutyNurse.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblDutyNurse.setBounds(150, 178, 358, 21);

		Label lblNewLabel = new Label(BedInfoShell, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel.setBounds(57, 205, 75, 23);
		lblNewLabel.setText("响应护士：");

		lblConfirm = new Label(BedInfoShell, SWT.NONE);
		lblConfirm.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblConfirm.setBounds(138, 205, 185, 23);

		btnPatientDischarge = new Button(BedInfoShell, SWT.NONE);
		// 病人出院
		btnPatientDischarge.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox messageBox = new MessageBox(BedInfoShell, SWT.OK | SWT.CANCEL);
				messageBox.setText("确认");
				messageBox.setMessage("    该操作将同时删除病人信息,确定这样做吗？");
				if (messageBox.open() == SWT.OK) {
					String bedNum = bedPanel.getLblPanelID();
					if (BedPatientBound.contain(bedNum)) {
						SystemConfig.bedPatientBoundDao
								.setPatientLeaveHospital(BedPatientBound.getPatientByBedNo(bedNum).getPatientId());
						BedPatientBound.removeBedPatientBound(bedNum);
						BedInfoShell.close();
					}
				}
			}
		});
		btnPatientDischarge.setFont(SWTResourceManager.getFont("微软雅黑", 20, SWT.BOLD));
		btnPatientDischarge.setBounds(57, 246, 140, 111);
		btnPatientDischarge.setText("病人出院");

		Button btnClose = new Button(BedInfoShell, SWT.NONE);
		btnClose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				BedInfoShell.close();
			}
		});
		btnClose.setFont(SWTResourceManager.getFont("微软雅黑", 16, SWT.NORMAL));
		btnClose.setBounds(355, 322, 153, 35);
		btnClose.setText("关闭");

		lblBedNum = new Label(BedInfoShell, SWT.NONE);
		lblBedNum.setFont(SWTResourceManager.getFont("微软雅黑", 25, SWT.BOLD));
		lblBedNum.setBounds(185, 26, 40, 46);

		lblPatientInfo = new Label(BedInfoShell, SWT.NONE);
		lblPatientInfo.setFont(SWTResourceManager.getFont("微软雅黑", 25, SWT.BOLD));
		lblPatientInfo.setBounds(226, 26, 282, 46);
	}

	// *设置当前滴速
	public void setCurrentSpeed(String currentSpeed) {
		if (currentSpeed != null && StringUtil.isSpeed(currentSpeed)) {
			lblCurrentSpeed.setText(StringUtil.getNum(currentSpeed.trim()) + " 滴/分钟");
		} else
			lblCurrentSpeed.setText("未知");

	}

	// *设置当前基准滴速
	public void setCurrentStandard(String currentStandard) {
		if (currentStandard != null && StringUtil.isSpeed(currentStandard)) {
			lblCurrentStandard.setText(StringUtil.getNum(currentStandard.trim()) + " 滴/分钟");
		} else
			lblCurrentStandard.setText("未知");

	}

	// *设置病人信息
	public void setPatientInfo(String patientInfo) {
		lblPatientInfo.setText(patientInfo);
	}

	// // *设置床位信息
	// public void setBedNum(String bedNum) {
	// lblBedNum.setText(bedNum);
	// }

	// *设置责任护士
	public void setLblDutyNurse(String lblDutyNurse) {
		this.lblDutyNurse.setText(lblDutyNurse);
	}

	// // *当有终端信息时修改病床显示
	// public void setDeviceStatus(DeviceInfo deviceInfo) {
	// this.setCurrentStandard(deviceInfo.getBaseSpd());
	// this.setCurrentSpeed(deviceInfo.getCurrentSpd());
	// }

	// *当只有病人信息时修改病床显示
	// public void setBedInfo(PatientInfo patientInfo) {
	// this.setPatientInfo("(" + patientInfo.getPatientName() + " " +
	// patientInfo.getPatientGender() + " "
	// + patientInfo.getPatientAge() + ")");
	// }

	// *获取设置的基准滴速值
	public String getTextBase() {
		String textBase = textSetBase.getText().trim().replaceAll("^(0+)", "");
		if (textBase != null && StringUtil.isSpeed(textBase))
			return StringUtil.getNum(textBase) + "";
		return "未知";
	}
}
