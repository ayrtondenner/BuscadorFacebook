package br.com.ufg.buscadorfacebook.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.ufg.buscadorfacebook.util.JsonConverter;
import br.com.ufg.buscadorfacebook.util.SharedPreferencesUtil;
import br.com.ufg.trabalhoquinta.R;
import br.com.ufg.buscadorfacebook.adapters.RecyclerAdapter;
import br.com.ufg.buscadorfacebook.model.FacebookRow;
import br.com.ufg.buscadorfacebook.util.FacebookRequester;
import br.com.ufg.buscadorfacebook.util.StringValidation;

//Trabalho Ayrton Denner
//Matrícula 122249


public class MainPageController extends FragmentActivity {
    LoginButton facebookButton;

    CallbackManager callbackManager;

    Context context;

    Button buscarButton;
    EditText buscarEditText;
    Spinner buscarSpinner;
    RecyclerView recyclerView;

    ArrayAdapter<CharSequence> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this);

        setContentView(R.layout.main_page_activity);

        facebookButton = (LoginButton) this.findViewById(R.id.login_button);

        callbackManager = CallbackManager.Factory.create();
        List<String> facebookPermitions = Arrays.asList("email", "public_profile", "user_friends", "user_location", "user_likes");

        facebookButton.setReadPermissions(facebookPermitions);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final RecyclerAdapter recyclerAdapter = new RecyclerAdapter(new ArrayList<FacebookRow>(), this);
        recyclerView.setAdapter(recyclerAdapter);


        context = this;


        facebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult arg0) {
                Toast.makeText(context, "Sucesso!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException arg0) {
                Toast.makeText(context, "Erro!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(context, "Cancelar!", Toast.LENGTH_LONG).show();
            }
        });

        buscarButton = (Button) findViewById(R.id.buscarButton);
        buscarEditText = (EditText) findViewById(R.id.buscarEditText);
        buscarSpinner = (Spinner) findViewById(R.id.buscarSpinner);

        buscarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recyclerAdapter.removeAll();

                String termoDeBusca = buscarEditText.getText().toString();
                String quantidade = buscarSpinner.getSelectedItem().toString();

                SharedPreferencesUtil.writePreferences(context, SharedPreferencesUtil.ChavesPreferenciais.termoDeBusca, termoDeBusca);
                SharedPreferencesUtil.writePreferences(context, SharedPreferencesUtil.ChavesPreferenciais.quantidade, quantidade);


                FacebookRequester.PlaceRequest(Integer.parseInt(quantidade), termoDeBusca,
                        new GraphRequest.GraphJSONArrayCallback() {
                            @Override
                            public void onCompleted(JSONArray jsonArray, GraphResponse graphResponse) {

                                ArrayList<FacebookRow> locationModel = JsonConverter.JsonArrayToObjectArray(jsonArray, FacebookRow.class);


                                recyclerAdapter.addRange(locationModel);
                            }
                        }, context);

            }
        });

        SharedPreferencesUtil.obtainFilePreferences(this);

        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.valoresSpinner, android.R.layout.simple_spinner_item);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        buscarSpinner.setAdapter(spinnerAdapter);

        try {
            String termoDeBusca = SharedPreferencesUtil.readPreferences(context, SharedPreferencesUtil.ChavesPreferenciais.termoDeBusca);
            if (termoDeBusca != StringValidation.BuscarStringResource(context, R.string.campoNaoEncontrado)) {
                buscarEditText.setText(termoDeBusca);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String quantidade = SharedPreferencesUtil.readPreferences(context, SharedPreferencesUtil.ChavesPreferenciais.quantidade);
            if (quantidade != StringValidation.BuscarStringResource(context, R.string.campoNaoEncontrado)) {
                int position = spinnerAdapter.getPosition(quantidade);
                buscarSpinner.setSelection(position);
            }
        } catch (Exception e) {

        }


    }

    public void setVisibilidadeCampos(int visibilidadeCampos) {
        buscarButton.setVisibility(visibilidadeCampos);
        buscarEditText.setVisibility(visibilidadeCampos);
        buscarSpinner.setVisibility(visibilidadeCampos);
        recyclerView.setVisibility(visibilidadeCampos);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
