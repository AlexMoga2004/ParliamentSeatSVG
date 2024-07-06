import svg.HexColor;
import svg.SVGBuilder;
import svg.objects.*;

public class Main {

    public static void main(String[] args) {
        // Testing basic SVG Functionality

        SVGBuilder svgBuilder = new SVGBuilder(200, 200);
        svgBuilder.setBackgroundColor(new HexColor("#AAAAAA"));

        svgBuilder.appendObject(SvgRect.builder()
                .xPos(10)
                .yPos(10)
                .width(10)
                .height(10)
                .hexColor(new HexColor("#000000"))
                .fill(true)
                .build()
        );

        svgBuilder.appendObject(SvgCircle.builder()
                .xPos(30)
                .yPos(30)
                .radius(5)
                .thickness(1)
                .hexColor(new HexColor("#00FF00"))
                .build()
        );

        svgBuilder.appendObject(SvgSector.builder()
                .xPos(50)
                .yPos(50)
                .radius(5)
                .startAngleDeg(0)
                .endAngleDeg(90)
                .hexColor(new HexColor("#0000FF"))
                .build()
        );

        svgBuilder.appendObject(SvgLine.builder()
                .x1(0)
                .y1(0)
                .x2(100)
                .y2(100)
                .relative(true)
                .hexColor(new HexColor("#FF00FF"))
                .width(1)
                .build()
        );

        svgBuilder.appendObject(SvgText.builder()
                .xPos(45)
                .yPos(65)
                .size(5)
                .text("Text")
                .build()
        );

        svgBuilder.writeToFile("test.svg");
    }
}