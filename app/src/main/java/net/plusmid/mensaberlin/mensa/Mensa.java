package net.plusmid.mensaberlin.mensa;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tim on 27.04.15.
 */
public class Mensa implements Serializable {
    public Week currentWeek;
    public Week nextWeek;
    public URL currentUrl;
    public URL nextUrl;

    public Mensa(URL url, int timeout) throws IOException {
        Document document = Jsoup.parse(url, timeout);
        Element weekPlan = document.getElementById("week_plan");
        Elements weeks = weekPlan.getElementsByTag("a");

        currentUrl = new URL("http://www.studentenwerk-berlin.de" + weeks.first().attr("href"));
        nextUrl = new URL("http://www.studentenwerk-berlin.de" + weeks.last().attr("href"));
    }

    public Week getCurrentWeek(int timeout) throws IOException, ParseException {
        if (currentWeek != null) {
            return currentWeek;
        }

        if (currentUrl != null) {
            currentWeek = new Week(currentUrl, timeout);
            return currentWeek;
        }

        return null;
    }

    public Week getNextWeek(int timeout) throws IOException, ParseException {
        if (nextWeek != null) {
            return nextWeek;
        }

        if (nextUrl != null) {
            nextWeek = new Week(nextUrl, timeout);
            return nextWeek;
        }

        return null;
    }

    public Week getWeekWithDate(Date date, int timeout) throws IOException, ParseException {
        if (date == null) {
            date = Calendar.getInstance().getTime();
        }

        Week week = getCurrentWeek(timeout);
        if ((date.compareTo(week.start) >= 0) && (date.compareTo(week.end) <= 0)) {
            return week;
        }

        week = getNextWeek(timeout);
        if ((date.compareTo(week.start) >= 0) && (date.compareTo(week.end) <= 0)) {
            return week;
        }

        return null;
    }

}
