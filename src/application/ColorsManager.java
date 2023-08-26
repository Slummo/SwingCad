package application;

import java.awt.*;
import java.util.HashMap;

public class ColorsManager {
    public static final Color BLACK = Color.BLACK;
    public static final Color WHITE = Color.WHITE;
    public static final Color RED = Color.RED;
    public static final Color GREEN = Color.GREEN;
    public static final Color BLUE = Color.BLUE;

    private static final HashMap<Color, String> colorStrings = createStringsMap();
    private static final HashMap<String, Color> stringColors = createColorsMap();

    private static HashMap<Color, String> createStringsMap() {
        HashMap<Color, String> map = new HashMap<>();
        map.put(BLACK, "Black");
        map.put(WHITE, "White");
        map.put(RED, "Red");
        map.put(GREEN, "Green");
        map.put(BLUE, "Blue");
        return map;
    }

    private static HashMap<String, Color> createColorsMap() {
        HashMap<String, Color> map = new HashMap<>();
        map.put("Black", BLACK);
        map.put("White", WHITE);
        map.put("Red", RED);
        map.put("Green", GREEN);
        map.put("Blue", BLUE);
        return map;
    }

    public static String getStringFromColor(Color color) {
        return colorStrings.getOrDefault(color, "Black");
    }

    public static Color getColorFromString(String color) {
        return stringColors.getOrDefault(color, Color.BLACK);
    }
}
