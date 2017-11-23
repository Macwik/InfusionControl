package com.bd.SecondPage;

import com.bd.Dao.PatientAddDao;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.bd.Control.BedPatientBound;
import com.bd.Control.SystemConfig;
import com.bd.Control.Util.StringUtil;
import com.bd.objects.PatientInfo;

public class BedSetPage {

    protected Shell BedSetShell;
    private Text textBedNum;// 床位号 两位数字
    private Text textPatientName;
    private Text textPatientAge;// 病人年龄 1-3位数字
    private Text textPatientID; // 住院号先不验证
    private Text textPatientSymptom;// 病症
    private Combo comboPatientGender;
    private Button btnOk;
    private Button btnCancel;

    public BedSetPage() {
        Display display = Display.getDefault();
        createContents();
        BedSetShell.open();
        BedSetShell.layout();
        while (!BedSetShell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    // *创建页面的各个资源
    protected void createContents() {
        BedSetShell = new Shell(SWT.APPLICATION_MODAL | SWT.CLOSE);
        BedSetShell.setImage(SWTResourceManager.getImage(BedSetPage.class, "/resource/youdao.png"));
        BedSetShell.setSize(538, 384);
        BedSetShell.setText("床位设置");
        BedSetShell.setLocation(Display.getCurrent().getClientArea().width / 2 - BedSetShell.getShell().getSize().x / 2,
                Display.getCurrent().getClientArea().height / 2 - BedSetShell.getSize().y / 2);

        Label lblNewLabel = new Label(BedSetShell, SWT.NONE);
        lblNewLabel.setFont(SWTResourceManager.getFont("微软雅黑", 14, SWT.NORMAL));
        lblNewLabel.setBounds(127, 29, 85, 25);
        lblNewLabel.setText("病 床 号 :");

        textBedNum = new Text(BedSetShell, SWT.BORDER);
        textBedNum.setFont(SWTResourceManager.getFont("微软雅黑", 14, SWT.NORMAL));
        textBedNum.setBounds(218, 26, 173, 28);
        textBedNum.addKeyListener(new KeyListener() {

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.keyCode == SWT.CR) {
                    textPatientName.setFocus();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
        });

        Label lblNewLabel_1 = new Label(BedSetShell, SWT.NONE);
        lblNewLabel_1.setFont(SWTResourceManager.getFont("微软雅黑", 14, SWT.NORMAL));
        lblNewLabel_1.setBounds(127, 72, 85, 25);
        lblNewLabel_1.setText("病人姓名:");

        textPatientName = new Text(BedSetShell, SWT.BORDER);
        textPatientName.setFont(SWTResourceManager.getFont("微软雅黑", 14, SWT.NORMAL));
        textPatientName.setBounds(218, 69, 173, 28);
        textPatientName.addKeyListener(new KeyListener() {

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.keyCode == SWT.CR) {
                    textPatientAge.setFocus();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
        });

        Label lblNewLabel_2 = new Label(BedSetShell, SWT.NONE);
        lblNewLabel_2.setFont(SWTResourceManager.getFont("微软雅黑", 14, SWT.NORMAL));
        lblNewLabel_2.setBounds(127, 118, 85, 25);
        lblNewLabel_2.setText("性     别 :");

        comboPatientGender = new Combo(BedSetShell, SWT.READ_ONLY);
        comboPatientGender.setFont(SWTResourceManager.getFont("微软雅黑", 14, SWT.NORMAL));
        comboPatientGender.setItems(new String[]{"男", "女"});
        comboPatientGender.setBounds(218, 115, 88, 25);
        comboPatientGender.setText("男");

        Label lblNewLabel_3 = new Label(BedSetShell, SWT.NONE);
        lblNewLabel_3.setFont(SWTResourceManager.getFont("微软雅黑", 14, SWT.NORMAL));
        lblNewLabel_3.setBounds(127, 162, 85, 25);
        lblNewLabel_3.setText("年     龄 :");

        textPatientAge = new Text(BedSetShell, SWT.BORDER);
        textPatientAge.setFont(SWTResourceManager.getFont("微软雅黑", 14, SWT.NORMAL));
        textPatientAge.setBounds(218, 159, 173, 28);
        textPatientAge.addKeyListener(new KeyListener() {

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.keyCode == SWT.CR) {
                    textPatientID.setFocus();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
        });

        Label lblNewLabel_4 = new Label(BedSetShell, SWT.NONE);
        lblNewLabel_4.setFont(SWTResourceManager.getFont("微软雅黑", 14, SWT.NORMAL));
        lblNewLabel_4.setBounds(127, 205, 85, 25);
        lblNewLabel_4.setText("住 院 号 :");

        textPatientID = new Text(BedSetShell, SWT.BORDER);
        textPatientID.setFont(SWTResourceManager.getFont("微软雅黑", 14, SWT.NORMAL));
        textPatientID.setBounds(218, 202, 173, 28);
        textPatientID.addKeyListener(new KeyListener() {

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.keyCode == SWT.CR) {
                    textPatientSymptom.setFocus();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
        });

        Label lblNewLabel_5 = new Label(BedSetShell, SWT.NONE);
        lblNewLabel_5.setFont(SWTResourceManager.getFont("微软雅黑", 14, SWT.NORMAL));
        lblNewLabel_5.setBounds(127, 249, 85, 25);
        lblNewLabel_5.setText("疾病名称:");

        textPatientSymptom = new Text(BedSetShell, SWT.BORDER);
        textPatientSymptom.setFont(SWTResourceManager.getFont("微软雅黑", 14, SWT.NORMAL));
        textPatientSymptom.setBounds(218, 246, 173, 28);
        textPatientSymptom.addKeyListener(new KeyListener() {

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

        btnOk = new Button(BedSetShell, SWT.NONE);
        btnOk.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String bedId = textBedNum.getText().trim();
                String patientAge = textPatientAge.getText().trim();
                String disease = textPatientSymptom.getText().trim();
                String patientID = textPatientID.getText().trim();
                String name = textPatientName.getText().trim();
                PatientAddDao patientAddDao = new PatientAddDao();
                if (StringUtil.verifyWrite(bedId, name, patientID, patientAge, disease)) {
                    String age = StringUtil.getNum(patientAge) + "";
                    PatientInfo pi = getPatientInfo(name, patientID, age, disease);
                    if (SystemConfig.patientDao.canInsert(patientID)
                            && SystemConfig.bedPatientBoundDao.canInsert(patientID)
                            && BedPatientBound.addBedPatientBound(getBedNum(bedId), pi)) {
                        SystemConfig.patientDao.addPatient(patientID, name, pi.getPatientGender(), age, disease);
                        SystemConfig.bedPatientBoundDao.addBedPatientBound(patientID, getBedNum(bedId));
                        BedSetShell.close();
                    } else {
                        if (!BedPatientBound.containValue(new PatientInfo(patientID))) {
                            MessageBox dialog = new MessageBox(BedSetShell, SWT.ICON_INFORMATION | SWT.YES | SWT.NO);
                            dialog.setText("警告");
                            dialog.setMessage("该病床已有病人或此病人已有床位，是否仍要继续添加？");
                            int rc = dialog.open();
                            if (rc == SWT.YES) {
                                SystemConfig.patientDao.deletePatient(patientID);
                                SystemConfig.bedPatientBoundDao.deleteBedPatientInfo(patientID);
                                SystemConfig.patientDao.addPatient(patientID, name, pi.getPatientGender(), age,
                                        disease);
                                SystemConfig.bedPatientBoundDao.addBedPatientBound(patientID, getBedNum(bedId));
                                BedPatientBound.addBedPatientBound(getBedNum(bedId), pi);
                                BedSetShell.close();
                            }
                        } else {
                            MessageBox dialog = new MessageBox(BedSetShell, SWT.ICON_INFORMATION | SWT.OK);
                            dialog.setText("警告");
                            dialog.setMessage("该病人尚未出院，无法添加！！！");
                            dialog.open();
                        }
                    }

                } else {
                    int[] bl = {0, 0, 0, 0, 0};
                    if (!StringUtil.isBedID(bedId)) {
                        bl[0] = 1;
                    }
                    if (!StringUtil.notEmptyString(name)) {
                        bl[1] = 1;
                    }
                    if (!StringUtil.isAge(patientAge)) {
                        bl[2] = 1;
                    }
                    if (!StringUtil.isPatientID(patientID)) {
                        bl[3] = 1;
                    }
                    if (!StringUtil.notEmptyString(disease)) {
                        bl[4] = 1;
                    }

                    String[] display = {" 病床号 ", " 病人姓名 ", " 病人年龄 ", " 住院号 ", " 疾病名称 "};
                    if (bl[0] + bl[1] + bl[2] + bl[3] + bl[4] != 0) {
                        StringBuilder dis = new StringBuilder();
                        if (bl[0] == 1) {
                            textBedNum.setText("");
                            dis.append(display[0]);
                        }
                        if (bl[1] == 1) {
                            textPatientName.setText("");
                            dis.append(display[1]);
                        }
                        if (bl[2] == 1) {
                            textPatientAge.setText("");
                            dis.append(display[2]);
                        }
                        if (bl[3] == 1) {
                            textPatientID.setText("");
                            dis.append(display[3]);
                        }
                        if (bl[4] == 1) {
                            textPatientSymptom.setText("");
                            dis.append(display[4]);
                        }
                        MessageBox dialog = new MessageBox(BedSetShell, SWT.OK | SWT.ICON_INFORMATION);
                        dialog.setText("警告");
                        dialog.setMessage("请正确输入" + dis + "!!!");
                        dialog.open();

                    }
                }
            }
        });
        btnOk.setFont(SWTResourceManager.getFont("微软雅黑", 14, SWT.NORMAL));
        btnOk.setBounds(127, 299, 85, 35);
        btnOk.setText("确定");

        btnCancel = new Button(BedSetShell, SWT.NONE);
        btnCancel.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                BedSetShell.close();
            }

        });
        btnCancel.setFont(SWTResourceManager.getFont("微软雅黑", 14, SWT.NORMAL));
        btnCancel.setBounds(302, 299, 89, 35);
        btnCancel.setText("取消");
    }

    // *获取病人信息
    public PatientInfo getPatientInfo(String name, String patientID, String patientAge, String disease) {
        PatientInfo patientInfo = new PatientInfo(patientID);
        patientInfo.setPatientName(name);
        patientInfo.setPatientGender(comboPatientGender.getText());
        patientInfo.setPatientAge(patientAge);
        patientInfo.setPatientSymptom(disease);

        return patientInfo;
    }

    // *获取病床号
    public String getBedNum(String num) {
        int i = StringUtil.getNum(num);
        if (i < 10)
            return "0" + i;
        else
            return i + "";

    }

}
