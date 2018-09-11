package com.gft.wishpinsdksample.worker;

import com.gemalto.cardcompanionsdk.beans.parameters.DefinitionParameters;
import com.gemalto.cardcompanionsdk.beans.parameters.GetRSAParameters;
import com.gemalto.cardcompanionsdk.beans.response.DefinitionResponse;
import com.gemalto.cardcompanionsdk.beans.response.GetRSAResponse;
import com.gemalto.cardcompanionsdk.requests.DefinitionRequest;
import com.gemalto.cardcompanionsdk.requests.RSAKeyRequest;
import com.gft.wishpinsdksample.Constants;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;

public class DefineWishPinWorker extends Worker {

  private static final String SUCCESS_CODE = "0";

  @NonNull
  @Override
  public Result doWork() {
    Result result = null;
    DefinitionResponse definitionResponse = null;

    Data incomingData = this.getInputData();

    //First we have to get an RSA key from configuration
    GetRSAResponse rsaResponse =
        new RSAKeyRequest()
            .postDataForGetRSARequest(
                this.getApplicationContext(),
                new GetRSAParameters(incomingData.getString(Constants.KEY_BANK),
                    incomingData.getString(Constants.KEY_CARD_ID),
                    incomingData.getString(Constants.KEY_TIMESTAMP),
                    incomingData.getString(Constants.KEY_HMAC)),
                incomingData.getString(Constants.KEY_HOST_URL));

    //Having the RSA Key We proceed to define a WISH PIN
    if (rsaResponse.getKey() != null && !rsaResponse.getKey().isEmpty()) {
      definitionResponse = new DefinitionRequest()
          .postDataToDefinePin(
              this.getApplicationContext(),
              new DefinitionParameters(incomingData.getString(Constants.KEY_BANK),
                  incomingData.getString(Constants.KEY_CARD_ID),
                  incomingData.getString(Constants.KEY_TIMESTAMP),
                  incomingData.getString(Constants.KEY_HMAC),
                  incomingData.getString(Constants.KEY_WISH_PIN)),
              incomingData.getString(Constants.KEY_HOST_URL),
              rsaResponse.getKey());
    }

    result = definitionResponse != null && definitionResponse.getErrorCode().equals(SUCCESS_CODE)
        ? Result.SUCCESS
        : Result.FAILURE;

    return result;
  }
}