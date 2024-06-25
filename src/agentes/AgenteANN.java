/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agentes;

import jade.core.*;
import jade.core.behaviours.CyclicBehaviour;
import jade.wrapper.StaleProxyException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jade.wrapper.AgentContainer;
import jade.lang.acl.ACLMessage;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author alejo
 */
public class AgenteANN extends Agent {

    String bandera = "0";
    int alias = 1;
    String prediccion;

    @Override
    protected void setup() {
        addBehaviour(new Comportamiento());
    }

    @Override
    protected void takeDown() {
        super.takeDown(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    class Comportamiento extends CyclicBehaviour {

        @Override
        public void action() {

            ACLMessage aclmsj = blockingReceive();

            if (aclmsj.getConversationId().equals("AgenteDatos-AgenteANN")) {

                String datos = (String) aclmsj.getContent();
                String modelo = (String) getArguments()[0];

                Process p;
                List<String> cmdList = new ArrayList<>();
                cmdList.add("python");
                cmdList.add(modelo);
                cmdList.add(datos);

                
                try {
                    String cmd = String.join(" ", cmdList);

                    p = java.lang.Runtime.getRuntime().exec(cmd);

//                  BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));

                    prediccion = br.readLine();
                    
                    
                    if (bandera.equals("0")) {
                        Comunicacion.msj(ACLMessage.REQUEST, getAgent(), "AgenteNotificar", prediccion, null, "AgenteANN-AgenteNotificar");

                    } else {

                        Comunicacion.msj(ACLMessage.REQUEST, getAgent(), "AgenteNotificarH" + alias, prediccion, null, "AgenteANN-AgenteNotificar");

                    }

                    p.waitFor();
                    p.destroy();
                } catch (Exception e) {
                    System.err.println("Error ejecutando el script de Python: " + e.getMessage());
                }
            }

            if (aclmsj.getConversationId().equals("AgenteNotificar-AgenteANN")) {
                bandera = (String) aclmsj.getContent();
                if (bandera.equals("1")) {
                    try {
                        AgentContainer contenedorPrincipal = (AgentContainer) getArguments()[1];
                        contenedorPrincipal.createNewAgent("AgenteNotificarH1", AgenteNotificarH.class.getName(), new Object[]{contenedorPrincipal, 1}).start();
                    } catch (StaleProxyException ex) {
                        Logger.getLogger(AgenteANN.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if (aclmsj.getConversationId().equals("AgenteNotificarH" + alias + "-AgenteANN")) {
                alias++;
                String aviso = (String) aclmsj.getContent();
                if (aviso.equals("1")) {
                    try {
                        AgentContainer contenedorPrincipal = (AgentContainer) getArguments()[1];
                        contenedorPrincipal.createNewAgent("AgenteNotificarH" + alias, AgenteNotificarH.class.getName(), new Object[]{contenedorPrincipal, alias}).start();

                    } catch (StaleProxyException ex) {
                        Logger.getLogger(AgenteANN.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

        }

    }

}
