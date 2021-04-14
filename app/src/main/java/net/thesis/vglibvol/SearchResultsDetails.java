package net.thesis.vglibvol;

        import android.arch.lifecycle.Observer;
        import android.arch.lifecycle.ViewModelProviders;
        import android.content.Context;
        import android.content.ContextWrapper;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.drawable.Drawable;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.design.widget.BottomNavigationView;
        import android.support.design.widget.FloatingActionButton;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatDelegate;
        import android.text.TextUtils;
        import android.util.Log;
        import android.view.Gravity;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;
        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.Volley;
        import com.squareup.picasso.MemoryPolicy;
        import com.squareup.picasso.Picasso;
        import com.squareup.picasso.Target;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;
        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;


        import static net.thesis.vglibvol.SearchResults.EXTRA_CREATOR;
        import static net.thesis.vglibvol.SearchResults.EXTRA_LIKES;
        import static net.thesis.vglibvol.SearchResults.EXTRA_URL;
        import static net.thesis.vglibvol.SearchResults.EXTRA_GUID;


public class SearchResultsDetails extends AppCompatActivity implements View.OnClickListener {


    private NoteViewModel noteViewModel; //define db model
    private WishlistViewModel wishlistViewModel;
    private RequestQueue mRequestQueue;
    TextView textViewDeck;

    FloatingActionButton addtolib;
    FloatingActionButton addtowish;


    //variables for saving to db
    private String sgamedeck = new String();
    public String sgamename = new String();
    private String sgameplatform = new String();
    private  String sgamegenres = new String();
    private String sgamedevelopers = new String();
    private String sgamerelease_date = new String();

    private List<String> guidcheck;
    private List<String> guidcheckwish;

    private String imagedownloadURL;
    private String imagenamebyguid;

    private BottomNavigationView mMainNav;

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(SearchResultsDetails.this, SearchResults.class));
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
        setContentView(R.layout.activity_details);


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
                                                                     //return to main activity
                                                                     Intent intent0 = new Intent(SearchResultsDetails.this, MainActivity.class);
                                                                     startActivity(intent0);
                                                                     break;

                                                                 case R.id.nav_collection :
                                                                     //mMainNav.setItemBackgroundResource(R.color.colorGray);
                                                                     Intent intent1 = new Intent(SearchResultsDetails.this, CollectionActivity.class);
                                                                     startActivity(intent1);
                                                                     break;

                                                                 case R.id.nav_wishlist:
                                                                     Intent intent2 = new Intent(SearchResultsDetails.this, WishlistActivity.class);
                                                                     startActivity(intent2);
                                                                     break;

                                                                 case R.id.nav_statistics :
                                                                     Intent intent3 = new Intent(SearchResultsDetails.this, StatisticsActivity.class);
                                                                     startActivity(intent3);
                                                                     break;

                                                                 case R.id.nav_settings :
                                                                     Intent intent4 = new Intent(SearchResultsDetails.this, SettingsActivity.class);
                                                                     startActivity(intent4);
                                                                     break;

                                                             }
                                                             return false;
                                                         }


                                                     }
        );


        mRequestQueue = Volley.newRequestQueue(this);

        printsearcheddetails();

        addtolib = (FloatingActionButton) findViewById(R.id.btnaddtocollection);
        addtolib.setOnClickListener(SearchResultsDetails.this);

        addtowish = (FloatingActionButton) findViewById(R.id.btnaddtowishlist);
        addtowish.setOnClickListener(SearchResultsDetails.this);

        //lifesaver
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        wishlistViewModel = ViewModelProviders.of(this).get(WishlistViewModel.class);

        noteViewModel.getTitles_guid();
        guidcheck = noteViewModel.getTitles_guid();

        wishlistViewModel.getTitles_guid();
        guidcheckwish = wishlistViewModel.getTitles_guid();

        noteViewModel.getLAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> sgame) {


                noteViewModel.getTitles();

            }
        });

    }


    private void printsearcheddetails() {

        //transfer the searched details
        Intent intent = getIntent();

        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String Name = intent.getStringExtra(EXTRA_CREATOR);
        String platformlist = intent.getStringExtra(EXTRA_LIKES);

        sgamename = Name;

        sgameplatform = platformlist;




        String guid = intent.getStringExtra(EXTRA_GUID);


        String url = "https://www.giantbomb.com/api/game/" + guid + "/?api_key=20f8b23e1d9e24646b2bcfc0e049d5cc9c84dbba&format=json&field_list=deck,original_release_date,genres,developers,expected_release_year,expected_release_month,expected_release_day";
        //Log.d("TAG", String.valueOf(url)); //log test for url creation based on search

        imagedownloadURL = imageUrl;
        imagenamebyguid = guid;


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject detailsobject = response.getJSONObject("results");

                            //genre_get_start
                            ArrayList<String> genreCount = new ArrayList<>(); //arraylist for genre names
                            //check if platform returns null
                            if (!detailsobject.isNull("genres")) {


                                JSONArray genres = detailsobject.getJSONArray("genres");


                                for (int i = 0; i < genres.length(); i++) {

                                    JSONObject genreName = genres.getJSONObject(i);
                                    genreCount.add(genreName.getString("name"));

                                }
                            } else {
                                genreCount.add("N/A");
                            }

                            String genrelist = TextUtils.join(" / ", genreCount); //convert genre names arraylist to string
                            //gernre get_end


                            //developer_get_start
                            ArrayList<String> developerCount = new ArrayList<>(); //arraylist for developer names
                            //check if platform returns null
                            if (!detailsobject.isNull("developers")) {


                                JSONArray developers = detailsobject.getJSONArray("developers");


                                for (int j = 0; j < developers.length(); j++) {

                                    JSONObject developerName = developers.getJSONObject(j);
                                    developerCount.add(developerName.getString("name"));

                                }
                            } else {
                                developerCount.add("N/A");
                            }

                            String developerlist = TextUtils.join(" / ", developerCount); //convert developer names arraylist to string
                            //developer get_end


                            String deckm = detailsobject.getString("deck");  //get deck


                            String release = new String();

                            String releasefix = new String();
                            if (!detailsobject.isNull("original_release_date")) {
                                release = detailsobject.getString("original_release_date"); //get release date
                                releasefix = release.substring(0, Math.min(release.length(), 10)); //get first 10 characters of release date
                            } else if (!detailsobject.isNull("expected_release_year") || !detailsobject.isNull("expected_release_month") || !detailsobject.isNull("expected_release_day")) {
                                int year = detailsobject.getInt("expected_release_year");
                                int month = detailsobject.getInt("expected_release_month");
                                String monthfix;
                                if (month <= 9) {
                                    monthfix = "0" + String.valueOf(month);
                                } else {
                                    monthfix = String.valueOf(month);
                                }

                                int day = detailsobject.getInt("expected_release_day");
                                releasefix = String.valueOf(year) + "-" + monthfix + "-" + String.valueOf(day);  //get full date
                            } else {
                                releasefix = "N/A";
                            }


                            textViewDeck.setText("Description:\n" + deckm + "\n\nRelease Date: " + releasefix + "\n\nGenres: \n" + genrelist + "\n\nDevelopers: \n" + developerlist);

                            sgamedeck = deckm;
                            sgamegenres = genrelist;
                            sgamedevelopers = developerlist;
                            sgamerelease_date = releasefix;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);


        ImageView imageView = findViewById(R.id.searched_detail_image);
        TextView textViewCreator = findViewById(R.id.searched_detail_title);
        TextView textViewLikes = findViewById(R.id.searched_detail_platforms);


        textViewDeck = findViewById(R.id.searched_detail_misc);
        //Log.d("TAG", String.valueOf(textViewDeck));


        Picasso.get().load(imageUrl).error(R.drawable.image_loading).fit().centerInside().into(imageView);
        textViewCreator.setText(Name);
        textViewLikes.setText("Platforms: " + platformlist);


    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {

            case R.id.btnaddtocollection:
        Note sgame = new Note(sgamename, sgamedeck, sgameplatform, imagenamebyguid, sgamegenres, sgamerelease_date, sgamedevelopers);


        if (!guidcheck.contains(imagenamebyguid) && !guidcheckwish.contains(imagenamebyguid)) {
            noteViewModel.insert(sgame);
            Toast toast = Toast.makeText(SearchResultsDetails.this,"Game added to Collection", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Intent back = new Intent(SearchResultsDetails.this, SearchResults.class);


            Picasso.get().load(imagedownloadURL).error(R.drawable.image_loading).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(picassoImageTarget(getApplicationContext(), "imageDir", imagenamebyguid+".png"));

           startActivity(back);
        }





        if (guidcheck.contains(imagenamebyguid)) {

            Toast toast = Toast.makeText(SearchResultsDetails.this,"Game already saved to Collection", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }


        if (guidcheckwish.contains(imagenamebyguid)) {

            Toast toast = Toast.makeText(SearchResultsDetails.this,"Game already saved to WishList", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }



        noteViewModel.getTitles();



        //Log.d("TAG", sgamename+sgamedeck);
        Log.d("TAG", guidcheck.toString());
        Log.d("TAG", sgamename);
        break;

            case R.id.btnaddtowishlist:

                Wishlist_Item wgame = new Wishlist_Item(sgamename, sgamedeck, sgameplatform, imagenamebyguid, sgamegenres, sgamerelease_date, sgamedevelopers);


                if (!guidcheckwish.contains(imagenamebyguid) && !guidcheck.contains(imagenamebyguid)) {
                    wishlistViewModel.insert(wgame);
                    Toast toast = Toast.makeText(SearchResultsDetails.this,"Game added to WishList", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    Intent back = new Intent(SearchResultsDetails.this, SearchResults.class);


                    Picasso.get().load(imagedownloadURL).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(picassoImageTarget(getApplicationContext(), "imageDir", imagenamebyguid+".png"));

                    startActivity(back);
                }

                if (guidcheckwish.contains(imagenamebyguid)) {

                    Toast toast = Toast.makeText(SearchResultsDetails.this,"Game already saved to WishList", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }


                if (guidcheck.contains(imagenamebyguid)) {

                    Toast toast = Toast.makeText(SearchResultsDetails.this,"Game already saved to Collection", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }


                wishlistViewModel.getTitles();

                break;

     }

    }


    //image save
    private Target picassoImageTarget(Context context, final String imageDir, final String imageName) {
        Log.d("picassoImageTarget", " picassoImageTarget");
        ContextWrapper cw = new ContextWrapper(context);
        final File directory = cw.getDir(imageDir, Context.MODE_PRIVATE); // path to /data/data/yourapp/app_imageDir
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final File myImageFile = new File(directory, imageName); // Create image file
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(myImageFile);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.i("image", "image saved to >>>" + myImageFile.getAbsolutePath());

                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable != null) {
                }
            }

        };




    }


}