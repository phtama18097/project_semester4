/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.Towns;
import com.cusc.helps.NotificationTools;
import com.cusc.sessionbean.TownsFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Admin
 */
@Named(value = "townMB")
@SessionScoped
public class TownMB implements Serializable {

    @EJB
    private TownsFacadeLocal townsFacade;

    private static final String BEAN_OBJECT = "town";
    private Towns towns;
    private String notice = "";
    private String message = "";
    private int editID = 0;
    
    public TownMB() {
        towns = new Towns();
    }
    
    public List<Towns> showAll() {
        return townsFacade.findAll();
    }

    public void create() {
        try {
            Towns t = new Towns();
            boolean isInvalid = false;
            if (towns.getTownName().length() < 2 || towns.getTownName().length() > 20) {
                notice += NotificationTools.error("The length of the name must be between 2 and 20 characters.");
                isInvalid = true;
            }
            if (isInvalid) {
                message = NotificationTools.openModal();
                return;
            }
            t.setTownName(towns.getTownName());
            townsFacade.create(t);
            resetForm();
            notice = NotificationTools.createSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
            notice = NotificationTools.createFail(BEAN_OBJECT);
        }
    }

    public void delete(Towns t){
        try{
            townsFacade.remove(t);
            notice = NotificationTools.deleteSuccess(BEAN_OBJECT);
        }catch(Exception ex){
            notice = NotificationTools.deleteFail(BEAN_OBJECT);
        }
    }
    
    public void update() {
        try{
            Towns tt = townsFacade.find(editID);
            boolean isInvalid = false;
            if (towns.getTownName().length() < 2 || towns.getTownName().length() > 20) {
                notice += NotificationTools.error("The length of the name must be between 2 and 20 characters.");
                isInvalid = true;
            }
            if (isInvalid) {
                message = NotificationTools.editModal(tt.getTownId());
                return;
            }
            tt.setTownName(towns.getTownName());
            townsFacade.edit(tt);
            resetForm();
            notice = NotificationTools.updateSuccess(BEAN_OBJECT);
        }catch(Exception ex){
            notice = NotificationTools.updateFail(BEAN_OBJECT);
        }
    }
    
    public void resetForm(){
        towns.setTownName(null);
        editID = 0;
    }

    public Towns getTowns() {
        return towns;
    }

    public void setTowns(Towns towns) {
        this.towns = towns;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public int getEditID() {
        return editID;
    }

    public void setEditID(int editID) {
        this.editID = editID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
