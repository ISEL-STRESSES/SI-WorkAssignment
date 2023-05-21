package pt.isel.logic.restrictions;

import pt.isel.model.entities.player.Player;

public class DBModelRestrictions {

    public static class PlayerRestrictions implements Restrictions<Player> {
        @Override
        public Boolean checkDBRestrictions(Player entity) throws RestrictionException {
            if (!entity.getEmail().isValid())
                throw new RestrictionException("Email is not valid");

            if (!entity.getEstado().matches("^(ativo|banido|inativo)$"))
                throw new RestrictionException("Estado is not valid");

            return true;
        }
    }
}
