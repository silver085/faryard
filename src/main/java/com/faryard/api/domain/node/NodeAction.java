package com.faryard.api.domain.node;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Document("NodeActions")
public class NodeAction {
    @Id
    private String id;
    private Date actionCommitDate;
    private Action action;
    private Boolean confirmed;
    private Date actionConfirmedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getActionCommitDate() {
        return actionCommitDate;
    }

    public void setActionCommitDate(Date actionCommitDate) {
        this.actionCommitDate = actionCommitDate;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Date getActionConfirmedDate() {
        return actionConfirmedDate;
    }

    public void setActionConfirmedDate(Date actionConfirmedDate) {
        this.actionConfirmedDate = actionConfirmedDate;
    }
}
