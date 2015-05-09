package com.estimote.examples.demos;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Shows the menu for the current date.
 *
 * @author wiktor@estimote.com (Wiktor Gworek)
 */
public class ShowMenuActivity extends Activity {

  private Cart cart;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    cart = new Cart();

    setContentView(R.layout.menu);

    // Abfrage Produktliste
    ArrayList<FoodItem> valueList = new ArrayList<>();
    valueList.add(new FoodItem("Pizza Tonno", "101", 790));
    valueList.add(new FoodItem("Pizza Salame", "102", 790));
    valueList.add(new FoodItem("Spaghetti Napoli", "201", 890));

    LinearLayout productsContainer = (LinearLayout) findViewById(R.id.products);
    final Button orderButton = (Button) findViewById(R.id.order);


    //foreach
    for (final FoodItem item:valueList) {
      Button button = new Button(getApplicationContext());
      button.setText(item.toString());
      button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            cart.addItem(item);
            orderButton.setText(String.format("Place Order (Total: %.2f €)", (float)cart.getTotal() / 100));
            Toast.makeText(getApplicationContext(), String.format("%s was added to your cart.", item.getName()), Toast.LENGTH_SHORT).show();
          }
        }
      );
      productsContainer.addView(button);
    }

  }
}
