package net.thesis.vglibvol;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.util.List;
import static net.thesis.vglibvol.CollectionActivity.EXTRA_SDECK;
import static net.thesis.vglibvol.CollectionActivity.EXTRA_SDEVELOPERS;
import static net.thesis.vglibvol.CollectionActivity.EXTRA_SGENRES;
import static net.thesis.vglibvol.CollectionActivity.EXTRA_SGUID;
import static net.thesis.vglibvol.CollectionActivity.EXTRA_SPLATFORM;
import static net.thesis.vglibvol.CollectionActivity.EXTRA_SRELEASE_DATE;
import static net.thesis.vglibvol.CollectionActivity.EXTRA_STITLE;


public class CollectionGameDetails extends AppCompatActivity implements View.OnClickListener {


    private NoteViewModel noteViewModel;
    private String coltitle;
    private String coldeck;
    private String colplatform;
    private String colguid;
    private String colgenres;
    private String colrelease;
    private String coldevelopers;

    private Note transferedobject;

    FloatingActionButton deletefromlib;
    private NoteAdapter adapter;

    private BottomNavigationView mMainNav;

    List<Note> allgames;

    public static NoteDatabase instance;


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(CollectionGameDetails.this, CollectionActivity.class));
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
        setContentView(R.layout.collection_game_details);

        //mMainFrame =(FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);

        //final EditText search =(EditText) findViewById(R.id.collection_search);

        Menu menu = mMainNav.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        //call event list
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                                                         @Override
                                                         public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                                                             switch (menuItem.getItemId()) {

                                                                 case R.id.nav_search:
                                                                     Intent intent0 = new Intent(CollectionGameDetails.this, MainActivity.class);
                                                                     startActivity(intent0);

                                                                     break;

                                                                 case R.id.nav_collection:
                                                                     //back to collection activity
                                                                     Intent intent1 = new Intent(CollectionGameDetails.this, CollectionActivity.class);
                                                                     startActivity(intent1);
                                                                     break;

                                                                 case R.id.nav_wishlist:
                                                                     Intent intent2 = new Intent(CollectionGameDetails.this, WishlistActivity.class);
                                                                     startActivity(intent2);
                                                                     break;

                                                                 case R.id.nav_statistics:
                                                                     Intent intent3 = new Intent(CollectionGameDetails.this, StatisticsActivity.class);
                                                                     startActivity(intent3);
                                                                     break;

                                                                 case R.id.nav_settings:
                                                                     Intent intent4 = new Intent(CollectionGameDetails.this, SettingsActivity.class);
                                                                     startActivity(intent4);
                                                                     break;

                                                             }
                                                             return false;
                                                         }


                                                     }
        );



        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);


        ImageView sgameimage = findViewById(R.id.collection_detail_image);
        TextView sgametitle = findViewById(R.id.collection_detail_title);
        TextView sgameplatforms = findViewById(R.id.collection_detail_platforms);
        TextView sgamemisc = findViewById(R.id.collection_detail_misc);

        Intent intent = getIntent();
        String title = intent.getStringExtra(EXTRA_STITLE);
        String guid = intent.getStringExtra(EXTRA_SGUID);
        String platformlist = intent.getStringExtra(EXTRA_SPLATFORM);
        String deck = intent.getStringExtra(EXTRA_SDECK);
        String releasedate = intent.getStringExtra(EXTRA_SRELEASE_DATE);
        String genrelist = intent.getStringExtra(EXTRA_SGENRES);
        String developerlist = intent.getStringExtra(EXTRA_SDEVELOPERS);




        File f = new File("/data/user/0/net.thesis.vglibvol/app_imageDir/" + guid + ".png");

        Picasso.get().load(f).error(R.drawable.image_loading).into(sgameimage);

        sgametitle.setText(title);
        sgameplatforms.setText(platformlist);

        sgamemisc.setText("Description:\n" + deck + "\n\nRelease Date: " + releasedate + "\n\nGenres: \n" + genrelist + "\n\nDevelopers: \n" + developerlist);

        coltitle = title;
        coldeck = deck;
        colplatform = platformlist;
        colguid = guid;
        colgenres = genrelist;
        colrelease = releasedate;
        coldevelopers = developerlist;

        deletefromlib = (FloatingActionButton) findViewById(R.id.btndeletecollection);
        deletefromlib.setOnClickListener(CollectionGameDetails.this);

    }


    @Override
    public void onClick(View v) {

        Intent i = getIntent();


        //get selected game from table
        transferedobject = i.getParcelableExtra("object");

        //delete selected game from table
        noteViewModel.delete(transferedobject);

        //delete saved picture
        File f = new File("/data/user/0/net.thesis.vglibvol/app_imageDir/" + colguid + ".png");
        f.delete();


        //return to list
        Intent intent = new Intent(CollectionGameDetails.this, CollectionActivity.class);

        startActivity(intent);


        Toast toast = Toast.makeText(CollectionGameDetails.this,"Game removed from Collection", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        //Log.d("TAG", String.valueOf(transferedobject));


    }

}
