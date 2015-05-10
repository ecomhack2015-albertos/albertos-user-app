package com.estimote.examples.demos;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Shows the menu for the current date.
 *
 * @author Team Alfredos
 */

public class ViewCheckout extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.checkout);

        final Cart cart = AlfredosUserApplication.cart;

        final Button payButton = (Button) findViewById(R.id.payment);
        payButton.setText(String.format("Pay now (Total: %.2f Euro)", (float) cart.getTotal() / 100));

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PlaceOrderTask().execute();
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ViewCheckout.class);
                startActivity(intent);
            }


        });
    }

    private class PlaceOrderTask extends AsyncTask<String, Void, Integer> {

        protected Integer doInBackground(String... params) {
            try {
                URL url = new URL("http://10.0.2.2:9000/orders");
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(url.toString());

                ArrayList<NameValuePair> postParams = new ArrayList<NameValuePair>();
                postParams.add(new BasicNameValuePair("user", UUID.randomUUID().toString()));
                post.setEntity(new UrlEncodedFormEntity(postParams));

                client.execute(post);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        }

        protected void onPostExecute(Integer result) {

        }

    }
}
