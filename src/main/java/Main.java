import svg.HexColor;

public class Main {

    public static void main(String[] args) {
        GraphBuilder graphBuilder = GraphBuilder.builder()
                .imageWidth(300 * 5)
                .imageHeight(150 * 5)
                .backgroundColor(new HexColor("#DCDBD9"))
                .foregroundColor(new HexColor("#000000"))
                .title("Distribution of Seats in Barking")
                .rows(6)
                .cols(24)
                .animationTime(3)
                .build();

        graphBuilder.generateSVG("parties.json", "graph.svg");
    }
}
