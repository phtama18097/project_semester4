/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.AccommodationSchedules;
import com.cusc.entities.CarRegistration;
import com.cusc.entities.CarRegistrationDetails;
import com.cusc.entities.CarRegistrationDetailsPK;
import com.cusc.entities.Cars;
import com.cusc.entities.Customers;
import com.cusc.entities.DestinationSchedules;
import com.cusc.entities.PaymentMethods;
import com.cusc.entities.RestaurantSchedules;
import com.cusc.entities.TourRegistration;
import com.cusc.entities.Tours;
import com.cusc.helps.JavaMailUtil;
import com.cusc.helps.NotificationTools;
import com.cusc.sessionbean.AccommodationSchedulesFacadeLocal;
import com.cusc.sessionbean.CarRegistrationDetailsFacadeLocal;
import com.cusc.sessionbean.CarRegistrationFacadeLocal;
import com.cusc.sessionbean.CarsFacadeLocal;
import com.cusc.sessionbean.CustomersFacadeLocal;
import com.cusc.sessionbean.DestinationSchedulesFacadeLocal;
import com.cusc.sessionbean.PaymentMethodsFacadeLocal;
import com.cusc.sessionbean.RestaurantSchedulesFacadeLocal;
import com.cusc.sessionbean.TourRegistrationFacadeLocal;
import com.cusc.sessionbean.ToursFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author Admin
 */
@Named(value = "cartMB")
@SessionScoped
public class CartMB implements Serializable {

    @EJB
    private CarsFacadeLocal carsFacade;

    @EJB
    private RestaurantSchedulesFacadeLocal restaurantSchedulesFacade;

    @EJB
    private AccommodationSchedulesFacadeLocal accommodationSchedulesFacade;

    @EJB
    private DestinationSchedulesFacadeLocal destinationSchedulesFacade;

    @EJB
    private CarRegistrationDetailsFacadeLocal carRegistrationDetailsFacade;

    @EJB
    private CarRegistrationFacadeLocal carRegistrationFacade;

    @EJB
    private ToursFacadeLocal toursFacade;

    @EJB
    private TourRegistrationFacadeLocal tourRegistrationFacade;

    @EJB
    private CustomersFacadeLocal customersFacade;

    @EJB
    private PaymentMethodsFacadeLocal paymentMethodsFacade;

    @Inject
    private CustomerLoginMB customerLoginMB;

    private int selectedPayment;
    private String note = "";
    private String notice = "";

    /**
     * Parameter of tour cart
     */
    private List<TourRegistration> tourCart = null;
    private int tourQty = 1;

    /**
     * Parameter of car cart
     */
    private List<CarRegistrationDetails> carCart = null;
    private int carQty = 1;

    /**
     * Parameter of car registration detail
     */
    private CarRegistration regisCar;
    private TourRegistration regisTour;
    private List<CarRegistrationDetails> carDetailList;

    public CartMB() {
    }

    public void addTourToCart(Tours tour) {
        int quantity = 1;
        if (tourQty > 1) {
            quantity = tourQty;
        }
        if (tourCart == null) {
            tourCart = new ArrayList<>();
            TourRegistration od = new TourRegistration();
            od.setTourId(tour);
            od.setQuantity(quantity);
            tourCart.add(od);
        } else {
            boolean isNewProduct = true;
            for (TourRegistration item : tourCart) {
                if (Objects.equals(item.getTourId().getTourId(), tour.getTourId())) {
                    item.setQuantity(item.getQuantity() + quantity);
                    isNewProduct = false;
                }
            }
            if (isNewProduct) {
                TourRegistration od = new TourRegistration();
                od.setTourId(tour);
                od.setQuantity(quantity);
                tourCart.add(od);
            }
        }

        notice = NotificationTools.success(tour.getTourName() + " has been added to your cart.");
        setTourQty(1);
    }

    public void addCarToCart(Cars car) {
        int quantity = 1;
        if (carQty > 1) {
            quantity = carQty;
        }
        if (carCart == null) {
            carCart = new ArrayList<>();
            CarRegistrationDetails od = new CarRegistrationDetails();
            od.setCars(car);
            od.setQuantity(quantity);
            carCart.add(od);
            System.out.println("Add " + car.getCarName() + " to your cart!");
        } else {
            boolean isNewProduct = true;
            for (CarRegistrationDetails item : carCart) {
                if (Objects.equals(item.getCars().getCarId(), car.getCarId())) {
                    item.setQuantity(item.getQuantity() + quantity);
                    isNewProduct = false;
                }
            }
            if (isNewProduct) {
                CarRegistrationDetails od = new CarRegistrationDetails();
                od.setCars(car);
                od.setQuantity(quantity);
                carCart.add(od);
            }
        }

        notice = NotificationTools.success(car.getCarName() + " has been added to your cart.");
        setCarQty(1);
    }

    public void updateTourCart() {
        setTourCart(tourCart);
        setCarCart(carCart);
    }

    public void removeItem(Tours tour) {
        for (int i = 0; i < tourCart.size(); i++) {
            if (tourCart.get(i).getTourId().getTourId() == tour.getTourId()) {
                tourCart.remove(i);
            }
        }
        notice = NotificationTools.info(tour.getTourName() + " has been removed.");
    }

    public void removeCarItem(Cars car) {
        for (int i = 0; i < carCart.size(); i++) {
            if (carCart.get(i).getCars().getCarId() == car.getCarId()) {
                carCart.remove(i);
            }
        }
        notice = NotificationTools.info(car.getCarName() + " has been removed.");
    }

    public String removeAllCart() {
        setTourCart(null);
        setCarCart(null);
        notice = NotificationTools.info("Your cart does not any products");
        return "homepage";
    }

    public int findQuantityItemsInCart() {
        int quantity = 0;
        if (tourCart == null && carCart == null) {
            return 0;
        } else {
            if (carCart != null) {
                for (CarRegistrationDetails item : carCart) {
                    quantity += item.getQuantity();
                }
            }
            if (tourCart != null) {
                for (TourRegistration item : tourCart) {
                    quantity += item.getQuantity();
                }
            }
            return quantity;
        }
    }

    public long totalCart() {
        long total = 0;
        if (tourCart == null && carCart == null) {
            return 0;
        } else {
            if (carCart != null) {
                for (CarRegistrationDetails item : carCart) {
                    total += (item.getQuantity() * item.getCars().getUnitPrice().longValue());
                }
            }
            if (tourCart != null) {
                for (TourRegistration item : tourCart) {
                    total += (item.getQuantity() * item.getTourId().getUnitPrice().longValue());
                }
            }
            return total;
        }
    }

    public List<PaymentMethods> showActivePaymentMethods() {
        return paymentMethodsFacade.findActivePaymentMethods();
    }

    public String placeOrder() {
        if (tourCart == null && carCart == null) {
            return "homepage";
        }
        Customers user = customersFacade.find(customerLoginMB.getCusSignedIn().getCustomerId());
        PaymentMethods payment = paymentMethodsFacade.find(selectedPayment);

        if (tourCart != null && tourCart.size() > 0) {
            for (TourRegistration item : tourCart) {
                TourRegistration order = new TourRegistration();
                order.setCustomerId(user);
                order.setTourId(toursFacade.find(item.getTourId().getTourId()));
                order.setRegistrationDate(new Date(System.currentTimeMillis()));
                order.setTotal(BigInteger.valueOf(item.getQuantity() * item.getTourId().getUnitPrice().longValue()));
                order.setNote(note);
                order.setMethodId(payment);
                order.setStatus((short) 0);
                order.setQuantity(item.getQuantity());
                tourRegistrationFacade.create(order);
            }
        }

        if (carCart != null && carCart.size() > 0) {
            CarRegistration carRegistration = new CarRegistration();
            carRegistration.setCustomerId(user);
            carRegistration.setMethodId(payment);
            carRegistration.setNote(note);
            carRegistration.setRegistrationDate(new Date(System.currentTimeMillis()));
            carRegistration.setStatus((short) 0);
            carRegistrationFacade.create(carRegistration);
            long total = 0;

            for (CarRegistrationDetails item : carCart) {
                CarRegistrationDetailsPK crdpk = new CarRegistrationDetailsPK(carRegistration.getRegistrationId(), item.getCars().getCarId());
                CarRegistrationDetails registrationDetails = new CarRegistrationDetails();
                registrationDetails.setCarRegistrationDetailsPK(crdpk);
                registrationDetails.setQuantity(item.getQuantity());
                registrationDetails.setUnitPrice(item.getCars().getUnitPrice());
                carRegistrationDetailsFacade.create(registrationDetails);
                total += (item.getQuantity() * item.getCars().getUnitPrice().longValue());
            }
            CarRegistration carUpdate = carRegistrationFacade.find(carRegistration.getRegistrationId());
            carUpdate.setTotal(BigInteger.valueOf(total));
            carRegistrationFacade.edit(carUpdate);
        }

        JavaMailUtil.sendMail(user.getEmail(), "Placed order successfully", JavaMailUtil.createPlacedOrderMessage(user.getLastName() + " " + user.getFirstName(),
                tourCart != null ? tourCart : new ArrayList<TourRegistration>(), carCart != null ? carCart : new ArrayList<CarRegistrationDetails>()));

        setTourCart(null);
        setCarCart(null);
        setSelectedPayment(0);
        setNote("");
        notice = NotificationTools.success("Congratulation! You have placed order successfully.");
        return "homepage";
    }

    public List<TourRegistration> showTourRegistrationHistory() {
        return customerLoginMB.getCusSignedIn() != null ? tourRegistrationFacade.findHistory(customerLoginMB.getCusSignedIn().getCustomerId()) : new ArrayList<>();
    }

    public List<CarRegistration> showCarRegistrationHistory() {
        return customerLoginMB.getCusSignedIn() != null ? carRegistrationFacade.findHistory(customerLoginMB.getCusSignedIn().getCustomerId()) : new ArrayList<>();
    }

    public String viewRegistrationCarDetail(int registrationId) {
        carDetailList = carRegistrationDetailsFacade.findDetailsByRegistration(registrationId);
        setRegisCar(carRegistrationFacade.find(registrationId));
        return "carRegistrationDetail";

    }

    public String viewTourRegistrationDetail(int registrationId) {
        setRegisTour(tourRegistrationFacade.find(registrationId));
        return "tourRegistrationDetail";
    }
    
    public String showCarName(int carId){
        return carsFacade.find(carId).getCarName();
    }
    
    public String showCarLicencePlate(int carId){
        return carsFacade.find(carId).getLicencePlate();
    }
    
    public String showCarThumbnail(int carId){
        return carsFacade.find(carId).getThumbnail();
    }

    public List<DestinationSchedules> showDestinationSchedules() {
        return destinationSchedulesFacade.findDestinations(regisTour.getTourId().getPackageId().getPackageId());
    }

    public List<RestaurantSchedules> showRestaurantSchedules() {
        return restaurantSchedulesFacade.findRestaurantsOfTour(regisTour.getTourId().getTourId());
    }

    public List<AccommodationSchedules> showAccommodationSchedules() {
        return accommodationSchedulesFacade.findAccommodationsOfTour(regisTour.getTourId().getTourId());
    }

//    public List<CarRegistrationDetails> showCarRegistrationDetail() {
//        return carRegistrationDetailsFacade.findDetailsByRegistration(regisCar.getRegistrationId());
//    }
    
    public String convertDate(Date date) {
        return date == null ? "" : date.getDate() + "/" + (date.getMonth() + 1) + "/" + (date.getYear() + 1900) + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
    }

    public String toDate(Date date) {
        return date == null ? "" : date.getDate() + "/" + (date.getMonth() + 1) + "/" + (date.getYear() + 1900);
    }

    public List<TourRegistration> getTourCart() {
        return tourCart;
    }

    public void setTourCart(List<TourRegistration> tourCart) {
        this.tourCart = tourCart;
    }

    public int getTourQty() {
        return tourQty;
    }

    public void setTourQty(int tourQty) {
        this.tourQty = tourQty;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public int getSelectedPayment() {
        return selectedPayment;
    }

    public void setSelectedPayment(int selectedPayment) {
        this.selectedPayment = selectedPayment;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<CarRegistrationDetails> getCarCart() {
        return carCart;
    }

    public void setCarCart(List<CarRegistrationDetails> carCart) {
        this.carCart = carCart;
    }

    public int getCarQty() {
        return carQty;
    }

    public void setCarQty(int carQty) {
        this.carQty = carQty;
    }

    public CarRegistration getRegisCar() {
        return regisCar;
    }

    public void setRegisCar(CarRegistration regisCar) {
        this.regisCar = regisCar;
    }

    public TourRegistration getRegisTour() {
        return regisTour;
    }

    public void setRegisTour(TourRegistration regisTour) {
        this.regisTour = regisTour;
    }

    public List<CarRegistrationDetails> getCarDetailList() {
        return carDetailList;
    }

    public void setCarDetailList(List<CarRegistrationDetails> carDetailList) {
        this.carDetailList = carDetailList;
    }


}
