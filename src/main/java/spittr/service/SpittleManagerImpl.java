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
@Transactional(readOnly = true)
public class SpittleManagerImpl implements SpittleManager{

    SpittleRepository spittleRepository;

    @Autowired
    public SpittleManagerImpl(SpittleRepository spittleRepository) {
        this.spittleRepository = spittleRepository;
    }

    @Override
    public List<Spittle> findAll() {
        return spittleRepository.findAll();
    }

    @Override
    public List<Spittle> findByPage(int pageSize, int page){
        return  spittleRepository.findByPage(pageSize, page);
    }

    @Override
    public List<Spittle> findRecentSpittles() {
        return spittleRepository.findRecentSpittles();
    }

    @Override
    public Spittle findOne(long id) {
        return spittleRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = false)
    public Spittle save(Spittle spittle) {
        return spittleRepository.save(spittle);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(long id) {
        spittleRepository.delete(id);
    }

    @Override
    public long count() {
        return spittleRepository.count();
    }
}
