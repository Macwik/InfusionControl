package com.bd.UI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import com.bd.SecondPage.BedSelectPage;

public class ImgPhonePanel extends Composite {
	private static String GRAY = "/resource/01.png";
	private static String GREEN = "/resource/06.png";
	@SuppressWarnings("unused")
	private BedSelectPage bedSelect;
	private Label phoneNum;

	public ImgPhonePanel(Composite parent, int start) {
		super(parent, SWT.BORDER);

		this.setBounds(37, 63, 76, 135);
		this.setBackgroundMode(SWT.INHERIT_DEFAULT);
		phoneNum = new Label(this, SWT.NONE);
		phoneNum.setFont(SWTResourceManager.getFont("微软雅黑", 15, SWT.NORMAL));
		phoneNum.setBounds(14, 30, 24, 24);
		phoneNum.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				bedSelect = new BedSelectPage(ImgPhonePanel.this);
			}
		});
		if (start < 10)
			phoneNum.setText("0" + start);
		else
			phoneNum.setText("10");
		setBgColorGray();
		this.setVisible(false);
		this.setVisible(false);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				bedSelect = new BedSelectPage(ImgPhonePanel.this);
			}
		});
	}

	// *设置手机图标 变灰
	public void setBgColorGray() {

		this.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				Image img = SWTResourceManager.getImage(ImgPhonePanel.class, GRAY);
				setBackgroundImage(img);
			}
		});

	}

	// *设置使得手机图标颜色 变绿
	public void setBgColorGreen() {
		this.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				Image img = SWTResourceManager.getImage(ImgPhonePanel.class, GREEN);
				setBackgroundImage(img);
			}
		});

	}

	// *获取手机ID号
	public String getPhoneNum() {
		return phoneNum.getText();
	}

	// *设置 使得手机图标能够显示
	public void setPhoneVisable(boolean isVisible) {
		this.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				ImgPhonePanel.this.setVisible(isVisible);
			}
		});

	}

}
