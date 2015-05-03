package net.plusmid.mensaberlin.mensa;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tim on 27.04.15.
 */
public class Dish implements Serializable {
    public static final Pattern singlePattern = Pattern.compile("^EUR (\\d+\\.\\d+)$");
    public static final Pattern multiPattern = Pattern.compile("^EUR (\\d+\\.\\d+) / (\\d+\\.\\d+) / (\\d+\\.\\d+)$");

    public String name;
    public String desc;
    public Property[] properties;
    public float[] price;

    public static Dish parseDish(Element e) {
        Dish dish = new Dish();

        Element nameElem = e.getElementsByTag("strong").first();
        dish.name = nameElem.text();

        Node descNode = nameElem.nextSibling();
        if (descNode instanceof TextNode && !((TextNode) descNode).isBlank()) {
            dish.desc = ((TextNode) descNode).text().trim();
        } else {
            dish.desc = "";
        }

        dish.parseProperties(e.getElementsByClass("zusatz"));
        dish.parsePrice(e.getElementsByClass("mensa_preise").first());

        return dish;
    }

    private void parsePrice(Element priceElem) {
        String priceString = priceElem.text();

        Matcher matcher = multiPattern.matcher(priceString);
        if (matcher.find()) {
            price = new float[]{Float.parseFloat(matcher.group(1)), Float.parseFloat(matcher.group(2)), Float.parseFloat(matcher.group(3))};
        } else {
            matcher = singlePattern.matcher(priceString);
            if (matcher.find()) {
                float val = Float.parseFloat(matcher.group(1));
                price = new float[]{val, val, val};
            }
        }
    }

    private void parseProperties(Elements propertyElems) {
        properties = new Property[propertyElems.size()];
        for (int i = 0; i < propertyElems.size(); i++) {
            properties[i] = new Property(Integer.parseInt(propertyElems.get(i).text()), propertyElems.get(i).attr("title"));
        }
    }
}
