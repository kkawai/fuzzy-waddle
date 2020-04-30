package com.example.android.fuzzy_waddle;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android.fuzzy_waddle.ui.AspectRatioImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GiphyGifFullscreenFragment extends Fragment {

  private GiphyGifFullscreenFragment(){}

  public static GiphyGifFullscreenFragment newInstance(String gifUrl, String stillUrl, float aspectRatio) {
    Bundle args = new Bundle();
    args.putString("gifUrl", gifUrl);
    args.putString("stillUrl", stillUrl);
    args.putFloat("aspectRatio", aspectRatio);
    GiphyGifFullscreenFragment fragment = new GiphyGifFullscreenFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.giphy_fullscreen, container, false);
    RequestBuilder<Drawable> thumbnailRequest = Glide.with(this)
            .load(getArguments().getString("stillUrl")).diskCacheStrategy(DiskCacheStrategy.ALL);
    AspectRatioImageView aspectRatioImageView = (AspectRatioImageView)view.findViewById(R.id.thumbnail);
    aspectRatioImageView.setAspectRatio(getArguments().getFloat("aspectRatio"));
    Glide.with(this)
            .load(getArguments().getString("gifUrl"))
            .thumbnail(thumbnailRequest)
            .into(aspectRatioImageView);
    return view;
  }

}
