package net.thesis.vglibvol;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class WishlistActivity extends AppCompatActivity {

    private List<Wishlist_Item> searchList = new ArrayList<>();

    public static final String EXTRA_WTITLE = "wtitle";
    public static final String EXTRA_WGENRES = "wgenre";
    public static final String EXTRA_WGUID = "wguid";
    public static final String EXTRA_WDECK= "wdeck";
    public static final String EXTRA_WPLATFORM= "wplatform";
    public static final String EXTRA_WRELEASE_DATE= "wrelease_date";
    public static final String EXTRA_WDEVELOPERS= "wdevelopers";

    private WishlistViewModel wgameViewModel;
    private NoteViewModel sgameViewModel;

    private BottomNavigationView mMainNav;

    private Wishlist_Item w;





    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(WishlistActivity.this, MainActivity.class));
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
        setContentView(R.layout.wishlist_list);




        //layout(tab)
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
                                                                     Intent intent0 = new Intent(WishlistActivity.this, MainActivity.class);
                                                                     startActivity(intent0);
                                                                     break;

                                                                 case R.id.nav_collection :
                                                                     Intent intent1 = new Intent(WishlistActivity.this, CollectionActivity.class);
                                                                     startActivity(intent1);
                                                                     break;

                                                                 case R.id.nav_wishlist:
                                                                     //current activity
                                                                     break;

                                                                 case R.id.nav_statistics :
                                                                     Intent intent3 = new Intent(WishlistActivity.this, StatisticsActivity.class);
                                                                     startActivity(intent3);
                                                                     break;

                                                                 case R.id.nav_settings :
                                                                     Intent intent4 = new Intent(WishlistActivity.this, SettingsActivity.class);
                                                                     startActivity(intent4);
                                                                     break;

                                                             }
                                                             return false;
                                                         }


                                                     });




        RecyclerView recyclerview_wishlist = findViewById(R.id.wishlist_recycler_view);
        recyclerview_wishlist.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        recyclerview_wishlist.setHasFixedSize(true);

        final WishlistAdapter wgameAdapter = new WishlistAdapter();
        recyclerview_wishlist.setAdapter(wgameAdapter);




        wgameViewModel = ViewModelProviders.of(this).get(WishlistViewModel.class);

        sgameViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);


        wgameViewModel.getWLAllNotes().observe(this, new Observer<List<Wishlist_Item>>(){
            @Override
            public void onChanged(@Nullable List<Wishlist_Item> wgameNotes) {

                //update RecyclerView
                wgameAdapter.setWishlist_items(wgameNotes);

                searchList = wgameNotes;


                //Toast.makeText(CollectionActivity.this, "onChanged", Toast.LENGTH_SHORT).show();

            }
        });




        final RecyclerView.ViewHolder viewHolder = null;



        wgameAdapter.setOnItemClickListener(new WishlistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Wishlist_Item wishlist_item) {


                Intent intent = new Intent(WishlistActivity.this, WishlistGameDetails.class);

                intent.putExtra(EXTRA_WTITLE, wishlist_item.getTitle());
                intent.putExtra(EXTRA_WGENRES, wishlist_item.getGenres());
                intent.putExtra(EXTRA_WGUID, wishlist_item.getGuid());
                intent.putExtra(EXTRA_WDECK, wishlist_item.getDescription());
                intent.putExtra(EXTRA_WPLATFORM, wishlist_item.getPlatform());
                intent.putExtra(EXTRA_WRELEASE_DATE, wishlist_item.getRelease_date());
                intent.putExtra(EXTRA_WDEVELOPERS, wishlist_item.getDevelopers());


                //transfer selected game to collectiondetailsactivity
                intent.putExtra("wobject", wishlist_item);

                startActivity(intent);

               // Log.d("TAG", String.valueOf(names));
            }
        });

        EditText editText = findViewById(R.id.search_wishlist); //declare searchbar

        editText.setFocusable(true);
        editText.setClickable(true);

        //searchbar



        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                List<Wishlist_Item> filteredList = new ArrayList<>();
                for (Wishlist_Item item : searchList) {

                    if (item.getTitle().toLowerCase().contains(s.toString().toLowerCase())) {
                        filteredList.add(item);
                    }
                }


                wgameAdapter.filterList(filteredList);

            }
        });


        //searchbar

        //remove by swipe
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                wgameViewModel.delete(wgameAdapter.getwishlistAt(viewHolder.getAdapterPosition()));

                Toast toast = Toast.makeText(WishlistActivity.this,"Game removed from WishList", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }



        }).attachToRecyclerView(recyclerview_wishlist);



        //remove by swipe
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {


                w=wgameAdapter.getwishlistAt(viewHolder.getAdapterPosition());

                String a = w.getTitle();
                String b = w.getDescription();
                String c = w.getPlatform();
                String d = w.getGuid();
                String e = w.getGenres();
                String f = w.getRelease_date();
                String g = w.getDevelopers();

                Note note = new Note(a,b,c,d,e,f,g);

                sgameViewModel.insert(note);

                wgameViewModel.delete(wgameAdapter.getwishlistAt(viewHolder.getAdapterPosition()));

                Toast toast = Toast.makeText(WishlistActivity.this,"Game moved to Collection", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }



        }).attachToRecyclerView(recyclerview_wishlist);



    }


}
