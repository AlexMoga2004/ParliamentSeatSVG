package svg.objects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import svg.Constants;
import svg.HexColor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class SvgSector extends SvgObject{
    private double xPos;
    private double yPos;
    private double radius;
    private double startAngleDeg;
    private double endAngleDeg;
    private HexColor hexColor;
    private double thickness;
    private boolean fill;
    private List<String> animations;

    public void addIDFade(String ID, double startOpacity, double endOpacity, double duration) {
        if (animations == null) animations = new ArrayList<>();

        animations.add(Constants.createIDFade(ID, startOpacity, endOpacity, duration));
    }

    public void addIDFade(String ID, String beginID,
                          double startOpacity, double endOpacity, double duration) {
        if (animations == null) animations = new ArrayList<>();

        animations.add(Constants.createIDFade(ID, beginID, startOpacity, endOpacity, duration));
    }

    @Override
    public String toString() {
        double startAngleRad = Math.toRadians(this.startAngleDeg);
        double endAngleRad = Math.toRadians(this.endAngleDeg);

        double x1 = xPos + radius * Math.cos(startAngleRad);
        double y1 = yPos - radius * Math.sin(startAngleRad);
        double x2 = xPos + radius * Math.cos(endAngleRad);
        double y2 = yPos - radius * Math.sin(endAngleRad);

        // SVG flags to ensure sector faces correct direction
        int largeArcFlag = (Math.abs(startAngleDeg - endAngleDeg) > 180) ? 1 : 0;
        int sweepFlag = (startAngleDeg > endAngleDeg) ? 1 : 0;

        String fillOrTrace = fill ? "fill" : "stroke";

        return "  <path " + fillOrTrace + "=\"" + hexColor + "\" stroke-width=\"" + thickness + "\" d=\"M " + xPos + ","
                + yPos + " L " + x1 + "," + y1 + " A " + radius +  "," + radius  + " 0 " + largeArcFlag + ","
                + sweepFlag + " " + x2 + "," + y2  + " Z\"/>\n";
    }
}
