package com.swagshop.deeplinklite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;

import com.facebook.applinks.AppLinkData;

import android.net.Uri;


public class    MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Deep link implementation
        //e.g. seapac://mobileapps?id=2 on App Ads Helper
        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();
        if (null != data) {
            String product_id_str = data.getQueryParameter("id");
            if (null != product_id_str) {
                this.gotoProduct(Integer.parseInt(product_id_str));
            }
        }

        //Deferred deep link implementation
        //e.g. seapac://mobileapps?id=4 on App Ads Helper
        final MainActivity mainActivity = this;
        AppLinkData.fetchDeferredAppLinkData(this, new AppLinkData.CompletionHandler() {
            @Override
            public void onDeferredAppLinkDataFetched(AppLinkData appLinkData) {
                if (appLinkData != null) {
                    final Uri data = appLinkData.getTargetUri();
                    String productId = data.getQueryParameter("id");
                    if (productId != null) {
                        mainActivity.gotoProduct(Integer.parseInt(productId));
                    }
                } else {
                    Log.e("Deferred DL", "No deferred deep link found");
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btnProductOnClick(View view) {
        int product_id = 1;
        switch (view.getId()) {
            case R.id.btnproduct1:
                product_id = 1;
                break;
            case R.id.btnproduct2:
                product_id = 2;
                break;
            case R.id.btnproduct3:
                product_id = 3;
                break;
            case R.id.btnproduct4:
                product_id = 4;
                break;
        }

        this.gotoProduct(product_id);
    }

    private void gotoProduct(int product_id) {
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra("productid", product_id);
        this.startActivity(intent);
    }
}
