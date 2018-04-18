package com.bd.UI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import com.bd.Control.SystemConfig;
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
	private static final int PANEL_FONT;
	private static final int FONT_HIGHT;
	private static final int PANELID;
	private static String BG_Warming;
	private static String BG_Remove;
	private static final int WARM_X;
	private static final int WARM_Y;

	static {
		if (SystemConfig.SCREEN_SIZE_1920) {
			PANEL_FONT = 11;
			FONT_HIGHT = 20;
			PANELID = 18;
			BG_Warming = "/resource/warming.png";
			BG_Remove = "/resource/warmingRemove.png";
			WARM_X = 110;
			WARM_Y = 80;
		} else if (SystemConfig.SCREEN_SIZE_1600) {
			PANEL_FONT = 9;
			FONT_HIGHT = 17;
			PANELID = 16;
			BG_Warming = "/resource/warming1600.png";
			BG_Remove = "/resource/warmingRemove1600.png";
			WARM_X = 88;
			WARM_Y = 65;
		} else {
			PANEL_FONT = 8;
			FONT_HIGHT = 14;
			PANELID = 14;
			BG_Warming = "/resource/warming1366.png";
			BG_Remove = "/resource/warmingRemove1366.png";
			WARM_X = 80;
			WARM_Y = 60;
		}
	}

	public BedPanel(Composite parent, String i) {
		super(parent, SWT.BORDER);
		this.setBackgroundMode(SWT.INHERIT_DEFAULT);
		color = SWTResourceManager.getColor(SWT.COLOR_GRAY);
		if (SystemConfig.SCREEN_SIZE_1920) {
			setFont(SWTResourceManager.getFont("微软雅黑", PANEL_FONT, SWT.NORMAL));
		} else if (SystemConfig.SCREEN_SIZE_1600) {
			setFont(SWTResourceManager.getFont("微软雅黑", PANEL_FONT, SWT.NORMAL));
		} else {
			setFont(SWTResourceManager.getFont("微软雅黑", PANEL_FONT, SWT.NORMAL));
		}

		lblPanelID = new Label(this, SWT.NONE);
		lblPanelID.setFont(SWTResourceManager.getFont("微软雅黑", PANELID, SWT.NORMAL));
		lblPanelID.setBounds(0, 34, 31, 33);
		lblPanelID.setText(i);
		lblPanelID.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				bedInfo = new BedInfoPage(BedPanel.this);
			}
		});

		// 标记标签
		/**
		 * Author: zhangchao Date:2018年4月15日 TODO
		 */
		// Label lblNewLabel = new Label(this, SWT.NONE);
		// lblNewLabel.setFont(SWTResourceManager.getFont("微软雅黑", PANELID,
		// SWT.NORMAL));
		// lblNewLabel.setBounds(WARM_X, WARM_Y, 100, 80);
		// lblNewLabel.setText("");
		/**
		 * 2018.04.18 去除panel上报警图标
		 */
		// lblNewLabel.addMouseListener(new MouseAdapter() {
		// @Override
		// public void mouseDown(MouseEvent e) {
		// Image bg = getBackgroundImage();
		// Image img = SWTResourceManager.getImage(BedPanel.class, BG_Warming);
		// Image imgRemove = SWTResourceManager.getImage(BedPanel.class,
		// BG_Remove);
		// if (bg != null) {
		// if (bg.equals(imgRemove)) {
		// setBackgroundImage(img);
		// // BedWarmingMp3Manager.addWarming(lblPanelID.getText());
		// // BedWarmingMp3Manager.removeQuit(lblPanelID.getText());
		// } else {
		// setBackgroundImage(imgRemove);
		//
		// }
		// } else {
		// bedInfo = new BedInfoPage(BedPanel.this);
		// }
		// }
		// });

		lblPatientInfo = new Label(this, SWT.NONE);
		lblPatientInfo.setFont(SWTResourceManager.getFont("微软雅黑", PANEL_FONT, SWT.BOLD));
		lblPatientInfo.setBounds(31, 4, 98, FONT_HIGHT);
		lblPatientInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				bedInfo = new BedInfoPage(BedPanel.this);
			}
		});

		lblPatientID = new Label(this, SWT.NONE);
		lblPatientID.setFont(SWTResourceManager.getFont("微软雅黑", PANEL_FONT, SWT.NORMAL));
		lblPatientID.setBounds(31, 19, 98, FONT_HIGHT);
		lblPatientID.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				bedInfo = new BedInfoPage(BedPanel.this);
			}
		});

		lblDisease = new Label(this, SWT.NONE);
		lblDisease.setFont(SWTResourceManager.getFont("微软雅黑", PANEL_FONT, SWT.NORMAL));
		lblDisease.setBounds(31, 33, 98, FONT_HIGHT);
		lblDisease.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				bedInfo = new BedInfoPage(BedPanel.this);
			}
		});

		lblCurrentSpd = new Label(this, SWT.NONE);
		lblCurrentSpd.setFont(SWTResourceManager.getFont("微软雅黑", PANEL_FONT, SWT.NORMAL));
		lblCurrentSpd.setBounds(31, 49, 98, FONT_HIGHT);
		lblCurrentSpd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				bedInfo = new BedInfoPage(BedPanel.this);
			}
		});

		lblWorkStatus = new Label(this, SWT.NONE);
		lblWorkStatus.setFont(SWTResourceManager.getFont("微软雅黑", PANEL_FONT, SWT.NORMAL));
		lblWorkStatus.setBounds(31, 63, 98, FONT_HIGHT);

		// Composite composite = new WarmingLogo(this, SWT.NONE);
		// composite.setBounds(338, 143, 84, 88);
		// composite.setVisible(true);
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

		// Image img = SWTResourceManager.getImage(ImgPhonePanel.class,
		// BG_Blue);
		// setBackgroundImage(img);

		// InputStream inputStream = new
		// FileInputStream("/resource/WarmRemove.png");
		// Image image = new Image(Device.this, inputStream);
		// this.setBackgroundImage(image);
	}

	public String getCurrentSpd() {
		return lblCurrentSpd.getText().trim();
	}

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

	private void setLblPatientID(String patientID) {
		if (patientID != null)
			lblPatientID.setText(patientID);
	}

	private void setLblDisease(String disease) {
		if (disease != null)
			lblDisease.setText(disease);
	}

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

	private void setLblWorkStatus(String workStatus) {
		this.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (workStatus != null) {
					lblWorkStatus.setText(workStatus);
				}
			}
		});

	}

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
				// // TODO 演示版本 蓝色 变绿色
				color = new Color(Display.getDefault(), 0, 255, 0);
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
				// // TODO 演示版本 红色变黄色
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
				color = new Color(Display.getDefault(), 0, 255, 0);
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

	public void clearBedDeviceInfo() {
		this.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				setLblCurrentSpd("");
				setLblWorkStatus("");
			}
		});
	}

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
					setBackgroundImage(null);
					setMyPanelBgColortoYellow();
					break;
				case NOTWORK:
					setBackgroundImage(null);
					// TODO 修改护士以响应背景
					String status = device.getWorkStatus();
					if (status.equals("护士已响应") || status.equals("门打开")) {
						Image img = SWTResourceManager.getImage(BedPanel.class, BG_Warming);
						setBackgroundImage(img);
					} else {
						setMyPanelBgColortoBlue();
					}
					break;
				case WAIT:
					setBackgroundImage(null);
					setMyPanelBgColortoGray();
					break;
				case WARNING:
					setBackgroundImage(null);
					setMyPanelBgColortoRed();
					break;
				case WORK:
					setBackgroundImage(null);
					setMyPanelBgColortoGreen();
					break;
				default:
					setBackgroundImage(null);
					setMyPanelBgColortoGray();
					break;
				}
			}
		});
	}
}