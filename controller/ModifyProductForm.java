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
 *
 * @author Avery Landseadal*/
public class ModifyProductForm implements Initializable {

    Stage stage;
    Parent scene;

    //List containing the associated parts
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @FXML
    private TextField modifyProductIDField;

    @FXML
    private TextField modifyProductNameField;

    @FXML
    private TextField modifyProductInventoryField;

    @FXML
    private TextField modifyProductPriceField;

    @FXML
    private TextField modifyProductMaxField;

    @FXML
    private TextField modifyProductMinField;

    @FXML
    private TextField partSearchBox;

    @FXML
    private TableView<Part> modifyProductPartsTable;

    @FXML
    private TableColumn<Part, Integer> modifyProductPartsTablePartID;

    @FXML
    private TableColumn<Part, String> modifyProductPartsTablePartName;

    @FXML
    private TableColumn<Part, Integer> modifyProductPartsTablePartInventoryLevel;

    @FXML
    private TableColumn<Part, Double> modifyProductPartsTablePartPrice;

    @FXML
    private TableView<Part> associatedPartsTable;

    @FXML
    private TableColumn<Part, Integer> associatedPartsTablePartID;

    @FXML
    private TableColumn<Part, String> associatedPartsTablePartName;

    @FXML
    private TableColumn<Part, Integer> associatedPartsTablePartInventoryLevel;

    @FXML
    private TableColumn<Part, Double> associatedPartsTablePartPrice;

    // Starts Modify product form
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {



        modifyProductPartsTablePartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductPartsTablePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductPartsTablePartInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductPartsTablePartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        modifyProductPartsTable.setItems(Inventory.getAllParts());



        associatedPartsTablePartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartsTablePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartsTablePartInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartsTablePartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTable.setItems(associatedParts);
    }

    /**
     *
     * @param event cancels the modification of the product
     * @throws IOException gives a conformation
     */

    @FXML
    void cancelModify(ActionEvent event) throws IOException
    {


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("THIS WILL CANCEL THE MODIFY");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();


        }

    }
    /**
     *
     * @param event modifies the product
     * @throws IOException gives a confirmation to save the product and errors if needed
     */
    @FXML
    void modifyProduct(ActionEvent event) throws IOException
    {


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Do you wish the save the product?");

        Optional<ButtonType> result = alert.showAndWait();



        if (result.isPresent() && result.get() == ButtonType.OK)


        {

            try {
                int id = Integer.parseInt(modifyProductIDField.getText());
                String name = modifyProductNameField.getText();
                double price = Double.parseDouble(modifyProductPriceField.getText());
                int stock = Integer.parseInt(modifyProductInventoryField.getText());
                int min = Integer.parseInt(modifyProductMinField.getText());
                int max = Integer.parseInt(modifyProductMaxField.getText());

                int index = Inventory.getAllProducts().indexOf(this.product);

                if (min <= max) {

                    if ((min <= stock) && (stock <= max)) {
                        Product product = new Product(id, name, price, stock, min, max);
                        product.getAllAssociatedParts().addAll(associatedParts);
                        Inventory.updateProduct(index, product);

                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();
                    } else {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setContentText("INVENTORY MUST BE BETWEEN MIX AND MAX");
                        alert2.showAndWait();
                    }
                }
                else
                {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setContentText("MAX VALUE CANNOT BE LOWER THAN MIN");
                    alert2.showAndWait();
                }
            }
            catch(NumberFormatException e)
            {

                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setContentText("A INVALID VALUE HAS BEEN ENTERED");
                alert2.showAndWait();

            }


        }
    }
    Product product;

    /**
     *
     * @param product sends modified product to the product table
     */

    public void sendProduct(Product product)
    {
        this.product = product;

        modifyProductIDField.setText(Integer.toString(product.getId()));
        modifyProductNameField.setText(product.getName());
        modifyProductInventoryField.setText(Integer.toString(product.getStock()));
        modifyProductPriceField.setText(Double.toString(product.getPrice()));
        modifyProductMaxField.setText(Integer.toString(product.getMax()));
        modifyProductMinField.setText(Integer.toString(product.getMin()));

        associatedParts.addAll(product.getAllAssociatedParts());

    }

    /**
     * adds the part and gives an error if no product was selected
     */

    @FXML
    void addPart() {

        if (modifyProductPartsTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("NO PART WAS SELECTED");

            alert.showAndWait();
        }
        else
        {

            product.addAssociatedPart(modifyProductPartsTable.getSelectionModel().getSelectedItem());

            associatedParts.add(modifyProductPartsTable.getSelectionModel().getSelectedItem());
            associatedPartsTable.setItems(associatedParts);

        }

    }

    /**
     * removes the associated part
     */
    @FXML
    void removedAssociatedPart()
    {

        if (associatedPartsTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("NO PART WAS SELECTED");

            alert.showAndWait();
        }
        else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("THIS WILL REMOVE THE PART, CONTINUE?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                associatedParts.remove(associatedPartsTable.getSelectionModel().getSelectedItem());
                associatedPartsTable.setItems(associatedParts);


                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setContentText("The part was removed.");

                alert2.showAndWait();
            } else {
                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                alert3.setContentText("The part was not removed.");

                alert3.showAndWait();

            }
        }
    }

    /**
     * searches the part table
     */
    @FXML
    void searchParts() {

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
            alert.setContentText("NO PART WAS FOUND");

            alert.showAndWait();

            return;
        }

        modifyProductPartsTable.setItems(searchResults);


    }
}