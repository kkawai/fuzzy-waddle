package com.example.android.fuzzy_waddle;

import android.os.Bundle;

import com.example.android.fuzzy_waddle.model.GiphyImage;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements GiphyAdapter.OnGiphyImageClickListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getSupportActionBar() != null)
      getSupportActionBar().hide();
    setContentView(R.layout.activity_main);

    GiphyGifFragment giphyGifFragment;
    if (savedInstanceState == null) {
      giphyGifFragment = new GiphyGifFragment();
      getSupportFragmentManager().beginTransaction()
              .add(R.id.fragment, giphyGifFragment, "giphyGifFragment").commit();
    } else {
      giphyGifFragment = (GiphyGifFragment) getSupportFragmentManager()
              .findFragmentByTag("giphyGifFragment");
    }
    giphyGifFragment.setClickListener(this);
  }

  @Override
  public void onGiphyImageClicked(GiphyImage giphyImage) {
    getSupportFragmentManager().beginTransaction()
            .add(R.id.fragment,
                    GiphyGifFullscreenFragment
                            .newInstance(giphyImage.getGifUrl(), giphyImage.getGifAspectRatio()))
            .addToBackStack(null).commit();
  }

  @Override
  public void onBackPressed() {
    if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
      getSupportFragmentManager().popBackStack();
    } else {
        super.onBackPressed();
    }
  }
}
