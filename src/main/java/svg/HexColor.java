package svg;
import static svg.Constants.HEX_CODE_ERROR;
import static svg.Constants.hexColorRegex;

public class HexColor {
    String hexColor;

    public HexColor(String hexColor) {
        if (!hexColorRegex.matcher(hexColor).matches())
            throw new IllegalArgumentException(HEX_CODE_ERROR);

        this.hexColor = hexColor;
    }

    @Override
    public String toString() {
        return hexColor;
    }
}
