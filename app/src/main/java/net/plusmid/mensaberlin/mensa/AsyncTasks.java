package net.plusmid.mensaberlin.mensa;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;

/**
 * Created by tim on 28.04.15.
 */
public class AsyncTasks {

    public static class FetchMensa extends AsyncTask<URL, Object, Mensa> {
        @Override
        protected Mensa doInBackground(URL... params) {
            if (params.length == 0)
                return null;

            try {
                Mensa mensa = Parser.fetchMensa(params[0]);
                mensa.getCurrentWeek(10000);
                mensa.getNextWeek(10000);
                return mensa;
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public static class FetchCurrentWeek extends AsyncTask<Mensa, Object, Week> {
        @Override
        protected Week doInBackground(Mensa... params) {
            if (params.length == 0)
                return null;

            try {
                return params[0].getCurrentWeek(5000);
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}
