package spittr.data;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import spittr.entity.Spittle;

@Repository
public class JdbcSpittleRepository implements SpittleRepository {

  private SessionFactory sessionFactory;

  @Autowired
  public JdbcSpittleRepository(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  private Session currentSession() {
    return sessionFactory.getCurrentSession();//<co id="co_RetrieveCurrentSession"/>
  }

  public List<Spittle> findAll() {
    return (List<Spittle>) spittleCriteria().list();
  }

  public List<Spittle> findRecentSpittles() {
    return findRecentSpittles(10);
  }

  public List<Spittle> findRecentSpittles(int count) {
    return (List<Spittle>) spittleCriteria()
            .setMaxResults(count)
            .list();
  }

  public Spittle findOne(long id) {
    return (Spittle) currentSession().get(Spittle.class, id);
  }

  public Spittle save(Spittle spittle) {
    Serializable id = currentSession().save(spittle);
    return new Spittle(
            (Long) id,
            spittle.getMessage(),
            spittle.getTime(),
            spittle.getLatitude(),
            spittle.getLongitude());
  }


  public void delete(long id) {
    currentSession().delete(findOne(id));
  }

  public long count() {
    return findAll().size();
  }

  private Criteria spittleCriteria() {
    return currentSession()
            .createCriteria(Spittle.class)
            .addOrder(Order.desc("time"));
  }

}
