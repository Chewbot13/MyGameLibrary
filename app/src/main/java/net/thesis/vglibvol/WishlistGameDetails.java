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



import static net.thesis.vglibvol.WishlistActivity.EXTRA_WDECK;
import static net.thesis.vglibvol.WishlistActivity.EXTRA_WDEVELOPERS;
import static net.thesis.vglibvol.WishlistActivity.EXTRA_WGENRES;
import static net.thesis.vglibvol.WishlistActivity.EXTRA_WGUID;
import static net.thesis.vglibvol.WishlistActivity.EXTRA_WPLATFORM;
import static net.thesis.vglibvol.WishlistActivity.EXTRA_WRELEASE_DATE;
import static net.thesis.vglibvol.WishlistActivity.EXTRA_WTITLE;




public class WishlistGameDetails extends AppCompatActivity implements View.OnClickListener {



    private WishlistViewModel wishlistViewModel;
    private NoteViewModel sgameViewModel;

    private String coltitle;
    private String coldeck;
    private String colplatform;
    private String colguid;
    private String colgenres;
    private String colrelease;
    private String coldevelopers;

    private Wishlist_Item transferedobject;

    private Wishlist_Item w;

    FloatingActionButton deletefromwish;
    FloatingActionButton movetocollection;

    //final NoteAdapter adapter = new NoteAdapter();
    private BottomNavigationView mMainNav;


    List<Wishlist_Item> allgames;

    public static NoteDatabase instance;


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(WishlistGameDetails.this, WishlistActivity.class));
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
        setContentView(R.layout.wishlist_game_details);


        //layout(tab)
        //mMainFrame =(FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.wishlist_nav);



        Menu menu = mMainNav.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        //call event list
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                                                         @Override
                                                         public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                                                             switch (menuItem.getItemId()) {

                                                                 case R.id.nav_search :
                                                                     Intent intent0 = new Intent(WishlistGameDetails.this, MainActivity.class);
                                                                     startActivity(intent0);
                                                                     break;

                                                                 case R.id.nav_collection :
                                                                     Intent intent1 = new Intent(WishlistGameDetails.this, CollectionActivity.class);
                                                                     startActivity(intent1);
                                                                     break;

                                                                 case R.id.nav_wishlist:
                                                                     //back to wishlist
                                                                     Intent intent2 = new Intent(WishlistGameDetails.this, WishlistActivity.class);
                                                                     startActivity(intent2);
                                                                     break;

                                                                 case R.id.nav_statistics :
                                                                     Intent intent3 = new Intent(WishlistGameDetails.this, StatisticsActivity.class);
                                                                     startActivity(intent3);
                                                                     break;

                                                                 case R.id.nav_settings :
                                                                     Intent intent4 = new Intent(WishlistGameDetails.this, SettingsActivity.class);
                                                                     startActivity(intent4);
                                                                     break;

                                                             }
                                                             return false;
                                                         }


                                                     }
        );



        wishlistViewModel = ViewModelProviders.of(this).get(WishlistViewModel.class);
        sgameViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);


        ImageView wgameimage = findViewById(R.id.wishlist_detail_image);
        TextView wgametitle = findViewById(R.id.wishlist_detail_title);
        TextView wgameplatforms = findViewById(R.id.wishlist_detail_platforms);
        TextView wgamemisc = findViewById(R.id.wishlist_detail_misc);

        Intent intent = getIntent();
        String title = intent.getStringExtra(EXTRA_WTITLE);
        String guid = intent.getStringExtra(EXTRA_WGUID);
        String platformlist = intent.getStringExtra(EXTRA_WPLATFORM);
        String deck = intent.getStringExtra(EXTRA_WDECK);
        String releasedate = intent.getStringExtra(EXTRA_WRELEASE_DATE);
        String genrelist = intent.getStringExtra(EXTRA_WGENRES);
        String developerlist = intent.getStringExtra(EXTRA_WDEVELOPERS);




        File f = new File("/data/user/0/net.thesis.vglibvol/app_imageDir/" + guid + ".png");

        Picasso.get().load(f).error(R.drawable.image_loading).into(wgameimage);

        wgametitle.setText(title);
        wgameplatforms.setText(platformlist);

        wgamemisc.setText("Description:\n" + deck + "\n\nRelease Date: " + releasedate + "\n\nGenres: \n" + genrelist + "\n\nDevelopers: \n" + developerlist);

        coltitle = title;
        coldeck = deck;
        colplatform = platformlist;
        colguid = guid;
        colgenres = genrelist;
        colrelease = releasedate;
        coldevelopers = developerlist;

        deletefromwish = (FloatingActionButton) findViewById(R.id.btndeletewishlist);
        deletefromwish.setOnClickListener(WishlistGameDetails.this);

        movetocollection = (FloatingActionButton) findViewById(R.id.btnmovetocollection);
        movetocollection.setOnClickListener(WishlistGameDetails.this);

    }


    @Override
    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.btndeletewishlist:


        Intent i = getIntent();


        //get selected game from table
        transferedobject = i.getParcelableExtra("wobject");

        //delete selected game from table
        wishlistViewModel.delete(transferedobject);

        //delete saved picture
        File f = new File("/data/user/0/net.thesis.vglibvol/app_imageDir/" + colguid + ".png");
        f.delete();


        //return to list
        Intent intent = new Intent(WishlistGameDetails.this, WishlistActivity.class);

        startActivity(intent);


                Toast toast = Toast.makeText(WishlistGameDetails.this,"Game removed from WishList", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

        //Log.d("TAG", String.valueOf(transferedobject));

            break;



            case R.id.btnmovetocollection:

                Intent y = getIntent();

                //get selected game from table
                transferedobject = y.getParcelableExtra("wobject");

                w = transferedobject;

                String a = w.getTitle();
                String b = w.getDescription();
                String c = w.getPlatform();
                String d = w.getGuid();
                String e = w.getGenres();
                String h = w.getRelease_date();
                String g = w.getDevelopers();

                Note note = new Note(a,b,c,d,e,h,g);

                sgameViewModel.insert(note);

                //delete selected game from table
                wishlistViewModel.delete(transferedobject);

                //delete saved picture
                File fil = new File("/data/user/0/net.thesis.vglibvol/app_imageDir/" + colguid + ".png");
                fil.delete();


                //return to list
                Intent intent2 = new Intent(WishlistGameDetails.this, WishlistActivity.class);

                startActivity(intent2);


                Toast toast2 = Toast.makeText(WishlistGameDetails.this,"Game moved to Collection", Toast.LENGTH_LONG);
                toast2.setGravity(Gravity.CENTER, 0, 0);
                toast2.show();

                break;



    }

    }


}
