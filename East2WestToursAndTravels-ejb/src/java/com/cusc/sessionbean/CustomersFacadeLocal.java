/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.Customers;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Admin
 */
@Local
public interface CustomersFacadeLocal {

    void create(Customers customers);

    void edit(Customers customers);

    void remove(Customers customers);

    Customers find(Object id);

    List<Customers> findAll();

    List<Customers> findRange(int[] range);

    int count();

    public Customers login(String username, String password);

    public boolean validateEmail(String email);

    public boolean validateUsername(String username);

    public boolean validateExistedUsername(String newUsername, String existedUsername);

    public boolean validateExistedEmail(String newEmail, String existedEmail);
    
}
