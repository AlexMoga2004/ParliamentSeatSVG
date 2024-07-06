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
    private boolean relative;

    @Builder.Default
    private HexColor hexColor = new HexColor("#000000");
    @Builder.Default
    private double width = 1;

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
                width + relString
        );
    }
}
