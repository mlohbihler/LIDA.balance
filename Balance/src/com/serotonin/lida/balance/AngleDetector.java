package com.serotonin.lida.balance;

import java.util.HashMap;
import java.util.Map;

import edu.memphis.ccrg.lida.pam.PamLinkable;
import edu.memphis.ccrg.lida.pam.tasks.MultipleDetectionAlgorithm;

public class AngleDetector extends MultipleDetectionAlgorithm {
    private static final double FALLEN = 1.4;
    private static final double FALLING = 0.05;

    private PamLinkable upright;
    private PamLinkable fallingLeft;
    private PamLinkable fallingRight;
    private PamLinkable fallenLeft;
    private PamLinkable fallenRight;
    private final Map<String, Object> detectorParams = new HashMap<String, Object>();

    @Override
    public void init() {
        super.init();

        upright = pamNodeMap.get("upright");
        fallingLeft = pamNodeMap.get("fallingLeft");
        fallingRight = pamNodeMap.get("fallingRight");
        fallenLeft = pamNodeMap.get("fallenLeft");
        fallenRight = pamNodeMap.get("fallenRight");

        detectorParams.put("attr", "angle");
    }

    @Override
    public void detectLinkables() {
        double angle = (Double) sensoryMemory.getSensoryContent(null, detectorParams);

        if (angle < -FALLEN)
            pam.receiveExcitation(fallenLeft, 1);
        else if (angle > FALLEN)
            pam.receiveExcitation(fallenRight, 1);
        else if (angle < -FALLING)
            pam.receiveExcitation(fallingLeft, 1);
        else if (angle > FALLING)
            pam.receiveExcitation(fallingRight, 1);
        else
            pam.receiveExcitation(upright, 1);
    }
}
