import lombok.Builder;
import svg.HexColor;

@Builder
public class GenerateSeatGraph {

    private int IMAGE_WIDTH, IMAGE_HEIGHT;
    private HexColor BACKGROUND_COLOR;
    private HexColor FOREGROUND_COLOR;
    private String TITLE;
    private int ROWS;
    private int COLS;
    private final double THETA_MIN = 135;
    private final double THETA_MAX = 360 + 45;
    private final double INNER_RADIUS = 12;
    private final double OUTER_RADIUS = 48;
    private final double ANIMATION_TIME = 3;


}
