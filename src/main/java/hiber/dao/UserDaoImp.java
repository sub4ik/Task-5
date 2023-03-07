package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserWithCar(String model, int series) {
      User user;
      String hql = "SELECT u FROM User u INNER JOIN u.car c WHERE c.model = ?1 AND c.series = ?2";
      Query<User> query = sessionFactory.getCurrentSession().createQuery(hql);
      query.setParameter(1, model);
      query.setParameter(2, series);
      try {
         user = query.getSingleResult();
      } catch (NoResultException noResultException){
         return null;
      }
      return user;
   }

}
