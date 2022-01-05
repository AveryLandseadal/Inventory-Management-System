package model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** @author Avery Landseadal */
public class Product {

    /**
     * Contains all associated parts.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     *
     * @param id id for the product
     * @param name name of the product
     * @param price price of the product
     * @param stock inventory level of the product
     * @param min min amount of inventory
     * @param max amount of inventory
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }


    /**
     *
     * @param id sets productID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @param name sets the name of the product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param price sets the price of the product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     *
     * @param stock sets the stock of the product
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     *
     * @param min sets the min inventory of the product
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     *
     * @param max sets the max inventory of the product
     */
    public void setMax(int max) {
        this.max = max;
    }


    /**
     *
     * @return returns the product ID
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return returns the product name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return returns the product price
     */
    public double getPrice() {
        return price;
    }

    /**
     *
     * @return returns the inventory level of the product
     */
    public int getStock() {
        return stock;
    }

    /**
     *
     * @return returns the min inventory level of the product
     */
    public int getMin() {
        return min;
    }

    /**
     *
     * @return returns the max inventory level of the product
     */
    public int getMax() {
        return max;
    }

    /**
     *
     * @param part adds the accosted part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     *
     * @param part deletes the associated part
     */
    public void deleteAssociatedPart(Part part) {
        associatedParts.remove(part);
    }

    /**
     *
     * @return returns the associated parts
     */
    public  ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

}