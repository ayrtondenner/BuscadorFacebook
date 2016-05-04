package br.com.ufg.buscadorfacebook.adapters;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import java.util.ArrayList;
import java.util.List;

import br.com.ufg.buscadorfacebook.controllers.FacebookDialogFragmentController;
import br.com.ufg.buscadorfacebook.model.FacebookRow;
import br.com.ufg.buscadorfacebook.util.FacebookRequester;
import br.com.ufg.buscadorfacebook.util.StringValidation;
import br.com.ufg.trabalhoquinta.R;
import br.com.ufg.buscadorfacebook.util.PhotoValidation;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.FacebookRowViewHolder> {

    public static List<FacebookRow> facebookRows;
    private FragmentActivity context;
    static String loadingString;


    public RecyclerAdapter(List<FacebookRow> facebookRows, FragmentActivity context) {
        this.facebookRows = facebookRows;
        this.context = context;
        this.loadingString = context.getResources().getString(R.string.com_facebook_loading);
    }

    @Override
    public FacebookRowViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_page_row, viewGroup, false);
        FacebookRowViewHolder facebookRowViewHolder = new FacebookRowViewHolder(view);
        return facebookRowViewHolder;
    }


    @Override
    public void onBindViewHolder(final FacebookRowViewHolder holder, final int position) {
        final FacebookRow facebookRow = facebookRows.get(position);

        if (facebookRow != null) {

            final FacebookRowViewHolder finalHolder = holder;

            finalHolder.rowName.setText(facebookRow.getName());

            finalHolder.rowLocation.setText(StringValidation.ValidarEndereco(context, facebookRow.getLocation()));

            finalHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DialogFragment dialogFragment = new FacebookDialogFragmentController();

                    Bundle bundle = new Bundle();

                    bundle.putString("idRow", facebookRow.getId());

                    dialogFragment.setArguments(bundle);
                    dialogFragment.show(context.getSupportFragmentManager(), dialogFragment.toString());

                }
            });

            final ImageView imageView = finalHolder.rowImage;

            String tag = imageView.getTag().toString();


            if (tag == loadingString) {

                FacebookRequester.ImageRequest(facebookRow.getId(), new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        PhotoValidation.PhotoSet(response, imageView, context);
                    }
                }, context);
            }
        }
    }

    @Override
    public int getItemCount() {
        return facebookRows.size();
    }

    public static class FacebookRowViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView rowName;
        TextView rowLocation;
        ImageView rowImage;

        FacebookRowViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card);
            rowName = (TextView) itemView.findViewById(R.id.rowName);
            rowLocation = (TextView) itemView.findViewById(R.id.rowLocation);
            rowImage = (ImageView) itemView.findViewById(R.id.profile_icon);
        }
    }

    public void addRange(List<FacebookRow> rows) {

        if (rows != null && rows.size() > 0) {
            facebookRows.addAll(rows);
            notifyDataSetChanged();
        }
    }

    public void removeAll() {
        facebookRows = new ArrayList<FacebookRow>();
        notifyDataSetChanged();
    }

    public void setImage(String id) {
        FacebookRow facebookRow = null;

        for (FacebookRow facebookData : facebookRows) {
            if (facebookData.getId() == id) {
                facebookRow = facebookData;
                break;
            }
        }

        notifyDataSetChanged();
    }

}
