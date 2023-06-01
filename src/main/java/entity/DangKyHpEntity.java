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
@Table(name="DangKyHp")
public class DangKyHpEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="mssv")
    private String mssv;

    @Column(name="ma_hp")
    private String maHp;

    @Column(name="ngay_dk")
    private Date ngayDk;

    public DangKyHpEntity() {}

    public DangKyHpEntity(String mssv, String maHp, Date ngayDk) {
        this.mssv = mssv;
        this.maHp = maHp;
        this.ngayDk = ngayDk;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getMaHp() {
        return maHp;
    }

    public void setMaHp(String maHp) {
        this.maHp = maHp;
    }

    public Date getNgayDk() {
        return ngayDk;
    }

    public void setNgayDk(Date ngayDk) {
        this.ngayDk = ngayDk;
    }
}

