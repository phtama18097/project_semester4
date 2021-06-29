/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.sessionbean.DestinationImagesFacadeLocal;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 * @author Admin
 */
@Named(value = "destinationImageMB")
@SessionScoped
public class DestinationImageMB implements Serializable {

    @EJB
    private DestinationImagesFacadeLocal destinationImagesFacade;
    
    private Part file;

    public DestinationImageMB() {
    }

    public static Collection<Part> getAllParts(Part part) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getParts().stream().filter(p -> part.getName().equals(p.getName())).collect(Collectors.toList());
    }

    public void submit() throws ServletException, IOException {
        for (Part part : getAllParts(file)) {
            String fileName = part.getSubmittedFileName();
            InputStream fileContent = part.getInputStream();
            System.out.println("file name: " + fileName);
        }
    }

    public Part getFile() {
        return null;
    }

    public void setFile(Part file) {
        this.file = file;
    }
}
