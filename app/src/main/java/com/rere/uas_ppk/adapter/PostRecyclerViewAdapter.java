package com.rere.uas_ppk.adapter;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rere.uas_ppk.R;
import com.rere.uas_ppk.model.Post;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PostRecyclerViewAdapter extends RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder> {

    protected ArrayList<Post> posts = new ArrayList<>();

    public PostRecyclerViewAdapter(ArrayList<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(posts.get(position));

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, authorTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.JudulTextView);
            authorTextView = itemView.findViewById(R.id.authorTextView);
        }

        public void bind(Post post) {
            titleTextView.setText(post.getTitle());
            authorTextView.setText(post.getAuthor().getName());
        }
    }


}
