package com.ecomhack.albertos.app;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.estimote.examples.demos.R;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

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
    Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.checkout);

        cart = AlfredosUserApplication.cart;

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
                URL url = new URL(AlfredosUserApplication.ApiURL + "/orders");
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(url.toString());

                ArrayList<NameValuePair> postParams = new ArrayList<NameValuePair>();
                postParams.add(new BasicNameValuePair("user", "a29eda3c-5cc2-4afc-bd37-6d2723fc5551"));
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
