package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

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
    @SuppressWarnings("unchecked")
    public User getUserByCar(String model, int series) {
        Car car = sessionFactory.getCurrentSession()
                .createQuery("select i from Car i where i.model = :model and i.series = :series", Car.class)
                .setParameter("model", model)
                .setParameter("series", series)
                .getSingleResult();

        User u = sessionFactory.getCurrentSession().get(User.class, car.getId());

        System.out.println("*****");
        System.out.println("User with car model " + model + " and series " + series + " is: ");
        System.out.println("Id = "+u.getId());
        System.out.println("First Name = "+u.getFirstName());
        System.out.println("Last Name = "+u.getLastName());
        System.out.println("Email = "+u.getEmail());
        System.out.println("*****");

        return u;
    }
}
