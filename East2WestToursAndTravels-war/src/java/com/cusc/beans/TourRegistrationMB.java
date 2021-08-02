/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.AccommodationSchedules;
import com.cusc.entities.DestinationSchedules;
import com.cusc.entities.RestaurantSchedules;
import com.cusc.entities.TourRegistration;
import com.cusc.helps.JavaMailUtil;
import com.cusc.helps.NotificationTools;
import com.cusc.sessionbean.AccommodationSchedulesFacadeLocal;
import com.cusc.sessionbean.DestinationSchedulesFacadeLocal;
import com.cusc.sessionbean.RestaurantSchedulesFacadeLocal;
import com.cusc.sessionbean.TourRegistrationFacadeLocal;
import com.cusc.sessionbean.ToursFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Admin
 */
@Named(value = "tourRegistrationMB")
@SessionScoped
public class TourRegistrationMB implements Serializable {

    @EJB
    private AccommodationSchedulesFacadeLocal accommodationSchedulesFacade;

    @EJB
    private RestaurantSchedulesFacadeLocal restaurantSchedulesFacade;

    @EJB
    private DestinationSchedulesFacadeLocal destinationSchedulesFacade;

    @EJB
    private ToursFacadeLocal toursFacade;

    @EJB
    private TourRegistrationFacadeLocal tourRegistrationFacade;

    private String notice = "";
    private TourRegistration tourRegistration;

    public TourRegistrationMB() {
    }

    public List<TourRegistration> showAll() {
        return tourRegistrationFacade.findAll();
    }

    public String toDateTime(Date date) {
        return date == null ? "" : date.getDate() + "/" + (date.getMonth() + 1) + "/" + (date.getYear() + 1900) + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
    }

    public String toDate(Date date) {
        return date == null ? "" : date.getDate() + "/" + (date.getMonth() + 1) + "/" + (date.getYear() + 1900);
    }

    public String detail(int id) {
        tourRegistration = tourRegistrationFacade.find(id);
        return "tourReservationInvoice";
    }

    public List<DestinationSchedules> showDestinationSchedules(int id) {
        return destinationSchedulesFacade.findDestinations(id);
    }

    public List<RestaurantSchedules> showRestaurantSchedules(int id) {
        return restaurantSchedulesFacade.findRestaurantsOfTour(id);
    }

    public List<AccommodationSchedules> showAccommodationSchedules(int id) {
        return accommodationSchedulesFacade.findAccommodationsOfTour(id);
    }

    public void updateStatus(int id, int status) {
        try {
            TourRegistration rg = tourRegistrationFacade.find(id);
            rg.setStatus((short) status);
            tourRegistrationFacade.edit(rg);
            tourRegistration = tourRegistrationFacade.find(id);
            if (status == 1) {
                JavaMailUtil.sendMail(rg.getCustomerId().getEmail(), "Tour Booking Response", JavaMailUtil.createApprovedTourRegistrationMessage(rg.getCustomerId().getLastName() + " " + rg.getCustomerId().getFirstName(), rg.getTourId().getTourName(), toDateTime(rg.getRegistrationDate()), toDate(rg.getTourId().getDepartureDate())));

            } else {
                JavaMailUtil.sendMail(rg.getCustomerId().getEmail(), "Tour Booking Response", JavaMailUtil.createCanceledTourRegistrationMessage(rg.getCustomerId().getLastName() + " " + rg.getCustomerId().getFirstName(), rg.getTourId().getTourName(), toDateTime(rg.getRegistrationDate()), toDate(rg.getTourId().getDepartureDate())));

            }
            notice = NotificationTools.updateSuccess("registration status");
        } catch (Exception ex) {
            notice = NotificationTools.info("The e-mail was not sent to customer.");
        }
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public TourRegistration getTourRegistration() {
        return tourRegistration;
    }

    public void setTourRegistration(TourRegistration tourRegistration) {
        this.tourRegistration = tourRegistration;
    }
}
