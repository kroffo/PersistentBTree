import java.io.*;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Loader {
    private static HashMap<String> dataCache;
    private static ArrayList<String> cities;

    /*
     * Check if the tree needs to be updated,
     * and if so update both the tree and the
     * ByteStream of data it references.
     *
    */
    public static void update(String treeName) {
        // read in the cities to be loaded
        cities = new ArrayList<>();

        File f = null;
        Scanner sc = null;
        try {
            f = new File("cities.txt");
            sc = new Scanner(f);
            while( sc.hasNextLine() )
                cities.add(sc.nextLine());
        } catch (FileNotFoundException e) {
            System.out.println("The cities file could not be located.");
            System.exit(1);
        }

        if(requiresUpdate(treeName)) {
            BTree tree = new BTree(treeName);
            buildTree(tree);
        }
    }

    public static void writeTree(BTree tree) {
        try (
                FileOutputStream fileOut = new FileOutputStream("Data/" + tree.getName());
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
        ) {
            out.writeObject(tree);
            //System.out.println("Serialized tree is saved in " + tree.getName());
        } catch(IOException i) {
            i.printStackTrace();
        }
    }

    /*
     * Build the BTree from the data cache created during the update check.
     *
     * Also, build the ByteStream file full of the actual data points.
     *
     * Organizes the keys in to TempCategories based on the temperatures
     * they point to
    */
    private static void buildTree(BTree tree) {
        System.out.println("Building the BTree...");
        Categories categories = new Categories(tree.getName());
        try {
            File file = new File("Data/" + tree.getName() + "_Data");

            // When the tree is built, also build the ByteStream file where the
            // actual data is stored
            ByteWriter writer = new ByteWriter(file);

            for (int j = 0, length = cities.size(); j < length; ++j) {

                String[] splitLine = cities.get(j).split(",");
                String city = splitLine[0];
                String cityID = splitLine[1];
                String cityJSON = dataCache.get(cityID);

                // Use JSON-Java package to parse JSON response
                JSONObject cityObj = new JSONObject(cityJSON);
                JSONArray fields = cityObj.getJSONArray("results");

                for (int i = 0, numStates = fields.length(); i < numStates; ++i) {
                    double value = fields.getJSONObject(i).getDouble("value");
                    String date = fields.getJSONObject(i).getString("date");
                    String[] dateList = date.split("-");
                    String year = dateList[0];
                    String month = dateList[1];
                    String key = city + "." + year + "." + month;
                    int offset = writer.writeDouble(value);
                    tree.insert(key, offset);
                    categories.addKey(key, value);
                }

            }
            writer.close();
            categories.writeCategories();
            System.out.println("Tree created.");
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /*
     * Returns whether the tree needs to be updated
     * by comparing the data stored in the cache with responses
     * from the web service used to acquire data
     *
     * If update is necessary, the cache is updated to prevent
     * the tree builder from needing to make the calls to the
     * web service a second time this run.
     *
    */
    private static boolean requiresUpdate(String treeName) {
        boolean update = false;
        dataCache = readDataCache(treeName);
        System.out.println("Checking cache vs web service...");
        for(int i=0, length=cities.size(); i<length; ++i) {
            if (i+1 % 5 == 0) { // sleep after every fifth one to ensure we don't exceed 5 requests per second.
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Error. It is possible not all data will be acquired.");
                }
            }
            String[] splitLine = cities.get(i).split(",");
            String cityID = splitLine[1];
            String cityJSON = DataReader.readData(cityID);
            if (cityJSON == null) {
                System.out.println("An error occurred.");
                System.exit(1);
            }

            // compare the JSON response with the cached response
            String cachedJSON = dataCache.get(cityID);
            if(cachedJSON == null || !cachedJSON.equals(cityJSON)) {
                dataCache.put(cityID, cityJSON);
                if(!update) {
                    System.out.println("Update required.");
                    System.out.println("Pulling the rest of the data...");
                }
                update = true;
            }
        }

        // If an update is necessary, the data cache was modified
        // so update the stored data cache.
        if(update) {
            writeDataCache(treeName);
        } else {
            System.out.println("The tree is up to date.");
        }
        return update;
    }

    /*
     * Stores the data cache to allow checking
     * if updates are necessary in future runs
     *
     * This will allow the loader to skip BTree
     * creation when the tree already exists
     * if it is up-to-date.
    */
    private static void writeDataCache(String treeName) {
        try (
                FileOutputStream fileOut = new FileOutputStream("Data/" + treeName + "_dataCache");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
        ) {
            out.writeObject(dataCache);
        } catch(IOException i) {
            i.printStackTrace();
        }
    }

    /*
     * Loads in the data cache to check if the tree
     * needs to be updated (rebuilt)
     *
     * If no data is stored, returns an empty hash map.
    */
    private static HashMap<String> readDataCache(String treeName) {
        try (
                FileInputStream fileIn = new FileInputStream("Data/" + treeName + "_dataCache");
                ObjectInputStream in = new ObjectInputStream(fileIn);
        ) {
            return (HashMap<String>) in.readObject();
        } catch(FileNotFoundException e) {
            return new HashMap<String>();
        } catch(IOException i) {
            i.printStackTrace();
            return null;
        } catch(ClassNotFoundException e) {
            System.out.println("HashMap<String> class not found");
            e.printStackTrace();
            return null;
        }
    }
}
