package com.veronica.myjournal.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.veronica.myjournal.Constants;
import com.veronica.myjournal.R;
import com.veronica.myjournal.adapters.DrawerItemCustomAdapter;
import com.veronica.myjournal.app.MyJournalApplication;
import com.veronica.myjournal.fragments.ContainerFragment;
import com.veronica.myjournal.kinvey.KinveyConnector;
import com.veronica.myjournal.models.User;
import com.veronica.myjournal.objects.ObjectDrawerItem;

import java.util.List;

public class JournalActivity extends AppCompatActivity{

    MyJournalApplication appJournal;

    private String[] mNavigationDrawerItemTitles;
    private ObjectDrawerItem[] mDrawerItems;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private Toolbar mToolbar;
    private User mCurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appJournal = (MyJournalApplication)this.getApplication();
        setContentView(R.layout.activity_journal);

        if(!appJournal.getAuthManager().isLoggedIn()){
            startActivity(new Intent(JournalActivity.this,LoginActivity.class));
        }

        String currentUserEmail = appJournal.getAuthManager().getUser();
        mCurrentUser =  appJournal.getUserDbManager().getByEmail(currentUserEmail);

        //drawer
        initializeDrawerItems();
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new DrawerItemCustomAdapter(this,R.layout.drawer_item_row,mDrawerItems));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        //toolbar
        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle(Constants.HOME);
        mToolbar.setNavigationIcon(R.drawable.icon_menu);

        //place fragments container
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, new ContainerFragment(),"container_fragment")
                .disallowAddToBackStack()
                .commit();


        // Set the list's click listener

//        String customFont = "IndieFlower.ttf";
//        Typeface typeface = Typeface.createFromAsset(getAssets(),customFont);
//        txtView.setTypeface(typeface);


    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        Fragment fragment;

        mDrawerList.setItemChecked(position, true);
        mToolbar.setTitle(mDrawerItems[position].name);
        mDrawerLayout.closeDrawer(mDrawerList);

        ObjectDrawerItem itemClicked = mDrawerItems[position];

        String itemName = itemClicked.name;
        if (itemName.equals(Constants.HOME)) {

        } else if (itemName.equals(Constants.PROFILE)) {

        } else if (itemName.equals(Constants.JOURNALS)) {

        }else if(itemName.equals(Constants.IMPORT_FROM_DB)){

        }else if(itemName.equals(Constants.CHANGE_PASS)){

        }
        else if (itemName.equals(Constants.EXIT)) {
            appJournal.getAuthManager().logoutUser();
            startActivity(new Intent(JournalActivity.this,LoginActivity.class));
            finish();
        }
    }

    private void initializeDrawerItems() {
        mDrawerItems = new ObjectDrawerItem[7];
        mDrawerItems[0] = new ObjectDrawerItem(R.drawable.icon_home, Constants.HOME);
        mDrawerItems[1] = new ObjectDrawerItem(R.drawable.icon_profile, Constants.PROFILE);
        mDrawerItems[2] = new ObjectDrawerItem(R.drawable.icon_change_pass, Constants.CHANGE_PASS);
        mDrawerItems[3] = new ObjectDrawerItem(R.drawable.icon_notes, Constants.JOURNALS);
        mDrawerItems[4] = new ObjectDrawerItem(R.drawable.icon_import_remote, Constants.IMPORT_FROM_DB);
        mDrawerItems[5] = new ObjectDrawerItem(R.drawable.icon_import_remote, Constants.EXPORT_TO_REMOTE_DB);
        mDrawerItems[6] = new ObjectDrawerItem(R.drawable.icon_exit, Constants.EXIT);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawers();
                }else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.ad_new_note_toolbar:
                Toast.makeText(getApplicationContext(), "Add new note", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.syncronize_user_data_to_remote_storage:
//                if(isNetworkAvailable()) {
//                    Toast.makeText(getApplicationContext(), "synchronizing..", Toast.LENGTH_SHORT).show();
//                    KinveyConnector.getInstance().createUser(mCurrentUser);
//                }else{
//                    Toast.makeText(getApplicationContext(), "No network connection. Please synchronize later", Toast.LENGTH_SHORT).show();
//                }
//                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = JournalActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                {
                    return fragment;
                }
            }
        }
        return null;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
