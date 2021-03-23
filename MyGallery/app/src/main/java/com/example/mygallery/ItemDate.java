package com.example.mygallery;

public class ItemDate {
    private GridAdapter imageAdapter;
    private String  textdate;

    public  ItemDate( GridAdapter imageAdapter, String textdate)
    {
        this.imageAdapter=imageAdapter;
        this.textdate=textdate;
    }

    public GridAdapter getImageAdapter() {
        return imageAdapter;
    }

    public void setImageAdapter(GridAdapter imageAdapter) {
        this.imageAdapter = imageAdapter;
    }

    public String getTextdate() {
        return textdate;
    }

    public void setTextdate(String textdate) {
        this.textdate = textdate;
    }
}
