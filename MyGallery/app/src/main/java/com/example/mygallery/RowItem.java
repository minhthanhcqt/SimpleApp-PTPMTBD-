package com.example.mygallery;

public class RowItem {
        private ItemImage imageId;
        private String title;
        private String desc;

        public RowItem(ItemImage imageId, String title, String desc) {
            this.imageId = imageId;
            this.title = title;
            this.desc = desc;
        }

    public ItemImage getImageId() {
        return imageId;
    }

    public void setImageId(ItemImage imageId) {
        this.imageId = imageId;
    }

    public String getDesc() {
            return desc;
        }
        public void setDesc(String desc) {
            this.desc = desc;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        @Override
        public String toString() {
            return title + "\n" + desc;
        }
}
