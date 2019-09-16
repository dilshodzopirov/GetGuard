package com.getguard.client.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.getguard.client.R;

public class OnBoardingViewPagerAdapter extends PagerAdapter {

    private Context mContext;

    private int[] images = new int[]{
            R.drawable.onboarding_1,
            R.drawable.onboarding_2,
            R.drawable.onboarding_3,
            R.drawable.onboarding_4,
            R.drawable.onboarding_5,
            0};

    private String[] texts = new String[]{
            "Выберите тип охраны",
            "Укажите требования и цену",
            "Получите множество предложений",
            "Выберите исполнителя",
            "Оплатите в одно касание с помощью безопасной сделки",
            ""};

    public OnBoardingViewPagerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.onboarding_page, collection, false);
        ImageView imageView = layout.findViewById(R.id.image);
        TextView textView = layout.findViewById(R.id.text);
        if (position == 5) {
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        } else {
            imageView.setImageResource(images[position]);
            textView.setText(texts[position]);
        }
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
