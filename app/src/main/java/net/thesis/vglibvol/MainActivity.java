package net.thesis.vglibvol;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import android.view.View.OnKeyListener;
import android.view.KeyEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity{

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;


    //declare user input edittext
    public static EditText searchText;

    //declare string for url transfer
    public static String completeurl;

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(MainActivity.this, MainActivity.class));
        finish();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //check and change theme
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        }
        else setTheme(R.style.AppTheme);
        //check and change theme

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //layout(tab)
        //mMainFrame =(FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);


        //address menu item
        Menu menu = mMainNav.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        //call event list
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.nav_search :
                        //current activity
                    break;

                    case R.id.nav_collection :
                        //mMainNav.setItemBackgroundResource(R.color.colorGray);
                        Intent intent1 = new Intent(MainActivity.this, CollectionActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.nav_wishlist:
                        Intent intent2 = new Intent(MainActivity.this, WishlistActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.nav_statistics :
                        Intent intent3 = new Intent(MainActivity.this, StatisticsActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.nav_settings :
                        Intent intent4 = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent4);
                        break;

                }
                return false;
            }


                                                     }
        );



        Button searchinitiateBtn = (Button) findViewById(R.id.searchBtn);

        final EditText useripnput = (EditText) findViewById(R.id.searchText);


        //search on enter button
        useripnput.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Intent searchIntent = new Intent(MainActivity.this, SearchResults.class);  //define intent for another activity

                    completeurl = useripnput.getText().toString();

                    if (completeurl.equals(null) || completeurl.equals("")){
                        Toast toast = Toast.makeText(MainActivity.this,"Please enter a game title or keyword.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

                    else  startActivity(searchIntent);
                    return true;
                }
                return false;
            }
        });



        searchinitiateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(MainActivity.this, SearchResults.class);  //how to set another activity

                completeurl = useripnput.getText().toString();

                if (completeurl.equals(null) || completeurl.equals("")){
                    Toast toast = Toast.makeText(MainActivity.this,"Please enter a game title or keyword!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else  startActivity(searchIntent);
            }


        });

    }


}