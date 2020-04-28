package com.example.android.fuzzy_waddle;

/*
This module borrowed from open source Signal android app https://github.com/signalapp/Signal-Android
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.fuzzy_waddle.model.GiphyImage;
import com.example.android.fuzzy_waddle.net.GiphyLoader;
import com.example.android.fuzzy_waddle.util.InfiniteScrollListener;
import com.example.android.fuzzy_waddle.util.ViewUtil;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public abstract class GiphyFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<GiphyImage>>, GiphyAdapter.OnGiphyImageClickListener {

  private static final String TAG = GiphyFragment.class.getSimpleName();

  private GiphyAdapter                     giphyAdapter;
  private RecyclerView recyclerView;
  private ProgressBar loadingProgress;
  private TextView noResultsView;
  private GiphyAdapter.OnGiphyImageClickListener listener;

  protected String searchString;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
    ViewGroup container = ViewUtil.inflate(inflater, viewGroup, R.layout.giphy_fragment);
    this.recyclerView    = ViewUtil.findById(container, R.id.giphy_list);
    this.loadingProgress = ViewUtil.findById(container, R.id.loading_progress);
    this.noResultsView   = ViewUtil.findById(container, R.id.no_results);

    return container;
  }

  @Override
  public void onActivityCreated(Bundle bundle) {
    super.onActivityCreated(bundle);

    this.giphyAdapter = new GiphyAdapter(getActivity(), new LinkedList<>());
    this.giphyAdapter.setListener(this);

    setLayoutManager(true); //true -> use grid layout
    this.recyclerView.setItemAnimator(new DefaultItemAnimator());
    this.recyclerView.setAdapter(giphyAdapter);
    this.recyclerView.addOnScrollListener(new GiphyScrollListener());

    getLoaderManager().initLoader(0, null, this);
  }

  @Override
  public void onLoadFinished(@NonNull Loader<List<GiphyImage>> loader, @NonNull List<GiphyImage> data) {
    this.loadingProgress.setVisibility(View.GONE);

    if (data.isEmpty()) noResultsView.setVisibility(View.VISIBLE);
    else                noResultsView.setVisibility(View.GONE);

    this.giphyAdapter.setImages(data);
  }

  @Override
  public void onLoaderReset(@NonNull Loader<List<GiphyImage>> loader) {
    noResultsView.setVisibility(View.GONE);
    this.giphyAdapter.setImages(new LinkedList<GiphyImage>());
  }

  public void setLayoutManager(boolean gridLayout) {
    recyclerView.setLayoutManager(getLayoutManager(gridLayout));
  }

  private RecyclerView.LayoutManager getLayoutManager(boolean gridLayout) {
    return gridLayout ? new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                      : new LinearLayoutManager(getActivity());
  }

  public void setClickListener(GiphyAdapter.OnGiphyImageClickListener listener) {
    this.listener = listener;
  }

  public void setSearchString(@Nullable String searchString) {
    this.searchString = searchString;
    this.noResultsView.setVisibility(View.GONE);
    this.getLoaderManager().restartLoader(0, null, this);
  }

  @Override
  public void onGiphyImageClicked(GiphyImage giphyImage) {
    if (listener != null) {
      listener.onGiphyImageClicked(giphyImage);
    }
  }

  private class GiphyScrollListener extends InfiniteScrollListener {
    @Override
    public void onLoadMore(final int currentPage) {
      final Loader<List<GiphyImage>> loader = getLoaderManager().getLoader(0);
      if (loader == null) return;

      new AsyncTask<Void, Void, List<GiphyImage>>() {
        @Override
        protected List<GiphyImage> doInBackground(Void... params) {
          return ((GiphyLoader)loader).loadPage(currentPage * GiphyLoader.PAGE_SIZE);
        }

        protected void onPostExecute(List<GiphyImage> images) {
          giphyAdapter.addImages(images);
        }
      }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
  }

}
