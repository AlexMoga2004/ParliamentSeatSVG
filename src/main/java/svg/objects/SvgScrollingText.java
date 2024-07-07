package svg.objects;

import lombok.Builder;
import svg.HexColor;

@Builder
public class SvgScrollingText extends SvgObject{
    private double xPos;
    private double yPos;
    private double size;
    private String text;
    private HexColor hexColor;
    private boolean relative;
    private int startCount;
    private int endCount;
    private double duration;

    /*
        Create multiple text elements that count from startCount to endCount in duration seconds
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        double increment = duration / (endCount - startCount);

        for (int i = startCount; i < endCount; ++i) {
            SvgText text = SvgText.builder()
                    .xPos(xPos)
                    .yPos(yPos)
                    .size(size)
                    .text(String.valueOf(i))
                    .hexColor(hexColor)
                    .opacity(0)
                    .relative(relative)
                    .build();

            text.setDuration((i - startCount) * increment, ((i + 1) - startCount) * increment);
            stringBuilder.append(text);
        }

        SvgText finalText = SvgText.builder()
                .xPos(xPos)
                .yPos(yPos)
                .size(size)
                .text(String.valueOf(endCount))
                .hexColor(hexColor)
                .opacity(0)
                .relative(relative)
                .build();

        finalText.setAppearance(duration);
        return stringBuilder.toString();
    }
}
