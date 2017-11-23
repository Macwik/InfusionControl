package com.bd.SecondPage;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.window.WindowManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.StyledTextPrintOptions;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.bd.Control.SystemConfig;
import com.bd.Control.Util.StringUtil;
import com.bd.objects.InfusionEvent;
import com.bd.objects.PatientInfo;

public class ReportForm {

	private Display display;
	protected Shell shell;
	private Table table;
	private Text textPatientID;
	private Composite composite;
	private Label lblDatetime;

	private Label lblPatientID;
	private Label lblName;
	private Label lblSex;
	private Label lblAge;
	private DateTime dateTime;
	private Button btnCheckDate;

	private ArrayList<InfusionEvent> al = null;
	private PatientInfo pi = null;
	private Font font;
	private String date = "";
	private Button btnSearch;

	ApplicationWindow applicationWindow;
	WindowManager manager;

	StyledTextPrintOptions styledTextPrintOptions;
	StyledText styledText;

	Text text;

	Printer printer;
	GC gc;
	int lineHeight = 0;
	int tabWidth = 0;
	int leftMargin, rightMargin, topMargin, bottomMargin;
	int x, y;
	int index, end;
	String textToPrint;
	String tabs;
	StringBuffer wordBuffer;

	public ReportForm() {
		display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(SWT.CLOSE | SWT.APPLICATION_MODAL);
		shell.setImage(SWTResourceManager.getImage(ReportForm.class, "/resource/youdao.png"));
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		shell.setSize(665, 585);
		shell.setText("统计报表");
		shell.setLocation(Display.getCurrent().getClientArea().width / 2 - shell.getShell().getSize().x / 2,
				Display.getCurrent().getClientArea().height / 2 - shell.getSize().y / 2);

		composite = new Composite(shell, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		composite.setBounds(0, 74, 659, 473);
		composite.setVisible(false);

		table = new Table(composite, SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		table.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		table.setBounds(0, 99, 659, 340);

		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(232);
		tblclmnNewColumn.setText("日期");

		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(186);
		tblclmnNewColumn_1.setText("时间");

		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(236);
		tableColumn.setText("事件");

		Label lblNewLabel_2 = new Label(composite, SWT.NONE);
		lblNewLabel_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_2.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel_2.setBounds(296, 21, 48, 26);
		lblNewLabel_2.setText("日期：");

		lblDatetime = new Label(composite, SWT.NONE);
		lblDatetime.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblDatetime.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblDatetime.setBounds(350, 21, 114, 26);

		Label lblNewLabel_4 = new Label(composite, SWT.NONE);
		lblNewLabel_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_4.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel_4.setBounds(10, 21, 61, 26);
		lblNewLabel_4.setText("住院号：");

		lblPatientID = new Label(composite, SWT.NONE);
		lblPatientID.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblPatientID.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblPatientID.setBounds(77, 21, 128, 26);

		Label lblNewLabel_6 = new Label(composite, SWT.NONE);
		lblNewLabel_6.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_6.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel_6.setBounds(10, 53, 61, 26);
		lblNewLabel_6.setText("姓   名：");

		lblName = new Label(composite, SWT.NONE);
		lblName.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblName.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblName.setBounds(77, 53, 128, 26);

		Label lblNewLabel_8 = new Label(composite, SWT.NONE);
		lblNewLabel_8.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_8.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel_8.setBounds(296, 53, 48, 26);
		lblNewLabel_8.setText("性别：");

		lblSex = new Label(composite, SWT.NONE);
		lblSex.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblSex.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblSex.setBounds(350, 53, 61, 26);

		Label lblNewLabel_10 = new Label(composite, SWT.NONE);
		lblNewLabel_10.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_10.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel_10.setBounds(470, 53, 48, 26);
		lblNewLabel_10.setText("年龄：");

		lblAge = new Label(composite, SWT.NONE);
		lblAge.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblAge.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblAge.setBounds(524, 53, 86, 26);

		Button btnPrint = new Button(composite, SWT.NONE);
		btnPrint.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		btnPrint.setBounds(455, 445, 80, 27);
		btnPrint.setText("打印");

		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		btnNewButton.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		btnNewButton.setBounds(559, 445, 80, 27);
		btnNewButton.setText("关闭");

		Label label = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setText("报表");
		label.setBounds(0, 91, 659, 2);

		Label label_1 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setBounds(0, 13, 659, 2);
		label_1.setText("报表");
		btnPrint.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				if (pi != null && !al.isEmpty()) {
					if (!btnCheckDate.getSelection()) {
						date = getDate(dateTime);
					} else
						date = "";
					int i = StringUtil.getChineseCharacterCount(pi.getPatientName());
					StringBuilder sb = new StringBuilder();
					sb.append("\t住院号：" + StringUtil.appendSpace(pi.getPatientId(), 17) + "日期："
							+ StringUtil.appendSpace(date, 18) + "\n");
					sb.append("\t姓  名： " + StringUtil.appendSpace(pi.getPatientName(), 18 - i * 3 / 2) + "性别："
							+ StringUtil.appendSpace(pi.getPatientGender(), 15) + "年龄："
							+ StringUtil.appendSpace(pi.getPatientAge(), 10) + "\n\n");

					sb.append("\t日期" + StringUtil.appendSpace(" ", 15) + "时间" + StringUtil.appendSpace(" ", 13)
							+ "事件\n\n");

					Iterator<InfusionEvent> it = al.iterator();
					while (it.hasNext()) {
						InfusionEvent ie = it.next();
						sb.append("\t" + StringUtil.appendSpace(ie.getDate(), 18)
								+ StringUtil.appendSpace(ie.getTime(), 18) + StringUtil.appendSpace(ie.getEvent(), 18)
								+ "\n");
					}
					Print(sb.toString());
				}
			}
		});

		Label lblNewLabel = new Label(shell, SWT.SHADOW_NONE);
		lblNewLabel.setBounds(9, 32, 61, 26);
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel.setText("住院号：");

		textPatientID = new Text(shell, SWT.BORDER);
		textPatientID.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		textPatientID.setBounds(76, 29, 128, 29);
		textPatientID.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == SWT.CR) {
					dateTime.setFocus();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setBounds(290, 21, 48, 26);
		lblNewLabel_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_1.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel_1.setText("日期：");

		dateTime = new DateTime(shell, SWT.BORDER);
		dateTime.setBounds(340, 21, 114, 24);
		dateTime.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		dateTime.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == SWT.CR) {
					btnSearch.setFocus();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		btnSearch = new Button(shell, SWT.NONE);
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				table.removeAll();
				composite.setVisible(false);
				String patientID = textPatientID.getText();
				if (StringUtil.isPatientID(patientID)) {
					if (btnCheckDate.getSelection()) {
						al = SystemConfig.infusionEventDao.getAllData(patientID);
						lblDatetime.setText("");
					} else {
						al = SystemConfig.infusionEventDao.getAllData(patientID, getDate(dateTime));
						lblDatetime.setText(getDate(dateTime));
					}
					if (!al.isEmpty()) {
						composite.setVisible(true);

						pi = SystemConfig.patientDao.getPatientInfo(patientID);
						if (pi != null) {
							lblPatientID.setText(pi.getPatientId());
							lblName.setText(pi.getPatientName());
							lblSex.setText(pi.getPatientGender());
							lblAge.setText(pi.getPatientAge());
						}
						Iterator<InfusionEvent> it = al.iterator();
						while (it.hasNext()) {
							InfusionEvent ie = it.next();
							TableItem item = new TableItem(table, SWT.CENTER);
							item.setText(new String[] { ie.getDate(), ie.getTime(), ie.getEvent() });
						}
					}
				}
			}
		});
		btnSearch.setBounds(529, 10, 92, 63);
		btnSearch.setFont(SWTResourceManager.getFont("微软雅黑", 14, SWT.NORMAL));
		btnSearch.setText("查询");

		btnCheckDate = new Button(shell, SWT.CHECK);
		btnCheckDate.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		btnCheckDate.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		btnCheckDate.setBounds(340, 51, 98, 17);
		btnCheckDate.setText("忽略日期查询");

	}

	public String getDate(DateTime dateTime) {
		if (dateTime != null) {
			String date = dateTime.getYear() + "-" + add0(dateTime.getMonth() + 1) + "-" + add0(dateTime.getDay());
			return date;
		}
		return null;
	}

	public static String add0(int i) {
		if (i < 10)
			return "0" + i;
		return i + "";
	}

	void Print(String prt) {
		PrintDialog dialog = new PrintDialog(shell, SWT.NULL);
		PrinterData data = dialog.open();
		if (data == null)
			return;
		textToPrint = prt;
		printer = new Printer(data);
		Thread printingThread = new Thread("printing") {
			public void run() {
				printText(printer);
				printer.dispose();
			}
		};
		printingThread.start();
	}

	void printText(Printer printer) {
		if (printer.startJob("Text")) {
			Rectangle clientArea = printer.getClientArea();
			Rectangle trim = printer.computeTrim(0, 0, 0, 0);
			Point dpi = printer.getDPI();
			leftMargin = dpi.x + trim.x;
			rightMargin = clientArea.width - dpi.x + trim.x + trim.width;
			topMargin = dpi.y + trim.y;
			bottomMargin = clientArea.height - dpi.y + trim.y + trim.height;

			int tabSize = 4;
			StringBuffer tabBuffer = new StringBuffer(tabSize);
			for (int i = 0; i < tabSize; i++)
				tabBuffer.append(' ');
			tabs = tabBuffer.toString();
			gc = new GC(printer);

			tabWidth = gc.stringExtent(tabs).x;
			lineHeight = gc.getFontMetrics().getHeight();

			printText();
			printer.endJob();
			gc.dispose();
		}
	}

	void printText() {
		printer.startPage();
		wordBuffer = new StringBuffer();
		x = leftMargin;
		y = topMargin;
		index = 0;
		end = textToPrint.length();
		while (index < end) {
			char c = textToPrint.charAt(index);
			index++;
			if (c != 0) {
				if (c == 0x0a || c == 0x0d) {
					if (c == 0x0d && index < end && textToPrint.charAt(index) == 0x0a) {
						index++;
					}
					printWordBuffer();
					newline();
				} else {
					if (c != '\t') {
						wordBuffer.append(c);
					}
					if (Character.isWhitespace(c)) {
						printWordBuffer();
						if (c == '\t') {
							x += tabWidth;
						}
					}
				}
			}
		}
		if (y + lineHeight <= bottomMargin) {
			printer.endPage();
		}
	}

	void printWordBuffer() {
		if (wordBuffer.length() > 0) {
			String word = wordBuffer.toString();
			int wordWidth = gc.stringExtent(word).x;
			if (x + wordWidth > rightMargin) {
				newline();
			}
			font = new Font(display, "宋体", 70, SWT.NORMAL);
			gc.setFont(font);
			gc.drawString(word, x, y, false);
			x += wordWidth;
			wordBuffer = new StringBuffer();
		}
	}

	void newline() {
		x = leftMargin;
		y += lineHeight;
		if (y + lineHeight > bottomMargin) {
			printer.endPage();
			if (index + 1 < end) {
				y = topMargin;
				printer.startPage();
			}
		}
	}
}
