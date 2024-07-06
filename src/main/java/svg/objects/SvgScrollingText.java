package svg.objects;

import lombok.Builder;
import svg.HexColor;

@Builder
public class SvgScrollingText extends SvgObject{
    private double xPos;
    private double yPos;
    private double size;
    private HexColor hexColor;
    private boolean centered;
    private boolean relative;
    private int startCount;
    private int endCount;
    private double duration;

    @Builder.Default
    private String prepend = "";

    @Builder.Default
    private String append = "";

    /*
        Create multiple text elements that increment from startCount to endCount in duration seconds
     */
    @Override
    public String toString() {
        assert startCount < endCount;

        StringBuilder stringBuilder = new StringBuilder();
        double increment = duration / (endCount - startCount);

        for (int i = startCount; i < endCount; ++i) {
            SvgText text = SvgText.builder()
                    .xPos(xPos)
                    .yPos(yPos)
                    .size(size)
                    .centered(centered)
                    .text(prepend + i + append)
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
                .centered(centered)
                .text(prepend + endCount + append)
                .hexColor(hexColor)
                .opacity(0)
                .relative(relative)
                .build();

        finalText.setAppearance(duration);
        stringBuilder.append(finalText);
        return stringBuilder.toString();
    }
}
