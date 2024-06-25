/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agentes;

import jade.core.*;
import jade.wrapper.AgentContainer;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.wrapper.StaleProxyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alejo
 */
public class AgenteDatos extends Agent {

    @Override
    protected void setup() {
        addBehaviour(new Comportamiento());
    }

    @Override
    protected void takeDown() {
        super.takeDown();
    }

    class Comportamiento extends CyclicBehaviour {

        @Override
        public void action() {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Introduce los argumentos para el script Python separados por espacios (presiona Enter para terminar):");
            String datosUnidos = scanner.nextLine();
            //0 7 2472 3135 109 363 8 1 6800 8160000 0 0.969100 7

            Comunicacion.msj(ACLMessage.INFORM, getAgent(), "AgenteANN", datosUnidos, null, "AgenteDatos-AgenteANN");
            block(2000);
            System.out.println("Prediciendo...");
            block(5000);

        }

    }

}
