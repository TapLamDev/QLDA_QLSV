package SV;

import javax.swing.ImageIcon;

/**
 *
 * @author haops25533
 */
public class sinhvien 
{
     String MaSV;
     String Hoten;
     String Email;
     String soDT;
     String DiaChi;
     
     boolean gioiTinh;
     ImageIcon img;

    public sinhvien() {
    }

    public sinhvien(String MaSV, String Hoten, String Email, String soDT,boolean gioiTinh,String DiaChi) {
        this.MaSV = MaSV;
        this.Hoten = Hoten;
        this.Email = Email;
        this.soDT = soDT;
        this.DiaChi = DiaChi;   
        this.gioiTinh = gioiTinh;
        
    }

    public String getMaSV() {
        return MaSV;
    }

    public void setMaSV(String MaSV) {
        this.MaSV = MaSV;
    }

    public String getHoten() {
        return Hoten;
    }

    public void setHoten(String Hoten) {
        this.Hoten = Hoten;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public ImageIcon getImg() {
        return img;
    }

    public void setImg(ImageIcon img) {
        this.img = img;
    }

    
     
    

}