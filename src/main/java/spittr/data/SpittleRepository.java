package spittr.data;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import spittr.entity.Spittle;

public interface SpittleRepository {

  @Cacheable("spittleCache")
  List<Spittle> findAll();

  @Cacheable("spittleCache")
  List<Spittle> findByPage(int pageSize, int page);

  List<Spittle> findRecentSpittles();

  @Cacheable("spittleCache")
  Spittle findOne(long id);

  @Cacheable(value = "spittleCache", key = "#result.id")
  Spittle save(Spittle spittle);

  @CacheEvict("spittleCache")
  void delete(long id);

  long count();
}
