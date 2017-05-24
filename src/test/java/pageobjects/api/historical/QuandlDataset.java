package pageobjects.api.historical;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by dannyl on 2017-05-19.
 */

/**
 Class to store the different types of data that are retrieved from the Quandl api
 */
public class QuandlDataset {
    private ArrayList<String> closingPrices = new ArrayList<String>();
    private String rawData;

    JSONParser parser = new JSONParser();
    // Fills the data-types initialized above with it's corresponding data
    public QuandlDataset(String input, String type){
        try {

            JSONObject json = (JSONObject) parser.parse(input);
            // Create a JSONObject containing all the stock information (To understand better reference the json file that the quandl api outputs)
            JSONObject dataSet = (JSONObject) json.get("dataset_data");
            rawData = dataSet.toString();
            // Parses through the JSONObject json for all the values under the key "data"
            JSONArray tempClosingPrice = (JSONArray) dataSet.get("data");
            Iterator<JSONArray> iterator = tempClosingPrice.iterator();

            // Store the closing prices taken from tempClosingPrice into closingPrices
            while (iterator.hasNext()){
                JSONArray handler = iterator.next();
                closingPrices.add(handler.get(1).toString());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // Method to return ArrayList of closingPrices
    public ArrayList<String> getClosingPrices(){

        return closingPrices;
    }
}
