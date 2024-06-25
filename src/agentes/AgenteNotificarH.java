/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agentes;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alejo
 */
public class AgenteNotificarH extends Agent {

    AgentContainer contenedorPrincipal;
    int alias;

    @Override
    protected void setup() {
        addBehaviour(new Comportamiento());
    }

    @Override
    protected void takeDown() {
        super.takeDown();
    }

    class Comportamiento extends Behaviour {
        @Override
        public void action() {
            ACLMessage aclmsj = blockingReceive();

            contenedorPrincipal = (AgentContainer) getArguments()[0];
            alias = ((int) getArguments()[1]);

            String valorPredicho = (String) aclmsj.getContent();

            System.out.println("(AgenteNotificarH: " + alias + ") - El valor predicho es: " + valorPredicho);

            String aviso = "1";

            Comunicacion.msj(ACLMessage.REQUEST, getAgent(), "AgenteANN", aviso, null, "AgenteNotificarH" + alias + "-AgenteANN");
            doDelete();

        }

        @Override
        public boolean done() {
            return true;
        }

    }

}
