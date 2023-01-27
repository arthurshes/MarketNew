package mychati.app.Client.ClientShopsHolders;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import mychati.app.Client.ItemClickListner.ItemClickListener;
import mychati.app.Client.TovarActivity;
import mychati.app.R;

public class ClientShopHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView aotekaname,textcityapt,textadresssapt,aotekaas1,textmedes,textstar;
    public RoundedImageView imageLogoApteka;
    public CardView card_item;
    public ImageView rateyellow1,rateyellow2,rateyellow3,rateyellow4,rateyellow5;
public ItemClickListener itemClickListener;

    public ClientShopHolder(View iteemView){
        super(iteemView);
        aotekaname=iteemView.findViewById(R.id.aotekaname);
       /// textcityapt=iteemView.findViewById(R.id.textcityapt);
       /// textadresssapt=iteemView.findViewById(R.id.textadresssapt);
        imageLogoApteka=iteemView.findViewById(R.id.imageLogoApteka);
        rateyellow1=iteemView.findViewById(R.id.rateyellow1);
        rateyellow2=iteemView.findViewById(R.id.rateyellow2);
        rateyellow3=iteemView.findViewById(R.id.rateyellow3);
        textmedes=iteemView.findViewById(R.id.textmedes);
        aotekaas1=iteemView.findViewById(R.id.aotekaas1);
        textstar=iteemView.findViewById(R.id.textstar);
        rateyellow4=iteemView.findViewById(R.id.rateyellow4);
        rateyellow5=iteemView.findViewById(R.id.rateyellow5);
        card_item=iteemView.findViewById(R.id.card_item);

    }
    public void setItemClickListner(ItemClickListener listner){this.itemClickListener=listner;}

    @Override
    public void onClick(View view){
       itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}

