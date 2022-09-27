import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A class to load the information from the extra information file and create objects based on the data found
 */
public class BoroughStatisticDataLoader {
 
    /** 
     * Return an ArrayList containing the rows in the extra information dataset CSV file.
     * @return boroughStatistics
     */
    public ArrayList<BoroughStatistic> load() {
        ArrayList<BoroughStatistic> boroughStatistics = new ArrayList<BoroughStatistic>();
        try{
            URL url = getClass().getResource("extrainformation.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            //skip the first row (column headers)
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                String code = line[0];
                String areaName = line[1];
                String innerOrOuterLondon = line[2];
                int populationEstimate = convertInt(line[3]);
                double areaHectares = convertDouble(line[4]);
                double populationDensity = convertDouble(line[5]);
                double averageAge = convertDouble(line[6]);
                int internalMigration = convertInt(line[7]);
                int internationalMigration = convertInt(line[8]);
                double percentageBornAbroad = convertDouble(line[9]);
                double employmentRate = convertDouble(line[10]);
                double unemploymentRate = convertDouble(line[11]);
                int grossAnnualPay = convertInt(line[12]);
                int activeBusinesses = convertInt(line[13]);
                double crimeRateByThousand = convertDouble(line[14]);
                double firesByThousand = convertDouble(line[15]);
                double ambulanceByThousand = convertDouble(line[16]);
                int medianHousePrice = convertInt(line[17]);
                double greenspacePercentage = convertDouble(line[18]);
                int totalCarbonEmissions = convertInt(line[19]);
                double happinessScore = convertDouble(line[20]);

                BoroughStatistic statistic = new BoroughStatistic (code, areaName, innerOrOuterLondon, populationEstimate,
                areaHectares, populationDensity, averageAge, internalMigration, internationalMigration,
                percentageBornAbroad, employmentRate, unemploymentRate, grossAnnualPay, activeBusinesses, crimeRateByThousand,
                firesByThousand, ambulanceByThousand, medianHousePrice, greenspacePercentage, totalCarbonEmissions, happinessScore);
                boroughStatistics.add(statistic);
            }
        } catch(IOException | URISyntaxException e){
            System.out.println("Failure! Something went wrong");
            e.printStackTrace();
        }
        return boroughStatistics;
    }

    /**
     *
     * @param doubleString the string to be converted to Double type
     * @return the Double value of the string, or -1.0 if the string is either empty or just whitespace
     */
    private Double convertDouble(String doubleString){
        if(doubleString != null && !doubleString.trim().equals("")){
            return Double.parseDouble(doubleString);
        }
        return -1.0;
    }

    /**
     *
     * @param intString the string to be converted to Integer type
     * @return the Integer value of the string, or -1 if the string is either empty or just whitespace
     */
    private Integer convertInt(String intString){
        if(intString != null && !intString.trim().equals("")){
            return Integer.parseInt(intString);
        }
        return -1;
    }
}
