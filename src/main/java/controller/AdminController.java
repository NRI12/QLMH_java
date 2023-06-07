/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.microsoft.sqlserver.jdbc.StringUtils;
import entity.AccountEntity;
import entity.HocPhanEntity;
import entity.SinhVienEntity;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class AdminController {
    private String maHp;

    public void loadtable(JTable modeltable) {
        DefaultTableModel modelx = (DefaultTableModel) modeltable.getModel();
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
        System.out.println("Load table hocphan successfully!");
    }
    
    
    ///////////
    public void addhp(String mahocphan,String tenhocphan,String sotinchistr,String trongsostr,String vienquanly,String thoiluong,JTable modeltable) {
        if (StringUtils.isEmpty(mahocphan) || StringUtils.isEmpty(tenhocphan) || StringUtils.isEmpty(sotinchistr) || StringUtils.isEmpty(trongsostr) || StringUtils.isEmpty(vienquanly) || StringUtils.isEmpty(thoiluong)) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin học phần!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        int sotinchi = 0;
        double trongso = 0;
        if (!StringUtils.isNumeric(sotinchistr) || !sotinchistr.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng tín chỉ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            sotinchi = Integer.parseInt(sotinchistr);
            if (sotinchi < 1 || sotinchi > 3) {
                JOptionPane.showMessageDialog(null, "Số tín chỉ phải trong khoảng từ 1 đến 3!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        if (!StringUtils.isNumeric(trongsostr)) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng trọng số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            trongso = Double.parseDouble(trongsostr);
            if (trongso < 0 || trongso > 1) {
                JOptionPane.showMessageDialog(null, "Trọng số phải trong khoảng từ 0 đến 1!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        Session session1 = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = null;
        try {
            tx1 = session1.beginTransaction();
            Query query = session1.createQuery("FROM HocPhanEntity WHERE ma_hp = :maHp OR ten_hp = :tenHp");
            query.setParameter("maHp", mahocphan);
            query.setParameter("tenHp", tenhocphan);
            List<HocPhanEntity> results = query.list();
            if (results != null && !results.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Mã hoặc tên học phần đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (HibernateException e) {
            JOptionPane.showMessageDialog(null, "Không thể truy vấn cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            session1.close();
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            HocPhanEntity hocphan = new HocPhanEntity(mahocphan, tenhocphan, thoiluong, sotinchi, trongso, vienquanly);
            session.save(hocphan);
            tx.commit();
            loadtable(modeltable);
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            JOptionPane.showMessageDialog(null, "Không thể lưu học phần vào cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void xoahp(JTable modeltable) {
        int selectedRow = modeltable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một dòng để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maHp = modeltable.getModel().getValueAt(selectedRow, 0).toString();
        System.out.println("Mã HP được chọn để xóa: " + maHp);

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            // Thực hiện truy vấn
            Query query = session.createQuery("SELECT hp FROM HocPhanEntity hp WHERE hp.maHp = :maHp");
            query.setParameter("maHp", maHp);
            HocPhanEntity hocPhan = (HocPhanEntity) query.uniqueResult();

            // Xóa dòng tương ứng với học phần
            session.delete(hocPhan);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        loadtable(modeltable);
    }
  
    public void savehp(String mahocphan,String tenhocphan,String sotinchistr,String trongsostr,String vienquanly,String thoiluong) {
 
    }
    public  void loadtableUser(JTable tableUser) {
        DefaultTableModel modelx = (DefaultTableModel) tableUser.getModel();
        modelx.setRowCount(0); // Xóa dữ liệu cũ trong bảng
        List<AccountEntity> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "from AccountEntity";
            Query query = session.createQuery(hql);
            list = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        for (AccountEntity acc : list) {
            Object[] row = {acc.getId(),acc.getUserName(), acc.getPassWord(),acc.getAccountType()};
            modelx.addRow(row); // Thêm dữ liệu mới vào bảng
        }
        System.out.println("Load table Users successfully!");
    }
     public static String getHash(String data, String hash_type){
        if(data == null) return null;
        StringBuilder sb = new StringBuilder();
        try{
            MessageDigest md = MessageDigest.getInstance(hash_type);
            md.update(data.getBytes());
            byte byteData[] = md.digest();

            for (int i = 0; i < byteData.length; i++) {
             sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return sb.toString();
    }
    public void getlistsv(JTable tablesinhvien) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DefaultTableModel modelx = (DefaultTableModel) tablesinhvien.getModel();
        modelx.setRowCount(0); // Xóa dữ liệu cũ trong bảng
        List<SinhVienEntity> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "from SinhVienEntity";
            Query query = session.createQuery(hql);
            list = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        for (SinhVienEntity sv : list) {
            Object[] row = {sv.getId(), sv.getMssv(), sv.getHoVaTen(), dateFormat.format(sv.getNgaySinh()),sv.getDiachi(), sv.getLop(), sv.getHeHoc(), sv.getTrangThai(), sv.getSodienthoai(),sv.getEmail()};
            modelx.addRow(row); // Thêm dữ liệu mới vào bảng
        }

        System.out.println("Load table sinh_vien successfully!");
    }
    public void xoasv(JTable tablesinhvien) {
        DefaultTableModel modelx = (DefaultTableModel) tablesinhvien.getModel();
        int selectedRow = tablesinhvien.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }
        List<SinhVienEntity> sinhvienList = new ArrayList<>();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from SinhVienEntity");
        List<SinhVienEntity> results = query.list();
        sinhvienList.addAll(results);
        session.close();
        SinhVienEntity sv = sinhvienList.get(selectedRow);

        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa sinh viên này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Session session1 = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            try {
                tx = session1.beginTransaction();
                session1.delete(sv);
                tx.commit();
            } catch (HibernateException ex) {
                if (tx != null) {
                    tx.rollback();
                }
                JOptionPane.showMessageDialog(null, "Lỗi khi xóa sinh viên: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            getlistsv(tablesinhvien);
        }

    }
    public void addSinhVien(String mssv, String hoVaTen,String ngaySinhStr,String lop, String heHoc, String trangThai, String email) {
        // Kiểm tra các trường nhập liệu
        if (mssv.equals("") || hoVaTen.equals("") || ngaySinhStr == null || lop.equals("") || heHoc.equals("") || trangThai.equals("") || email.equals("")) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin sinh viên!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!mssv.matches("\\d{8}")) {
            JOptionPane.showMessageDialog(null, "Mã số sinh viên chỉ bao gồm số có 8 chữ số", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Date ngaySinh = null;
        try {
            ngaySinh = new SimpleDateFormat("dd/MM/yyyy").parse(ngaySinhStr);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng ngày tháng năm", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        SinhVienEntity sinhVienEntity = null;
        System.out.println("MSSV: " + mssv);
        org.hibernate.Query query = session.createQuery("SELECT COUNT(*) FROM SinhVienEntity WHERE mssv = :mssv");
        query.setParameter("mssv", mssv);
        Long count = (Long) query.uniqueResult();
        if (count > 0) {
            JOptionPane.showMessageDialog(null, "Mã số sinh viên đã tồn tại trong cơ sở dữ liệu!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else {
            // Tạo đối tượng SinhVienEntity với các thông tin lấy từ form
            sinhVienEntity = new SinhVienEntity();
            sinhVienEntity.setMssv(mssv);
            sinhVienEntity.setHoVaTen(hoVaTen);
            sinhVienEntity.setNgaySinh(ngaySinh);
            sinhVienEntity.setLop(lop);
            sinhVienEntity.setHeHoc(heHoc);
            sinhVienEntity.setTrangThai(trangThai);
            sinhVienEntity.setEmail(email);
            try {
                tx = session.beginTransaction();
                session.save(sinhVienEntity);
              
                JOptionPane.showMessageDialog(null, "Thêm sinh viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (HibernateException he) {
                if (tx != null) {
                    tx.rollback();
                }
                JOptionPane.showMessageDialog(null, "Thêm sinh viên thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                he.printStackTrace();
            } finally {
                session.close();
            }
            Session session1 = HibernateUtil.getSessionFactory().openSession();
            AccountEntity user = new AccountEntity();
            user.setUserName(mssv);
            user.setPassWord(getHash(getHash(mssv, "MD5"), "SHA-256"));
            user.setAccountType("s");
          
            Transaction tx1 = null;
            try {
                  tx1 = session1.beginTransaction();
                  // Tạo tài khoản cho sinh viên
                  session1.save(user);
                  tx1.commit();
                  JOptionPane.showMessageDialog(null, "Thêm tài khoản sinh viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            } catch (HibernateException he) {
                  if (tx1 != null) {
                      tx1.rollback();
                  }
                  he.printStackTrace();
            } finally {
                  session1.close();
            }
        }
    } 
    

}
