import lombok.Builder;
import svg.HexColor;
import svg.Party;
import svg.SVGBuilder;
import svg.objects.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    @Builder.Default
    private int maxParties = 5;

    public void generateSVG(String partiesFilepath, String outputFilepath) {
        List<Party> parties = Party.getPartiesFromJSON(partiesFilepath);
        assert parties != null && !parties.isEmpty();

        // Sort by seat number
        parties.sort(Comparator.comparingInt(Party::getNumSeats));
        Collections.reverse(parties);

        // Shorten party list
        int totalSeats = parties.stream().mapToInt(Party::getNumSeats).sum();
        parties = groupSmallParties(parties, totalSeats);

        SVGBuilder svgBuilder = new SVGBuilder(imageWidth, imageHeight);
        svgBuilder.setBackgroundColor(backgroundColor);

        /* Draw main graph (excluding legend)*/

        double centerX = 30;
        double centerY = 50;
        double titleHeight = 6;
        double countHeight = 25;

        // Add title and total seat count
        svgBuilder.add(generateTitle(centerX, titleHeight));
        svgBuilder.add(generateTotalCount(centerX-4, countHeight, totalSeats));

        //TODO: Draw parties (CBA today)

        // Create inner hole
        svgBuilder.add(generateInnerHole(centerX, centerY));

        /* Draw Legend */

        // Create dividing line
        double marginX = centerX + (outerRadius) * ((double) imageHeight / imageWidth);
        double marginGap = 10;
        svgBuilder.add(generateMargin(marginX, marginGap));

        // Create the party colors and names (TODO: and counts)
        generatePartyLegends(parties, marginX).forEach(svgBuilder::add);

        svgBuilder.writeToFile(outputFilepath);
    }

    private List<Party> groupSmallParties(List<Party> parties, int totalSeats) {
        if (parties.size() <= maxParties) return parties;

        parties = parties.subList(0, maxParties);

        int classifiedSeats = parties.stream().mapToInt(Party::getNumSeats).sum();

        Party other = Party.builder()
                .name("Other")
                .numSeats(totalSeats - classifiedSeats)
                .hexColor(new HexColor("#AAAAAA"))
                .build();

        parties.add(other);
        return parties;
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
                .append(" seats")
                .endCount(partyCount)
                .hexColor(foregroundColor)
                .duration(animationTime)
                .build();
    }

    private SvgObject generateInnerHole(double centerX, double centerY) {
        return SvgCircle.builder()
                .xPos(centerX)
                .yPos(centerY)
                .radius(innerRadius / 2)
                .hexColor(foregroundColor)
                .fill(true)
                .relative(true)
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

    private List<SvgObject> generatePartyLegends(List<Party> parties, double margin) {
        int numParties = parties.size();
        double colorXOffset = 5;
        double textXOffset = 5;
        double YOffset = 10;
        double textYOffset = 2;
        double countYOffset = 5;
        double YGap = 90;
        double colorWidth = 4;
        double colorHeight = 2;
        double partyNameSize = 7 * ((double) imageWidth / 300);
        double partyCountSize = 5 * ((double) imageWidth / 300);

        List<SvgObject> partyLegends = new ArrayList<>();

        for (int i = 0; i < numParties; ++i) {
            Party party = parties.get(i);
            double xPos = colorXOffset + margin;
            double yPos = YOffset + YGap * ((double) i / numParties);

            // Create rectangle with party colour
            SvgRect rect = SvgRect.builder()
                    .xPos(xPos)
                    .yPos(yPos)
                    .width(colorWidth)
                    .height(colorHeight)
                    .hexColor(party.getHexColor())
                    .fill(true)
                    .relative(true)
                    .build();

            // Create text with party name
            SvgText text = SvgText.builder()
                    .xPos(xPos + textXOffset)
                    .yPos(yPos + textYOffset)
                    .hexColor(foregroundColor)
                    .text(party.getName())
                    .size(partyNameSize)
                    .relative(true)
                    .build();

            // Create scrolling text with party count
            SvgScrollingText scrollingText = SvgScrollingText.builder()
                    .xPos(xPos + textXOffset)
                    .yPos(yPos + countYOffset)
                    .hexColor(foregroundColor)
                    .endCount(party.getNumSeats())
                    .duration(animationTime)
                    .relative(true)
                    .size(partyCountSize)
                    .build();

            partyLegends.add(rect);
            partyLegends.add(text);
            partyLegends.add(scrollingText);
        }

        return partyLegends;
    }

}
