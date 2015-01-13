/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication16;

import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;

/**
 *
 * @author Jörn
 */
public class KnowledgeGoddess {
    //TODO eventuell listen von objekten
    private static InMemoSharkKB instance = null;
    private List<Tuple<String, Integer>> serverList = new ArrayList<Tuple<String, Integer>>();

    private KnowledgeGoddess() {
    }

    public static void initInstance() {
        if (instance == null) {
            instance = new KnowledgeGoddess();
        }

    }

    public void storeKnowledge(Thing thing) {

    }

    public void storeKnowledge(Profile profile) {

    }

    public void storeKnowledge(User user) {

    }

    public void storeKnowledge(Product product) {

    }

    public void storeKnowledge(Interest interest) {

    }

    public void storeKnowledge(Cathegory cathegory) {

    }



}
