package players;

public class BluePlayer implements Player {
    private final String name = "Blue";
    private final int startIndex = 9;
    private final int tailStartIndex = 10;
    private final int tailEndIndex = 12;
    private final String colorCode = "\u001B[34m"; //Blue colour.

    @Override
    public String getName() {
        return name;
    }
    @Override
    public int getStartIndex() {
        return startIndex;
    }
    @Override
    public int getTailStartIndex() {
        return tailStartIndex;
    }

    @Override
    public int getTailEndIndex() {
        return tailEndIndex;
    }
    @Override
    public String getColorCode() {
        return colorCode;
    }
}
