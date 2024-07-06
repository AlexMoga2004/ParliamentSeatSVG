import lombok.Builder;
import svg.HexColor;
import svg.Party;
import svg.SVGBuilder;
import svg.objects.SvgLine;
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

    @Builder.Default
    private double animationTime = 3;

    @Builder.Default
    private double thetaMin = 135;

    @Builder.Default
    private double thetaMax = 360 + 45;

    @Builder.Default
    private double innerRadius = 12;

    @Builder.Default
    private double outerRadius = 48;

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


        // Create inner hole
        //TODO

        /* Draw Legend */

        // Create dividing line
        double marginX = centerX + (outerRadius) * ((double) imageHeight / imageWidth);
        double marginGap = 10;
        svgBuilder.add(generateMargin(marginX, marginGap));


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

    private SvgObject generateMargin(double xPos, double gap) {
        double lineThickness = 0.5;

        return SvgLine.builder()
                .x1(xPos)
                .x2(xPos)
                .y1(gap)
                .y2(100 - gap)
                .relative(true)
                .width(lineThickness)
                .build();
    }

}
