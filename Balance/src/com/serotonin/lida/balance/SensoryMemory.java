package com.serotonin.lida.balance;

import java.util.HashMap;
import java.util.Map;

import edu.memphis.ccrg.lida.sensorymemory.SensoryMemoryImpl;

public class SensoryMemory extends SensoryMemoryImpl {
    private final Map<String, String> angleParam = new HashMap<String, String>();
    private final Map<String, String> velocityParam = new HashMap<String, String>();

    private volatile double angle;
    private volatile double velocity;

    @Override
    public void init() {
        super.init();
        angleParam.put("attr", "angle");
        velocityParam.put("attr", "velocity");
    }

    @Override
    public void runSensors() {
        angle = (Double) environment.getState(angleParam);
        velocity = (Double) environment.getState(velocityParam);
    }

    @Override
    public Object getSensoryContent(String modality, Map<String, Object> params) {
        String attr = (String) params.get("attr");
        if ("angle".equals(attr))
            return angle;
        if ("velocity".equals(attr))
            return velocity;
        return null;
    }

    @Override
    public Object getModuleContent(Object... os) {
        return "a=" + angle + ", v=" + velocity;
    }

    @Override
    public void decayModule(long l) {
        //System.out.println("SensoryMemory.decayModule: " + l);
    }
}
