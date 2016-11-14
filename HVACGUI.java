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
import java.util.InputMismatchException;

/**
 * Give the HVAC ServiceCall application a GUI.
 You should be able to add at least Furnace and CentralAC service calls.
 Can you use radio buttons to indicate what type of service call you are creating?
 Use JLists to display the service calls.
 Be able to resolve a service call.
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
        setSize(800,500);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //display current date on form
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("EEE MMM dd");
        String sDate = df.format(date);
        lblDate.setText(sDate);
        //set list modrl for jList component
        listModel = new DefaultListModel<ServiceCall>();
        //initializes jList box with list model
        scJList.setModel(listModel);
        addListeners();

    }
    //listener methods for components
    private void addListeners() {
        furnaceRadioButton.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                //changes enabled state to match radio button class option
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
                        //creates a new instance of a central ac service call getting data from various components
                        CentralAC c = new CentralAC(txtAddress.getText(), txtDescription.getText(), new Date(), txtModel.getText());
                        //add service call to jlist
                        listModel.addElement(c);
                        txtAddress.setText("");
                        txtDescription.setText("");
                        txtModel.setText("");
                    } else if (furnaceRadioButton.isSelected()) {
                        Furnace f = new Furnace(txtAddress.getText(), txtDescription.getText(), new Date(), typeComboBox.getSelectedIndex());
                        listModel.addElement(f);

                        txtAddress.setText("");
                        txtDescription.setText("");
                        txtModel.setText("");
                    }
            }
        });
        scJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    //get object form jlist and explicitly convert it to a service call object instance
                    ServiceCall selected = (ServiceCall) scJList.getSelectedValue();
                    //provide information to user about selected item
                    lblAddress.setText(selected.getServiceAddress());
                    lblDescription.setText(selected.getProblemDescription());
                    DateFormat df = new SimpleDateFormat("EEE MMM dd");
                    String sDate = df.format(selected.getReportedDate());
                    lblDateReported.setText(sDate);
                }
                //clears information from resolved service call
                catch (NullPointerException npe){
                    lblAddress.setText("");
                    lblDescription.setText("");
                    lblDateReported.setText("");

                }
            }
        });
        resolveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //add resolution data to service call
                    ServiceCall selected = (ServiceCall) scJList.getSelectedValue();
                    selected.setFee(Double.parseDouble(txtFee.getText()));
                    selected.setResolution(txtResolution.getText());
                    //remove service call from list
                    listModel.removeElement(selected);
                    //clears component text property
                    txtFee.setText("");
                    txtResolution.setText("");
                }
                catch(NumberFormatException nfe){
                    JOptionPane.showMessageDialog(rootPane, "Please enter a numeric value for Service Call Fee");
                    txtFee.setText("");
                    txtFee.requestFocus();
                }
            }
        });
    }
}
