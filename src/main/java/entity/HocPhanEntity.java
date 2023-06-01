/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "HocPhan")
public class HocPhanEntity {   
    @Id
    @Column(name = "ma_hp")
    private String maHp;

    @Column(name = "ten_hp")
    private String tenHp;

    @Column(name = "thoiluong")
    private String thoiLuong;

    @Column(name = "tinchi")
    private int tinChi;

    @Column(name = "trong_so")
    private double trongSo;

    @Column(name = "ma_khoa")
    private String tenKhoa;
    public HocPhanEntity(String maHp, String tenHp, String thoiLuong, int tinChi, double trongSo, String tenKhoa) {
        this.maHp = maHp;
        this.tenHp = tenHp;
        this.thoiLuong = thoiLuong;
        this.tinChi = tinChi;
        this.trongSo = trongSo;
        this.tenKhoa = tenKhoa;
    }
    public HocPhanEntity() {
    }
    public String getMaHp() {
        return maHp;
    }

    public String getTenHp() {
        return tenHp;
    }

    public String getThoiLuong() {
        return thoiLuong;
    }

    public int getTinChi() {
        return tinChi;
    }

    public double getTrongSo() {
        return trongSo;
    }

    public String getTenKhoa() {
        return tenKhoa;
    }

    public void setMaHp(String maHp) {
        this.maHp = maHp;
    }

    public void setTenHp(String tenHp) {
        this.tenHp = tenHp;
    }

    public void setThoiLuong(String thoiLuong) {
        this.thoiLuong = thoiLuong;
    }

    public void setTinChi(int tinChi) {
        this.tinChi = tinChi;
    }

    public void setTrongSo(double trongSo) {
        this.trongSo = trongSo;
    }

    public void setTenKhoa(String tenKhoa) {
        this.tenKhoa = tenKhoa;
    }

}
