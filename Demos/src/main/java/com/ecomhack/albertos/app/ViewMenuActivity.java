package com.ecomhack.albertos.app;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.estimote.examples.demos.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Shows the menu for the current date.
 *
 * @author Team Alfredos
 */
public class ViewMenuActivity extends Activity {

  protected ArrayList<FoodItem> products = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.menu);

    new GetProductsTask().execute();

    final Button orderButton = (Button) findViewById(R.id.order);

    orderButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), ViewCartActivity.class);
        startActivity(intent);
      }
    });

  }

  private class GetProductsTask extends AsyncTask<String, Void, Integer> {

    protected Integer doInBackground(String... params) {
      try {
        URL url = new URL(AlbertosUserApplication.ApiURL + "/products");
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url.toString());
        HttpResponse response = client.execute(get);
        String json = EntityUtils.toString(response.getEntity());
        JSONObject jsonResponse = new JSONObject(json);
        for (int i = 0; i < jsonResponse.getJSONArray("foods").length(); i++) {
          products.add(getFoodItemFromJson(jsonResponse.getJSONArray("foods").getJSONObject(i)));
        }
      } catch (IOException | JSONException e) {
        e.printStackTrace();
      }
      return 0;
    }

    private FoodItem getFoodItemFromJson(JSONObject json) throws JSONException {
      return new FoodItem(json.getString("name"), json.getString("id"), (int)json.getDouble("price") * 100);
    }

    protected void onPostExecute(Integer result) {
      fillProductList();
    }

    protected void fillProductList()
    {
      final Cart cart = AlbertosUserApplication.cart;
      final Button orderButton = (Button) findViewById(R.id.order);

      LinearLayout productsContainer = (LinearLayout) findViewById(R.id.products);
      //foreach
      for (final FoodItem item : products) {
        Button button = new Button(getApplicationContext());
        button.setText(item.toString());
        orderButton.setText(String.format("Go To Cart (Total: %.2f €)", (float) cart.getTotal() / 100));
        button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
              cart.addItem(item);
              orderButton.setText(String.format("Go To Cart (Total: %.2f €)", (float) cart.getTotal() / 100));
              Toast.makeText(getApplicationContext(), String.format("%s was added to your cart.", item.getName()), Toast.LENGTH_SHORT).show();
            }
          }
        );
        productsContainer.addView(button);
      }
    }

  }
}

