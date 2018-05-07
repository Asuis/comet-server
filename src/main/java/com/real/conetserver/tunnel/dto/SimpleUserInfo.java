package com.real.conetserver.tunnel.dto;

/**
 * @author asuis
 */
public class SimpleUserInfo {
    private String nickName;
    private String avatarUrl;
    private String userId;

    public String getNickName() {
        return this.nickName;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
