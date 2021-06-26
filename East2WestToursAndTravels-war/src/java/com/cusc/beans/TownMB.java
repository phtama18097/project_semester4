/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.Towns;
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

    private Towns towns;
    private String notice = "";
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
            t.setTownName(towns.getTownName());
            townsFacade.create(t);
            resetForm();
            notice = "toastr.success(\"New town has been added successfully!\");";
        } catch (Exception ex) {
            notice = "toastr.error(\"New town has not added. Try again\");";
        }
    }

    public void delete(Towns t){
        try{
            townsFacade.remove(t);
            notice = "toastr.success(\"The tour type has been deleted successfully!\");";
        }catch(Exception ex){
            notice = "toastr.error(\"The tour type has a constraint. You cannot delete it.\");";
        }
    }
    
    public void update() {
        try{
            Towns tt = townsFacade.find(editID);
            tt.setTownName(towns.getTownName());
            townsFacade.edit(tt);
            resetForm();
            notice = "toastr.success(\"The town has been updated successfully!\");";
        }catch(Exception ex){
            notice = "toastr.error(\"The town has not updated. Try again.\");";
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
    
}
