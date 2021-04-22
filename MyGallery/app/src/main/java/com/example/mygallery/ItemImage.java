package com.example.mygallery;

public class ItemImage {
     private String Path;
     private  String Date;


        public  ItemImage( String path, String Date)
        {
            this.Path=path;
            this.Date=Date;
        }

    public String getPath() {
        return Path;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setPath(String path) {
        Path = path;
    }
}
