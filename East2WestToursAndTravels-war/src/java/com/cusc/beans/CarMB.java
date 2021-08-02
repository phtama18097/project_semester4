/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.Cars;
import com.cusc.sessionbean.CarModelsFacadeLocal;
import com.cusc.sessionbean.CarTypesFacadeLocal;
import com.cusc.sessionbean.CarsFacadeLocal;
import com.cusc.helps.ImageTools;
import com.cusc.helps.*;
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
@Named(value = "carMB")
@SessionScoped
public class CarMB implements Serializable {

    @EJB
    private CarModelsFacadeLocal carModelsFacade;

    @EJB
    private CarTypesFacadeLocal carTypesFacade;

    @EJB
    private CarsFacadeLocal carsFacade;

    private static final String UPLOAD_DIRECTORY = "imgCars";
    private static final String BEAN_OBJECT = "car";
    private Part excelFile;

    private Part file;
    private Cars car;
    private String notice = "";
    private String message = "";
    private int editID = 0;
    private int typeID = 0;
    private int modelID = 0;

    public CarMB() {
        car = new Cars();
    }

    public List<Cars> showAll() {
        return carsFacade.findAll();
    }

    public void create() {
        try {
            Cars c = new Cars();
            boolean isInvalid = false;
            if (car.getCarName().length() < 2 || car.getCarName().length() > 80) {
                notice += NotificationTools.error("The length of the name must be between 2 and 80 characters.");
                isInvalid = true;
            }
            if (car.getLicencePlate().length() < 4 || car.getLicencePlate().length() > 15) {
                notice += NotificationTools.error("The length of the license plate must be between 4 and 15 characters.");
                isInvalid = true;
            }
            if (car.getUnitPrice() == null || car.getUnitPrice().longValue() < (long) 1) {
                notice += NotificationTools.error("The unit price must be more than 1 VND.");
                isInvalid = true;
            }
            if (car.getShortDescripiton().length() < 2 || car.getShortDescripiton().length() > 300) {
                notice += NotificationTools.error("The length of the short description must be between 2 and 300 characters.");
                isInvalid = true;
            }
            if (car.getDescription().length() < 5 || car.getDescription().length() > 2147483647) {
                notice += NotificationTools.error("The length of the description must be greater than 5 characters.");
                isInvalid = true;
            }
            if (modelID == 0) {
                notice += NotificationTools.error("You must select a model for the car.");
                isInvalid = true;
            }
            if (typeID == 0) {
                notice += NotificationTools.error("You must select a type for the car.");
                isInvalid = true;
            }
            String result = ImageTools.uploadFile(file, UPLOAD_DIRECTORY);
            switch (result) {
                case "":
                    notice += NotificationTools.error(CommonConstant.IMAGE_IS_NULL_NOTICE);
                    isInvalid = true;
                    break;
                case CommonConstant.FILE_IO:
                    notice += NotificationTools.error(CommonConstant.UPLOADING_FAIL_NOTICE);
                    isInvalid = true;
                    break;
                case CommonConstant.FILE_EXTENSION:
                    notice += NotificationTools.error(CommonConstant.INVALID_IMAGE_EXTENSION_NOTICE);
                    isInvalid = true;
                    break;
                case CommonConstant.FILE_SIZE:
                    notice += NotificationTools.error(CommonConstant.INVALID_FILE_SIZE_NOTICE);
                    isInvalid = true;
                    break;
                default:
                    c.setThumbnail(result);
                    break;
            }
            if (isInvalid) {
                message = NotificationTools.openModal();
                return;
            }

            c.setCarName(car.getCarName());
            c.setLicencePlate(car.getLicencePlate());
            c.setModelId(carModelsFacade.find(modelID));
            c.setTypeId(carTypesFacade.find(typeID));
            c.setUnitPrice(car.getUnitPrice());
            c.setShortDescripiton(car.getShortDescripiton());
            c.setDescription(car.getDescription());
            c.setStatus(car.getStatus());
            carsFacade.create(c);
            resetForm();
            notice = NotificationTools.createSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
            notice = NotificationTools.createFail(BEAN_OBJECT);
        }
    }

    public void delete(Cars c) {
        try {
            carsFacade.remove(c);
            if (ImageTools.deleteFile(c.getThumbnail(), UPLOAD_DIRECTORY)) {
                notice = NotificationTools.deleteSuccess(BEAN_OBJECT);
            } else {
                notice = NotificationTools.deleteFail(BEAN_OBJECT);
            }
        } catch (Exception ex) {
            notice = NotificationTools.createFail(BEAN_OBJECT);
        }
    }

    public void update() {
        try {
            Cars c = carsFacade.find(editID);
            boolean isInvalid = false;
            if (car.getCarName().length() < 2 || car.getCarName().length() > 80) {
                notice += NotificationTools.error("The length of the name must be between 2 and 80 characters.");
                isInvalid = true;
            }
            if (car.getLicencePlate().length() < 4 || car.getLicencePlate().length() > 15) {
                notice += NotificationTools.error("The length of the license plate must be between 4 and 15 characters.");
                isInvalid = true;
            }
            if (car.getUnitPrice() == null || car.getUnitPrice().longValue() < (long) 1) {
                notice += NotificationTools.error("The unit price must be more than 1 VND.");
                isInvalid = true;
            }
            if (car.getShortDescripiton().length() < 2 || car.getShortDescripiton().length() > 300) {
                notice += NotificationTools.error("The length of the short description must be between 2 and 300 characters.");
                isInvalid = true;
            }
            if (car.getDescription().length() < 5 || car.getDescription().length() > 2147483647) {
                notice += NotificationTools.error("The length of the description must be greater than 5 characters.");
                isInvalid = true;
            }
            if (modelID == 0) {
                notice += NotificationTools.error("You must select a model for the car.");
                isInvalid = true;
            }
            if (typeID == 0) {
                notice += NotificationTools.error("You must select a type for the car.");
                isInvalid = true;
            }
            if (file != null) {
                String result = ImageTools.uploadFile(file, UPLOAD_DIRECTORY);
                switch (result) {
                    case "":
                        notice += NotificationTools.info(CommonConstant.IMAGE_IS_NULL_NOTICE);
                        isInvalid = true;
                        break;
                    case CommonConstant.FILE_IO:
                        notice += NotificationTools.error(CommonConstant.UPLOADING_FAIL_NOTICE);
                        isInvalid = true;
                        break;
                    case CommonConstant.FILE_EXTENSION:
                        notice += NotificationTools.error(CommonConstant.INVALID_IMAGE_EXTENSION_NOTICE);
                        isInvalid = true;
                        break;
                    case CommonConstant.FILE_SIZE:
                        notice += NotificationTools.error(CommonConstant.INVALID_FILE_SIZE_NOTICE);
                        isInvalid = true;
                        break;
                    default:
                        c.setThumbnail(result);
                        ImageTools.deleteFile(car.getThumbnail(), UPLOAD_DIRECTORY);
                        break;
                }
            }
            if (isInvalid) {
                message = NotificationTools.editModal(c.getCarId());
                return;
            }
            c.setCarName(car.getCarName());
            c.setLicencePlate(car.getLicencePlate());
            c.setModelId(carModelsFacade.find(modelID));
            c.setTypeId(carTypesFacade.find(typeID));
            c.setUnitPrice(car.getUnitPrice());
            c.setShortDescripiton(car.getShortDescripiton());
            c.setDescription(car.getDescription());
            c.setStatus(car.getStatus());
            carsFacade.edit(c);
            resetForm();
            notice = NotificationTools.updateSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
            notice = NotificationTools.updateFail(BEAN_OBJECT);
        }
    }

    public void resetForm() {
        car.setCarName(null);
        car.setLicencePlate(null);
        car.setModelId(null);
        car.setTypeId(null);
        car.setUnitPrice(null);
        car.setShortDescripiton(null);
        car.setDescription(null);
        car.setThumbnail(null);
        car.setStatus(null);
        setEditID(0);
        setModelID(0);
        setTypeID(0);
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
                        Cars obj = new Cars();
                        HSSFRow row = (HSSFRow) rows.next();

                        Iterator cells = row.cellIterator();

                        while (cells.hasNext()) {
                            HSSFCell cell = (HSSFCell) cells.next();
                            int columnIndex = cell.getColumnIndex();

                            switch (columnIndex) {
                                case 0:
                                    obj.setCarName(cell.getStringCellValue());
                                    break;
                                case 1:
                                    obj.setLicencePlate(cell.getStringCellValue());
                                    break;
                                case 2:
                                    obj.setModelId(carModelsFacade.find((int) cell.getNumericCellValue()));
                                    break;
                                case 3:
                                    obj.setTypeId(carTypesFacade.find((int) cell.getNumericCellValue()));
                                    break;
                                case 4:
                                    obj.setUnitPrice(BigInteger.valueOf((long) cell.getNumericCellValue()));
                                    break;
                                case 5:
                                    obj.setShortDescripiton(cell.getStringCellValue());
                                    break;
                                case 6:
                                    obj.setDescription(cell.getStringCellValue());
                                    break;
                                case 7:
                                    obj.setStatus((short) cell.getNumericCellValue());
                                    break;
                            }
                        }
                        carsFacade.create(obj);

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

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public int getModelID() {
        return modelID;
    }

    public void setModelID(int modelID) {
        this.modelID = modelID;
    }

    public Cars getCar() {
        return car;
    }

    public void setCar(Cars car) {
        this.car = car;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
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
