package br.com.ufg.buscadorfacebook.util;

import android.content.Context;

import br.com.ufg.buscadorfacebook.model.Location;
import br.com.ufg.trabalhoquinta.R;

public class StringValidation {

    public static String BuscarStringResource(Context context, int resource)
    {
        String retorno = "";

        try {
            retorno = context.getResources().getString(resource);
        }
        catch (Exception e)
        {

        }

        return  retorno;
    }
    public static String ValidarEndereco(Context context, Location location) {
        String endereco = "";

        if (location.getStreet() != null && location.getStreet() != "" && location.getStreet() != "null") {
            endereco += location.getStreet();
        }

        if (location.getCity() != null && location.getCity() != "" && location.getCity() != "null") {
            if (endereco == "") {
                endereco += location.getCity();
            } else {
                endereco += ", " + location.getCity();
            }
        }

        if (location.getState() != null && location.getState() != "" && location.getState() != "null") {
            if (endereco == "") {
                endereco += location.getState();
            } else {
                endereco += ", " + location.getState();
            }
        }

        if (location.getCountry() != null && location.getCountry() != "" && location.getCountry() != "null") {
            if (endereco == "") {
                endereco += location.getCountry();
            } else {
                endereco += ", " + location.getCountry();
            }
        }

        if (endereco == "") {
            endereco = BuscarStringResource(context, R.string.campoNaoEncontrado);
        }

        return endereco;
    }

    public static String ValidarLatitudeLongitude(Context context, Location location)
    {
        String latitudeLongitude;
            if(location.getLatitude() != null && location.getLongitude() != null)
            {
                latitudeLongitude = location.getLatitude() + ", " + location.getLongitude();
            }
            else
            {
                latitudeLongitude = StringValidation.BuscarStringResource(context, R.string.campoNaoEncontrado);
            }

        return  latitudeLongitude;

    }
}
