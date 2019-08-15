package com.news.diagnaltestapp.ui.list;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.news.diagnaltestapp.R;
import com.news.diagnaltestapp.data.model.Content;
import com.news.diagnaltestapp.data.model.PageContent;


import java.io.IOException;
import java.io.InputStream;
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
        ImageView imageView = view.findViewById(R.id.moviePoster);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) imageView.getLayoutParams();
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, context.getResources().getDisplayMetrics());
        layoutParams.width = (viewGroup.getWidth()-(margin*6))/3;
        layoutParams.height = (int) (layoutParams.width*1.5);
        layoutParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics());
        layoutParams.setMargins(margin, 0, margin, 0);
        imageView.setLayoutParams(layoutParams);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder moviesViewHolder, int i) {
        InputStream is = null;
        try {
            is = context.getAssets().open(movieList.get(i).getPosterImage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        Drawable d = Drawable.createFromStream(is, null);
        String imageFilePath = "file:///android_asset/images/"+movieList.get(i).getName();
//        Picasso.get().load(imageFilePath).into(moviesViewHolder.moviePoster);
        moviesViewHolder.moviePoster.setImageBitmap(bitmap);
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
