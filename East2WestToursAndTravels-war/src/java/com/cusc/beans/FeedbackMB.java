/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.Feedbacks;
import com.cusc.sessionbean.FeedbacksFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Admin
 */
@Named(value = "feedbackMB")
@SessionScoped
public class FeedbackMB implements Serializable {

    @EJB
    private FeedbacksFacadeLocal feedbacksFacade;

    private String notice = "";
    
    public FeedbackMB() {
    }

    public List<Feedbacks> showAll(){
        return feedbacksFacade.findAll();
    }
    
    public void delete(Feedbacks fb){
        try{
            feedbacksFacade.remove(fb);
            notice = "toastr.success(\"The feedback has been deleted successfully!\");";
        }catch(Exception ex){
            notice = "toastr.error(\"The feedback has a constraint. You cannot delete it.\");";
        }
    }
    
    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
    
}
