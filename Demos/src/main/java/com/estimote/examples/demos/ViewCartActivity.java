package com.estimote.examples.demos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewCartActivity extends Activity {

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), ViewMenuActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_cart);

        final Cart cart = AlfredosUserApplication.cart;

        ArrayList<FoodItem> valueList = cart.getItems();

        LinearLayout productsContainer = (LinearLayout) findViewById(R.id.cartReview);

        final Button checkoutButton = (Button) findViewById(R.id.checkout);

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ViewCheckout.class);
                startActivity(intent);
            }
        });

        for (final FoodItem item:valueList) {
            Button button = new Button(getApplicationContext());
            button.setText(item.toString());
            checkoutButton.setText(String.format("Place Order (Total: %.2f €)", (float) cart.getTotal() / 100));
            button.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      cart.removeItem(item);
                      checkoutButton.setText(String.format("Go To Cart (Total: %.2f €)", (float) cart.getTotal() / 100));
                      Toast.makeText(getApplicationContext(), String.format("%s was removed from your cart.", item.getName()), Toast.LENGTH_SHORT).show();
                      Intent intent = new Intent();
                      intent.setClass(getApplicationContext(), ViewCartActivity.class);
                      startActivity(intent);
                  }
              }
            );
            productsContainer.addView(button);
            checkoutButton.setText(String.format("Place Order (Total: %.2f €)", (float) cart.getTotal() / 100));
        }
    }
}
