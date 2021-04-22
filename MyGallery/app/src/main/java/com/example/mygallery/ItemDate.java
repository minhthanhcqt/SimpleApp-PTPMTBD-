package com.example.mygallery;

public class ItemDate {
    private GalleryAdapter galleryAdapter;
    private String  textdate;

    public  ItemDate(GalleryAdapter galleryAdapter, String textdate)
    {
        this.galleryAdapter=galleryAdapter;
        this.textdate=textdate;
    }

    public String getTextdate() {
        return textdate;
    }

    public GalleryAdapter getGalleryAdapter() {
        return galleryAdapter;
    }

    public void setTextdate(String textdate) {
        this.textdate = textdate;
    }

    public void setGalleryAdapter(GalleryAdapter galleryAdapter) {
        this.galleryAdapter = galleryAdapter;
    }
}
