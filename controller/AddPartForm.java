package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 * @author Avery Landseadal*/
public class AddPartForm implements Initializable {

    Stage stage;
    Parent scene;

    /** Creates the add part class*/
    @FXML
    private Label machineID;

    @FXML
    private TextField addPartIDField;

    @FXML
    private TextField addPartNameField;

    @FXML
    private TextField addPartInventoryField;

    @FXML
    private TextField addPartPriceField;

    @FXML
    private TextField addPartMaxField;

    @FXML
    private TextField machineIDField;

    @FXML
    private TextField addPartMinField;

    @FXML
    private RadioButton inHouseRadio;


    // This method starts the Add Part Form.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addPartIDField.setText(String.valueOf(Inventory.partID));

        inHouseRadio.setSelected(true);


    }

    /**
     * This will change the label to say Machine ID if the inHouse radio button is selected
     */
    @FXML
    void inHouse()
    {
        machineID.setText("Machine ID");
    }

    /**
     * This will change the label to say Company Name if the Outsourced radio button is selected
     */
    @FXML
    void outsourced()
    {
        machineID.setText("Company Name");
    }

    /**
     *
     * @param event this event cancels the add part
     * @throws IOException gives a warning message
     */
    @FXML
    void cancelAddPart(ActionEvent event) throws IOException
    {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Are you sure you want to cancel?");

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
     * @param event adds the part
     * @throws IOException gives a confirmation message and or an error message
     */
    @FXML
    void addPart(ActionEvent event) throws IOException
    {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("This will save the part, do you wish to continue?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK)

        {
            try
            {
                int id = Integer.parseInt(addPartIDField.getText());
                String name = addPartNameField.getText();
                int stock = Integer.parseInt(addPartInventoryField.getText());
                double price = Double.parseDouble(addPartPriceField.getText());
                int min = Integer.parseInt(addPartMinField.getText());
                int max = Integer.parseInt(addPartMaxField.getText());


                if (min <= max)
                {

                    if ((min <= stock) && (stock <= max)) {


                        if (inHouseRadio.isSelected())
                        {

                            int machineID = Integer.parseInt(machineIDField.getText());

                            Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID) {
                            });



                        }
                        else
                        {
                            String companyName = machineIDField.getText();

                            Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName) {
                            });
                        }

                        /** part ID starts at 0 for some reason, FIX THIS. FIXED set public static int partID = 1 **/
                        Inventory.partID++;

                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();

                    }

                    else

                    {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setContentText("MUST BE BETWEEN MAX AND MIN VALUES");
                        alert2.showAndWait();
                    }
                }
                else
                {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setContentText("MIN VALUE CANNOT BE GREATER THAN MAX VALUE");
                    alert2.showAndWait();
                }

            }

            catch(NumberFormatException e)
            {

                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                alert3.setContentText("Please enter a valid value for each Text Field!");
                alert3.showAndWait();

            }

        }

    }
}