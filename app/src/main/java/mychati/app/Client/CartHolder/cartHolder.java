package mychati.app.Client.CartHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import mychati.app.Client.ItemClickListner.ItemClickListener;
import mychati.app.R;

public class cartHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView textname,textprice,textmany,textplus,textminus;
    public RoundedImageView cartProductImage;
    public ItemClickListener itemClickListener;
public ImageView deletetovar;

    public cartHolder(View itemView){
        super(itemView);
        textmany=itemView.findViewById(R.id.manytovarcarttext);
        textname=itemView.findViewById(R.id.textcarttovarname);
        textprice=itemView.findViewById(R.id.textpriceinsidecart);
        textplus=itemView.findViewById(R.id.tovarinsidepluscart);
        deletetovar=itemView.findViewById(R.id.deletetovar);
        textminus=itemView.findViewById(R.id.tovarinsideminuscart);
        cartProductImage=itemView.findViewById(R.id.phototovarcart);
    }
    public void setItemClickListner(ItemClickListener listner){this.itemClickListener=listner;}

    @Override
    public void onClick(View view){

        itemClickListener.onClick(view,getAdapterPosition(),false);




    }
}
