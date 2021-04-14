package net.thesis.vglibvol;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    private BottomNavigationView mMainNav;

    private NoteViewModel sgameViewModel;
    private WishlistViewModel wgameViewModel;

    private List<Note> collection_count;
    private List<Wishlist_Item> wishlist_count;

    private List<String> col_genres_count;




    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(StatisticsActivity.this, MainActivity.class));
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
        setContentView(R.layout.statistics_tab);



        //check and change theme
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        }
        else setTheme(R.style.AppTheme);
        //check and change theme

        sgameViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        wgameViewModel = ViewModelProviders.of(this).get(WishlistViewModel.class);

        //get collection count
        collection_count=sgameViewModel.getAllNotes();
        int collection_counter = collection_count.size();
        String test1 = String.valueOf(collection_counter);

        wishlist_count=wgameViewModel.getAllWNotes();
        int wishlist_counter = wishlist_count.size();
        String test2 = String.valueOf(wishlist_counter);

        TextView total_games = findViewById(R.id.collection_number);
        TextView total_wishlist = findViewById(R.id.wishlist_number);
        TextView genres_count = findViewById(R.id.col_genres);


        total_games.setText(String.valueOf(collection_counter));
        total_wishlist.setText(String.valueOf(wishlist_counter));


        Log.d("TAG", "Games saved to collection:\n" +collection_counter + "\n" +wishlist_counter);


        //get genre list
        col_genres_count=sgameViewModel.getGenres();


        //define counters

        int col_action_counter=0;
        int col_fighting_counter=0;
        int col_shooter_counter=0;
        int col_rpg_counter=0;
        int col_adventure_counter=0;
        int col_racing_counter=0;
        int col_sports_counter=0;
        int col_simulation_counter=0;
        int col_platformer_counter=0;
        int col_strategy_counter=0;
        int col_party_counter=0;
        int col_music_counter=0;

        for (int i = 0; i < col_genres_count.size(); i++) {

            //get shooter count
                if (col_genres_count.get(i).contains("First-Person Shooter") || col_genres_count.get(i).contains("Shooter")) {
                    col_shooter_counter ++;
                }

            //get action count
            if (col_genres_count.get(i).contains("Action-Adventure") || col_genres_count.get(i).contains("Action")) {
                col_action_counter ++;
            }

            //get action-adventure count
            if (col_genres_count.get(i).contains("Action-Adventure") || col_genres_count.get(i).contains("Adventure")) {
                col_adventure_counter ++;
            }


            //get rpg count
            if (col_genres_count.get(i).contains("Role-Playing") || col_genres_count.get(i).contains("MMORPG")) {
                col_rpg_counter ++;
            }


            //get fighting count
            if (col_genres_count.get(i).contains("Fighting")) {
                col_fighting_counter ++;
            }


            //get racing count
            if (col_genres_count.get(i).contains("Racing") || col_genres_count.get(i).contains("Driving")) {
                col_racing_counter ++;
            }


            //get sports count
            if (col_genres_count.get(i).contains("Sports")) {
                col_sports_counter ++;
            }

            //get simulation count
            if (col_genres_count.get(i).contains("Simulation")) {
                col_simulation_counter ++;
            }

            //get platformer count
            if (col_genres_count.get(i).contains("Platformer")) {
                col_platformer_counter ++;
            }

            //get Strategy count
            if (col_genres_count.get(i).contains("Strategy")) {
                col_strategy_counter ++;
            }

            //get Party game count
            if (col_genres_count.get(i).contains("Trivia") || col_genres_count.get(i).contains("Board Game") || col_genres_count.get(i).contains("Minigame Collection")) {
                col_party_counter ++;
            }

            //get Music/rhythm count
            if (col_genres_count.get(i).contains("Rhythm") || col_genres_count.get(i).contains("Music")) {
                col_music_counter ++;
            }

        }


        genres_count.setText("Action: "+col_action_counter+"\nShooter: "+col_shooter_counter+"\nRole-Playing: "+col_rpg_counter+"\nFighting: "+col_fighting_counter+"\nRacing: "+col_racing_counter+"\nAdventure: "
                +col_adventure_counter+"\nSports: "+col_sports_counter+"\nPlatformer: "+col_platformer_counter+"\nSimulation: "+col_simulation_counter+"\nStrategy: "+col_strategy_counter+"\nMusic: "+col_music_counter+"\nParty/Board: "+col_party_counter);

        Log.d("TAG", "Games saved to collection:\n" +col_shooter_counter + "\n" + col_adventure_counter+ "\n" + col_action_counter+ "\n" + col_rpg_counter + "\n" +col_fighting_counter + "\n" +col_racing_counter + "\n" +col_sports_counter + "\n" +col_simulation_counter + "\n" + col_platformer_counter);





        //layout(tab)
        //mMainFrame =(FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);


        Menu menu = mMainNav.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        //call event list
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                                                         @Override
                                                         public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                                                             switch (menuItem.getItemId()) {

                                                                 case R.id.nav_search:
                                                                     Intent intent0 = new Intent(StatisticsActivity.this, MainActivity.class);
                                                                     startActivity(intent0);
                                                                     break;

                                                                 case R.id.nav_collection:
                                                                     Intent intent1 = new Intent(StatisticsActivity.this, CollectionActivity.class);
                                                                     startActivity(intent1);
                                                                     break;

                                                                 case R.id.nav_wishlist:
                                                                     Intent intent2 = new Intent(StatisticsActivity.this, WishlistActivity.class);
                                                                     startActivity(intent2);
                                                                     break;

                                                                 case R.id.nav_statistics:
                                                                     //current activity
                                                                     break;

                                                                 case R.id.nav_settings:
                                                                     Intent intent4 = new Intent(StatisticsActivity.this, SettingsActivity.class);
                                                                     startActivity(intent4);
                                                                     break;

                                                             }
                                                             return false;
                                                         }


                                                     }
        );




    }
}
