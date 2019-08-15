package com.news.diagnaltestapp.ui.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.news.diagnaltestapp.R;
import com.news.diagnaltestapp.data.model.Content;
import com.news.diagnaltestapp.data.model.PageContent;
import com.news.diagnaltestapp.utilities.ViewUtil;
import java.util.List;

/**
 * Created by sreehari
 * on 15/8/19.
 */
public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.MoviesViewHolder> {

    private Context context;
    private List<Content> movieList;

    public MoviesRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_movies_list_item, viewGroup, false);
        ViewUtil.setMovieListItemLayoutParam(view, viewGroup, context.getResources().getDisplayMetrics());
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder moviesViewHolder, int i) {
        ViewUtil.setPosterImage(context.getAssets(), movieList.get(i).getPosterImage(), moviesViewHolder.moviePoster);
        moviesViewHolder.movieTitle.setText(movieList.get(i).getName());
    }

    void updateMovies(PageContent movies) {
        this.movieList = movies.getPage().getContentItems().getContent();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(movieList != null) {
            return movieList.size();
        } else {
            return 0;
        }
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {
        ImageView moviePoster;
        TextView movieTitle;
        MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.moviePoster);
            movieTitle = itemView.findViewById(R.id.movieTitle);
        }
    }
}
