/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Sean
 */
//Import
import java.io.File;
import javax.swing.JFileChooser;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.time.Year;

public class PMS extends javax.swing.JFrame {

    /**
     * Creates new form PMS
     */
    //Globals
    Database database = new Database("jdbc:mysql://localhost/employee_payroll", "root", "");
    String[] db = {"jdbc:mysql://localhost/employee_payroll", "root", ""};
    EditEmployee EditEmployeeWindow = new EditEmployee();
    AddPayroll proll = new AddPayroll();

    
    public PMS() {
        initComponents();
        Connect();
        Fetch();
        teachingtype.setVisible(false);
        codee.setVisible(false);
    }
    
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    public void Connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(db[0], db[1], db[2]);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Missing Necessary Libraries");
            Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Invalid");
            Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Fetch(){
        int q;
        try {
            pst = con.prepareStatement("SELECT * FROM Employee");
            rs = pst.executeQuery();  
            ResultSetMetaData rss = rs.getMetaData();
            q = rss.getColumnCount();
            
            DefaultTableModel df = (DefaultTableModel)jTable1.getModel(); 
            df.setRowCount(0);
            while(rs.next()){
                 Vector v2 = new Vector();
                 for(int i = 1; i <= q; i++){
                     v2.add(rs.getString("code"));
                     v2.add(rs.getString("lname"));
                     v2.add(rs.getString("fname"));
                     v2.add(rs.getString("position"));
                     v2.add(rs.getString("department"));
                     v2.add(rs.getString("type"));
                     v2.add(rs.getString("payrate"));
                     v2.add(rs.getString("salary"));
                 }
                 df.addRow(v2);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Incompatible Database");
            Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }
    //Manual Codes
    public void EditEmployeee(){
        try {
            String fname = fNameField.getText();
            String lname = lNameField.getText();
            String position = PositionField.getText();
            String department = DepartmentField.getText();
            String type = teachingtype.getText();
            String payrate = payratee.getText();
            String code = codee.getText();
            //Update
            pst = con.prepareStatement("UPDATE Employee SET fname=?, lname=?, department=?, position=?, type=?, payrate=? WHERE CODE=?");
            //Set Values
            pst.setString(1, fname);
            pst.setString(2, lname);
            pst.setString(3, department);
            pst.setString(4, position);
            pst.setString(5, type);
            pst.setString(6, payrate);
            pst.setString(7, code);
            //Check if Record is Found
            int k = pst.executeUpdate();
            //Fetch
            Fetch();
            if(k == 1){
                JOptionPane.showMessageDialog(this, "Record Updated");
                fNameField.setText("");
                lNameField.setText("");
                PositionField.setText("");
                DepartmentField.setText("");
                payratee.setText("");
            }
            else{
                JOptionPane.showMessageDialog(this, "Record Not Updated");
                fNameField.setText("");
                lNameField.setText("");
                PositionField.setText("");
                DepartmentField.setText("");
                payratee.setText("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void SubmitSalary(){
        try {
            //Update Salary
            pst = con.prepareStatement("UPDATE Employee SET salary = ? WHERE code = ?");
            pst.setString(1, salary.getText());
            pst.setString(2, codee.getText());
            
            int k = pst.executeUpdate();
            Fetch();
                     
            if(k == 1){

            }
            else{
                JOptionPane.showMessageDialog(this, "Payroll Error");
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddPayroll.class.getName()).log(Level.SEVERE, null, ex);
        }
        Fetch();
        try {
            pst = con.prepareStatement("INSERT INTO Payroll(employee_code,lname,type,employee_salary,teachingHours,payRate,overPay,overHours,allowance,absentHours,deductions) VALUES(?,?,?,?,?,?,?,?,?,?,?)");
            pst.setString(1, codee.getText());
            pst.setString(2, lNameField.getText());
            pst.setString(3, teachingtype.getText());
            pst.setString(4, salary.getText());
            pst.setString(5, thours.getText());
            pst.setString(6, payratee.getText());
            pst.setString(7, opay.getText());
            pst.setString(8, ohours.getText());
            pst.setString(9, allowance.getText());
            pst.setString(10, ahours.getText());
            pst.setString(11, deductions.getText());
            
            int k = pst.executeUpdate();
            if(k == 1){
                JOptionPane.showMessageDialog(this, "Payroll Added");
            }
            else{
                JOptionPane.showMessageDialog(this, "Payroll Error");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        public void UpdateSalary(){
        try {
            //Update Salary
            pst = con.prepareStatement("UPDATE Employee SET salary = ? WHERE code = ?");
            pst.setString(1, salary.getText());
            pst.setString(2, codee.getText());
            
            int k = pst.executeUpdate();
            Fetch();
                     
            if(k == 1){

            }
            else{
                JOptionPane.showMessageDialog(this, "Payroll Error");
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddPayroll.class.getName()).log(Level.SEVERE, null, ex);
        }
        Fetch();
        try {
            pst = con.prepareStatement("UPDATE Payroll SET lname=?,type=?,employee_salary=?,teachingHours=?,payRate=?,overPay=?,overHours=?,allowance=?,absentHours=?,deductions=? WHERE employee_code=?");
            pst.setString(1, lNameField.getText());
            pst.setString(2, teachingtype.getText());
            pst.setString(3, salary.getText());
            pst.setString(4, thours.getText());
            pst.setString(5, payratee.getText());
            pst.setString(6, opay.getText());
            pst.setString(7, ohours.getText());
            pst.setString(8, allowance.getText());
            pst.setString(9, ahours.getText());
            pst.setString(10, deductions.getText());
            pst.setString(11, codee.getText());
            
            int k = pst.executeUpdate();
            if(k == 1){
                JOptionPane.showMessageDialog(this, "Payroll Updated");
            }
            else{
                JOptionPane.showMessageDialog(this, "Payroll Error");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lNameField = new javax.swing.JTextField();
        DepartmentField = new javax.swing.JTextField();
        PositionField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        fNameField = new javax.swing.JTextField();
        TEmployeeButton = new javax.swing.JButton();
        NTEmployeeButton = new javax.swing.JButton();
        payratee = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        TeachingEmployee = new javax.swing.JButton();
        NonTeachingEmployee = new javax.swing.JButton();
        AllEmployee = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        teachingtype = new javax.swing.JLabel();
        codee = new javax.swing.JLabel();
        salary = new javax.swing.JLabel();
        allowance = new javax.swing.JLabel();
        deductions = new javax.swing.JLabel();
        thours = new javax.swing.JLabel();
        ohours = new javax.swing.JLabel();
        ahours = new javax.swing.JLabel();
        opay = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("XYZ Payroll Management System");
        setMinimumSize(new java.awt.Dimension(1280, 720));
        setResizable(false);
        setSize(new java.awt.Dimension(1280, 720));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Payroll Management System");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Employee ID", "Last Name", "First Name", "Position", "Department", "Employee Type", "PayRate", "Salary"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(6).setResizable(false);
            jTable1.getColumnModel().getColumn(7).setResizable(false);
        }

        lNameField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        DepartmentField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        PositionField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("First Name:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Last Name:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Department:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Position:");

        fNameField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        fNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fNameFieldActionPerformed(evt);
            }
        });

        TEmployeeButton.setText("Add as Teaching Employee");
        TEmployeeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TEmployeeButtonActionPerformed(evt);
            }
        });

        NTEmployeeButton.setText("Add as Non-Teaching Employee");
        NTEmployeeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NTEmployeeButtonActionPerformed(evt);
            }
        });

        payratee.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Pay Rate:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(DepartmentField)
                    .addComponent(fNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lNameField)
                    .addComponent(PositionField, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(NTEmployeeButton)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(payratee, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TEmployeeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(fNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(lNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TEmployeeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(payratee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(DepartmentField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(PositionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NTEmployeeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        jButton3.setText("View Employee");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Edit Employee");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Delete Employee");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Employee Options");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Payroll Options");

        jButton6.setText("Add Payroll");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Edit Payroll");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Delete Payroll");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("View Payroll");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Table Options");

        jButton12.setText("Search");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setText("Sort");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setText("Payroll Table");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel7))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel8))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel6)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        TeachingEmployee.setText("View Teaching Employees");
        TeachingEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TeachingEmployeeActionPerformed(evt);
            }
        });

        NonTeachingEmployee.setText("View Non-Teaching Employees");
        NonTeachingEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NonTeachingEmployeeActionPerformed(evt);
            }
        });

        AllEmployee.setText("View All Employees");
        AllEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AllEmployeeActionPerformed(evt);
            }
        });

        jButton11.setText("Load Database");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton15.setText("New Database");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AllEmployee)
                .addGap(18, 18, 18)
                .addComponent(TeachingEmployee)
                .addGap(18, 18, 18)
                .addComponent(NonTeachingEmployee)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton11)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AllEmployee)
                    .addComponent(TeachingEmployee)
                    .addComponent(NonTeachingEmployee)
                    .addComponent(jButton11)
                    .addComponent(jButton15))
                .addGap(49, 49, 49))
        );

        teachingtype.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        teachingtype.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.borderColor"));
        teachingtype.setText("|");

        codee.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        codee.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.borderColor"));
        codee.setText("|");

        salary.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        salary.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.borderColor"));

        allowance.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        allowance.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.borderColor"));

        deductions.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        deductions.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.borderColor"));

        thours.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        thours.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.borderColor"));

        ohours.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        ohours.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.borderColor"));

        ahours.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        ahours.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.borderColor"));

        opay.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        opay.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.borderColor"));

        jButton2.setText("New School Year");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addGap(389, 389, 389)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(salary, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(34, 34, 34)
                                        .addComponent(thours, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(deductions, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(allowance, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ohours, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(ahours, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(opay, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(codee, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(teachingtype, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(deductions)
                            .addComponent(ohours)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(codee)
                            .addComponent(salary)
                            .addComponent(thours))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(teachingtype)
                            .addComponent(allowance)
                            .addComponent(ahours)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(opay))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton2)))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fNameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fNameFieldActionPerformed

    private void TEmployeeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TEmployeeButtonActionPerformed
        // TODO add your handling code here:
        //Code Incrementation
        int code = Year.now().getValue() * 100000;
        try {
            pst = con.prepareStatement("SELECT MAX(code) FROM Employee");
            rs = pst.executeQuery();
            //
            if(rs.next()){
                code = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
        }
        code = code > 0 ? code + 1 : Year.now().getValue() * 100000+1;

        //Other Information
        String fname = fNameField.getText();
        String lname = lNameField.getText();
        String department = DepartmentField.getText();
        String position = PositionField.getText();
        String type = "Teaching Employee";
        String payrate = payratee.getText();
        //Check if Empty
        if(fname.equals("") || lname.equals("") || department.equals("") || position.equals("") || payrate.equals("")){
            JOptionPane.showMessageDialog(this, "Incomplete Record");
            return;
        }
        //Check if Only Alphabet
        if(fname.matches(".*\\\\d.*") || lname.matches(".*\\d.*") || department.matches(".*\\d.*") || position.matches(".*\\d.*" )){
            JOptionPane.showMessageDialog(this, "Contains Non-Alphabetic Characters");
            return;
        }
        //Check if Negative
        if(payrate.startsWith("-")){
            JOptionPane.showMessageDialog(this, "Input Contains Negative Value");
            return;
        }
        //Check if Only Number
        if(payrate.matches("(\\d*\\.?\\d+)|(\\.\\d+)")){
        }
        else{
            JOptionPane.showMessageDialog(this, "Pay Rate Contains Non-Numeric Characters");
            return;
        }
        //Check if Beyond 15 digits
        if(payrate.length() > 15){
            JOptionPane.showMessageDialog(this, "Pay Rate cannot exceed 15 digits");
        }
        try {
            //Store to Database
            pst = con.prepareStatement("INSERT INTO Employee(code,fname,lname,department,position,type,payrate)VALUES(?,?,?,?,?,?,?)");
            pst.setInt(1, code);
            pst.setString(2, fname);
            pst.setString(3, lname);
            pst.setString(4, department);
            pst.setString(5, position);
            pst.setString(6, type);
            pst.setString(7, payrate);
            
            int k = pst.executeUpdate();
            
            if(k == 1){
                JOptionPane.showMessageDialog(this, "Employee Successfully Added");
                fNameField.setText("");
                lNameField.setText("");
                DepartmentField.setText("");
                PositionField.setText("");
                payratee.setText("");
            }
            else{
                JOptionPane.showMessageDialog(this, "Record Failed");
                fNameField.setText("");
                lNameField.setText("");
                DepartmentField.setText("");
                PositionField.setText("");
                payratee.setText("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
        }
        Fetch();
    }//GEN-LAST:event_TEmployeeButtonActionPerformed

    private void TeachingEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TeachingEmployeeActionPerformed
        // TODO add your handling code here:
        int q;
        try {
            pst = con.prepareStatement("SELECT * FROM Employee");
            rs = pst.executeQuery();  
            ResultSetMetaData rss = rs.getMetaData();
            q = rss.getColumnCount();
            
            DefaultTableModel df = (DefaultTableModel)jTable1.getModel(); 
            df.setRowCount(0);
            while(rs.next()){
                 Vector v2 = new Vector();
                 if(rs.getString("type").equals("Teaching Employee") == false){
                     continue;
                 }
                 for(int i = 1; i <= q; i++){
                     v2.add(rs.getString("code"));
                     v2.add(rs.getString("lname"));
                     v2.add(rs.getString("fname"));
                     v2.add(rs.getString("position"));
                     v2.add(rs.getString("department"));
                     v2.add(rs.getString("type"));
                     v2.add(rs.getString("salary"));
                 }
                 df.addRow(v2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_TeachingEmployeeActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        String dbname = JOptionPane.showInputDialog("Database Name:");
        if(dbname == null){
            return;
        }
        db[0] = "jdbc:mysql://localhost/" + dbname;
        Connect();
        Fetch();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void NTEmployeeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NTEmployeeButtonActionPerformed
        // TODO add your handling code here:
        //Code Incrementation
        int code = Year.now().getValue() * 100000;
        try {
            pst = con.prepareStatement("SELECT MAX(code) FROM Employee");
            rs = pst.executeQuery();
            //
            if(rs.next()){
                code = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
        }
        code = code > 0 ? code + 1 : Year.now().getValue() * 100000+1;

        //Other Information
        String fname = fNameField.getText();
        String lname = lNameField.getText();
        String department = DepartmentField.getText();
        String position = PositionField.getText();
        String type = "Non-Teaching Employee";
        String payrate = payratee.getText();
        //Check if Empty
        if(fname.equals("") || lname.equals("") || department.equals("") || position.equals("") || payrate.equals("")){
            JOptionPane.showMessageDialog(this, "Incomplete Record");
            return;
        }
        //Check if Only Alphabet
        if(fname.matches(".*\\\\d.*") || lname.matches(".*\\d.*") || department.matches(".*\\d.*") || position.matches(".*\\d.*" )){
            JOptionPane.showMessageDialog(this, "Contains Non-Alphabetic Characters");
            return;
        }
        //Check if Negative
        if(payrate.startsWith("-")){
            JOptionPane.showMessageDialog(this, "Input Contains Negative Value");
            return;
        }
        //Check if Only Number
        if(payrate.matches("(\\d*\\.?\\d+)|(\\.\\d+)")){
        }
        else{
            JOptionPane.showMessageDialog(this, "Pay Rate Contains Non-Numeric Characters");
            return;
        }
        //Check if Beyond 15 digits
        if(payrate.length() > 15){
            JOptionPane.showMessageDialog(this, "Pay Rate cannot exceed 15 digits");
        }

        try {
            //Store to Database
            pst = con.prepareStatement("INSERT INTO Employee(code,fname,lname,department,position,type,payrate)VALUES(?,?,?,?,?,?,?)");
            pst.setInt(1, code);
            pst.setString(2, fname);
            pst.setString(3, lname);
            pst.setString(4, department);
            pst.setString(5, position);
            pst.setString(6, type);
            pst.setString(7, payrate);
            
            int k = pst.executeUpdate();
            
            if(k == 1){
                JOptionPane.showMessageDialog(this, "Employee Successfully Added");
                fNameField.setText("");
                lNameField.setText("");
                DepartmentField.setText("");
                PositionField.setText("");
                payratee.setText("");
            }
            else{
                JOptionPane.showMessageDialog(this, "Record Failed");
                fNameField.setText("");
                lNameField.setText("");
                DepartmentField.setText("");
                PositionField.setText("");
                payratee.setText("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
        }
        Fetch();
    }//GEN-LAST:event_NTEmployeeButtonActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel df = (DefaultTableModel)jTable1.getModel();
        //Make Sure a Table is Selected
        if(jTable1.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(this, "No Row Selected");
            return;
        }
        int[] code = jTable1.getSelectedRows();
        //Check if Employee has Salary
        for(int i = 0; i < code.length; i++){
            if(jTable1.getModel().getValueAt(code[i], 7).toString().equals("0.00") == false){
                JOptionPane.showMessageDialog(this, "A Selected Employee has a Payroll");
                return;
            }
        }
        //Confirmation
        int confirm = JOptionPane.showConfirmDialog(this, "Delete " + jTable1.getSelectedRowCount() + " Records?");
        if(confirm == JOptionPane.YES_OPTION){
        }
        else{
            return;
        }
        //Delete Record/s
        int counter = 0;
        int counterf = 0;
        for(int i = 0; i < code.length; i++){
            try {
                String deletecode = jTable1.getModel().getValueAt(code[i], 0).toString();
                //Delete
                pst = con.prepareStatement("DELETE FROM Employee WHERE code=?");
                pst.setString(1, deletecode);
                //Counter for Successful/Failed Deletion
                int k = pst.executeUpdate();
                if(k == 1){
                    counter++;
                }
                else{
                    counterf++;
                }
            } catch (SQLException ex) {
                Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        JOptionPane.showMessageDialog(this, counter + " Records Succesfully Deleted\n" + counterf + " Records can't be Deleted");
        Fetch();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        ViewEmployee ViewEmployee = new ViewEmployee();
                // TODO add your handling code here:
        DefaultTableModel df = (DefaultTableModel)jTable1.getModel();
        //Make Sure a Table is Selected
        if(jTable1.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(this, "No Row Selected");
            return;
        }
        if(jTable1.getSelectedRowCount() > 1){
            JOptionPane.showMessageDialog(this, "More than one Row Selected");
            return;
        }
        //Make Sure it is the Employee Table
        int count = df.getColumnCount();
        if(count != 8){
            JOptionPane.showMessageDialog(this, "Employee Table is not Selected");
            return;
        }
        //Get Highlighted Values
        String code = df.getValueAt(jTable1.getSelectedRow(), 0).toString();
        String lname = df.getValueAt(jTable1.getSelectedRow(), 1).toString();
        String fname = df.getValueAt(jTable1.getSelectedRow(), 2).toString();
        String position = df.getValueAt(jTable1.getSelectedRow(), 3).toString();
        String department = df.getValueAt(jTable1.getSelectedRow(), 4).toString();
        String type = df.getValueAt(jTable1.getSelectedRow(), 5).toString();
        String payrate = df.getValueAt(jTable1.getSelectedRow(), 6).toString();
        String salary = df.getValueAt(jTable1.getSelectedRow(), 7).toString();
        //Transfer Value to New Window and Open
        Employee employee = new Employee(code, lname, fname, position, department, type, salary);
        //Pass Database Information
        ViewEmployee.Code.setText(code);
        ViewEmployee.lNameField2.setText(lname);
        ViewEmployee.fNameField2.setText(fname);
        ViewEmployee.PositionField2.setText(position);
        ViewEmployee.DepartmentField2.setText(department);
        ViewEmployee.payrate2.setText(payrate);
        ViewEmployee.salary.setText(salary);
        ViewEmployee.type.setText(type);
        ViewEmployee.setVisible(true);
        ViewEmployee.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void AllEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AllEmployeeActionPerformed
        // TODO add your handling code here:
        Fetch();
    }//GEN-LAST:event_AllEmployeeActionPerformed

    private void NonTeachingEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NonTeachingEmployeeActionPerformed
        // TODO add your handling code here:
        int q;
        try {
            pst = con.prepareStatement("SELECT * FROM Employee");
            rs = pst.executeQuery();  
            ResultSetMetaData rss = rs.getMetaData();
            q = rss.getColumnCount();
            
            DefaultTableModel df = (DefaultTableModel)jTable1.getModel(); 
            df.setRowCount(0);
            while(rs.next()){
                 Vector v2 = new Vector();
                 if(rs.getString("type").equals("Non-Teaching Employee") == false){
                     continue;
                 }
                 for(int i = 1; i <= q; i++){
                     v2.add(rs.getString("code"));
                     v2.add(rs.getString("lname"));
                     v2.add(rs.getString("fname"));
                     v2.add(rs.getString("position"));
                     v2.add(rs.getString("department"));
                     v2.add(rs.getString("type"));
                     v2.add(rs.getString("salary"));
                 }
                 df.addRow(v2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_NonTeachingEmployeeActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel df = (DefaultTableModel)jTable1.getModel();
        //Make Sure a Table is Selected
        if(jTable1.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(this, "No Row Selected");
            return;
        }
        if(jTable1.getSelectedRowCount() > 1){
            JOptionPane.showMessageDialog(this, "More than one Row Selected");
            return;
        }
        //Make Sure it is the Employee Table
        int count = df.getColumnCount();
        if(count != 8){
            JOptionPane.showMessageDialog(this, "Employee Table is not Selected");
            return;
        }
        //Get Highlighted Values
        String code = df.getValueAt(jTable1.getSelectedRow(), 0).toString();
        String lname = df.getValueAt(jTable1.getSelectedRow(), 1).toString();
        String fname = df.getValueAt(jTable1.getSelectedRow(), 2).toString();
        String position = df.getValueAt(jTable1.getSelectedRow(), 3).toString();
        String department = df.getValueAt(jTable1.getSelectedRow(), 4).toString();
        String type = df.getValueAt(jTable1.getSelectedRow(), 5).toString();
        String payrate = df.getValueAt(jTable1.getSelectedRow(), 6).toString();
        String salary = df.getValueAt(jTable1.getSelectedRow(), 7).toString();
        //Transfer Value to New Window and Open
        Employee employee = new Employee(code, lname, fname, position, department, type, salary);
        //Pass Database Information
        EditEmployeeWindow.Code.setText(code);
        EditEmployeeWindow.lNameField2.setText(lname);
        EditEmployeeWindow.fNameField2.setText(fname);
        EditEmployeeWindow.PositionField2.setText(position);
        EditEmployeeWindow.DepartmentField2.setText(department);
        EditEmployeeWindow.payrate2.setText(payrate);
        EditEmployeeWindow.mainframe(this);
        EditEmployeeWindow.setVisible(true);

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel df = (DefaultTableModel)jTable1.getModel();
        AddPayroll proll = new AddPayroll();
        //Make Sure a Table is Selected
        if(jTable1.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(this, "No Row Selected");
            return;
        }
        if(jTable1.getSelectedRowCount() > 1){
            JOptionPane.showMessageDialog(this, "More than one Row Selected");
            return;
        }
        //Make Sure it is the Employee Table
        int count = df.getColumnCount();
        if(count != 8){
            JOptionPane.showMessageDialog(this, "Employee Table is not Selected");
            return;
        }
        //Get Highlighted Values
        String code = df.getValueAt(jTable1.getSelectedRow(), 0).toString();
        String lname = df.getValueAt(jTable1.getSelectedRow(), 1).toString();
        String type = df.getValueAt(jTable1.getSelectedRow(), 5).toString();
        String payrate = df.getValueAt(jTable1.getSelectedRow(), 6).toString();
        String salary = df.getValueAt(jTable1.getSelectedRow(), 7).toString();
        //Check that there is no salary yet
        if(salary.equals("0.00") == false){
            JOptionPane.showMessageDialog(this, "Employee already has a Payroll");
            return;
        }
        //Pass Values to AddPayroll Frame
        proll.code.setText(code);
        proll.lname.setText(lname);
        proll.type.setText(type);
        //If non-teaching disable teaching hours
        if(type.equals("Non-Teaching Employee")){
            proll.thours.setText("0");
            proll.thours.setEditable(false);
            proll.thours.setVisible(false);
            proll.thourslabel.setVisible(false);
        }
        proll.payrate.setText(payrate);
        proll.mainframe(this);
        proll.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Fetch();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel df = (DefaultTableModel)jTable1.getModel();
        //Make Sure a Table is Selected
        if(jTable1.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(this, "No Row Selected");
            return;
        }
        //If no Salary
        if(df.getValueAt(jTable1.getSelectedRow(), 7).toString().equals("0.00")){
            JOptionPane.showMessageDialog(this, "Employee has no Payroll");
            return;
        }
        //Confirmation
        int confirm = JOptionPane.showConfirmDialog(this, "Delete " + jTable1.getSelectedRowCount() + " Records?");
        if(confirm == JOptionPane.YES_OPTION){
        }
        else{
            return;
        }
        //Delete Record/s
        int counter = 0;
        int counterf = 0;
        int[] code = jTable1.getSelectedRows();
        for(int i = 0; i < code.length; i++){
            try {
                String deletecode = jTable1.getModel().getValueAt(code[i], 0).toString();
                //Delete form Salary
                pst = con.prepareStatement("UPDATE Employee SET salary=? WHERE code = ?");
                pst.setString(1, "0");
                pst.setString(2, deletecode);
                pst.executeUpdate();
                //Delete from Payroll
                pst = con.prepareStatement("DELETE FROM payroll WHERE employee_code=?");
                pst.setString(1, deletecode);
                //Counter for Successful/Failed Deletion
                int k = pst.executeUpdate();
                if(k == 1){
                    counter++;
                }
                else{
                    counterf++;
                }
            } catch (SQLException ex) {
                Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        JOptionPane.showMessageDialog(this, counter + " Records Succesfully Deleted\n" + counterf + " Records Can't be Deleted");
        Fetch();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel df = (DefaultTableModel)jTable1.getModel();
        EditPayroll eproll = new EditPayroll();
        //Make Sure a Table is Selected
        if(jTable1.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(this, "No Row Selected");
            return;
        }
        if(jTable1.getSelectedRowCount() > 1){
            JOptionPane.showMessageDialog(this, "More than one Row Selected");
            return;
        }
        //Make Sure it is the Employee Table
        int count = df.getColumnCount();
        if(count != 8){
            JOptionPane.showMessageDialog(this, "Employee Table is not Selected");
            return;
        }
        //Get Highlighted Values
        String code = df.getValueAt(jTable1.getSelectedRow(), 0).toString();
        //If 0 Salary
        if(df.getValueAt(jTable1.getSelectedRow(), 7).toString().equals("0.00")){
            JOptionPane.showMessageDialog(this, "Employee has no Payroll");
            return;
        }
        try {
            //Get Database Value
            pst = con.prepareStatement("SELECT*FROM payroll WHERE employee_code = ?");
            pst.setString(1, code);
            rs = pst.executeQuery();
            //Display Values
            if(rs.next()){
                eproll.code.setText(rs.getString(1));
                eproll.lname.setText(rs.getString(2));
                eproll.type.setText(rs.getString(3));
                eproll.salary.setText(rs.getString(4));
                eproll.thours.setText(rs.getString(5));
                eproll.payrate.setText(rs.getString(6));
                eproll.otpayfield.setText(rs.getString(7));
                eproll.ohours.setText(rs.getString(8));
                eproll.allowance.setText(rs.getString(9));
                eproll.ahours.setText(rs.getString(10));
                eproll.deductions.setText(rs.getString(11));
            }
            else{
                JOptionPane.showMessageDialog(this, "Payroll not Found");
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(eproll.type.getText().equals("Non-Teaching Employee")){
            eproll.thourslabel.setVisible(false);
            eproll.thours.setVisible(false);
            eproll.thours.setText("0");
        }
        eproll.mainframe(this);
        eproll.setVisible(true);     
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel df = (DefaultTableModel)jTable1.getModel();
        ViewPayRoll vroll = new ViewPayRoll();
        //Make Sure a Table is Selected
        if(jTable1.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(this, "No Row Selected");
            return;
        }
        if(jTable1.getSelectedRowCount() > 1){
            JOptionPane.showMessageDialog(this, "More than one Row Selected");
            return;
        }
        //Make Sure it is the Employee Table
        int count = df.getColumnCount();
        if(count != 8){
            JOptionPane.showMessageDialog(this, "Employee Table is not Selected");
            return;
        }
        //Get Highlighted Values
        String code = df.getValueAt(jTable1.getSelectedRow(), 0).toString();
        //If 0 Salary
        if(df.getValueAt(jTable1.getSelectedRow(), 7).toString().equals("0.00")){
            JOptionPane.showMessageDialog(this, "Employee has no Payroll");
            return;
        }
        try {
            //Get Database Value
            pst = con.prepareStatement("SELECT*FROM payroll WHERE employee_code = ?");
            pst.setString(1, code);
            rs = pst.executeQuery();
            //Display Values
            if(rs.next()){
                vroll.code.setText(rs.getString(1));
                vroll.lname.setText(rs.getString(2));
                vroll.type.setText(rs.getString(3));
                vroll.salary.setText(rs.getString(4));
                vroll.thours.setText(rs.getString(5));
                vroll.payrate.setText(rs.getString(6));
                vroll.otpayfield.setText(rs.getString(7));
                vroll.ohours.setText(rs.getString(8));
                vroll.allowance.setText(rs.getString(9));
                vroll.ahours.setText(rs.getString(10));
                vroll.deductions.setText(rs.getString(11));
            }
            else{
                JOptionPane.showMessageDialog(this, "Payroll not Found");
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(vroll.type.getText().equals("Non-Teaching Employee")){
            vroll.thourslabel.setVisible(false);
            vroll.thours.setVisible(false);
        }
        vroll.setVisible(true);     
    }//GEN-LAST:event_jButton9ActionPerformed
    //Search Methods
        //By Code
        public void CodeSearch(String code){
            int q;
            try {
                pst = con.prepareStatement("SELECT * FROM Employee WHERE Code = ?");
                pst.setString(1, code);
                rs = pst.executeQuery();  
                ResultSetMetaData rss = rs.getMetaData();
                q = rss.getColumnCount();

                DefaultTableModel df = (DefaultTableModel)jTable1.getModel(); 
                df.setRowCount(0);
                while(rs.next()){
                     Vector v2 = new Vector();
                     for(int i = 1; i <= q; i++){
                         v2.add(rs.getString("code"));
                         v2.add(rs.getString("lname"));
                         v2.add(rs.getString("fname"));
                         v2.add(rs.getString("position"));
                         v2.add(rs.getString("department"));
                         v2.add(rs.getString("type"));
                         v2.add(rs.getString("payrate"));
                         v2.add(rs.getString("salary"));
                     }
                     df.addRow(v2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //By Last Name
        public void lNameSearch(String lname){
            int q;
            try {
                pst = con.prepareStatement("SELECT * FROM Employee WHERE lname = ?");
                pst.setString(1, lname);
                rs = pst.executeQuery();  
                ResultSetMetaData rss = rs.getMetaData();
                q = rss.getColumnCount();

                DefaultTableModel df = (DefaultTableModel)jTable1.getModel(); 
                df.setRowCount(0);
                while(rs.next()){
                     Vector v2 = new Vector();
                     for(int i = 1; i <= q; i++){
                         v2.add(rs.getString("code"));
                         v2.add(rs.getString("lname"));
                         v2.add(rs.getString("fname"));
                         v2.add(rs.getString("position"));
                         v2.add(rs.getString("department"));
                         v2.add(rs.getString("type"));
                         v2.add(rs.getString("payrate"));
                         v2.add(rs.getString("salary"));
                     }
                     df.addRow(v2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //By First Name
        public void fNameSearch(String fname){
            int q;
            try {
                pst = con.prepareStatement("SELECT * FROM Employee WHERE fname = ?");
                pst.setString(1, fname);
                rs = pst.executeQuery();  
                ResultSetMetaData rss = rs.getMetaData();
                q = rss.getColumnCount();

                DefaultTableModel df = (DefaultTableModel)jTable1.getModel(); 
                df.setRowCount(0);
                while(rs.next()){
                     Vector v2 = new Vector();
                     for(int i = 1; i <= q; i++){
                         v2.add(rs.getString("code"));
                         v2.add(rs.getString("lname"));
                         v2.add(rs.getString("fname"));
                         v2.add(rs.getString("position"));
                         v2.add(rs.getString("department"));
                         v2.add(rs.getString("type"));
                         v2.add(rs.getString("payrate"));
                         v2.add(rs.getString("salary"));
                     }
                     df.addRow(v2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //By Position
        public void PositionSearch(String position){
            int q;
            try {
                pst = con.prepareStatement("SELECT * FROM Employee WHERE position = ?");
                pst.setString(1, position);
                rs = pst.executeQuery();  
                ResultSetMetaData rss = rs.getMetaData();
                q = rss.getColumnCount();

                DefaultTableModel df = (DefaultTableModel)jTable1.getModel(); 
                df.setRowCount(0);
                while(rs.next()){
                     Vector v2 = new Vector();
                     for(int i = 1; i <= q; i++){
                         v2.add(rs.getString("code"));
                         v2.add(rs.getString("lname"));
                         v2.add(rs.getString("fname"));
                         v2.add(rs.getString("position"));
                         v2.add(rs.getString("department"));
                         v2.add(rs.getString("type"));
                         v2.add(rs.getString("payrate"));
                         v2.add(rs.getString("salary"));
                     }
                     df.addRow(v2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //By Department
        public void DepartmentSearch(String department){
            int q;
            try {
                pst = con.prepareStatement("SELECT * FROM Employee WHERE Department = ?");
                pst.setString(1, department);
                rs = pst.executeQuery();  
                ResultSetMetaData rss = rs.getMetaData();
                q = rss.getColumnCount();

                DefaultTableModel df = (DefaultTableModel)jTable1.getModel(); 
                df.setRowCount(0);
                while(rs.next()){
                     Vector v2 = new Vector();
                     for(int i = 1; i <= q; i++){
                         v2.add(rs.getString("code"));
                         v2.add(rs.getString("lname"));
                         v2.add(rs.getString("fname"));
                         v2.add(rs.getString("position"));
                         v2.add(rs.getString("department"));
                         v2.add(rs.getString("type"));
                         v2.add(rs.getString("payrate"));
                         v2.add(rs.getString("salary"));
                     }
                     df.addRow(v2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //By Type
        public void TypeSearch(String type){
            int q;
            try {
                pst = con.prepareStatement("SELECT * FROM Employee WHERE type = ?");
                pst.setString(1, type);
                rs = pst.executeQuery();  
                ResultSetMetaData rss = rs.getMetaData();
                q = rss.getColumnCount();

                DefaultTableModel df = (DefaultTableModel)jTable1.getModel(); 
                df.setRowCount(0);
                while(rs.next()){
                     Vector v2 = new Vector();
                     for(int i = 1; i <= q; i++){
                         v2.add(rs.getString("code"));
                         v2.add(rs.getString("lname"));
                         v2.add(rs.getString("fname"));
                         v2.add(rs.getString("position"));
                         v2.add(rs.getString("department"));
                         v2.add(rs.getString("type"));
                         v2.add(rs.getString("payrate"));
                         v2.add(rs.getString("salary"));
                     }
                     df.addRow(v2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //By Pay Rate greater than or equal the Value
        public void GPRSearch(String pr){
            int q;
            try {
                pst = con.prepareStatement("SELECT * FROM Employee WHERE payrate >= ?");
                pst.setString(1, pr);
                rs = pst.executeQuery();  
                ResultSetMetaData rss = rs.getMetaData();
                q = rss.getColumnCount();

                DefaultTableModel df = (DefaultTableModel)jTable1.getModel(); 
                df.setRowCount(0);
                while(rs.next()){
                     Vector v2 = new Vector();
                     for(int i = 1; i <= q; i++){
                         v2.add(rs.getString("code"));
                         v2.add(rs.getString("lname"));
                         v2.add(rs.getString("fname"));
                         v2.add(rs.getString("position"));
                         v2.add(rs.getString("department"));
                         v2.add(rs.getString("type"));
                         v2.add(rs.getString("payrate"));
                         v2.add(rs.getString("salary"));
                     }
                     df.addRow(v2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        public void LPRSearch(String pr){
            int q;
            try {
                pst = con.prepareStatement("SELECT * FROM Employee WHERE payrate <= ?");
                pst.setString(1, pr);
                rs = pst.executeQuery();  
                ResultSetMetaData rss = rs.getMetaData();
                q = rss.getColumnCount();

                DefaultTableModel df = (DefaultTableModel)jTable1.getModel(); 
                df.setRowCount(0);
                while(rs.next()){
                     Vector v2 = new Vector();
                     for(int i = 1; i <= q; i++){
                         v2.add(rs.getString("code"));
                         v2.add(rs.getString("lname"));
                         v2.add(rs.getString("fname"));
                         v2.add(rs.getString("position"));
                         v2.add(rs.getString("department"));
                         v2.add(rs.getString("type"));
                         v2.add(rs.getString("payrate"));
                         v2.add(rs.getString("salary"));
                     }
                     df.addRow(v2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //By Salary
        public void GSSearch(String s){
            int q;
            try {
                pst = con.prepareStatement("SELECT * FROM Employee WHERE salary >= ?");
                pst.setString(1, s);
                rs = pst.executeQuery();  
                ResultSetMetaData rss = rs.getMetaData();
                q = rss.getColumnCount();

                DefaultTableModel df = (DefaultTableModel)jTable1.getModel(); 
                df.setRowCount(0);
                while(rs.next()){
                     Vector v2 = new Vector();
                     for(int i = 1; i <= q; i++){
                         v2.add(rs.getString("code"));
                         v2.add(rs.getString("lname"));
                         v2.add(rs.getString("fname"));
                         v2.add(rs.getString("position"));
                         v2.add(rs.getString("department"));
                         v2.add(rs.getString("type"));
                         v2.add(rs.getString("payrate"));
                         v2.add(rs.getString("salary"));
                     }
                     df.addRow(v2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        public void LSSearch(String s){
            int q;
            try {
                pst = con.prepareStatement("SELECT * FROM Employee WHERE salary <= ?");
                pst.setString(1, s);
                rs = pst.executeQuery();  
                ResultSetMetaData rss = rs.getMetaData();
                q = rss.getColumnCount();

                DefaultTableModel df = (DefaultTableModel)jTable1.getModel(); 
                df.setRowCount(0);
                while(rs.next()){
                     Vector v2 = new Vector();
                     for(int i = 1; i <= q; i++){
                         v2.add(rs.getString("code"));
                         v2.add(rs.getString("lname"));
                         v2.add(rs.getString("fname"));
                         v2.add(rs.getString("position"));
                         v2.add(rs.getString("department"));
                         v2.add(rs.getString("type"));
                         v2.add(rs.getString("payrate"));
                         v2.add(rs.getString("salary"));
                     }
                     df.addRow(v2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        SearchOptions search = new SearchOptions();
        //Open Window
        search.mainframe(this);
        search.setVisible(true);
    }//GEN-LAST:event_jButton12ActionPerformed
    //Sort Methods
        //By Code
        public void CodeSort(){
            int q;
            try {
                pst = con.prepareStatement("SELECT * FROM Employee ORDER BY code ASC");
                rs = pst.executeQuery();  
                ResultSetMetaData rss = rs.getMetaData();
                q = rss.getColumnCount();

                DefaultTableModel df = (DefaultTableModel)jTable1.getModel(); 
                df.setRowCount(0);
                while(rs.next()){
                     Vector v2 = new Vector();
                     for(int i = 1; i <= q; i++){
                         v2.add(rs.getString("code"));
                         v2.add(rs.getString("lname"));
                         v2.add(rs.getString("fname"));
                         v2.add(rs.getString("position"));
                         v2.add(rs.getString("department"));
                         v2.add(rs.getString("type"));
                         v2.add(rs.getString("payrate"));
                         v2.add(rs.getString("salary"));
                     }
                     df.addRow(v2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //By Last Name
        public void lNameSort(){
            int q;
            try {
                pst = con.prepareStatement("SELECT * FROM Employee ORDER BY lname ASC");
                rs = pst.executeQuery();  
                ResultSetMetaData rss = rs.getMetaData();
                q = rss.getColumnCount();

                DefaultTableModel df = (DefaultTableModel)jTable1.getModel(); 
                df.setRowCount(0);
                while(rs.next()){
                     Vector v2 = new Vector();
                     for(int i = 1; i <= q; i++){
                         v2.add(rs.getString("code"));
                         v2.add(rs.getString("lname"));
                         v2.add(rs.getString("fname"));
                         v2.add(rs.getString("position"));
                         v2.add(rs.getString("department"));
                         v2.add(rs.getString("type"));
                         v2.add(rs.getString("payrate"));
                         v2.add(rs.getString("salary"));
                     }
                     df.addRow(v2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //By First Name
        public void fNameSort(){
            int q;
            try {
                pst = con.prepareStatement("SELECT * FROM Employee ORDER BY fname ASC");
                rs = pst.executeQuery();  
                ResultSetMetaData rss = rs.getMetaData();
                q = rss.getColumnCount();

                DefaultTableModel df = (DefaultTableModel)jTable1.getModel(); 
                df.setRowCount(0);
                while(rs.next()){
                     Vector v2 = new Vector();
                     for(int i = 1; i <= q; i++){
                         v2.add(rs.getString("code"));
                         v2.add(rs.getString("lname"));
                         v2.add(rs.getString("fname"));
                         v2.add(rs.getString("position"));
                         v2.add(rs.getString("department"));
                         v2.add(rs.getString("type"));
                         v2.add(rs.getString("payrate"));
                         v2.add(rs.getString("salary"));
                     }
                     df.addRow(v2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //By Department
        public void DepartmentSort(){
            int q;
            try {
                pst = con.prepareStatement("SELECT * FROM Employee ORDER BY department ASC");
                rs = pst.executeQuery();  
                ResultSetMetaData rss = rs.getMetaData();
                q = rss.getColumnCount();

                DefaultTableModel df = (DefaultTableModel)jTable1.getModel(); 
                df.setRowCount(0);
                while(rs.next()){
                     Vector v2 = new Vector();
                     for(int i = 1; i <= q; i++){
                         v2.add(rs.getString("code"));
                         v2.add(rs.getString("lname"));
                         v2.add(rs.getString("fname"));
                         v2.add(rs.getString("position"));
                         v2.add(rs.getString("department"));
                         v2.add(rs.getString("type"));
                         v2.add(rs.getString("payrate"));
                         v2.add(rs.getString("salary"));
                     }
                     df.addRow(v2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //By Position
        public void PositionSort(){
            int q;
            try {
                pst = con.prepareStatement("SELECT * FROM Employee ORDER BY position ASC");
                rs = pst.executeQuery();  
                ResultSetMetaData rss = rs.getMetaData();
                q = rss.getColumnCount();

                DefaultTableModel df = (DefaultTableModel)jTable1.getModel(); 
                df.setRowCount(0);
                while(rs.next()){
                     Vector v2 = new Vector();
                     for(int i = 1; i <= q; i++){
                         v2.add(rs.getString("code"));
                         v2.add(rs.getString("lname"));
                         v2.add(rs.getString("fname"));
                         v2.add(rs.getString("position"));
                         v2.add(rs.getString("department"));
                         v2.add(rs.getString("type"));
                         v2.add(rs.getString("payrate"));
                         v2.add(rs.getString("salary"));
                     }
                     df.addRow(v2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //By Type
        public void TypeSort(){
            int q;
            try {
                pst = con.prepareStatement("SELECT * FROM Employee ORDER BY type ASC");
                rs = pst.executeQuery();  
                ResultSetMetaData rss = rs.getMetaData();
                q = rss.getColumnCount();

                DefaultTableModel df = (DefaultTableModel)jTable1.getModel(); 
                df.setRowCount(0);
                while(rs.next()){
                     Vector v2 = new Vector();
                     for(int i = 1; i <= q; i++){
                         v2.add(rs.getString("code"));
                         v2.add(rs.getString("lname"));
                         v2.add(rs.getString("fname"));
                         v2.add(rs.getString("position"));
                         v2.add(rs.getString("department"));
                         v2.add(rs.getString("type"));
                         v2.add(rs.getString("payrate"));
                         v2.add(rs.getString("salary"));
                     }
                     df.addRow(v2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //By Payrate
        public void AscPayRateSort(){
            int q;
            try {
                pst = con.prepareStatement("SELECT * FROM Employee ORDER BY payrate ASC");
                rs = pst.executeQuery();  
                ResultSetMetaData rss = rs.getMetaData();
                q = rss.getColumnCount();

                DefaultTableModel df = (DefaultTableModel)jTable1.getModel(); 
                df.setRowCount(0);
                while(rs.next()){
                     Vector v2 = new Vector();
                     for(int i = 1; i <= q; i++){
                         v2.add(rs.getString("code"));
                         v2.add(rs.getString("lname"));
                         v2.add(rs.getString("fname"));
                         v2.add(rs.getString("position"));
                         v2.add(rs.getString("department"));
                         v2.add(rs.getString("type"));
                         v2.add(rs.getString("payrate"));
                         v2.add(rs.getString("salary"));
                     }
                     df.addRow(v2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        public void DescPayRateSort(){
            int q;
            try {
                pst = con.prepareStatement("SELECT * FROM Employee ORDER BY payrate DESC");
                rs = pst.executeQuery();  
                ResultSetMetaData rss = rs.getMetaData();
                q = rss.getColumnCount();

                DefaultTableModel df = (DefaultTableModel)jTable1.getModel(); 
                df.setRowCount(0);
                while(rs.next()){
                     Vector v2 = new Vector();
                     for(int i = 1; i <= q; i++){
                         v2.add(rs.getString("code"));
                         v2.add(rs.getString("lname"));
                         v2.add(rs.getString("fname"));
                         v2.add(rs.getString("position"));
                         v2.add(rs.getString("department"));
                         v2.add(rs.getString("type"));
                         v2.add(rs.getString("payrate"));
                         v2.add(rs.getString("salary"));
                     }
                     df.addRow(v2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //By Salary
        public void AscSalarySort(){
            int q;
            try {
                pst = con.prepareStatement("SELECT * FROM Employee ORDER BY salary ASC");
                rs = pst.executeQuery();  
                ResultSetMetaData rss = rs.getMetaData();
                q = rss.getColumnCount();

                DefaultTableModel df = (DefaultTableModel)jTable1.getModel(); 
                df.setRowCount(0);
                while(rs.next()){
                     Vector v2 = new Vector();
                     for(int i = 1; i <= q; i++){
                         v2.add(rs.getString("code"));
                         v2.add(rs.getString("lname"));
                         v2.add(rs.getString("fname"));
                         v2.add(rs.getString("position"));
                         v2.add(rs.getString("department"));
                         v2.add(rs.getString("type"));
                         v2.add(rs.getString("payrate"));
                         v2.add(rs.getString("salary"));
                     }
                     df.addRow(v2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        public void DescSalarySort(){
            int q;
            try {
                pst = con.prepareStatement("SELECT * FROM Employee ORDER BY salary DESC");
                rs = pst.executeQuery();  
                ResultSetMetaData rss = rs.getMetaData();
                q = rss.getColumnCount();

                DefaultTableModel df = (DefaultTableModel)jTable1.getModel(); 
                df.setRowCount(0);
                while(rs.next()){
                     Vector v2 = new Vector();
                     for(int i = 1; i <= q; i++){
                         v2.add(rs.getString("code"));
                         v2.add(rs.getString("lname"));
                         v2.add(rs.getString("fname"));
                         v2.add(rs.getString("position"));
                         v2.add(rs.getString("department"));
                         v2.add(rs.getString("type"));
                         v2.add(rs.getString("payrate"));
                         v2.add(rs.getString("salary"));
                     }
                     df.addRow(v2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        SortOptions sort = new SortOptions();
        //Open Window
        sort.mainframe(this);
        sort.setVisible(true);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        int q;
        try {
            pst = con.prepareStatement("SELECT * FROM Payroll");
            rs = pst.executeQuery();  
            ResultSetMetaData rss = rs.getMetaData();
            q = rss.getColumnCount();
            
            //Create Payroll Table
            PayRollTable ptable = new PayRollTable();
            //Reference the Table
            DefaultTableModel df = (DefaultTableModel)ptable.jTable1.getModel(); 
            df.setRowCount(0);
            while(rs.next()){
                 Vector v2 = new Vector();
                 for(int i = 1; i <= q; i++){
                     v2.add(rs.getString("employee_code"));
                     v2.add(rs.getString("lname"));
                     v2.add(rs.getString("type"));
                     v2.add(rs.getString("employee_salary"));
                     v2.add(rs.getString("teachingHours"));
                     v2.add(rs.getString("payRate"));
                     v2.add(rs.getString("overPay"));
                     v2.add(rs.getString("overHours"));
                     v2.add(rs.getString("allowance"));
                     v2.add(rs.getString("absentHours"));
                     v2.add(rs.getString("deductions"));
                 }
                 df.addRow(v2);
            }
            ptable.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int choice = JOptionPane.showConfirmDialog(this, "Start New School Year?");
        if(choice == JOptionPane.YES_OPTION){
            try {
                //Employee Table Salary reset to 0
                pst = con.prepareStatement("UPDATE Employee SET Salary = 0 WHERE type = 'Teaching Employee'");
                pst.executeUpdate();
                //Payroll Table Delete every Teaching Employee Record
                pst = con.prepareStatement("DELETE FROM payroll WHERE type = 'Teaching Employee'"); 
                int k = pst.executeUpdate();
                //Confirm
                JOptionPane.showMessageDialog(this, k + " Records has been Deleted");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "An Error has Occured");
                Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Command Cancelled");
        }
        Fetch();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        //New Database
        //Check Error
        int k = 0;
        //Get DBName
        String dbname = JOptionPane.showInputDialog("Database Name: ");
        if(dbname == null){
            return;
        }
        try {
            //Create Database
            pst = con.prepareStatement("CREATE DATABASE" + " " + dbname);
            k += pst.executeUpdate();
           
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Name");
            Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        //Update Database URL
        db[0] = "jdbc:mysql://localhost/" + dbname;
        Connect();
        try {
        //Create Employee
            pst = con.prepareStatement("CREATE TABLE `employee` (`code` int(11) NOT NULL, `fname` varchar(150) NOT NULL, `lname` varchar(150) NOT NULL, `department` varchar(150) NOT NULL, `position` varchar(150) NOT NULL, `type` varchar(150) NOT NULL, `payrate` decimal(15,2) NOT NULL, `salary` decimal(15,2) DEFAULT 0.00, PRIMARY KEY (`code`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;");
            k += pst.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Employee Table Error");
            Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        try {
            //Create Payroll
            pst = con.prepareStatement("""
                                       CREATE TABLE `payroll` (
                                         `employee_code` int(11) NOT NULL,
                                         `lname` varchar(150) NOT NULL,
                                         `type` varchar(50) NOT NULL,
                                         `employee_salary` decimal(30,2) NOT NULL,
                                         `teachingHours` double NOT NULL,
                                         `payRate` decimal(15,2) NOT NULL,
                                         `overPay` double NOT NULL,
                                         `overHours` double NOT NULL,
                                         `allowance` double NOT NULL,
                                         `absentHours` double NOT NULL,
                                         `deductions` double NOT NULL
                                       ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;""");
            k += pst.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Payroll Table Error");
            Logger.getLogger(PMS.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        Fetch();
        JOptionPane.showMessageDialog(this, dbname + " has been created");
    }//GEN-LAST:event_jButton15ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PMS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PMS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PMS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PMS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PMS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AllEmployee;
    public javax.swing.JTextField DepartmentField;
    private javax.swing.JButton NTEmployeeButton;
    private javax.swing.JButton NonTeachingEmployee;
    public javax.swing.JTextField PositionField;
    public javax.swing.JButton TEmployeeButton;
    private javax.swing.JButton TeachingEmployee;
    public javax.swing.JLabel ahours;
    public javax.swing.JLabel allowance;
    public javax.swing.JLabel codee;
    public javax.swing.JLabel deductions;
    public javax.swing.JTextField fNameField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    public javax.swing.JTextField lNameField;
    public javax.swing.JLabel ohours;
    public javax.swing.JLabel opay;
    public javax.swing.JTextField payratee;
    public javax.swing.JLabel salary;
    public javax.swing.JLabel teachingtype;
    public javax.swing.JLabel thours;
    // End of variables declaration//GEN-END:variables
}
