package BusinessLogic.services;

import java.util.List;

import BusinessLogic.entities.Pintura;
import DataAccess.IPinturaDAO;

public class PinturaService {

    private IPinturaDAO pinturaDAO;

    public PinturaService(IPinturaDAO pinturaDAO) {
        this.pinturaDAO = pinturaDAO;
    }

    public boolean agregarPintura(Pintura pintura) {
        // Validaciones de negocio
        if (pintura.getTitulo() == null || pintura.getTitulo().isEmpty()) {
            return false;
        }
        
        return pinturaDAO.insertarPintura(pintura);
    }

    public List<Pintura> obtenerTodasLasPinturas() {
        return pinturaDAO.obtenerTodasLasPinturas();
    }
}
