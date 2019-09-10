package com.getguard.client.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.getguard.client.R;
import com.getguard.client.models.network.EventResponse;
import com.getguard.client.models.network.EventType;
import com.getguard.client.models.network.EventsResponse;
import com.getguard.client.utils.Config;
import com.getguard.client.utils.Consts;
import com.getguard.client.utils.Consumer;
import com.getguard.client.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private Consumer<EventResponse.RespondedUser> listener;
    private ArrayList<EventResponse.RespondedUser> items = new ArrayList<>();

    public UsersAdapter(Consumer<EventResponse.RespondedUser> listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        EventResponse.RespondedUser item = getItem(position);

        holder.getHolder().setOnClickListener(view -> {
            listener.accept(item);
        });

            holder.getNameText().setText(item.getUserName());
            Glide.with(holder.itemView.getContext())
                    .load(Config.BASE_URL + item.getPhotoId())
                    .apply(new RequestOptions().centerCrop())
                    .into(holder.getImage());

        holder.getRatingText().setText(item.getRating() + ", " + item.getUserRatingCount() + "Оценки");
        holder.getRatingBar().setRating(item.getRating());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public EventResponse.RespondedUser getItem(int position) {
        return items.get(position);
    }

    public void setItems(List<EventResponse.RespondedUser> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout holder;
        private TextView nameText, ratingText;
        private ImageView image;
        private AppCompatRatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);

            holder = itemView.findViewById(R.id.holder);
            nameText = itemView.findViewById(R.id.name_text);
            ratingText = itemView.findViewById(R.id.rating_text);
            image = itemView.findViewById(R.id.image);
            ratingBar = itemView.findViewById(R.id.rating_bar);

        }

        public RelativeLayout getHolder() {
            return holder;
        }

        public TextView getNameText() {
            return nameText;
        }

        public TextView getRatingText() {
            return ratingText;
        }

        public ImageView getImage() {
            return image;
        }

        public AppCompatRatingBar getRatingBar() {
            return ratingBar;
        }
    }

}