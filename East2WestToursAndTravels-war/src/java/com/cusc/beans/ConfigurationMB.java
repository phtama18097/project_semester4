/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.Configuration;
import com.cusc.helps.CommonConstant;
import com.cusc.helps.ImageTools;
import com.cusc.helps.NotificationTools;
import com.cusc.sessionbean.ConfigurationFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.Part;

/**
 *
 * @author Admin
 */
@Named(value = "configurationMB")
@SessionScoped
public class ConfigurationMB implements Serializable {

    @EJB
    private ConfigurationFacadeLocal configurationFacade;

    private Part file;
    private static final String UPLOAD_DIRECTORY = "imgConfigurations";
    private static final String BEAN_OBJECT = "configuration";
    
    private Configuration configuration;
    private int editID = 0;
    private String notice = "";
    
    
    public ConfigurationMB() {
        configuration = new Configuration();
    }
    
    public List<Configuration> showAll(){
        return configurationFacade.findAll();
    }
    
    public void update() {
        try {
            Configuration c = configurationFacade.find(editID);

            if (file != null) {              
                String result = ImageTools.uploadFile(file, UPLOAD_DIRECTORY);
                switch (result) {
                    case "":
                        notice = NotificationTools.info(CommonConstant.IMAGE_IS_NULL_NOTICE);
                        return;
                    case CommonConstant.FILE_IO:
                        notice = NotificationTools.error(CommonConstant.UPLOADING_FAIL_NOTICE);
                        return;
                    case CommonConstant.FILE_EXTENSION:
                        notice = NotificationTools.error(CommonConstant.INVALID_IMAGE_EXTENSION_NOTICE);
                        return;
                    case CommonConstant.FILE_SIZE:
                        notice = NotificationTools.error(CommonConstant.INVALID_FILE_SIZE_NOTICE);
                        return;
                    default:
                        ImageTools.deleteFile(c.getFileLocation(), UPLOAD_DIRECTORY);
                        c.setFileLocation(result);
                        break;
                }
            }
            configurationFacade.edit(c);
            resetForm();
            notice = NotificationTools.updateSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
            notice = NotificationTools.updateFail(BEAN_OBJECT);
        }
    }
    
    public void resetForm(){
        configuration.setConfigName(null);
        configuration.setFileLocation(null);
        setFile(null);
        setEditID(0);
    }
    
    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public int getEditID() {
        return editID;
    }

    public void setEditID(int editID) {
        this.editID = editID;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
    
    
    
}
