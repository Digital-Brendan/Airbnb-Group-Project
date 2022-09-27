import javafx.scene.control.Hyperlink;
/**
 * A class utilised by the map table view
 */
public class TableEntry {
    private String hostName;
    private int minNights;
    private double price;
    private int reviews;
    private Hyperlink streetView;


    public TableEntry(double price, int reviews, int nights, String hostName, Hyperlink streetView){
        this.price = price;
        this.reviews = reviews;
        this.minNights = nights;
        this.hostName = hostName;
        this.streetView = streetView;
    }

    public Hyperlink getStreetView() {
        return streetView;
    }

    public void setStreetView(Hyperlink streetView) {
        this.streetView = streetView;
    }
    /**
     * Get name of host
     * @return hostName
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Set name of host
     * @param hostName name of host
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * Get minimum number of nights that property
     * must be rented for
     * @return minNights
     */
    public int getMinNights() {
        return minNights;
    }

    /**
     * Set min number of nights someone must rent for
     * @param minNights Required minimum number of nights
     */
    public void setMinNights(int minNights) {
        this.minNights = minNights;
    }

    /**
     * Get price of property
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set price of property
     * @param price Price of property
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get number of reviews about property
     * @return reviews
     */
    public int getReviews() {
        return reviews;
    }

    /**
     * Set number of reviews for property
     * @param reviews Number of reviews
     */
    public void setReviews(int reviews) {
        this.reviews = reviews;
    }
}
