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

        // Draw the party sectors
        generatePartySectors(parties, centerX, centerY).forEach(svgBuilder::add);

        // Create inner hole
        svgBuilder.add(generateInnerHole(centerX, centerY));

        // Split the sectors
        generatePartyGaps(centerX, centerY).forEach(svgBuilder::add);

        // Cover the sectors, and animate reveal using sweeping sector
        svgBuilder.add(generateSweepingSector(centerX, centerY));

        /* Draw Legend */

        // Create dividing line
        double marginX = centerX + (outerRadius) * ((double) imageHeight / imageWidth) + 5;
        double marginGap = 10;
        svgBuilder.add(generateMargin(marginX, marginGap));

        // Create the party colors and names
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

    /*
        Calculate how many visible slots each party gets based on the proportion of seats
        Note: Number of slots = rows * cols
     */
    private int[] allocateSlots(List<Party> parties) {
        int totalSeats = parties.stream().mapToInt(Party::getNumSeats).sum();

        return parties.stream()
                .mapToInt(party -> (int) (0.5 + (double) party.getNumSeats() *
                        (rows * cols) / totalSeats))
                .toArray();
    }

    private List<SvgObject> generatePartySectors(List<Party> parties, double centerX, double centerY) {
        List<SvgObject> partySectors = new ArrayList<>();
        int[] slotsPerParty = allocateSlots(parties);
        int currentPartyIndex = 0;
        int currentRow = 0;
        int currentCol = 0;

        for (int i = 0; i < rows * cols; ++i) {
            if (slotsPerParty[currentPartyIndex] == 0) ++currentPartyIndex;

            double radius = innerRadius + ((outerRadius - innerRadius) * ((double) (rows - currentRow) / rows));
            double startAngle = thetaMin + (thetaMax - thetaMin) * ((double) currentCol / cols);
            double endAngle = thetaMin + (thetaMax - thetaMin) * ((double) (currentCol + 1) / cols);

            SvgSector sector = SvgSector.builder()
                    .xPos(centerX * imageWidth / 100d) // Sectors do not support relative, so adjust manually
                    .yPos(centerY * imageHeight / 100d)
                    .radius(radius * imageHeight / 100d)
                    .startAngleDeg(startAngle)
                    .endAngleDeg(endAngle)
                    .hexColor(parties.get(currentPartyIndex).getHexColor())
                    .fill(true)
                    .build();

            partySectors.add(sector);

            ++currentRow;
            --slotsPerParty[currentPartyIndex];

            // Wrap around to next column if entire column filled out
            if(currentRow == rows) {
                currentRow = 0;
                ++currentCol;
            }
        }

        return partySectors;
    }

    private List<SvgObject> generatePartyGaps(double centerX, double centerY) {
        double seatGap = 0.5;
        List<SvgObject> gaps = new ArrayList<>();

        for (int col = 0; col < cols; ++col) {
            for (int row = 0; row < rows; ++row){
                double radius = innerRadius + ((outerRadius - innerRadius) * ((double) (rows - row) / rows));
                double startAngle = thetaMin + (thetaMax - thetaMin) * ((double) col / cols);
                double endAngle = thetaMin + (thetaMax - thetaMin) * ((double) (col + 1) / cols);

                SvgSector gap = SvgSector.builder()
                        .xPos(centerX * imageWidth / 100)
                        .yPos(centerY * imageHeight / 100)
                        .radius(radius * imageHeight / 100)
                        .startAngleDeg(startAngle)
                        .endAngleDeg(endAngle)
                        .hexColor(backgroundColor)
                        .thickness(seatGap)
                        .thicknessRelative(true)
                        .build();

                gaps.add(gap);
            }
        }
        return gaps;
    }

    private SvgObject generateInnerHole(double centerX, double centerY) {
        return SvgCircle.builder()
                .xPos(centerX * imageWidth / 100)
                .yPos(centerY * imageHeight / 100)
                .radius(innerRadius / 2 * (imageHeight / 100))
                .hexColor(backgroundColor)
                .fill(true)
                .relative(true)
                .build();
    }

    private SvgObject generateSweepingSector(double centerX, double centerY) {
        int resolution = 260;

        return SvgSweepingSector.builder()
                .xPos(centerX * imageWidth / 100)
                .yPos(centerY * imageHeight / 100)
                .radius(outerRadius * imageHeight / 100)
                .hexColor(backgroundColor)
                .startAngleDeg(thetaMax)
                .endAngleDeg(thetaMin)
                .duration(animationTime)
                .id("inner")
                .resolution(resolution)
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
