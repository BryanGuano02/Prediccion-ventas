/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agentes;

import jade.core.*;
import jade.core.behaviours.Behaviour;
import jade.wrapper.AgentContainer;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.wrapper.StaleProxyException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alejo
 */
public class AgenteNotificar extends Agent {

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
            if (aclmsj.getConversationId().equals("AgenteANN-AgenteNotificar")) {
                String valorPredicho = (String) aclmsj.getContent();

                System.out.println("(AgenteNotificar) - El valor predicho es: " + valorPredicho);

                String aviso = "1";
                

                Comunicacion.msj(ACLMessage.REQUEST, getAgent(), "AgenteANN", aviso, null, "AgenteNotificar-AgenteANN");
                doDelete();
            }

        }

        @Override
        public boolean done() {
            return true;
        }

    }

}
