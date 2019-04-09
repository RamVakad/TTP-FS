package com.example.assesment.converter;

import com.example.assesment.model._enum.TxnType;

import javax.persistence.AttributeConverter;

public class TxnTypeConverter implements AttributeConverter<TxnType, String> {

    @Override
    public String convertToDatabaseColumn(TxnType txnType) {
        return txnType.getType();
    }

    @Override
    public TxnType convertToEntityAttribute(String dbData) {
        return TxnType.getTxnType(dbData);
    }

}
