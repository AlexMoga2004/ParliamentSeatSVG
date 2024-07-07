package svg;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Party {
    private String name;
    private int numSeats;
    private HexColor hexColor;

    public static List<Party> getPartiesFromJSON(String filepath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(
                    new File(filepath), objectMapper.getTypeFactory().constructCollectionType(List.class, Party.class)
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
