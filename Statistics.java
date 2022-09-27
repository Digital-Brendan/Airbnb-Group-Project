import java.awt.*;
import java.util.*;

/**
 * A class to calculate statistics based on data from the listings and additional data from the wider borough area
 */

public class Statistics {

    // Initialise a data loader for the Airbnb listings and call the method to create an ArrayList of
    // all listings
    AirbnbDataLoader airbnbLoader = new AirbnbDataLoader();
    ArrayList<AirbnbListing> listings = airbnbLoader.load();

    // Initialise a data loader for the extra statistics and call the method to create an ArrayList of
    // objects containing each borough's statistics
    BoroughStatisticDataLoader statisticLoader = new BoroughStatisticDataLoader();
    ArrayList<BoroughStatistic> extraStatistics = statisticLoader.load();

    // Create two maps, one containing each borough and an ArrayList of listings from that borough. The other
    // containing each borough and the statistic object that correlates to it.
    HashMap<String, ArrayList<AirbnbListing>> listingsMap = split(listings);
    HashMap<String, BoroughStatistic> statisticMap = mapBoroughToStatistic();

    /**
     * Only use data in the price range if values specified
     * @param minValue Minimum property value
     * @param maxValue Maximum property value
     */
    public Statistics(int minValue, int maxValue) {
        listings = getNewListings(minValue, maxValue);
        listingsMap = createPriceMap(minValue, maxValue);
    }

    // If no parameters set, use all data
    public Statistics() {
    }

        //=========================================PUBLIC METHODS=========================================

        /**
         Finds the average price of the borough inputted.
         @return The average price of a stay in the borough.
         @param name The name of the borough to check the average price of.
         **/
        public int averagePrice (String name){
            ArrayList<AirbnbListing> propertyList = listingsMap.get(name);
            if (propertyList == null || propertyList.size() == 0 ) {
                return 0;
            }else {
                int runningTotal = 0;
                for (AirbnbListing listing : propertyList) {
                    runningTotal = runningTotal + (listing.getPrice() * listing.getMinimumNights());
                }
                return runningTotal / propertyList.size();
            }
        }

        /**
         * Finds the amount of available properties by checking the amount of total listings
         * @return The number of available properties
         */
        public int availableProperties () {
            return listings.size();
        }

        /**
         * Finds the total number of listings that are of an entire property, as opposed to a private room
         * @return The number of whole properties from the total listings
         */
        public int numberOfWholeProperties () {
            int runningTotal = 0;
            for (AirbnbListing listing : listings) {
                if (listing.getRoom_type().equals("Entire home/apt")) {
                    runningTotal++;
                }
            }
            return runningTotal;
        }

        /**
         * Returns the number of average number of reviews left on listing
         * @return The average amount of reviews left on a listing
         */
        public int averageReviews () {
            int runningTotal = 0;
            for (AirbnbListing listing : listings) {
                runningTotal = runningTotal + listing.getNumberOfReviews();
            }
            return runningTotal / listings.size();
        }

        /**
         * Calculates the most expensive borough by comparing the average price of a stay in each borough to one another
         * @param selectedBoroughs The list of boroughs to get the most expensive from, if null then checks from all boroughs
         * @return The name of the most expensive borough on the list
         */
    public String mostExpensiveBorough (Set < String > selectedBoroughs) {
        Set<String> boroughList;
         if (selectedBoroughs == null) {
                boroughList = getBoroughNames();
            } else {
                boroughList = selectedBoroughs;
            }
            String expensiveBorough = null;
            double expensiveBoroughPrice = 0;
            for (String borough : boroughList) {
                double boroughPrice = averagePrice(borough);
                if (expensiveBorough == null | boroughPrice > expensiveBoroughPrice) {
                    expensiveBorough = borough;
                    expensiveBoroughPrice = boroughPrice;
                }
            }
            return expensiveBorough;
        }

    /**
     * Creates a map with each borough name, and a colour corresponding to the number of properties in the borough
     * from red for the least number of properties to green for the most.
     * @param selectedBoroughs The list of boroughs to get colours for, if null then gets for all boroughs
     * @return A HashMap of each borough's name and a colour corresponding to the number of properties
     */
    public HashMap<String, Color> boroughColors (Set <String> selectedBoroughs) {
        Set<String> boroughList;
        HashMap<String, Color> boroughColorMap = new HashMap<>();
        ArrayList<String> orderedBoroughs = new ArrayList<>();

        if (selectedBoroughs == null) {
            boroughList = getBoroughNames();
        } else {
            boroughList = selectedBoroughs;
        }

        while (mostListingsFromBoroughs(boroughList) != null) {
            String mostListings = mostListingsFromBoroughs(boroughList);
            orderedBoroughs.add(mostListings);
            boroughList.remove(mostListings);
        }

        Iterator<String> iterator = orderedBoroughs.iterator();
        while(iterator.hasNext()){
            String borough = iterator.next();
            if (listingsMap.get(borough).size() == 0) {
                iterator.remove();
                boroughColorMap.put(borough.replaceAll("\\s", ""), Color.lightGray); // removes from the list and sets color to grey if no properties in the borough (with no spaces in the name)
            }
        }

        // Puts the borough name with no spaces and a color value for each borough, which is calculated by dividing the hue value between red and green into enough segments for the number of boroughs
        // to be ordered and then assigns them in order from most to least
        int boroughListSize = orderedBoroughs.size();
        for (int i = 0; i < orderedBoroughs.size(); i++) {
            boroughColorMap.put(orderedBoroughs.get(i).replaceAll("\\s", ""), Color.getHSBColor((float) ((0.3 / boroughListSize) * (boroughListSize - i)), 0.8f, 0.8f));
        }
        return boroughColorMap;
    }

        /**
         * Creates a set of Strings containing every borough's name.
         * @return The set of all borough names
         */
        public Set<String> getBoroughNames () {
            return new HashSet<>(listingsMap.keySet());
        }

        /**
         * Calculates the quietest borough by comparing a value calculated from the population estimate and population
         * density of each borough
         * @return The name of the quietest borough
         */
        public String quietestBorough () {
            String quietest = null;
            double quietestValue = 99999999;
            for (String borough : getBoroughNames()) {
                BoroughStatistic currentStatistic = statisticMap.get(borough);
                double current = (currentStatistic.getPopulationEstimate() * currentStatistic.getPopulationDensity());
                if (current < quietestValue) {
                    quietest = borough;
                    quietestValue = current;
                }
            }
            return quietest;
        }

        /**
         * Calculates the least polluted borough by comparing a calculated value factoring in the "green space" in each borough
         * and the total carbon emissions from that borough
         * @return The name of the least polluted borough
         */
        public String leastPollutedBorough () {
            String polluted = null;
            double pollutionValue = 99999999;
            for (String borough : getBoroughNames()) {
                BoroughStatistic currentStatistic = statisticMap.get(borough);
                double current = (currentStatistic.getGreenspacePercentage() * currentStatistic.getTotalCarbonEmissions());
                if (current < pollutionValue) {
                    polluted = borough;
                    pollutionValue = current;
                }
            }
            return polluted;
        }

        /**
         * Calculates the least dangerous borough by comparing a value calculated by factoring in the ambulance incidents, fires and crime rates per thousand population
         * in each borough
         * @return The name of the least dangerous borough
         */
        public String leastDangerousBorough () {
            String dangerous = null;
            double dangerousValue = 99999999;
            for (String borough : getBoroughNames()) {
                BoroughStatistic currentStatistic = statisticMap.get(borough);
                double current = ((currentStatistic.getAmbulanceByThousand() + currentStatistic.getFiresByThousand()) * currentStatistic.getCrimeRateByThousand());
                if (current < dangerousValue) {
                    dangerous = borough;
                    dangerousValue = current;
                }
            }
            return dangerous;
        }

        /**
         * Calculates the best borough for employment by calculating a value from the active businesses and employment rate from each
         * borough and comparing them
         * @return The name of the best borough for employment
         */
        public String bestBoroughForEmployment () {
            String employment = null;
            double employmentValue = 0;
            for (String borough : getBoroughNames()) {
                BoroughStatistic currentStatistic = statisticMap.get(borough);
                double current = ((currentStatistic.getActiveBusinesses() * currentStatistic.getEmploymentRate()));
                if (current > employmentValue) {
                    employment = borough;
                    employmentValue = current;
                }
            }
            return employment;
        }

        /**
         * Finds the happiest borough by comparing a calculated value that factors in a "happiness score" from surveys and the gross
         * annual salary from each borough
         * @return The name of the happiest borough
         */
        public String happiestBorough () {
            String happiest = null;
            double happiestValue = 0;
            for (String borough : getBoroughNames()) {
                BoroughStatistic currentStatistic = statisticMap.get(borough);
                double current = (currentStatistic.getHappinessScore() * currentStatistic.getGrossAnnualPay());
                if (current > happiestValue) {
                    happiest = borough;
                    happiestValue = current;
                }
            }
            return happiest;
        }

        /**
         * Creates a map with the name of each borough and the statistic object that corresponds to it
         * @return A HashMap containing each borough's name and the relevant statistic
         */
        public HashMap<String, BoroughStatistic> mapBoroughToStatistic () {
            HashMap<String, BoroughStatistic> last = new HashMap<>();
            for (String boroughName : getBoroughNames()) {
                for (BoroughStatistic statistic : extraStatistics) {
                    if (statistic.getAreaName().equals(boroughName)) {
                        last.put(boroughName, statistic);
                    }
                }
            }
            return last;
        }

    /**
     * Finds all of the listings where a minimum stay falls in the price range specified in a given borough
     * @param borough The borough to get the listings from
     * @param minValue The bottom value for the price of a minimum stay
     * @param maxValue The top value for a price of a minimum stay
     * @return The list of listings in the price range
     */
        public ArrayList<AirbnbListing> getListingsInRange (String borough,int minValue, int maxValue){
            ArrayList<AirbnbListing> boroughList = listingsMap.get(borough);
            ArrayList<AirbnbListing> newList = new ArrayList<>();
            for (AirbnbListing listing : boroughList) {
                int priceOfStay = listing.getPrice() * listing.getMinimumNights();
                if (priceOfStay < maxValue && priceOfStay > minValue) {
                    newList.add(listing);
                }
            }
            return newList;
        }

        /**
         * Gets the list of listings that are located in the London borough specified
         * @param borough The borough to get the listings from
         * @return An ArrayList of the listings from the borough
         */
        public ArrayList<AirbnbListing> getListOfListings (String borough){
            return listingsMap.get(borough);
        }

        public HashMap<String, ArrayList<AirbnbListing>> createPriceMap(int minValue, int maxValue){
            HashMap<String, ArrayList<AirbnbListing>> newMap = new HashMap<>();
            for (String borough : listingsMap.keySet()) {
                newMap.put(borough, getListingsInRange(borough, minValue, maxValue));
            }
            return newMap;
        }

    /**
     * Filters the list of listings to only include the listings in the price range
     * @param minValue The bottom value for the minimum price of a stay
     * @param maxValue The top value for the minimum price of a stay
     * @return The list of listings in this price range
     */
        public ArrayList<AirbnbListing> getNewListings(int minValue, int maxValue) {
            ArrayList<AirbnbListing> newList = new ArrayList<>();
            for (AirbnbListing listing : listings) {
                int minStayPrice = listing.getPrice() * listing.getMinimumNights();
                if (minStayPrice > minValue && minStayPrice < maxValue) {
                    newList.add(listing);
                }
            }
            return newList;
        }

    /**
     * Gets the map containing all of the boroughs and lists of the listings in that borough
     * @return The map of boroughs and lists of listings
     */
    public HashMap<String, ArrayList<AirbnbListing>> getListingsMap(){
            return listingsMap;
        }

//=========================================PRIVATE METHODS=========================================

    /**
    * Creates a map containing the name of each borough and an ArrayList of all of the listings that are from the borough
    * @param list The list of all listings
    * @return A HashMap containing the name of each borough and an ArrayList of all listings located in that borough
    */
    private HashMap<String, ArrayList<AirbnbListing>> split (ArrayList < AirbnbListing > list)
    {
        HashMap<String, ArrayList<AirbnbListing>> last = new HashMap<>();
        for (AirbnbListing listing : list) {
            String currentBorough = listing.getNeighbourhood();
            if (last.containsKey(currentBorough)) {
                ArrayList<AirbnbListing> currentList = last.get(currentBorough);
                currentList.add(listing);
            } else {
                ArrayList<AirbnbListing> listingList = new ArrayList<>();
                listingList.add(listing);
                last.put(currentBorough, listingList);
            }
        }
        return last;
    }


    /**
     * Finds the borough with the most listings from a set of boroughs
     * @param selectedBoroughs The boroughs to find the one with the most listings from, gets from all if left as null
     * @return The borough with the most listings
     */
    private String mostListingsFromBoroughs (Set<String> selectedBoroughs) {
        // Finds the list of boroughs to be working on based on the input
        Set<String> boroughList;
        if (selectedBoroughs == null) {
            boroughList = getBoroughNames();
        } else {
            boroughList = selectedBoroughs;
        }

        String mostListings = null;
        int mostListingsNumber = 0;
        for (String borough : boroughList) {
            int boroughListingsNumber = listingsMap.get(borough).size();
            if (mostListings == null | boroughListingsNumber > mostListingsNumber) {
                mostListings = borough;
                mostListingsNumber = boroughListingsNumber;
            }
        }
        return mostListings;
    }
}
