/**
 * Represents one listing of a property for rental on Airbnb.
 * This is essentially one row in the data table. Each column
 * has a corresponding field.
 *
 * @version 30.03.2022
 * @author Ryan Bunting (K21067173), Solomon Asare (K21018954), Brendan Rogan (K21041662) and Rahul Imran (K21060140)
 */ 

public class AirbnbListing {
    // The ID and name of the individual property
    private String id;
    private String name;

    /*
     * The ID and name of the host for this listing.
     * Each listing has only one host, but one host may
     * list many properties.
     */
    private String host_id;
    private String host_name;

    /*
     * The grouped location to where the listed property is situated.
     * For this data set, it is a london borough.
     */
    private String neighbourhood;

    // The location on a map where the property is situated.
    // The location on a map where the property is situated.
    private double latitude;
    private double longitude;

    // The type of property, either "Private room" or "Entire Home/apt".
    private String room_type;

    // The price per night's stay
    private int price;

    // The minimum number of nights the listed property must be booked for.
    private int minimumNights;
    private int numberOfReviews;

    // The date of the last review, but as a String
    private String lastReview;
    private double reviewsPerMonth;

    // The total number of listings the host holds across AirBnB
    private int calculatedHostListingsCount;

    // The total number of days in the year that the property is available for
    private int availability365;

    public AirbnbListing(String id, String name, String host_id,
                         String host_name, String neighbourhood, double latitude,
                         double longitude, String room_type, int price,
                         int minimumNights, int numberOfReviews, String lastReview,
                         double reviewsPerMonth, int calculatedHostListingsCount, int availability365) {
        this.id = id;
        this.name = name;
        this.host_id = host_id;
        this.host_name = host_name;
        this.neighbourhood = neighbourhood;
        this.latitude = latitude;
        this.longitude = longitude;
        this.room_type = room_type;
        this.price = price;
        this.minimumNights = minimumNights;
        this.numberOfReviews = numberOfReviews;
        this.lastReview = lastReview;
        this.reviewsPerMonth = reviewsPerMonth;
        this.calculatedHostListingsCount = calculatedHostListingsCount;
        this.availability365 = availability365;
    }

    /**
     * Get property ID
     * @return id
     */
    public String getId() { return id; }

    /**
     * Get property name
     * @return name
     */
    public String getName() { return name; }

    /**
     * Get host's ID
     * @return host_id
     */
    public String getHost_id() { return host_id; }

    /**
     * Get name of host
     * @return host_name
     */
    public String getHost_name() { return host_name; }

    /**
     * Get what borough the property is in
     * @return neighbourhood
     */
    public String getNeighbourhood() { return neighbourhood; }

    /**
     * Get latitude of property
     * @return latitude
     */
    public double getLatitude() { return latitude; }

    /**
     * Get longitude of property
     * @return longitude
     */
    public double getLongitude() { return longitude; }

    /**
     * Get the type of room
     * @return room_type
     */
    public String getRoom_type() { return room_type; }

    /**
     * Get price of renting the property
     * @return price
     */
    public int getPrice() { return price; }

    /**
     * Get minimum length of stay
     * @return minimumNights
     */
    public int getMinimumNights() { return minimumNights; }

    /**
     * Get number of reviews left on property
     * @return numberOfrReviews
     */
    public int getNumberOfReviews() { return numberOfReviews; }

    /**
     * Get the most recent review
     * @return lastReview
     */
    public String getLastReview() { return lastReview; }

    /**
     * Get the number of reviews left per month
     * @return reviewsPerMonth
     */
    public double getReviewsPerMonth() { return reviewsPerMonth; }

    /**
     * Get number of listings per host
     * @return calculatedHostListingsCount
     */
    public int getCalculatedHostListingsCount() { return calculatedHostListingsCount; }

    /**
     * Get number of days a year the property is available
     * @return availability365
     */
    public int getAvailability365() { return availability365; }

    /**
     * @return Property information string
     */
    @Override
    public String toString() {
        return "AirbnbListing{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", host_id='" + host_id + '\'' +
                ", host_name='" + host_name + '\'' +
                ", neighbourhood='" + neighbourhood + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", room_type='" + room_type + '\'' +
                ", price=" + price +
                ", minimumNights=" + minimumNights +
                ", numberOfReviews=" + numberOfReviews +
                ", lastReview='" + lastReview + '\'' +
                ", reviewsPerMonth=" + reviewsPerMonth +
                ", calculatedHostListingsCount=" + calculatedHostListingsCount +
                ", availability365=" + availability365 +
                '}';
    }
}
