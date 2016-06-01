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
public class SpitterManagerImpl implements SpitterManager {


    SpitterRepository spitterRepository;

    @Autowired
    public SpitterManagerImpl(SpitterRepository spitterRepository) {
        this.spitterRepository = spitterRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Spitter> findAll() {
        return spitterRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Spitter findByUsername(String username) {
        return spitterRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Spitter findOne(Long id) {
        return spitterRepository.findOne(id);
    }

    @Override
    @Transactional
    public Spitter save(Spitter spitter) {
        return spitterRepository.save(spitter);
    }

    @Override
    @Transactional
    public void update(Spitter spitter) {
        spitterRepository.update(spitter);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return spitterRepository.count();
    }
}
