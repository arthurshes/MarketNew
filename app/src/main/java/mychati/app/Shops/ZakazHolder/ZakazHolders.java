package mychati.app.Shops.ZakazHolder;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import mychati.app.Client.ItemClickListner.ItemClickListener;
import mychati.app.R;

public class ZakazHolders  extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView price,phone;
    public ItemClickListener itemClickListener;
    public CardView card;

    public ZakazHolders(View itemView){
        super(itemView);
        price=itemView.findViewById(R.id.textpricezakazshop);
        card=itemView.findViewById(R.id.cardmuzakaz);
        phone=itemView.findViewById(R.id.textnumberphonezakaz);
    }

    public void setItemClickListner(ItemClickListener listner){this.itemClickListener=listner;}

    @Override
    public void onClick(View view){

        itemClickListener.onClick(view,getAdapterPosition(),false);




    }
}
