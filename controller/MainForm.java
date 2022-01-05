package controller;

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
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 * @author Avery Landseadal*/

    public class MainForm implements Initializable {

        Stage stage;
        Parent scene;

        @FXML
        private TextField searchParts;

        @FXML
        private TableView<Part> partsTable;

        @FXML
        private TableColumn<Part, Integer> partID;

        @FXML
        private TableColumn<Part, String> partName;

        @FXML
        private TableColumn<Part, Integer> partInventory;

        @FXML
        private TableColumn<Part, Double> partPrice;

        @FXML
        private TextField searchProducts;

        @FXML
        private TableView<Product> productsTable;

        @FXML
        private TableColumn<Product, Integer> productID;

        @FXML
        private TableColumn<Product, String> productName;

        @FXML
        private TableColumn<Product, Integer> productInventory;

        @FXML
        private TableColumn<Product, Double> productPrice;

        // Starts the main form
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

            partsTable.setItems(Inventory.getAllParts());

            partID.setCellValueFactory(new PropertyValueFactory<>("id"));
            partName.setCellValueFactory(new PropertyValueFactory<>("name"));
            partInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
            partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

            productsTable.setItems(Inventory.getAllProducts());

            productID.setCellValueFactory(new PropertyValueFactory<>("id"));
            productName.setCellValueFactory(new PropertyValueFactory<>("name"));
            productInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
            productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        }

        /**
         * this method deletes the selected part.
         */
        @FXML
        void deletePart() {

            if (partsTable.getSelectionModel().isEmpty())
            {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("A PART NEEDS TO BE SELECTED TO DELETE.");
                alert.showAndWait();
            }

            else
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("The part will be deleted, continue?");

                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {


                    Inventory.deletePart(partsTable.getSelectionModel().getSelectedItem());

                    partsTable.setItems(Inventory.getAllParts());


                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setContentText("The part was deleted.");

                    alert2.showAndWait();
                }
                else {
                    Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                    alert3.setContentText("The part was not deleted.");
                    alert3.showAndWait();
                }

            }

        }

        /**
         *
         */
        @FXML
        void searchParts() {

            String searchString = searchParts.getText();

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
                alert.setContentText("NO PART WAS FOUND.");

                alert.showAndWait();

                return;
            }

            partsTable.setItems(searchResults);


        }
/** RUNTIME ERROR
 * I originally could not get the AddProductScreen to display.
 * It turns out I was trying to call the AddProductForm instead of the AddProductScreen fxml file.
 * **/
        /**
         *
         * @param event shows the AddProductForm
         */
        @FXML
        void showAddProductForm(ActionEvent event) throws IOException
        {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../view/AddProductScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }

        /**
         *
         * @param event displays the ModifyProductForm
         * @throws IOException gives an error if no product is selected
         */
        @FXML
        void showModifyProductForm(ActionEvent event) throws IOException
        {

            if (productsTable.getSelectionModel().isEmpty())
            {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("A PRODUCT MUST BE SELECTED.");

                 alert.showAndWait();
            }

            else
            {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../view/ModifyProductScreen.fxml"));
                loader.load();

                ModifyProductForm ADMController = loader.getController();
                ADMController.sendProduct(productsTable.getSelectionModel().getSelectedItem());

                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();

            }

        }

        /**
         * this method deletes the selected product
         */
        @FXML
        void deleteProduct() {

            if (productsTable.getSelectionModel().isEmpty())
            {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("A PRODUCT MUST BE SELECTED TO DELETE");

                alert.showAndWait();
            }

            else
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("The product will be deleted, continue?");

                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {

                    if (productsTable.getSelectionModel().getSelectedItem().getAllAssociatedParts().size() != 0)
                    {
                        Alert alert3 = new Alert(Alert.AlertType.ERROR);
                        alert3.setContentText("CANNOT DELETE, THE PRODUCT IS STILL ASSOCIATED WITH A PART");

                        alert3.showAndWait();
                    }
                    else
                    {
                        Inventory.deleteProduct(productsTable.getSelectionModel().getSelectedItem());

                        productsTable.setItems(Inventory.getAllProducts());


                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setContentText("The product was deleted.");

                        alert2.showAndWait();
                    }

                }
                else {
                    Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                    alert3.setContentText("The product was not deleted.");

                    alert3.showAndWait();
                }

            }

        }

        /**
         * searches the products
         */

        @FXML
        void searchProducts() {

            String searchString = searchProducts.getText();

            ObservableList<Product> searchResults = Inventory.lookupProduct(searchString);

            if (searchResults.size() == 0)
            {
                try {
                    Product p = Inventory.lookupProduct(Integer.parseInt(searchString));
                    if (p != null) {
                        searchResults.add(p);
                    }
                } catch (NumberFormatException e) {

                }


            }

            if (searchResults.size() == 0)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No product was found.");

                alert.showAndWait();

                return;
            }
            productsTable.setItems(searchResults);
        }

    public void toFirst(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("First Screen");
        stage.setScene(scene);
        stage.show();
    }

        /**
         *
         * @param event display the AddPartForm
         */
        @FXML
        public void showAddPartForm(ActionEvent event) throws IOException
        {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../view/AddPartScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }

        /**
         *
         * @param event Displays the ModifyPartForm
         */
        @FXML
        void showModifyPartForm(ActionEvent event) throws IOException
        {

            if (partsTable.getSelectionModel().isEmpty())
            {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("A PART MUST BE SELECTED TO MODIFY.");

                alert.showAndWait();
            }

            else
            {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../view/ModifyPartScreen.fxml"));
                loader.load();

                ModifyPartForm ADMController = loader.getController();
                ADMController.sendPart(partsTable.getSelectionModel().getSelectedItem());


                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();
            }

        }
        /**
         * closes the program
         */
        @FXML
        void closeApp() {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("CLOSE THE PROGRAM?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.exit(0);
            }

        }
}

