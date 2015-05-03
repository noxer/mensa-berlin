package net.plusmid.mensaberlin.mensa;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by tim on 27.04.15.
 */
public class Week implements Serializable {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("E, d.MM.yyyy", Locale.GERMANY);

    public Date start;
    public Date end;

    public Day[] days;

    public Week(URL url, int timeout) throws IOException, ParseException {
        Document document = Jsoup.parse(url, 5000);
        parseStartEnd(document.getElementsByClass("mensa_week_head").first());
        parseDays(document.getElementsByClass("mensa_week_table").first());
    }

    public void parseStartEnd(Element table) throws ParseException {
        Elements days = table.getElementsByClass("mensa_week_head_col");
        ParseException lastException = null;

        for (Element d : days) {
            try {
                if (start == null) {
                    start = dateFormat.parse(d.text());
                } else {
                    end = dateFormat.parse(d.text());
                }
            } catch (ParseException e) {
                lastException = e;
            }
        }

        if (start == null) {
            throw lastException == null ? new ParseException("Unable to parse start date!", 100) : lastException;
        }
        if (end == null) {
            end = start;
        }
    }

    public void parseDays(Element table) throws ParseException {

        Elements headers = table.getElementsByClass("mensa_week_head_col");
        Elements starters = table.getElementsByClass("starters");
        Elements salads = table.getElementsByClass("salads");
        Elements soups = table.getElementsByClass("soups");
        Elements special = table.getElementsByClass("special");
        Elements main = table.getElementsByClass("food");
        Elements sides = table.getElementsByClass("side_dishes");
        Elements desserts = table.getElementsByClass("desserts");

        days = new Day[headers.size()];

        for (int i = 0; i < headers.size(); i++) {
            days[i] = Day.parseDay(
                    elementOrNull(headers, i),
                    elementOrNull(starters, i),
                    elementOrNull(salads, i),
                    elementOrNull(soups, i),
                    elementOrNull(special, i),
                    elementOrNull(main, i),
                    elementOrNull(sides, i),
                    elementOrNull(desserts, i)
            );
        }

    }

    private Element elementOrNull(Elements elements, int index) {
        if (index >= elements.size()) {
            return null;
        }
        return elements.get(index);
    }

}
