package com.rere.uas_ppk.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rere.uas_ppk.PostActivity;
import com.rere.uas_ppk.R;
import com.rere.uas_ppk.model.Post;
import com.rere.uas_ppk.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserProfileRecyclerViewAdapter extends RecyclerView.Adapter<UserProfileRecyclerViewAdapter.ViewHolder> {

    List<Post> posts;
    Context context;

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public UserProfileRecyclerViewAdapter(Context context) {
        this.posts = new ArrayList<>();
        this.context = context;
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView, authorTextView;
        CardView postCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.JudulTextView);
            authorTextView = itemView.findViewById(R.id.authorTextView);
            postCardView = itemView.findViewById(R.id.postCardView);
        }

        public void bind(Post post) {
            titleTextView.setText(post.getTitle());
            authorTextView.setText(post.getAuthor().getName());
            postCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, PostActivity.class).putExtra("post_id", post.getId()));
                }
            });
        }
    }
}
