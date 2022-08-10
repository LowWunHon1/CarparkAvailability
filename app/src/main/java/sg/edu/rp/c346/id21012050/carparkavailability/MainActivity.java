package sg.edu.rp.c346.id21012050.carparkavailability;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    ListView lvCarpark;
    Spinner spnLotType;
    AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCarpark = findViewById(R.id.listViewCarpark);
        spnLotType = findViewById(R.id.spinnerLotType);
        client = new AsyncHttpClient();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<Carpark> alCarkpark = new ArrayList<Carpark>();
        ArrayAdapter<Carpark> aaCarpark = new ArrayAdapter<Carpark>(this, android.R.layout.simple_list_item_1, alCarkpark);
        CustomAdapter caCarpark = new CustomAdapter(this, R.layout.row, alCarkpark);

        client.get("https://api.data.gov.sg/v1/transport/carpark-availability", new JsonHttpResponseHandler() {

            String total_lots;
            String lot_type;
            String lots_available;
            String carpark_number;
            String update_datetime;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray jsonArrItems = response.getJSONArray("items");
                    JSONObject firstObj = jsonArrItems.getJSONObject(0);
                    JSONArray jsonArrCarParkData = firstObj.getJSONArray("carpark_data");

                    for (int i = 0; i < jsonArrCarParkData.length(); i++) {
                        JSONObject firstObj2 = jsonArrCarParkData.getJSONObject(i);
                        JSONArray jsonArrCarParkInfo = firstObj2.getJSONArray("carpark_info");
                        JSONObject jsonObjForecast = jsonArrCarParkInfo.getJSONObject(0);
                        carpark_number = firstObj2.getString("carpark_number");
                        update_datetime = firstObj2.getString("update_datetime");
                        total_lots = jsonObjForecast.getString("total_lots");
                        lot_type = jsonObjForecast.getString("lot_type");
                        lots_available = jsonObjForecast.getString("lots_available");
                        Carpark carpark = new Carpark(total_lots, lot_type, lots_available, carpark_number, update_datetime);
                        alCarkpark.add(carpark);
                    }
                } catch (JSONException e) {

                }

                lvCarpark.setAdapter(caCarpark);
                caCarpark.notifyDataSetChanged();
            }
        });
    }
}