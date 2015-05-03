package net.plusmid.mensaberlin;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import net.plusmid.mensaberlin.mensa.AsyncTasks;
import net.plusmid.mensaberlin.mensa.Mensa;

import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoadMensaFragment.OnMensaLoadedListener} interface
 * to handle interaction events.
 * Use the {@link LoadMensaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoadMensaFragment extends Fragment {
    private static final String ARG_URL = "url";

    private URL mUrl;
    private Mensa mMensa;

    private OnMensaLoadedListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param url The url to load.
     * @return A new instance of fragment LoadingFragment.
     */
    public static LoadMensaFragment newInstance(URL url) {
        LoadMensaFragment fragment = new LoadMensaFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    public LoadMensaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = (URL) getArguments().getSerializable(ARG_URL);
        }

        // This fragment should survive a config change (eg. screen rotation)
        setRetainInstance(true);

        if (mMensa != null) {
            mListener.onMensaLoaded(mMensa);
        } else {
            new AsyncTasks.FetchMensa() {
                @Override
                protected void onPreExecute() {
                }

                @Override
                protected void onPostExecute(Mensa mensa) {
                    if (mListener != null) {
                        mMensa = mensa;
                        mListener.onMensaLoaded(mensa);
                    }
                }

                @Override
                protected void onCancelled(Mensa mensa) {

                }
            }.execute(mUrl);
        }
    }

    private void onMensaLoaded(Mensa mensa) {
        if (mListener != null) {
            mListener.onMensaLoaded(mensa);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnMensaLoadedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMensaLoadedListener");
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
    public interface OnMensaLoadedListener {
        public void onMensaLoaded(Mensa mensa);
    }

}
