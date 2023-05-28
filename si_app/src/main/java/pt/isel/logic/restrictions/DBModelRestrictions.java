package pt.isel.logic.restrictions;

import pt.isel.model.entities.player.Player;

/**
 * class that implements the Database model restrictions.
 */
public class DBModelRestrictions {

    /**
     * class that implements the {@link Player} restrictions.
     */
    public static class PlayerRestrictions implements Restrictions<Player> {

        /**
         * Checks if the {@link Player} entity is valid.
         * @param entity the player entity to be checked.
         * @return true if the player entity is valid, false otherwise.
         * @throws RestrictionException if the player entity is not valid.
         */
        @Override
        public Boolean checkDBRestrictions(Player entity) throws RestrictionException {
//            if (!entity.getEmail().isValid())
//                throw new RestrictionException("Email is not valid");
//
//            if (!entity.getState().matches("^(ativo|banido|inativo)$"))
//                throw new RestrictionException("Estado is not valid");

            return true;
        }
    }
}
