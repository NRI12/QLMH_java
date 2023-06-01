/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "SinhVienx")
public class SinhVienEntity {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "mssv")
    private String mssv;

    @Column(name = "HoVaTen")
    private String hoVaTen;

    @Column(name = "NgaySinh")
    private Date ngaySinh;
    @Column(name = "ma_lop")
    private String lop;

    @Column(name = "HeHoc")
    private String heHoc;

    @Column(name = "TrangThai")
    private String trangThai;

    @Column(name = "Email")
    private String email;

    public String getSodienthoai() {
        return Sodienthoai;
    }

    public void setSodienthoai(String Sodienthoai) {
        this.Sodienthoai = Sodienthoai;
    }
    
    @Column(name = "Diachi")
    private String diachi;
    @Column(name = "Sodienthoai")
    private String Sodienthoai;

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getDiachi() {
        return diachi;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public void setHeHoc(String heHoc) {
        this.heHoc = heHoc;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getMssv() {
        return mssv;
    }

    public String getHoVaTen() {
        return hoVaTen;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public String getLop() {
        return lop;
    }

    public String getHeHoc() {
        return heHoc;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public String getEmail() {
        return email;
    }

}
