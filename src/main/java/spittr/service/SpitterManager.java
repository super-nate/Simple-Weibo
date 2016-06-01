package spittr.service;

import spittr.entity.Spitter;

import java.util.List;

/**
 * Created by Administrator on 2016/5/29.
 */
public interface SpitterManager {
    List<Spitter> findAll();

    Spitter findByUsername(String username);

    Spitter findOne(Long id);

    Spitter save(Spitter spitter);

    void update(Spitter spitter);

    long count();

}
