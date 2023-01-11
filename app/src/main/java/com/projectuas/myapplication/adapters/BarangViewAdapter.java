package com.projectuas.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.projectuas.myapplication.R;
import com.projectuas.myapplication.models.Barang;
import com.projectuas.myapplication.models.Post;

import java.util.List;

public class BarangViewAdapter extends RecyclerView.Adapter<BarangViewAdapter.ViewHolder> {
    private Context mContext;
    private List<Barang> mData;
    private OnItemLongClickListener monItemLongClickListener;

    public BarangViewAdapter(Context mContext, List<Barang> mData, OnItemLongClickListener monItemLongClickListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.monItemLongClickListener = monItemLongClickListener;
    }

    @NonNull
    @Override
    public BarangViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.barang_item,parent, false);
        return new ViewHolder(view, monItemLongClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BarangViewAdapter.ViewHolder holder, int position) {
        holder.tvNama.setText(mData.get(position).getNama());
        holder.tvStok.setText(Integer.toString(mData.get(position).getStok()));
        holder.tvCreatedDate.setText(mData.get(position).getCreated_date());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public TextView tvNama,tvStok, tvCreatedDate;
        public OnItemLongClickListener onItemLongClickListener;
        public ViewHolder(@NonNull View itemView, OnItemLongClickListener onItemLongClickListener) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvStok = itemView.findViewById(R.id.tv_stok);
            tvCreatedDate = itemView.findViewById(R.id.tv_created_date);
            this.onItemLongClickListener = onItemLongClickListener;

            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            onItemLongClickListener.onItemLongClick(v, getAdapterPosition());
            return false;
        }
    }

    public interface OnItemLongClickListener
    {
        void onItemLongClick(View v, int position);
    }
}
