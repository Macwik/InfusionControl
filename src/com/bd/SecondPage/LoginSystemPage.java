package com.bd.SecondPage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.bd.Control.SystemConfig;
import com.bd.Control.Util.SocketUtils;
import com.bd.UI.IndexPage;

import logfile.log;

public class LoginSystemPage {

	private static Shell shell = new Shell(SWT.CLOSE);
	protected Shell LoginSystemShell;
	private Text textCount;
	private Text textPsw;
	public static boolean isSuperLogin = false;

	public static void main(String[] args) {

		if (!SocketUtils.isrunning()) {
			new LoginSystemPage();
		} else {
			MessageBox dialog = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
			dialog.setText("警告");
			dialog.setMessage("已经打开了一个程序窗口，不要多次打开");
			int OK = dialog.open();
			if (OK == 32)
				System.exit(0);
		}

	}

	public LoginSystemPage() {

		Display display = Display.getDefault();
		createContents();
		LoginSystemShell.open();
		LoginSystemShell.layout();
		while (!LoginSystemShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		LoginSystemShell = new Shell(SWT.CLOSE | SWT.APPLICATION_MODAL | SWT.MAX);
		LoginSystemShell.setImage(SWTResourceManager.getImage(LoginSystemPage.class, "/resource/youdao.png"));
		LoginSystemShell.setBackground(SWTResourceManager.getColor(255, 250, 250));
		LoginSystemShell.setSize(344, 300);
		LoginSystemShell.setText("身份验证");
		LoginSystemShell.setLocation(
				Display.getCurrent().getClientArea().width / 2 - LoginSystemShell.getShell().getSize().x / 2,
				Display.getCurrent().getClientArea().height / 2 - LoginSystemShell.getSize().y / 2);

		Label lblCount = new Label(LoginSystemShell, SWT.NONE);
		lblCount.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblCount.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblCount.setBounds(32, 72, 48, 29);
		lblCount.setText("账号：");

		textCount = new Text(LoginSystemShell, SWT.BORDER);
		textCount.setBounds(88, 72, 204, 29);

		Label lblPsw = new Label(LoginSystemShell, SWT.NONE);
		lblPsw.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblPsw.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblPsw.setBounds(34, 128, 48, 29);
		lblPsw.setText("密码：");

		textPsw = new Text(LoginSystemShell, SWT.BORDER | SWT.PASSWORD);
		textPsw.setBounds(88, 129, 204, 29);

		Button btnOk = new Button(LoginSystemShell, SWT.NONE);
		btnOk.setBounds(34, 195, 80, 27);
		btnOk.setText("登录");
		btnOk.setFocus();

		Button btnCancel = new Button(LoginSystemShell, SWT.NONE);
		btnCancel.setBounds(212, 195, 80, 27);
		btnCancel.setText("取消");

		if (SystemConfig.init() != null) {
			MessageBox dialog = new MessageBox(LoginSystemShell, SWT.ICON_INFORMATION);
			dialog.setText("警告");
			dialog.setMessage(SystemConfig.errorStatus(SystemConfig.init()));
			dialog.open();
			System.exit(0);
		}

		String userName = SystemConfig.userDao.findUserNameByUserID(2);
		textCount.setText(userName);

		String psw = SystemConfig.userDao.findPswByUserID(2);
		textPsw.setText(psw);

		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				String account = textCount.getText();
				String psw = textPsw.getText();

				if (SystemConfig.userDao.findPassWordByName(account).equals(psw)) {
					LoginSystemShell.close();
					if (account.equals("StarCode"))
						isSuperLogin = true;
					try {
						// SystemConfig.userDao.setLogin();
						IndexPage indexPage = new IndexPage();
						indexPage.open();
					} catch (Exception e1) {
						log.FileException("IndexPage");
					}
				}

				else {
					MessageBox dialog = new MessageBox(LoginSystemShell, SWT.OK | SWT.ICON_INFORMATION);
					dialog.setText("警告");
					dialog.setMessage("您输入的账号或密码有误，请重新输入！");
					dialog.open();
				}
				// } //else {
				// MessageBox messageBox = new MessageBox(LoginSystemShell,
				// SWT.ICON_INFORMATION | SWT.OK);
				// messageBox.setText("重复运行");
				// messageBox.setMessage("已经打开了一个本程序窗口，不要重复打开");
				// messageBox.open();
				/*
				 * int rc = messageBox.open(); if (rc == SWT.YES) { String
				 * account = textCount.getText(); String psw =
				 * textPsw.getText();
				 * 
				 * if (SystemConfig.userDao.findPassWordByName(account).equals(
				 * psw)) { LoginSystemShell.close(); if
				 * (account.equals("StarCode")) isSuperLogin = true; try {
				 * IndexPage indexPage = new IndexPage(); indexPage.open(); }
				 * catch (Exception e1) { log.FileException("IndexPage"); } }
				 * 
				 * else { MessageBox dialog = new MessageBox(LoginSystemShell,
				 * SWT.OK | SWT.ICON_INFORMATION); dialog.setText("警告");
				 * dialog.setMessage("您输入的账号或密码有误，请重新输入！"); dialog.open(); } }
				 * else { LoginSystemShell.close(); }
				 */
				// }
			}
		});

		btnCancel.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				LoginSystemShell.close();
			}
		});
	}
}
