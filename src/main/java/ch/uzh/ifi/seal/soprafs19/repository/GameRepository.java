package ch.uzh.ifi.seal.soprafs19.repository;


import ch.uzh.ifi.seal.soprafs19.entity.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface GameRepository extends CrudRepository<Game, Long> {
    Boolean existsById(long id);
    Game findById(long id);

}
