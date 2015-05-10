package com.ecomhack.albertos.app;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.estimote.examples.demos.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

/**
 * Shows the menu for the current date.
 *
 * @author Team Alfredos
 */

public class ViewCheckout extends Activity{
    Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.checkout);

        cart = AlbertosUserApplication.cart;

        final Button payButton = (Button) findViewById(R.id.payment);
        payButton.setText(String.format("Confirm Payment and check out with PayPal (Total: %.2f Euro)", (float) cart.getTotal() / 100));

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PlaceOrderTask().execute();
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), RecognizeBeacon.class);
                startActivity(intent);
            }


        });
    }

    private class PlaceOrderTask extends AsyncTask<String, Void, Integer> {

        protected Integer doInBackground(String... params) {
            try {
                URL url = new URL(AlbertosUserApplication.ApiURL + "/orders");
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(url.toString());

                JSONObject obj = new JSONObject();
                try {
                    JSONArray items = new JSONArray();

                    for (FoodItem item : cart.getItems()) {
                        JSONObject itemObject = new JSONObject();
                        JSONObject foodObject = new JSONObject();
                        foodObject.put("id", item.getSku());
                        foodObject.put("name", item.getName());
                        foodObject.put("price", item.getPrice());
                        itemObject.put("food", foodObject);
                        itemObject.put("quantity", 1);
                        items.put(itemObject);
                    }
                    obj.put("userId", "a29eda3c-5cc2-4afc-bd37-6d2723fc5551");
                    obj.put("lineItems", items);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                post.setEntity(new StringEntity(obj.toString()));

                HttpResponse response = client.execute(post);
                System.out.println(response.getStatusLine().getStatusCode());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        }

        protected void onPostExecute(Integer result) {

        }

    }
}
