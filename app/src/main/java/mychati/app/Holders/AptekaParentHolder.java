package mychati.app.Holders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import mychati.app.Client.ItemClickListner.ItemClickListener;
import mychati.app.R;

public class AptekaParentHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
public TextView textAptekaPare;
public RecyclerView recparentaptek;
    RecyclerView.LayoutManager layoutManager;
    public ItemClickListener itemClickListener;






    public AptekaParentHolder(View itemView){
        super(itemView);
        textAptekaPare=itemView.findViewById(R.id.textAptekaPare);
        recparentaptek=itemView.findViewById(R.id.recparentaptek);
        layoutManager = new LinearLayoutManager(itemView.getContext());
recparentaptek.setHasFixedSize(true);
recparentaptek.setLayoutManager(layoutManager);

    }
    public void setItemClickListner(ItemClickListener listner){this.itemClickListener=listner;}

    @Override
    public void onClick(View view){
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
