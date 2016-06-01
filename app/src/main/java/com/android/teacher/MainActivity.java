//TODO: edit word, dict.activity sound don't play 
package com.android.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.android.teacher.fragments.AlfavitFragmentHome;
import com.android.teacher.fragments.FragmentHome;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean returned = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, AlfavitFragmentHome.newInstance()).commit();
            navigationView.getMenu().getItem(0).setChecked(true);
            setTitle(R.string.alphabet);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        String title = item.getTitle().toString();
        switch (id){
            case R.id.nav_alfavit:
                showFragment(AlfavitFragmentHome.newInstance());
                setTitle(title);
                break;
            case R.id.nav_tustor:
                showFragment(FragmentHome.newInstance(1));
                setTitle(title);
                break;
            case R.id.nav_janybarlar:
                showFragment(FragmentHome.newInstance(2));
                setTitle(title);
                break;
            case R.id.nav_buyumdar:
                showFragment(FragmentHome.newInstance(3));
                setTitle(title);
                break;
            case R.id.nav_adamdyn_mucholoru:
                showFragment(FragmentHome.newInstance(4));
                setTitle(title);
                break;
            case R.id.nav_kiyimder:
                showFragment(FragmentHome.newInstance(5));
                setTitle(title);
                break;
            case R.id.nav_jashylcha_jemishter:
                showFragment(FragmentHome.newInstance(6));
                setTitle(title);
                break;
            case R.id.nav_dictionary:
                startActivity(new Intent(this, DictionaryMain.class));
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onResume() {super.onResume();
        if(returned){
            finish();
            startActivity(getIntent());
        }
    }

    @Override
    public void onPause() {super.onPause();
        returned = true;
    }
}
