package com.example.demo.models;


public class Products {


    private int productId;
    private String productName;
    private int productQuantity;

    public Products(){

    }

    public Products(int productId, String productName, int productQuantity){
        this.productId = productId;
        this.productName = productName;
        this.productQuantity = productQuantity;
    }

    public int getProductId(){
        return productId;
    }

    public void  setProductId(int productId){
        this.productId = productId;
    }

    public String getProductName(){
        return productName;
    }

    public void setProductName(String productName){
        this.productName = productName;
    }

    public int getProductQuantity(){
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity){
        this.productQuantity = productQuantity;
    }

    
}





