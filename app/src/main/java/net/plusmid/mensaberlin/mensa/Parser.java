package net.plusmid.mensaberlin.mensa;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;

/**
 * Created by tim on 27.04.15.
 */
public class Parser {

    public static Mensa fetchMensa(URL url) throws IOException, ParseException {
        return fetchMensa(url, 5000);
    }

    public static Mensa fetchMensa(URL url, int timeout) throws IOException, ParseException {

        return new Mensa(url, timeout);

    }

}
