import svg.HexColor;

import org.apache.commons.cli.*;
import java.awt.Color;

public class Main {

    public static void main(String[] args) {
        // Define options
        Options options = getOptions();

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            // Required options
            String partiesPath = cmd.getOptionValue("p");
            String outputPath = cmd.getOptionValue("o");

            if (partiesPath == null || outputPath == null) {
                throw new ParseException("arguments --p and --o required");
            }

            // Optional options with defaults
            int imageWidth = Integer.parseInt(cmd.getOptionValue("iw", "1500"));
            int imageHeight = Integer.parseInt(cmd.getOptionValue("ih", "750"));
            HexColor backgroundColor = new HexColor(cmd.getOptionValue("bg", "#DCDBD9"));
            HexColor foregroundColor = new HexColor(cmd.getOptionValue("fg", "#000000"));
            String title = cmd.getOptionValue("t", "");
            int rows = Integer.parseInt(cmd.getOptionValue("r", "6"));
            int cols = Integer.parseInt(cmd.getOptionValue("c", "24"));
            int animationTime = Integer.parseInt(cmd.getOptionValue("at", "3"));

            // Build the graph
            GraphBuilder graphBuilder = GraphBuilder.builder()
                    .imageWidth(imageWidth)
                    .imageHeight(imageHeight)
                    .backgroundColor(backgroundColor)
                    .foregroundColor(foregroundColor)
                    .title(title)
                    .rows(rows)
                    .cols(cols)
                    .animationTime(animationTime)
                    .build();

            // Generate the SVG
            graphBuilder.generateSVG(partiesPath, outputPath);

        } catch (ParseException e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("GraphGenerator", options);
        }
    }

    private static Options getOptions() {
        Options options = new Options();

        options.addOption("iw", "imageWidth", true, "Image width in pixels");
        options.addOption("ih", "imageHeight", true, "Image height in pixels");
        options.addOption("bg", "backgroundColor", true, "Background color in hex");
        options.addOption("fg", "foregroundColor", true, "Foreground color in hex");
        options.addOption("t", "title", true, "Title of the graph");
        options.addOption("r", "rows", true, "Number of rows");
        options.addOption("c", "cols", true, "Number of columns");
        options.addOption("at", "animationTime", true, "Animation time in seconds");
        options.addOption("p", "parties", true, "Path to the JSON file containing party data");
        options.addOption("o", "output", true, "Output file path for the SVG");
        return options;
    }
}
