/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prj;

/**
 *
 * @author Tire
 */
public class Product {

    private int id;
    private String name;
    private String type;
    private int price;
    private int amount;
    private String nxb;
    private String author;

    public Product() {
    }

    public Product(int id, String name, String type, int price, int amount, String nxb, String author) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.amount = amount;
        this.nxb = nxb;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPrice() {
        return (price/1000)+"."+String.format("%03d",price%1000);
    }

    public int getAmount() {
        return amount;
    }

    public String getNxb() {
        return nxb;
    }

    public String getAuthor() {
        return author;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setNxb(String nxb) {
        this.nxb = nxb;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    
}
