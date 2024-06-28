package com.example.savenote.Adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savenote.Model.Nots;
import com.example.savenote.NotsClick;
import com.example.savenote.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CAdepter extends RecyclerView.Adapter<CViewHolder>{
    Context context;
    List<Nots> list;
    NotsClick listner;

    public CAdepter(Context context, List<Nots> list, NotsClick listner) {
        this.context = context;
        this.list = list;
        this.listner = listner;
    }

    @NonNull
    @Override
    public CViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CViewHolder(LayoutInflater.from(context).inflate(R.layout.nots_list , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull CViewHolder holder, int position) {
        holder.view_title.setText(list.get(position).getTitle());
        holder.view_title.setSelected(true);
        holder.Tv_not.setText(list.get(position).getNotes());
        holder.Tv_date.setText(list.get(position).getDate());
        holder.Tv_date.setSelected(true);

        if (list.get(position).isPinne()){
            holder.img_pin.setImageResource(R.drawable.piins);
        }else {
            holder.img_pin.setImageResource(0);
        }

        int C_code = getRColor();
        holder.Ca_view.setCardBackgroundColor(holder.itemView.getResources().getColor(C_code , null));

        holder.Ca_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onClick(list.get(holder.getAdapterPosition()));
            }
        });
        holder.Ca_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listner.onPress(list.get(holder.getAdapterPosition()) , holder.Ca_view);
                return true;
            }
        });
    }

    private int getRColor(){
        List<Integer> colorcode = new ArrayList<>();
        colorcode.add(R.color.color1);
        colorcode.add(R.color.color2);
        colorcode.add(R.color.color3);
        colorcode.add(R.color.color4);
        colorcode.add(R.color.color5);

        Random random = new Random();
        int R_color = random.nextInt(colorcode.size());
        return colorcode.get(R_color);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void FilterList(List<Nots> Flists){
        list = Flists;
        notifyDataSetChanged();
    }
}
class CViewHolder extends RecyclerView.ViewHolder {
    CardView Ca_view;
    TextView view_title , Tv_not , Tv_date;
    ImageView img_pin;

    public CViewHolder(@NonNull View itemView) {
        super(itemView);
        Ca_view = itemView.findViewById(R.id.Ca_view);
        view_title = itemView.findViewById(R.id.view_title);
        Tv_not = itemView.findViewById(R.id.Tv_not);
        Tv_date = itemView.findViewById(R.id.Tv_date);
        img_pin = itemView.findViewById(R.id.img_pin);
    }
}
