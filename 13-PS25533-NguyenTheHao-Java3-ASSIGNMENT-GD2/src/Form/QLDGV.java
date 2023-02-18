package Form;

import DiemSV.diemSinhVien;
import com.sun.jdi.connect.spi.Connection;
import com.sun.net.httpserver.Authenticator;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 *
 * @author haops25533
 */
public class QLDGV extends javax.swing.JFrame {
    DefaultTableModel tblModel;
    private ArrayList<diemSinhVien> list = new ArrayList<>();
    private int index = -1;
    int current = 0;
    String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=ASM_GD2;user=sa;password=My27012003@;encrypt=true;trustServerCertificate=true";
   
    
    public QLDGV() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Quan Ly Diem");
        txtHoTen.setEditable(false);
        
        initTable();
        load_data();
        
        
    }
    public boolean validateForm() {
        if (txtMaSVDiem.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập mã sinh viên");
            txtMaSVDiem.setBackground(Color.YELLOW);
            return false;
        } else {
            txtMaSV.setBackground(Color.WHITE);
        }
        for (diemSinhVien sv : list) {
            if (sv.getMaSV().equals(txtMaSV.getText())) {
                JOptionPane.showMessageDialog(this, "Mã nhân viên đã tồn tại!");
                return false;
            }
        }
       
        if (txtDiemTA.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập điểm Tiếng Anh");
            return false;
        }
        if (Double.parseDouble(txtDiemTA.getText()) < 0) {
            JOptionPane.showMessageDialog(this, "Điểm phải lớn hơn 0");
            return false;
        }
        if (Double.parseDouble(txtDiemTA.getText()) > 10) {
            JOptionPane.showMessageDialog(this, "Điểm phải nhỏ hơn hoặc bằng 10");
            return false;
        }
        try {
            Double.parseDouble(txtDiemTA.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Điểm phải là số", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (txtDiemTinHoc.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập điểm tin học");
            return false;
        }
        if (Double.parseDouble(txtDiemTinHoc.getText()) < 0) {
            JOptionPane.showMessageDialog(this, "Điểm phải lớn hơn 0");
            return false;
        }
        if (Double.parseDouble(txtDiemTinHoc.getText()) > 10) {
            JOptionPane.showMessageDialog(this, "Điểm phải nhỏ hơn hoặc bằng 10");
            return false;
        }
        try {
            Double.parseDouble(txtDiemTinHoc.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Điểm phải là số", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (txtDiemGDTC.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập điểm giáo dục thể chất");
            return false;
        }
        if (Double.parseDouble(txtDiemGDTC.getText()) < 0) {
            JOptionPane.showMessageDialog(this, "Điểm phải lớn hơn 0");
            return false;
        }
        if (Double.parseDouble(txtDiemGDTC.getText()) > 10) {
            JOptionPane.showMessageDialog(this, "Điểm phải nhỏ hơn hoặc bằng 10");
            return false;
        }
        try {
            Double.parseDouble(txtDiemGDTC.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Điểm phải là số", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    public void initTable() {
        tblModel = (DefaultTableModel) tblDiemSV.getModel();
        String[] nav = new String[]{"MÃ SV", "HỌ VÀ TÊN","Tin Học","Tiếng Anh","Giáo Dục TC", "Điểm TB"};
        tblModel.setColumnIdentifiers(nav);
    }
    //2. ham load data tu database len tbldiemSv
    public void load_data()
    {
        try(java.sql.Connection con = DriverManager.getConnection(connectionUrl);) {
            String sql = "SELECT SINHVIEN.MASV,TENSV,DIEMTH,DIEMTA,DIEMGDTC,(DIEMTH+DIEMTA+DIEMGDTC)/3.0 AS 'DIEM TB'"
                    + "FROM SINHVIEN,DIEM WHERE SINHVIEN.MASV = DIEM.MASV";          
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            list.clear();
            while(rs.next())
            {
                //tao 1 doi tuong
                diemSinhVien diem = new diemSinhVien();
                //gan du lieu vao
                diem.setMaSV(rs.getString("MASV"));
                diem.setHoTen(rs.getString("TENSV"));
                diem.setDiemTH(rs.getFloat("DIEMTH"));
                diem.setDiemTA(rs.getFloat("DIEMTA"));
                diem.setDiemGDTC(rs.getFloat("DIEMGDTC"));
                //them diem vao danh sach list
                list.add(diem);
            }
            //hien thi len bang diem
                //lay mo hinh du lieu va xoa sach cac bang
                DefaultTableModel tblModel = (DefaultTableModel) tblDiemSV.getModel();
                tblModel.setRowCount(0);
            //duyet qua danh sach list, lay tung dong them vao table
            for (diemSinhVien sv : list)
            {
                Object[] row = new Object[]{sv.getMaSV(),sv.getHoTen(),sv.getDiemTH(),sv.getDiemTA(),sv.getDiemGDTC(),sv.getDiemTB()};
                tblModel.addRow(row);
            }   
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveForm()
    {
        try (java.sql.Connection con = DriverManager.getConnection(connectionUrl);)
        {
            String sqlInsert = "INSERT INTO DIEM VALUES(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sqlInsert);
            ps.setString(1, txtMaSVDiem.getText());
            ps.setFloat(2, Float.parseFloat(txtDiemTinHoc.getText()));
            ps.setFloat(3, Float.parseFloat(txtDiemTA.getText()));
            ps.setFloat(4, Float.parseFloat(txtDiemGDTC.getText()));
            
            int kq = ps.executeUpdate();
            if(kq == 1 )
            {
                JOptionPane.showMessageDialog(this, "ADD SUCCESSFULLY");
            }
            ps.close();
            con.close();
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    public void delete()
    {
        try (java.sql.Connection con = DriverManager.getConnection(connectionUrl);)
        {
            String sqlInsert = "DELETE FROM DIEM WHERE MASV=?";
            PreparedStatement ps = con.prepareStatement(sqlInsert);
            
            ps.setString(1, txtMaSVDiem.getText());
            
            int kq = ps.executeUpdate();
            if(kq == 1)
            {
                JOptionPane.showMessageDialog(this, "DELETE SUCCESSFULLY");
            }else
            {
                JOptionPane.showMessageDialog(this, "NOT FOUND");
            }
            
            ps.close();
            con.close();
            
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    public void search()
    {
        try (java.sql.Connection con = DriverManager.getConnection(connectionUrl);)
        {
           String sql = "SELECT SINHVIEN.MASV,TENSV,DIEMTH,DIEMTA,DIEMGDTC,(DIEMTH+DIEMTA+DIEMGDTC)/3.0 AS 'DIEM TB'"
                    + "FROM SINHVIEN,DIEM WHERE SINHVIEN.MASV = DIEM.MASV AND SINHVIEN.MASV = ?";  
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, txtMaSV.getText());
            ResultSet rs = ps.executeQuery();
            list.clear();
            while(rs.next())
            {
                //tao 1 doi tuong
                diemSinhVien diem = new diemSinhVien();
                //gan du lieu vao
                diem.setMaSV(rs.getString("MASV"));
                diem.setHoTen(rs.getString("TENSV"));
                diem.setDiemTH(rs.getFloat("DIEMTH"));
                diem.setDiemTA(rs.getFloat("DIEMTA"));
                diem.setDiemGDTC(rs.getFloat("DIEMGDTC"));
                //them diem vao danh sach list
                list.add(diem);
            }
            //hien thi len bang diem
                //lay mo hinh du lieu va xoa sach cac bang
                DefaultTableModel tblModel = (DefaultTableModel) tblDiemSV.getModel();
                tblModel.setRowCount(0);
            //duyet qua danh sach list, lay tung dong them vao table
            for (diemSinhVien sv : list)
            {
                Object[] row = new Object[]{sv.getMaSV(),sv.getHoTen(),sv.getDiemTH(),sv.getDiemTA(),sv.getDiemGDTC(),sv.getDiemTB()};
                tblModel.addRow(row);
            }   
           
            
            
            ps.close();
            con.close();
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public void update()
    {
        try (java.sql.Connection con = DriverManager.getConnection(connectionUrl);)
        {
            String sql = "UPDATE DIEM SET DIEMTA =?,DIEMTH=?,DIEMGDTC=? WHERE MASV =?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setFloat(1, Float.parseFloat(txtDiemTA.getText()));
            ps.setFloat(2, Float.parseFloat(txtDiemTinHoc.getText()));
            ps.setFloat(3, Float.parseFloat(txtDiemGDTC.getText()));
            ps.setString(4, txtMaSVDiem.getText());
            
            int kq = ps.executeUpdate();
            if(kq == 1)
            {
                JOptionPane.showMessageDialog(this, "UPDATE SUCCESSFULLY");
            }
            else
            {
                JOptionPane.showMessageDialog(this, "UPDATE FAIL");
            }
            load_data();
            showTop3();
            con.close();
            ps.close();
      
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
   public void newForm() 
   {
        txtMaSV.setText("");
        txtHoTen.setText("");
        txtDiemTA.setText("");
        txtDiemTinHoc.setText("");
        txtDiemGDTC.setText("");
        lblDiem.setText("");
        txtMaSVDiem.setText("");
    }
    public void showTop3()
    {
        try (java.sql.Connection con = DriverManager.getConnection(connectionUrl);) 
        {
            String sql = "SELECT TOP 3 SINHVIEN.MASV,TENSV,DIEMTH,DIEMTA,DIEMGDTC,(DIEMTA+DIEMTH+DIEMGDTC)/3.0 AS 'DIEMTB'"
                    +"FROM SINHVIEN,DIEM WHERE SINHVIEN.MASV = DIEM.MASV";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            list.clear();
            while(rs.next())
            {
                //tao 1 doi tuong
                diemSinhVien diem = new diemSinhVien();
                //gan du lieu vao
                diem.setMaSV(rs.getString("MASV"));
                diem.setHoTen(rs.getString("TENSV"));
                diem.setDiemTH(rs.getFloat("DIEMTH"));
                diem.setDiemTA(rs.getFloat("DIEMTA"));
                diem.setDiemGDTC(rs.getFloat("DIEMGDTC"));
                //them diem vao danh sach list
                list.add(diem);
            }
            //hien thi len bang diem
                //lay mo hinh du lieu va xoa sach cac bang
                DefaultTableModel tblModel = (DefaultTableModel) tblDiemSV.getModel();
                tblModel.setRowCount(0);
            //duyet qua danh sach list, lay tung dong them vao table
            for (diemSinhVien sv : list)
            {
                Object[] row = new Object[]{sv.getMaSV(),sv.getHoTen(),sv.getDiemTH(),sv.getDiemTA(),sv.getDiemGDTC(),sv.getDiemTB()};
                tblModel.addRow(row);
            }

        } catch (Exception e) {
        }
    }
    
        public void showTable(int index) 
    {
        txtMaSV.setText(list.get(index).getMaSV());
        txtHoTen.setText(list.get(index).getHoTen());
        txtDiemTA.setText(String.valueOf(list.get(index).getDiemTA()));
        txtDiemTinHoc.setText(String.valueOf(list.get(index).getDiemTH()));
        txtDiemGDTC.setText(String.valueOf(list.get(index).getDiemGDTC()));
        lblDiem.setText(String.valueOf(list.get(index).getDiemTB()));
        txtMaSVDiem.setText(list.get(index).getMaSV());
    }
        
        public void first() 
    {
        index = 0;
        tblDiemSV.setRowSelectionInterval(index, index);
        showTable(index);
    }
     public void last() 
    {
        index = list.size() - 1;
        tblDiemSV.setRowSelectionInterval(index, index);
        showTable(index);
    }
    public void prev() {
        if (index == 0) {
            last();
        } else {
            index--;
        }
        tblDiemSV.setRowSelectionInterval(index, index);
        showTable(index);
    }
    public void next() {
        if (index == list.size() - 1) {
            first();
        } else {
            index++;
        }
        tblDiemSV.setRowSelectionInterval(index, index);
        showTable(index);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMaSV = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        pnl2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtDiemTA = new javax.swing.JTextField();
        txtMaSVDiem = new javax.swing.JTextField();
        txtDiemTinHoc = new javax.swing.JTextField();
        txtDiemGDTC = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        lblDiem = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDiemSV = new javax.swing.JTable();
        lblRecord = new javax.swing.JLabel();
        btnNew = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("QUẢN LÝ ĐIỂM");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TÌM KIẾM", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("MÃ SV:");

        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-search-32.png"))); // NOI18N
        btnSearch.setText("SEARCH");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSearch)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pnl2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel3.setText("Họ Tên:");

        jLabel4.setText("Mã SV:");

        jLabel5.setText("Tiếng Anh:");

        jLabel6.setText("Tin Học:");

        jLabel7.setText("GDTC:");

        txtDiemTA.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        txtMaSVDiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        txtDiemTinHoc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        txtDiemGDTC.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setText("Điểm TB");

        lblDiem.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblDiem.setText("0");

        txtHoTen.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        javax.swing.GroupLayout pnl2Layout = new javax.swing.GroupLayout(pnl2);
        pnl2.setLayout(pnl2Layout);
        pnl2Layout.setHorizontalGroup(
            pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDiemTA, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                    .addComponent(txtMaSVDiem)
                    .addComponent(txtDiemTinHoc)
                    .addComponent(txtDiemGDTC)
                    .addComponent(txtHoTen))
                .addGap(38, 38, 38)
                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl2Layout.setVerticalGroup(
            pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtMaSVDiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtDiemTA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDiem))
                .addGap(18, 18, 18)
                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtDiemTinHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtDiemGDTC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(83, Short.MAX_VALUE))
        );

        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/First-icon.png"))); // NOI18N
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Previous-icon.png"))); // NOI18N
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Next-icon.png"))); // NOI18N
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Last-icon.png"))); // NOI18N
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        tblDiemSV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblDiemSV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDiemSVMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDiemSV);

        lblRecord.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lblRecord.setText("3 sinh viên có điểm cao nhất");

        btnNew.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-new-32.png"))); // NOI18N
        btnNew.setText("New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Save-icon.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Delete-icon.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-update-32.png"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Exit.png"))); // NOI18N
        btnLogout.setText("Exit");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblRecord)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(95, 95, 95)
                                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(103, 103, 103)
                                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(99, 99, 99)
                                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel1)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(pnl2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnUpdate)
                            .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)))
                .addGap(17, 17, 17))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnLogout)
                        .addGap(22, 22, 22)
                        .addComponent(btnNew)
                        .addGap(18, 18, 18)
                        .addComponent(btnSave)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdate)
                        .addGap(11, 11, 11)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(lblRecord)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        search();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        newForm();
        load_data();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if(!txtMaSVDiem.getText().equals(""))
        {
            delete();
            load_data();
        }else
        {
            JOptionPane.showMessageDialog(this, "Nhập mã số sinh viên cần xóa");
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
        load_data();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
       if(!txtMaSVDiem.getText().equals(""))
        {
            update();
            
        }else
        {
            JOptionPane.showMessageDialog(this, "Nhập mã số sinh viên cần cập nhật");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        try {
            this.first();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Danh sách sinh viên không tồn tại");
        }
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        try {
            this.prev();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Danh sách sinh viên không tồn tại");
        }
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        try {
            this.next();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Danh sách sinh viên không tồn tại");
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        try {
            this.last();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Danh sách sinh viên không tồn tại");
        }
    }//GEN-LAST:event_btnLastActionPerformed

    private void tblDiemSVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDiemSVMouseClicked
         index = tblDiemSV.getSelectedRow();
         this.showTable(index);
    }//GEN-LAST:event_tblDiemSVMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
       
        
    }//GEN-LAST:event_formWindowOpened

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        DangNhapGV flg = new DangNhapGV();
        flg.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnLogoutActionPerformed

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
            java.util.logging.Logger.getLogger(QLDGV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLDGV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLDGV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLDGV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLDGV().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDiem;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JPanel pnl2;
    private javax.swing.JTable tblDiemSV;
    private javax.swing.JTextField txtDiemGDTC;
    private javax.swing.JTextField txtDiemTA;
    private javax.swing.JTextField txtDiemTinHoc;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaSV;
    private javax.swing.JTextField txtMaSVDiem;
    // End of variables declaration//GEN-END:variables
}
