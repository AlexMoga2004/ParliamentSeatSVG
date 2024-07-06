package svg.objects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import svg.HexColor;

@Getter
@Setter
@Builder
public class SvgLine extends SvgObject{
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private double width;
    private HexColor hexColor;
    private boolean relative;

    @Override
    public String toString() {
        if (width <= 0) width = 1;

        String relString = relative ? "%" : "";
        return String.format("  <line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" stroke=\"%s\" stroke-width=\"%s\"/>\n",
                x1 + relString,
                y1 + relString,
                x2 + relString,
                y2 + relString,
                hexColor,
                width
        );
    }
}
