package svg;

import svg.objects.SvgObject;
import svg.objects.SvgRect;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SVGBuilder {

    private final int IMAGE_WIDTH;
    private final int IMAGE_HEIGHT;
    private List<SvgObject> svgObjects = new ArrayList<>();

    public SVGBuilder(int imageWidth, int imageHeight) {
        IMAGE_WIDTH = imageWidth;
        IMAGE_HEIGHT = imageHeight;
    }

    public void reset() {
        svgObjects = new ArrayList<>();
    }

    public void setBackgroundColor(HexColor hexColor) {
        SvgRect backgroundRect = SvgRect.builder()
            .xPos(0)
            .yPos(0)
            .width(100)
            .height(100)
            .hexColor(hexColor)
            .relative(true)
            .fill(true)
            .build();

        svgObjects.add(0, backgroundRect);
    }

    public void appendObject(SvgObject svgObject) {
        svgObjects.add(svgObject);
    }

    public void insertObject(int index, SvgObject svgObject) {
        svgObjects.add(index, svgObject);
    }

    @Override
    public String toString() {
        StringBuilder rawSVG = new StringBuilder();
        rawSVG.append(Constants.initialSvg
                .replaceAll("WIDTH", String.valueOf(IMAGE_WIDTH))
                .replaceAll("WIDTH", String.valueOf(IMAGE_HEIGHT)));

        svgObjects.forEach(rawSVG::append);

        rawSVG.append(Constants.finalSVG);
        return rawSVG.toString();
    }

    public void writeToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(this.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
