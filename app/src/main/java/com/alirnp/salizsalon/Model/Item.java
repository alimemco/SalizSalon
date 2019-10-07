package com.alirnp.salizsalon.Model;

public class Item {

    private int ID;
    private String title;
    private int image;


    public Item(int ID, String title, int image) {
        this.ID = ID;
        this.title = title;
        this.image = image;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
