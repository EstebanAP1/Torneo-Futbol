package Modelo;

import java.util.LinkedList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class JugadoresDAO {
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
    public void addJugador(Jugadores jd) {
	iniciarOperacion();
	session.save(jd);
	terminarOperacion();
    }
    public Jugadores searchJugador (int cedula) {
	iniciarOperacion();
	Jugadores jd = (Jugadores)session.get(Jugadores.class, cedula);
	terminarOperacion();
	return jd;
    }
    public void updateJugador(Jugadores jd) {
	iniciarOperacion();
	session.update(jd);
	terminarOperacion();
    }
    public void deleteJugador(Jugadores jd) {
	iniciarOperacion();
	session.delete(jd);
	terminarOperacion();
    }
    public List<Jugadores> listJugadores() {
	List lista = new LinkedList();
	iniciarOperacion();
	lista = session.createQuery("from Jugadores").list();
	terminarOperacion();
	return lista;
    }
}
