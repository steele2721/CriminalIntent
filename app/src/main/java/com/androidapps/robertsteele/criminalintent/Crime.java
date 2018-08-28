package com.androidapps.robertsteele.criminalintent;

import java.util.Date;
import java.util.UUID;

public class Crime {

    private UUID mId;
    private String mtitle;
    private Date mDate;
    private boolean mSolved;
    private String mSuspect;

    public String getmSuspect() {
        return mSuspect;
    }

    public void setmSuspect(String mSuspect) {
        this.mSuspect = mSuspect;
    }

    public UUID getmId() {
        return mId;
    }

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean ismSolved() {
        return mSolved;
    }

    public void setmSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    public Crime(UUID uuid){
        mId = uuid;
    }
    public Crime() {
        this(UUID.randomUUID());
        mDate = new Date();
    }

    public String getPhotoFilename() {
        return "IMG_" + getmId().toString() + ".jpg";
    }
}