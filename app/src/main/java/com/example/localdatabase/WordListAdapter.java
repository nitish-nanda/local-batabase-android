package com.example.localdatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localdatabase.models.WordModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private Context context;
    private List<WordModel> mWordModels;
    private OnItemClickListener<WordModel> listenerDelete, listenerUpdate;

    public WordListAdapter(Context context, List<WordModel> mWordModels, OnItemClickListener<WordModel> listenerDelete, OnItemClickListener<WordModel> listenerUpdate) {
        this.context = context;
        this.mWordModels = mWordModels;
        this.listenerDelete = listenerDelete;
        this.listenerUpdate = listenerUpdate;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        WordModel model = mWordModels.get(position);
        holder.textView.setText(model.getWord());
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerDelete.onClickNotify(model, position);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerUpdate.onClickNotify(model, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mWordModels.size();
    }

    static class WordViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView)
        public TextView textView;

        @BindView(R.id.imageView)
        public ImageView ivDelete;

        private WordViewHolder(View itemView) {
            super(itemView);
            try {
                ButterKnife.bind(this, itemView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}