/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.FeedbackTypes;
import com.cusc.helps.NotificationTools;
import com.cusc.sessionbean.FeedbackTypesFacadeLocal;
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
@Named(value = "feedbackTypeMB")
@SessionScoped
public class FeedbackTypeMB implements Serializable {

    @EJB
    private FeedbackTypesFacadeLocal feedbackTypesFacade;

    private static final String BEAN_OBJECT = "feedback type";
    private Part excelFile;
    private FeedbackTypes feedbacktype;
    private String notice = "";
    private String message = "";
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
            boolean isInvalid = false;
            if (feedbacktype.getTypeName().length() < 2 || feedbacktype.getTypeName().length() > 50) {
                notice += NotificationTools.error("The length of the name must be between 2 and 50 characters.");
                isInvalid = true;
            }
            if (isInvalid) {
                message = NotificationTools.openModal();
                return;
            }
            ft.setTypeName(feedbacktype.getTypeName());
            feedbackTypesFacade.create(ft);
            resetForm();
            notice = NotificationTools.createSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
            notice = NotificationTools.createFail(BEAN_OBJECT);
        }
    }
    public void delete(FeedbackTypes fbt){
        try{
            feedbackTypesFacade.remove(fbt);
            notice = NotificationTools.deleteSuccess(BEAN_OBJECT);
        }catch(Exception ex){
            notice = NotificationTools.deleteFail(BEAN_OBJECT);
        }
    }
    
    public void update() {
        try{
            FeedbackTypes fbt = feedbackTypesFacade.find(editID);
            boolean isInvalid = false;
            if (feedbacktype.getTypeName().length() < 2 || feedbacktype.getTypeName().length() > 50) {
                notice += NotificationTools.error("The length of the name must be between 2 and 50 characters.");
                isInvalid = true;
            }
            if (isInvalid) {
                message = NotificationTools.editModal(fbt.getTypeId());
                return;
            }
            fbt.setTypeName(feedbacktype.getTypeName());
            feedbackTypesFacade.edit(fbt);
            resetForm();
            notice = NotificationTools.updateSuccess(BEAN_OBJECT);
        }catch(Exception ex){
            notice = NotificationTools.updateFail(BEAN_OBJECT);
        }
    }

    public void resetForm() {
        feedbacktype.setTypeName(null);
        setEditID(0);
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
                        FeedbackTypes obj = new FeedbackTypes();
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
                        feedbackTypesFacade.create(obj);

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
