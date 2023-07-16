package Modelo;

import java.util.LinkedList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class EquiposDAO {
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
    public void addEquipo(Equipos eq) {
	iniciarOperacion();
	session.save(eq);
	terminarOperacion();
    }
    public Equipos searchEquipo (int id) {
	iniciarOperacion();
	Equipos eq = (Equipos)session.get(Equipos.class, id);
	terminarOperacion();
	return eq;
    }
    public void updateEquipo(Equipos eq) {
	iniciarOperacion();
	session.update(eq);
	terminarOperacion();
    }
    public void deleteEquipo(Equipos eq) {
	iniciarOperacion();
	session.delete(eq);
	terminarOperacion();
    }
    public List<Equipos> listEquipos() {
	List lista = new LinkedList();
	iniciarOperacion();
	lista = session.createQuery("from Equipos").list();
	terminarOperacion();
	return lista;
    }
}
