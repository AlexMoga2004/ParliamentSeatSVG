package svg;

import java.util.regex.Pattern;

public class Constants {
    /* Raw SVG strings used to build SVG file*/

    public static String createInitialSVG(double width, double height) {
        final String initialSvg = """
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<svg xmlns:svg="http://www.w3.org/2000/svg"
xmlns="http://www.w3.org/2000/svg" version="1.1"
width="WIDTH" height="HEIGHT">
""";

        return initialSvg
                .replaceAll("WIDTH", String.valueOf(width))
                .replaceAll("HEIGHT", String.valueOf(height));
    }

    static final String finalSVG = "</svg>";

    public static String createIDFade(String ID, String beginID,
                                      double startOpacity, double endOpacity, double duration) {
        String ID_fade = "<animate id=\"ID\" begin=\"BEGIN\" attributeName=\"opacity\" from=\"FROM\" to=\"TO\" dur=\"DURs\" fill=\"freeze\" />";

        return ID_fade
                .replaceAll("ID", ID)
                .replaceAll("BEGIN", beginID)
                .replaceAll("FROM", String.valueOf(startOpacity))
                .replaceAll("TO", String.valueOf(endOpacity))
                .replaceAll("DUR", String.valueOf(duration));
    }

    public static String createIDFade(String ID, double startOpacity, double endOpacity, double duration) {
        String ID_fade = "<animate id=\"ID\" attributeName=\"opacity\" from=\"FROM\" to=\"TO\" dur=\"DURs\" fill=\"freeze\" />";

        return ID_fade
                .replaceAll("ID", ID)
                .replaceAll("FROM", String.valueOf(startOpacity))
                .replaceAll("TO", String.valueOf(endOpacity))
                .replaceAll("DUR", String.valueOf(duration));
    }


    public static String createFade(double startOpacity, double endOpacity, double duration) {
        String animation
                = "    <animate attributeName=\"opacity\" from=\"FROM\" to=\"TO\" dur=\"DURs\" fill=\"freeze\"/>\n";

        return animation
                .replaceAll("FROM", String.valueOf(startOpacity))
                .replaceAll("TO", String.valueOf(endOpacity))
                .replaceAll("DUR", String.valueOf(duration));
    }

    public static String createInstantTransition(double startTime, double endTime) {
        String animation = """
    <animate attributeName="opacity" from="0" to="1" begin="BEG1s" dur="0.01s" fill="freeze"/>
    <animate attributeName="opacity" from="1" to="0" begin="BEG2s" dur="0.01s" fill="freeze"/>
""";

        return animation
                .replaceAll("BEG1", String.valueOf(startTime))
                .replaceAll("BEG2", String.valueOf(endTime));
    }

    public static String createAppearance(double time) {
        String animation = "    <animate attributeName=\"opacity\" from=\"0\" to=\"1\" begin=\"BEG1s\" dur=\"0.01s\" fill=\"freeze\"\n/>";

        return animation
                .replaceAll("BEG1", String.valueOf(time));
    }


    /* Regex pattern matcher to check validity of hex colors */
    static final Pattern hexColorRegex = Pattern.compile("#[0-9a-fA-F]{6}");

    /* Common status messages */
    public static final String	HEX_CODE_ERROR = "Invalid hex code";

}
