package com.gft.wishpinsdksample.viewmodel;

import com.gft.wishpinsdksample.Constants;
import com.gft.wishpinsdksample.MainActivity;
import com.gft.wishpinsdksample.worker.DefineWishPinWorker;

import java.util.HashMap;

import androidx.lifecycle.ViewModel;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class MainViewModel extends ViewModel implements MainActivity.ViewModel {

  @Override
  public void defineWishPin() {
      Data someData = new Data.Builder()
          .putAll(this.defaultConfiguration())
          .build();

      Constraints constraints = new Constraints.Builder()
          .setRequiredNetworkType(NetworkType.CONNECTED)
          .build();

      OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(DefineWishPinWorker.class)
          .setConstraints(constraints)
          .setInputData(someData)
          .build();

      WorkManager.getInstance().enqueue(request);
    }

  private HashMap<String, Object> defaultConfiguration() {
    return new DefinitionObjectBuilder()
        .bankId("BANK_ID")
        .cardId("CARD_ID")
        .hmac("84af4fdd5b6d22e40d710d8bd8c146281ddf19b47ff69741da53d38c9b4efa97")
        .timestamp("1536580188")
        .hostUrl("https://mock.com/WishPinDefinition_Server/mobile/")
        .wishPin("2468")
        .toMap();
  }

  class DefinitionObjectBuilder {
    private String bankId;
    private String cardId;
    private String hmac;
    private String timestamp;
    private String hostUrl;
    private String wishPin;

    public DefinitionObjectBuilder bankId(String bankId) {
      this.bankId = bankId;
      return this;
    }

    public DefinitionObjectBuilder cardId(String cardId) {
      this.cardId = cardId;
      return this;
    }

    public DefinitionObjectBuilder hmac(String hmac) {
      this.hmac = hmac;
      return this;
    }

    public DefinitionObjectBuilder timestamp(String timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public DefinitionObjectBuilder hostUrl(String hostUrl) {
      this.hostUrl = hostUrl;
      return this;
    }

    public DefinitionObjectBuilder wishPin(String wishPin) {
      this.wishPin = wishPin;
      return this;
    }

    public HashMap<String, Object> toMap() {
      HashMap<String, Object> data = new HashMap<>();
      data.put(Constants.KEY_BANK, this.bankId);
      data.put(Constants.KEY_CARD_ID, this.cardId);
      data.put(Constants.KEY_TIMESTAMP, this.timestamp);
      data.put(Constants.KEY_HMAC, this.hmac);
      data.put(Constants.KEY_HOST_URL, this.hostUrl);
      data.put(Constants.KEY_WISH_PIN, this.wishPin);

      return data;
    }
  }
}
