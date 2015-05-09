package com.estimote.examples.demos;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class ViewCartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_cart);

        final Cart cart = AlfredosUserApplication.cart;
        final Button orderButton = (Button) findViewById(R.id.order);

        orderButton.setText(String.format("Place Order (Total: %.2f €)", (float)cart.getTotal() / 100));

    }

}
