package com.mistique.mywalletapp.mywalletapp.data;

import com.mistique.mywalletapp.mywalletapp.data.base.AbstractGenericRepository;
import com.mistique.mywalletapp.mywalletapp.data.base.GenericRepository;
import com.mistique.mywalletapp.mywalletapp.models.Type;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class TypeSqlRepository extends AbstractGenericRepository<Type> implements GenericRepository<Type> {
    private SessionFactory factory;

    @Autowired
    public TypeSqlRepository(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Type> listAll() {
        List<Type> types = new ArrayList<>();
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            types = session.createQuery("from Type").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return types;
    }



    @Override
    public Type findById(int id) {
        Type type = null;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            type = session.get(Type.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return type;
    }

    @Override
    public void update(int id, Type type) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Type t = session.get(Type.class, id);
            t.setName(type.getName());
            session.getTransaction().commit();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            Type type = session.get(Type.class, id);
            session.delete(type);
            session.getTransaction().commit();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
