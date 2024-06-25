/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package contenedor;

import agentes.*;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alejo
 */
public class Contenedor {

    public void crearContenedor(String host, int port, String name) {
        jade.core.Runtime runtime = jade.core.Runtime.instance();

        Profile p = new ProfileImpl(host, port, name);
        AgentContainer contenedorPrincipal = runtime.createMainContainer(p);

        crearAgentes(contenedorPrincipal);

    }

    private void crearAgentes(AgentContainer contenedorPrincipal) {

        try {

            // El tercer null representa el conocimiento previo del agente, por ahí se le pasa
            // por ejemplo una red neuronal

            contenedorPrincipal.createNewAgent("AgenteANN", AgenteANN.class.getName(), new Object[]{"src/modelo/predecir.py", contenedorPrincipal}).start();
            contenedorPrincipal.createNewAgent("AgenteDatos", AgenteDatos.class.getName(), null).start();
            contenedorPrincipal.createNewAgent("AgenteNotificar", AgenteNotificar.class.getName(), null).start();

        } catch (StaleProxyException ex) {
            Logger.getLogger(Contenedor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
