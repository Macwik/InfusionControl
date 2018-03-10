package com.bd.UI.Util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.bd.Control.SystemConfig;
import com.bd.UI.BedPanel;
import com.bd.UI.ImgPhonePanel;

public class CompositeUtil {
	/**
	 * 创建一行MyPanel对象，12个
	 * 
	 * @param shell
	 *            父窗口
	 * @param start
	 *            开始编号
	 * @param x
	 *            起始位置横坐标
	 * @param y
	 *            起始位置纵坐标
	 * @param width
	 *            控件的宽度
	 * @param height
	 *            控件的高度
	 */
	public static BedPanel[] createLineComposite(Shell shell, int start, int x, int y, int width, int height) {
		BedPanel[] mp = new BedPanel[12];
		for (int i = 0; i < 12; i++) {
			if (start == 1 && i < 9) {
				mp[i] = new BedPanel(shell, "0" + (i + 1));
			} else {
				mp[i] = new BedPanel(shell, start + i + "");
			}
			// TODO
			mp[i].setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
			// mp[i].setBackground(SWTResourceManager.getColor(SWT.COLOR_YELLOW));
			// *1920*1080
			// mp[i].setBounds(x + 159 * i + 6, y, width, height);
			// *1920*1080

			// *1366*768
			if (SystemConfig.SCREEN_SIZE_1920) {
				mp[i].setBounds(x + 158 * i + 6, y, width, height);
			} else if (SystemConfig.SCREEN_SIZE_1600) {
				mp[i].setBounds(x + 131 * i + 5, y, width, height);
			} else {
				mp[i].setBounds(x + 112 * i + 5, y, width, height);
			}
			// *1366*768
		}
		return mp;
	}

	/**
	 * 创建手机和电脑的分隔线
	 * 
	 * @param shell
	 *            父窗口
	 * @param x
	 *            分隔线的起始坐标
	 * @param y
	 *            分隔线的起始坐标
	 * @param width
	 *            分割线的宽和高
	 * @param height
	 */
	public static void createDivideLine(Shell shell, int x, int y, int width, int height, String str) {

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		composite.setBounds(x, y, width, height);

		Label label = new Label(composite, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("黑体", 10, SWT.BOLD));
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		label.setBounds(width / 2 - 50, height / 2 - 10 + 5, 200, 15);
		label.setText(str);
	}

	public static ImgPhonePanel[] createLineImgPhone(Shell shell, int x, int y, int width, int height) {
		ImgPhonePanel[] imgPhone = new ImgPhonePanel[10];
		for (int i = 0; i < 10; i++) {
			imgPhone[i] = new ImgPhonePanel(shell, i + 1);
			imgPhone[i].setBounds(x + 100 * i + 3, y, width, height);
		}
		return imgPhone;
	}

	public static Button[] createCheckButton(Composite composite) {
		Button[] checkButton = new Button[72];
		int j = 1;
		for (int i = 1; i <= 72; i++) {
			checkButton[i - 1] = new Button(composite, SWT.CHECK);
			if (i < 10)
				checkButton[i - 1].setText("0" + i);
			else
				checkButton[i - 1].setText("" + i);

			checkButton[i - 1].setFont(SWTResourceManager.getFont("微软雅黑", 15, SWT.NORMAL));
			checkButton[i - 1].setBounds(10 + 53 * (j - 1), 30 * ((i - 1) / 9), 47, 28);
			j++;
			if (j == 10)
				j = 1;
		}
		return checkButton;
	}

}