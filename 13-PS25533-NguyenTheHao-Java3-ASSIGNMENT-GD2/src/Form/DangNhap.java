package Form;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.text.html.HTML.Attribute.ID;

/**
 *
 * @author haops25533
 */
public class DangNhap extends javax.swing.JFrame {
    int current = 0;
    public boolean check = false;
    ArrayList<Account> listAc = new ArrayList<>();
    String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=ASM_GD2;user=sa;password=My27012003@;encrypt=true;trustServerCertificate=true";
    
    
    public DangNhap() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Login - ASM - PS25533");
   
    }
    
    public void LoadDataAccountsToArray() //doc tat ca du lieu trong table account vao arraylist
    {
        try (Connection con = DriverManager.getConnection(connectionUrl);Statement stmt = con.createStatement();) {
            String sql = "select * From Accounts";
            ResultSet rs = stmt.executeQuery(sql);
            listAc.clear();
            while (rs.next()) {
                String username = rs.getString(1);
                String pass = rs.getString(2);
                String vaitro = rs.getString(3);
                String mand = rs.getString(4);
                Account ac = new Account(username, pass, vaitro, mand);
                listAc.add(ac);
            }
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public boolean checkNull()
    {
        if (txtUserName.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên tài khoản!");
            txtUserName.requestFocus();
            txtUserName.setBackground(Color.YELLOW);
            return false;
        }
        if (txtPassword.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập password!");            
            txtPassword.setBackground(Color.YELLOW);
            return false;
        }
        return true;
    }

   public void checkLogin() 
   {
       if(checkNull())
       {
           if(rdoGV.isSelected())
           {
               try (Connection con = DriverManager.getConnection(connectionUrl);Statement stmt = con.createStatement();)
               {
                   String sql = "select * From Accounts";
                   ResultSet rs = stmt.executeQuery(sql);
                   while (rs.next()) 
                   {
                       String username = rs.getString("Username");
                       String pass = rs.getString("Pass");
                       boolean vaitro = rs.getBoolean("vaitro");
                       String mand = rs.getString(4);

                       if (username.equalsIgnoreCase(txtUserName.getText()) && pass.equalsIgnoreCase(txtPassword.getText()) && vaitro == true ){
                           JOptionPane.showMessageDialog(null, "Đăng nhập thành công!", "Thông báo!", JOptionPane.INFORMATION_MESSAGE);
                           DangNhapGV gv = new DangNhapGV();
                           gv.setVisible(true);
                           this.dispose();
                           return;
                       }
                   }
                   
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
           else if (rdoCB.isSelected()) {
               try ( Connection con = DriverManager.getConnection(connectionUrl);  Statement stmt = con.createStatement();) {
                   String sql = "select * From Accounts";
                   ResultSet rs = stmt.executeQuery(sql);
                   while (rs.next()) {
                       String username = rs.getString("Username");
                       String pass = rs.getString("Pass");
                       boolean vaitro = rs.getBoolean("Vaitro");
                       String mand = rs.getString(4);

                       if (username.equalsIgnoreCase(txtUserName.getText()) && pass.equalsIgnoreCase(txtPassword.getText()) && vaitro == false) {
                           JOptionPane.showMessageDialog(null, "Đăng nhập thành công!", "Thông báo!", JOptionPane.INFORMATION_MESSAGE);
                           DangNhapCB cb = new DangNhapCB();
                           cb.setVisible(true);
                           this.dispose();
                           return;
                       }
                   }

               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
           else
           {
               JOptionPane.showMessageDialog(null, "Tài khoản hoặc mật khẩu không đúng vui lòng nhập lại!", "Thông báo!", JOptionPane.INFORMATION_MESSAGE);            
               txtPassword.setText("");
               return;
           }
           
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtUserName = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        rdoGV = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        rdoCB = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "LOGIN", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 24), new java.awt.Color(255, 51, 51))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("User name:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Password:");

        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLogin.setText("LOGIN");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        btnLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnLoginKeyPressed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCancel.setText("CANCEL");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoGV);
        rdoGV.setText("Giảng Viên");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Role:");

        buttonGroup1.add(rdoCB);
        rdoCB.setText("Cán bộ đào tạo");
        rdoCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoCBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rdoCB)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(81, 81, 81))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                            .addComponent(txtUserName)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rdoGV)
                                .addGap(93, 93, 93)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoGV)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addComponent(rdoCB)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancel)
                        .addGap(26, 26, 26))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        checkLogin();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLoginKeyPressed
        checkLogin();
    }//GEN-LAST:event_btnLoginKeyPressed

    private void rdoCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoCBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoCBActionPerformed

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
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DangNhap().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnLogin;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton rdoCB;
    private javax.swing.JRadioButton rdoGV;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}
