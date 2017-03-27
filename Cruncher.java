import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * This class contains the functions to connect the Interface buttons
 * with the Data.
*/
public class Cruncher {
    
    /*
     * The persistent BTree used to store temperature
     * data for each city, month and year
    */
    private final BTree tree;
    
    /*
     * A list of all cities used for populating a city selector
     * in the interface and the similarity function of this class
    */
    private final ArrayList<String> cities = new ArrayList<>();
    private final String[] years;
    
    public Cruncher(String treeName) {
        tree = readTree(treeName);

        readCities();

        int max = DataReader.END_YEAR, min = DataReader.START_YEAR;
        int numYears = max-min;
        years = new String[numYears];
        for(int i=0; i<numYears; ++i)
            years[i] = Integer.toString(min + i);
    }
    /*
     * Reads in the cities to be used for the
     * interface and similar city function
    */
    private void readCities() {
        File f = null;
        Scanner sc = null;
        try {
            f = new File("cities.txt");
            sc = new Scanner(f);
        } catch (FileNotFoundException e) {
            System.out.println("The cities file could not be located.");
            System.exit(1);
        }
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] splitLine = line.split(",");
            String city = splitLine[0];
            cities.add(city);
        }
    }

    /*
     * Get a reference to the BTree
     *
     * Note that the actual nodes are not loaded
     * except when they are being used.
    */
    private static BTree readTree(String name) {
        try (
                FileInputStream fileIn = new FileInputStream("Data/" + name);
                ObjectInputStream in = new ObjectInputStream(fileIn);
        ) {
            return (BTree)in.readObject();
        } catch(IOException i) {
            i.printStackTrace();
            return null;
        } catch(ClassNotFoundException e) {
            System.out.println("BTree class not found");
            e.printStackTrace();
            return null;
        }
    }
    
    // returns the value stored in the map for a given key
    public double getValue(String key) {
        double value = -1;

        int offset = tree.search(key);
        if (offset > -1)
            try {
                File file = new File("Data/" + tree.getName() + "_Data");
                ByteReader reader = new ByteReader(file);
                value = reader.readDouble(offset);
                reader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return value;
    }
    
    /*
     * Querys the map of cities to get all data for a city,
     * then averages the temperature for each month over
     * the available years.
     *
     * These averages are returned in an array in order by month
    */
    private double[] getAverageTempsPerMonth(String city) {
        double[] avgTemps = new double[12];
        int startYear = DataReader.START_YEAR;
        int endYear = DataReader.END_YEAR;
        
        // for each month, average the values for all the years
        // and put them in the array
        for(int m=1; m<13; ++m) {
            double sum = 0;
            String month = String.format("%02d", m);
            for(int y=startYear; y<=endYear; ++y) {
                sum += getValue(city + "." + y + "." + month);
            }
            avgTemps[m-1] = sum/12;
        }
        return avgTemps;
    }
    
    /*
     * returns the sum of the differences between
     *
     * corresponding elements of avgs1 and avgs2
    */
    private double getDifferenceBetweenAverages(double[] avgs1, double[] avgs2) {
        if(avgs1.length != avgs2.length) {
            System.out.println("Error: Arrays for comparison differ in length");
            return 0;
        }
        double difference = 0;
        for(int i=0; i<avgs1.length; ++i) {
            difference += Math.abs(avgs1[i] - avgs2[i]);
        }
        return difference;
    }
    
    /* returns the most similar city to the given city
     *
     * An average temperature for each month of the year
     * is calculated by averaging a month's temperature values
     * over all the years of data.
     * This differences between each monthly average with that of
     * another city's are added to give a total difference
     * The city with the smallest difference is the most similar city
    */
    public String getMostSimilar(String city) {
        
        // get the average month temps for the given city
        double[] cityAverages = getAverageTempsPerMonth(city);
        
        // store the most similar city information here during traversal
        String mostSimilarCity = "";
        double mostSimilarValue = -1;
        
        // loop through all the cities
        String[] keyCities = this.getCities();
        for(String keyCity : keyCities) {
            
            // Don't compare the city with itself!
            if(!keyCity.equals(city)) {
                
                // get the temp averages for the city to compare
                double[] keyAverages = getAverageTempsPerMonth(keyCity);
                
                // get the similarity difference, and if it's the current smallest
                // keep track of it
                double difference = getDifferenceBetweenAverages(cityAverages, keyAverages);
                if(mostSimilarValue == -1 || difference < mostSimilarValue) {
                    mostSimilarCity = keyCity;
                    mostSimilarValue = difference;
                }
            }
        }
        return mostSimilarCity;
    }

    public String getCategory(String key) {
        TempCategory cat = Categories.getCategory(tree.getName(), getValue(key));
        return (cat != null) ? cat.getName() : "No Category";
    }

    /*
     * Returns the set of keys in the same category as
     * the supplied key
     *
    */
    public String[] getKeysInCategory(String key) {
        TempCategory cat = Categories.getCategory(tree.getName(), getValue(key));
        if(cat == null)
            return new String[0];
        return cat.getKeys();
    }
    
    /*
     * returns all cities the map holds data for
    */
    public String[] getCities() {
        return cities.toArray(new String[cities.size()]);
    }

    public String[] getYears() {
        return years;
    }
}
