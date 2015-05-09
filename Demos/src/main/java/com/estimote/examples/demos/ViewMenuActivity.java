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
public class ViewMenuActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    final Cart cart = AlfredosUserApplication.cart;

    setContentView(R.layout.menu);

    // Abfrage Produktliste
    ArrayList<FoodItem> valueList = new ArrayList<>();
    valueList.add(new FoodItem("Pizza Tonno", "101", 790));
    valueList.add(new FoodItem("Pizza Salame", "102", 790));
    valueList.add(new FoodItem("Spaghetti Napoli", "201", 890));

    LinearLayout productsContainer = (LinearLayout) findViewById(R.id.products);
    final Button orderButton = (Button) findViewById(R.id.order);

    orderButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), ViewCartActivity.class);
        startActivity(intent);
      }
    });

    // TEST //
    //testActivity code
    findViewById(R.id.buttonTestActivity).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), TestActivity.class);
        startActivity(intent);
      }
    });

    //foreach
    for (final FoodItem item:valueList) {
      Button button = new Button(getApplicationContext());
      button.setText(item.toString());
      orderButton.setText(String.format("Go To Cart (Total: %.2f €)", (float) cart.getTotal() / 100));
      button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            cart.addItem(item);
            orderButton.setText(String.format("Go To Cart (Total: %.2f €)", (float)cart.getTotal() / 100));
            Toast.makeText(getApplicationContext(), String.format("%s was added to your cart.", item.getName()), Toast.LENGTH_SHORT).show();
          }
        }
      );
      productsContainer.addView(button);
    }

  }
}
