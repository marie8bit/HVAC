import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Marie on 11/8/2016.
 */
public class HVACGUI extends JFrame{
    private JPanel rootPanel;
    private JRadioButton centralACRadioButton;
    private JTextField txtAddress;
    private JTextField txtDescription;
    private JLabel lblDate;
    private JList scJList;
    private JTextField txtResolution;
    private JTextField txtFee;
    private JButton addButton;
    private JButton resolveButton;
    private JComboBox typeComboBox;
    private JTextField txtModel;
    private JLabel lblAddress;
    private JLabel lblDescription;
    private JLabel lblFee;
    private JLabel lblDateReported;
    private JRadioButton furnaceRadioButton;
    private DefaultListModel<ServiceCall>listModel;
    protected HVACGUI(){
        setContentPane(rootPanel);
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("EEE MMM dd");
        String sDate = df.format(date);
        lblDate.setText(sDate);
        listModel = new DefaultListModel<ServiceCall>();
        //initializes jList box with list model
        scJList.setModel(listModel);
        addListeners();



    }

    private void addListeners() {
        furnaceRadioButton.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                typeComboBox.setEnabled(true);
                txtModel.setEnabled(false);
            }
        });
        centralACRadioButton.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                typeComboBox.setEnabled(false);
                txtModel.setEnabled(true);
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (centralACRadioButton.isSelected()) {

                    CentralAC c = new CentralAC(txtAddress.getText(), txtDescription.getText(), new Date(), txtModel.getText());
                    listModel.addElement(c);
                    typeComboBox.setEnabled(false);
                    txtModel.setEnabled(false);
                }
                else if (furnaceRadioButton.isSelected()){
                    Furnace f = new Furnace(txtAddress.getText(), txtDescription.getText(), new Date(), typeComboBox.getSelectedIndex());
                    listModel.addElement(f);
                    typeComboBox.setEnabled(false);
                    txtModel.setEnabled(false);
                    txtAddress.setText("");
                    txtDescription.setText("");
                }
            }
        });
        scJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    ServiceCall selected = (ServiceCall) scJList.getSelectedValue();
                    lblAddress.setText(selected.getServiceAddress());
                    lblDescription.setText(selected.getProblemDescription());
                    DateFormat df = new SimpleDateFormat("EEE MMM dd");
                    String sDate = df.format(selected.getReportedDate());
                    lblDateReported.setText(sDate);
                }
                catch (NullPointerException npe){
                    lblAddress.setText("");
                    lblDescription.setText("");
                    lblDateReported.setText("");
                    txtFee.setText("");
                    txtResolution.setText("");
                }
            }
        });
        resolveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ServiceCall selected = (ServiceCall) scJList.getSelectedValue();
                selected.setFee(Double.parseDouble(txtFee.getText()));
                selected.setResolution(txtResolution.getText());
                listModel.removeElement(selected);
            }
        });
    }
}
