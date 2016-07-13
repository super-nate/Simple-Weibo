package spittr.data;

import org.springframework.cache.annotation.Cacheable;
import spittr.entity.Spitter;

import java.util.List;

public interface SpitterRepository {

  List<Spitter> findAll();

  @Cacheable("spittleCache")
  Spitter findByUsername(String username);

  @Cacheable("spittleCache")
  Spitter findOne(Long id);

  Spitter save(Spitter spitter);

  void update(Spitter spitter);

  long count();

}
