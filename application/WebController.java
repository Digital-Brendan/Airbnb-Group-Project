package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;


public class WebController<LinkModel> implements Initializable {

    @FXML
    private Button reloadButton;

    @FXML
    private AnchorPane webAnchorPane;

    @FXML
    private WebView webView;

    @FXML
    private Label zoomLabel;

    @FXML
    private Slider zoomSlider;


    private WebEngine engine;

    // the starting page in case a user has not selected a property yet.
    private String homePage;

    private String url;

    // the zoom level of the web view
    private double webZoom;


    /**
     * initalising the webview
     * @param location not to be confused with the string url
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        engine = webView.getEngine();
        homePage = "www.google.com";
        loadPage(homePage);

        // refreshes the page.
        reloadButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                refreshPage();
            }
        });
        // Changes the value of the zoom slider label, and zooms the web view when the slider is changed
        zoomSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int labelInt = (int) zoomSlider.getValue();
                zoomLabel.setText("Zoom: " + labelInt + "%");
                webZoom = (zoomSlider.getValue()/100);
                webView.setZoom(webZoom);
            }
        });
    }

    // loads the page given a url
    public void loadPage(String url){
        engine.load("https://" + url);
    }
    //
    public void refreshPage(){
        engine.reload();
    }
}
