package org.example.crmtp.model;

import lombok.Getter;

@Getter
public enum OrderState {

    // Chaque état est associé à un entier pour le stockage en base de données.
    CANCELED(0),
    OPTION(1),
    CONFIRMED(2);

    private final int value;

    OrderState(int value) {
        this.value = value;
    }


    // Pour convertir l'entier venant de la base de données ou d'une requête en une constante
    public static OrderState fromValue(int value) {
        // On parcourt toutes les constantes de l'énumération pour trouver celle qui correspond à la valeur passée en paramètre.
        for (OrderState state : OrderState.values()) {
            if (state.getValue() == value) {
                return state;  // Si une correspondance est trouvée, on retourne l'état associé.
            }
        }
        // Exception si non trouvé
        throw new IllegalArgumentException("Valeur inconnue: " + value);
    }
}
