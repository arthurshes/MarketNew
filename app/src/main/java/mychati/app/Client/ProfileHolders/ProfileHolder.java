package mychati.app.Client.ProfileHolders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import mychati.app.Client.ItemClickListner.ItemClickListener;
import mychati.app.R;

public class ProfileHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView textDate,textTime,statuActiv,TextadressStat;
    public ItemClickListener itemClickListener;

    public ProfileHolder(View itemView){
        super(itemView);
        textDate=itemView.findViewById(R.id.textDate);
        textTime=itemView.findViewById(R.id.textTime);
        statuActiv=itemView.findViewById(R.id.statuActiv);
        TextadressStat=itemView.findViewById(R.id.TextadressStat);
    }

    public void setItemClickListner(ItemClickListener listner){this.itemClickListener=listner;}

    @Override
    public void onClick(View view){
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
