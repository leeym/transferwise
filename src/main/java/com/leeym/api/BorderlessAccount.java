package com.leeym.api;

import java.util.Arrays;

public class BorderlessAccount {
    private String id;
    private String profileId;
    private String recipientId;
    private String creationTime;
    private String modificationTime;
    private Boolean active;
    private Boolean eligible;
    private Balance[] balances;

    @Override
    public String toString() {
        return "BorderlessAccount{" +
                "id='" + id + '\'' +
                ", profileId='" + profileId + '\'' +
                ", recipientId='" + recipientId + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", modificationTime='" + modificationTime + '\'' +
                ", active=" + active +
                ", eligible=" + eligible +
                ", balances=" + Arrays.toString(balances) +
                '}';
    }
}
