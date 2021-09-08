package template;

import com.intellij.ide.util.PropertiesComponent;

import javax.swing.*;
import java.awt.event.*;

public class change_default_value extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField integer;
    private JTextField bool;
    private JTextField string;
    private JTextField array;
    private JTextField object;
    private JLabel tip;
    private JButton buttonReset;

    public change_default_value() {
        setContentPane(contentPane);
        setModal(true);

        integer.setText(ModelUtil.defaultValue.integer);
        bool.setText(ModelUtil.defaultValue.bool);
        string.setText(ModelUtil.defaultValue.string);
        array.setText(ModelUtil.defaultValue.array);
        object.setText(ModelUtil.defaultValue.object);

        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        buttonReset.addActionListener(e -> onReset());

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

    private void onOK() {
        ModelUtil.defaultValue.integer = integer.getText().isEmpty() ? "null" : integer.getText();
        ModelUtil.defaultValue.bool = bool.getText().isEmpty() ? "null" : bool.getText();
        ModelUtil.defaultValue.string = string.getText().isEmpty() ? "null" : string.getText();
        ModelUtil.defaultValue.array = array.getText().isEmpty() ? "null" : array.getText();
        ModelUtil.defaultValue.object = object.getText().isEmpty() ? "null" : object.getText();

        SettingUtil.storeSetting();

        dispose();
    }

    private void onCancel() {
        dispose();
    }

    private void onReset() {

        tip.setText("Success！");

        ModelUtil.defaultValue.integer = "null";
        ModelUtil.defaultValue.bool = "null";
        ModelUtil.defaultValue.string = "null";
        ModelUtil.defaultValue.array = "null";
        ModelUtil.defaultValue.object = "null";

        integer.setText(ModelUtil.defaultValue.integer);
        bool.setText(ModelUtil.defaultValue.bool);
        string.setText(ModelUtil.defaultValue.string);
        array.setText(ModelUtil.defaultValue.array);
        object.setText(ModelUtil.defaultValue.object);
    }

    public static void main(String[] args) {
        change_default_value dialog = new change_default_value();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
