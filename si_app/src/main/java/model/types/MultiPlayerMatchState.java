package model.types;

/**
 * Represents a multiplayer match state
 */
public enum MultiPlayerMatchState {
    NOT_STARTED, WAITING_FOR_PLAYERS, IN_PROGRESS, FINISHED;

    public static final String MULTI_PLAYER_MATCH_STATE_REGEX = "^(Por iniciar|A aguardar jogadores|Em curso|Terminada)$";

    static {
        NOT_STARTED.description = "Por iniciar";
        WAITING_FOR_PLAYERS.description = "A aguardar jogadores";
        IN_PROGRESS.description = "Em curso";
        FINISHED.description = "Terminada";
    }

    public String description;

    /**
     * Parses a string to a multiplayer match state
     *
     * @param state the string to parse
     * @return the multiplayer match state
     */
    public static MultiPlayerMatchState parse(String state) {
        return switch (state.toLowerCase()) {
            case "por iniciar" -> NOT_STARTED;
            case "a aguardar jogadores" -> WAITING_FOR_PLAYERS;
            case "em curso" -> IN_PROGRESS;
            case "terminada" -> FINISHED;
            default -> throw new IllegalStateException("Unexpected value: " + state);
        };
    }
}
