package net.thesis.vglibvol;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;




public class SearchResults extends AppCompatActivity implements SearchedGameAdapter.OnItemClickListerner {

    JSONArray results_check;

    private BottomNavigationView mMainNav;


    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_CREATOR = "Name";
    public static final String EXTRA_LIKES = "platformslist";
    public static final String EXTRA_GUID= "guid";



    private RecyclerView mRecyclerView;
    private SearchedGameAdapter mSearchedGameAdapter;
    private ArrayList<SearchedItem> mSearchedGameList;
    private RequestQueue mRequestQueue;


    int check_if_empty;

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(SearchResults.this, MainActivity.class));
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
        setContentView(R.layout.search_results);

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
                                                                     //return to main activity
                                                                     Intent intent0 = new Intent(SearchResults.this, MainActivity.class);
                                                                     startActivity(intent0);
                                                                     break;

                                                                 case R.id.nav_collection :
                                                                     //mMainNav.setItemBackgroundResource(R.color.colorGray);
                                                                     Intent intent1 = new Intent(SearchResults.this, CollectionActivity.class);
                                                                     startActivity(intent1);
                                                                     break;

                                                                 case R.id.nav_wishlist:
                                                                     Intent intent2 = new Intent(SearchResults.this, WishlistActivity.class);
                                                                     startActivity(intent2);
                                                                     break;

                                                                 case R.id.nav_statistics :
                                                                     Intent intent3 = new Intent(SearchResults.this, StatisticsActivity.class);
                                                                     startActivity(intent3);
                                                                     break;

                                                                 case R.id.nav_settings :
                                                                     Intent intent4 = new Intent(SearchResults.this, SettingsActivity.class);
                                                                     startActivity(intent4);
                                                                     break;

                                                             }
                                                             return false;
                                                         }


                                                     }
        );


        mRequestQueue = Volley.newRequestQueue(this);



        parsesearchJSON();

            //check for input mistakes or faulty connection

            mRecyclerView = findViewById(R.id.recycler_view);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


            mSearchedGameList = new ArrayList<>();


            mSearchedGameAdapter = new SearchedGameAdapter(SearchResults.this, mSearchedGameList);  //create adapter


            mRecyclerView.setAdapter(mSearchedGameAdapter); //set adapter on recycler view
            mSearchedGameAdapter.setOnItemClickListener(SearchResults.this);



    }

    private void parsesearchJSON() {
        String url = "https://www.giantbomb.com/api/search/?api_key=20f8b23e1d9e24646b2bcfc0e049d5cc9c84dbba&format=json&query=" + MainActivity.completeurl + "&resources=game&fields=game&field_list=name,platforms,image,guid";


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {



                            JSONArray jsonArray = response.getJSONArray("results");
                            results_check = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);


                                String Name = hit.getString("name");


                                JSONObject imageObject = hit.getJSONObject("image");

                                String Icon_url = imageObject.getString("small_url");

                                String guid = hit.getString("guid");


                                ArrayList<String> likeCount = new ArrayList<>();


                                //check if platform returns null
                                if (!hit.isNull("platforms")) {


                                    JSONArray ja = hit.getJSONArray("platforms");


                                    for (int j = 0; j < ja.length(); j++) {

                                        JSONObject jsonplatform = ja.getJSONObject(j);
                                        likeCount.add(jsonplatform.getString("abbreviation"));

                                    }
                                } else {
                                    likeCount.add("N/A");
                                }

                                String platformlist = TextUtils.join(" / ", likeCount); //convert platforms arraylist to string

                                mSearchedGameList.add(new SearchedItem(Icon_url, Name, platformlist, guid));

                                Log.d("TAG", mSearchedGameList.toString());

                            }


                            mSearchedGameAdapter.notifyDataSetChanged();


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

    }

    @Override
    public void onItemClick(int position) {
        Intent searchdetailIntent = new Intent(this, SearchResultsDetails.class );


        SearchedItem clickedItem = mSearchedGameList.get(position);

        searchdetailIntent.putExtra(EXTRA_URL, clickedItem.getImageUrl());
        searchdetailIntent.putExtra(EXTRA_CREATOR, clickedItem.getCreator());
        searchdetailIntent.putExtra(EXTRA_LIKES, clickedItem.getNameCount());
        searchdetailIntent.putExtra(EXTRA_GUID, clickedItem.getGuid());


        startActivity(searchdetailIntent);

    }
}


