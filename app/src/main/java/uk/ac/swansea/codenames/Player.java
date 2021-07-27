package uk.ac.swansea.codenames;

public class Player {
    private String nickname;
    private boolean isHost;
    private boolean isSpymaster;

    public Player(String nickname, boolean isHost, boolean isSpymaster) {
        this.nickname = nickname;
        this.isHost = isHost;
        this.isSpymaster = isSpymaster;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }

    public boolean isSpymaster() {
        return isSpymaster;
    }

    public void setSpymaster(boolean spymaster) {
        isSpymaster = spymaster;
    }
}
