package com.estimote.examples.demos;

import android.app.Application;

import com.estimote.sdk.EstimoteSDK;

/**
 * Main {@link Application} object for Demos. It configures EstimoteSDK.
 *
 * @author Team Alfredos
 */
public class AlfredosUserApplication extends Application {

  public static Cart cart = new Cart();

  // TODO put this into a config file
  //public static String ApiURL = "http://10.1.227.4:9000";
  public static String ApiURL = "https://albertos.herokuapp.com";

  @Override
  public void onCreate() {
    super.onCreate();

    // Initializes Estimote SDK with your App ID and App Token from Estimote Cloud.
    // You can find your App ID and App Token in the
    // Apps section of the Estimote Cloud (http://cloud.estimote.com).
    EstimoteSDK.initialize(this, "YOUR APP ID", "YOUR APP TOKEN");

    // Configure verbose debug logging.
    EstimoteSDK.enableDebugLogging(true);
  }
}
