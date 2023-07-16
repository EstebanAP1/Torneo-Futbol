package main;

import Control.GeneralControl;
import Modelo.EquiposDAO;
import Vista.EquipoVista;
import Modelo.JugadoresDAO;
import Vista.JugadorVista;
import Modelo.PartidosDAO;
import Vista.PartidoVista;
import Vista.finVista;

public class main {

    public static void main(String[] args) {
        EquiposDAO eqD = new EquiposDAO();
	EquipoVista ev = new EquipoVista();
	JugadoresDAO jdD = new JugadoresDAO();
	JugadorVista jv = new JugadorVista();
	PartidosDAO pdD = new PartidosDAO();
	PartidoVista pv = new PartidoVista();
	finVista fv = new finVista();
	
	GeneralControl gCon = new GeneralControl(eqD, ev, jdD, jv, pdD, pv, fv);
	
        ev.setVisible(true);
	gCon.listEquipo();
    }
    
}
