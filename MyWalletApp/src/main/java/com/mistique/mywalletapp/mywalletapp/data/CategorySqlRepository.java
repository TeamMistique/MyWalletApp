package com.mistique.mywalletapp.mywalletapp.data;

import com.mistique.mywalletapp.mywalletapp.data.base.AbstractGenericRepository;
import com.mistique.mywalletapp.mywalletapp.data.base.GenericRepository;
import com.mistique.mywalletapp.mywalletapp.models.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CategorySqlRepository extends AbstractGenericRepository<Category> implements GenericRepository<Category> {

    private SessionFactory factory;

    @Autowired
    public CategorySqlRepository(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Category> listAll() {
        List<Category> categories = new ArrayList<>();
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            categories = session.createQuery("from Category").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return categories;
    }

    @Override
    public Category findById(int id) {
        Category category = null;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            category = session.get(Category.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return category;
    }

    @Override
    public void update(int id, Category category) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Category c = session.get(Category.class, id);
            c = category;
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Category category = session.get(Category.class, id);
            session.delete(category);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
