package spittr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spittr.data.SpitterRepository;
import spittr.entity.Spitter;

import java.util.List;

/**
 * Created by Administrator on 2016/5/29.
 */
@Service
@Transactional(readOnly = true)
public class SpitterManagerImpl implements SpitterManager {


    SpitterRepository spitterRepository;

    @Autowired
    public SpitterManagerImpl(SpitterRepository spitterRepository) {
        this.spitterRepository = spitterRepository;
    }

    @Override
    public List<Spitter> findAll() {
        return spitterRepository.findAll();
    }

    @Override
    public Spitter findByUsername(String username) {
        return spitterRepository.findByUsername(username);
    }

    @Override
    public Spitter findOne(Long id) {
        return spitterRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = false)
    public Spitter save(Spitter spitter) {
        return spitterRepository.save(spitter);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(Spitter spitter) {
        spitterRepository.update(spitter);
    }

    @Override
    public long count() {
        return spitterRepository.count();
    }
}
