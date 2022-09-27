import java.util.ArrayList;
import java.util.HashMap;

import application.WebController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * A class to control the Table View windows that appear when a borough is clicked on the Map panel of the Airbnb window
 */

public class MapTableViewController {

    private String borough;

    private HashMap<String, ArrayList<AirbnbListing>> filteredListings = new HashMap<>();

    private ObservableList<TableEntry> tableEntryList = FXCollections.observableArrayList();



    public MapTableViewController(String borough, HashMap<String, ArrayList<AirbnbListing>> filteredListings){
        this.borough = filterName(borough);
        this.filteredListings = removeSpacesFromNameMap(filteredListings);

    }

    public void mapTableStart(){
        filteredListings = removeSpacesFromNameMap(filteredListings);
        generateTableEntries();
        setTableData();
    }

    public String filterName(String string)
    {
        string = string.replaceAll("\\s", "" );
        return string;
    }

    
    @FXML
    private TableColumn<TableEntry, String> hostName;

    @FXML
    private TableColumn<TableEntry, Integer> minNights;

    @FXML
    private TableColumn<TableEntry, Double> price;

    @FXML
    private TableColumn<TableEntry, Integer> reviews;

    @FXML
    private TableColumn<TableEntry, Hyperlink> streetView;

    @FXML
    private TableView<TableEntry> table;

    @FXML
    private BorderPane tableBorderPane;

    /**
     * Creates table entry objcets that pertain to the borough which was clicked.
     */
    private void generateTableEntries(){
        for(AirbnbListing listing: filteredListings.get(borough)){

            TableEntry tableEntry = new TableEntry(listing.getPrice(), listing.getNumberOfReviews(), listing.getMinimumNights(), listing.getHost_name(), generateStreetViewLink(listing));
            tableEntryList.add(tableEntry);
        }
    }

    /**
     * Due to how fx:ID works, spaces must be removed from the names.
     * @param inputMap the map to have its spaces removed
     * @return a hashmap with spaces removed from the key
     */
    private HashMap<String, ArrayList<AirbnbListing>> removeSpacesFromNameMap(HashMap<String, ArrayList<AirbnbListing>> inputMap){
        HashMap<String, ArrayList<AirbnbListing>> newMap = new HashMap<String, ArrayList<AirbnbListing>>();

        for(String borough: inputMap.keySet()){
            newMap.put(filterName(borough), inputMap.get(borough));
        }
      return newMap;
    }

    /**
     * adds the generated data to the cells of the table column
     */
    private void setTableData(){
        price.setCellValueFactory(new PropertyValueFactory<TableEntry, Double>("Price"));
        reviews.setCellValueFactory(new PropertyValueFactory<TableEntry, Integer>("Reviews"));
        minNights.setCellValueFactory(new PropertyValueFactory<TableEntry, Integer>("minNights"));
        hostName.setCellValueFactory(new PropertyValueFactory<TableEntry,String>("hostName"));
        streetView.setCellValueFactory(new PropertyValueFactory<TableEntry, Hyperlink>("streetView"));

        table.setItems(tableEntryList);
    }

    /**
     * uses the longitude and latitude of an airbnb listing to generate a google maps view
     *  -- STREET VIEW DOES NOT WORK BECAUSE JAVAFX VIEWPANE DOESN'T HAVE 3D ELEMENTS --
     * @param listing the listing that is to be located on maps
     * @return a google maps link to the google maps
     */
    private Hyperlink generateStreetViewLink(AirbnbListing listing){
        String longitude = "" + listing.getLongitude();
        String latitude = "" + listing.getLatitude();

        String viewLinkString = "www.google.com/maps/search/?api=1&map_action=pano&query=" + latitude + "," + longitude;

        Hyperlink viewLink = new Hyperlink(viewLinkString);
        //action listener that creates a web view window if the user clicks a hyperlink
        viewLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    viewLink.setVisited(true);
                    createWebWindow(viewLinkString);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        return viewLink;
    }

    /**
     * Uses the web view controller to create a window that links to the google maps view that was linked.
     *
     * @param link the link to be opened by the web controller
     * @throws Exception
     */
    public void createWebWindow(String link) throws Exception{
        try{
            WebController webController = new WebController();
            FXMLLoader webLoader = new FXMLLoader(getClass().getResource("webView.fxml"));
            webLoader.setController(webController);
            Pane root = webLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Property map view");
            stage.setScene(new Scene(root));
            stage.setResizable(false);

            webController.loadPage(link);
            stage.show();


        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
