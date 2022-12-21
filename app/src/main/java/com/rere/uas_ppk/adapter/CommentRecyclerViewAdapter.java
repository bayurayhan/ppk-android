package com.rere.uas_ppk.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rere.uas_ppk.R;
import com.rere.uas_ppk.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder> {

    protected List<Comment> comments;

    public CommentRecyclerViewAdapter() {
        comments = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);

        return new CommentRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(comments.get(position));

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView isiTextView, authorTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            isiTextView = itemView.findViewById(R.id.isiTextView);
            authorTextView = itemView.findViewById(R.id.authorTextView);
        }

        public void bind(Comment comment) {
            isiTextView.setText(comment.getBody());
            authorTextView.setText(comment.getAuthor().getName());
        }
    }
}
