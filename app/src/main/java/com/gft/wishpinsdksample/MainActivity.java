package com.gft.wishpinsdksample;

import android.os.Bundle;

import com.gft.wishpinsdksample.viewmodel.MainViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {

  private ViewModel aViewModel;

  public interface ViewModel {
    void defineWishPin();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.activity_main);

    this.aViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    this.aViewModel.defineWishPin();
  }
}
