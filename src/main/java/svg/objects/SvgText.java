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
public class SvgText extends SvgObject{

    private double xPos;
    private double yPos;
    private double size;
    private String text;
    private HexColor hexColor;
    private double opacity;
    private boolean relative;
    private final List<String> animations = new ArrayList<>();

    public void addFade(double startOpacity, double endOpacity, double durationSeconds) {
        animations.add(Constants.createFade(startOpacity, endOpacity, durationSeconds));
    }

    public void setDuration(double startTime, double endTime) {
        animations.add(Constants.createInstantTransition(startTime, endTime));
    }

    @Override
    public String toString() {
        String relString = relative ? "%" : "";

        StringBuilder svg = new StringBuilder(
                String.format("  <text x=\"%s\" y=\"%s\" style=\"font-size:%spx;font-weight:bold;text-align:center;" +
                        "text-anchor:start;font-family:sans-serif;\" opacity=\"%s\">%s",
                        xPos + relString,
                        yPos + relString,
                        size,
                        opacity,
                        text
        ));

        if (!animations.isEmpty()) svg.append("\n");
        animations.forEach(svg::append);

        svg.append("  </text>\n");
        return svg.toString();
    }
}
