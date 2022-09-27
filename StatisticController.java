import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A class to control the Statistics panel of the Airbnb window
 */

public class StatisticController {

    // Create variables to hold values based on the constructors
    ArrayList<StatisticListTuple> statisticList;
    Statistics stats;


    /**
     * If the user specifies values, create objects based on these values and set them to the variables
     * @param minValue the minimum price range the user specifies
     * @param maxValue the maximum price range the user specifies
     */
   public StatisticController(int minValue, int maxValue) {
       stats = new Statistics(minValue, maxValue);
       statisticList = createStatisticList();
    }

    /**
     * If the user does not specify a price range, create for all data
     */
    public StatisticController() {
        stats = new Statistics();
        statisticList = createStatisticList();
    }

    /**
     * Splits the text from the label into two parts, the title and the statistic value
     * @param labelText The string to split
     * @return An array containing the two parts
     */
    public String[] getLabelTextArray(String labelText) {
        if(!(labelText.isEmpty())){
            return labelText.split("_");
        }else{
            return null;
        }
    }

    /**
     * Creates the list of statistics in the form of tuples containing their location (if they have one) and the value of the statistic
     * @return The list of StatisticListTuples
     */
    private ArrayList<StatisticListTuple> createStatisticList() {
        ArrayList<StatisticListTuple> last = new ArrayList<>();

        StatisticListTuple availableProperties = new StatisticListTuple("",("Total number of available properties_" + stats.availableProperties()));
        last.add(availableProperties);

        StatisticListTuple happiestBorough = new StatisticListTuple("tr",("The happiest borough_" + stats.happiestBorough()));
        last.add(happiestBorough);

        StatisticListTuple averageReviews = new StatisticListTuple("",("Average number of reviews per listing_" + stats.averageReviews()));
        last.add(averageReviews);

        StatisticListTuple totalNumProperties = new StatisticListTuple("",("Total number of entire properties (not private rooms)_" + stats.numberOfWholeProperties()));
        last.add(totalNumProperties);

        StatisticListTuple mostExpensiveBorough = new StatisticListTuple("tl",("The most expensive borough for a stay_" + stats.mostExpensiveBorough(null)));
        last.add(mostExpensiveBorough);

        StatisticListTuple leastDangerous = new StatisticListTuple("",("The least dangerous borough_" + stats.leastDangerousBorough()));
        last.add(leastDangerous);

        StatisticListTuple leastPollutedBorough = new StatisticListTuple("",("The least polluted borough_" + stats.leastPollutedBorough()));
        last.add(leastPollutedBorough);

        StatisticListTuple quietestBorough = new StatisticListTuple("br",("The quietest borough_" + stats.quietestBorough()));
        last.add(quietestBorough);

        StatisticListTuple bestForEmployment = new StatisticListTuple("bl",("The best borough for employment_" + stats.bestBoroughForEmployment()));
        last.add(bestForEmployment);

        return last;
    }

    /**
     * The handler for a button in the top left segment of the statistic panel being called. Depending on the left or right button clicked, changes
     * what is displayed on the panel
     * @param event The event of the button click
     */
    @FXML
    private void buttonClickTL(ActionEvent event) {
        Button button = (Button) event.getSource();
        String buttonName = button.getId();
        if (Objects.equals(buttonName, "tlLbutton")){
            leftButtonClicked("tl", button);
        }
        else{
            rightButtonClicked("tl",button);
        }

    }

    /**
     * The handler for a button in the top right segment of the statistic panel being called. Depending on the left or right button clicked, changes
     * what is displayed on the panel
     * @param event The event of the button click
     */
    @FXML
    private void buttonClickTR(ActionEvent event) {
        Button button = (Button) event.getSource();
        String buttonName = button.getId();
        if (Objects.equals(buttonName, "trLbutton")){
            leftButtonClicked("tr",button);
        }
        else{
            rightButtonClicked("tr",button);
        }


    }

    /**
     * The handler for a button in the bottom left segment of the statistic panel being called. Depending on the left or right button clicked, changes
     * what is displayed on the panel
     * @param event The event of the button click
     */
    @FXML
    private void buttonClickBL(ActionEvent event) {
        Button button = (Button) event.getSource();
        String buttonName = button.getId();
        if (Objects.equals(buttonName, "blLbutton")){
            leftButtonClicked("bl", button);
        }
        else{
            rightButtonClicked("bl",button);
        }

    }

    /**
     * The handler for a button in the bottom right segment of the statistic panel being called. Depending on the left or right button clicked, changes
     * what is displayed on the panel
     * @param event The event of the button click
     */
    @FXML
    private void buttonClickBR(ActionEvent event) {
        Button button = (Button) event.getSource();
        String buttonName = button.getId();
        if (Objects.equals(buttonName, "brLbutton")){
            leftButtonClicked("br", button);
        }
        else{
            rightButtonClicked("br",button);
        }

    }

    /**
     * The handler for when one of the panels left arrow buttons is clicked. Load the previous statistic depending on if that statistic
     * is loaded.
     * @param buttonName The name of the button clicked
     * @param button The button clicked
     */
    @FXML
    private void leftButtonClicked(String buttonName, Button button){
       int currentIndex = getPresentIndex(buttonName);
        if(currentIndex == -1){
            return;
        }

        int displayIndex = getValidNextIndex(currentIndex, -1);

        String displayString = getStatAtValidNextIndex(displayIndex);

        displayStatistic(button, buttonName, displayString, currentIndex, displayIndex);

        //get the index of what is currently being displayed
        // "look" to the left (negative direction) until you find an empty string
        //display the empty string, remove from the old index and add to the new

    }

    /**
     * The handler for when one of the panels right arrow buttons is clicked. Load the next statistic depending on if that statistic
     * is loaded.
     * @param buttonName The name of the button clicked
     * @param button The button clicked
     */
    private void rightButtonClicked(String buttonName, Button button){
        int currentIndex = getPresentIndex(buttonName);
        if(currentIndex == -1){
            return;
        }else{
        }

        int displayIndex = getValidNextIndex(currentIndex, 1);

        String displayString = getStatAtValidNextIndex(displayIndex);

        displayStatistic(button, buttonName, displayString, currentIndex, displayIndex);
        //get the index of what is currently being displayed
        // "look" to the right (positive direction) until you find an empty string
        //display the empty string, remove from the old index and add to the new

    }

    /**
     * Get the index of the currently loaded statistic where the button is located. For example, if the button clicked is
     * in the top left panel, the current index of the statistic loaded at the top left panel is returned.
     * @param buttonName The name of the clicked button
     * @return The index of the statistic loaded in the panel where the button is located
     */
    private int getPresentIndex(String buttonName) {

        for (int i = 0; i < statisticList.size(); i++) {
            StatisticListTuple listTuple = statisticList.get(i);
            if (listTuple.getKey().matches(buttonName)) {
                return i;
            } else {
            }
        }
        return -1;
    }

    /**
     * Gets the next valid index of a statistic to be displayed. It is valid if the statistic at that index is not currently loaded
     * @param currentIndex The current index of the statistic displayed
     * @param stepDirection -1 for looking backwards in the list, 1 for looking forward in the list
     * @return currentIndex
     */
    private int getValidNextIndex(int currentIndex, int stepDirection){
        boolean validIndexFound = false;

        while(!validIndexFound){

            if((currentIndex == -1) && (stepDirection == -1)){
                currentIndex = 8;
            }else if((currentIndex == 9) && (stepDirection == 1)){
                currentIndex = 0;
            }
            StatisticListTuple listTuple = statisticList.get(currentIndex);
            if(listTuple.getKey().matches("")){
                validIndexFound = true;
                return currentIndex;
            }else{
                currentIndex += stepDirection;
            }
        }
        return -1;
    }


    /**
     * Gets the statistic value at the next valid index
     * @param displayIndex The index to get the statistic from
     * @return The statistic at that index
     */
    private String getStatAtValidNextIndex(int displayIndex) {
        StatisticListTuple listTuple = statisticList.get(displayIndex);
        return listTuple.getValue();
    }

    /**
     * Displays the correct statistic to be loaded in the correct panel based on the passed parameters.
     * @param button The button that has been clicked
     * @param buttonName The name of the button that has been clicked
     * @param displayData The data to display
     * @param previousIndex The index of the previously displayed the statistic
     * @param displayIndex The index of the statistic to display
     */
    private void displayStatistic(Button button, String buttonName, String displayData, int previousIndex, int displayIndex){
        BorderPane borderPane = (BorderPane) button.getParent();

        Label topLabel = (Label) borderPane.getTop();
        Label botLabel = (Label) borderPane.getBottom();

        String[] parts = getLabelTextArray(displayData);
        topLabel.setText(parts[0]);
        botLabel.setText(parts[1]);

        StatisticListTuple previousTuple = statisticList.get(previousIndex);
        StatisticListTuple displayTuple = statisticList.get(displayIndex);

        previousTuple.setKey("");
        displayTuple.setKey(buttonName);
    }
}
