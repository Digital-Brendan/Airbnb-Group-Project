import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * A class to control the Map panel of the Airbnb window
 *
 * The map is an anchorpane that consists of 33 hexagonal buttons
 * The buttons are coloured based on data, and user input to determine the bounds of said data
 * The buttons lift up when being hovered over, and go down when pressed (CSS)
 * When a button is pressed, a window opens with statistics about the proprety of a borough.
 * Boroughs are coloured grey if they do not contain any properties for a given range
 */

public class MapController {

    // Create
    @FXML
    Pane topLevel;
    Statistics boroughStats;

    public MapController(int minValue, int maxValue) {
        boroughStats = new Statistics(minValue, maxValue);
    }

    public MapController() {
        boroughStats = new Statistics();
    }

    /**
     * Generates a CSS style string and sets the button colour based on it
     * @param button the button to be set
     * @param color the color to be set
     */
    public void setButtonColor(Button button, Color color) {

        String styleString = "-fx-background-color: #" + Integer.toHexString(color.getRGB()).substring(2); // Adds the styling by getting the RGB value of the HSB colour (first two bits are alpha so not important)

        button.setStyle(styleString);

    }



    /**
     * Initalises the map by colouring preparing its dataset and colouring all the buttons.
     */
    public void mapViewStart() {
        HashMap<String, Color> boroughColourMap = boroughStats.boroughColors(null);

        for (String borough : boroughColourMap.keySet()) {
            borough = borough.replaceAll("\\s", "");
            String lookupString = "#" + borough;
            Color colorToSet = boroughColourMap.get(borough);


            Button button = (Button) topLevel.lookup(lookupString);
            setButtonColor(button, colorToSet);
        }
    }


    /**
     * Launches the mapTable controller to display stats about the borough that was clicked
     * @param boroughName the name of the borough that was clicked
     * @throws Exception
     */
    private void displayStats(String boroughName) throws Exception {

        MapTableViewController tableController = new MapTableViewController(boroughName, boroughStats.getListingsMap());
        FXMLLoader tableLoader = new FXMLLoader(getClass().getResource("mapTable.fxml"));
        tableLoader.setController(tableController);


        Pane root = tableLoader.load();
        Stage stage = new Stage();

        stage.setTitle(boroughName);
        stage.setScene(new Scene(root));
        stage.setResizable(false);

        tableController.mapTableStart();
        stage.show();
    }


    /**
     * Semi obselete method that was used when there were different buttons.
     *
     * @param string the id of the button that was pressed
     * @return whether or not the string passed in is a map button
     */
    private boolean isMapButtonID(String string) {
        ArrayList<String> boroughsNoSpaces = new ArrayList<>();
        for (String borough : boroughStats.getBoroughNames()) {
            borough = borough.replaceAll("\\s", "");
            boroughsNoSpaces.add(borough);
        }
        return boroughsNoSpaces.contains(string);

    }

    /**
     * All map button events are routed through this to reduce the amount of code
     * @param event any button event
     */
    @FXML
    private void mapButtonHandler(Event event) {
        Button button = (Button) event.getSource();
        String boroughName = button.getId();
        try {
            if (isMapButtonID(boroughName)) {
                displayStats(boroughName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
