/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nhom2_quanlyanphamtrongthuvien;

/**
 *
 * @author Tire
 */
public class Danhsachanpham {

    private String Id;
    private String Name;
    private String Type;
    private String Cost;
    private String Nxb;
    private String Author;
    private int Quantity;

    public Danhsachanpham(String Id, String Name, String Type, String Cost, String Nxb, String Author, int Quantity) {
        this.Id = Id;
        this.Name = Name;
        this.Type = Type;
        this.Cost = Cost;
        this.Nxb = Nxb;
        this.Author = Author;
        this.Quantity = Quantity;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }

    public String getCost() {
        return Cost;
    }

    public int getQuantity() {
        return Quantity;
    }

    public String getNxb() {
        return Nxb;
    }

    public String getAuthor() {
        return Author;
    }

}
