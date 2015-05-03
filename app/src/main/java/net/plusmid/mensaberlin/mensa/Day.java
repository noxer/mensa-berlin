package net.plusmid.mensaberlin.mensa;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by tim on 27.04.15.
 */
public class Day implements Serializable {

    public Date date;
    public Dish[] starters;
    public Dish[] salads;
    public Dish[] soups;
    public Dish[] specials;
    public Dish[] mains;
    public Dish[] sides;
    public Dish[] desserts;

    public Day(Date date, Dish[] starters, Dish[] salads, Dish[] soups, Dish[] specials, Dish[] mains, Dish[] sides, Dish[] desserts) {
        this.date = date;
        this.starters = starters;
        this.salads = salads;
        this.soups = soups;
        this.specials = specials;
        this.mains = mains;
        this.sides = sides;
        this.desserts = desserts;
    }

    public static Day parseDay(
            Element date,
            Element starters,
            Element salads,
            Element soups,
            Element specials,
            Element mains,
            Element sides,
            Element desserts) throws ParseException {

        int i;
        Date mDate = null;
        try {
            mDate = Week.dateFormat.parse(date.text());
        } catch (ParseException e) {
        }

        Elements elems;
        Dish[] mStarters = null, mSalads = null, mSoups = null, mSpecials = null, mMains = null, mSides = null, mDesserts = null;

        // Parse starters
        if (starters != null) {
            elems = starters.getElementsByClass("mensa_speise");
            mStarters = new Dish[elems.size()];
            for (i = 0; i < elems.size(); i++) {
                mStarters[i] = Dish.parseDish(elems.get(i));
            }
        }

        // Parse salads
        if (salads != null) {
            elems = salads.getElementsByClass("mensa_speise");
            mSalads = new Dish[elems.size()];
            for (i = 0; i < elems.size(); i++) {
                mSalads[i] = Dish.parseDish(elems.get(i));
            }
        }

        // Parse soups
        if (soups != null) {
            elems = soups.getElementsByClass("mensa_speise");
            mSoups = new Dish[elems.size()];
            for (i = 0; i < elems.size(); i++) {
                mSoups[i] = Dish.parseDish(elems.get(i));
            }
        }

        // Parse specials
        if (specials != null) {
            elems = specials.getElementsByClass("mensa_speise");
            mSpecials = new Dish[elems.size()];
            for (i = 0; i < elems.size(); i++) {
                mSpecials[i] = Dish.parseDish(elems.get(i));
            }
        }

        // Parse mains
        if (mains != null) {
            elems = mains.getElementsByClass("mensa_speise");
            mMains = new Dish[elems.size()];
            for (i = 0; i < elems.size(); i++) {
                mMains[i] = Dish.parseDish(elems.get(i));
            }
        }

        // Parse sides
        if (sides != null) {
            elems = sides.getElementsByClass("mensa_speise");
            mSides = new Dish[elems.size()];
            for (i = 0; i < elems.size(); i++) {
                mSides[i] = Dish.parseDish(elems.get(i));
            }
        }

        // Parse dessert
        if (desserts != null) {
            elems = desserts.getElementsByClass("mensa_speise");
            mDesserts = new Dish[elems.size()];
            for (i = 0; i < elems.size(); i++) {
                mDesserts[i] = Dish.parseDish(elems.get(i));
            }
        }

        return new Day(mDate, mStarters, mSalads, mSoups, mSpecials, mMains, mSides, mDesserts);
    }
}
