package org.example.crmtp.model;

import lombok.Getter;

@Getter
public enum CustomerState {
    ACTIVE(0),
    INACTIVE(1);

    private final int value;

    CustomerState(int value) {
        this.value = value;
    }

    public static CustomerState fromValue(int value) {
        for (CustomerState state : CustomerState.values()) {
            if (state.getValue() == value) {
                return state;
            }
        }
        throw new IllegalArgumentException("Valeur inconnue: " + value);
    }
}

