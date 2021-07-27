/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.helps;

import com.cusc.entities.TourTypes;

/**
 *
 * @author Hoang Tam
 */
public class TourTypeCheckbox {
    private TourTypes tourType;
    private boolean status = false;

    public TourTypeCheckbox() {
        tourType = new TourTypes();
    }

    public TourTypes getTourType() {
        return tourType;
    }

    public void setTourType(TourTypes tourType) {
        this.tourType = tourType;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
