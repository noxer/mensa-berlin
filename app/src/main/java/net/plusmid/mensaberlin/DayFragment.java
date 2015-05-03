package net.plusmid.mensaberlin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.plusmid.mensaberlin.mensa.Day;
import net.plusmid.mensaberlin.mensa.Dish;
import net.plusmid.mensaberlin.views.DishView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DayFragment extends Fragment {
    private static final String ARG_DAY = "day";

    private LinearLayout layoutStarters;
    private LinearLayout layoutSalads;
    private LinearLayout layoutSoups;
    private LinearLayout layoutSpecials;
    private LinearLayout layoutMains;
    private LinearLayout layoutSides;
    private LinearLayout layoutDesserts;

    private Day mDay;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param day The day to present.
     * @return A new instance of fragment DayFragment.
     */
    public static DayFragment newInstance(Day day) {
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DAY, day);
        fragment.setArguments(args);
        return fragment;
    }

    public DayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDay = (Day) getArguments().getSerializable(ARG_DAY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_day, container, false);

        layoutStarters = (LinearLayout) rootView.findViewById(R.id.layout_starters);
        layoutSalads = (LinearLayout) rootView.findViewById(R.id.layout_salads);
        layoutSoups = (LinearLayout) rootView.findViewById(R.id.layout_soups);
        layoutSpecials = (LinearLayout) rootView.findViewById(R.id.layout_specials);
        layoutMains = (LinearLayout) rootView.findViewById(R.id.layout_mains);
        layoutSides = (LinearLayout) rootView.findViewById(R.id.layout_sides);
        layoutDesserts = (LinearLayout) rootView.findViewById(R.id.layout_desserts);

        if (mDay != null && mDay.date != null) {
            insertDay(mDay);
        }

        return rootView;
    }

    private void insertDay(Day day) {
        if (day.starters != null) {
            layoutStarters.removeAllViewsInLayout();
            for (Dish d : day.starters) {
                insertDishes(layoutStarters, d);
            }
        }

        if (day.salads != null) {
            layoutSalads.removeAllViewsInLayout();
            for (Dish d : day.salads) {
                insertDishes(layoutSalads, d);
            }
        }

        if (day.soups != null) {
            layoutSoups.removeAllViewsInLayout();
            for (Dish d : day.soups) {
                insertDishes(layoutSoups, d);
            }
        }

        if (day.specials != null) {
            layoutSpecials.removeAllViewsInLayout();
            for (Dish d : day.specials) {
                insertDishes(layoutSpecials, d);
            }
        }

        if (day.mains != null) {
            layoutMains.removeAllViewsInLayout();
            for (Dish d : day.mains) {
                insertDishes(layoutMains, d);
            }
        }

        if (day.sides != null) {
            layoutSides.removeAllViewsInLayout();
            for (Dish d : day.sides) {
                insertDishes(layoutSides, d);
            }
        }

        if (day.desserts != null) {
            layoutDesserts.removeAllViewsInLayout();
            for (Dish d : day.desserts) {
                insertDishes(layoutDesserts, d);
            }
        }
    }

    private void insertDishes(LinearLayout layout, Dish dish) {
        DishView dishView = new DishView(layout.getContext());
        dishView.setDish(dish);
        layout.addView(dishView);
    }

}
