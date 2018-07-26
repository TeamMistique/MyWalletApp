package com.mistique.mywalletapp.mywalletapp.data;

import com.mistique.mywalletapp.mywalletapp.data.base.AbstractGenericRepository;
import com.mistique.mywalletapp.mywalletapp.data.base.GenericRepository;
import com.mistique.mywalletapp.mywalletapp.models.Wallet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class WalletSqlRepository extends AbstractGenericRepository<Wallet> implements GenericRepository<Wallet> {
    private SessionFactory factory;

    @Autowired
    public WalletSqlRepository(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Wallet> listAll() {
        List<Wallet> wallets = new ArrayList<>();
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            wallets = session.createQuery("FROM Wallet").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return wallets;
    }

    @Override
    public Wallet findById(int id) {
        Wallet wallet = null;

        try (Session session = factory.openSession()) {
            session.beginTransaction();
            wallet = session.get(Wallet.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return wallet;
    }

    @Override
    public void update(int id, Wallet entity) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Wallet wallet = session.get(Wallet.class, id);
            wallet = entity;
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Wallet wallet = session.get(Wallet.class, id);
            session.delete(wallet);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
