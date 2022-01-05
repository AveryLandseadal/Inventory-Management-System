package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Avery Landseadal
 */
public class AddProductForm implements Initializable {

    Stage stage;
    Parent scene;

    /**
     * List that has all of the associated parts.
      */

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    /**
     *  Creates the add product class
      */

    @FXML
    private TextField productIDField;

    @FXML
    private TextField productNameField;

    @FXML
    private TextField inventoryField;

    @FXML
    private TextField productPriceField;

    @FXML
    private TextField productMaxField;

    @FXML
    private TextField productMinField;

    @FXML
    private TextField partSearchBox;

    @FXML
    private TableView<Part> productTable;

    @FXML
    private TableColumn<Part, Integer> productTablePartID;

    @FXML
    private TableColumn<Part, String> productPartsTablePartName;

    @FXML
    private TableColumn<Part, Integer> productInventoryLevel;

    @FXML
    private TableColumn<Part, Double> productPartsTablePrice;

    @FXML
    private TableView<Part> associatedPartsTable;

    @FXML
    private TableColumn<Part, Integer> associatedPartID;

    @FXML
    private TableColumn<Part, String> associatedName;

    @FXML
    private TableColumn<Part, Integer> associatedInventoryLevel;

    @FXML
    private TableColumn<Part, Double> associatedPrice;

    // This method starts the add product form
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        productIDField.setText(String.valueOf(Inventory.productID));

        productTable.setItems(Inventory.getAllParts());

        productTablePartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productPartsTablePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPartsTablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * adds the product and gives an error if needed.
     */
    @FXML
    void addProduct() {

        if (productTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("A PART NEEDS TO BE SELECTED IN ORDER TO ADD IT.");

            alert.showAndWait();
        }
        else
        {

            associatedParts.add(productTable.getSelectionModel().getSelectedItem());

            associatedPartsTable.setItems(associatedParts);

        }
    }
    /**
     *
     * @param event saves the current product
     * @throws IOException gives a conformation message or an error message
     */
    @FXML
    void saveProduct(ActionEvent event) throws IOException
    {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("The product will be saved");

        Optional<ButtonType> result = alert.showAndWait();



        if (result.isPresent() && result.get() == ButtonType.OK)


        {

            try {
                int id = Integer.parseInt(productIDField.getText());
                String name = productNameField.getText();
                double price = Double.parseDouble(productPriceField.getText());
                int stock = Integer.parseInt(inventoryField.getText());
                int min = Integer.parseInt(productMinField.getText());
                int max = Integer.parseInt(productMaxField.getText());

                if (min <= max) {

                    if ((min <= stock) && (stock <= max)) {

                        Product product = new Product(id, name, price, stock, min, max);
                        product.getAllAssociatedParts().addAll(associatedParts);
                        Inventory.addProduct(product);

                        /** product ID starts at 0 for some reason, FIX THIS. FIXED set public static int productID = 1 **/
                        Inventory.productID += 1;

                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();
                    } else {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setContentText("INVENTORY MUST BE EQUAL TO OR BETWEEN THE MIN AND MAX VALUES");
                        alert2.showAndWait();
                    }
                } else {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setContentText("THE MIN VALUE CANNOT EXCEED THE MAX VALUE!");
                    alert2.showAndWait();
                }
            }
            catch(NumberFormatException e)
            {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setContentText("THERE IS A TEXT FIELD WITH AN EMPTY VALUE");
                alert2.showAndWait();
            }

        }

    }

    /**
     *
     * @param event cancels the add product
     * @throws IOException gives a warning message when cancelling the add product
     */

    @FXML
    void cancelProduct(ActionEvent event) throws IOException
    {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Unsaved progress will be lost and you will return to the main screen.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();


        }

    }

    /**
     * searches the parts using a search bar, gives an error if nothing is found.
     */
    @FXML
    void onActionSearchParts() {

        String searchString = partSearchBox.getText();

        ObservableList<Part> searchResults = Inventory.lookupPart(searchString);

        if (searchResults.size() == 0)
        {
            try {
                Part p = Inventory.lookupPart(Integer.parseInt(searchString));
                if (p != null) {
                    searchResults.add(p);
                }
            }
            catch (NumberFormatException ex){
                ex.printStackTrace();
            }
        }

        if (searchResults.size() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("PART NOT FOUND.");
            alert.setContentText("No part was found using those search parameters.");

            alert.showAndWait();

            return;
        }

        productTable.setItems(searchResults);
    }

    /**
     * removes the associated part.
     */
    @FXML
    void removeAssociatedPart() {

        if (associatedPartsTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("A PART NEEDS TO BE SELECTED TO DELETE");

            alert.showAndWait();
        }
        else
        {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("This will remove the part, continue?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {


                associatedParts.remove(associatedPartsTable.getSelectionModel().getSelectedItem());

                associatedPartsTable.setItems(associatedParts);


                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setContentText("The part was removed");

                alert2.showAndWait();
            }
            else {
                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                alert3.setContentText("The part wasn't removed.");

                alert3.showAndWait();

            }

        }

    }

}