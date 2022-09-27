import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;

/**
 * A class to test the base statistics object for correct values
 */
public class statisticsTest
{

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
    }

    /**
     * Tests to see if the average price method returns the correct results for an example
     * and negative tests for some empty and null values
     */
    @Test
    public void testAveragePrice(){
        Statistics stats = new Statistics();
        assertEquals(260, stats.averagePrice("Southwark"));
        assertEquals(0, stats.averagePrice(""));
        assertEquals(0, stats.averagePrice(null));
    }

    /**
     * Tests to see if the available properties method returns the correct value with an example call
     */
    @Test
    public void testAvailableProperties(){
        Statistics stats = new Statistics();
        assertEquals(53904, stats.availableProperties());
    }

    /**
     * Tests to see if the most expensive borough method returns the correct value no parameters, but
     * also with an example set of data
     */
    @Test
    public void testMostExpensiveBorough(){
        Statistics stats = new Statistics();
        HashSet<String> testSet = new HashSet<>();
        testSet.add("Lewisham");
        testSet.add("Southwark");
        testSet.add("City of London");

        assertEquals("Richmond upon Thames", stats.mostExpensiveBorough(null));
        assertEquals("City of London", stats.mostExpensiveBorough(testSet));
    }

    /**
     * Tests to see if the borough to statistic method contains the right number of entries with an example call
     */
    @Test
    public void testMapBoroughToStatistic() {
        Statistics stats = new Statistics();
        assertEquals(33, stats.mapBoroughToStatistic().size());
    }

    /**
     * Tests to see if the method to find the number of whole properties returns the correct value with an example call
     */
    @Test
    public void testWholeProperties() {
        Statistics stats = new Statistics();
        assertEquals(27175, stats.numberOfWholeProperties());
    }

    /**
     * Tests to see if the average reviews method returns the correct value with an example call
     */
    @Test
    public void testAverageReviews() {
        Statistics stats = new Statistics();
        assertEquals(12, stats.averageReviews());
    }

    /**
     * Tests to see if bonus statistics methods all return the correct information when called
     */
    @Test
    public void testBonusStatistics() {
        Statistics stats = new Statistics();
        assertEquals("City of London", stats.quietestBorough());
        assertEquals("City of London", stats.leastDangerousBorough());
        assertEquals("City of London", stats.leastPollutedBorough());
        assertEquals("Westminster", stats.bestBoroughForEmployment());
        assertEquals("Richmond upon Thames", stats.happiestBorough());
    }

    /**
     * Tests to see if the method to map the boroughs to a colour returns the correct number of entries with no parameters,
     * but also with a test set of data
     */
    @Test
    public void testBoroughColorMap() {
        Statistics stats = new Statistics();
        HashSet<String> testSet = new HashSet<>();
        testSet.add("Lewisham");
        testSet.add("Southwark");
        testSet.add("City of London");

        assertEquals(33, stats.boroughColors(null).size());
        assertEquals(3, stats.boroughColors(testSet).size());
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
}
