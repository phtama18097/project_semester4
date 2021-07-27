/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.AccommodationSchedules;
import com.cusc.entities.CarImages;
import com.cusc.entities.Cars;
import com.cusc.entities.DestinationImages;
import com.cusc.entities.DestinationSchedules;
import com.cusc.entities.Destinations;
import com.cusc.entities.RestaurantSchedules;
import com.cusc.entities.Tours;
import com.cusc.sessionbean.AccommodationSchedulesFacadeLocal;
import com.cusc.sessionbean.CarImagesFacadeLocal;
import com.cusc.sessionbean.CarModelsFacadeLocal;
import com.cusc.sessionbean.CarTypesFacadeLocal;
import com.cusc.sessionbean.CarsFacadeLocal;
import com.cusc.sessionbean.DestinationImagesFacadeLocal;
import com.cusc.sessionbean.DestinationSchedulesFacadeLocal;
import com.cusc.sessionbean.RestaurantSchedulesFacadeLocal;
import com.cusc.sessionbean.TourTypesFacadeLocal;
import com.cusc.sessionbean.ToursFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;

/**
 *
 * @author Admin
 */
@Named(value = "businessMB")
@SessionScoped
public class BusinessMB implements Serializable {

    @EJB
    private CarImagesFacadeLocal carImagesFacade;

    @EJB
    private CarModelsFacadeLocal carModelsFacade;

    @EJB
    private CarTypesFacadeLocal carTypesFacade;

    @EJB
    private CarsFacadeLocal carsFacade;

    @EJB
    private AccommodationSchedulesFacadeLocal accommodationSchedulesFacade;

    @EJB
    private RestaurantSchedulesFacadeLocal restaurantSchedulesFacade;

    @EJB
    private DestinationImagesFacadeLocal destinationImagesFacade;

    @EJB
    private ToursFacadeLocal toursFacade;

    @EJB
    private TourTypesFacadeLocal tourTypesFacade;

    @EJB
    private DestinationSchedulesFacadeLocal destinationSchedulesFacade;
    
    private static final int QUANTITY_IN_ONE_PAGE = 8;      // Quantity of items in each page

    /*
        Parameter of Tour
    */
    private List<Tours> listTours = null;                   // Tour list by destination ID
    private Tours selectedTour = null;                      // Tour detail
    private List<Destinations> tourImages = null;           // Image list of tour detail
    private Destinations tourImage = null;                  // Main image of tour detail
    private List<DestinationSchedules> desSchedules = null; // Destinations list of tour detail
    private List<RestaurantSchedules> resSchedules = null;  // Restaurants list of tour detail
    private List<AccommodationSchedules> accSchedules = null;  // Accommodation list of tour detail
    private int currentTourType ;
    private int currentPage;
    /*
        Parameter of Cars
    */
    private List<Cars> listCars = null;                     // Tour list by car
    private Cars selectedCar = null;                        // Car detail
    private List<CarImages> carImages = null;               // Image list of car detail
    private CarImages carImage = null;                      // Main Image of car detail
    private int currentCarType = 0;                         // current car type when user choose
    private int currentCarModel = 0;                        // current car model when user choose
    private int currentCarPage;                             // current page when user choose
    

    public BusinessMB() {
    }

    public int countToursByDestination(int typeId) {
        return destinationSchedulesFacade.countToursByDestinationType(typeId);
    }
    
    public int countCars(int typeId, int modelId) {
        if(typeId > 0 && modelId == 0){
            return carsFacade.countNewCarsByType(typeId);
        } else if(typeId == 0 && modelId > 0){
            return carsFacade.countNewCarsByModel(modelId);
        } else if(typeId > 0 && modelId > 0){
            return 0;
        } else {
            return carsFacade.countNewCars();
        }
    }

    public String showToursByDestination(int typeId, int page) {
        listTours = null;
        ArrayList<Tours> list = new ArrayList<>(destinationSchedulesFacade.findToursByDestinationType(typeId, QUANTITY_IN_ONE_PAGE, page));
        listTours = removeDuplicates(list);
        setCurrentTourType(typeId);
        setCurrentPage(page);
        return "tours";
    }
    
    public String showCars(int typeId, int modelId, int page){
        listCars = null;
        
        if(typeId > 0 && modelId == 0){
            listCars = carsFacade.findNewCarsByType(typeId, QUANTITY_IN_ONE_PAGE, page);
        } else if(typeId == 0 && modelId > 0){
            listCars = carsFacade.findNewCarsByModel(modelId, QUANTITY_IN_ONE_PAGE, page);
        } else if(typeId > 0 && modelId > 0){
            return "homepage";
        } else {
            listCars = carsFacade.findNewCars(QUANTITY_IN_ONE_PAGE, page);
        }
        setCurrentCarType(typeId);
        setCurrentCarModel(modelId);
        setCurrentCarPage(page);
        return "cars";
    }
    
    public String findTourTypeName(int id){
        return  tourTypesFacade.find(id).getTypeName();
    }
    
    public String findCarTypeOrModelName(int typeId, int modelId){
        if(typeId > 0 && modelId == 0){
            return carTypesFacade.find(typeId).getDescription();
        } else if(typeId == 0 && modelId > 0){
            return carModelsFacade.find(modelId).getModelName();
        } else if(typeId > 0 && modelId > 0){
            return "Cars";
        } else {
            return "";
        }
    }
    
    public int totalTourPage(){
        return (int)Math.ceil((float)destinationSchedulesFacade.countToursByDestinationType(currentTourType) / (float)QUANTITY_IN_ONE_PAGE);
    }
    
    public int totalCarPage(){
        if(currentCarType > 0 && currentCarModel == 0){
            return (int)Math.ceil((float)carsFacade.countNewCarsByType(currentCarType) / (float)QUANTITY_IN_ONE_PAGE);
        } else if(currentCarType == 0 && currentCarModel > 0){
            return (int)Math.ceil((float)carsFacade.countNewCarsByModel(currentCarModel) / (float)QUANTITY_IN_ONE_PAGE);
        } else if(currentCarType > 0 && currentCarModel > 0){
            return 0;
        } else {
            return (int)Math.ceil((float)carsFacade.countNewCars() / (float)QUANTITY_IN_ONE_PAGE);
        }  
    }
    
    public String detailTour(int id) {
        resetTour();
        selectedTour = toursFacade.find(id);
        desSchedules = destinationSchedulesFacade.findDestinations(selectedTour.getPackageId().getPackageId());
        resSchedules = restaurantSchedulesFacade.findRestaurantsOfTour(selectedTour.getTourId());
        accSchedules = accommodationSchedulesFacade.findAccommodationsOfTour(selectedTour.getTourId());
        List<DestinationSchedules> schedule = destinationSchedulesFacade.find4Schedules(selectedTour.getPackageId().getPackageId());
        tourImages = new ArrayList<>();
        if (schedule.size() > 0) {
            for (DestinationSchedules item : schedule) {
                tourImages.add(item.getDestinations());
            }
            tourImage = schedule.get(0).getDestinations();
        }
        return "tourDetails";
    }
    
    public String detailCar(int id){
        selectedCar = null;
        selectedCar = carsFacade.find(id);
        carImages = carImagesFacade.find4Images(id);
        carImage = carImages.size() > 0 ? carImages.get(0) : null;
        return "carDetails";
    }
    
    public List<DestinationImages> showDestinationImages(int id){
        return destinationImagesFacade.findImagesByDestination(id);
    }
        
    private void resetTour(){
        selectedTour = null;
        tourImages = null;
        tourImage = null;
        desSchedules = null;
        resSchedules = null;
        accSchedules = null;
    }
    
    private <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {
        Set<T> set = new LinkedHashSet<>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
  
        return list;
    }

    public List<Tours> getListTours() {
        return listTours;
    }

    public void setListTours(List<Tours> listTours) {
        this.listTours = listTours;
    }

    public Tours getSelectedTour() {
        return selectedTour;
    }

    public void setSelectedTour(Tours selectedTour) {
        this.selectedTour = selectedTour;
    }

    public List<Destinations> getTourImages() {
        return tourImages;
    }

    public void setTourImages(List<Destinations> tourImages) {
        this.tourImages = tourImages;
    }

    public Destinations getTourImage() {
        return tourImage;
    }

    public void setTourImage(Destinations tourImage) {
        this.tourImage = tourImage;
    }

    public List<DestinationSchedules> getDesSchedules() {
        return desSchedules;
    }

    public void setDesSchedules(List<DestinationSchedules> desSchedules) {
        this.desSchedules = desSchedules;
    }

    public List<RestaurantSchedules> getResSchedules() {
        return resSchedules;
    }

    public void setResSchedules(List<RestaurantSchedules> resSchedules) {
        this.resSchedules = resSchedules;
    }

    public List<AccommodationSchedules> getAccSchedules() {
        return accSchedules;
    }

    public void setAccSchedules(List<AccommodationSchedules> accSchedules) {
        this.accSchedules = accSchedules;
    }

    public int getCurrentTourType() {
        return currentTourType;
    }

    public void setCurrentTourType(int currentTourType) {
        this.currentTourType = currentTourType;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<Cars> getListCars() {
        return listCars;
    }

    public void setListCars(List<Cars> listCars) {
        this.listCars = listCars;
    }

    public Cars getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(Cars selectedCar) {
        this.selectedCar = selectedCar;
    }

    public List<CarImages> getCarImages() {
        return carImages;
    }

    public void setCarImages(List<CarImages> carImages) {
        this.carImages = carImages;
    }

    public int getCurrentCarType() {
        return currentCarType;
    }

    public void setCurrentCarType(int currentCarType) {
        this.currentCarType = currentCarType;
    }

    public int getCurrentCarModel() {
        return currentCarModel;
    }

    public void setCurrentCarModel(int currentCarModel) {
        this.currentCarModel = currentCarModel;
    }

    public int getCurrentCarPage() {
        return currentCarPage;
    }

    public void setCurrentCarPage(int currentCarPage) {
        this.currentCarPage = currentCarPage;
    }

    public CarImages getCarImage() {
        return carImage;
    }

    public void setCarImage(CarImages carImage) {
        this.carImage = carImage;
    }


}
