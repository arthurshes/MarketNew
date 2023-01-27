package mychati.app.Client.ClientTovarsHolder;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import mychati.app.Client.FragBottom.BottomFrag;
import mychati.app.Client.ItemClickListner.ItemClickListener;
import mychati.app.Client.OpisanieBottomFrag.TovarBottomOpisFragment;
import mychati.app.Client.TovarActivity;
import mychati.app.R;

public class ClientTivarHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView texttovarstatnonalkli,texttovarstatyesnalkli,texttovarname,texttovarprice,tovarpus,tovarminus,tovarcart;
    public RoundedImageView imageTovar;
    public CardView cardTovar;
    public ItemClickListener itemClickListener;
    private Dialog dialog;
    private Context context;

    public ClientTivarHolder(View itemView){
        super(itemView);
        texttovarname=itemView.findViewById(R.id.texttovrnme);
        texttovarprice=itemView.findViewById(R.id.pricetexttovar);
        imageTovar=itemView.findViewById(R.id.tovarImage);
        cardTovar=itemView.findViewById(R.id.tovarcard);
        texttovarstatnonalkli=itemView.findViewById(R.id.texttovarstatnonalkli);
        texttovarstatyesnalkli=itemView.findViewById(R.id.texttovarstatyesnalkli);
tovarpus=itemView.findViewById(R.id.tovarpluscart);
tovarminus=itemView.findViewById(R.id.tovarminuscart);


tovarcart=itemView.findViewById(R.id.tovarcart);

    }


    public void setItemClickListner(ItemClickListener listner){this.itemClickListener=listner;}

    @Override
    public void onClick(View view){

        itemClickListener.onClick(view,getAdapterPosition(),false);




    }
}
