/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.Accommodations;
import com.cusc.helps.CommonConstant;
import com.cusc.helps.ImageTools;
import com.cusc.helps.NotificationTools;
import com.cusc.sessionbean.AccommodationsFacadeLocal;
import com.cusc.sessionbean.TownsFacadeLocal;
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
@Named(value = "accommodationMB")
@SessionScoped
public class AccommodationMB implements Serializable {

    @EJB
    private AccommodationsFacadeLocal accommodationsFacade;

    @EJB
    private TownsFacadeLocal townsFacade;

    private Part file;
    private static final String UPLOAD_DIRECTORY = "imgAccommodations";
    private static final String BEAN_OBJECT = "accommodation";
    private Part excelFile;

    private Accommodations accommodations;
    private String notice = "";
    private String message = "";
    private int editID = 0;
    private int townID = 0;

    public AccommodationMB() {
        accommodations = new Accommodations();
    }

    public List<Accommodations> showAll() {
        return accommodationsFacade.findAll();
    }

    public void create() {
        try {
            Accommodations r = new Accommodations();
            boolean isInvalid = false;
            if (accommodations.getAccommodationName().length() < 2 || accommodations.getAccommodationName().length() > 50) {
                notice += NotificationTools.error("The length of the name must be between 2 and 50 characters.");
                isInvalid = true;
            }
            if (accommodations.getDescription().length() < 5 || accommodations.getDescription().length() > 2147483647) {
                notice += NotificationTools.error("The length of the description must be greater than 5 characters.");
                isInvalid = true;
            }
            if (townID == 0) {
                notice += NotificationTools.error("You must select a town for the accommodation.");
                isInvalid = true;
            }
            if (accommodations.getMinPrice() == null || accommodations.getMinPrice().longValue() < (long) 1) {
                notice += NotificationTools.error("The minimum price must be more than 1 VND.");
                isInvalid = true;
            }
            if (accommodations.getMaxPrice()== null || accommodations.getMaxPrice().longValue() < (long) 1) {
                notice += NotificationTools.error("The maximum price must be more than 1 VND.");
                isInvalid = true;
            }
            if (accommodations.getMaxPrice() != null && accommodations.getMinPrice() != null && accommodations.getMaxPrice().longValue() <= accommodations.getMinPrice().longValue()) {
                notice += NotificationTools.error("The maximum price must be more than minimum price.");
                isInvalid = true;
            }
            if (accommodations.getLocation().length() < 5 || accommodations.getLocation().length() > 2147483647) {
                notice += NotificationTools.error("The length of the location must be greater than 5 characters.");
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
                    r.setThumbnail(result);
                    break;
            }
            if (isInvalid) {
                message = NotificationTools.openModal();
                return;
            }    
            r.setAccommodationName(accommodations.getAccommodationName());
            r.setDescription(accommodations.getDescription());
            r.setMinPrice(accommodations.getMinPrice());
            r.setMaxPrice(accommodations.getMaxPrice());
            r.setLocation(accommodations.getLocation());

            r.setTownId(townsFacade.find(townID));

            accommodationsFacade.create(r);
            resetForm();
            notice = NotificationTools.createSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
            notice = NotificationTools.createFail(BEAN_OBJECT);
        }
    }

    public void delete(Accommodations r) {
        try {
            accommodationsFacade.remove(r);
            if (ImageTools.deleteFile(r.getThumbnail(), UPLOAD_DIRECTORY)) {
                notice = NotificationTools.deleteSuccess(BEAN_OBJECT);
            } else {
                notice = NotificationTools.deleteFail(BEAN_OBJECT);
            }
        } catch (Exception ex) {
            notice = NotificationTools.deleteFail(BEAN_OBJECT);
        }
    }

    public void update() {
        try {
            Accommodations r = accommodationsFacade.find(editID);
            boolean isInvalid = false;
            if (accommodations.getAccommodationName().length() < 2 || accommodations.getAccommodationName().length() > 50) {
                notice += NotificationTools.error("The length of the name must be between 2 and 50 characters.");
                isInvalid = true;
            }
            if (accommodations.getDescription().length() < 5 || accommodations.getDescription().length() > 2147483647) {
                notice += NotificationTools.error("The length of the description must be greater than 5 characters.");
                isInvalid = true;
            }
            if (townID == 0) {
                notice += NotificationTools.error("You must select a town for the accommodation.");
                isInvalid = true;
            }
            if (accommodations.getMinPrice() == null || accommodations.getMinPrice().longValue() < (long) 1) {
                notice += NotificationTools.error("The minimum price must be more than 1 VND.");
                isInvalid = true;
            }
            if (accommodations.getMaxPrice()== null || accommodations.getMaxPrice().longValue() < (long) 1) {
                notice += NotificationTools.error("The maximum price must be more than 1 VND.");
                isInvalid = true;
            }
            if (accommodations.getLocation().length() < 5 || accommodations.getLocation().length() > 2147483647) {
                notice += NotificationTools.error("The length of the location must be greater than 5 characters.");
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
                        r.setThumbnail(result);
                        ImageTools.deleteFile(accommodations.getThumbnail(), UPLOAD_DIRECTORY);
                        break;
                }
            }
            if (isInvalid) {
                message = NotificationTools.editModal(r.getAccommodationId());
                return;
            }
            r.setAccommodationName(accommodations.getAccommodationName());
            r.setDescription(accommodations.getDescription());
            r.setMinPrice(accommodations.getMinPrice());
            r.setMaxPrice(accommodations.getMaxPrice());
            r.setLocation(accommodations.getLocation());
            r.setTownId(townsFacade.find(townID));

            accommodationsFacade.edit(r);
            resetForm();
            notice = NotificationTools.updateSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
            notice = NotificationTools.updateFail(BEAN_OBJECT);
        }
    }

    public void resetForm() {
        accommodations.setAccommodationName(null);
        accommodations.setDescription(null);
        accommodations.setLocation(null);
        accommodations.setMinPrice(null);
        accommodations.setMaxPrice(null);
        accommodations.setThumbnail(null);
        accommodations.setTownId(null);
        setEditID(0);
        setTownID(0);
        setFile(null);
        setExcelFile(null);
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
                        Accommodations obj = new Accommodations();
                        HSSFRow row = (HSSFRow) rows.next();

                        Iterator cells = row.cellIterator();

                        while (cells.hasNext()) {
                            HSSFCell cell = (HSSFCell) cells.next();
                            int columnIndex = cell.getColumnIndex();

                            switch (columnIndex) {
                                case 0:
                                    obj.setAccommodationName(cell.getStringCellValue());
                                    break;
                                case 1:
                                    obj.setDescription(cell.getStringCellValue());
                                    break;
                                case 2:
                                    obj.setMinPrice(BigInteger.valueOf((long) cell.getNumericCellValue()));
                                    break;
                                case 3:
                                    obj.setMaxPrice(BigInteger.valueOf((long) cell.getNumericCellValue()));
                                    break;
                                case 4:
                                    obj.setLocation(cell.getStringCellValue());
                                    break;
                                case 5:
                                    obj.setTownId(townsFacade.find((int) cell.getNumericCellValue()));
                                    break;
                            }
                        }
                        accommodationsFacade.create(obj);

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

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public Accommodations getAccommodations() {
        return accommodations;
    }

    public void setAccommodations(Accommodations accommodations) {
        this.accommodations = accommodations;
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

    public int getTownID() {
        return townID;
    }

    public void setTownID(int townID) {
        this.townID = townID;
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
