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
import com.getguard.client.models.network.EventsResponse;
import com.getguard.client.utils.Config;
import com.getguard.client.utils.Consts;
import com.getguard.client.utils.Consumer;
import com.getguard.client.utils.Utils;

import java.util.ArrayList;

public class GuardAdapter extends RecyclerView.Adapter<GuardAdapter.ViewHolder> {

    private Consumer<EventsResponse.Event> listener;
    private ArrayList<EventsResponse.Event> items = new ArrayList<>();

    public GuardAdapter(Consumer<EventsResponse.Event> listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_guard, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        EventsResponse.Event item = getItem(position);
        EventType eventType = Consts.eventTypeMap.get(item.getEventType());

        holder.getHolder().setOnClickListener(view -> {
            listener.accept(item);
        });

        if (eventType != null) {
            holder.getEventText().setText(eventType.getName());
            Glide.with(holder.itemView.getContext())
                    .load(Config.BASE_URL + eventType.getUrl())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.overlayBg.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .apply(new RequestOptions().centerCrop())
                    .into(holder.getBgImg());
        }
        holder.getAddressText().setText(item.getAddress());
        holder.getDateText().setText(Utils.dateFormat(item.getStartDate()) + " - " + Utils.dateFormat(item.getEndDate()));
        holder.getPriceText().setText(Utils.roundAmount(item.getRatePrice()));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public EventsResponse.Event getItem(int position) {
        return items.get(position);
    }

    public void setItems(ArrayList<EventsResponse.Event> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private FrameLayout holder;
        private TextView eventText, addressText, dateText, priceText;
        private ImageView bgImg;
        private View overlayBg;

        public ViewHolder(View itemView) {
            super(itemView);

            holder = itemView.findViewById(R.id.holder);
            eventText = itemView.findViewById(R.id.event_text);
            addressText = itemView.findViewById(R.id.address_text);
            dateText = itemView.findViewById(R.id.date_text);
            priceText = itemView.findViewById(R.id.price_text);
            bgImg = itemView.findViewById(R.id.bg_img);
            overlayBg = itemView.findViewById(R.id.overlay_bg);

        }

        public View getOverlayBg() {
            return overlayBg;
        }

        public TextView getEventText() {
            return eventText;
        }

        public TextView getAddressText() {
            return addressText;
        }

        public TextView getDateText() {
            return dateText;
        }

        public TextView getPriceText() {
            return priceText;
        }

        public ImageView getBgImg() {
            return bgImg;
        }

        public FrameLayout getHolder() {
            return holder;
        }
    }

}