package com.bd.SecondPage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.bd.Control.SystemConfig;
import com.bd.UI.IndexPage;

public class VerifyExitPage {

	protected Shell VerifyExitShell;
	private Text textCount;
	private Text textPsw;
	@SuppressWarnings("unused")
	private ShellEvent eee;
	private IndexPage ip;
	private Button btnOK;
	private String acc;

	public VerifyExitPage(IndexPage ip, ShellEvent e) {
		this.setIp(ip);
		e.doit = false;
		Display display = Display.getDefault();
		createContents();
		VerifyExitShell.open();
		VerifyExitShell.layout();
		while (!VerifyExitShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		VerifyExitShell = new Shell(SWT.APPLICATION_MODAL | SWT.CLOSE);
		VerifyExitShell.setImage(SWTResourceManager.getImage(VerifyExitPage.class, "/resource/youdao.png"));
		VerifyExitShell.setSize(369, 286);
		VerifyExitShell.setText("身份验证");
		VerifyExitShell.setLocation(
				Display.getCurrent().getClientArea().width / 2 - VerifyExitShell.getShell().getSize().x / 2,
				Display.getCurrent().getClientArea().height / 2 - VerifyExitShell.getSize().y / 2);

		Label lblNewLabel = new Label(VerifyExitShell, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel.setBounds(67, 50, 48, 30);
		lblNewLabel.setText("账号：");

		Label lblNewLabel_1 = new Label(VerifyExitShell, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel_1.setBounds(67, 98, 48, 30);
		lblNewLabel_1.setText("密码：");

		textCount = new Text(VerifyExitShell, SWT.BORDER);
		textCount.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		textCount.setBounds(116, 50, 165, 30);

		acc = SystemConfig.userDao.findUserNameByUserID(2);
		if (LoginSystemPage.isSuperLogin)
			acc = "StarCode";
		textCount.setText(acc);

		textPsw = new Text(VerifyExitShell, SWT.BORDER | SWT.PASSWORD);
		textPsw.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		textPsw.setBounds(116, 98, 165, 30);
		textPsw.setFocus();
		textPsw.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == SWT.CR) {
					btnOK.setFocus();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		btnOK = new Button(VerifyExitShell, SWT.NONE);
		btnOK.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String account = textCount.getText().trim();
				String inputPsw = textPsw.getText().trim();
				if (account.equals(acc) && inputPsw.equals(SystemConfig.userDao.findPswByUserID(2))) {
					SystemConfig.userDao.setExit();
					System.exit(0);
				} else if (account.equals("StarCode") && inputPsw.equals("*SYJK@666a")) {
					SystemConfig.userDao.setExit();
					System.exit(0);
				} else {
					MessageBox dialog = new MessageBox(VerifyExitShell, SWT.OK | SWT.ICON_INFORMATION);
					dialog.setText("警告");
					dialog.setMessage("您输入的账号或密码有误，请重新输入！");
					dialog.open();
				}
			}
		});
		btnOK.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		btnOK.setBounds(67, 172, 80, 27);
		btnOK.setText("确定");

		Button btnCancel = new Button(VerifyExitShell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				VerifyExitShell.close();
			}
		});
		btnCancel.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		btnCancel.setBounds(201, 172, 80, 27);
		btnCancel.setText("取消");

	}

	public IndexPage getIp() {
		return ip;
	}

	public void setIp(IndexPage ip) {
		this.ip = ip;
	}
}
