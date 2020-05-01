package com.example.android.fuzzy_waddle;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.android.fuzzy_waddle.ui.AspectRatioImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GiphyGifFullscreenFragment extends Fragment {

  private RequestManager requestManager;
  private AspectRatioImageView aspectRatioImageView;

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
    requestManager = Glide.with(this);
    RequestBuilder<Drawable> thumbnailRequest = requestManager
            .load(getArguments().getString("stillUrl")).diskCacheStrategy(DiskCacheStrategy.ALL);
    aspectRatioImageView = view.findViewById(R.id.thumbnail);
    aspectRatioImageView.setAspectRatio(getArguments().getFloat("aspectRatio"));
    requestManager
            .load(getArguments().getString("gifUrl"))
            .listener(new RequestListener<Drawable>() {
              @Override
              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                //todo
                return false;
              }

              @Override
              public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                  if (getActivity() != null && getView() != null)
                    getView().findViewById(R.id.progress_horizontal).setVisibility(View.GONE);
                  return false;
              }
            })
            .thumbnail(thumbnailRequest)
            .into(aspectRatioImageView);
    return view;
  }

  @Override
  public void onDestroy() {
    if (requestManager != null && aspectRatioImageView != null) {
      requestManager.clear(aspectRatioImageView);
    }
    super.onDestroy();
  }
}
