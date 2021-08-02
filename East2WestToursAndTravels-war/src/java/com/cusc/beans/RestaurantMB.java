/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.Restaurants;
import com.cusc.helps.CommonConstant;
import com.cusc.helps.ImageTools;
import com.cusc.helps.NotificationTools;
import com.cusc.sessionbean.RestaurantsFacadeLocal;
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
@Named(value = "restaurantMB")
@SessionScoped
public class RestaurantMB implements Serializable {

    @EJB
    private TownsFacadeLocal townsFacade;

    @EJB
    private RestaurantsFacadeLocal restaurantsFacade;

    private Part file;
    private final String UPLOAD_DIRECTORY = "imgRestaurants";
    private static final String BEAN_OBJECT = "restaurant";
    private Part excelFile;

    private Restaurants restaurants;
    private String notice = "";
    private String message = "";
    private int editID = 0;
    private int townID = 0;

    public RestaurantMB() {
        restaurants = new Restaurants();
    }

    public List<Restaurants> showAll() {
        return restaurantsFacade.findAll();
    }

    public void create() {
        try {
            Restaurants r = new Restaurants();
            boolean isInvalid = false;
            if (restaurants.getRestaurantName().length() < 5 || restaurants.getRestaurantName().length() > 50) {
                notice += NotificationTools.error("The length of the name must be between 5 and 50 characters.");
                isInvalid = true;
            }
            if (restaurants.getDescription().length() < 5 || restaurants.getDescription().length() > 2147483647) {
                notice += NotificationTools.error("The length of the description must be greater than 5 characters.");
                isInvalid = true;
            }
            if (townID == 0) {
                notice += NotificationTools.error("You must select a town for the restaurant.");
                isInvalid = true;
            }
            if (restaurants.getMinPrice() == null || restaurants.getMinPrice().longValue() < (long) 1) {
                notice += NotificationTools.error("The minimum price must be more than 1 VND.");
                isInvalid = true;
            }
            if (restaurants.getMaxPrice()== null || restaurants.getMaxPrice().longValue() < (long) 1) {
                notice += NotificationTools.error("The maximum price must be more than 1 VND.");
                isInvalid = true;
            }
            if (restaurants.getMaxPrice() != null && restaurants.getMinPrice() != null && restaurants.getMaxPrice().longValue() <= restaurants.getMinPrice().longValue()) {
                notice += NotificationTools.error("The maximum price must be more than minimum price.");
                isInvalid = true;
            }
            if (restaurants.getLocation().length() < 5 || restaurants.getLocation().length() > 2147483647) {
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
            r.setRestaurantName(restaurants.getRestaurantName());
            r.setDescription(restaurants.getDescription());
            r.setMinPrice(restaurants.getMinPrice());
            r.setMaxPrice(restaurants.getMaxPrice());
            r.setLocation(restaurants.getLocation());
            r.setTownId(townsFacade.find(townID));

            restaurantsFacade.create(r);
            resetForm();
            notice = NotificationTools.createSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
            notice = NotificationTools.createFail(BEAN_OBJECT);
        }
    }

    public void delete(Restaurants r) {
        try {
            restaurantsFacade.remove(r);
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
            Restaurants r = restaurantsFacade.find(editID);
            boolean isInvalid = false;
            if (restaurants.getRestaurantName().length() < 5 || restaurants.getRestaurantName().length() > 50) {
                notice += NotificationTools.error("The length of the name must be between 5 and 50 characters.");
                isInvalid = true;
            }
            if (restaurants.getDescription().length() < 5 || restaurants.getDescription().length() > 2147483647) {
                notice += NotificationTools.error("The length of the description must be greater than 5 characters.");
                isInvalid = true;
            }
            if (townID == 0) {
                notice += NotificationTools.error("You must select a town for the restaurant.");
                isInvalid = true;
            }
            if (restaurants.getMinPrice() == null || restaurants.getMinPrice().longValue() < (long) 1) {
                notice += NotificationTools.error("The minimum price must be more than 1 VND.");
                isInvalid = true;
            }
            if (restaurants.getMaxPrice()== null || restaurants.getMaxPrice().longValue() < (long) 1) {
                notice += NotificationTools.error("The maximum price must be more than 1 VND.");
                isInvalid = true;
            }
            if (restaurants.getLocation().length() < 5 || restaurants.getLocation().length() > 2147483647) {
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
                        ImageTools.deleteFile(restaurants.getThumbnail(), UPLOAD_DIRECTORY);
                        break;
                }
            }
            if (isInvalid) {
                message = NotificationTools.editModal(r.getRestaurantId());
                return;
            }
            r.setRestaurantName(restaurants.getRestaurantName());
            r.setDescription(restaurants.getDescription());
            r.setMinPrice(restaurants.getMinPrice());
            r.setMaxPrice(restaurants.getMaxPrice());
            r.setLocation(restaurants.getLocation());
            r.setTownId(townsFacade.find(townID));

            restaurantsFacade.edit(r);
            resetForm();
            notice = NotificationTools.updateSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
            notice = NotificationTools.updateFail(BEAN_OBJECT);
        }
    }

    public void resetForm() {
        restaurants.setRestaurantName(null);
        restaurants.setDescription(null);
        restaurants.setLocation(null);
        restaurants.setMinPrice(null);
        restaurants.setMaxPrice(null);
        restaurants.setThumbnail(null);
        restaurants.setTownId(null);
        setEditID(0);
        setTownID(0);
        setFile(null);
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
                        Restaurants obj = new Restaurants();
                        HSSFRow row = (HSSFRow) rows.next();

                        Iterator cells = row.cellIterator();

                        while (cells.hasNext()) {
                            HSSFCell cell = (HSSFCell) cells.next();
                            int columnIndex = cell.getColumnIndex();

                            switch (columnIndex) {
                                case 0:
                                    obj.setRestaurantName(cell.getStringCellValue());
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
                        restaurantsFacade.create(obj);

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

    public Restaurants getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Restaurants restaurants) {
        this.restaurants = restaurants;
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
