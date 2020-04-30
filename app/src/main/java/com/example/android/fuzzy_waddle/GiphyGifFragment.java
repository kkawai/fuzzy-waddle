package com.example.android.fuzzy_waddle;

/*
This module borrowed from open source Signal android app https://github.com/signalapp/Signal-Android
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.fuzzy_waddle.model.GiphyImage;
import com.example.android.fuzzy_waddle.net.GiphyGifLoader;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.loader.content.Loader;

public class GiphyGifFragment extends GiphyFragment {

  @Override
  public @NonNull
  Loader<List<GiphyImage>> onCreateLoader(int id, Bundle args) {
    return new GiphyGifLoader(getActivity(), searchString);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
    View view = super.onCreateView(inflater, viewGroup, bundle);
    ((SearchView)view.findViewById(R.id.gifSearchBar))
            .setOnQueryTextListener(new SearchView.OnQueryTextListener() {
              @Override
              public boolean onQueryTextSubmit(String query) {
                if (query.length() < 2) {
                  return false;
                }
                setSearchString(query);
                return false;
              }

              @Override
              public boolean onQueryTextChange(String newText) {
                return false;
              }
            });
    return view;
  }
}
