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
    private boolean relative;
    private boolean centered;

    @Builder.Default
    private HexColor hexColor = new HexColor("#000000");

    @Builder.Default
    private double opacity = 1;

    private final List<String> animations = new ArrayList<>();

    public void addFade(double startOpacity, double endOpacity, double durationSeconds) {
        animations.add(Constants.createFade(startOpacity, endOpacity, durationSeconds));
    }

    public void setDuration(double startTime, double endTime) {
        animations.add(Constants.createInstantTransition(startTime, endTime));
    }

    public void setAppearance(double time) {
        animations.add(Constants.createAppearance(time));
    }

    @Override
    public String toString() {
        String relString = relative ? "%" : "";
        String anchorString = centered ? "middle" : "start";

        StringBuilder svg = new StringBuilder(
                String.format("  <text x=\"%s\" y=\"%s\" style=\"font-size:%spx;font-weight:bold;text-align:center;" +
                        "text-anchor:%s;font-family:sans-serif;\" opacity=\"%s\">%s",
                        xPos + relString,
                        yPos + relString,
                        size,
                        anchorString,
                        opacity,
                        text
        ));

        if (!animations.isEmpty()) svg.append("\n");
        animations.forEach(svg::append);

        svg.append("  </text>\n");
        return svg.toString();
    }
}
