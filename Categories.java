import java.io.*;

public class Categories {
    public static final double[] CENTERS = { 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90 };

    private TempCategory[] categories;
    private String dataLabel;

    public Categories(String dataLabel) {
        categories = new TempCategory[CENTERS.length];
        for(int i=0, length=CENTERS.length; i<length; ++i) {
            categories[i] = new TempCategory(getCategoryName(i));
        }
        this.dataLabel = dataLabel;
    }

    /*
     * Returns the name of the category for CENTER i
    */
    private static String getCategoryName(int i) {
        return Double.toString(CENTERS[i]);
    }

    /*
     * Determines which category the key belongs in
     * then adds it.
     *
     */
    public void addKey(String key, double temp) {
        int index = findCategoryIndex(temp);
        categories[index].addKey(key);
    }

    /*
     * Returns the index of the center temp is
     * closest to
    */
    private static int findCategoryIndex(double temp) {
        // Find the index of the center temp is closest to
        int index = 0;
        for(int i=1, length=CENTERS.length; i<length; ++i)
            if(Math.abs(temp - CENTERS[i]) < Math.abs(temp - CENTERS[index]))
                index = i;

        return index;
    }

    /*
     * Serializes the categories so they can persist
     * for the cruncher to grab them
    */
    public void writeCategories() {
        for(int i=0, length=categories.length; i<length; ++i) {
            writeCategory(categories[i]);
        }
    }

    public static TempCategory getCategory(String dataLabel, double temp) {
        int index = findCategoryIndex(temp);
        String categoryName = getCategoryName(index);
        return readCategory(dataLabel, categoryName);
    }

    private void writeCategory(TempCategory category) {
        try (
                FileOutputStream fileOut = new FileOutputStream("Data/" + dataLabel + "_" + category.getName());
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
        ) {
            out.writeObject(category);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private static TempCategory readCategory(String dataLabel, String name) {
        try (
                FileInputStream fileIn = new FileInputStream("Data/" + dataLabel + "_" +  name);
                ObjectInputStream in = new ObjectInputStream(fileIn);
        ) {
            return (TempCategory)in.readObject();
        } catch(IOException i) {
            i.printStackTrace();
            return null;
        } catch(ClassNotFoundException e) {
            System.out.println("BTree class not found");
            e.printStackTrace();
            return null;
        }
    }
}
