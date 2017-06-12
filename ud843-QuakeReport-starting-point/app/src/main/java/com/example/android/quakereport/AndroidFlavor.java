package com.example.android.quakereport;

import java.util.Date;

/**
 * Created by user on 6/8/2017.
 */

public class AndroidFlavor {
    private String mCity;
    private Double mScale;
    private long  mTimeInMilliseconds;
    public AndroidFlavor(Double vScale,String vCity,long timeInMilliseconds )
    {
        mScale=vScale;
        mCity=vCity;
        mTimeInMilliseconds = timeInMilliseconds;
    }
    public String getCity()
    {
        return mCity;
    }
    public Double getScale()
    {
        return mScale;
    }
    public long getDate()
    {
        return  mTimeInMilliseconds;
    }

}
