import lombok.Builder;
import svg.HexColor;
import svg.Party;
import svg.SVGBuilder;
import svg.objects.SvgObject;
import svg.objects.SvgScrollingText;
import svg.objects.SvgText;

import java.util.List;

@Builder
public class GraphBuilder {

    private int imageWidth;
    private int imageHeight;
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

    public void generateSVG(String partiesFilepath, String outputFilepath) {
        List<Party> parties = Party.getPartiesFromJSON(partiesFilepath);
        assert parties != null && !parties.isEmpty();

        SVGBuilder svgBuilder = new SVGBuilder(imageWidth, imageHeight);
        svgBuilder.setBackgroundColor(backgroundColor);

        int totalSeats = parties.stream().mapToInt(Party::getNumSeats).sum();

        /* Draw main graph (excluding legend)*/

        double centerX = 30;
        double centerY = 50;
        double titleHeight = 6;
        double countHeight = 25;

        // Add title and total seat count
        svgBuilder.add(generateTitle(centerX, titleHeight));
        svgBuilder.add(generateTotalCount(centerX, countHeight, totalSeats));


        /* Draw Legend */

        svgBuilder.writeToFile(outputFilepath);
    }

    private SvgObject generateTitle(double xPos, double yPos) {
        double titleSize = 8 * ((double) imageHeight / 150);

        return SvgText.builder()
                .xPos(xPos)
                .yPos(yPos)
                .text(title)
                .hexColor(foregroundColor)
                .centered(true)
                .size(titleSize)
                .relative(true)
                .opacity(1)
                .build();
    }

    private SvgObject generateTotalCount(double xPos, double yPos, int partyCount) {
        double textSize = 6 * ((double) imageWidth / 300);

        return SvgScrollingText.builder()
                .xPos(xPos)
                .yPos(yPos)
                .size(textSize)
                .relative(true)
                .hexColor(foregroundColor)
                .startCount(0)
                .endCount(partyCount)
                .hexColor(foregroundColor)
                .duration(animationTime)
                .build();
    }

}
