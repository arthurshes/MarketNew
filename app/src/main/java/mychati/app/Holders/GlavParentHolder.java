package mychati.app.Holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import mychati.app.Client.ItemClickListner.ItemClickListener;
import mychati.app.R;

public class GlavParentHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
public TextView luchsheetext;
public RecyclerView recyc_glavparent;
    RecyclerView.LayoutManager layoutManager;
    public ItemClickListener itemClickListener;


 public GlavParentHolder(View itemView){
     super(itemView);
     layoutManager = new LinearLayoutManager(itemView.getContext(),LinearLayoutManager.HORIZONTAL,false);
     luchsheetext=itemView.findViewById(R.id.luchsheetext);
     recyc_glavparent=itemView.findViewById(R.id.recyc_glavparent);
     recyc_glavparent.setHasFixedSize(true);
     recyc_glavparent.setLayoutManager(layoutManager);
 }
    public void setItemClickListner(ItemClickListener listner){this.itemClickListener=listner;}

    @Override
    public void onClick(View view){
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
