package net.plusmid.mensaberlin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import net.plusmid.mensaberlin.mensa.Mensa;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

public class MainActivity extends ActionBarActivity implements LoadMensaFragment.OnMensaLoadedListener {
    private static final String FRAGMENT_TAG = "mensa_fragment";

    private LoadMensaFragment loadMensaFragment;
    private boolean isMensaLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        URL url = null;
        try {
            url = new URL("http://www.studentenwerk-berlin.de/mensen/speiseplan/fu2/index.html");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        }

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (fragment instanceof LoadMensaFragment) {
            loadMensaFragment = (LoadMensaFragment) fragment;
        } else {
            loadMensaFragment = LoadMensaFragment.newInstance(url);
            getSupportFragmentManager().beginTransaction()
                    .add(loadMensaFragment, FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    public void onMensaLoaded(Mensa mensa) {
        if (!isMensaLoaded && mensa != null) {
            isMensaLoaded = true;

            try {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, WeekFragment.newInstance(mensa.getCurrentWeek(0), mensa.getNextWeek(0)))
                        .commit();
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
        if (mensa == null) {
            Toast.makeText(this, R.string.error_loading_mensa, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
