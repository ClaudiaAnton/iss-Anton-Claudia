package repo;
import domain.Agent;
import domain.Produs;
import events.EventType;
import observer.Observable;
import observer.Observer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class ProdusRepo implements Observable<EventType> {
    private SessionFactory sessionFactory;
    public ProdusRepo(SessionFactory props) {
        this.sessionFactory=props;

    }

    public List<Produs> getAll() {
        List<Produs> users = new ArrayList<>();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
               users = session.createQuery("from Produs as m", Produs.class).list();
                tx.commit();
                return users;
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select "+ex);
                if (tx != null)
                    tx.rollback();
            }
            return users;
        }
    }

    public void update(String denumire, long cantitate) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                Produs message = session.load( Produs.class, denumire );
                message.setCantitate( cantitate );
                tx.commit();
                notifyObservers(new EventType());
            } catch(RuntimeException ex){
                System.err.println("Eroare la update "+ex);
                if (tx!=null)
                    tx.rollback();
            }
        }

    }

    public Produs getOne(String nume) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query<Produs> query = session.createQuery("select m from Produs as m where m.denumire=?1", Produs.class);
                query.setParameter(1, nume);
                Produs messages = query.uniqueResult();
                tx.commit();
                return messages;
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select " + ex);
                if (tx != null)
                    tx.rollback();
            }
            return null;

        }
    }

    public void add(Produs a){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(a);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la inserare "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    private List<Observer<EventType>> observers=new ArrayList<>();
    @Override
    public void addObserver(Observer<EventType> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<EventType> e) {

    }

    @Override
    public void notifyObservers(EventType t) {
        observers.stream().forEach(x->x.update(t));
    }
}

