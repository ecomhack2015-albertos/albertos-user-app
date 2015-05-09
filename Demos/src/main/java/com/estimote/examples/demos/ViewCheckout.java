package com.estimote.examples.demos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

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

        ArrayList<FoodItem> valueList = cart.getItems();

        final Button payButton = (Button) findViewById(R.id.payment);
        payButton.setText(String.format("Pay now (Total: %.2f Euro)", (float) cart.getTotal() / 100));

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ViewCheckout.class);
                startActivity(intent);
            }


        });
    }
}
