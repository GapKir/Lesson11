package com.example.lesson11.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lesson11.Utils.Actions;
import com.example.lesson11.databinding.ItemSingerBinding;
import com.example.lesson11.model.Singer;

import java.util.List;

public class SingerListAdapter extends RecyclerView.Adapter<SingerListAdapter.PlayListViewHolder> implements View.OnClickListener {
    private List<Singer> singerList;
    private Actions actions;


    public void setSingerList(List<Singer> singerList) {
        this.singerList = singerList;
        notifyDataSetChanged();
    }

    public void setActions(Actions actions) {
        this.actions = actions;
    }


    @NonNull
    @Override
    public PlayListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemSingerBinding binding = ItemSingerBinding.inflate(inflater, parent, false);

        binding.itemSinger.setOnClickListener(this);

        return new PlayListViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull PlayListViewHolder holder, int position) {
        Singer singer = singerList.get(position);
        holder.bind(singer);
    }

    @Override
    public int getItemCount() {
        return singerList.size();
    }

    @Override
    public void onClick(View view) {
        Singer singer = (Singer) view.getTag();
        actions.showFilteredSongsFromSinger(singer);
    }

    class PlayListViewHolder extends RecyclerView.ViewHolder {
        private final ItemSingerBinding binding;

        public PlayListViewHolder(ItemSingerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Singer singer) {
            binding.itemSinger.setTag(singer);

            binding.ivSingerPhoto.setImageResource(singer.getSingerPhoto());
            binding.tvSingerName.setText(singer.getName());
            binding.tvSingerBirth.setText(singer.getBirthCountry());
        }
    }
}
