package spittr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spittr.data.SpittleRepository;
import spittr.entity.Spittle;

import java.util.List;

/**
 * Created by Administrator on 2016/5/29.
 */
//@Transactional(readOnly = true)
//can use @Transactional in the class level and override it in the method level
@Service
public class SpittleManagerImpl implements SpittleManager{

    SpittleRepository spittleRepository;

    @Autowired
    public SpittleManagerImpl(SpittleRepository spittleRepository) {
        this.spittleRepository = spittleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Spittle> findAll() {
        return spittleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Spittle> findRecentSpittles() {
        return spittleRepository.findRecentSpittles();
    }

    @Override
    @Transactional(readOnly = true)
    public Spittle findOne(long id) {
        return spittleRepository.findOne(id);
    }

    @Override
    @Transactional
    public Spittle save(Spittle spittle) {
        return spittleRepository.save(spittle);
    }

    @Override
    @Transactional
    public void delete(long id) {
        spittleRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return spittleRepository.count();
    }
}
