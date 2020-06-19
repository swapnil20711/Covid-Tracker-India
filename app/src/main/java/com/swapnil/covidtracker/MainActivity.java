package com.swapnil.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView recover;
    private TextView death;
    private TextView confirmed;
    private TextView active;
    private RequestQueue mQueue;
    private TextView updated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recover = findViewById(R.id.covid_recover);
        confirmed = findViewById(R.id.covid_confirmed);
        active = findViewById(R.id.covid_active);
        death = findViewById(R.id.covid_death);
        updated=findViewById(R.id.last_updated);
//        btn = findViewById(R.id.parse);
//        btn_reset = findViewById(R.id.reset);
        mQueue = Volley.newRequestQueue(this);

        String url = "https://api.covid19india.org/data.json";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray statewise = response.getJSONArray("statewise");
                    JSONObject country_india = statewise.getJSONObject(0);
                    String active_now = country_india.getString("active");
                    String confirm = country_india.getString("confirmed");
                    String total_deaths = country_india.getString("deaths");
                    String time = country_india.getString("lastupdatedtime");
                    String recovered = country_india.getString("recovered");
                    recover.setText(recovered + "");
                    death.setText(total_deaths + "");
                    confirmed.setText(confirm + "");
                    active.setText(active_now + "");
                    updated.setText(time+"");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showData();
//            }
//
//            private void showData() {
//
//            }
//        });
//        btn_reset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                recover.setText("");
//                death.setText("");
//            }
//        });
    }
}
