/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.DestinationSchedules;
import com.cusc.entities.DestinationSchedulesPK;
import com.cusc.entities.Destinations;
import com.cusc.entities.TourPackages;
import com.cusc.helps.NotificationTools;
import com.cusc.sessionbean.DestinationSchedulesFacadeLocal;
import com.cusc.sessionbean.DestinationsFacadeLocal;
import com.cusc.sessionbean.TourPackagesFacadeLocal;
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
@Named(value = "tourPackageMB")
@SessionScoped
public class TourPackageMB implements Serializable {

    @EJB
    private DestinationsFacadeLocal destinationsFacade;

    @EJB
    private DestinationSchedulesFacadeLocal destinationSchedulesFacade;

    @EJB
    private TourPackagesFacadeLocal tourPackagesFacade;

    private static final String BEAN_OBJECT = "tour package";
    private Part excelFile;
    private TourPackages tourPackages;
    private String notice = "";
    private String message = "";
    private int editID = 0;
    private boolean packageStatus = true;
    private TourPackages scheduledPackage;
    private int destinationId = 0;
    private int dayQuantity = 1;

    public TourPackageMB() {
        tourPackages = new TourPackages();
        scheduledPackage = new TourPackages();
    }

    public List<TourPackages> showAll() {
        return tourPackagesFacade.findAll();
    }

    public List<DestinationSchedules> showDestinationSchedules(int id) {
        return destinationSchedulesFacade.findDestinations(id);
    }

    public List<Destinations> showDestinationForPackage(int id) {
        return destinationSchedulesFacade.findDestinationForPackage(id);
    }

    public String viewSchedule(int id) {
        scheduledPackage = tourPackagesFacade.find(id);
        return "scheduleDestination";
    }

    public boolean notifySchedule(int id) {
        return destinationSchedulesFacade.countSchedule(id) > 0;
    }

    public void scheduleDestination() {
        try {
            boolean isInvalid = false;
            if (dayQuantity <  1) {
                notice += NotificationTools.error("The day quantity must be more than 1 day.");
                isInvalid = true;
            }
            if (isInvalid) {
                message = NotificationTools.openModal();
                return ;
            }
            DestinationSchedulesPK destinationSchedulesPK = new DestinationSchedulesPK(scheduledPackage.getPackageId(), destinationId);
            DestinationSchedules ds = new DestinationSchedules();
            ds.setDestinationSchedulesPK(destinationSchedulesPK);
            ds.setDayQuantity((short) dayQuantity);
            destinationSchedulesFacade.create(ds);
            setDayQuantity(1);
            setDestinationId(0);
            notice = NotificationTools.createSuccess("destination");
        } catch (Exception ex) {
            setDayQuantity(1);
            setDestinationId(0);
            notice = NotificationTools.createFail("destination");
        }

    }

    public void deleteSchedule(int id) {
        try {
            DestinationSchedulesPK destinationSchedulesPK = new DestinationSchedulesPK(scheduledPackage.getPackageId(), id);
            DestinationSchedules ds = destinationSchedulesFacade.find(destinationSchedulesPK);
            destinationSchedulesFacade.remove(ds);
            notice = NotificationTools.deleteSuccess("destination");
        } catch (Exception ex) {
            notice = NotificationTools.deleteFail("destination");
        }
    }

    public String showDestinationName(int id) {
        return destinationsFacade.find(id).getDestinationName();
    }

    public String showThumbnail(int id) {
        return destinationsFacade.find(id).getThumbnail();
    }

    public String showTypeName(int id) {
        return destinationsFacade.find(id).getTypeId().getTypeName();
    }

    public String create() {
        try {
            TourPackages tp = new TourPackages();
            boolean isInvalid = false;
            if (tourPackages.getPackageName().length() < 5 || tourPackages.getPackageName().length() > 120) {
                notice += NotificationTools.error("The length of the name must be between 5 and 120 characters.");
                isInvalid = true;
            }
            if (tourPackages.getDescription().length() < 5 || tourPackages.getDescription().length() > 2147483647) {
                notice += NotificationTools.error("The length of the description must be greater than 5 characters.");
                isInvalid = true;
            }
            if (isInvalid) {
                message = NotificationTools.openModal();
                return "manageTourPackages";
            }
            tp.setPackageName(tourPackages.getPackageName());
            tp.setDescription(tourPackages.getDescription());
            if (packageStatus) {
                tp.setStatus((short) 1);
            } else {
                tp.setStatus((short) 0);
            }
            tourPackagesFacade.create(tp);
            resetForm();
            scheduledPackage = tourPackagesFacade.find(tp.getPackageId());
            return "scheduleDestination";
        } catch (Exception ex) {
            notice = NotificationTools.createFail(BEAN_OBJECT);
            return "manageTourPackages";
        }
    }

    public void delete(TourPackages tp) {
        try {
            tourPackagesFacade.remove(tp);
            notice = NotificationTools.deleteSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
            notice = NotificationTools.deleteFail(BEAN_OBJECT);
        }
    }

    public void update() {
        try {
            TourPackages tp = tourPackagesFacade.find(editID);
            boolean isInvalid = false;
            if (tourPackages.getPackageName().length() < 5 || tourPackages.getPackageName().length() > 120) {
                notice += NotificationTools.error("The length of the name must be between 5 and 120 characters.");
                isInvalid = true;
            }
            if (tourPackages.getDescription().length() < 5 || tourPackages.getDescription().length() > 2147483647) {
                notice += NotificationTools.error("The length of the description must be greater than 5 characters.");
                isInvalid = true;
            }
            if (isInvalid) {
                message = NotificationTools.editModal(tp.getPackageId());
                return ;
            }
            tp.setPackageName(tourPackages.getPackageName());
            tp.setDescription(tourPackages.getDescription());
            if (packageStatus) {
                tp.setStatus((short) 1);
            } else {
                tp.setStatus((short) 0);
            }
            tourPackagesFacade.edit(tp);
            resetForm();
            notice = NotificationTools.updateSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
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
                        TourPackages obj = new TourPackages();
                        HSSFRow row = (HSSFRow) rows.next();

                        Iterator cells = row.cellIterator();

                        while (cells.hasNext()) {
                            HSSFCell cell = (HSSFCell) cells.next();
                            int columnIndex = cell.getColumnIndex();

                            switch (columnIndex) {
                                case 0:
                                    obj.setPackageName(cell.getStringCellValue());
                                    break;
                                case 1:
                                    obj.setDescription(cell.getStringCellValue());
                                    break;
                                case 2:
                                    obj.setStatus((short) cell.getNumericCellValue());
                                    break;
                            }
                        }
                        tourPackagesFacade.create(obj);

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
        tourPackages.setPackageName(null);
        tourPackages.setDescription(null);
        tourPackages.setStatus(null);
        editID = 0;
    }

    public TourPackages getTourPackages() {
        return tourPackages;
    }

    public void setTourPackages(TourPackages tourPackages) {
        this.tourPackages = tourPackages;
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

    public boolean isPackageStatus() {
        return packageStatus;
    }

    public void setPackageStatus(boolean packageStatus) {
        this.packageStatus = packageStatus;
    }

    public TourPackages getScheduledPackage() {
        return scheduledPackage;
    }

    public void setScheduledPackage(TourPackages scheduledPackage) {
        this.scheduledPackage = scheduledPackage;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public int getDayQuantity() {
        return dayQuantity;
    }

    public void setDayQuantity(int dayQuantity) {
        this.dayQuantity = dayQuantity;
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
