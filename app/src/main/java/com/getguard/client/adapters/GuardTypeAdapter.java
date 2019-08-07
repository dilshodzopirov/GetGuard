package com.getguard.client.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.getguard.client.R;
import com.getguard.client.models.network.EventType;
import com.getguard.client.utils.Config;
import com.getguard.client.utils.Consumer;
import com.getguard.client.utils.Utils;

import java.util.ArrayList;

public class GuardTypeAdapter extends RecyclerView.Adapter<GuardTypeAdapter.ViewHolder> {

    private Consumer<EventType> listener;
    private ArrayList<EventType> items = new ArrayList<>();

    public GuardTypeAdapter(Consumer<EventType> listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_guard_type, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        EventType item = getItem(position);

        holder.getHolder().setOnClickListener(view -> {
                listener.accept(item);
        });
        holder.getNameText().setText(item.getName());
        Glide.with(holder.itemView.getContext())
                .load(Config.BASE_URL + item.getUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.getOverlayBg().setVisibility(View.GONE);
                        return false;
                    }
                })
                .apply(new RequestOptions().centerCrop())
                .into(holder.getBgImg());
        holder.getDescriptionText().setText(item.getDescription());
        holder.getPriceText().setText(Utils.roundAmount(item.getMinPrice()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public EventType getItem(int position) {
        return items.get(position);
    }

    public void setItems(ArrayList<EventType> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private FrameLayout holder;
        private ImageView bgImg;
        private TextView nameText, descriptionText, priceText;
        private View overlayBg;

        public ViewHolder(View itemView) {
            super(itemView);

            holder = itemView.findViewById(R.id.holder);
            bgImg = itemView.findViewById(R.id.bg_img);
            nameText = itemView.findViewById(R.id.name_text);
            descriptionText = itemView.findViewById(R.id.description_text);
            priceText = itemView.findViewById(R.id.price_text);
            overlayBg = itemView.findViewById(R.id.overlay_bg);
        }

        public View getOverlayBg() {
            return overlayBg;
        }

        public FrameLayout getHolder() {
            return holder;
        }

        public ImageView getBgImg() {
            return bgImg;
        }

        public TextView getNameText() {
            return nameText;
        }

        public TextView getDescriptionText() {
            return descriptionText;
        }

        public TextView getPriceText() {
            return priceText;
        }
    }

}