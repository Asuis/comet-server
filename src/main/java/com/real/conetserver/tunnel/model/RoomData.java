package com.real.conetserver.tunnel.model;

import com.real.conetserver.tunnel.dto.SimpleUserInfo;

import java.util.ArrayList;

/**
 * @author asuis
 */
public class RoomData {

    private ArrayList<SimpleUserInfo> members = new ArrayList<>();

    public void addMember(UserSession userSession) {
        SimpleUserInfo userInfo = new SimpleUserInfo();
        userInfo.setUserId(userSession.getUserInfo().getOpenId());
        userInfo.setAvatarUrl(userSession.getUserInfo().getAvatarUrl());
        userInfo.setNickName(userSession.getUserInfo().getNickName());
        members.add(userInfo);
    }

    public ArrayList<SimpleUserInfo> getMembers() {
        return this.members;
    }

}
