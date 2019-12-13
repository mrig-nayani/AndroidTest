package com.androidtest;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;

/**
 * Created by Mrignayani on 20/8/15.
 */
public class AppData {

    private SharedPreferences pref;
    private Context mCtx;

    public AppData(Context ctx) {
        mCtx = ctx;
        pref = ctx.getSharedPreferences(ctx.getPackageName(), Context.MODE_PRIVATE);
    }

    public void setclearAllData(){
          pref.edit().clear().apply();
    }

    public String getStoreArray(){
        return pref.getString("storeArray", "[]");
    }

    public void setStoreArray(String storeArray){
        pref.edit().putString("storeArray", storeArray).apply();
    }
    public String getCurrentUserName(){
        return pref.getString("fullName","");
    }
    public void setCurrentUserName(String fullName){
        pref.edit().putString("fullName",fullName).apply();
    }

    public int getCurrentNoOfAddedPatients(){
        return pref.getInt("addedPatients",0);
    }
    public void setCurrentNoOfAddedPatients(int addedPatients){
        pref.edit().putInt("addedPatients",addedPatients).apply();
    }

    public int getMaxNoOfPatientsAllowed(){
        return pref.getInt("maxNoOfPatients",0);
    }
    public void setMaxNoOfPatientsAllowed(int maxNoOfPatients){
        pref.edit().putInt("maxNoOfPatients",maxNoOfPatients).apply();
    }
}


