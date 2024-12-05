package com.lucas.tarefas.entities;

import com.lucas.tarefas.entities.enums.Status;
import jakarta.persistence.AttributeConverter;

import java.util.stream.Stream;

public class StatusConverter implements AttributeConverter<Status, String> {
    @Override
    public String convertToDatabaseColumn(Status status) {
        if (status == null){
            return null;
        }
        return status.getValue();
    }

    @Override
    public Status convertToEntityAttribute(String s) {
        if (s == null){
            return null;
        }
        return Stream.of(Status.values())
                .filter(c -> c.getValue().equals(s))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
