package Control;

import Modelo.Equipos;
import Modelo.Jugadores;
import Modelo.Partidos;
import Modelo.EquiposDAO;
import Modelo.JugadoresDAO;
import Modelo.PartidosDAO;
import Vista.EquipoVista;
import Vista.JugadorVista;
import Vista.PartidoVista;
import Vista.finVista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class GeneralControl implements ActionListener {

    EquiposDAO eqD = new EquiposDAO();
    EquipoVista ev = new EquipoVista();

    JugadoresDAO jdD = new JugadoresDAO();
    JugadorVista jv = new JugadorVista();

    PartidosDAO pdD = new PartidosDAO();
    PartidoVista pv = new PartidoVista();

    finVista fv = new finVista();

    public GeneralControl(EquiposDAO eqD, EquipoVista ev, JugadoresDAO jdD, JugadorVista jv, PartidosDAO pdD, PartidoVista pv, finVista fv) {
	this.eqD = eqD;
	this.ev = ev;

	this.jdD = jdD;
	this.jv = jv;

	this.pdD = pdD;
	this.pv = pv;

	this.fv = fv;

	ev.addTeamBtn.addActionListener(this);
	ev.modTeamBtn.addActionListener(this);
	ev.delTeamBtn.addActionListener(this);
	ev.playerBtn.addActionListener(this);
	ev.btnManejoP.addActionListener(this);

	jv.addPlayerBtn.addActionListener(this);
	jv.backBtn.addActionListener(this);

	pv.btnGoles.addActionListener(this);
	pv.btnAmarillas.addActionListener(this);
	pv.btnRojas.addActionListener(this);
	pv.btnBackP1.addActionListener(this);
	pv.btnBackP2.addActionListener(this);
	pv.btnBackP3.addActionListener(this);
	pv.btnCierreP.addActionListener(this);
	pv.btnCierreTorneo.addActionListener(this);

	fv.btnDeleteALL.addActionListener(this);
	fv.btnDeleteALL1.addActionListener(this);
	fv.btnBuscar.addActionListener(this);
	fv.btnBuscar2.addActionListener(this);
    }

    public boolean validarCodigoE(int codigo) {
	boolean sw = false;
	List<Equipos> equipos = eqD.listEquipos();
	for (Equipos eq : equipos) {
	    if (eq.getCodigoEquipo() == codigo) {
		sw = true;
	    }
	}
	return sw;
    }

    public boolean validarNombreE(String nombre) {
	boolean sw = false;
	List<Equipos> equipos = eqD.listEquipos();
	for (Equipos eq : equipos) {
	    if (nombre.equals(eq.getNombreEquipo())) {
		sw = true;
	    }
	}
	return sw;
    }

    public int teamCount() {
	int teams = 0;
	List<Equipos> equipos = eqD.listEquipos();
	for (Equipos eq : equipos) {
	    teams++;
	}
	return teams;
    }

    public boolean validarCedulaJ(int cedula) {
	boolean sw = false;
	List<Jugadores> jugadores = jdD.listJugadores();
	for (Jugadores jd : jugadores) {
	    if (jd.getCedula() == cedula) {
		sw = true;
	    }
	}
	return sw;
    }

    public boolean validarEquipoJ(String nombreE, int cedulaJ) {
	boolean sw = false;
	List<Jugadores> jugadores = jdD.listJugadores();
	for (Jugadores jd : jugadores) {
	    if (nombreE.equals(jd.getEquipoJugador()) && cedulaJ == jd.getCedula()) {
		sw = true;
	    }
	}
	return sw;
    }

    public boolean validarEquipoJ(String nombreE) {
	boolean sw = false;
	List<Jugadores> jugadores = jdD.listJugadores();
	for (Jugadores jd : jugadores) {
	    if (nombreE.equals(jd.getEquipoJugador())) {
		sw = true;
	    }
	}
	return sw;
    }

    public boolean validarCodigoP(int codigo) {
	boolean sw = false;
	List<Partidos> partidos = pdD.listPartidos();
	for (Partidos pd : partidos) {
	    if (codigo == pd.getCodigoPartido()) {
		sw = true;
	    }
	}
	return sw;
    }

    public int matchCount() {
	int matches = 0;
	List<Partidos> partidos = pdD.listPartidos();
	for (Partidos pd : partidos) {
	    matches++;
	}
	return matches;
    }

    public int finishedMatchesCount() {
	int matches = 0;
	List<Partidos> partidos = pdD.listPartidos();
	for (Partidos pd : partidos) {
	    if (pd.getCierre() == 1) {
		matches++;
	    }
	}
	return matches;
    }

    public void addEquipo(String nombre, String pais, String codigo) {
	if (this.teamCount() < 4) {
	    if (codigo.length() != 5) {
		JOptionPane.showMessageDialog(null, "El código debe ser de 5 digitos");
	    } else {
		if (this.validarCodigoE(Integer.parseInt(codigo)) || this.validarNombreE(nombre)) {
		    JOptionPane.showMessageDialog(null, "Ya existe un equipo con ese nombre o código");
		} else {
		    Equipos eq = new Equipos(Integer.parseInt(codigo), nombre, pais, 0, 0);

		    eqD.addEquipo(eq);

		    ev.textTeamName.setText("");
		    ev.textTeamCountry.setText("");
		    ev.textTeamCode.setText("");
		    if (this.teamCount() == 4) {
			this.addPartidos();
		    }
		}
	    }
	} else {
	    JOptionPane.showMessageDialog(null, "Puedes agregar un máximo de 4 equipos");
	}
    }

    public void updateEquipo(String newNombre, String codigo, String newPais) {
	if (this.validarCodigoE(Integer.parseInt(codigo))) {
	    Equipos eq = eqD.searchEquipo(Integer.parseInt(codigo));

	    List<Jugadores> jugadores = jdD.listJugadores();
	    for (Jugadores jd : jugadores) {
		if (jd.getEquipoJugador().equals(eq.getNombreEquipo())) {
		    jd.setEquipoJugador(newNombre);
		    jdD.updateJugador(jd);
		}
	    }

	    List<Partidos> partidos = pdD.listPartidos();
	    for (Partidos pd : partidos) {
		if (pd.getManejoEquipo1().equals(eq.getNombreEquipo())) {
		    pd.setManejoEquipo1(newNombre);
		    pdD.updatePartido(pd);
		}
		if (pd.getManejoEquipo2().equals(eq.getNombreEquipo())) {
		    pd.setManejoEquipo2(newNombre);
		    pdD.updatePartido(pd);
		}
	    }

	    eq.setNombreEquipo(newNombre);
	    eq.setPaisEquipo(newPais);

	    eqD.updateEquipo(eq);

	    ev.textModID.setText("");
	    ev.textModNewName.setText("");
	    ev.textModNewCountry.setText("");
	} else {
	    JOptionPane.showMessageDialog(null, "El codigo que ingresaste no existe");
	}

    }

    public void deleteEquipo(String id) {
	if (this.validarCodigoE(Integer.parseInt(id))) {
	    Equipos eq = eqD.searchEquipo(Integer.parseInt(id));

	    if (this.validarEquipoJ(eq.getNombreEquipo())) {
		JOptionPane.showMessageDialog(null, "No puedes eliminar un equipo que tenga un partido asignado");
	    } else {
		eqD.deleteEquipo(eq);

		ev.textDel.setText("");
	    }
	} else {
	    JOptionPane.showMessageDialog(null, "El codigo que ingresaste no existe");
	}
    }

    public void listEquipo() {
	DefaultTableModel lm = (DefaultTableModel) ev.tablaEquipos.getModel();
	DefaultTableModel lm1 = (DefaultTableModel) ev.tablaEquipos1.getModel();
	DefaultTableModel lm2 = (DefaultTableModel) ev.tablaEquipos2.getModel();
	List<Equipos> equipos = eqD.listEquipos();
	for (Equipos eq : equipos) {
	    String[] arreglo = new String[3];
	    arreglo[0] = eq.getNombreEquipo();
	    arreglo[1] = eq.getPaisEquipo();
	    arreglo[2] = Integer.toString(eq.getCodigoEquipo());

	    lm.addRow(arreglo);
	    lm1.addRow(arreglo);
	    lm2.addRow(arreglo);
	}
    }

    public void limpiarTablaEquipo() {
	DefaultTableModel lm = (DefaultTableModel) ev.tablaEquipos.getModel();
	DefaultTableModel lm1 = (DefaultTableModel) ev.tablaEquipos1.getModel();
	DefaultTableModel lm2 = (DefaultTableModel) ev.tablaEquipos2.getModel();
	for (int i = 0; i < lm.getRowCount(); i++) {
	    lm.removeRow(i);
	    i -= 1;
	}
	for (int i = 0; i < lm1.getRowCount(); i++) {
	    lm1.removeRow(i);
	    i -= 1;
	}
	for (int i = 0; i < lm2.getRowCount(); i++) {
	    lm2.removeRow(i);
	    i -= 1;
	}
    }

    public void addJugador(String nombre, int cedula, String equipo) {
	if (this.validarNombreE(equipo)) {
	    if (!this.validarCedulaJ(cedula)) {
		Jugadores jd = new Jugadores(cedula, nombre, equipo, 0, 0, 0);

		jdD.addJugador(jd);

		jv.textPlayerName.setText("");
		jv.textPlayerID.setText("");
		jv.textPlayerTeam.setText("");
	    } else {
		JOptionPane.showMessageDialog(null, "Ya existe un jugador con esa cédula");
	    }
	} else {
	    JOptionPane.showMessageDialog(null, "Ese equipo no existe");
	}

    }

    public void listJugador() {
	DefaultTableModel te = (DefaultTableModel) jv.tablaEquiposJ.getModel();
	DefaultTableModel lm = (DefaultTableModel) jv.tablaJugadores.getModel();
	List<Jugadores> jugadores = jdD.listJugadores();
	List<Equipos> equipos = eqD.listEquipos();
	for (Equipos eq : equipos) {
	    String[] arreglo = new String[1];
	    arreglo[0] = eq.getNombreEquipo();

	    te.addRow(arreglo);
	}
	for (Jugadores jd : jugadores) {
	    String[] arreglo = new String[3];
	    arreglo[0] = jd.getNombreJugador();
	    arreglo[1] = Integer.toString(jd.getCedula());
	    arreglo[2] = jd.getEquipoJugador();

	    lm.addRow(arreglo);
	}
    }

    public void limpiarTablaJugador() {
	DefaultTableModel lm = (DefaultTableModel) jv.tablaEquiposJ.getModel();
	DefaultTableModel lm1 = (DefaultTableModel) jv.tablaJugadores.getModel();
	for (int i = 0; i < lm.getRowCount(); i++) {
	    lm.removeRow(i);
	    i -= 1;
	}
	for (int i = 0; i < lm1.getRowCount(); i++) {
	    lm1.removeRow(i);
	    i -= 1;
	}
    }

    public void addPartidos() {
	ArrayList arreglo = new ArrayList();
	boolean sw;
	List<Equipos> equipos = eqD.listEquipos();
	for (Equipos eq : equipos) {
	    arreglo.add(eq.getNombreEquipo());
	}
	if (this.matchCount() < 6) {
	    Partidos pd = new Partidos(arreglo.get(0).toString(), arreglo.get(1).toString(), 0, 0, 0, 0, 0, 0, 0);
	    pdD.addPartido(pd);
	    pd = new Partidos(arreglo.get(3).toString(), arreglo.get(1).toString(), 0, 0, 0, 0, 0, 0, 0);
	    pdD.addPartido(pd);
	    pd = new Partidos(arreglo.get(0).toString(), arreglo.get(2).toString(), 0, 0, 0, 0, 0, 0, 0);
	    pdD.addPartido(pd);
	    pd = new Partidos(arreglo.get(1).toString(), arreglo.get(2).toString(), 0, 0, 0, 0, 0, 0, 0);
	    pdD.addPartido(pd);
	    pd = new Partidos(arreglo.get(3).toString(), arreglo.get(0).toString(), 0, 0, 0, 0, 0, 0, 0);
	    pdD.addPartido(pd);
	    pd = new Partidos(arreglo.get(2).toString(), arreglo.get(3).toString(), 0, 0, 0, 0, 0, 0, 0);
	    pdD.addPartido(pd);
	    JOptionPane.showMessageDialog(null, "Se han creado los partidos");
	}
    }

    public void listPartido() {
	ArrayList array = new ArrayList();
	DefaultTableModel lm = (DefaultTableModel) pv.tablaEquiposP1.getModel();
	List<Partidos> partidos = pdD.listPartidos();
	for (Partidos pd : partidos) {
	    String[] arreglo = new String[7];
	    if (pd.getCierre() == 0) {
		arreglo[0] = pd.getManejoEquipo1();
		array.add(pd.getManejoEquipo1());
		arreglo[1] = pd.getManejoEquipo2();
		array.add(pd.getManejoEquipo2());
		arreglo[2] = Integer.toString(pd.getNumGolesEquipo1());
		arreglo[3] = Integer.toString(pd.getNumGolesEquipo2());
		arreglo[4] = Integer.toString(pd.getCodigoPartido());
		lm.addRow(arreglo);
	    }
	}
	DefaultTableModel lm1 = (DefaultTableModel) pv.tablaJugadoresP1.getModel();
	List<Jugadores> jugadores = jdD.listJugadores();
	for (Jugadores jd : jugadores) {
	    String[] arreglo = new String[4];
	    if (array.contains(jd.getEquipoJugador())) {
		arreglo[0] = jd.getNombreJugador();
		arreglo[1] = Integer.toString(jd.getCedula());
		arreglo[2] = jd.getEquipoJugador();
		arreglo[3] = Integer.toString(jd.getNumGoles());
		lm1.addRow(arreglo);
	    }
	}

	DefaultTableModel lm2 = (DefaultTableModel) pv.tablaEquiposP2.getModel();
	for (Partidos pd : partidos) {
	    String[] arreglo = new String[7];
	    if (pd.getCierre() == 0) {
		arreglo[0] = pd.getManejoEquipo1();
		arreglo[1] = pd.getManejoEquipo2();
		arreglo[2] = Integer.toString(pd.getNumAmarillasEquipo1());
		arreglo[3] = Integer.toString(pd.getNumAmarillasEquipo2());
		arreglo[4] = Integer.toString(pd.getCodigoPartido());
		lm2.addRow(arreglo);
	    }
	}
	DefaultTableModel lm3 = (DefaultTableModel) pv.tablaJugadoresP2.getModel();
	jugadores = jdD.listJugadores();
	for (Jugadores jd : jugadores) {
	    String[] arreglo = new String[4];
	    if (array.contains(jd.getEquipoJugador())) {
		arreglo[0] = jd.getNombreJugador();
		arreglo[1] = Integer.toString(jd.getCedula());
		arreglo[2] = jd.getEquipoJugador();
		arreglo[3] = Integer.toString(jd.getNumAmarillas());
		lm3.addRow(arreglo);
	    }
	}

	DefaultTableModel lm4 = (DefaultTableModel) pv.tablaEquiposP3.getModel();
	for (Partidos pd : partidos) {
	    String[] arreglo = new String[7];
	    if (pd.getCierre() == 0) {
		arreglo[0] = pd.getManejoEquipo1();
		arreglo[1] = pd.getManejoEquipo2();
		arreglo[2] = Integer.toString(pd.getNumRojasEquipo1());
		arreglo[3] = Integer.toString(pd.getNumRojasEquipo2());
		arreglo[4] = Integer.toString(pd.getCodigoPartido());
		lm4.addRow(arreglo);
	    }
	}
	DefaultTableModel lm5 = (DefaultTableModel) pv.tablaJugadoresP3.getModel();
	jugadores = jdD.listJugadores();
	for (Jugadores jd : jugadores) {
	    String[] arreglo = new String[4];
	    if (array.contains(jd.getEquipoJugador())) {
		arreglo[0] = jd.getNombreJugador();
		arreglo[1] = Integer.toString(jd.getCedula());
		arreglo[2] = jd.getEquipoJugador();
		arreglo[3] = Integer.toString(jd.getNumRojas());
		lm5.addRow(arreglo);
	    }
	}
	DefaultTableModel lm6 = (DefaultTableModel) pv.tablaCierre.getModel();
	for (Partidos pd : partidos) {
	    String[] arreglo = new String[9];
	    if (pd.getCierre() == 0) {
		arreglo[0] = pd.getManejoEquipo1();
		arreglo[1] = pd.getManejoEquipo2();
		arreglo[2] = Integer.toString(pd.getNumGolesEquipo1());
		arreglo[3] = Integer.toString(pd.getNumGolesEquipo2());
		arreglo[4] = Integer.toString(pd.getNumAmarillasEquipo1());
		arreglo[5] = Integer.toString(pd.getNumAmarillasEquipo2());
		arreglo[6] = Integer.toString(pd.getNumRojasEquipo1());
		arreglo[7] = Integer.toString(pd.getNumRojasEquipo2());
		arreglo[8] = Integer.toString(pd.getCodigoPartido());
		lm6.addRow(arreglo);
	    }
	}
    }

    public void limpiarTablaPartido() {
	DefaultTableModel lm = (DefaultTableModel) pv.tablaEquiposP1.getModel();
	DefaultTableModel lm1 = (DefaultTableModel) pv.tablaJugadoresP1.getModel();
	DefaultTableModel lm2 = (DefaultTableModel) pv.tablaEquiposP2.getModel();
	DefaultTableModel lm3 = (DefaultTableModel) pv.tablaJugadoresP2.getModel();
	DefaultTableModel lm4 = (DefaultTableModel) pv.tablaEquiposP3.getModel();
	DefaultTableModel lm5 = (DefaultTableModel) pv.tablaJugadoresP3.getModel();
	DefaultTableModel lm6 = (DefaultTableModel) pv.tablaCierre.getModel();
	for (int i = 0; i < lm.getRowCount(); i++) {
	    lm.removeRow(i);
	    i -= 1;
	}
	for (int i = 0; i < lm1.getRowCount(); i++) {
	    lm1.removeRow(i);
	    i -= 1;
	}
	for (int i = 0; i < lm2.getRowCount(); i++) {
	    lm2.removeRow(i);
	    i -= 1;
	}
	for (int i = 0; i < lm3.getRowCount(); i++) {
	    lm3.removeRow(i);
	    i -= 1;
	}
	for (int i = 0; i < lm4.getRowCount(); i++) {
	    lm4.removeRow(i);
	    i -= 1;
	}
	for (int i = 0; i < lm5.getRowCount(); i++) {
	    lm5.removeRow(i);
	    i -= 1;
	}
	for (int i = 0; i < lm6.getRowCount(); i++) {
	    lm6.removeRow(i);
	    i -= 1;
	}
    }

    public void addGoles(String nombreE, int cedulaJ, int numGoles, int codigo) {
	if (this.validarEquipoJ(nombreE, cedulaJ)) {
	    if (numGoles > 0) {
		if (this.validarCodigoP(codigo)) {
		    Jugadores jd = jdD.searchJugador(cedulaJ);
		    jd.setNumGoles(numGoles + jd.getNumGoles());
		    jdD.updateJugador(jd);

		    boolean sw = true;
		    List<Partidos> partidos = pdD.listPartidos();
		    Partidos pdU = new Partidos();
		    for (Partidos pd : partidos) {
			if (nombreE.equals(pd.getManejoEquipo1()) && codigo == pd.getCodigoPartido()) {
			    pdU = pd;
			    pdU.setNumGolesEquipo1(numGoles + pdU.getNumGolesEquipo1());
			    pdD.updatePartido(pdU);
			    sw = false;
			}
			if (nombreE.equals(pd.getManejoEquipo2()) && codigo == pd.getCodigoPartido()) {
			    pdU = pd;
			    pdU.setNumGolesEquipo2(numGoles + pdU.getNumGolesEquipo2());
			    pdD.updatePartido(pdU);
			    sw = false;
			}
		    }
		    if (sw) {
			JOptionPane.showMessageDialog(null, "El nombre del equipo no corresponde al partido ingresado");
		    } else {
			pv.textCedP1.setText("");
			pv.textCodP1.setText("");
			pv.textNombreEP1.setText("");
			pv.textNumGolesP1.setText("");
		    }
		} else {
		    JOptionPane.showMessageDialog(null, "No existe un partido con ese código");
		}
	    } else {
		JOptionPane.showMessageDialog(null, "El número de goles debe ser positivo");
	    }
	} else {
	    JOptionPane.showMessageDialog(null, "El jugador ingresado no está en el equipo ingresado");
	}
    }

    public void addAmarillas(String nombreE, int cedulaJ, int numAmarillas, int codigo) {
	if (this.validarEquipoJ(nombreE, cedulaJ)) {
	    if (numAmarillas > 0) {
		if (this.validarCodigoP(codigo)) {
		    Jugadores jd = jdD.searchJugador(cedulaJ);
		    jd.setNumAmarillas(numAmarillas + jd.getNumAmarillas());
		    jdD.updateJugador(jd);

		    boolean sw = true;
		    List<Partidos> partidos = pdD.listPartidos();
		    Partidos pdU = new Partidos();
		    for (Partidos pd : partidos) {
			if (nombreE.equals(pd.getManejoEquipo1()) && codigo == pd.getCodigoPartido()) {
			    pdU = pd;
			    pdU.setNumAmarillasEquipo1(numAmarillas + pdU.getNumAmarillasEquipo1());
			    pdD.updatePartido(pdU);
			    sw = false;
			}
			if (nombreE.equals(pd.getManejoEquipo2()) && codigo == pd.getCodigoPartido()) {
			    pdU = pd;
			    pdU.setNumAmarillasEquipo2(numAmarillas + pdU.getNumAmarillasEquipo2());
			    pdD.updatePartido(pdU);
			    sw = false;
			}
		    }
		    if (sw) {
			JOptionPane.showMessageDialog(null, "El nombre del equipo no corresponde al partido ingresado");
		    } else {
			pv.textCedP2.setText("");
			pv.textCodP2.setText("");
			pv.textNombreEP2.setText("");
			pv.textNumAmarillasP2.setText("");
		    }
		} else {
		    JOptionPane.showMessageDialog(null, "No existe un partido con ese código");
		}
	    } else {
		JOptionPane.showMessageDialog(null, "El número de goles debe ser positivo");
	    }
	} else {
	    JOptionPane.showMessageDialog(null, "El jugador ingresado no está en el equipo ingresado");
	}
    }

    public void addRojas(String nombreE, int cedulaJ, int numRojas, int codigo) {
	if (this.validarEquipoJ(nombreE, cedulaJ)) {
	    if (numRojas > 0) {
		if (this.validarCodigoP(codigo)) {
		    Jugadores jd = jdD.searchJugador(cedulaJ);
		    jd.setNumRojas(numRojas + jd.getNumRojas());
		    jdD.updateJugador(jd);

		    boolean sw = true;
		    List<Partidos> partidos = pdD.listPartidos();
		    Partidos pdU = new Partidos();
		    for (Partidos pd : partidos) {
			if (nombreE.equals(pd.getManejoEquipo1()) && codigo == pd.getCodigoPartido()) {
			    pdU = pd;
			    pdU.setNumRojasEquipo1(numRojas + pdU.getNumRojasEquipo1());
			    pdD.updatePartido(pdU);
			    sw = false;
			}
			if (nombreE.equals(pd.getManejoEquipo2()) && codigo == pd.getCodigoPartido()) {
			    pdU = pd;
			    pdU.setNumRojasEquipo2(numRojas + pdU.getNumRojasEquipo2());
			    pdD.updatePartido(pdU);
			    sw = false;
			}
		    }
		    if (sw) {
			JOptionPane.showMessageDialog(null, "El nombre del equipo no corresponde al partido ingresado");
		    } else {
			pv.textCedP3.setText("");
			pv.textCodP3.setText("");
			pv.textNombreEP3.setText("");
			pv.textNumRojasP3.setText("");
		    }
		} else {
		    JOptionPane.showMessageDialog(null, "No existe un partido con ese código");
		}
	    } else {
		JOptionPane.showMessageDialog(null, "El número de goles debe ser positivo");
	    }
	} else {
	    JOptionPane.showMessageDialog(null, "El jugador ingresado no está en el equipo ingresado");
	}
    }

    public void cerrarPartido(int codigo) {
	if (this.validarCodigoP(codigo)) {
	    Partidos pd = pdD.searchPartido(codigo);
	    if (pd.getNumGolesEquipo1() > pd.getNumGolesEquipo2()) {
		String winner = pd.getManejoEquipo1();
		List<Equipos> equipos = eqD.listEquipos();
		for (Equipos eq : equipos) {
		    if (eq.getNombreEquipo().equals(winner)) {
			eq.setPuntosEquipo(3 + eq.getPuntosEquipo());
			eq.setPartidosGanadosEquipo(1 + eq.getPartidosGanadosEquipo());
			eqD.updateEquipo(eq);
		    }
		}
	    } else if (pd.getNumGolesEquipo1() < pd.getNumGolesEquipo2()) {
		String winner = pd.getManejoEquipo2();
		List<Equipos> equipos = eqD.listEquipos();
		for (Equipos eq : equipos) {
		    if (eq.getNombreEquipo().equals(winner)) {
			eq.setPuntosEquipo(3 + eq.getPuntosEquipo());
			eq.setPartidosGanadosEquipo(1 + eq.getPartidosGanadosEquipo());
			eqD.updateEquipo(eq);
		    }
		}
	    } else if (pd.getNumGolesEquipo1() == pd.getNumGolesEquipo2()) {
		String emp1 = pd.getManejoEquipo1();
		String emp2 = pd.getManejoEquipo2();
		List<Equipos> equipos = eqD.listEquipos();
		for (Equipos eq : equipos) {
		    if (eq.getNombreEquipo().equals(emp1) || eq.getNombreEquipo().equals(emp2)) {
			eq.setPuntosEquipo(1 + eq.getPuntosEquipo());
			eqD.updateEquipo(eq);
		    }
		}
	    }
	    pd.setCierre(1);
	    pdD.updatePartido(pd);
	    pv.textCierre.setText("");
	} else {
	    JOptionPane.showMessageDialog(null, "El código ingresado no existe");
	}
    }

    public void cerrarTorneo() {
	if (this.finishedMatchesCount() == 6) {
	    pv.setVisible(false);
	    fv.setVisible(true);
	    listEquiposPuntos();
	    listEquiposPartidos();
	} else {
	    JOptionPane.showMessageDialog(null, "Para cerrar el torneo deben haber finalizado todos los partidos");
	}
    }

    public void listEquiposPuntos() {
	Equipos aux = new Equipos();
	ArrayList<Equipos> array = new ArrayList();
	DefaultTableModel lm = (DefaultTableModel) fv.tablaPuntos.getModel();
	List<Equipos> equipos = eqD.listEquipos();
	for (Equipos eq : equipos) {
	    array.add(eq);
	}
	for (int i = 0; i < array.size(); i++) {
	    for (int j = 0; j < array.size(); j++) {
		if (array.get(i).getPuntosEquipo() > array.get(j).getPuntosEquipo()) {
		    aux = array.get(i);
		    array.set(i, array.get(j));
		    array.set(j, aux);
		}
	    }
	}
	for (int i = 0; i < array.size(); i++) {
	    String[] arreglo = new String[5];
	    arreglo[0] = array.get(i).getNombreEquipo();
	    arreglo[1] = array.get(i).getPaisEquipo();
	    arreglo[2] = Integer.toString(array.get(i).getCodigoEquipo());
	    arreglo[3] = Integer.toString(array.get(i).getPuntosEquipo());
	    arreglo[4] = Integer.toString(array.get(i).getPartidosGanadosEquipo());

	    lm.addRow(arreglo);
	}
    }

    public void listEquiposPartidos() {
	Equipos aux = new Equipos();
	ArrayList<Equipos> array = new ArrayList();
	DefaultTableModel lm = (DefaultTableModel) fv.tablaPartidos.getModel();
	List<Equipos> equipos = eqD.listEquipos();
	for (Equipos eq : equipos) {
	    array.add(eq);
	}
	for (int i = 0; i < array.size(); i++) {
	    for (int j = 0; j < array.size(); j++) {
		if (array.get(i).getPartidosGanadosEquipo() > array.get(j).getPartidosGanadosEquipo()) {
		    aux = array.get(i);
		    array.set(i, array.get(j));
		    array.set(j, aux);
		}
	    }
	}
	for (int i = 0; i < array.size(); i++) {
	    String[] arreglo = new String[5];
	    arreglo[0] = array.get(i).getNombreEquipo();
	    arreglo[1] = array.get(i).getPaisEquipo();
	    arreglo[2] = Integer.toString(array.get(i).getCodigoEquipo());
	    arreglo[3] = Integer.toString(array.get(i).getPuntosEquipo());
	    arreglo[4] = Integer.toString(array.get(i).getPartidosGanadosEquipo());

	    lm.addRow(arreglo);
	}
    }

    public void buscarEquipos(String equipo1, String equipo2) {
	DefaultTableModel lm = (DefaultTableModel) fv.tablaFinPartidos1.getModel();
	DefaultTableModel lm2 = (DefaultTableModel) fv.tablaFinPartidos2.getModel();
	for (int i = 0; i < lm.getRowCount(); i++) {
	    lm.removeRow(i);
	    i -= 1;
	}
	for (int i = 0; i < lm2.getRowCount(); i++) {
	    lm2.removeRow(i);
	    i -= 1;
	}
	List<Partidos> partidos = pdD.listPartidos();
	for (Partidos pd : partidos) {
	    if ((pd.getManejoEquipo1().equals(equipo1) && pd.getManejoEquipo2().equals(equipo2)
		    || (pd.getManejoEquipo1().equals(equipo2) && pd.getManejoEquipo2().equals(equipo1)))) {
		String[] arreglo = new String[9];
		arreglo[0] = pd.getManejoEquipo1();
		arreglo[1] = pd.getManejoEquipo2();
		arreglo[2] = Integer.toString(pd.getNumGolesEquipo1());
		arreglo[3] = Integer.toString(pd.getNumGolesEquipo2());
		arreglo[4] = Integer.toString(pd.getNumAmarillasEquipo1());
		arreglo[5] = Integer.toString(pd.getNumAmarillasEquipo2());
		arreglo[6] = Integer.toString(pd.getNumRojasEquipo1());
		arreglo[7] = Integer.toString(pd.getNumRojasEquipo2());
		arreglo[8] = Integer.toString(pd.getCodigoPartido());
		lm.addRow(arreglo);
		lm2.addRow(arreglo);
	    }
	}
    }
    
    public void deleteALL() {
	List<Equipos> equipos = eqD.listEquipos();
	for (Equipos eq : equipos) {
	    eqD.deleteEquipo(eq);
	}
	List<Jugadores> jugadores = jdD.listJugadores();
	for (Jugadores jd : jugadores) {
	    jdD.deleteJugador(jd);
	}
	List<Partidos> partidos = pdD.listPartidos();
	for (Partidos pd : partidos) {
	    pdD.deletePartido(pd);
	}
	fv.setVisible(false);
	ev.setVisible(true);
	this.limpiarTablaEquipo();
	this.listEquipo();
	JOptionPane.showMessageDialog(null, "Se eliminaron todos los datos");
    }

    public void actionPerformed(ActionEvent e) {
	if (e.getSource() == ev.addTeamBtn) {
	    addEquipo(ev.textTeamName.getText(),
		    ev.textTeamCountry.getText(),
		    ev.textTeamCode.getText());

	    limpiarTablaEquipo();
	    listEquipo();
	}
	if (e.getSource() == ev.modTeamBtn) {
	    updateEquipo(ev.textModNewName.getText(),
		    ev.textModID.getText(),
		    ev.textModNewCountry.getText());

	    limpiarTablaEquipo();
	    listEquipo();
	}
	if (e.getSource() == ev.delTeamBtn) {
	    deleteEquipo(ev.textDel.getText());

	    limpiarTablaEquipo();
	    listEquipo();
	}
	if (e.getSource() == ev.playerBtn) {
	    ev.setVisible(false);
	    jv.setVisible(true);

	    limpiarTablaJugador();
	    listJugador();
	}
	if (e.getSource() == ev.btnManejoP) {
	    ev.setVisible(false);
	    pv.setVisible(true);

	    limpiarTablaPartido();
	    listPartido();
	}

	if (e.getSource() == jv.addPlayerBtn) {
	    addJugador(jv.textPlayerName.getText(),
		    Integer.parseInt(jv.textPlayerID.getText()),
		    jv.textPlayerTeam.getText());

	    limpiarTablaJugador();
	    listJugador();
	}
	if (e.getSource() == jv.backBtn) {
	    jv.setVisible(false);
	    ev.setVisible(true);
	}

	if (e.getSource() == pv.btnBackP1) {
	    pv.setVisible(false);
	    ev.setVisible(true);
	}
	if (e.getSource() == pv.btnBackP2) {
	    pv.setVisible(false);
	    ev.setVisible(true);
	}
	if (e.getSource() == pv.btnBackP3) {
	    pv.setVisible(false);
	    ev.setVisible(true);
	}
	if (e.getSource() == pv.btnGoles) {
	    addGoles(pv.textNombreEP1.getText(),
		    Integer.parseInt(pv.textCedP1.getText()),
		    Integer.parseInt(pv.textNumGolesP1.getText()),
		    Integer.parseInt(pv.textCodP1.getText()));

	    limpiarTablaPartido();
	    listPartido();
	}
	if (e.getSource() == pv.btnAmarillas) {
	    addAmarillas(pv.textNombreEP2.getText(),
		    Integer.parseInt(pv.textCedP2.getText()),
		    Integer.parseInt(pv.textNumAmarillasP2.getText()),
		    Integer.parseInt(pv.textCodP2.getText()));

	    limpiarTablaPartido();
	    listPartido();
	}
	if (e.getSource() == pv.btnRojas) {
	    addRojas(pv.textNombreEP3.getText(),
		    Integer.parseInt(pv.textCedP3.getText()),
		    Integer.parseInt(pv.textNumRojasP3.getText()),
		    Integer.parseInt(pv.textCodP3.getText()));

	    limpiarTablaPartido();
	    listPartido();
	}
	if (e.getSource() == pv.btnCierreP) {
	    cerrarPartido(Integer.parseInt(pv.textCierre.getText()));

	    limpiarTablaPartido();
	    listPartido();
	}
	if (e.getSource() == pv.btnCierreTorneo) {
	    cerrarTorneo();
	}
	
	if (e.getSource() == fv.btnBuscar) {
	    buscarEquipos(fv.textFin1E1.getText(), fv.textFin1E2.getText());
	}
	if (e.getSource() == fv.btnBuscar2) {
	    buscarEquipos(fv.textFin2E1.getText(), fv.textFin2E2.getText());
	}
	if (e.getSource() == fv.btnDeleteALL) {
	    deleteALL();
	}
	if (e.getSource() == fv.btnDeleteALL1) {
	    deleteALL();
	}
    }
}
