package com.example.mygallery;

import androidx.annotation.NonNull;

public class ItemFace {
    private int faceID;
    private String  numimage;

    public  ItemFace( int faceID, String numimage)
    {
        this.faceID=faceID;
        this.numimage=numimage;
    }
    public int getFaceID() {
        return this.faceID;
    }

    public void setFaceID(int faceID) {
        this.faceID = faceID;
    }

    public String getNumimage() {
        return numimage;
    }

    public void setNumimage(String numimage) {
        this.numimage = numimage;
    }

}
