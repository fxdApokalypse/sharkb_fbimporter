package htw.shark.nowdiscover.container;

import htw.shark.nowdiscover.userutils.*;
import net.sharkfw.knowledgeBase.*;
import net.sharkfw.knowledgeBase.inmemory.*;

public class ContextUtil {
	private KnowledgeGoddess kg;
	private ContextCoordinates cc;
	private ContextPoint kbcp;
	private Information kbInfo;

	public ContextUtil(SemanticTag topic, PeerSemanticTag originator,
			PeerSemanticTag peer, SemanticTag direction)
			throws SharkKBException {
		cc = kg.getKB().createContextCoordinates(topic, originator, peer, null,
				null, null, SharkCS.DIRECTION_OUT);
		kbcp = kg.getKB().createContextPoint(cc);
	}

	public InMemoSharkKB getKnowledgeBase() {
		return kg.getKB();
	}

}
