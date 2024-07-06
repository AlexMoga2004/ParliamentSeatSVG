package svg;

import java.util.regex.Pattern;

public class Constants {
    /* Raw SVG strings used to build SVG file*/
    static final String initialSvg = """
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<svg xmlns:svg="http://www.w3.org/2000/svg"
xmlns="http://www.w3.org/2000/svg" version="1.1"
width="WIDTH" height="HEIGHT">
""";

    static final String finalSVG = "</svg>";

    static final String GENERIC_FADE = """
<animate ID BEGIN attributeName="opacity" from="FROM" to="TO" dur="DURs" fill="freeze" />""";

    static final String INSTANT_TRANSITION = """
<animate attributeName="opacity" from="0" to="1" begin="BEG1s" dur="0.01s" fill="freeze"/>
<animate attributeName="opacity" from="1" to="0" begin="BEG2s" dur="0.01s" fill="freeze"/>
""";


    /* Regex pattern matcher to check validity of hex colors */
    static final Pattern hexColorRegex = Pattern.compile("#[0-9a-fA-F]{6}");

    /* Common status messages */
    public static final String	HEX_CODE_ERROR = "Invalid hex code";

}
