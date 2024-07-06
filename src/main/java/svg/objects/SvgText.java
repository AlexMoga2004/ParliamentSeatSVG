package svg.objects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import svg.HexColor;

@Getter
@Setter
@Builder
public class SvgText extends SvgObject{

    private double xPos;
    private double yPos;
    private double size;
    private String text;
    private HexColor hexColor;
    private double opacity;
    private boolean relative;

    @Override
    public String toString() {
        if (opacity <= 0) opacity = 1;
        String relString = relative ? "%" : "";

        return String.format("<text x=\"%s\" y=\"%s\" style=\"font-size:%spx;font-weight:bold;text-align:center;" +
                "text-anchor:start;font-family:sans-serif;\" opacity=\"%s\">%s</text>",
                xPos + relString,
                yPos + relString,
                size,
                opacity,
                text
        );
    }
}
