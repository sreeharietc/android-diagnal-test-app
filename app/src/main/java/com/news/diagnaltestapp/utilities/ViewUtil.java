package com.news.diagnaltestapp.utilities;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.news.diagnaltestapp.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by sreehari
 * on 15/8/19.
 */
public class ViewUtil {
    private static final int MARGIN = 5;
    private static final double ASPECT_RATIO = 1.5;
    private static final int MARGIN_TOP = 22;

    public static void setMovieListItemLayoutParam(View view, ViewGroup viewGroup, DisplayMetrics displayMetrics) {
        ImageView imageView = view.findViewById(R.id.moviePoster);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) imageView.getLayoutParams();
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGIN, displayMetrics);
        int marginTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGIN_TOP, displayMetrics);
        layoutParams.width = (viewGroup.getWidth()-(margin*Constants.NUMBER_SIX))/Constants.NUMBER_THREE;
        layoutParams.height = (int) (layoutParams.width*ASPECT_RATIO);
        layoutParams.setMargins(margin, marginTop, margin, Constants.NUMBER_ZERO);
        imageView.setLayoutParams(layoutParams);
    }

    public static void setPosterImage(AssetManager assetManager, String posterImageName, ImageView moviePosterImageView) {
        InputStream is = null;
        try {
            is = assetManager.open(posterImageName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        moviePosterImageView.setImageBitmap(bitmap);
    }
}
