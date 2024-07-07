package svg.objects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import svg.HexColor;

@Getter
@Setter
@Builder
public class SvgSweepingSector extends SvgObject{

    private double xPos;
    private double yPos;
    private double radius;
    private double startAngleDeg;
    private double endAngleDeg;
    private String id;
    private HexColor hexColor;
    private int resolution;
    private double duration;

    @Override
    public String toString() {
        assert id != null;
        StringBuilder stringBuilder = new StringBuilder();

        if (duration > 0) {
            for (int i = 0; i < resolution; ++i) {
                double localDuration = duration / (double) resolution;
                double startAngle = startAngleDeg + i * (endAngleDeg - startAngleDeg) / resolution;

                SvgSector sector = SvgSector.builder()
                        .xPos(xPos)
                        .yPos(yPos)
                        .radius(radius)
                        .startAngleDeg(startAngle)
                        .endAngleDeg(endAngleDeg)
                        .hexColor(hexColor)
                        .fill(true)
                        .build();

                if (i == 0) {
                    // No start ID required for first run
                    sector.addIDFade(id + "_" + i, 1, 0, localDuration);
                } else {
                    // Start once the previous call has ended
                    sector.addIDFade(id + "_" + i, id + "_" + (i - 1), 1, 0, localDuration);
                }

                stringBuilder.append(sector);
            }
        }
        System.out.println(stringBuilder);
        return stringBuilder.toString();
    }
}
