package br.com.ufg.buscadorfacebook.util;

import android.content.Context;
import android.content.SharedPreferences;

import br.com.ufg.trabalhoquinta.R;

/**
 * Created by Ayrton on 06/06/2015.
 */
public class SharedPreferencesUtil {


    public enum ChavesPreferenciais
    {
        termoDeBusca,
        quantidade
    }
    public static SharedPreferences obtainFilePreferences(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SharedPreferencesUtil.class.getCanonicalName(), Context.MODE_PRIVATE);

        return sharedPreferences;
    }

    public static void writePreferences(Context context, ChavesPreferenciais chave, String valor)
    {
       obtainFilePreferences(context).edit().putString(chave.toString(), valor).apply();
    }

    public static String readPreferences(Context context, ChavesPreferenciais chave)
    {
        String retorno = obtainFilePreferences(context).getString(chave.toString(), StringValidation.BuscarStringResource(context, R.string.campoNaoEncontrado));

        return  retorno;
    }
}
