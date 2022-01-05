package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**@author Avery Landseadal*/
public class Inventory
{
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    public static void updatePart (int index, Part part){
        allParts.set(index, part);
    }
    public static void updateProduct (int index, Product product){
        allProducts.set(index, product);
    }
    public static void deletePart(Part part) {
        allParts.remove(part);
    }
    public static void deleteProduct(Product product) {
        allProducts.remove(product);
    }
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public static int partID = 1;
    public static int productID = 1;


    /**
     *
     * @param part adds a part to the allparts inventory
     */
    public static void addPart(Part part) {
        allParts.add(part);
    }

    /**
     *
     * @param product adds a product to the all products inventory
     */

    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /**
     *
     * @param partID look up part by partID
     * @return returns the part if found
     */

    public static Part lookupPart(int partID) {
        for (Part part : allParts) {
            if (part.getId() == (partID)) {
                return part;
            }
        }
        return null;
    }

    /**
     *
     * @param productID looks up the product by the product ID
     * @return returns the product if found
     */

    public static Product lookupProduct(int productID) {
        for (Product product : allProducts) {
            if (product.getId() == (productID)) {
                return product;
            }
        }
        return null;
    }

    /**
     *
     * @param partName looks up part by the part name
     * @return returns the search results based on the part name
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> searchResults = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                searchResults.add(part);
            }
        }
        return searchResults;

    }
    /**
     *
     * @param productName looks up product by the product name
     * @return returns the results based on the search
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> searchResults = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getName().contains(productName)) {
                searchResults.add(product);
            }
        }
        return searchResults;

    }
}