/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.CarTypes;
import com.cusc.helps.NotificationTools;
import com.cusc.sessionbean.CarTypesFacadeLocal;
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
@Named(value = "carTypeMB")
@SessionScoped
public class CarTypeMB implements Serializable {

    @EJB
    private CarTypesFacadeLocal carTypesFacade;

    private static final String BEAN_OBJECT = "car type";
    private Part excelFile;
    private CarTypes carTypes;
    private String notice = "";
    private String message = "";
    private int editID = 0;

    /**
     * Creates a new instance of FeedbackTypeMB
     */
    public CarTypeMB() {
        carTypes = new CarTypes();
    }

    public List<CarTypes> showAll() {
        return carTypesFacade.findAll();
    }

    public void create() {
        try {
            CarTypes ct = new CarTypes();
            boolean isInvalid = false;
            if (carTypes.getDescription().length() < 2 || carTypes.getDescription().length() > 50) {
                notice += NotificationTools.error("The length of the description must be between 2 and 50 characters.");
                isInvalid = true;
            }
            if (carTypes.getSeat()== null || carTypes.getSeat().longValue() < (long) 1) {
                notice += NotificationTools.error("The seat must be more than 1.");
                isInvalid = true;
            }
            if (isInvalid) {
                message = NotificationTools.openModal();
                return;
            }
            ct.setDescription(carTypes.getDescription());
            ct.setSeat(carTypes.getSeat());
            carTypesFacade.create(ct);
            resetForm();
            notice = NotificationTools.createSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
            notice = NotificationTools.createFail(BEAN_OBJECT);
        }
    }

    public void delete(CarTypes ct) {
        try {
            carTypesFacade.remove(ct);
            notice = NotificationTools.deleteSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
            notice = NotificationTools.deleteFail(BEAN_OBJECT);
        }
    }

    public void update() {
        try {
            CarTypes ct = carTypesFacade.find(editID);
            boolean isInvalid = false;
            if (carTypes.getDescription().length() < 2 || carTypes.getDescription().length() > 50) {
                notice += NotificationTools.error("The length of the description must be between 2 and 50 characters.");
                isInvalid = true;
            }
            if (carTypes.getSeat()== null || carTypes.getSeat().longValue() < (long) 1) {
                notice += NotificationTools.error("The seat must be more than 1.");
                isInvalid = true;
            }
            if (isInvalid) {
                message = NotificationTools.editModal(ct.getTypeId());
                return;
            }
            ct.setDescription(carTypes.getDescription());
            ct.setSeat(carTypes.getSeat());
            carTypesFacade.edit(ct);
            resetForm();
            notice = NotificationTools.updateSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
            notice = NotificationTools.updateFail(BEAN_OBJECT);
        }
    }

    public void resetForm() {
        carTypes.setDescription(null);
        carTypes.setSeat(null);
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
                        CarTypes obj = new CarTypes();
                        HSSFRow row = (HSSFRow) rows.next();

                        Iterator cells = row.cellIterator();

                        while (cells.hasNext()) {
                            HSSFCell cell = (HSSFCell) cells.next();
                            int columnIndex = cell.getColumnIndex();

                            switch (columnIndex) {
                                case 0:
                                    obj.setDescription(cell.getStringCellValue());
                                    break;
                                case 1:
                                    obj.setSeat((short)cell.getNumericCellValue());
                                    break;
                            }
                        }
                        carTypesFacade.create(obj);

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

    public CarTypes getCarTypes() {
        return carTypes;
    }

    public void setCarTypes(CarTypes carTypes) {
        this.carTypes = carTypes;
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
