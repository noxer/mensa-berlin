package net.plusmid.mensaberlin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import net.plusmid.mensaberlin.mensa.AsyncTasks;
import net.plusmid.mensaberlin.mensa.Mensa;
import net.plusmid.mensaberlin.mensa.Week;

import java.net.URL;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link net.plusmid.mensaberlin.LoadWeekFragment.OnWeekLoadedListener} interface
 * to handle interaction events.
 * Use the {@link net.plusmid.mensaberlin.LoadWeekFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoadWeekFragment extends Fragment {
    private static final String ARG_MENSA = "mensa";

    private Mensa mMensa;
    private Week mWeek;

    private OnWeekLoadedListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param url The url to load.
     * @return A new instance of fragment LoadingFragment.
     */
    public static LoadWeekFragment newInstance(URL url) {
        LoadWeekFragment fragment = new LoadWeekFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MENSA, url);
        fragment.setArguments(args);
        return fragment;
    }

    public LoadWeekFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMensa = (Mensa) getArguments().getSerializable(ARG_MENSA);
        }

        // This fragment should survive a config change (eg. screen rotation)
        setRetainInstance(true);
    }

    private void onWeekLoaded(Week week) {
        if (mListener != null) {
            mListener.onWeekLoaded(week);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnWeekLoadedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnWeekLoadedListener");
        }

        if (mWeek != null) {
            mListener.onWeekLoaded(mWeek);
        } else {
            new AsyncTasks.FetchCurrentWeek() {
                private ProgressDialog progressDialog = new ProgressDialog(getActivity());

                @Override
                protected void onPreExecute() {
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setMessage(getString(R.string.loading_week));
                    progressDialog.show();
                }

                @Override
                protected void onPostExecute(Week week) {
                    progressDialog.hide();

                    if (mListener != null) {
                        mWeek = week;
                        mListener.onWeekLoaded(week);
                    }
                }

                @Override
                protected void onCancelled(Week week) {
                    progressDialog.hide();
                }
            }.execute(mMensa);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnWeekLoadedListener {
        public void onWeekLoaded(Week week);
    }

}
