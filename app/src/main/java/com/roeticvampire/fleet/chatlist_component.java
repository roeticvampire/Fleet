package com.roeticvampire.fleet;

public class chatlist_component {
    private String chatName;
    private String lastMessage;
    private int profilePic;
    private String lastTextTime;
    private String chatUsername;
    public String getChatUsername() {
        return chatUsername;
    }

    public void setChatUsername(String chatUsername) {
        this.chatUsername = chatUsername;
    }


    public chatlist_component(String chatName, String chatUsername,String lastMessage, int profilePic, String lastTextTime) {
        this.chatName = chatName;
        this.lastMessage = lastMessage;
        this.profilePic = profilePic;
        this.lastTextTime = lastTextTime;
        this.chatUsername=chatUsername;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public int getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(int profilePic) {
        this.profilePic = profilePic;
    }

    public String getLastTextTime() {
        return lastTextTime;
    }

    public void setLastTextTime(String lastTextTime) {
        this.lastTextTime = lastTextTime;
    }
}
