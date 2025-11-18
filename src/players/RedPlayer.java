package players;

import playersgamepositions.PlayersInGameContext;

public class RedPlayer implements Player{
    private final String name = "Red";
    private final int startIndex = 0;
    private final int tailStartIndex = 18;
    private final int tailEndIndex = 20;
    private final String colorCode = "\u001B[31m"; //Red colour.



    //Holding a Players state and behaviour
    //private final PlayersInGameContext playerContext;

    public RedPlayer(){
    }
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
