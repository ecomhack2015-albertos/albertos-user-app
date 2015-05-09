package com.estimote.examples.demos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Shows the menu for the current date.
 *
 * @author wiktor@estimote.com (Wiktor Gworek)
 */
public class ShowMenuActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.menu);

    List valueList = new ArrayList<>();
    valueList.add(new FoodItem("Pizza Tonno", "101", 790));
    valueList.add(new FoodItem("Pizza Salame", "102", 790));
    valueList.add(new FoodItem("Spaghetti Napoli", "201", 890));

    ListView myListView = (ListView) findViewById(R.id.listView);
    ListAdapter menuItemsAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, valueList);
    myListView.setAdapter(menuItemsAdapter);


    findViewById(R.id.notify_demo_button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(ShowMenuActivity.this, ListBeaconsActivity.class);
        intent.putExtra(ListBeaconsActivity.EXTRAS_TARGET_ACTIVITY, NotifyDemoActivity.class.getName());
        startActivity(intent);
      }
    });

  }
}
