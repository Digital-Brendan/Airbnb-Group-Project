/**
 * Represents a collection of statistics from one borough
 */ 

public class BoroughStatistic {

    //Initialise variables to hold the information parsed from the CSV file
    private String code;
    private String areaName;
    private String innerOrOuterLondon;
    private int populationEstimate;
    private double areaHectares;
    private double populationDensity;
    private double averageAge;
    private int internalMigration;
    private int internationalMigration;
    private double percentageBornAbroad;
    private double employmentRate;
    private double unemploymentRate;
    private int grossAnnualPay;
    private int activeBusinesses;
    private double crimeRateByThousand;
    private double firesByThousand;
    private double ambulanceByThousand;
    private int medianHousePrice;
    private double greenspacePercentage;
    private int totalCarbonEmissions;
    private double happinessScore;

    public BoroughStatistic(String code, String areaName, String innerOrOuterLondon, int populationEstimate,
                            double areaHectares, double populationDensity, double averageAge,
                            int internalMigration, int internationalMigration, double percentageBornAbroad, double employmentRate,
                            double unemploymentRate, int grossAnnualPay, int activeBusinesses, double crimeRateByThousand,
                            double firesByThousand, double ambulanceByThousand, int medianHousePrice, double greenspacePercentage,
                            int totalCarbonEmissions, double happinessScore) {
        this.code = code;
        this.areaName = areaName;
        this.innerOrOuterLondon = innerOrOuterLondon;
        this.populationEstimate = populationEstimate;
        this.areaHectares = areaHectares;
        this.populationDensity = populationDensity;
        this.averageAge = averageAge;
        this.internalMigration = internalMigration;
        this.internationalMigration = internationalMigration;
        this.percentageBornAbroad = percentageBornAbroad;
        this.employmentRate = employmentRate;
        this.unemploymentRate = unemploymentRate;
        this.grossAnnualPay = grossAnnualPay;
        this.activeBusinesses = activeBusinesses;
        this.crimeRateByThousand = crimeRateByThousand;
        this.firesByThousand = firesByThousand;
        this.ambulanceByThousand = ambulanceByThousand;
        this.medianHousePrice = medianHousePrice;
        this.greenspacePercentage = greenspacePercentage;
        this.totalCarbonEmissions = totalCarbonEmissions;
        this.happinessScore = happinessScore;
    }

    /**
     *
     * @return code
     */
    public String getCode() { return code; }

    /**
     *
     * @return areaName
     */
    public String getAreaName() { return areaName; }

    /**
     *
     * @return innerOrOuterLondon
     */
    public String getInnerOrOuterLondon() { return innerOrOuterLondon; }

    /**
     *
     * @return populationEstimate
     */
    public int getPopulationEstimate() { return populationEstimate; }

    /**
     *
     * @return areaHectares
     */
    public double getAreaHectares() { return areaHectares; }

    /**
     *
     * @return populationDensity
     */
    public double getPopulationDensity() { return populationDensity; }

    /**
     *
     * @return averageAge
     */
    public double getAverageAge() { return averageAge; }

    /**
     *
     * @return internalMigration
     */
    public int getInternalMigration() { return internalMigration; }

    /**
     *
     * @return internationalMigration
     */
    public int getInternationalMigration() { return internationalMigration; }

    /**
     *
     * @return percentageBornAbroad
     */
    public double getPercentageBornAbroad() { return percentageBornAbroad; }

    /**
     *
     * @return employmentRate
     */
    public double getEmploymentRate() { return employmentRate; }

    /**
     *
     * @return unemploymentRate
     */
    public double getUnemploymentRate() { return unemploymentRate; }

    /**
     *
     * @return grossAnnualPay
     */
    public int getGrossAnnualPay() { return grossAnnualPay; }

    /**
     *
     * @return activeBusinesses
     */
    public int getActiveBusinesses() { return activeBusinesses; }

    /**
     *
     * @return crimeRateByThousand
     */
    public double getCrimeRateByThousand() { return crimeRateByThousand; }

    /**
     *
     * @return firesByThousand
     */
    public double getFiresByThousand() { return firesByThousand; }

    /**
     *
     * @return ambulanceByThousand
     */
    public double getAmbulanceByThousand() { return ambulanceByThousand; }

    /**
     *
     * @return medianHousePrice
     */
    public int getMedianHousePrice() { return medianHousePrice; }

    /**
     *
     * @return greenspacePercentage
     */
    public double getGreenspacePercentage() { return greenspacePercentage; }

    /**
     *
     * @return totalCarbonEmissions
     */
    public int getTotalCarbonEmissions() { return totalCarbonEmissions; }

    /**
     *
     * @return happinessScore
     */
    public double getHappinessScore() { return happinessScore; }
}
