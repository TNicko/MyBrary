package com.example.mybrary.network;

public class ConnectionModel {

    private String type;
    private boolean isConnected;

    public ConnectionModel(String type, boolean isConnected) {
        this.type = type;
        this.isConnected = isConnected;
    }

    public String getType() {
        return type;
    }

    public boolean getIsConnected() {
        return isConnected;
    }
}
