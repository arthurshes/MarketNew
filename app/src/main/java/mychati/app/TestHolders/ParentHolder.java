package mychati.app.TestHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import mychati.app.Client.ItemClickListner.ItemClickListener;
import mychati.app.R;

public class ParentHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public RecyclerView nestedrecer;
    public RoundedImageView roundedImagemaglogoparent;
    public AppCompatButton buylist;
    public TextView textitogParent;
    public ImageView imagedeletemag;
    public int dodo;
    RecyclerView.LayoutManager layoutManager;
    public ItemClickListener itemClickListener;


    public ParentHolder( View itemView) {
        super(itemView);
        nestedrecer=itemView.findViewById(R.id.nestedrecer);
        roundedImagemaglogoparent=itemView.findViewById(R.id.roundedImagemaglogoparent);
        textitogParent=itemView.findViewById(R.id.textitogParent);
        imagedeletemag=itemView.findViewById(R.id.imagedeletemag);
        layoutManager = new LinearLayoutManager(itemView.getContext());
        nestedrecer.setHasFixedSize(true);
        nestedrecer.setLayoutManager(layoutManager);


        buylist=itemView.findViewById(R.id.buylist);
    }

    public void setItemClickListner(ItemClickListener listner){this.itemClickListener=listner;}

    @Override
    public void onClick(View view){

        itemClickListener.onClick(view,getAdapterPosition(),false);




    }



}

