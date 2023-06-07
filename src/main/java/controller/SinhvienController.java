/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import entity.AccountEntity;
import entity.HocPhanEntity;
import entity.SinhVienEntity;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import org.hibernate.Query;
/**
 *
 * @author pc
 */
public class SinhvienController {
    public void timKiemSinhVienTheoTuKhoa(String tuKhoa, JTable tablesinhvien) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DefaultTableModel modelx = (DefaultTableModel) tablesinhvien.getModel();
        modelx.setRowCount(0); // Xóa dữ liệu cũ trong bảng
        List<HocPhanEntity> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            String hqlQuery = "FROM HocPhanEntity WHERE hoVaTen LIKE :keyword OR mssv LIKE :keyword";
            Query query = session.createQuery(hqlQuery);
            query.setParameter("keyword", "%" + tuKhoa + "%");
            List<SinhVienEntity> resultList = query.list();
            for (HocPhanEntity hp : list) {
            Object[] row = { hp.getMaHp(), hp.getTenHp(),hp.getTinChi(), hp.getTrongSo(), hp.getTenKhoa(), hp.getThoiLuong()};
            modelx.addRow(row); // Thêm dữ liệu mới vào bảng
            }
        } finally {
            session.close();
        }
    }
}
