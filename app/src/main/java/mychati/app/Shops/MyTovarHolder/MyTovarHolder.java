package mychati.app.Shops.MyTovarHolder;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import mychati.app.Client.ItemClickListner.ItemClickListener;
import mychati.app.R;

public class MyTovarHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView textMytovarname,textmytovarPrice,txtStatusyes,txtStatusno;
    public RoundedImageView myTovarImage;
    public ItemClickListener itemClickListener;
    public CardView myTovarCard;

    public MyTovarHolder(View itemView){
        super(itemView);
        textMytovarname=itemView.findViewById(R.id.tovaarmyname);
        textmytovarPrice=itemView.findViewById(R.id.tovarmyprice);
        myTovarCard=itemView.findViewById(R.id.tovarmycard);
        txtStatusno=itemView.findViewById(R.id.texttovarstatnonal);
        txtStatusyes=itemView.findViewById(R.id.texttovarstatyesnal);
        myTovarImage=itemView.findViewById(R.id.tovarmyimage);
    }
    public void setItemClickListner(ItemClickListener listner){this.itemClickListener=listner;}

    @Override
    public void onClick(View view){

        itemClickListener.onClick(view,getAdapterPosition(),false);




    }
}
