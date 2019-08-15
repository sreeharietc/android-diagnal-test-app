package com.news.diagnaltestapp.ui.list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.news.diagnaltestapp.R;
import com.news.diagnaltestapp.data.model.PageContent;
import com.news.diagnaltestapp.utilities.InjectorUtil;

public class MoviesListActivity extends AppCompatActivity {
    private MoviesListViewModel moviesListViewModel;
    private RecyclerView moviesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        moviesRecyclerView = findViewById(R.id.recyclerView);
        setSupportActionBar(toolbar);
        setMoviesRecyclerView();

        MoviesListViewModelFactory moviesListViewModelFactory = InjectorUtil.
                provideMoviesListViewModelFactory(this.getApplicationContext());
        moviesListViewModel = ViewModelProviders.
                of(this, moviesListViewModelFactory).get(MoviesListViewModel.class);
        moviesListViewModel.getMoviesData().observe(this, new Observer<PageContent>() {
            @Override
            public void onChanged(@Nullable PageContent pageContent) {

            }
        });

    }

    private void setMoviesRecyclerView() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
