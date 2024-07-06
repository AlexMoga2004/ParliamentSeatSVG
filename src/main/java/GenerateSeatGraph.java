import lombok.Builder;
import svg.HexColor;
import svg.Party;

import java.util.List;

@Builder
public class GenerateSeatGraph {

    private int imageWidth, imageHeight;
    private HexColor backgroundColor;
    private HexColor foregroundColor;
    private String title;
    private int rows;
    private int cols;
    private double animationTime;

    private final double THETA_MIN = 135;
    private final double THETA_MAX = 360 + 45;
    private final double INNER_RADIUS = 12;
    private final double OUTER_RADIUS = 48;

}
