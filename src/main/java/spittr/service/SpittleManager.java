package spittr.service;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import spittr.entity.Spittle;

import java.util.List;

/**
 * Created by Administrator on 2016/5/29.
 */
public interface SpittleManager {
    List<Spittle> findAll();

    //@PostFilter( "filterObject.spitter.username == principal.name")
    List<Spittle> findByPage(int pageSize, int page);

    List<Spittle> findRecentSpittles();

    @PostAuthorize("returnObject.spitter.username == principal.username")
    Spittle findOne(long id);

    Spittle save(Spittle spittle);

    void delete(long id);

    long count();
}
