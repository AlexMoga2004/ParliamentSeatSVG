package svg.objects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import svg.HexColor;

@Getter
@Setter
@Builder
public class SvgRect extends SvgObject {
    private double xPos;
    private double yPos;
    private double width;
    private double height;
    private HexColor hexColor;
    private boolean relative;
    private double thickness;
    private boolean fill;

    @Override
    public String toString() {
        String relString = relative ? "%" : "";

        return String.format("  <rect x=\"%s\" y=\"%s\" width=\"%s\" height=\"%s\" ",
                xPos + relString,
                yPos + relString,
                width + relString,
                height + relString) +
                String.format("fill=\"%s\" ", hexColor) +
                String.format("stroke-width=\"%f\" ", thickness) +
                "/>\n";
    }
}
