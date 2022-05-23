package repo;


import domain.Agent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.JdbcUtils;

import java.util.Properties;


public class AgentRepo {
    private SessionFactory sessionFactory;
    public AgentRepo(SessionFactory sessionFactory) {
        this.sessionFactory=sessionFactory;
    }

    public Agent findOneByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query<Agent> query = session.createQuery("select m from Agent as m where m.username=?1", Agent.class);
                query.setParameter(1, username);
                Agent messages = query.uniqueResult();
                System.out.println(messages.getUsername());
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
}
