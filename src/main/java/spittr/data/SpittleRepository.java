package spittr.data;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import spittr.entity.Spittle;

public interface SpittleRepository {

  @Cacheable("spittleCache")
  List<Spittle> findAll();

  //TODO this will mess up the cache with different user, so I should add a id field in the params list
  @Cacheable("spittleCache")
  List<Spittle> findByPage(int pageSize, int page);

  List<Spittle> findRecentSpittles();

  @Cacheable("spittleCache")
  Spittle findOne(long id);

  @CachePut(value = "spittleCache", key = "#result.id")
  Spittle save(Spittle spittle);

  @CacheEvict("spittleCache")
  void delete(long id);

  long count();
}
