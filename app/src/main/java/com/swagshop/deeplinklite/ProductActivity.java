package com.swagshop.deeplinklite;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {

    private int product_id;
    private AppEventsLogger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Getting intent value
        Intent intent = getIntent();
        this.product_id = intent.getIntExtra("productid", 1);
        String message = "Now showing product ID : " + String.valueOf(this.product_id);
        ((TextView) findViewById(R.id.product_information)).setText(message);

        //Init Facebook App event
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        FacebookSdk.setIsDebugEnabled(true);
        FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS);
        this.logger = AppEventsLogger.newLogger(this);

        //Viewcontent App event
        Bundle parameters = new Bundle();
        parameters.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, "product");
        parameters.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, "[" + String.valueOf(this.product_id) + "]");
        parameters.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, "USD");
        this.logger.logEvent(AppEventsConstants.EVENT_NAME_VIEWED_CONTENT, this.getPrice(), parameters);

        //AddToCart Button listener
        FloatingActionButton addToCartFab = (FloatingActionButton) findViewById(R.id.addToCart);
        addToCartFab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.addToCart:
                Snackbar snackbar = Snackbar
                        .make(getWindow().getDecorView().getRootView(), "Add to cart!", Snackbar.LENGTH_LONG);
                snackbar.show();
                Bundle parameters = new Bundle();
                parameters.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, "product");
                parameters.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, "[" + String.valueOf(this.product_id) + "]");
                parameters.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, "USD");
                this.logger.logEvent(AppEventsConstants.EVENT_NAME_ADDED_TO_CART, this.getPrice(), parameters);
                break;
        }
    }

    private double getPrice() {
        return this.product_id * 2.00;
    }

}
