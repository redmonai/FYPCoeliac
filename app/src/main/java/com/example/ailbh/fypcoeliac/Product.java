package com.example.ailbh.fypcoeliac;

/**
 * Created by ailbh on 27/01/2018.
 */

public class Product {
    public String prodName;
    public String brand;
    public String category;
    public String type;
    public String size;

    public Product ()
    {

    }

    public Product (String prodName, String brand, String category, String type, String size)
    {
        this.prodName = prodName;
        this.brand = brand;
        this.category = category;
        this.type = type;
        this.size = size;
    }
}

