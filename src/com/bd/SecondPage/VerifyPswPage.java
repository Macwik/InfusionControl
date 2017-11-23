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

public class VerifyPswPage {

	protected Shell ChangePswShell;
	private Text textPsw;
	private Button btnNewButton_1;
	private Button btnOK;

	public VerifyPswPage() {
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
		ChangePswShell.setImage(SWTResourceManager.getImage(VerifyPswPage.class, "/resource/youdao.png"));
		ChangePswShell.setText(" 修改密码");
		ChangePswShell.setSize(370, 220);
		ChangePswShell.setLocation(
				Display.getCurrent().getClientArea().width / 2 - ChangePswShell.getShell().getSize().x / 2,
				Display.getCurrent().getClientArea().height / 2 - ChangePswShell.getSize().y / 2);

		Label lblNewLabel = new Label(ChangePswShell, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel.setBounds(34, 59, 118, 27);
		lblNewLabel.setText("验证原始密码：");

		textPsw = new Text(ChangePswShell, SWT.BORDER | SWT.PASSWORD);
		textPsw.setBounds(154, 60, 170, 27);
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

		btnOK = new Button(ChangePswShell, SWT.NONE);
		btnOK.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (textPsw.getText().equals(SystemConfig.userDao.findPswByUserID(2))) {
					ChangePswShell.close();
					new ChangePswPage();
				} else {
					textPsw.setText("");
					MessageBox dialog = new MessageBox(ChangePswShell, SWT.OK | SWT.ICON_INFORMATION);
					dialog.setText("警告");
					dialog.setMessage("您输入的密码有误，请重新输入！");
					dialog.open();
				}
			}
		});
		btnOK.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		btnOK.setBounds(131, 115, 80, 27);
		btnOK.setText("确定");

		btnNewButton_1 = new Button(ChangePswShell, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ChangePswShell.close();
			}
		});
		btnNewButton_1.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		btnNewButton_1.setBounds(244, 115, 80, 27);
		btnNewButton_1.setText("取消");
	}

}
