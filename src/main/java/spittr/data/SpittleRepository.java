package spittr.data;

import java.util.List;

import spittr.entity.Spittle;

public interface SpittleRepository {

  List<Spittle> findAll();

  List<Spittle> findByPage(int pageSize, int page);

  List<Spittle> findRecentSpittles();

  Spittle findOne(long id);

  Spittle save(Spittle spittle);

  void delete(long id);

  long count();
}
