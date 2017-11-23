package com.bd.SecondPage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

import com.bd.Control.DeviceBound;
import com.bd.Control.SystemConfig;

import logfile.log;

public class DeviceLoginPage {

	protected Shell DeviceLoginShell;
	private Table tableDeviceID;

	public DeviceLoginPage() {
		Display display = Display.getDefault();
		createContents();
		initTable();

		DeviceLoginShell.open();
		DeviceLoginShell.layout();

		while (!DeviceLoginShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public void initTable() {
		HashSet<String> hs = DeviceBound.getDeviceBoundSet();
		Iterator<String> it = hs.iterator();
		while (it.hasNext()) {
			String ii = it.next();
			TableItem item = new TableItem(this.getTableDeviceID(), SWT.CENTER);
			item.setText(ii);
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		DeviceLoginShell = new Shell(SWT.CLOSE | SWT.APPLICATION_MODAL);
		DeviceLoginShell.setImage(SWTResourceManager.getImage(DeviceLoginPage.class, "/resource/youdao.png"));
		DeviceLoginShell.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		DeviceLoginShell.setSize(419, 346);
		DeviceLoginShell.setText("输液终端绑定");
		DeviceLoginShell.setLocation(
				Display.getCurrent().getClientArea().width / 2 - DeviceLoginShell.getShell().getSize().x / 2,
				Display.getCurrent().getClientArea().height / 2 - DeviceLoginShell.getSize().y / 2);

		tableDeviceID = new Table(DeviceLoginShell, SWT.NONE);
		tableDeviceID.setBounds(0, 0, 415, 274);

		Button btnAddDevice = new Button(DeviceLoginShell, SWT.NONE);
		btnAddDevice.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new InputDeviceIDPage(DeviceLoginPage.this);
			}
		});
		btnAddDevice.setBounds(40, 280, 80, 27);
		btnAddDevice.setText("添加");

		Button btnDeleteDevice = new Button(DeviceLoginShell, SWT.NONE);
		btnDeleteDevice.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (tableDeviceID.getItemCount() > 0 && tableDeviceID.getSelectionCount() > 0) {
					int ii = tableDeviceID.getSelectionIndex();
					String i = tableDeviceID.getItem(ii).getText(0);

					DeviceBound.deleteDevice(i);
					tableDeviceID.remove(ii);

					String key = getKey(i);
					if (key != null)
						SystemConfig.deviceProp.remove(key);

					FileOutputStream oFile = null;
					try {
						oFile = new FileOutputStream(SystemConfig.deviceFile);
						SystemConfig.deviceProp.store(oFile, null);
						oFile.close();
					} catch (FileNotFoundException e1) {
						log.FileException("DeviceLoginPage");
						MessageBox dialog = new MessageBox(DeviceLoginShell, SWT.OK | SWT.ICON_INFORMATION);
						dialog.setText("警告");
						dialog.setMessage("删除失败！！");
						dialog.open();
					} catch (IOException e1) {
						log.FileException("DeviceLoginPage");
						MessageBox dialog = new MessageBox(DeviceLoginShell, SWT.OK | SWT.ICON_INFORMATION);
						dialog.setText("警告");
						dialog.setMessage("删除失败！！");
						dialog.open();
					}
				}
			}
		});

		btnDeleteDevice.setBounds(169, 280, 80, 27);
		btnDeleteDevice.setText("删除");

		Button btnClose = new Button(DeviceLoginShell, SWT.NONE);
		btnClose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DeviceLoginShell.close();
			}
		});
		btnClose.setBounds(294, 280, 80, 27);
		btnClose.setText("关闭");

	}

	// *获取终端绑定表
	public Table getTableDeviceID() {
		return tableDeviceID;
	}

	// *获取终端绑定表里面的所有数据
	public ArrayList<String> getAllboundDeviceID() {
		ArrayList<String> allBoundDeviceID = new ArrayList<String>();
		TableItem[] items = tableDeviceID.getItems();
		for (int i = 0; i < items.length; i++)
			allBoundDeviceID.add(items[i].getText());
		return allBoundDeviceID;
	}

	public static String getKey(String value) {
		Set<Entry<Object, Object>> entry = SystemConfig.deviceProp.entrySet();
		Iterator<Entry<Object, Object>> it = entry.iterator();
		Entry<Object, Object> en;
		while (it.hasNext()) {
			en = it.next();
			if (value.equals((String) (en.getValue())))
				return (String) (en.getKey());
		}
		return null;
	}
}
