/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.CarModels;
import com.cusc.helps.NotificationTools;
import com.cusc.sessionbean.CarModelsFacadeLocal;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigInteger;
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
@Named(value = "carModelMB")
@SessionScoped
public class CarModelMB implements Serializable {

    @EJB
    private CarModelsFacadeLocal carModelsFacade;

    private static final String BEAN_OBJECT = "car model";
    private Part excelFile;
    private CarModels carModels;
    private String notice = "";
    private String message = "";
    private int editID = 0;
    
    public CarModelMB() {
        carModels = new CarModels();
    }
    
    public List<CarModels> showAll() {
        return carModelsFacade.findAll();
    }

    public void create() {
        try {
            CarModels ct = new CarModels();
            boolean isInvalid = false;
            if (carModels.getModelName().length() < 2 || carModels.getModelName().length() > 50) {
                notice += NotificationTools.error("The length of the name must be between 2 and 50 characters.");
                isInvalid = true;
            }
            if (isInvalid) {
                message = NotificationTools.openModal();
                return;
            }
            ct.setModelName(carModels.getModelName());
            carModelsFacade.create(ct);
            resetForm();
            notice = NotificationTools.createSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
            notice = NotificationTools.createFail(BEAN_OBJECT);
        }
    }
    public void delete(CarModels ct){
        try{
            carModelsFacade.remove(ct);
            notice = NotificationTools.deleteSuccess(BEAN_OBJECT);
        }catch(Exception ex){
            notice = NotificationTools.deleteFail(BEAN_OBJECT);
        }
    }
    
    public void update() {
        try{
            CarModels ct = carModelsFacade.find(editID);
            boolean isInvalid = false;
            if (carModels.getModelName().length() < 2 || carModels.getModelName().length() > 50) {
                notice += NotificationTools.error("The length of the name must be between 2 and 50 characters.");
                isInvalid = true;
            }
            if (isInvalid) {
                message = NotificationTools.editModal(ct.getModelId());
                return;
            }
            ct.setModelName(carModels.getModelName());
            carModelsFacade.edit(ct);
            resetForm();
            notice = NotificationTools.updateSuccess(BEAN_OBJECT);
        }catch(Exception ex){
            notice = NotificationTools.updateFail(BEAN_OBJECT);
        }
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
                        CarModels obj = new CarModels();
                        HSSFRow row = (HSSFRow) rows.next();

                        Iterator cells = row.cellIterator();

                        while (cells.hasNext()) {
                            HSSFCell cell = (HSSFCell) cells.next();
                            int columnIndex = cell.getColumnIndex();

                            switch (columnIndex) {
                                case 0:
                                    obj.setModelName(cell.getStringCellValue());
                                    break;
                            }
                        }
                        carModelsFacade.create(obj);

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

    public void resetForm() {
        carModels.setModelName(null);
        setEditID(0);
    }

    public CarModels getCarModels() {
        return carModels;
    }

    public void setCarModels(CarModels carModels) {
        this.carModels = carModels;
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
