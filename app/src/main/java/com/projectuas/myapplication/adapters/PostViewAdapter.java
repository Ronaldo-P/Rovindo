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

public class PostViewAdapter extends RecyclerView.Adapter<PostViewAdapter.ViewHolder> {
    private Context mContext;
    private List<Post> mData;
    private OnItemLongClickListener monItemLongClickListener;

    public PostViewAdapter(Context mContext, List<Post> mData, OnItemLongClickListener monItemLongClickListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.monItemLongClickListener = monItemLongClickListener;
    }

    @NonNull
    @Override
    public PostViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,parent, false);
        return new ViewHolder(view, monItemLongClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewAdapter.ViewHolder holder, int position) {
        holder.tvUsername.setText(mData.get(position).getUsername());
        holder.tvContent.setText(mData.get(position).getContent());
        holder.tvCreatedDate.setText(mData.get(position).getCreated_date());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public TextView tvUsername,tvContent, tvCreatedDate;
        public OnItemLongClickListener onItemLongClickListener;
        public ViewHolder(@NonNull View itemView, OnItemLongClickListener onItemLongClickListener) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tv_username);
            tvContent = itemView.findViewById(R.id.tv_content);
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
