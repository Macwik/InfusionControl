package com.bd.UI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import com.bd.SecondPage.AboutPage;
import com.bd.SecondPage.BedSetPage;
import com.bd.SecondPage.ChangePswPage;
import com.bd.SecondPage.DeviceLoginPage;
import com.bd.SecondPage.LoginSystemPage;
import com.bd.SecondPage.PhoneLoginPage;
import com.bd.SecondPage.ReportForm;
import com.bd.SecondPage.VerifyPswPage;

public class SystemMenu {
	private Shell shell;
	private MenuItem menuItemDeviceBound;
	private MenuItem menuItemNurseBound;
	private MenuItem menuItemBedSet;
	private MenuItem menuItemCountManage;
	private MenuItem menuItemReportTable;
	private MenuItem menuItemHelp;
	private MenuItem menuItemCopy;
	@SuppressWarnings("unused")
	private BedSetPage bedSet;
	@SuppressWarnings("unused")
	private VerifyPswPage verifyPsw;
	@SuppressWarnings("unused")
	private PhoneLoginPage phoneLogin;

	public SystemMenu(Shell shell) {
		this.shell = shell;
		createMenu();
	}

	public void createMenu() {

		Menu mainMenu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(mainMenu);

		MenuItem menuItemSysConfig = new MenuItem(mainMenu, SWT.CASCADE);
		menuItemSysConfig.setText("系统设置");

		Menu menuSysConfig = new Menu(menuItemSysConfig);
		menuItemSysConfig.setMenu(menuSysConfig);

		menuItemDeviceBound = new MenuItem(menuSysConfig, SWT.NONE);
		menuItemDeviceBound.setText("输液终端绑定(D)");
		menuItemDeviceBound.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new DeviceLoginPage();
			}
		});

		menuItemNurseBound = new MenuItem(menuSysConfig, SWT.NONE);
		menuItemNurseBound.setText("护士报警终端绑定(T)");
		menuItemNurseBound.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				phoneLogin = new PhoneLoginPage();
			}
		});

		menuItemBedSet = new MenuItem(menuSysConfig, SWT.NONE);
		menuItemBedSet.setText("床位设置(B)");
		menuItemBedSet.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				bedSet = new BedSetPage();
			}
		});

		menuItemCountManage = new MenuItem(mainMenu, SWT.CASCADE);
		menuItemCountManage.setText("账号管理(M)");
		menuItemCountManage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!LoginSystemPage.isSuperLogin)
					verifyPsw = new VerifyPswPage();
				else
					new ChangePswPage();
			}
		});

		menuItemReportTable = new MenuItem(mainMenu, SWT.CASCADE);
		menuItemReportTable.setText("统计报表(R)");
		menuItemReportTable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new ReportForm();
			}
		});

		MenuItem menuItemAbout = new MenuItem(mainMenu, SWT.CASCADE);
		menuItemAbout.setText("关于");

		Menu menuAbout = new Menu(menuItemAbout);
		menuItemAbout.setMenu(menuAbout);

		menuItemHelp = new MenuItem(menuAbout, SWT.NONE);
		menuItemHelp.setText("操作指南");

		menuItemCopy = new MenuItem(menuAbout, SWT.NONE);
		menuItemCopy.setText("关于(A)");
		menuItemCopy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new AboutPage();
			}
		});

	}

	public void setKeyDdown() {
		new DeviceLoginPage();
	}

	public void setKeyTdown() {
		phoneLogin = new PhoneLoginPage();
	}

	public void setKeyBdown() {
		bedSet = new BedSetPage();
	}

	public void setKeyMdown() {
		verifyPsw = new VerifyPswPage();
	}

	public void setKeyRdown() {
		new ReportForm();
	}

	public void setKeyAdown() {
		new AboutPage();
	}
}
