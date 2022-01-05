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
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 * @author Avery Landseadal*/
public class ModifyPartForm implements Initializable {

    Stage stage;
    Parent scene;


    @FXML
    private TextField modifyPartIDField;

    @FXML
    private TextField modifyPartNameField;

    @FXML
    private TextField modifyPartInventoryField;

    @FXML
    private TextField modifyPartPriceField;

    @FXML
    private TextField modifyPartMaxField;

    @FXML
    private TextField modifyPartMinField;

    @FXML
    private TextField modifyPartSwitchField;

    @FXML
    private Label machineID;

    @FXML
    private RadioButton inHouseRadio;

    @FXML
    private RadioButton outsourcedRadio;


    // starts the modify part form
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     *
     * @param event saves the modified part
     * @throws IOException gives a confirmation to save the part and an error if needed
     */
    @FXML
    void saveModifyPart(ActionEvent event) throws IOException
    {


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Save the modified part?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK)

        {

            try {
                int id = Integer.parseInt(modifyPartIDField.getText());
                String name = modifyPartNameField.getText();
                double price = Double.parseDouble(modifyPartPriceField.getText());
                int stock = Integer.parseInt(modifyPartInventoryField.getText());
                int min = Integer.parseInt(modifyPartMinField.getText());
                int max = Integer.parseInt(modifyPartMaxField.getText());


                if (min <= max) {
                    if ((min <= stock) && (stock <= max)) {

                        if (inHouseRadio.isSelected()) {

                            int machineID = Integer.parseInt(modifyPartSwitchField.getText());

                            Part part = new InHouse(id, name, price, stock, min, max, machineID);

                            int index = Inventory.getAllParts().indexOf(this.part);

                            Inventory.updatePart(index, part);

                        } else {

                            String companyName = modifyPartSwitchField.getText();

                            Part part = new Outsourced(id, name, price, stock, min, max, companyName);

                            int index = Inventory.getAllParts().indexOf(this.part);

                            Inventory.updatePart(index, part);

                        }

                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();
                    }
                    else
                    {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setContentText("INVENTORY VALUE CANNOT BE LOWER THAN THE MIN OR HIGHER THAN THE MAX");
                        alert2.showAndWait();
                    }
                }
                else
                {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setContentText("THE MIN VALUE CANNOT BE HIGHER THAN THE MAX");
                    alert2.showAndWait();
                }
            }

            catch(NumberFormatException e)
            {

                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setContentText("A VALUE IN THE TEXT FIELD IS INCORRECT");
                alert2.showAndWait();

            }

        }

    }
    /**
     * This will change the label to say Machine ID if the In House radio button is selected.
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
     * @param event sends the user back to the main screen
     * @throws IOException gives a confirmation to cancel modifying the part
     */
    @FXML
    void cancelModifyPart(ActionEvent event) throws IOException
    {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("CANCEL MODIFYING THE PART?");

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
     * @param part sends the modified part to the part table
     */
    public void sendPart(Part part)
    {
        this.part = part;

        modifyPartIDField.setText(Integer.toString(part.getId()));
        modifyPartNameField.setText(part.getName());
        modifyPartInventoryField.setText(Integer.toString(part.getStock()));
        modifyPartPriceField.setText(Double.toString(part.getPrice()));
        modifyPartMaxField.setText(Integer.toString(part.getMax()));
        modifyPartMinField.setText(Integer.toString(part.getMin()));

        if (part instanceof InHouse)
        {
            modifyPartSwitchField.setText(Integer.toString(((InHouse) part).getMachineId()));
            machineID.setText("Machine ID:");
            inHouseRadio.setSelected(true);
        }
        else
        {
            modifyPartSwitchField.setText(((Outsourced) part).getCompanyName());
            machineID.setText("Company Name:");
            outsourcedRadio.setSelected(true);
        }


    }
    Part part;
}