package com.serotonin.lida.balance;

import java.util.Map;

import edu.memphis.ccrg.lida.environment.EnvironmentImpl;
import edu.memphis.ccrg.lida.framework.tasks.FrameworkTaskImpl;

public class Environment extends EnvironmentImpl {
    private static final double GRAVITY = 0.00001;
    private static final double HALFPI = StrictMath.PI / 2;

    // Angle of pole lean where 0 is upright.
    private double poleAngle = -0.01;
    private double poleVelocity = 0;

    @Override
    public void init() {
        super.init();
        taskSpawner.addTask(new EnvironmentBackgroundTask());
    }

    private class EnvironmentBackgroundTask extends FrameworkTaskImpl {
        public EnvironmentBackgroundTask() {
            super(1 /* Run every tick */);
        }

        @Override
        protected void runThisFrameworkTask() {
            poleVelocity += StrictMath.sin(poleAngle) * GRAVITY;
            poleAngle += poleVelocity;
            if (poleAngle > HALFPI) {
                poleAngle = HALFPI;
                poleVelocity = 0;
            }
            else if (poleAngle < -HALFPI) {
                poleAngle = -HALFPI;
                poleVelocity = 0;
            }
        }
    }

    @Override
    public Object getState(Map<String, ?> params) {
        String attr = (String) params.get("attr");
        if ("angle".equals(attr))
            return poleAngle;
        if ("velocity".equals(attr))
            return poleVelocity;
        return null;
    }

    @Override
    public void processAction(Object o) {
        double impulse = 0;
        if ("algorithm.impulseRight".equals(o))
            impulse = 0.006;
        else if ("algorithm.impulseLeft".equals(o))
            impulse = -0.006;
        poleVelocity += impulse;
    }

    @Override
    public void resetState() {
        poleAngle = 0.01;
        poleVelocity = 0;
    }

    @Override
    public void decayModule(long ticks) {
        // no op
    }
}
