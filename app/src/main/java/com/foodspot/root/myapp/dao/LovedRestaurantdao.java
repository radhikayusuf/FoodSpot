package com.foodspot.root.myapp.dao;

/**
 * Created by root on 22/08/16.
 */
public class LovedRestaurantdao {
    String id, Name, LatLang, link_img_profile, link_img_bg;

    public LovedRestaurantdao(String id, String name, String latLang, String link_img_profile, String link_img_bg) {
        this.id = id;
        Name = name;
        LatLang = latLang;
        this.link_img_profile = link_img_profile;
        this.link_img_bg = link_img_bg;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLatLang() {
        return LatLang;
    }

    public void setLatLang(String latLang) {
        LatLang = latLang;
    }

    public String getLink_img_profile() {
        return link_img_profile;
    }

    public void setLink_img_profile(String link_img_profile) {
        this.link_img_profile = link_img_profile;
    }

    public String getLink_img_bg() {
        return link_img_bg;
    }

    public void setLink_img_bg(String link_img_bg) {
        this.link_img_bg = link_img_bg;
    }
}
