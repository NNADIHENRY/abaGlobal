package com.example.henry.abaglobal;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by ighub on 12/3/18.
 */

public class CustomAdapter extends PagerAdapter {
    private int[] images = {R.drawable.image,R.drawable.image2,R.drawable.image3,
         R.drawable.image4,R.drawable.image5,R.drawable.image6,R.drawable.image7,
            R.drawable.image8,R.drawable.image9,R.drawable.image10,R.drawable.image11,};

    private Context ctx;
    private LayoutInflater inflater;

    public CustomAdapter(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view ==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.flipper_items,container,false);
        ImageView img =(ImageView)v.findViewById(R.id.imageView);
//        TextView tv  = (TextView)v.findViewById(R.id.textView);
        img.setImageResource(images[position]);
//        tv.setText("Image :"+position);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        container.refreshDrawableState();
    }
}
