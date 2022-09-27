
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.scene.layout.*;
import javafx.util.Duration;

/**
 * The primary class of the Airbnb London project, which will display information from listings in a variety of forms alongside
 * statistics based on these listings and wider borough data.
 */

public class main extends Application {
    private int currentIndex = 0;
    private static final int LOAD_TIME = 2;


    @FXML
    private BorderPane buttonBorderPane;

    @FXML
    private StackPane centrePane;
    Pane mapPanel;
    Pane statsPanel;

    // Make reference to fromCombobox and its value
    @FXML
    private ComboBox<String> fromCombo;
    private String fromSelected;

    // Make reference to toCombobox and its value
    @FXML
    private ComboBox<String> toCombo;
    private String toSelected;


    public static void main(String[] args) {
        // Start JavaFX application
        launch(args);
    }


    /**
     * Create loading window
     *
     * @param stage primary stage for JavaFX application
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Open loading screen to begin
        URL url = getClass().getResource("fxml/loading.fxml");
        Pane loading = FXMLLoader.load(url);
        Scene scene = new Scene(loading);
        stage.setTitle("AirBnB London");
        stage.setScene(scene);
        stage.show();

        Statistics stats = new Statistics();

        // Wait for a time to give illusion of loading
        PauseTransition delay = new PauseTransition(Duration.seconds(LOAD_TIME));
        delay.setOnFinished(event -> {
            try {
                createMainWindow(stage);
            } catch (Exception e) {
                System.out.print(e);
            }
        });
        delay.play();

    }

    /**
     * Create main window
     *
     * @param stage primary stage for JavaFX application
     * @throws Exception e
     */
    protected void createMainWindow(Stage stage) throws Exception {
        URL url = getClass().getResource("template.fxml");
        Pane root = FXMLLoader.load(url);
        Scene scene = new Scene(root);

        stage.setTitle("AirBnB London");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    /**
     * Move to left panel
     * @param event Action event
     */
    @FXML
    private void left(ActionEvent event) {
        currentIndex = currentIndex - 1;
        changePanel();

    }

    /**
     * Move to right panel
     * @param event Action event
     */
    @FXML
    private void right(ActionEvent event) {
        currentIndex = currentIndex + 1;
        changePanel();
    }

    /**
     * Processes input from the combo boxes
     * If the values are invalid (from is greater than to, or to is less than from depending on the order the user selects them in)
     * An error box is thrown onto the screen to warn the user.
     *
     * The map and statistics panel are only created if the values selected are valid.
     * @param event Action event
     */
    @FXML
    private void from(ActionEvent event) {
        String toBoxString = toCombo.getValue();

        int checkFromValue = Integer.parseInt(fromCombo.getValue());
        int checkToValue = Integer.parseInt(toCombo.getValue());


        if( !toBoxString.isEmpty() && (checkFromValue >= checkToValue)){
            comboBoxError(checkFromValue, checkToValue);
        }else if (checkFromValue != 0 && checkToValue != 0 && checkFromValue < checkToValue) {
            loadPanels(checkFromValue, checkToValue);
        } else {
            fromSelected = fromCombo.getValue();
            // Load map pane into centre with lower and upper value
        }
    }

    /**
     * Processes input from the combo boxes
     * If the values are invalid (from is greater than to, or to is less than from depending on the order the user selects them in)
     * An error box is thrown onto the screen to warn the user.
     *
     * The map and statistics panel are only created if the values selected are valid.
     * @param event Action event
     */
    @FXML
    private void to(ActionEvent event) throws Exception {
        String fromBoxString = fromCombo.getValue();
        int checkFromValue = Integer.parseInt(fromCombo.getValue());
        int checkToValue = Integer.parseInt(toCombo.getValue());

        if(!fromBoxString.isEmpty() && (checkToValue <= checkFromValue)) {
            comboBoxError(checkFromValue, checkToValue);
        }else{
            toSelected = toCombo.getValue();

            //createWebWindow();

            loadPanels(checkFromValue, checkToValue);
            // Load map pane into centre with lower and upper value
        }
    }

    private void enableButtons(){
        Button leftButton = (Button) buttonBorderPane.lookup("#leftButton");
        Button rightButton = (Button) buttonBorderPane.lookup("#rightButton");

        leftButton.setDisable(false);
        rightButton.setDisable(false);

    }
    /**
     * Creates instances of the controller classes for the map panel and the statistics panel.
     * Generates a map based on user input
     * Generates statistics based on user input
     * @param minValue the "from" value of the combobox
     * @param maxValue the "to" value of the combobox
     */
    private void loadPanels(int minValue, int maxValue) {
        try {
            if (centrePane.getChildren() != null) {
                centrePane.getChildren().clear();
            }
            //Enables buttons
            enableButtons();
            //Loading map panel from FXML
            MapController mapController = new MapController(minValue, maxValue);
            FXMLLoader mapLoader = new FXMLLoader(getClass().getResource("mapViewFXML.fxml"));
            mapLoader.setController(mapController);
            //Loading stats panel from FXML
            StatisticController statsController = new StatisticController(minValue, maxValue);
            FXMLLoader statsLoader = new FXMLLoader(getClass().getResource("statistics.fxml"));
            statsLoader.setController(statsController);


            mapPanel = mapLoader.load();
            statsPanel = statsLoader.load();


            centrePane.getChildren().add(mapPanel);
            centrePane.getChildren().add(statsPanel);

            mapController.mapViewStart();

            statsPanel.setVisible(false);

        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    /**
     * Checks the current panel and switches to the one that is currently not being displayed
     */
    private void changePanel() {
        if (currentIndex % 2 == 0) {
            mapPanel.setVisible(true);
            statsPanel.setVisible(false);
        } else {
            mapPanel.setVisible(false);
            statsPanel.setVisible(true);
        }
    }

    /**
     * error box method that tells the user why their input was wrong
     * @param fromValue "from" combobox value
     * @param toValue "to" combobox value
     */
    private void comboBoxError(int fromValue, int toValue){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error: invalid selection");
        alert.setHeaderText("Values.  From: " + fromValue + " To: " + toValue);
        alert.setContentText("From must be less than To");

        alert.showAndWait();
    }

}


