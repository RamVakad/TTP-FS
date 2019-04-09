package com.example.assesment.model._enum;

public enum TxnType {
    TXN_BUY, TXN_SELL;

    public String getType() {
        return name();
    }

    public static TxnType getTxnType(String str) {
        switch (str) {
            case "TXN_BUY": return TxnType.TXN_BUY;
            case "TXN_SELL": return TxnType.TXN_SELL;
            default: throw new IllegalArgumentException("TXN_TYPE [" + str + "] not supported.");
        }
    }

}