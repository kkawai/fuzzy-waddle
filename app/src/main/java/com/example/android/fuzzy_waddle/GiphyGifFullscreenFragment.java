package com.example.android.fuzzy_waddle;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.android.fuzzy_waddle.ui.AspectRatioImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GiphyGifFullscreenFragment extends Fragment {

  public static GiphyGifFullscreenFragment newInstance(String imageUrl, float aspectRatio) {
    Bundle args = new Bundle();
    args.putString("imageUrl", imageUrl);
    args.putFloat("aspectRatio", aspectRatio);
    GiphyGifFullscreenFragment fragment = new GiphyGifFullscreenFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.giphy_fullscreen, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    AspectRatioImageView aspectRatioImageView = (AspectRatioImageView)getView().findViewById(R.id.thumbnail);
    aspectRatioImageView.setAspectRatio(getArguments().getFloat("aspectRatio"));
    Glide.with(this).load(getArguments().getString("imageUrl"))
            .into(aspectRatioImageView);
  }
}
