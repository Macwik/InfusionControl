package com.bd.UI;

import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class ClockPanel extends Composite implements Runnable {
	private Label lblDate;
	private Label lblTime;
	public Thread th;

	public ClockPanel(Shell parent, int style) {
		super(parent, style);
		this.setBackgroundMode(SWT.INHERIT_DEFAULT);
		this.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));

		lblDate = new Label(this, SWT.NONE);
		lblDate.setFont(SWTResourceManager.getFont("微软雅黑", 16, SWT.BOLD));
		lblDate.setBounds(47, 22, 132, 37);

		lblTime = new Label(this, SWT.NONE);
		lblTime.setFont(SWTResourceManager.getFont("微软雅黑", 16, SWT.BOLD));
		lblTime.setBounds(39, 65, 140, 37);
		th = new Thread(this);
		th.setDaemon(true);
		th.start();

	}

	@Override
	public void run() {
		while (true) {
			try {

				lblDate.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						Date date = new Date();
						@SuppressWarnings("deprecation")
						String[] dat = date.toLocaleString().split(" ");
						// 设置 时间
						lblDate.setText(dat[1]);
						lblTime.setText(dat[0]);
					}
				});
				Thread.sleep(1000);
			} catch (Exception e) {
			}

		}
	}

}
