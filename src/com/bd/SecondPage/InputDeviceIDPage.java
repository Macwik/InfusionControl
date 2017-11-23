package com.bd.SecondPage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

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
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.bd.Control.DeviceBound;
import com.bd.Control.SystemConfig;
import com.bd.Control.Util.StringUtil;

import logfile.log;

public class InputDeviceIDPage {

	protected Shell inputDeviceIDShell;
	private Text textDeviceID;
	private DeviceLoginPage deviceLogin;
	private Button btnOK;

	public InputDeviceIDPage(DeviceLoginPage deviceLogin) {
		this.deviceLogin = deviceLogin;
		Display display = Display.getDefault();
		createContents();
		inputDeviceIDShell.open();
		inputDeviceIDShell.layout();
		while (!inputDeviceIDShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		inputDeviceIDShell = new Shell(SWT.CLOSE | SWT.APPLICATION_MODAL);
		inputDeviceIDShell.setImage(SWTResourceManager.getImage(InputDeviceIDPage.class, "/resource/youdao.png"));
		inputDeviceIDShell.setSize(450, 137);
		inputDeviceIDShell.setText("提示");
		inputDeviceIDShell.setLocation(
				Display.getCurrent().getClientArea().width / 2 - inputDeviceIDShell.getShell().getSize().x / 2,
				Display.getCurrent().getClientArea().height / 2 - inputDeviceIDShell.getSize().y / 2);

		Label lblNewLabel = new Label(inputDeviceIDShell, SWT.NONE);
		lblNewLabel.setBounds(27, 10, 145, 17);
		lblNewLabel.setText("请输入要添加的输液终端ID");

		textDeviceID = new Text(inputDeviceIDShell, SWT.BORDER);
		textDeviceID.setBounds(27, 33, 383, 23);
		textDeviceID.addKeyListener(new KeyListener() {

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

		btnOK = new Button(inputDeviceIDShell, SWT.NONE);
		btnOK.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String id = textDeviceID.getText().trim();
				if (StringUtil.isDeviceID(id)) {

					if (DeviceBound.addDevice(id) && !SystemConfig.deviceProp.containsValue(id)) {

						FileOutputStream oFile = null;
						// try {
						// oFile = new
						// FileOutputStream(SystemConfig.deviceFile);
						// } catch (FileNotFoundException e1) {
						// log.FileException("InputDeviceIDPage");
						// MessageBox dialog = new
						// MessageBox(inputDeviceIDShell, SWT.OK |
						// SWT.ICON_INFORMATION);
						// dialog.setText("警告");
						// dialog.setMessage("添加失败，请重试！！");
						// dialog.open();
						// } // true表示追加打开
						// SystemConfig.deviceProp.setProperty("d" + id, id);
						// try {
						// SystemConfig.deviceProp.store(oFile, null);
						// } catch (IOException e1) {
						// log.FileException("InputDeviceIDPage");
						// MessageBox dialog = new
						// MessageBox(inputDeviceIDShell, SWT.OK |
						// SWT.ICON_INFORMATION);
						// dialog.setText("警告");
						// dialog.setMessage("添加失败，请重试！！");
						// dialog.open();
						// }
						try {
							oFile = new FileOutputStream(SystemConfig.deviceFile);
						} catch (FileNotFoundException e3) {
							e3.printStackTrace();
						}
						HashSet<String> temp = DeviceBound.getDeviceSet();
						SystemConfig.deviceProp.clear();
						ArrayList<String> prop = StringUtil.propSort(temp);
						for (String s : prop) {
							SystemConfig.deviceProp.put("d" + s, s);
						}
						SystemConfig.deviceProp.put("d" + id, id); // setProperty("d"
						// + id,
						// id);
						try {
							SystemConfig.deviceProp.store(oFile, null);
						} catch (IOException e2) {
							e2.printStackTrace();
						}
						try {
							oFile.close();
						} catch (IOException e1) {
							log.FileException("InputDeviceIDPage");
							MessageBox dialog = new MessageBox(inputDeviceIDShell, SWT.OK | SWT.ICON_INFORMATION);
							dialog.setText("警告");
							dialog.setMessage("添加失败，请重试！！");
							dialog.open();
						}

						TableItem item = new TableItem(deviceLogin.getTableDeviceID(), SWT.CENTER);
						item.setText(id);
					} else {
						MessageBox dialog = new MessageBox(inputDeviceIDShell, SWT.OK | SWT.ICON_INFORMATION);
						dialog.setText("警告");
						dialog.setMessage("此终端已经被注册！");
						dialog.open();
					}
					inputDeviceIDShell.close();
				} else {
					MessageBox dialog = new MessageBox(inputDeviceIDShell, SWT.OK | SWT.ICON_INFORMATION);
					dialog.setText("警告");
					dialog.setMessage("请正确输入输液终端的ID号");
					dialog.open();
				}
			}
		});
		btnOK.setBounds(225, 62, 80, 27);
		btnOK.setText("确定");

		Button btnCancel = new Button(inputDeviceIDShell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				inputDeviceIDShell.close();
			}
		});
		btnCancel.setBounds(330, 62, 80, 27);
		btnCancel.setText("取消");

	}

}
