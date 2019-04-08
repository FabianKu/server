package ch.uzh.ifi.seal.soprafs19.model;

public class Invitation {

    //CONSTRUCTOR
    public Invitation(){};
    public Invitation(long inviterId, long inviteeId, Boolean answer) {
        this.inviterId = inviterId;
        this.inviteeId = inviteeId;
        this.answer = answer;
    }

    //ATTRIBUTES
    private long inviterId;
    private long inviteeId;
    private Boolean answer;

    //GETTERS AND SETTERS
    public long getInviterId() {
        return inviterId;
    }

    public void setInviterId(long inviterId) {
        this.inviterId = inviterId;
    }

    public long getInviteeId() {
        return inviteeId;
    }

    public void setInviteeId(long inviteeId) {
        this.inviteeId = inviteeId;
    }

    public Boolean isAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }
}
