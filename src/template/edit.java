package template;

import javax.swing.*;
import java.awt.event.*;

public class edit extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField classname;
    private JTextArea json;
    private JLabel tip;
    private JButton settingButton;

    final String filePath;

    public edit(String path) {
        setContentPane(contentPane);
        setModal(true);

        filePath = path;

        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        settingButton.addActionListener(e -> onTap());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onTap() {
        change_default_value cdv = new change_default_value();
        cdv.setSize(400, 300);
        cdv.setLocationRelativeTo(null);
        cdv.setVisible(true);
    }

    private void onOK() {

        if (this.classname.getText().isEmpty()) {
            tip.setText("class name cannot be empty！");
            return;
        }

        if (this.json.getText().isEmpty()) {
            tip.setText("json cannot be empty！");
            return;
        }

        tip.setText("");

        // 获取类字符串
        String classText = ModelUtil.toClassString(this.classname.getText(), this.json.getText());

        // 保存并声称类文件
        FileUtil.createFileWithClass(this.filePath, this.classname.getText(), classText);

        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        edit dialog = new edit("");
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
