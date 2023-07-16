package Modelo;

import java.util.LinkedList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class PartidosDAO {
    private Session session;

    private void iniciarOperacion() {
	SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
	session = sessionFactory.openSession();
	session.getTransaction().begin();
    }
    private void terminarOperacion() {
	session.getTransaction().commit();
	session.close();
    }
    public void addPartido(Partidos pd) {
	iniciarOperacion();
	session.save(pd);
	terminarOperacion();
    }
    public Partidos searchPartido (int id) {
	iniciarOperacion();
	Partidos pd = (Partidos)session.get(Partidos.class, id);
	terminarOperacion();
	return pd;
    }
    public void updatePartido(Partidos pd) {
	iniciarOperacion();
	session.update(pd);
	terminarOperacion();
    }
    public void deletePartido(Partidos pd) {
	iniciarOperacion();
	session.delete(pd);
	terminarOperacion();
    }
    public List<Partidos> listPartidos() {
	List lista = new LinkedList();
	iniciarOperacion();
	lista = session.createQuery("from Partidos").list();
	terminarOperacion();
	return lista;
    }
}
