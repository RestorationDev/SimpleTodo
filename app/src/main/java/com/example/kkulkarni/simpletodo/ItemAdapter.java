package com.example.kkulkarni.simpletodo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

//need to take data at a certain position and put it in View holder
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{
    public interface OnLongClickListener{
        void onItemLongClocked(int position);
    }
    List <String> items;
    OnLongClickListener longClickListener;
    public ItemAdapter(List<String> items, OnLongClickListener longClickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Use a layout inflater to inflate a view
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);
        // wrap it inside a view holder and then return it
        return new ViewHolder(todoView);
    }

    //binds view to a viewholder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Grab item at position,
        String item = items.get(position);

        //Bind item into specified view holder
        holder.bind(item);
    }

    //Tells recycler view how many items are in the list
    @Override
    public int getItemCount() {
        return items.size();
    }

    // container providing easy access to views that that represent each list row
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvItem;

        public ViewHolder(@NonNull View itemView) { //initial problems because needed a constructor
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }


        //Update the view inside of the view holder with the data
        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View view){
                    longClickListener.onItemLongClocked(getAdapterPosition()); //clocked?
                    return true; //remove item from recycler view
                }
            });

        }
    }
}
