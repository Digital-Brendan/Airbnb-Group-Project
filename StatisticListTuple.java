/**
 * A class to store the information to be displayed on the individual statistics panels in the Statistics window
 * of the Airbnb project.
 */

public class StatisticListTuple {

    // The values to be stored
    private String key;
    private String value;

    public StatisticListTuple(String key, String value){
        this.key = key;
        this.value = value;

    }

    /**
     * Get stat key
     * @return this.key
     */
    public String getKey(){
        return this.key;
    }

    /**
     * Get stat value
     * @return this.value
     */
    public String getValue(){
        return this.value;
    }

    /**
     * Set stat key
     * @param key statistic key
     */
    public void setKey(String key){
        this.key = key;
    }

    /**
     * Set stat value
     * @param value statistic value
     */
    public void getValue(String value){
        this.value = value;
    }
}
