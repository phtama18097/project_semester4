/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.Destinations;
import com.cusc.helps.CommonConstant;
import com.cusc.helps.ImageTools;
import com.cusc.helps.NotificationTools;
import com.cusc.sessionbean.DestinationsFacadeLocal;
import com.cusc.sessionbean.TourTypesFacadeLocal;
import com.cusc.sessionbean.TownsFacadeLocal;
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
@Named(value = "destinationMB")
@SessionScoped
public class DestinationMB implements Serializable {

    @EJB
    private TourTypesFacadeLocal tourTypesFacade;

    @EJB
    private TownsFacadeLocal townsFacade;

    @EJB
    private DestinationsFacadeLocal destinationsFacade;

    private Part file;
    private static final String UPLOAD_DIRECTORY = "imgDestinations";
    private static final String BEAN_OBJECT = "destination";
    private Part excelFile;

    private Destinations destinations;
    private String notice = "";
    private String message = "";
    private int editID = 0;
    private int townID = 0;
    private int typeID = 0;

    public DestinationMB() {
        destinations = new Destinations();
    }

    public List<Destinations> showAll() {
        return destinationsFacade.findAll();
    }

    public void create() {
        try {
            Destinations d = new Destinations();
            boolean isInvalid = false;
            if (destinations.getDestinationName().length() < 2 || destinations.getDestinationName().length() > 100) {
                notice += NotificationTools.error("The length of the name must be between 2 and 100 characters.");
                isInvalid = true;
            }
            if (destinations.getDescription().length() < 5 || destinations.getDescription().length() > 2147483647) {
                notice += NotificationTools.error("The length of the description must be greater than 5 characters.");
                isInvalid = true;
            }
            if (townID == 0) {
                notice += NotificationTools.error("You must select a town for the destination.");
                isInvalid = true;
            }
            if (typeID == 0) {
                notice += NotificationTools.error("You must select a type for the destination.");
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
                    d.setThumbnail(result);
                    break;
            }
            if (isInvalid) {
                message = NotificationTools.openModal();
                return;
            }
            
            d.setDestinationName(destinations.getDestinationName());
            d.setDescription(destinations.getDescription());
            d.setTypeId(tourTypesFacade.find(typeID));
            d.setTownId(townsFacade.find(townID));

            destinationsFacade.create(d);
            resetForm();
            notice = NotificationTools.createSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
            notice = NotificationTools.createFail(BEAN_OBJECT);
        }
    }

    public void delete(Destinations d) {
        try {
            destinationsFacade.remove(d);
            if (ImageTools.deleteFile(d.getThumbnail(), UPLOAD_DIRECTORY)) {
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
            Destinations d = destinationsFacade.find(editID);
            boolean isInvalid = false;
            if (destinations.getDestinationName().length() < 2 || destinations.getDestinationName().length() > 100) {
                notice += NotificationTools.error("The length of the name must be between 2 and 100 characters.");
                isInvalid = true;
            }
            if (destinations.getDescription().length() < 5 || destinations.getDescription().length() > 2147483647) {
                notice += NotificationTools.error("The length of the description must be greater than 5 characters.");
                isInvalid = true;
            }
            if (townID == 0) {
                notice += NotificationTools.error("You must select a town for the destination.");
                isInvalid = true;
            }
            if (typeID == 0) {
                notice += NotificationTools.error("You must select a type for the destination.");
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
                        d.setThumbnail(result);
                        ImageTools.deleteFile(destinations.getThumbnail(), UPLOAD_DIRECTORY);
                        break;
                }
            }
            if (isInvalid) {
                message = NotificationTools.editModal(d.getDestinationId());
                return;
            }
            d.setDestinationName(destinations.getDestinationName());
            d.setDescription(destinations.getDescription());
            d.setTypeId(tourTypesFacade.find(typeID));
            d.setTownId(townsFacade.find(townID));

            destinationsFacade.edit(d);
            resetForm();
            notice = NotificationTools.updateSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
            notice = NotificationTools.updateFail(BEAN_OBJECT);
        }
    }

    public void resetForm() {
        destinations.setDestinationName(null);
        destinations.setDescription(null);
        destinations.setThumbnail(null);
        destinations.setTypeId(null);
        destinations.setTownId(null);
        setEditID(0);
        setTownID(0);
        setTypeID(0);
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
                        Destinations obj = new Destinations();
                        HSSFRow row = (HSSFRow) rows.next();

                        Iterator cells = row.cellIterator();

                        while (cells.hasNext()) {
                            HSSFCell cell = (HSSFCell) cells.next();
                            int columnIndex = cell.getColumnIndex();

                            switch (columnIndex) {
                                case 0:
                                    obj.setDestinationName(cell.getStringCellValue());
                                    break;
                                case 1:
                                    obj.setDescription(cell.getStringCellValue());
                                    break;
                                case 2:
                                    obj.setTypeId(tourTypesFacade.find((int) cell.getNumericCellValue()));
                                    break;
                                case 3:
                                    obj.setTownId(townsFacade.find((int) cell.getNumericCellValue()));
                                    break;
                            }
                        }
                        destinationsFacade.create(obj);

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

    public Destinations getDestinations() {
        return destinations;
    }

    public void setDestinations(Destinations destinations) {
        this.destinations = destinations;
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

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
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
