package com.example.oliver.l18_googlemaps;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class FetchAddressIntentService extends IntentService {
    protected ResultReceiver mReceiver;


    public FetchAddressIntentService() {
        super("FetchAddressIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        if (intent != null) {
            String errorMessage = "";

            // Get the location passed to this service through an extra.
            Location location = intent.getParcelableExtra(Constants.LOCATION_DATA_EXTRA);
            mReceiver = intent.getParcelableExtra(Constants.RECEIVER);
            List<Address> addresses = null;

            try {
                addresses = geocoder.getFromLocation(
                        location.getLatitude(),
                        location.getLongitude(),
                        // In this sample, get just a single address.
                        1);
            } catch (IOException ioException) {
                // Catch network or other I/O problems.
                errorMessage = getString(R.string.service_not_available);
                Log.e(Constants.TAG, errorMessage, ioException);
            } catch (IllegalArgumentException illegalArgumentException) {
                // Catch invalid latitude or longitude values.
                errorMessage = getString(R.string.invalid_lat_long_used);
                Log.e(Constants.TAG, errorMessage + ". " +
                        "Latitude = " + location.getLatitude() +
                        ", Longitude = " +
                        location.getLongitude(), illegalArgumentException);
            }

            // Handle case where no address was found.
            if (addresses == null || addresses.size()  == 0) {
                if (errorMessage.isEmpty()) {
                    errorMessage = getString(R.string.no_address_found);
                    Log.e(Constants.TAG, errorMessage);
                }
                deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage);
            } else {
                Address address = addresses.get(0);
                Log.i(Constants.TAG, getString(R.string.address_found));
                deliverResultToReceiver(Constants.SUCCESS_RESULT, getAddressInfo(address));
            }

        }
    }

    private String getAddressInfo(Address address) {
        StringBuilder result = new StringBuilder();
        String city          = address.getLocality();
        String state         = address.getAdminArea();
        String country       = address.getCountryName();
        String postalCode    = address.getPostalCode();
        String countryCode   = address.getCountryCode();
        String street        = address.getAddressLine(0);


        result.append( (countryCode!= null)  ? "Country code: " + countryCode + "\n": "" );
        result.append( (country != null)     ? "Country: " + country + "\n"         : "" );
        result.append( (state != null)       ? "State: " + state + "\n"             : "" );
        result.append( (city != null)        ? "City: " + city + "\n"               : "" );
        result.append( (postalCode != null)  ? "Postal code: " + postalCode + "\n"  : "" );
        result.append( (street != null)      ? "Address: " + street                 : "" );

        return  result.toString();
    }

    private void deliverResultToReceiver(int resultCode, String message) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.RESULT_DATA_KEY, message);
        mReceiver.send(resultCode, bundle);
    }

}
