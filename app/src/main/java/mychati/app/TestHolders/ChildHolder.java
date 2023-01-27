package mychati.app.TestHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import mychati.app.Client.ItemClickListner.ItemClickListener;
import mychati.app.R;

public class ChildHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView textnamechild,textpricechildtovar,tovarminuschildto,tovarpluschildto,tovarcartchild;
    public RoundedImageView imagetovarchild;
    public ItemClickListener itemClickListener;

    public ChildHolder(@NonNull View itemView) {
        super(itemView);
        textnamechild=itemView.findViewById(R.id.textnamechild);
        textpricechildtovar=itemView.findViewById(R.id.textpricechildtovar);
        tovarminuschildto=itemView.findViewById(R.id.tovarminuschildto);
        tovarpluschildto=itemView.findViewById(R.id.tovarpluschildto);
        tovarcartchild=itemView.findViewById(R.id.tovarcartchild);
        imagetovarchild=itemView.findViewById(R.id.imagetovarchild);
    }

    public void setItemClickListner(ItemClickListener listner){this.itemClickListener=listner;}

    @Override
    public void onClick(View view){

        itemClickListener.onClick(view,getAdapterPosition(),false);




    }

}
