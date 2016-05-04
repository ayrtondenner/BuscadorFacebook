package br.com.ufg.buscadorfacebook.util;

import android.content.Context;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.HttpMethod;

public class FacebookRequester {

    public static void PlaceRequest(int resultLimit, String searchText, GraphRequest.GraphJSONArrayCallback callback, Context context) {
        try {
            if (AccessToken.getCurrentAccessToken() != null) {
                GraphRequest request = GraphRequest.newPlacesSearchRequest
                        (AccessToken.getCurrentAccessToken(), null, Integer.MAX_VALUE, resultLimit, searchText, callback);

                request.executeAsync();
            } else {
                Toast.makeText(context, "Por favor, logue primeiro!", Toast.LENGTH_LONG).show();
            }
        }
        catch ( Exception e)
        {
            Toast.makeText(context, "Digite um valor válido!", Toast.LENGTH_LONG).show();
        }


    }

    public static void ImageRequest(String id, GraphRequest.Callback callback, Context context) {
        if (AccessToken.getCurrentAccessToken() != null) {
            GraphRequestAsyncTask async = new GraphRequest(
                    AccessToken.getCurrentAccessToken(), "/" + id + "/picture", null, HttpMethod.GET, callback
            ).executeAsync();
        } else {
            Toast.makeText(context, "Por favor, logue primeiro!", Toast.LENGTH_LONG).show();
        }
    }
}
