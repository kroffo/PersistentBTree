import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataReader {
    // The token to be used for the NOAA web service
    private static final String NOAA_CDO_TOKEN = "PkJxCuNPHVsGDiMKtHgShrjsTyCVuXbK";
    public static final int START_YEAR = 2006;
    public static final int END_YEAR = 2015;
    // Add &stationdid=<stationid> to this to make a proper request
    private static final String BASE_URL = "https://www.ncdc.noaa.gov/cdo-web/api/v2/data?datasetid=GSOM&datatypeid=TAVG&startdate=" + START_YEAR + "-01-01&enddate=" + END_YEAR + "-12-01&units=standard&limit=1000";   
    
    public static String readData(String stationid) {
        URL url = null;
        HttpURLConnection connection = null;
        // Try to read in the data for the requested url, and return the response as a string.
        try {
            url = new URL(BASE_URL + "&stationid=" + stationid); 
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "text/plain");
            connection.setRequestProperty("token", NOAA_CDO_TOKEN);
            
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()
                    )
            );
            
            String inputLine;
            String response = "";
            while ((inputLine = in.readLine()) != null) {
                response = response + inputLine + "\n";
            }
            in.close();
            return response;
        } catch(IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
