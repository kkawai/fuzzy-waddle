package com.example.android.fuzzy_waddle.ui;

/*
This module borrowed from open source Signal android app https://github.com/signalapp/Signal-Android
 */

import android.view.ViewStub;

import androidx.annotation.NonNull;

public class Stub<T> {

  private ViewStub viewStub;
  private T view;

  public Stub(@NonNull ViewStub viewStub) {
    this.viewStub = viewStub;
  }

  public T get() {
    if (view == null) {
      view = (T)viewStub.inflate();
      viewStub = null;
    }

    return view;
  }

  public boolean resolved() {
    return view != null;
  }

}
