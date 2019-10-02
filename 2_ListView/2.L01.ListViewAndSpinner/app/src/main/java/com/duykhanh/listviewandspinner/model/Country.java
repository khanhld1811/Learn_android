package com.duykhanh.listviewandspinner.model;

public class Country {
    private String mCountryName;
    private int mFlagImage;

    public Country(String countryName, int flagImage) {
        mCountryName = countryName;
        mFlagImage = flagImage;
    }

    public String getCountryName() {
        return mCountryName;
    }

    public int getFlagImage() {
        return mFlagImage;
    }
}
