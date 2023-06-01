package model.types;

/**
 * Represents a player state
 */
public enum PlayerState {
    ACTIVE, INACTIVE, BANED;

//    public String description;
//
//    static {
//        ACTIVE.description = "Ativo";
//        INACTIVE.description = "Inativo";
//        BANED.description = "Banido";
//    }
    public static final String PLAYER_STATE_REGEX = "^(ativo|banido|inativo)$";

    /**
     * Parses a string to a player state
     *
     * @param state the string to parse
     * @return the player state
     */
    public static PlayerState parse(String state) {
        return switch (state.toLowerCase()) {
            case "ativo" -> ACTIVE;
            case "inativo" -> INACTIVE;
            case "banido" -> BANED;
            default -> throw new IllegalStateException("Unexpected value: " + state);
        };
    }
}
