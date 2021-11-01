package com.ttn.bootcamp.token;

import com.ttn.bootcamp.ApplicationConstants;
import com.ttn.bootcamp.util.Utility;

import java.util.Date;

public class AccountActivationToken {
    private String secreteCode;
    private long userId;
    private long creationDate;

    public AccountActivationToken() {
    }

    public AccountActivationToken(long userId) {
        this.userId = userId;
        this.creationDate = new Date().getTime();
    }

    public String getSecreteCode() {
        return secreteCode;
    }

    public AccountActivationToken setSecreteCode(String secreteCode) {
        this.secreteCode = secreteCode;
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
        sb.append(ApplicationConstants.SECRETE_CODE).append("|").append(creationDate).append("|").append(userId);
        return Utility.encrypt(sb.toString());
    }

    public static AccountActivationToken getAccountActivationTokenBean(String token) {
        String decryptedToken = Utility.decrypt(token);
        String[] props = decryptedToken.split("\\|");
        if (props.length != 3) {
            return null;
        }
        return new AccountActivationToken()
                .setSecreteCode(props[0])
                .setCreationDate(Long.parseLong(props[1]))
                .setUserId(Long.parseLong(props[2]));
    }

    public boolean isNotExpired(int validationMinute) {
        long oneMinuteInMS = 60000;
        long currentTime = new Date().getTime();
        long createdTime = this.creationDate;
        return (createdTime + (validationMinute * oneMinuteInMS)) >= currentTime;
    }
}
