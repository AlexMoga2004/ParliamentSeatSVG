package svg.objects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import svg.HexColor;

@Getter
@Setter
@Builder
public class SvgCircle extends SvgObject {
    private double xPos;
    private double yPos;
    private double radius;
    private HexColor hexColor;
    private boolean relative;
    private double thickness;
    private boolean fill;

    @Override
    public String toString() {
        String relString = relative ? "%" : "";

        return String.format("  <circle cx=\"%s\" cy=\"%s\" r=\"%s\" ",
                xPos + relString,
                yPos + relString,
                radius + relString) +
                String.format("fill=\"%s\" ", hexColor) +
                String.format("stroke-width=\"%f\" ", thickness) +
                "/>\n";
    }
}
