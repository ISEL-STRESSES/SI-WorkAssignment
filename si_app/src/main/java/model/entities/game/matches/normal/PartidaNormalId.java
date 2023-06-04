package model.entities.game.matches.normal;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import model.entities.game.matches.PartidaId;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the primary key of the {@link PartidaNormal} entity.
 */
@Embeddable
public class PartidaNormalId implements Serializable {

    @Embedded
    private PartidaId matchId;

    public PartidaId getMatchId() {
        return matchId;
    }

    public void setMatchId(PartidaId matchId) {
        this.matchId = matchId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PartidaNormalId entity)) return false;
        return this.getMatchId().getMatchNumber().equals(entity.getMatchId().getMatchNumber()) &&
                this.getMatchId().getGameId().equals(entity.getMatchId().getGameId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchId.getGameId(), matchId.getMatchNumber());
    }
}