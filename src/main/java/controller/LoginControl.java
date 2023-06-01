/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import view.LoginForm;
import entity.LoginEntity;
import java.util.List;
import util.HibernateUtil;
import org.hibernate.CacheMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import view.LoginForm;
import view.UserType;

/**
 *
 * @author Dell
 */


public class LoginControl {
    private final LoginForm loginUI;
    private final LoginEntity loginEntity;
    private String userName;
    private String passWord;
    
    public void setLoginParameter(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public LoginControl(LoginForm loginUI) {
        loginEntity = new LoginEntity();
        this.loginUI = loginUI;
    }

    public UserType check_login() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
         
            SQLQuery query;
            query = (SQLQuery) session.createSQLQuery("EXEC [dbo].checkLogin :userName, :passWord")
                    .addScalar("retVal", IntegerType.INSTANCE)
                    .setParameter("userName", userName)
                    .setParameter("passWord", passWord)
                    .setResultTransformer(Transformers.aliasToBean(LoginEntity.class))
                    .setCacheMode(CacheMode.GET);
                    

            tx.commit();
            List result = query.list();
            int retVal = ((LoginEntity)result.get(0)).getRetVal();
            return UserType.getType(retVal);    
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
            he.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
}
