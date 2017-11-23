package com.bd.UI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import com.bd.Control.InterfaceAndEnum.EDeviceStatusColorEnum;
import com.bd.Control.Util.StringUtil;
import com.bd.SecondPage.BedInfoPage;
import com.bd.objects.DeviceInfo;
import com.bd.objects.PatientInfo;

public class BedPanel extends Composite {

	private Label lblPanelID;
	private Label lblPatientInfo;
	private Label lblPatientID;
	private Label lblDisease;
	private Label lblCurrentSpd;
	private Label lblWorkStatus;
	private Color color;
	@SuppressWarnings("unused")
	private BedInfoPage bedInfo;

	// **创建面板
	public BedPanel(Composite parent, String i) {
		super(parent, SWT.BORDER);
		this.setBackgroundMode(SWT.INHERIT_DEFAULT);
		color = SWTResourceManager.getColor(SWT.COLOR_GRAY);
		setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.NORMAL));

		lblPanelID = new Label(this, SWT.NONE);
		lblPanelID.setFont(SWTResourceManager.getFont("微软雅黑", 13, SWT.NORMAL));
		lblPanelID.setBounds(0, 34, 31, 27);
		lblPanelID.setText(i);
		lblPanelID.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				bedInfo = new BedInfoPage(BedPanel.this);
			}
		});

		lblPatientInfo = new Label(this, SWT.NONE);
		lblPatientInfo.setFont(SWTResourceManager.getFont("微软雅黑", 8, SWT.BOLD));
		lblPatientInfo.setBounds(31, 4, 98, 19);
		lblPatientInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				bedInfo = new BedInfoPage(BedPanel.this);
			}
		});

		lblPatientID = new Label(this, SWT.NONE);
		lblPatientID.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.NORMAL));
		lblPatientID.setBounds(31, 19, 98, 16);
		lblPatientID.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				bedInfo = new BedInfoPage(BedPanel.this);
			}
		});

		lblDisease = new Label(this, SWT.NONE);
		lblDisease.setFont(SWTResourceManager.getFont("微软雅黑", 8, SWT.NORMAL));
		lblDisease.setBounds(31, 33, 98, 16);
		lblDisease.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				bedInfo = new BedInfoPage(BedPanel.this);
			}
		});

		lblCurrentSpd = new Label(this, SWT.NONE);
		lblCurrentSpd.setFont(SWTResourceManager.getFont("微软雅黑", 8, SWT.NORMAL));
		lblCurrentSpd.setBounds(31, 49, 98, 16);
		lblCurrentSpd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				bedInfo = new BedInfoPage(BedPanel.this);
			}
		});

		lblWorkStatus = new Label(this, SWT.NONE);
		lblWorkStatus.setFont(SWTResourceManager.getFont("微软雅黑", 8, SWT.NORMAL));
		lblWorkStatus.setBounds(31, 63, 98, 16);
		lblWorkStatus.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				bedInfo = new BedInfoPage(BedPanel.this);
			}
		});
		setMyPanelBgColortoGray();
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				bedInfo = new BedInfoPage(BedPanel.this);
			}
		});
	}

	// * 获取当前滴速
	public String getCurrentSpd() {
		return lblCurrentSpd.getText().trim();
	}

	// *获取病人用户信息
	public String getPatientInfo() {
		if (lblPatientInfo.getText().trim().equals(""))
			return "";
		else
			return "（" + lblPatientInfo.getText().trim() + "）";
	}

	// * 设置病人信息 病人个人信息，包括姓名、性别、年龄

	private void setLblPatientInfo(String patientName, String patientSex, String patientAge) {
		lblPatientInfo.setText(patientName + " " + patientSex + " " + patientAge);
	}

	// * 设置病人住院号
	private void setLblPatientID(String patientID) {
		if (patientID != null)
			lblPatientID.setText(patientID);
	}

	// * 设置病人疾病名称
	private void setLblDisease(String disease) {
		if (disease != null)
			lblDisease.setText(disease);
	}

	// * 设置当前滴速
	private void setLblCurrentSpd(String currentSpd) {
		this.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (currentSpd != null) {
					String cs = currentSpd.trim();
					if (StringUtil.isSpeed(cs)) {
						lblCurrentSpd.setText(StringUtil.getNum(cs) + " 滴/分钟");
					} else
						lblCurrentSpd.setText("");
				} else
					lblCurrentSpd.setText("");
			}
		});

	}

	// * 设置当前工作状态
	private void setLblWorkStatus(String workStatus) {
		this.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (workStatus != null)
					lblWorkStatus.setText(workStatus);
			}
		});

	}

	// * 设置背景色为灰色
	private void setMyPanelBgColortoGray() {
		this.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				color = SWTResourceManager.getColor(SWT.COLOR_GRAY);
				BedPanel.this.setBackground(color);
			}
		});
	}

	// * 设置背景色为蓝色
	// TODO演示版本
	private void setMyPanelBgColortoBlue() {
		this.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				// color = new Color(Display.getDefault(), 128, 255, 255);
				// TODO 演示版本 蓝色 变绿色
				color = new Color(Display.getDefault(), 128, 255, 128);
				BedPanel.this.setBackground(color);
			}
		});
	}

	// * 设置背景色为红色
	// TODO 演示版本 红色变黄色
	private void setMyPanelBgColortoRed() {
		this.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				// color = new Color(Display.getDefault(), 255, 0, 0);
				// TODO 演示版本 红色变黄色
				color = new Color(Display.getDefault(), 255, 255, 128);
				BedPanel.this.setBackground(color);
			}
		});
	}

	// * 设置背景色为绿色
	private void setMyPanelBgColortoGreen() {
		this.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				color = new Color(Display.getDefault(), 128, 255, 128);
				BedPanel.this.setBackground(color);
			}
		});
	}

	// * 设置背景色为黄色
	private void setMyPanelBgColortoYellow() {
		this.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				color = new Color(Display.getDefault(), 255, 255, 128);
				BedPanel.this.setBackground(color);
			}
		});

	}

	public String getLblPanelID() {
		return lblPanelID.getText().trim();
	}

	// *设置床位显示病人信息
	public void setBedPatientInfo(PatientInfo patientInfo) {
		this.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				setLblPatientInfo(patientInfo.getPatientName(), patientInfo.getPatientGender(),
						patientInfo.getPatientAge());
				setLblPatientID(patientInfo.getPatientId());
				setLblDisease(patientInfo.getPatientSymptom());
			}
		});

	}

	// *清除病床上显示的病人信息
	public void clearBedPatientInfo() {
		this.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				setLblPatientInfo("", "", "");
				setLblPatientID("");
				setLblDisease("");
			}
		});

	}

	// *设置床位显示终端信息，包括当前滴速和当前工作状态
	public void setBedDeviceInfo(DeviceInfo deviceInfo) {
		this.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (deviceInfo.getCurrentSpd() != null)
					setLblCurrentSpd(deviceInfo.getCurrentSpd());
				if (deviceInfo.getWorkStatus() != null)
					setLblWorkStatus(deviceInfo.getWorkStatus());
			}
		});
	}

	// *清除病床终端信息
	public void clearBedDeviceInfo() {
		this.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				setLblCurrentSpd("");
				setLblWorkStatus("");
			}
		});
	}

	// *改变病床颜色显示
	public void setBgColor(DeviceInfo device) {
		if (device == null) {
			return;
		}
		this.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				EDeviceStatusColorEnum color = device.getStatusColor();
				switch (color) {
				case EARLYWARNING:
					setMyPanelBgColortoYellow();
					break;
				case NOTWORK:
					setMyPanelBgColortoBlue();
					break;
				case WAIT:
					setMyPanelBgColortoGray();
					break;
				case WARNING:
					setMyPanelBgColortoRed();
					break;
				case WORK:
					setMyPanelBgColortoGreen();
					break;
				default:
					setMyPanelBgColortoGray();
					break;
				}
			}
		});
	}
}
