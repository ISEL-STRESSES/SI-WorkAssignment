package pt.isel.dataAccess;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.LockModeType;
import pt.isel.model.entities.player.Player;

public class DataMappers {

    private final JPAContext context;

    public DataMappers(JPAContext context) {
        this.context = context;
    }

    protected class PlayerDataMapper implements pt.isel.logic.mappers.player.PlayerMapper {
        @Override
        public Integer create(Player entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        @Override
        public Player read(Integer id) {
            return context.em.find(Player.class, id);
        }

        @Override
        public Integer update(Player entity) {
            context.beginTransaction();
            Player player = context.em.find(Player.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (player == null) {
                throw new EntityNotFoundException("Player with id " + entity.getId() + " not found");
            }
            // player.setId(entity.getId());
            player.setUsername(entity.getUsername());
            player.setEmail(entity.getEmail());
            player.setEstado(entity.getEstado());
            player.setNomeRegiao(entity.getNomeRegiao());
            player.setJogadorEstatistica(entity.getJogadorEstatistica());
            context.commit();
            return entity.getId();
        }

        @Override
        public void delete(Integer id) {
            context.beginTransaction();
            Player player = context.em.find(Player.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (player == null) {
                throw new EntityNotFoundException("Player with id " + id + " not found");
            }
            context.em.remove(player);
            context.commit();
        }
    }
}
