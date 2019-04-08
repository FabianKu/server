package ch.uzh.ifi.seal.soprafs19.model;

public class AllId {
    //CONSTRUCTOR
    public AllId(){};
    public AllId(long gameId, long playerId, int cardId) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.cardId = cardId;
    }

    //ATTRIBUTES
    private long gameId;
    private long playerId;
    private int cardId;

    //GETTERS AND SETTERS
    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }
}