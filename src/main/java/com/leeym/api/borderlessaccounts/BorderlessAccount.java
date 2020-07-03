package com.leeym.api.borderlessaccounts;

import com.leeym.common.ProfileId;
import com.leeym.common.RecipientId;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BorderlessAccount {
    private String id;
    private ProfileId profileId;
    private RecipientId recipientId;
    private OffsetDateTime creationTime;
    private OffsetDateTime modificationTime;
    private Boolean active;
    private Boolean eligible;
    private Balance[] balances;

    public List<Balance> getBalances() {
        return Arrays.stream(balances).collect(Collectors.toList());
    }

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
