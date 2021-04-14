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


public class CollectionActivity extends AppCompatActivity {


    private List<Note> searchList = new ArrayList<>();
    private NoteAdapter searchAdapter;


    public static final String EXTRA_STITLE = "stitle";
    public static final String EXTRA_SGENRES = "sgenre";
    public static final String EXTRA_SGUID = "sguid";
    public static final String EXTRA_SDECK = "sdeck";
    public static final String EXTRA_SPLATFORM = "splatform";
    public static final String EXTRA_SRELEASE_DATE = "srelease_date";
    public static final String EXTRA_SDEVELOPERS = "sdevelopers";
    // public static final String EXTRA_SOBJECT= "sobject";


    private NoteViewModel sgameViewModel;
    private BottomNavigationView mMainNav;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CollectionActivity.this, MainActivity.class));
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
        setContentView(R.layout.collection_list);


        //layout(tab)
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
                                                                     Intent intent0 = new Intent(CollectionActivity.this, MainActivity.class);
                                                                     startActivity(intent0);

                                                                     break;

                                                                 case R.id.nav_collection:
                                                                     //current activity
                                                                     break;

                                                                 case R.id.nav_wishlist:
                                                                     Intent intent2 = new Intent(CollectionActivity.this, WishlistActivity.class);
                                                                     startActivity(intent2);
                                                                     break;

                                                                 case R.id.nav_statistics:
                                                                     Intent intent3 = new Intent(CollectionActivity.this, StatisticsActivity.class);
                                                                     startActivity(intent3);
                                                                     break;

                                                                 case R.id.nav_settings:
                                                                     Intent intent4 = new Intent(CollectionActivity.this, SettingsActivity.class);
                                                                     startActivity(intent4);
                                                                     break;

                                                             }
                                                             return false;
                                                         }


                                                     }
        );


        RecyclerView recyclerview_collection = findViewById(R.id.collection_recycler_view);
        recyclerview_collection.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerview_collection.setHasFixedSize(true);

        final NoteAdapter sgameAdapter = new NoteAdapter();
        recyclerview_collection.setAdapter(sgameAdapter);


        sgameViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        sgameViewModel.getLAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> sgameNotes) {

                //update RecyclerView

                sgameAdapter.setNotes(sgameNotes);

                searchList = sgameNotes;


                //Toast.makeText(CollectionActivity.this, "onChanged", Toast.LENGTH_SHORT).show();

            }
        });


        //class actions here

        //define collection recycler view


        sgameAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {


                Intent intent = new Intent(CollectionActivity.this, CollectionGameDetails.class);

                intent.putExtra(EXTRA_STITLE, note.getTitle());
                intent.putExtra(EXTRA_SGENRES, note.getGenres());
                intent.putExtra(EXTRA_SGUID, note.getGuid());
                intent.putExtra(EXTRA_SDECK, note.getDescription());
                intent.putExtra(EXTRA_SPLATFORM, note.getPlatform());
                intent.putExtra(EXTRA_SRELEASE_DATE, note.getRelease_date());
                intent.putExtra(EXTRA_SDEVELOPERS, note.getDevelopers());


                //transfer selected game to collectiondetailsactivity
                intent.putExtra("object", note);


                //transferobject = Note.getInstance();

                // intent.putExtra(EXTRA_SOBJECT, note);

                startActivity(intent);

                //Log.d("TAG", String.valueOf(names));
            }
        });




        EditText editText = findViewById(R.id.search_collection); //declare searchbar

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

                    List<Note> filteredList = new ArrayList<>();
                    for (Note item : searchList) {

                        if (item.getTitle().toLowerCase().contains(s.toString().toLowerCase())) {
                            filteredList.add(item);
                        }
                    }


                    sgameAdapter.filterList(filteredList);

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
                sgameViewModel.delete(sgameAdapter.getNoteAt(viewHolder.getAdapterPosition()));

                Toast toast = Toast.makeText(CollectionActivity.this,"Game removed from Collection", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }
        }).attachToRecyclerView(recyclerview_collection);


    }


}






/* older code backup



    public static final String EXTRA_STITLE = "stitle";
    public static final String EXTRA_SGENRES = "sgenre";
    public static final String EXTRA_SGUID = "sguid";
    public static final String EXTRA_SDECK = "sdeck";
    public static final String EXTRA_SPLATFORM = "splatform";
    public static final String EXTRA_SRELEASE_DATE = "srelease_date";
    public static final String EXTRA_SDEVELOPERS = "sdevelopers";
    // public static final String EXTRA_SOBJECT= "sobject";


    private NoteViewModel sgameViewModel;
    private BottomNavigationView mMainNav;



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CollectionActivity.this, MainActivity.class));
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_list);


        //layout(tab)
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
                                                                     Intent intent0 = new Intent(CollectionActivity.this, MainActivity.class);
                                                                     startActivity(intent0);

                                                                     break;

                                                                 case R.id.nav_collection:
                                                                     //current activity
                                                                     break;

                                                                 case R.id.nav_wishlist:
                                                                     Intent intent2 = new Intent(CollectionActivity.this, WishlistActivity.class);
                                                                     startActivity(intent2);
                                                                     break;

                                                                 case R.id.nav_statistics:
                                                                     Intent intent3 = new Intent(CollectionActivity.this, StatisticsActivity.class);
                                                                     startActivity(intent3);
                                                                     break;

                                                                 case R.id.nav_settings:
                                                                     Intent intent4 = new Intent(CollectionActivity.this, SettingsActivity.class);
                                                                     startActivity(intent4);
                                                                     break;

                                                             }
                                                             return false;
                                                         }


                                                     }
        );


        RecyclerView recyclerview_collection = findViewById(R.id.collection_recycler_view);
        recyclerview_collection.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_collection.setHasFixedSize(true);

        final NoteAdapter sgameAdapter = new NoteAdapter();
        recyclerview_collection.setAdapter(sgameAdapter);



        sgameViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        sgameViewModel.getLAllNotes().observe(this, new Observer<List<Note>>(){
            @Override
            public void onChanged(@Nullable List<Note> sgameNotes) {

                //update RecyclerView
                sgameAdapter.submitList(sgameNotes);



                //Toast.makeText(CollectionActivity.this, "onChanged", Toast.LENGTH_SHORT).show();

            }
        });

        //class actions here

        //define collection recycler view




        sgameAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {


                Intent intent = new Intent(CollectionActivity.this, CollectionGameDetails.class);

                intent.putExtra(EXTRA_STITLE, note.getTitle());
                intent.putExtra(EXTRA_SGENRES, note.getGenres());
                intent.putExtra(EXTRA_SGUID, note.getGuid());
                intent.putExtra(EXTRA_SDECK, note.getDescription());
                intent.putExtra(EXTRA_SPLATFORM, note.getPlatform());
                intent.putExtra(EXTRA_SRELEASE_DATE, note.getRelease_date());
                intent.putExtra(EXTRA_SDEVELOPERS, note.getDevelopers());


                //transfer selected game to collectiondetailsactivity
                intent.putExtra("object", note);


                //transferobject = Note.getInstance();

                // intent.putExtra(EXTRA_SOBJECT, note);

                startActivity(intent);

                //Log.d("TAG", String.valueOf(names));
            }
        });



        //remove by swipe
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                sgameViewModel.delete(sgameAdapter.getNoteAt(viewHolder.getAdapterPosition()));

                Toast.makeText(CollectionActivity.this, "Game removed from Collection", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerview_collection);




    }



 */

