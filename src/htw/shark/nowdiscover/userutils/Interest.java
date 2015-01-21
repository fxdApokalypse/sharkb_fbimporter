/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharknowdiscover.htw.shark.nowdiscover.userutils;

import net.sharkfw.knowledgeBase.*;
import net.sharkfw.knowledgeBase.inmemory.*;

/**
 *
 * @author Jörn
 */
public class Interest implements Interest_Interface {

    ContextPoint cp;

    public Interest(SemanticTag topic, PeerSemanticTag user, String value) throws SharkKBException {
        SharkKB kb = ShopEngine.getKB();
        cp = kb.getContextPoint(kb.createContextCoordinates(topic, user, null, null, null, null, SharkCS.DIRECTION_INOUT));
    }

    public Interest(ContextPoint cp){
    this.cp = cp;
    
    }

    @Override
    public Interest getInterest() {
        return Interest.this ;
    }
    
    
}
