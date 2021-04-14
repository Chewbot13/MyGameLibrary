package net.thesis.vglibvol;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    private BottomNavigationView mMainNav;
    Button delete_collection;
    Button delete_wishlist;
    Button delete_all;

    private Switch theme_switch;

    private NoteViewModel noteViewModel;
    private WishlistViewModel wishlistViewModel;

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //set theme
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        }
        else setTheme(R.style.AppTheme);
        //set theme

        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_tab);

        delete_collection = findViewById(R.id.delete_collection);
        delete_collection.setOnClickListener(SettingsActivity.this);
        delete_wishlist = findViewById(R.id.delete_wishlist);
        delete_wishlist.setOnClickListener(SettingsActivity.this);
        delete_all = findViewById(R.id.delete_all);
        delete_all.setOnClickListener(SettingsActivity.this);



        //layout(tab)
        //mMainFrame =(FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);


        Menu menu = mMainNav.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);

        //call event list
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                                                         @Override
                                                         public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                                                             switch (menuItem.getItemId()) {

                                                                 case R.id.nav_search:
                                                                     Intent intent0 = new Intent(SettingsActivity.this, MainActivity.class);
                                                                     startActivity(intent0);
                                                                     break;

                                                                 case R.id.nav_collection:
                                                                     Intent intent1 = new Intent(SettingsActivity.this, CollectionActivity.class);
                                                                     startActivity(intent1);
                                                                     break;

                                                                 case R.id.nav_wishlist:
                                                                     Intent intent2 = new Intent(SettingsActivity.this, WishlistActivity.class);
                                                                     startActivity(intent2);
                                                                     break;

                                                                 case R.id.nav_statistics:
                                                                     Intent intent3 = new Intent(SettingsActivity.this, StatisticsActivity.class);
                                                                     startActivity(intent3);
                                                                     break;

                                                                 case R.id.nav_settings:
                                                                     //current activity
                                                                     break;

                                                             }
                                                             return false;
                                                         }


                                                     }
        );



        //lifesaver
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        wishlistViewModel = ViewModelProviders.of(this).get(WishlistViewModel.class);



        theme_switch = findViewById(R.id.switch_theme);

        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            theme_switch.setChecked(true);
        }
        theme_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    restartApp();
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    restartApp();
                }
            }
        });



        }


    public void restartApp() {
        Intent i = new Intent(getApplicationContext(),SettingsActivity.class);
        startActivity(i);
        finish();
    }





    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.delete_collection:
                noteViewModel.deleteAllNotes();
                Toast toast = Toast.makeText(SettingsActivity.this, "Collection Deleted", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                break;


            case R.id.delete_wishlist:
                wishlistViewModel.deleteAllNotes();
                Toast toast1 = Toast.makeText(SettingsActivity.this, "WishList Deleted", Toast.LENGTH_LONG);
                toast1.setGravity(Gravity.CENTER, 0, 0);
                toast1.show();

                break;

            case R.id.delete_all:
                noteViewModel.deleteAllNotes();
                wishlistViewModel.deleteAllNotes();
                Toast toast3 = Toast.makeText(SettingsActivity.this, "All entries have been deleted", Toast.LENGTH_LONG);
                toast3.setGravity(Gravity.CENTER, 0, 0);
                toast3.show();

                break;



        }
    }
}
