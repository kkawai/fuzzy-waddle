package com.example.android.fuzzy_waddle.concurrent;
/*
This module borrowed from open source Signal android app https://github.com/signalapp/Signal-Android
 */

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public interface ListenableFuture<T> extends Future<T> {
  void addListener(Listener<T> listener);

  public interface Listener<T> {
    public void onSuccess(T result);
    public void onFailure(ExecutionException e);
  }
}
