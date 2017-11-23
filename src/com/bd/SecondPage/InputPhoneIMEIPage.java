package com.bd.SecondPage;

import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

import com.bd.Control.PhoneBound;
import com.bd.Control.SystemConfig;

public class InputPhoneIMEIPage {

	protected Shell inputPhoneIMEIShell;
	private PhoneLoginPage phoneLogin;
	private Combo comboIMEI;

	public InputPhoneIMEIPage(PhoneLoginPage phoneLogin) {
		this.phoneLogin = phoneLogin;
		Display display = Display.getDefault();
		createContents();
		Iterator<Object> iter = SystemConfig.phoneIMEIprop.keySet().iterator();
		while (iter.hasNext()) {
			comboIMEI.add(SystemConfig.phoneIMEIprop.getProperty((String) iter.next()));
		}
		comboIMEI.select(0);
		inputPhoneIMEIShell.open();
		inputPhoneIMEIShell.layout();
		while (!inputPhoneIMEIShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		inputPhoneIMEIShell = new Shell(SWT.APPLICATION_MODAL | SWT.CLOSE);
		inputPhoneIMEIShell.setImage(SWTResourceManager.getImage(InputPhoneIMEIPage.class, "/resource/youdao.png"));
		inputPhoneIMEIShell.setSize(546, 244);
		inputPhoneIMEIShell.setText("护士终端绑定");
		inputPhoneIMEIShell.setLocation(
				Display.getCurrent().getClientArea().width / 2 - inputPhoneIMEIShell.getShell().getSize().x / 2,
				Display.getCurrent().getClientArea().height / 2 - inputPhoneIMEIShell.getSize().y / 2);

		Label lblNewLabel = new Label(inputPhoneIMEIShell, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel.setBounds(32, 46, 192, 21);
		lblNewLabel.setText("请选择要绑定的手机编号：");

		Combo comboPhoneNum = new Combo(inputPhoneIMEIShell, SWT.NONE);
		comboPhoneNum.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		comboPhoneNum.setItems(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10" });
		comboPhoneNum.setBounds(230, 46, 77, 29);
		comboPhoneNum.setText("01");

		Label lblNewLabel_1 = new Label(inputPhoneIMEIShell, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel_1.setBounds(32, 103, 192, 21);
		lblNewLabel_1.setText("请输入手机的IMEI号码：");

		Button btnOK = new Button(inputPhoneIMEIShell, SWT.NONE);
		btnOK.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String id = comboIMEI.getText().trim();
				String num = comboPhoneNum.getText();
				if (PhoneBound.notContains(num, id)) {
					PhoneBound.addPhoneBound(num, id);
					TableItem item = new TableItem(phoneLogin.getTablePhoneLogin(), SWT.CENTER);
					item.setText(new String[] { num, id });
					// *添加数据库
					SystemConfig.phoneBedBoundDao.updatePhoneImei(num, id);
					SystemConfig.phoneBedBoundDao.setPhoneOnline(num);// 数据库设置手机在线
					// SystemConfig.phoneBedBoundDao.updatePhoneBedBound(num,
					// "");
					inputPhoneIMEIShell.close();
				} else {
					MessageBox dialog = new MessageBox(inputPhoneIMEIShell, SWT.OK | SWT.ICON_INFORMATION);
					dialog.setText("警告");
					dialog.setMessage("此护士终端或此IMEI已注册！");
					dialog.open();
				}
			}
		});
		btnOK.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		btnOK.setBounds(230, 155, 95, 29);
		btnOK.setText("确定");

		Button btnCancel = new Button(inputPhoneIMEIShell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				inputPhoneIMEIShell.close();
			}
		});
		btnCancel.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		btnCancel.setBounds(396, 155, 95, 29);
		btnCancel.setText("取消");

		comboIMEI = new Combo(inputPhoneIMEIShell, SWT.NONE);
		comboIMEI.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		comboIMEI.setBounds(230, 103, 261, 26);

	}
}
