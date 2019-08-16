package com.news.diagnaltestapp.ui.list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.news.diagnaltestapp.R;
import com.news.diagnaltestapp.data.model.PageContent;
import com.news.diagnaltestapp.utilities.Constants;
import com.news.diagnaltestapp.utilities.InjectorUtil;

public class MoviesListActivity extends AppCompatActivity {
    private static final int TOTAL_PAGE = 3;
    private static final int PAGE_START = 1;
    private MoviesListViewModel moviesListViewModel;
    private RecyclerView moviesRecyclerView;
    private MoviesRecyclerViewAdapter moviesRecyclerViewAdapter;
    private RecyclerView.OnScrollListener onScrollListener;
    private boolean isLoading;
    private int currentPage = 1;
    private boolean isLastPage;
    private int pageSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setMoviesRecyclerView();

        MoviesListViewModelFactory moviesListViewModelFactory = InjectorUtil.
                provideMoviesListViewModelFactory(this.getApplicationContext());
        moviesListViewModel = ViewModelProviders.
                of(this, moviesListViewModelFactory).get(MoviesListViewModel.class);
        moviesListViewModel.getMoviesData().observe(this, new Observer<PageContent>() {
            @Override
            public void onChanged(@Nullable PageContent pageContent) {
                pageSize += Integer.parseInt(pageContent.getPage().getPageSize());
                moviesRecyclerViewAdapter.updateMovies(pageContent);
                if (currentPage >= TOTAL_PAGE) {
                    isLastPage = true;
                }
                isLoading = false;
            }
        });

    }

    private void setMoviesRecyclerView() {
        moviesRecyclerView = findViewById(R.id.recyclerView);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, Constants.NUMBER_THREE);
        moviesRecyclerView.setLayoutManager(layoutManager);
        moviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter(this);
        moviesRecyclerView.setAdapter(moviesRecyclerViewAdapter);

        onScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= pageSize) {
                        loadMoreItems();
                    }
                }
            }
        };

        moviesRecyclerView.addOnScrollListener(onScrollListener);
    }

    private void loadMoreItems() {
        isLoading = true;
        currentPage++;
        moviesListViewModel.fetchMoviesFromRepositoryNextPage(currentPage);
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
