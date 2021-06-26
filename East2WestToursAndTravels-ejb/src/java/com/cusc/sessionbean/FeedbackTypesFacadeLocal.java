/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.FeedbackTypes;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Admin
 */
@Local
public interface FeedbackTypesFacadeLocal {

    void create(FeedbackTypes feedbackTypes);

    void edit(FeedbackTypes feedbackTypes);

    void remove(FeedbackTypes feedbackTypes);

    FeedbackTypes find(Object id);

    List<FeedbackTypes> findAll();

    List<FeedbackTypes> findRange(int[] range);

    int count();
    
}
