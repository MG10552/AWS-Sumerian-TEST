package s10552.shoppinglist.entities;

public class Product {

    private int id;
    private int quantity;
    private double price;
    private String name;
    private int bought;

    public int getBought() {
        return bought;
    }

    public void setBought(int bought) {
        this.bought = bought;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}