# ParliamentSeatSVG
Command-line SVG generation tool for the distribution of seats in political parties

Usage:

<pre>
-at,--animationTime &lt;arg&gt;     Animation time in seconds (default 3, warning: drastically increases file size, runs poorly on Safari (but great on Firefox, Chrome etc...))

-bg,--backgroundColor &lt;arg&gt;   Background color in hex (default grey)

-c,--cols &lt;arg&gt;               Number of columns (default 24)

-fg,--foregroundColor &lt;arg&gt;   Foreground color in hex (default black)

-ih,--imageHeight &lt;arg&gt;       Image height in pixels (default 150)

-iw,--imageWidth &lt;arg&gt;        Image width in pixels (default 300)

-o,--output &lt;arg&gt;             Output file path for the SVG (REQUIRED)

-p,--parties &lt;arg&gt;;           Path to the JSON file containing party data (REQUIRED)

-r,--rows &lt;arg&gt;               Number of rows (default 6)

-t,--title &lt;arg&gt;              Title of the graph (default "")
</pre>

Design inspired by: https://github.com/AlexMoga2004/ParliamentSeatSVG/blob/main/graph.svg

# Example: 
![graph](https://github.com/user-attachments/assets/dbcd5070-f7a2-4388-a950-35525a11cde9)
