/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import entity.DangKyHpEntity;
import entity.HocPhanEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author pc
 */
public class HocPhanController {
    public void loadhocphantable(JTable x) {
        DefaultTableModel modelx = (DefaultTableModel) x.getModel();
        modelx.setRowCount(0); // Xóa dữ liệu cũ trong bảng
        List<HocPhanEntity> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "from HocPhanEntity";
            Query query = session.createQuery(hql);
            list = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        for (HocPhanEntity hp : list) {
            Object[] row = { hp.getMaHp(), hp.getTenHp(),hp.getTinChi(), hp.getTrongSo(), hp.getTenKhoa(), hp.getThoiLuong()};
            modelx.addRow(row); // Thêm dữ liệu mới vào bảng
        }
        System.out.println("Load  học phần từ phòng đào tạo thành công!");
    }
    public void loadtable_dki(JTable x) {
        DefaultTableModel modelx = (DefaultTableModel)x.getModel();
        modelx.setRowCount(0); // Xóa dữ liệu cũ trong bảng
        List<HocPhanEntity> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "from HocPhanEntity";
            Query query = session.createQuery(hql);
            list = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        for (HocPhanEntity hp : list) {
            Object[] row = { hp.getMaHp(), hp.getTenHp(),hp.getTinChi(), hp.getTrongSo(), hp.getTenKhoa(), hp.getThoiLuong(),false};
            modelx.addRow(row); 
        }
    }
    public void dangkihpsv(String userName,JTable dki_hocphantable) {
        Set<String> registeredHpIds = new HashSet<>();
        Session session1 = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = null;
        try {
            tx1 = session1.beginTransaction();
            Query query = session1.createQuery("FROM DangKyHpEntity WHERE mssv = :mssv");
            query.setParameter("mssv", userName);
            List<DangKyHpEntity> dangKyHpList = query.list();
            
            for (DangKyHpEntity dangKyHp : dangKyHpList) {
                registeredHpIds.add(dangKyHp.getMaHp());
            }
            tx1.commit();
        } catch (HibernateException e) {
            if (tx1 != null) tx1.rollback();
            e.printStackTrace();
        } finally {
            session1.close();
        }

        // Process selected courses
        List<HocPhanEntity> selectedHps = new ArrayList<>();
        Set<String> selectedHpIds = new HashSet<>();
        Set<String> duplicateHpIds = new HashSet<>();
        String duplicateCourses = "";
        int tongtinchi = 0;
        for (int i = 0; i < dki_hocphantable.getRowCount(); i++) {
            boolean selected = (boolean)dki_hocphantable.getValueAt(i, dki_hocphantable.getColumnCount() - 1);
            if (selected) {
                HocPhanEntity hp = new HocPhanEntity();
                hp.setMaHp((String)dki_hocphantable.getValueAt(i, 0));
                hp.setTenHp((String)dki_hocphantable.getValueAt(i, 1));
                hp.setTinChi((int)dki_hocphantable.getValueAt(i, 2));
                hp.setTrongSo((double)dki_hocphantable.getValueAt(i, 3));
                hp.setTenKhoa((String)dki_hocphantable.getValueAt(i, 4));
                hp.setThoiLuong((String)dki_hocphantable.getValueAt(i, 5));
                if (registeredHpIds.contains(hp.getMaHp())) {
                    // User has already registered for this course
                    JOptionPane.showMessageDialog(null, "Học phần bạn chọn đã tồn tại trong cơ sở dữ liệu");
                    return;
                } else {
                    // User has not registered for this course, add to list of selected courses
                    selectedHps.add(hp);
                    selectedHpIds.add(hp.getMaHp());
                    tongtinchi += hp.getTinChi();
                }
            }
        }
        if (tongtinchi < 15 || tongtinchi > 24 ) {
            JOptionPane.showMessageDialog(null, "Tổng số tín chỉ của các học phần đã chọn phải nằm trong khoảng từ 15 đến 24");
            return;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for (HocPhanEntity hp : selectedHps) {
                // Tạo đối tượng DangKyHpEntity mới và thiết lập các giá trị cho các thuộc tính của nó
                DangKyHpEntity dangKyHp = new DangKyHpEntity();
                dangKyHp.setMssv(userName);
                dangKyHp.setNgayDk(new Date());
                // Set the selected course for this registration
                dangKyHp.setMaHp(hp.getMaHp());
                // Save the registration to the database
                session.save(dangKyHp);
            }
            tx.commit();
            JOptionPane.showMessageDialog(null, "Đăng kí thành công");
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void getlisthp_dki(String userName,JTable dki_hocphantable1) {
        DefaultTableModel modelx = (DefaultTableModel)dki_hocphantable1.getModel();
        modelx.setRowCount(0); // Xóa dữ liệu cũ trong bảng
        List<HocPhanEntity> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "SELECT hp FROM HocPhanEntity hp, DangKyHpEntity dk WHERE hp.maHp = dk.maHp AND dk.mssv = :mssv";
            Query query = session.createQuery(hql);
            query.setParameter("mssv", userName);
            list = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        for (HocPhanEntity hp : list) {
            Object[] row = { hp.getMaHp(), hp.getTenHp(),hp.getTinChi(), hp.getTrongSo(), hp.getTenKhoa(), hp.getThoiLuong(),false};
            modelx.addRow(row); 
        }
    }
    public void xoaallhp_sv(String userName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("FROM DangKyHpEntity WHERE mssv = :mssv");
            query.setParameter("mssv", userName);
            List<DangKyHpEntity> danhSachDangKy = query.list();
            for (DangKyHpEntity dangKy : danhSachDangKy) {
                session.delete(dangKy);
            }
            transaction.commit();
            JOptionPane.showMessageDialog(null, "Xóa thành công");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
   
}
