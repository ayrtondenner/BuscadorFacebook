package br.com.ufg.buscadorfacebook.controllers;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import br.com.ufg.buscadorfacebook.model.FacebookRow;
import br.com.ufg.buscadorfacebook.util.FacebookRequester;
import br.com.ufg.buscadorfacebook.util.StringValidation;
import br.com.ufg.trabalhoquinta.R;
import br.com.ufg.buscadorfacebook.adapters.RecyclerAdapter;
import br.com.ufg.buscadorfacebook.util.PhotoValidation;


public class FacebookDialogFragmentController extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //int title = getArguments().getInt("title");

        Bundle bundle = getArguments();

        final String idRow = bundle.getString("idRow");

        FacebookRow facebookData = null;

        for (FacebookRow data : RecyclerAdapter.facebookRows) {
                   if(data.getId() == idRow)
                   {
                       facebookData = data;
                       break;
                   }
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_expanded_row, null);
        builder.setView(view);

        TextView nameView = (TextView) view.findViewById(R.id.nomeContent);
        nameView.setText(facebookData != null ? facebookData.getName() : StringValidation.BuscarStringResource(getActivity(), R.string.campoNaoEncontrado));

        TextView categoriaView = (TextView) view.findViewById(R.id.categoriaContent);
        categoriaView.setText(facebookData != null && facebookData.getCategory() != null && facebookData.getCategory() != "" ?
                facebookData.getCategory() : StringValidation.BuscarStringResource(getActivity(), R.string.campoNaoEncontrado));

        TextView enderecoView = (TextView) view.findViewById(R.id.enderecoContent);
        enderecoView.setText(StringValidation.ValidarEndereco(getActivity(), facebookData.getLocation()));


        TextView latitudeLongitudeView = (TextView) view.findViewById(R.id.latitudeLongitudeContent);
        latitudeLongitudeView.setText(StringValidation.ValidarLatitudeLongitude(getActivity(), facebookData.getLocation()));

        TextView idView = (TextView) view.findViewById(R.id.idContent);
        idView.setText(facebookData != null ? facebookData.getId() : StringValidation.BuscarStringResource(getActivity(), R.string.campoNaoEncontrado));

        final ImageView photoView = (ImageView) view.findViewById(R.id.photoContent);
        FacebookRequester.ImageRequest(idRow, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                PhotoValidation.PhotoSet(graphResponse, photoView, getActivity());
            }
        }, getActivity());

        Button acessoButton = (Button) view.findViewById(R.id.acessoFacebookButton);
        acessoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                try {
                    getActivity().getPackageManager().getPackageInfo("com.facebook.katana", 0);
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + idRow));
                } catch (Exception e) {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"  + idRow));
                }

                startActivity(intent);
            }
        });


        builder.setTitle(facebookData.getName());

        int x = 0;

        return builder.create();


    }
}
