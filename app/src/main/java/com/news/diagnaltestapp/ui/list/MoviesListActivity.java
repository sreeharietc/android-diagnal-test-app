package com.news.diagnaltestapp.ui.list;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.news.diagnaltestapp.R;
import com.news.diagnaltestapp.data.model.Content;
import com.news.diagnaltestapp.data.model.PageContent;
import com.news.diagnaltestapp.utilities.Constants;
import com.news.diagnaltestapp.utilities.InjectorUtil;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

public class MoviesListActivity extends AppCompatActivity {
    private static final int TOTAL_PAGE = 3;
    private MoviesListViewModel moviesListViewModel;
    private RecyclerView moviesRecyclerView;
    private MoviesRecyclerViewAdapter moviesRecyclerViewAdapter;
    private RecyclerView.OnScrollListener onScrollListener;
    private boolean isLoading;
    private int currentPage = 1;
    private boolean isLastPage;
    private int pageSize;

    private ImageView searchIcon;
    private SearchView searchView;
    private ConstraintLayout toolbarContainer;
    private ImageView closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchIcon = findViewById(R.id.searchIcon);
        searchView =  findViewById(R.id.searchView);
        toolbarContainer = findViewById(R.id.toolbarContainer);
        closeButton = searchView.findViewById(R.id.search_close_btn);

        setUpToolbar();
        setUpSearchView();
        setMoviesRecyclerView();
        setUpViewModel();
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar.setTitleTextColor(Color.WHITE);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(getString(R.string.romcom));
        }
    }

    private void setUpSearchView() {
        EditText searchEditText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.colorAccent));

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbarContainer.setVisibility(View.INVISIBLE);
                searchView.setVisibility(View.VISIBLE);
                searchView.onActionViewExpanded();
                searchView.requestFocus();
                closeButton.setVisibility(View.VISIBLE);
            }
        });
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);

        closeButton.setImageResource(R.drawable.close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery(Constants.EMPTY_STRING, false);
                searchView.onActionViewCollapsed();
                searchView.setVisibility(View.INVISIBLE);
                toolbarContainer.setVisibility(View.VISIBLE);
            }
        });
        setSearchViewTextChangeListener();
    }

    private void setSearchViewTextChangeListener() {
        SearchView.OnQueryTextListener searchListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.length() >=3 ) {
                    moviesListViewModel.searchMoviesList(s);
                } else {
                    moviesRecyclerViewAdapter.updateSearchResult(moviesListViewModel.getTotalMoviesList());
                }
                return true;
            }
        };
        searchView.setOnQueryTextListener(searchListener);
    }

    private void setUpViewModel() {
        final MoviesListViewModelFactory moviesListViewModelFactory = InjectorUtil.
                provideMoviesListViewModelFactory(this.getApplicationContext());
        moviesListViewModel = ViewModelProviders.
                of(this, moviesListViewModelFactory).get(MoviesListViewModel.class);
        moviesListViewModel.getMoviesData().observe(this, new Observer<PageContent>() {
            @Override
            public void onChanged(@Nullable PageContent pageContent) {
                updateListView(pageContent);
            }
        });
        moviesListViewModel.getSearchedMoviesData().observe(this, new Observer<List<Content>>() {
            @Override
            public void onChanged(@Nullable List<Content> contents) {
                moviesRecyclerViewAdapter.updateSearchResult(contents);
            }
        });
    }

    private void updateListView(PageContent pageContent) {
        pageSize += Integer.parseInt(Objects.requireNonNull(pageContent).getPage().getPageSize());
        List<Content> moviesItems = Objects.requireNonNull(pageContent).getPage().getContentItems().getContent();
        moviesRecyclerViewAdapter.updateMovies(moviesItems);
        moviesListViewModel.updateTotalMoviesList(moviesItems);
        if (currentPage >= TOTAL_PAGE) {
            isLastPage = true;
        }
        isLoading = false;
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
}
