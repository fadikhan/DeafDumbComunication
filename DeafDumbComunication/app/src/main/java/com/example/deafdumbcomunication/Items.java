package com.example.deafdumbcomunication;

public class Items {

    private  String item_Name;
    private String item_Desc;
    private  String image_id;

    public Items()
    {

    }
    public Items(String item_Name, String image_id)
    {
        this.item_Name = item_Name;
        this.image_id = image_id;
    }
    public Items(String item_Name, String image_id , String item_Desc) {
        this.item_Name = item_Name;
        this.item_Desc = item_Desc;
        this.image_id = image_id;
    }

    public String getItem_Name() {
        return item_Name;
    }

    public void setItem_Name(String item_Name) {
        this.item_Name = item_Name;
    }

    public String getItem_Desc() {
        return item_Desc;
    }

    public void setItem_Desc(String item_Desc) {
        this.item_Desc = item_Desc;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }
}
