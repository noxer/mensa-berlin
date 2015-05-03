package net.plusmid.mensaberlin.mensa;

import java.io.Serializable;

/**
 * Created by tim on 27.04.15.
 */
public class Property implements Serializable {
    public int id;
    public String name;

    public static Property parseProperty(String entry) {
        String[] t = entry.split("  ");
        return new Property(Integer.parseInt(t[0]), t[1]);
    }

    public Property(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
