/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.TourTypes;
import com.cusc.helps.NotificationTools;
import com.cusc.sessionbean.TourTypesFacadeLocal;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.Part;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *
 * @author Admin
 */
@Named(value = "tourTypeMB")
@SessionScoped
public class TourTypeMB implements Serializable {

    @EJB
    private TourTypesFacadeLocal tourTypesFacade;

    private static final String BEAN_OBJECT = "tour type";
    private Part excelFile;
    private TourTypes tourTypes;
    private String notice = "";
    private String message = "";
    private int editID = 0;
    
    
    public TourTypeMB() {
        tourTypes = new TourTypes();
    }
    
    public List<TourTypes> showAll() {
        return tourTypesFacade.findAll();
    }

    public void create() {
        try {
            TourTypes tt = new TourTypes();
            boolean isInvalid = false;
            if (tourTypes.getTypeName().length() < 2 || tourTypes.getTypeName().length() > 50) {
                notice += NotificationTools.error("The length of the name must be between 2 and 50 characters.");
                isInvalid = true;
            }
            if (isInvalid) {
                message = NotificationTools.openModal();
                return;
            }
            tt.setTypeName(tourTypes.getTypeName());
            tourTypesFacade.create(tt);
            resetForm();
            notice = NotificationTools.createSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
            notice = NotificationTools.createFail(BEAN_OBJECT);
        }
    }

    public void delete(TourTypes tt){
        try{
            tourTypesFacade.remove(tt);
            notice = NotificationTools.deleteSuccess(BEAN_OBJECT);
        }catch(Exception ex){
            notice = NotificationTools.deleteFail(BEAN_OBJECT);
        }
    }
    
    public void update() {
        try{
            TourTypes tt = tourTypesFacade.find(editID);
            boolean isInvalid = false;
            if (tourTypes.getTypeName().length() < 2 || tourTypes.getTypeName().length() > 50) {
                notice += NotificationTools.error("The length of the name must be between 2 and 50 characters.");
                isInvalid = true;
            }
            if (isInvalid) {
                message = NotificationTools.editModal(tt.getTypeId());
                return;
            }
            tt.setTypeName(tourTypes.getTypeName());
            tourTypesFacade.edit(tt);
            resetForm();
            notice = NotificationTools.updateSuccess(BEAN_OBJECT);
        }catch(Exception ex){
            notice = NotificationTools.updateFail(BEAN_OBJECT);
        }
    }
    
    public void resetForm(){
        tourTypes.setTypeName(null);
        editID = 0;
    }
    
    public void importData() {
        if (excelFile != null) {
            String type = excelFile.getContentType();
            if (type.equals("application/vnd.ms-excel")) {
                try {
                    InputStream input = excelFile.getInputStream();
                    POIFSFileSystem fs = new POIFSFileSystem(input);
                    HSSFWorkbook wb = new HSSFWorkbook(fs);
                    HSSFSheet sheet = wb.getSheetAt(0);
                    Iterator rows = sheet.rowIterator();
                    rows.next();
                    while (rows.hasNext()) {
                        TourTypes obj = new TourTypes();
                        HSSFRow row = (HSSFRow) rows.next();

                        Iterator cells = row.cellIterator();

                        while (cells.hasNext()) {
                            HSSFCell cell = (HSSFCell) cells.next();
                            int columnIndex = cell.getColumnIndex();

                            switch (columnIndex) {
                                case 0:
                                    obj.setTypeName(cell.getStringCellValue());
                                    break;
                            }
                        }
                        tourTypesFacade.create(obj);

                    }
                    setExcelFile(null);
                    notice = NotificationTools.success("The file has been imported successfully.");
                } catch (IOException e) {
                    notice = NotificationTools.error("The file has not imported. Try again.");
                } catch (Exception e) {
                    notice = NotificationTools.error("Data of the file is invalid. Try again!");
                }
            } else {
                notice = NotificationTools.error("The extension of the file is invalid. Extentions: .xls");
            }
        } else {
            notice = NotificationTools.error("Please select a excel file to imported to database.");
        }

    }

    public TourTypes getTourTypes() {
        return tourTypes;
    }

    public void setTourTypes(TourTypes tourTypes) {
        this.tourTypes = tourTypes;
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

    public Part getExcelFile() {
        return excelFile;
    }

    public void setExcelFile(Part excelFile) {
        this.excelFile = excelFile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
