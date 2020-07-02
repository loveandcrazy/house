package org.linlinjava.litemall.admin.dto;

import org.linlinjava.litemall.db.domain.House;
import org.linlinjava.litemall.db.domain.HouseAttribute;


public class HouseAllinone {
    House house;

    HouseAttribute[] attributes;

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public HouseAttribute[] getAttributes() {
        return attributes;
    }

    public void setAttributes(HouseAttribute[] attributes) {
        this.attributes = attributes;
    }

}
