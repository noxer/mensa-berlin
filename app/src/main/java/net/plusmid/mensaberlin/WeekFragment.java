package net.plusmid.mensaberlin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import net.plusmid.mensaberlin.mensa.Week;

import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeekFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeekFragment extends Fragment {
    private static final String ARG_WEEK = "week";
    private static final String ARG_NEXT_WEEK = "next_week";

    private Week mWeek;
    private Week mOtherWeek;

    private ViewPager pager;

    private boolean isNextWeek = false;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param week Week.
     * @return A new instance of fragment WeekFragment.
     */
    public static WeekFragment newInstance(Week week, Week nextWeek) {
        WeekFragment fragment = new WeekFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_WEEK, week);
        args.putSerializable(ARG_NEXT_WEEK, nextWeek);
        fragment.setArguments(args);
        return fragment;
    }

    public WeekFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (getArguments() != null) {
            mWeek = (Week) getArguments().getSerializable(ARG_WEEK);
            mOtherWeek = (Week) getArguments().getSerializable(ARG_NEXT_WEEK);
        }

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_week, container, false);

        pager = (ViewPager) rootView.findViewById(R.id.pager);
        pager.setAdapter(new MyFragmentStatePagerAdapter(getFragmentManager(), mWeek));

        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);

        itemWeek = menu.getItem(0);
        itemNextWeek = menu.getItem(1);

        itemWeek.setVisible(!isNextWeek);
        itemNextWeek.setVisible(isNextWeek);
    }

    private MenuItem itemWeek;
    private MenuItem itemNextWeek;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Week buf = mWeek;
        mWeek = mOtherWeek;
        mOtherWeek = buf;

        isNextWeek = !isNextWeek;

        itemWeek.setVisible(!itemWeek.isVisible());
        itemNextWeek.setVisible(!itemNextWeek.isVisible());

        pager.setAdapter(new MyFragmentStatePagerAdapter(getFragmentManager(), mWeek));
        return true;
    }

    private class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
        private Week mWeek;

        public MyFragmentStatePagerAdapter(FragmentManager fragmentManager, Week week) {
            super(fragmentManager);
            mWeek = week;
        }

        @Override
        public Fragment getItem(int position) {
            return DayFragment.newInstance(mWeek.days[position]);
        }

        @Override
        public int getCount() {
            return mWeek.days.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Date date = mWeek.days[position].date;
            if (date == null) {
                return getString(R.string.error_loading_day);
            }
            Date today = Calendar.getInstance().getTime();
            if (today.getYear() == date.getYear() && today.getMonth() == date.getMonth() && today.getDay() == date.getDay()) {
                return DateFormat.format("E, dd.MM.yyyy", date) + " " + getString(R.string.today);
            }
            return DateFormat.format("E, dd.MM.yyyy", date);
        }
    }
}
