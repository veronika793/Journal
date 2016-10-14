package com.veronica.medaily.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.veronica.medaily.fragments.BaseFragment;
import com.veronica.medaily.fragments.CategoriesFragment;
import com.veronica.medaily.fragments.HomeFragment;
import com.veronica.medaily.fragments.NotesByCategory;
import com.veronica.medaily.fragments.NotesFragment;
import com.veronica.medaily.fragments.ProfileFragment;
import com.veronica.medaily.fragments.SynchronizeFragment;
import com.veronica.medaily.helpers.NotificationHandler;
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
    private BaseFragment nextFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainApp = (MainApplication)this.getApplication();
        setContentView(R.layout.activity_home);

        if(!mainApp.getAuthManager().isLoggedIn()){
            goToLoginActivity();
        }
        validatesCurrentUser();

        //drawer settings
        initializeDrawerItems();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // catches the state of drawer - in case it's closed place next fragment
        mDrawerLayout.addDrawerListener(new DrawerListener());
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        //sets the next fragment to be placed
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerList.setAdapter(new DrawerItemCustomAdapter(this,R.layout.drawer_item_row,mDrawerItems));

        //updates drawer selected item on back press
        getSupportFragmentManager().addOnBackStackChangedListener(new BackStackListener());

        //toolbar settings
        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle(Constants.HOME);
        mToolbar.setNavigationIcon(R.drawable.icon_menu);

        HomeFragment homeFragment = new HomeFragment();
        placeFragment(homeFragment);
    }

    private void goToLoginActivity() {
        startActivity(new Intent(HomeActivity.this,LoginActivity.class));
    }

    //listens for clicked item in drawer
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            setNextFragment(position);
        }
    }

    //sets drawer selected item on back press
    private class BackStackListener implements FragmentManager.OnBackStackChangedListener{

        @Override
        public void onBackStackChanged() {
            FragmentManager fm = getSupportFragmentManager();
            int stackCount = fm.getBackStackEntryCount();
            String currentStackName = fm.getBackStackEntryAt(stackCount-1).getName();

            if(currentStackName.equals(HomeFragment.class.getName())){
                mDrawerList.setItemChecked(0,true);
            }else if(currentStackName.equals(ProfileFragment.class.getName())){
                mDrawerList.setItemChecked(1,true);
            }else if(currentStackName.equals(NotesFragment.class.getName()) || currentStackName.equals(NotesByCategory.class.getName()) ){
                mDrawerList.setItemChecked(2,true);
            }else if(currentStackName.equals(CategoriesFragment.class.getName())){
                mDrawerList.setItemChecked(3,true);
            }else if(currentStackName.equals(SynchronizeFragment.class.getName())){
                mDrawerList.setItemChecked(4,true);
            }
        }
    }

    // listen for changes in drawer state - place next fragment after drawer is being closed in order to avoid any lag
    private class DrawerListener implements android.support.v4.widget.DrawerLayout.DrawerListener {
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {}
        @Override
        public void onDrawerOpened(View drawerView) {}
        @Override
        public void onDrawerStateChanged(int newState) {}
        @Override
        public void onDrawerClosed(View view) {
            if (nextFragment!=null) {
                placeFragment(nextFragment);
            }
            nextFragment = null;
        }
    }

    // sets the next fragment to be open by clicked position in drawer
    private void setNextFragment(int position) {

        mDrawerList.setItemChecked(position, true);

        switch (position){
            case 0:nextFragment = new HomeFragment();
                break;
            case 1:nextFragment = new ProfileFragment();
                break;
            case 2:nextFragment = new NotesFragment();
                break;
            case 3:nextFragment = new CategoriesFragment();
                break;
            case 4:nextFragment = new SynchronizeFragment();
                break;
            case 5: mainApp.getAuthManager().logoutUser();
                goToLoginActivity();
                finish();
                break;
        }
        mDrawerLayout.closeDrawer(mDrawerList);

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

    // main menu buttons action on click
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
                placeFragment(new AddNoteFragment());
                break;
            case R.id.ad_new_category_toolbar:
                if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawers();
                }
                placeFragment(new AddCategoryFragment());
        }
        return super.onOptionsItemSelected(item);
    }

    protected void placeFragment(@NonNull Fragment fragment) {
        String backStateName =  fragment.getClass().getName();
        String fragmentTag = backStateName;

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content_frame, fragment, fragmentTag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    // returns the previous fragments from stack if none close the activity
    //@Override
    public void onBackPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount == 1) {
            moveTaskToBack(true);
        } else {
            super.onBackPressed();
        }
    }

    private void validatesCurrentUser() {
        String userIdAsStr = mainApp.getAuthManager().getUser();
        if(userIdAsStr==null){
            this.mainApp.getAuthManager().logoutUser();
            this.goToLoginActivity();
        }else{

            List<User> users = User.find(User.class," id =? ",String.valueOf(userIdAsStr));
            if(users.isEmpty()){
                this.mainApp.getAuthManager().logoutUser();
                this.goToLoginActivity();
            }
        }
    }

}