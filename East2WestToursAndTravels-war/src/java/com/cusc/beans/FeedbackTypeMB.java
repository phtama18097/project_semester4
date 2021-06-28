/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.FeedbackTypes;
import com.cusc.sessionbean.FeedbackTypesFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Admin
 */
@Named(value = "feedbackTypeMB")
@SessionScoped
public class FeedbackTypeMB implements Serializable {

    @EJB
    private FeedbackTypesFacadeLocal feedbackTypesFacade;

    private FeedbackTypes feedbacktype;
    private String notice = "";
    private int editID = 0;

    /**
     * Creates a new instance of FeedbackTypeMB
     */
    public FeedbackTypeMB() {
        feedbacktype = new FeedbackTypes();
    }

    public List<FeedbackTypes> showAll() {
        return feedbackTypesFacade.findAll();
    }

    public void create() {
        try {
            FeedbackTypes ft = new FeedbackTypes();
            ft.setTypeName(feedbacktype.getTypeName());
            feedbackTypesFacade.create(ft);
            resetForm();
            notice = "toastr.success(\"New feedback type has been added successfully!\");";
        } catch (Exception ex) {
            notice = "toastr.error(\"New feedback type has not added. Try again\");";
        }
    }
    public void delete(FeedbackTypes fbt){
        try{
            feedbackTypesFacade.remove(fbt);
            notice = "toastr.success(\"The feedback type has been deleted successfully!\");";
        }catch(Exception ex){
            notice = "toastr.error(\"The feedback type has a constraint. You cannot delete it.\");";
        }
    }
    
    public void update() {
        try{
            FeedbackTypes fbt = feedbackTypesFacade.find(editID);
            fbt.setTypeName(feedbacktype.getTypeName());
            feedbackTypesFacade.edit(fbt);
            resetForm();
            notice = "toastr.success(\"The feedback type has been updated successfully!\");";
        }catch(Exception ex){
            notice = "toastr.error(\"The feedback type has not updated. Try again.\");";
        }
    }

    public void resetForm() {
        feedbacktype.setTypeName(null);
        setEditID(0);
    }
    
    public FeedbackTypes getFeedbacktype() {
        return feedbacktype;
    }

    public void setFeedbacktype(FeedbackTypes feedbacktype) {
        this.feedbacktype = feedbacktype;
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
