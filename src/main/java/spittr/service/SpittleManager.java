package spittr.service;

import spittr.entity.Spittle;

import java.util.List;

/**
 * Created by Administrator on 2016/5/29.
 */
public interface SpittleManager {
    List<Spittle> findAll();

    List<Spittle> findRecentSpittles();

    Spittle findOne(long id);

    Spittle save(Spittle spittle);

    void delete(long id);

    long count();
}
