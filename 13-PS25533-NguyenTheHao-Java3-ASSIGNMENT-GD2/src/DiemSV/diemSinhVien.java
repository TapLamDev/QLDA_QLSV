package DiemSV;

/**
 *
 * @author haops25533
 */
public class diemSinhVien {
    private String maSV;
    private String hoTen;
    private double diemTA;
    private double diemTH;
    private double diemGDTC;
   

   
    
    public diemSinhVien() {
    }

    public diemSinhVien(String maSV, String hoTen, double diemTA, double diemTH, double diemGDTC) {
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.diemTA = diemTA;
        this.diemTH = diemTH;
        this.diemGDTC = diemGDTC;      
        
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public double getDiemTA() {
        return diemTA;
    }

    public void setDiemTA(double diemTA) {
        this.diemTA = diemTA;
    }

    public double getDiemTH() {
        return diemTH;
    }

    public void setDiemTH(double diemTH) {
        this.diemTH = diemTH;
    }

    public double getDiemGDTC() {
        return diemGDTC;
    }

    public void setDiemGDTC(double diemGDTC) {
        this.diemGDTC = diemGDTC;
    }

    public double getDiemTB() {
        return (this.diemTA+this.diemTH+this.diemGDTC)/3.0;
    }
 
}
