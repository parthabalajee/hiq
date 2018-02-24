package com.healthsim.dataobjects;

import java.util.Date;

public class UserProfile {

    public static final int GLYCATION_MARK = 130;
    public static final int MINIMUM_SUGAR = 80;
    private final int userID;
    private int bloodSugar;
    private int glycation;
    private Date lastFoodTime;
    private Date lastExerciseTime;

    public UserProfile(int userID,int bloodSugar) {
        this.userID = userID;
        this.bloodSugar = bloodSugar;
    }

    public int getUserID() {
        return userID;
    }

    public int updateBloodSugar(int inc){
        if(bloodSugar <= MINIMUM_SUGAR){
            //no - op
            return this.bloodSugar;
        }
        this.bloodSugar+=inc;
        if(this.bloodSugar > GLYCATION_MARK )
            this.glycation+=5;
        return this.bloodSugar;
    }
    public int getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(int bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public int getGlycation() {
        return glycation;
    }

    public void setGlycation(int glycation) {
        this.glycation = glycation;
    }

    public Date getLastFoodTime() {
        return lastFoodTime;
    }

    public void setLastFoodTime(Date lastFoodTime) {
        this.lastFoodTime = lastFoodTime;
    }

    public Date getLastExerciseTime() {
        return lastExerciseTime;
    }

    public void setLastExerciseTime(Date lastExerciseTime) {
        this.lastExerciseTime = lastExerciseTime;
    }
}
