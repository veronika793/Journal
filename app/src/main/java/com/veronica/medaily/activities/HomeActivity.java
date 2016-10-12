package com.veronica.medaily.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.orm.SugarRecord;
import com.veronica.medaily.Constants;
import com.veronica.medaily.MainApplication;
import com.veronica.medaily.dbmodels.User;
import com.veronica.medaily.fragments.AddCategoryFragment;
import com.veronica.medaily.fragments.AddNoteFragment;
import com.veronica.medaily.fragments.CategoriesFragment;
import com.veronica.medaily.fragments.HomeFragment;
import com.veronica.medaily.fragments.NotesFragment;
import com.veronica.medaily.fragments.ProfileFragment;
import com.veronica.medaily.fragments.SynchronizeFragment;
import com.veronica.medaily.models.ObjectDrawerItem;
import com.veronica.medaily.R;
import com.veronica.medaily.adapters.DrawerItemCustomAdapter;

import java.util.List;

public class HomeActivity extends AppCompatActivity{

    MainApplication mainApp;

    private ObjectDrawerItem[] mDrawerItems;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private Toolbar mToolbar;
    private User mCurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainApp = (MainApplication)this.getApplication();
        setContentView(R.layout.activity_home);

        if(!mainApp.getAuthManager().isLoggedIn()){
            goToLoginActivity();
        }

        setCurrentUser();

        //drawer
        initializeDrawerItems();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
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


        HomeFragment homeFragment = new HomeFragment();
        Bundle data = new Bundle();//create bundle instance
        data.putParcelable("current_user", mCurrentUser);
        homeFragment.setArguments(data);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, homeFragment,"container_fragment")
                .disallowAddToBackStack()
                .commit();
    }

    private void goToLoginActivity() {
        startActivity(new Intent(HomeActivity.this,LoginActivity.class));
    }

    private void setCurrentUser() {
        String userIdAsStr = mainApp.getAuthManager().getUser();
        if(userIdAsStr==null){
            this.mainApp.getAuthManager().logoutUser();
            this.goToLoginActivity();
        }else{

            List<User> users = User.find(User.class," id =? ",String.valueOf(userIdAsStr));
            if(users.isEmpty()){
                this.mainApp.getAuthManager().logoutUser();
                this.goToLoginActivity();
            }else{
                this.mCurrentUser = users.get(0);
            }
            Log.d("DEBUG", "test");



        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {


        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);

        ObjectDrawerItem itemClicked = mDrawerItems[position];

        String itemName = itemClicked.name;

        if (itemName.equals(Constants.EXIT)) {
            mainApp.getAuthManager().logoutUser();
            goToLoginActivity();
            finish();
        }
        Fragment fragmentToLoad = null;
        if(itemName.equals(Constants.HOME)) {
            fragmentToLoad = new HomeFragment();
        }

        else if (itemName.equals(Constants.NOTES)) {
            fragmentToLoad = new NotesFragment();
        }
        else if(itemName.equals(Constants.SYNCHRONIZATION)){
            fragmentToLoad = new SynchronizeFragment();
        }
        else if(itemName.equals(Constants.CATEGORIES)){
            fragmentToLoad = new CategoriesFragment();
        }
        else if(itemName.equals(Constants.PROFILE)){
            fragmentToLoad = new ProfileFragment();
        }
        if(fragmentToLoad!=null) {
            placeFragment(R.id.content_frame, fragmentToLoad, fragmentToLoad.getClass().getSimpleName());
        }
    }

    private void initializeDrawerItems() {
        mDrawerItems = new ObjectDrawerItem[6];
        mDrawerItems[0] = new ObjectDrawerItem(R.drawable.icon_home, Constants.HOME);
        mDrawerItems[1] = new ObjectDrawerItem(R.drawable.icon_profile, Constants.PROFILE);
        mDrawerItems[2] = new ObjectDrawerItem(R.drawable.icon_notes, Constants.NOTES);
        mDrawerItems[3] = new ObjectDrawerItem(R.drawable.icon_categories, Constants.CATEGORIES);
        mDrawerItems[4] = new ObjectDrawerItem(R.drawable.icon_synchronization, Constants.SYNCHRONIZATION);
        mDrawerItems[5] = new ObjectDrawerItem(R.drawable.icon_exit, Constants.EXIT);
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
                if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawers();
                }
                placeFragment(R.id.content_frame,new AddNoteFragment(),"add_note_fragment");
                break;
            case R.id.ad_new_category_toolbar:
                if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawers();
                }
                placeFragment(R.id.content_frame,new AddCategoryFragment(),"add_category_fragment");
        }
        return super.onOptionsItemSelected(item);
    }

    private void placeFragment( @IdRes int containerViewId,
                                @NonNull Fragment fragment,
                                @NonNull String fragmentTag) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(null)
                .commit();
    }
    //@Override
    public void onBackPressed() {

        // returns the previous fragments if any
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount == 0) {
            moveTaskToBack(true);
        } else {
            super.onBackPressed();
        }
    }

}