package com.bd.SecondPage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class AboutPage {

	protected Shell shell;

	public AboutPage() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected void createContents() {
		shell = new Shell(SWT.CLOSE | SWT.APPLICATION_MODAL);
		shell.setImage(SWTResourceManager.getImage(AboutPage.class, "/resource/youdao.png"));
		shell.setSize(396, 347);
		shell.setText("关于");
		shell.setLocation(Display.getCurrent().getClientArea().width / 2 - shell.getShell().getSize().x / 2,
				Display.getCurrent().getClientArea().height / 2 - shell.getSize().y / 2);

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		composite.setBounds(0, 0, 390, 319);

		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel.setBounds(10, 31, 234, 21);
		lblNewLabel.setText("输液自动监控系统2.1.0311");

		Label lblNewLabel_1 = new Label(composite, SWT.NONE);
		lblNewLabel_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_1.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel_1.setBounds(10, 83, 234, 21);
		lblNewLabel_1.setText("版权所有(C)  2015");

		Label lblNewLabel_2 = new Label(composite, SWT.NONE);
		lblNewLabel_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_2.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel_2.setBounds(10, 140, 234, 21);
		lblNewLabel_2.setText("张家港保税区优道贸易有限公司");

		Label lblNewLabel_3 = new Label(composite, SWT.NONE);
		lblNewLabel_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_3.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel_3.setBounds(10, 193, 260, 21);
		lblNewLabel_3.setText("网址：www.youdaohealth.com");

		Label lblNewLabel_4 = new Label(composite, SWT.NONE);
		lblNewLabel_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_4.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel_4.setBounds(10, 220, 319, 21);
		lblNewLabel_4.setText("技术支持：深圳市艾宝科技有限公司");

		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		btnNewButton.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		btnNewButton.setBounds(134, 282, 94, 27);
		btnNewButton.setText("确定");

		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setBounds(249, 31, 121, 112);
		composite_1.setBackgroundImage(SWTResourceManager.getImage(AboutPage.class, "/resource/youdao_m.png"));

		Label label = new Label(composite, SWT.NONE);
		label.setText("深圳大学计算机与软件学院");
		label.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label.setBounds(88, 247, 198, 21);
	}
}
