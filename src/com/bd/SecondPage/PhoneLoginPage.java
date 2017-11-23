package com.bd.SecondPage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

import com.bd.Control.PhoneBedBound;
import com.bd.Control.PhoneBound;
import com.bd.Control.SystemConfig;

public class PhoneLoginPage {

	protected Shell PhoneLoginShell;
	private Table tablePhoneLogin;
	private TableColumn tblclmnNum;
	private TableColumn tblclmnImei;

	public PhoneLoginPage() {
		Display display = Display.getDefault();
		createContents();

		if (!PhoneBound.getPhoneBound().isEmpty()) {
			Iterator<Entry<String, String>> it = PhoneBound.getAllBound();

			while (it.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();

				String ii = (String) entry.getKey();
				TableItem item = new TableItem(tablePhoneLogin, SWT.CENTER);
				item.setText(new String[] { ii, (String) entry.getValue() });
			}
		}

		PhoneLoginShell.open();
		PhoneLoginShell.layout();
		while (!PhoneLoginShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		PhoneLoginShell = new Shell(SWT.APPLICATION_MODAL | SWT.CLOSE);
		PhoneLoginShell.setImage(SWTResourceManager.getImage(PhoneLoginPage.class, "/resource/youdao.png"));
		PhoneLoginShell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		PhoneLoginShell.setSize(331, 362);
		PhoneLoginShell.setText("护士终端绑定");
		PhoneLoginShell.setLocation(
				Display.getCurrent().getClientArea().width / 2 - PhoneLoginShell.getShell().getSize().x / 2,
				Display.getCurrent().getClientArea().height / 2 - PhoneLoginShell.getSize().y / 2);

		tablePhoneLogin = new Table(PhoneLoginShell, SWT.FULL_SELECTION);
		tablePhoneLogin.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		tablePhoneLogin.setBounds(0, 0, 325, 293);
		tablePhoneLogin.setHeaderVisible(true);

		tblclmnNum = new TableColumn(tablePhoneLogin, SWT.CENTER);
		tblclmnNum.setWidth(160);
		tblclmnNum.setText("终端编号");

		tblclmnImei = new TableColumn(tablePhoneLogin, SWT.LEFT);
		tblclmnImei.setWidth(165);
		tblclmnImei.setText("IMEI");

		Button btnAddPhone = new Button(PhoneLoginShell, SWT.NONE);
		btnAddPhone.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new InputPhoneIMEIPage(PhoneLoginPage.this);
			}
		});
		btnAddPhone.setBounds(10, 299, 80, 27);
		btnAddPhone.setText("添加");

		Button btnDeletePhone = new Button(PhoneLoginShell, SWT.NONE);
		btnDeletePhone.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (tablePhoneLogin.getItemCount() > 0 && tablePhoneLogin.getSelectionCount() > 0) {
					String ss = tablePhoneLogin.getItem(tablePhoneLogin.getSelectionIndex()).getText();
					PhoneBound.removePhoneBound(ss, PhoneBound.getPhoneIMEI(ss));
					tablePhoneLogin.remove(tablePhoneLogin.getSelectionIndex());
					// 使手机离线
					PhoneBedBound.removePhoneBedBound(ss);
					SystemConfig.phoneBedBoundDao.updatePhoneBedBound(ss, "");
					SystemConfig.phoneBedBoundDao.setPhoneOffline(ss);
				}
			}
		});
		btnDeletePhone.setBounds(124, 299, 80, 27);
		btnDeletePhone.setText("删除");

		Button btnClose = new Button(PhoneLoginShell, SWT.NONE);
		btnClose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PhoneLoginShell.close();
			}
		});
		btnClose.setBounds(235, 299, 80, 27);
		btnClose.setText("关闭");

	}

	// 获取手机绑定信息表
	public Table getTablePhoneLogin() {
		return tablePhoneLogin;
	}

	public HashMap<String, String> getPhoneBound() {
		HashMap<String, String> phoneBound = new HashMap<String, String>();
		TableItem[] all = tablePhoneLogin.getItems();
		for (int i = 0; i < tablePhoneLogin.getItemCount(); i++) {
			phoneBound.put(all[i].getText(0), all[i].getText(1));
		}
		return phoneBound;
	}

}
