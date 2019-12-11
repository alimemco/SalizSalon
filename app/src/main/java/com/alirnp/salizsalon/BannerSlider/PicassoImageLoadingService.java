package com.alirnp.salizsalon.BannerSlider;

import android.content.Context;
import android.widget.ImageView;

import com.alirnp.salizsalon.R;
import com.squareup.picasso.Picasso;

import ss.com.bannerslider.ImageLoadingService;

public class PicassoImageLoadingService implements ImageLoadingService {
    public Context context;

    public PicassoImageLoadingService(Context context) {
        this.context = context;
    }

    @Override
    public void loadImage(String url, ImageView imageView) {
        if (!url.equals(""))
            Picasso.get().load(url).into(imageView);
    }

    @Override
    public void loadImage(int resource, ImageView imageView) {
        Picasso.get().load(resource).into(imageView);
    }

    @Override
    public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
        if (!url.equals(""))
            Picasso.get().load(url).placeholder(R.color.gray_100).error(R.color.gray_blue_200).into(imageView);
    }
}