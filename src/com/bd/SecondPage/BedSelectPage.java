package com.bd.SecondPage;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.bd.Control.PhoneBedBound;
import com.bd.Control.SystemConfig;
import com.bd.Control.Util.StringUtil;
import com.bd.Manager.AboutPhone;
import com.bd.UI.ImgPhonePanel;
import com.bd.UI.Util.CompositeUtil;
import com.bd.objects.PhoneInfo;

public class BedSelectPage {
	protected Shell BedSelectShell;
	private Button[] checkButton;
	private ImgPhonePanel imgPhone;

	public BedSelectPage(ImgPhonePanel imgPhone) {

		Display display = Display.getDefault();
		this.imgPhone = imgPhone;
		checkButton = new Button[72];
		createContents();
		makeBedChecked();

		BedSelectShell.open();
		while (!BedSelectShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	// *显示护士绑定的病床
	public void makeBedChecked() {
		if (PhoneBedBound.getPhoneBedMap().containsKey(new PhoneInfo(imgPhone.getPhoneNum()))) {
			ArrayList<String> bedNum = PhoneBedBound.getBoundBed(imgPhone.getPhoneNum());
			Iterator<String> it = bedNum.iterator();
			while (it.hasNext()) {
				checkButton[StringUtil.getNum(it.next()) - 1].setSelection(true);
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		BedSelectShell = new Shell(SWT.CLOSE | SWT.APPLICATION_MODAL);
		BedSelectShell.setImage(SWTResourceManager.getImage(BedSelectPage.class, "/resource/youdao.png"));
		BedSelectShell.setSize(505, 458);
		BedSelectShell.setText("床位手机绑定");
		BedSelectShell.setLocation(
				Display.getCurrent().getClientArea().width / 2 - BedSelectShell.getShell().getSize().x / 2,
				Display.getCurrent().getClientArea().height / 2 - BedSelectShell.getSize().y / 2);

		Label lblNum = new Label(BedSelectShell, SWT.NONE);
		lblNum.setFont(SWTResourceManager.getFont("微软雅黑", 18, SWT.NORMAL));
		lblNum.setBounds(183, 10, 64, 36);
		lblNum.setText("编号：");

		Label lblBedNum = new Label(BedSelectShell, SWT.NONE);
		lblBedNum.setFont(SWTResourceManager.getFont("微软雅黑", 18, SWT.NORMAL));
		lblBedNum.setBounds(253, 10, 81, 36);

		lblBedNum.setText(imgPhone.getPhoneNum());

		Composite composite = new Composite(BedSelectShell, SWT.NONE);
		composite.setBounds(0, 96, 486, 335);

		checkButton = CompositeUtil.createCheckButton(composite);

		Button btnOk = new Button(composite, SWT.NONE);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PhoneBedBound.addPhoneBedBound(getPhoneID(), getSelected());

				if (getSelected().isEmpty())
					AboutPhone.setPhoneColor(getPhoneID(), false);

				SystemConfig.phoneBedBoundDao.updatePhoneBedBound(getPhoneID(),
						StringUtil.getArrayListString(getSelected()));
				BedSelectShell.close();
			}
		});
		btnOk.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		btnOk.setBounds(282, 278, 80, 27);
		btnOk.setText("确定");

		Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				BedSelectShell.close();
			}
		});
		btnCancel.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		btnCancel.setBounds(396, 278, 80, 27);
		btnCancel.setText("取消");

		Button btnAllOn = new Button(BedSelectShell, SWT.NONE);
		btnAllOn.setBounds(356, 63, 54, 27);
		btnAllOn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (int i = 0; i < 72; i++) {
					checkButton[i].setSelection(true);
				}
			}
		});
		btnAllOn.setText("全选");

		Button btnAllOff = new Button(BedSelectShell, SWT.NONE);
		btnAllOff.setBounds(416, 63, 54, 27);
		btnAllOff.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (int i = 0; i < 72; i++) {
					checkButton[i].setSelection(false);
				}
			}
		});
		btnAllOff.setText("清空");
	}

	// *获取全部被选中的病床号
	public ArrayList<String> getSelected() {
		ArrayList<String> selected = new ArrayList<String>();
		for (int i = 0; i < 72; i++) {
			if (checkButton[i].getSelection())
				selected.add(checkButton[i].getText());
		}

		return selected;
	}

	// *获取被选中的手机ID
	public String getPhoneID() {
		return imgPhone.getPhoneNum();
	}
}
