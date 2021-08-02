/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.AccommodationSchedules;
import com.cusc.entities.AccommodationSchedulesPK;
import com.cusc.entities.Accommodations;
import com.cusc.entities.RestaurantSchedules;
import com.cusc.entities.RestaurantSchedulesPK;
import com.cusc.entities.Restaurants;
import com.cusc.entities.Tours;
import com.cusc.helps.CommonConstant;
import com.cusc.helps.ImageTools;
import com.cusc.helps.NotificationTools;
import com.cusc.helps.StatusTools;
import com.cusc.sessionbean.AccommodationSchedulesFacadeLocal;
import com.cusc.sessionbean.AccommodationsFacadeLocal;
import com.cusc.sessionbean.EmployeesFacadeLocal;
import com.cusc.sessionbean.RestaurantSchedulesFacadeLocal;
import com.cusc.sessionbean.RestaurantsFacadeLocal;
import com.cusc.sessionbean.TourPackagesFacadeLocal;
import com.cusc.sessionbean.ToursFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.http.Part;

/**
 *
 * @author Admin
 */
@Named(value = "tourMB")
@SessionScoped
public class TourMB implements Serializable {

    @EJB
    private AccommodationSchedulesFacadeLocal accommodationSchedulesFacade;

    @EJB
    private AccommodationsFacadeLocal accommodationsFacade;

    @EJB
    private RestaurantSchedulesFacadeLocal restaurantSchedulesFacade;

    @EJB
    private RestaurantsFacadeLocal restaurantsFacade;

    @EJB
    private EmployeesFacadeLocal employeesFacade;

    @EJB
    private TourPackagesFacadeLocal tourPackagesFacade;

    @EJB
    private ToursFacadeLocal toursFacade;

    @Inject
    private EmployeeLoginMB employeeLoginMB;

    private Tours tours;
    private Part file;
    private String notice = "";
    private String message = "";
    private int editID = 0;
    private boolean tourStatus = true;
    private int packageID = 0;

    private static final String UPLOAD_DIRECTORY = "imgDestinations";
    private static final String BEAN_OBJECT = "tour";

    private Tours scheduledTour;
    private Date visitDate;
    private int restaurantId = 0;
    private Date accommodationVisitDate;
    private int accommodationId = 0;

    public TourMB() {
        tours = new Tours();
        scheduledTour = new Tours();
    }

    public List<Tours> showAll() {
        return toursFacade.findAll();
    }

    public String create() {
        try {
            Tours t = new Tours();
            boolean isInvalid = false;
            if (tours.getTourName().length() < 5 || tours.getTourName().length() > 120) {
                notice += NotificationTools.error("The length of the name must be between 5 and 120 characters.");
                isInvalid = true;
            }
            if (tours.getUnitPrice() == null || tours.getUnitPrice().longValue() < (long) 1) {
                notice += NotificationTools.error("The unit price must be more than 1 VND.");
                isInvalid = true;
            }
            if (tours.getShortDescription().length() < 2 || tours.getShortDescription().length() > 200) {
                notice += NotificationTools.error("The length of the short description must be between 2 and 200 characters.");
                isInvalid = true;
            }
            if (tours.getDescription().length() < 5 || tours.getDescription().length() > 2147483647) {
                notice += NotificationTools.error("The length of the description must be greater than 5 characters.");
                isInvalid = true;
            }
            if (tours.getDepartureDate()== null) {
                notice += NotificationTools.error("The departure date must be entered.");
                isInvalid = true;
            }
            if (tours.getReturnDate()== null) {
                notice += NotificationTools.error("The return date must be entered.");
                isInvalid = true;
            }
            if (tours.getDepartureDate() != null && tours.getReturnDate()!= null && tours.getDepartureDate().after(tours.getReturnDate())) {
                notice += NotificationTools.error("The return date must be more than departure date.");
                isInvalid = true;
            }
            if (tours.getMinQuantity()== null || tours.getMinQuantity().longValue() < (long) 1) {
                notice += NotificationTools.error("The minimum quantity must be more than 1 person.");
                isInvalid = true;
            }
            if (tours.getMaxQuantity()== null || tours.getMaxQuantity().longValue() < (long) 1) {
                notice += NotificationTools.error("The maximum quantity must be more than 1 person.");
                isInvalid = true;
            }
            if (tours.getMinQuantity()!= null && tours.getMaxQuantity()!= null && tours.getMaxQuantity().longValue() <= tours.getMinQuantity().longValue()) {
                notice += NotificationTools.error("The maximum quantity must be more than minimum quantity.");
                isInvalid = true;
            }
            if (packageID == 0) {
                notice += NotificationTools.error("You must select a tour package for the tour.");   
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
                    t.setThumbnail(result);
                    break;
            }
            if (isInvalid) {
                message = NotificationTools.openModal();
                return "manageTours";
            }
               
            t.setTourName(tours.getTourName());
            t.setPackageId(tourPackagesFacade.find(packageID));
            t.setUnitPrice(tours.getUnitPrice());
            t.setShortDescription(tours.getShortDescription());
            t.setDescription(tours.getDescription());
            t.setDepartureDate(tours.getDepartureDate());
            t.setReturnDate(tours.getReturnDate());
            t.setMinQuantity(tours.getMinQuantity());
            t.setMaxQuantity(tours.getMaxQuantity());
            t.setEmployeeId(employeesFacade.find(employeeLoginMB.getEmpSignedIn().getEmployeeId()));
            t.setStatus(StatusTools.readStatus(tourStatus));
            toursFacade.create(t);
            resetForm();
            notice = NotificationTools.createSuccess(BEAN_OBJECT);
            scheduledTour = toursFacade.find(t.getTourId());
            return "scheduleTours";
        } catch (Exception ex) {
            notice = NotificationTools.createFail(BEAN_OBJECT);
            return "manageTours";
        }
    }

    public void delete(Tours t) {
        try {
            toursFacade.remove(t);
            if (ImageTools.deleteFile(t.getThumbnail(), UPLOAD_DIRECTORY)) {
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
            Tours t = toursFacade.find(editID);
            boolean isInvalid = false;
            if (tours.getTourName().length() < 5 || tours.getTourName().length() > 120) {
                notice += NotificationTools.error("The length of the name must be between 5 and 120 characters.");
                isInvalid = true;
            }
            if (tours.getUnitPrice() == null || tours.getUnitPrice().longValue() < (long) 1) {
                notice += NotificationTools.error("The unit price must be more than 1 VND.");
                isInvalid = true;
            }
            if (tours.getShortDescription().length() < 2 || tours.getShortDescription().length() > 200) {
                notice += NotificationTools.error("The length of the short description must be between 2 and 200 characters.");
                isInvalid = true;
            }
            if (tours.getDescription().length() < 5 || tours.getDescription().length() > 2147483647) {
                notice += NotificationTools.error("The length of the description must be greater than 5 characters.");
                isInvalid = true;
            }
            if (tours.getDepartureDate()== null) {
                notice += NotificationTools.error("The departure date must be entered.");
                isInvalid = true;
            }
            if (tours.getReturnDate()== null) {
                notice += NotificationTools.error("The return date must be entered.");
                isInvalid = true;
            }
            if (tours.getDepartureDate() != null && tours.getReturnDate()!= null && tours.getDepartureDate().after(tours.getReturnDate())) {
                notice += NotificationTools.error("The return date must be more than departure date.");
                isInvalid = true;
            }
            if (tours.getMinQuantity()== null || tours.getMinQuantity().longValue() < (long) 1) {
                notice += NotificationTools.error("The minimum quantity must be more than 1 person.");
                isInvalid = true;
            }
            if (tours.getMaxQuantity()== null || tours.getMaxQuantity().longValue() < (long) 1) {
                notice += NotificationTools.error("The maximum quantity must be more than 1 person.");
                isInvalid = true;
            }
            if (tours.getMinQuantity()!= null && tours.getMaxQuantity()!= null && tours.getMaxQuantity().longValue() <= tours.getMinQuantity().longValue()) {
                notice += NotificationTools.error("The maximum quantity must be more than minimum quantity.");
                isInvalid = true;
            }
            if (packageID == 0) {
                notice += NotificationTools.error("You must select a tour package for the tour.");   
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
                        t.setThumbnail(result);
                        ImageTools.deleteFile(tours.getThumbnail(), UPLOAD_DIRECTORY);
                        break;
                }
            }
            if (isInvalid) {
                message = NotificationTools.editModal(t.getTourId());
                return ;
            }
            t.setTourName(tours.getTourName());
            t.setPackageId(tourPackagesFacade.find(packageID));
            t.setUnitPrice(tours.getUnitPrice());
            t.setShortDescription(tours.getShortDescription());
            t.setDescription(tours.getDescription());
            t.setDepartureDate(tours.getDepartureDate());
            t.setReturnDate(tours.getReturnDate());
            t.setMinQuantity(tours.getMinQuantity());
            t.setMaxQuantity(tours.getMaxQuantity());
            t.setEmployeeId(employeesFacade.find(employeeLoginMB.getEmpSignedIn().getEmployeeId()));
            t.setStatus(StatusTools.readStatus(tourStatus));
            toursFacade.edit(t);
            resetForm();
            notice = NotificationTools.updateSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
            notice = NotificationTools.updateFail(BEAN_OBJECT);
        }
    }

    public void resetForm() {
        tours.setTourName(null);
        tours.setPackageId(null);
        tours.setUnitPrice(null);
        tours.setShortDescription(null);
        tours.setDescription(null);
        tours.setDepartureDate(null);
        tours.setReturnDate(null);
        tours.setMinQuantity(null);
        tours.setMaxQuantity(null);
        tours.setEmployeeId(null);
        tours.setStatus((short) 0);
        setTourStatus(true);
        setPackageID(0);
        setFile(null);
        editID = 0;
    }

    public Tours getTours() {
        return tours;
    }

    public List<Restaurants> showRestaurantForTour(int id) {
        return restaurantsFacade.findRestaurantsForTour(id);
    }

    public List<Accommodations> showAccommodationForTour(int id) {
        return accommodationsFacade.findAccommodationsForTour(id);
    }

    public List<RestaurantSchedules> showSchedules(int id) {
        return restaurantSchedulesFacade.findRestaurantsOfTour(id);
    }

    public List<AccommodationSchedules> showAccommodationSchedules(int id) {
        return accommodationSchedulesFacade.findAccommodationsOfTour(id);
    }

    public String viewRestaurantSchedule(int id) {
        scheduledTour = toursFacade.find(id);
        return "scheduleTours";
    }

    public String viewAccommodationSchedule(int id) {
        scheduledTour = toursFacade.find(id);
        return "scheduleAccommodations";
    }

    public boolean notifyRestaurantSchedule(int id) {
        return restaurantsFacade.countSchedule(id) > 0;
    }

    public boolean notifyAccommodationSchedule(int id) {
        return accommodationsFacade.countSchedule(id) > 0;
    }

    public int countSchdedules(int id) {
        return restaurantsFacade.countSchedule(id);
    }

    public int countAccommodationSchdedules(int id) {
        return accommodationsFacade.countSchedule(id);
    }

    public void scheduleRestaurant() {
        try {
            boolean isInvalid = false;
            if (visitDate == null) {
                notice += NotificationTools.error("The visit date must be entered.");
                isInvalid = true;
            }
            if (visitDate != null && visitDate.before(new Date(System.currentTimeMillis()))) {
                notice += NotificationTools.error("The visit date must be more than today.");
                isInvalid = true;
            }
            if (isInvalid) {
                message = NotificationTools.openModal();
                return;
            }
            RestaurantSchedulesPK restaurantSchedulesPK = new RestaurantSchedulesPK(scheduledTour.getTourId(), restaurantId);
            RestaurantSchedules rs = new RestaurantSchedules();
            rs.setRestaurantSchedulesPK(restaurantSchedulesPK);
            rs.setVisitDate(visitDate);
            restaurantSchedulesFacade.create(rs);
            visitDate = null;
            restaurantId = 0;
            notice = NotificationTools.createSuccess("restaurant");
        } catch (Exception ex) {
            visitDate = null;
            restaurantId = 0;
            notice = NotificationTools.createFail("restaurant");
        }

    }

    public void scheduleAccommodation() {
        try {
            boolean isInvalid = false;
            if (accommodationVisitDate == null) {
                notice += NotificationTools.error("The visit date must be entered.");
                isInvalid = true;
            }
            if (accommodationVisitDate != null && accommodationVisitDate.before(new Date(System.currentTimeMillis()))) {
                notice += NotificationTools.error("The visit date must be more than today.");
                isInvalid = true;
            }
            if (isInvalid) {
                message = NotificationTools.openModal();
                return;
            }
            AccommodationSchedulesPK aspk = new AccommodationSchedulesPK(scheduledTour.getTourId(), accommodationId);
            AccommodationSchedules as = new AccommodationSchedules();
            as.setAccommodationSchedulesPK(aspk);
            as.setVisitDate(accommodationVisitDate);
            accommodationSchedulesFacade.create(as);
            accommodationVisitDate = null;
            accommodationId = 0;
            notice = NotificationTools.createSuccess("accommodation");
        } catch (Exception ex) {
            accommodationVisitDate = null;
            accommodationId = 0;
            notice = NotificationTools.createFail("accommodation");
        }

    }

    public void deleteAccommodationSchedule(int id) {
        try {
            AccommodationSchedulesPK aspk = new AccommodationSchedulesPK(scheduledTour.getTourId(), id);
            AccommodationSchedules as = accommodationSchedulesFacade.find(aspk);
            accommodationSchedulesFacade.remove(as);
            notice = NotificationTools.deleteSuccess("accommodation");
        } catch (Exception ex) {
            notice = NotificationTools.deleteFail("accommodation");
        }
    }

    public void deleteSchedule(int id) {
        try {
            RestaurantSchedulesPK restaurantSchedulesPK = new RestaurantSchedulesPK(scheduledTour.getTourId(), id);
            RestaurantSchedules rs = restaurantSchedulesFacade.find(restaurantSchedulesPK);
            restaurantSchedulesFacade.remove(rs);
            notice = NotificationTools.deleteSuccess("restaurant");
        } catch (Exception ex) {
            notice = NotificationTools.deleteFail("restaurant");
        }
    }

    public String showResName(int id) {
        return restaurantsFacade.find(id).getRestaurantName();
    }

    public String showResThumbnail(int id) {
        return restaurantsFacade.find(id).getThumbnail();
    }

    public String showAccName(int id) {
        return accommodationsFacade.find(id).getAccommodationName();
    }

    public String showAccThumbnail(int id) {
        return accommodationsFacade.find(id).getThumbnail();
    }

    public void setTours(Tours tours) {
        this.tours = tours;
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

    public int getPackageID() {
        return packageID;
    }

    public void setPackageID(int packageID) {
        this.packageID = packageID;
    }

    public boolean isTourStatus() {
        return tourStatus;
    }

    public void setTourStatus(boolean tourStatus) {
        this.tourStatus = tourStatus;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public Tours getScheduledTour() {
        return scheduledTour;
    }

    public void setScheduledTour(Tours scheduledTour) {
        this.scheduledTour = scheduledTour;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Date getAccommodationVisitDate() {
        return accommodationVisitDate;
    }

    public void setAccommodationVisitDate(Date accommodationVisitDate) {
        this.accommodationVisitDate = accommodationVisitDate;
    }

    public int getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(int accommodationId) {
        this.accommodationId = accommodationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
