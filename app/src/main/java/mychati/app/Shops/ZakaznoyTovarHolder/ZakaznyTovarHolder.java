package mychati.app.Shops.ZakaznoyTovarHolder;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import mychati.app.Client.ItemClickListner.ItemClickListener;
import mychati.app.R;

public class ZakaznyTovarHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ItemClickListener itemClickListener;

    public CardView cardcardshop;
    public RoundedImageView tovarphotoshop;
    public TextView tovarnameshop,tovarpriceshop,tovarvalueshop;







    public ZakaznyTovarHolder(View itemView){
        super(itemView);
        tovarvalueshop=itemView.findViewById(R.id.tovarvalueshop);

        tovarnameshop=itemView.findViewById(R.id.tovarnameshop);
        tovarpriceshop=itemView.findViewById(R.id.tovarpriceshop);

        cardcardshop=itemView.findViewById(R.id.cardcardshop);

        tovarphotoshop=itemView.findViewById(R.id.tovarphotoshop);

    }
    public void setItemClickListner(ItemClickListener listner){this.itemClickListener=listner;}

    @Override
    public void onClick(View view){

        itemClickListener.onClick(view,getAdapterPosition(),false);




    }
}
