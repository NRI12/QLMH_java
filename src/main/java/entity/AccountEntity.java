/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Dell
 */
@Entity
@Table(name = "Users")
public class AccountEntity {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public AccountEntity(String userName, String passWord, String accountType) {
        this.userName = userName;
        this.passWord = passWord;
        this.accountType = accountType;
    }

    public AccountEntity() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    @Column(name = "username")
    private String userName;
    @Column(name = "password")
    private String passWord;
    @Column(name = "permission")
    private String accountType;
}
