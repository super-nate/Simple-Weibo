package spittr.data;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import spittr.entity.Spitter;

@Repository
public class JdbcSpitterRepository implements SpitterRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public JdbcSpitterRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();//<co id="co_RetrieveCurrentSession"/>
    }

    @Override
    public List<Spitter> findAll() {
        return (List<Spitter>) currentSession()
                .createCriteria(Spitter.class)
                .addOrder(Order.asc("id")).list();
    }

    public Spitter findByUsername(String username) {
        return (Spitter) currentSession()
                .createCriteria(Spitter.class)
                .add(Restrictions.eq("username", username))
                .list().get(0);
    }

    @Override
    public Spitter findOne(Long id) {
        return (Spitter) currentSession().get(Spitter.class, id);
    }

    public Spitter save(Spitter spitter) {

        Serializable id = currentSession().save(spitter);  //<co id="co_UseCurrentSession"/>
        return new Spitter((Long) id,
                spitter.getUsername(),
                spitter.getPassword(),
                spitter.getFirstName(),
                spitter.getLastName(),
                spitter.getEmail()
        );
    }


    public void update(Spitter spitter) {
        currentSession().update(spitter);
    }

    public long count() {
        return findAll().size();
    }
}
