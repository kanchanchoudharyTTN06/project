package com.ttn.bootcamp.token;

import com.ttn.bootcamp.Utility;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class AccountActivationToken {
    private String uuid;
    private long userId;
    private long creationDate;

    public AccountActivationToken() {
    }

    public AccountActivationToken(long userId) {
        this.uuid = UUID.randomUUID().toString();
        this.userId = userId;
        this.creationDate = new Date().getTime();
    }

    public String getUuid() {
        return uuid;
    }

    public AccountActivationToken setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public AccountActivationToken setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public AccountActivationToken setCreationDate(long creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public String getToken() {
        StringBuilder sb = new StringBuilder();
        sb.append(uuid).append("|").append(creationDate).append("|").append(userId);
        return Utility.encrypt(sb.toString());
    }

    public static AccountActivationToken getAccountActivationTokenBean(String token) {
        String decryptedToken = Utility.decrypt(token);
        String[] props = decryptedToken.split("\\|");
        if (props.length != 3) {
            return null;
        }
        return new AccountActivationToken()
                .setUuid(props[0])
                .setCreationDate(Long.parseLong(props[1]))
                .setUserId(Long.parseLong(props[2]));
    }

    public boolean isValidToken(int validationMinute) {
        long oneMinuteInMS = 60000;
        long currentTime = new Date().getTime();
        long createdTime = this.creationDate;
        return (createdTime + (validationMinute * oneMinuteInMS)) >= currentTime;
    }
}
