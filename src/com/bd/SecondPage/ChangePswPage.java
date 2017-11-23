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

import com.bd.Control.SystemConfig;

public class ChangePswPage {

	protected Shell ChangePswShell;
	private Text textPsw1;
	private Text textPsw2;
	private Label lblNewLabel_2;
	private Text textCount;
	private Button btnOk;

	public ChangePswPage() {
		Display display = Display.getDefault();
		createContents();
		ChangePswShell.open();
		ChangePswShell.layout();
		while (!ChangePswShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		ChangePswShell = new Shell(SWT.CLOSE | SWT.APPLICATION_MODAL);
		ChangePswShell.setImage(SWTResourceManager.getImage(ChangePswPage.class, "/resource/youdao.png"));
		ChangePswShell.setSize(378, 315);
		ChangePswShell.setText("修改密码");
		ChangePswShell.setLocation(
				Display.getCurrent().getClientArea().width / 2 - ChangePswShell.getShell().getSize().x / 2,
				Display.getCurrent().getClientArea().height / 2 - ChangePswShell.getSize().y / 2);

		lblNewLabel_2 = new Label(ChangePswShell, SWT.NONE);
		lblNewLabel_2.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel_2.setBounds(44, 45, 112, 25);
		lblNewLabel_2.setText("请输入账户名：");

		textCount = new Text(ChangePswShell, SWT.BORDER);
		textCount.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		textCount.setBounds(160, 45, 142, 25);
		textCount.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == SWT.CR) {
					textPsw1.setFocus();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		Label lblNewLabel = new Label(ChangePswShell, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel.setBounds(44, 91, 110, 25);
		lblNewLabel.setText("输 入 新 密 码:");

		textPsw1 = new Text(ChangePswShell, SWT.BORDER | SWT.PASSWORD);
		textPsw1.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		textPsw1.setBounds(160, 88, 142, 28);
		textPsw1.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == SWT.CR) {
					textPsw2.setFocus();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		Label lblNewLabel_1 = new Label(ChangePswShell, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel_1.setBounds(44, 135, 112, 25);
		lblNewLabel_1.setText("确 认 新 密 码:");

		textPsw2 = new Text(ChangePswShell, SWT.BORDER | SWT.PASSWORD);
		textPsw2.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		textPsw2.setBounds(160, 136, 142, 27);
		textPsw2.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == SWT.CR) {
					btnOk.setFocus();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		btnOk = new Button(ChangePswShell, SWT.NONE);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String account = textCount.getText().trim();
				String psw1 = textPsw1.getText().trim();
				String psw2 = textPsw2.getText().trim();

				if (account.matches("^[a-zA-Z0-9_-]{5,15}$") && psw1.matches("^\\s*[^\\s\u4e00-\u9fa5]{6,16}\\s*$")) {
					if (psw1.equals(psw2)) {
						SystemConfig.userDao.updateUser(account, psw1);
						MessageBox dialog = new MessageBox(ChangePswShell, SWT.OK | SWT.ICON_INFORMATION);
						dialog.setText("消息");
						dialog.setMessage("密码修改成功！");
						if (dialog.open() == SWT.OK)
							ChangePswShell.close();
					} else {
						textPsw1.setText("");
						textPsw2.setText("");
						MessageBox dialog = new MessageBox(ChangePswShell, SWT.OK | SWT.ICON_INFORMATION);
						dialog.setText("警告");
						dialog.setMessage("两次密码输入不相同，请重新输入！");
						dialog.open();
					}
				} else {
					if (!account.matches("^[a-zA-Z0-9_-]{5,15}$")) {
						textCount.setText("");
						MessageBox dialog = new MessageBox(ChangePswShell, SWT.OK | SWT.ICON_INFORMATION);
						dialog.setText("警告");
						dialog.setMessage("您输入的用户名不合法，用户名可以包括字母、数字和下划线，长度在5~15位。");
						dialog.open();
					} else {
						textPsw1.setText("");
						textPsw2.setText("");
						MessageBox dialog = new MessageBox(ChangePswShell, SWT.OK | SWT.ICON_INFORMATION);
						dialog.setText("警告");
						dialog.setMessage("您输入的密码不合法，可以包括字母、数字、下划线和特殊字符，长度为6位及以上。");
						dialog.open();
					}
				}
			}
		});
		btnOk.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		btnOk.setBounds(44, 199, 83, 27);
		btnOk.setText("确定");

		Button btnCancel = new Button(ChangePswShell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ChangePswShell.close();
			}
		});
		btnCancel.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		btnCancel.setBounds(222, 199, 80, 27);
		btnCancel.setText("取消");

	}
}
