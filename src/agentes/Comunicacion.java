/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agentes;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alejo
 */
public class Comunicacion {

    public static void msj(int tipoMSJ, Agent emisor, String receptor, String contenidoStr, Serializable contenidoObj, String conversationId) {
        // crear el mensaje
        ACLMessage acl = new ACLMessage(tipoMSJ);

        // Añadir a un receptor
        // Si quiero agregar un solo receptor utilizo el String de alias, si quiero más
        // receptores utilizo un array de alias de receptores
        AID receptorID = new AID();
        receptorID.setLocalName(receptor);
        acl.addReceiver(receptorID);

        // Setar emisor
        acl.setSender(emisor.getAID());

        //setear lenguaje
        acl.setLanguage(FIPANames.ContentLanguage.FIPA_SL);

        // Setear el contenido de la conversación
        //el contenido hay 2 tipos content y Object
        // solo se puede tener uno de ellos, para enviar dos veces lo controlamos con un if
        if (contenidoStr == null) {
            try {
                acl.setContentObject(contenidoObj);
            } catch (IOException ex) {
                Logger.getLogger(Comunicacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            acl.setContent(contenidoStr);

        }

        // establecer el id de la conversación
        acl.setConversationId(conversationId);

        //enviar mensaje
        emisor.send(acl);

    }
}
