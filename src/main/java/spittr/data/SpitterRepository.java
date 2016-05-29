package spittr.data;

import spittr.entity.Spitter;

import java.util.List;

public interface SpitterRepository {

  List<Spitter> findAll();

  Spitter findByUsername(String username);

  Spitter findOne(Long id);

  Spitter save(Spitter spitter);

  void update(Spitter spitter);

  long count();

}
