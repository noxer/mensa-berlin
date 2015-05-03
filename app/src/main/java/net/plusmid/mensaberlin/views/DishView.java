package net.plusmid.mensaberlin.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.plusmid.mensaberlin.R;
import net.plusmid.mensaberlin.mensa.Dish;

public class DishView extends GridLayout {
    private Dish mDish;
    private View mRootView;

    public DishView(Context context) {
        super(context);
        init(context);
    }

    public DishView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DishView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mRootView = inflate(context, R.layout.view_dish, this);
        updateDish(mRootView, mDish);
    }

    public void setDish(Dish dish) {
        mDish = dish;
        updateDish(mRootView, dish);
    }

    private void updateDish(View rootView, Dish dish) {
        if (rootView == null || dish == null)
            return;

        TextView textName = (TextView) rootView.findViewById(R.id.text_name);
        TextView textDesc = (TextView) rootView.findViewById(R.id.text_desc);
        TextView textPrice = (TextView) rootView.findViewById(R.id.text_price);
//        LinearLayout layoutProps = (LinearLayout) rootView.findViewById(R.id.layout_props);

        textName.setText(dish.name);

        if (dish.desc == null || dish.desc.equals("")) {
            textDesc.setVisibility(View.GONE);
        } else {
            textDesc.setText(dish.desc);
        }
        textPrice.setText(String.format("%2.2f €", dish.price[0]));
    }

}
