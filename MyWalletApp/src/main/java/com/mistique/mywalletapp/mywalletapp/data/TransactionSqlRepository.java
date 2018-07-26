package com.mistique.mywalletapp.mywalletapp.data;

import com.mistique.mywalletapp.mywalletapp.data.base.AbstractGenericRepository;
import com.mistique.mywalletapp.mywalletapp.data.base.GenericRepository;
import com.mistique.mywalletapp.mywalletapp.models.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionSqlRepository extends AbstractGenericRepository<Transaction> implements GenericRepository<Transaction> {
    private SessionFactory factory;

    @Autowired
    public TransactionSqlRepository(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Transaction> listAll() {
        List<Transaction> transactions = new ArrayList<>();
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            transactions = session.createQuery("FROM Transaction").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return transactions;
    }

    @Override
    public Transaction findById(int id) {
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            session.beginTransaction();
            transaction = session.get(Transaction.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return transaction;
    }

    @Override
    public void update(int id, Transaction entity) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Transaction transaction = session.get(Transaction.class, id);
            transaction = entity;
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Transaction transaction = session.get(Transaction.class, id);
            session.delete(transaction);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
