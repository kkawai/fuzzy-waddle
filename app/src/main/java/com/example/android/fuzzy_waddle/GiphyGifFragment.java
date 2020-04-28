package com.example.android.fuzzy_waddle;


import android.os.Bundle;

import com.example.android.fuzzy_waddle.model.GiphyImage;
import com.example.android.fuzzy_waddle.net.GiphyGifLoader;
import com.example.android.fuzzy_waddle.util.ViewUtil;

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
  public void onActivityCreated(Bundle bundle) {
    super.onActivityCreated(bundle);

    SearchView searchView = ViewUtil.findById(getView(), R.id.gifSearchBar);
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
  }
}
