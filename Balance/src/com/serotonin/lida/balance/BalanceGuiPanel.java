package com.serotonin.lida.balance;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import edu.memphis.ccrg.lida.environment.Environment;
import edu.memphis.ccrg.lida.framework.ModuleName;
import edu.memphis.ccrg.lida.framework.gui.panels.GuiPanelImpl;

public class BalanceGuiPanel extends GuiPanelImpl {
    private static final long serialVersionUID = 1L;
    private final Map<String, String> angleParam = new HashMap<String, String>();
    private Environment environment;

    public BalanceGuiPanel() {
        angleParam.put("attr", "angle");
    }

    @Override
    public void initPanel(String[] param) {
        super.initPanel(param);
        environment = (Environment) agent.getSubmodule(ModuleName.Environment);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Determine the size that fits a maximal 2x1 box.
        Dimension size = getSize();
        int width, height;
        if (size.height < size.width / 2) {
            height = size.height;
            width = height * 2;
        }
        else {
            width = (size.width / 2) * 2;
            height = width / 2;
        }

        if (height < 50) {
            // Minimum
            height = 50;
            width = 100;
        }

        // Use a transformation to center the drawing in the space?

        paintComponentImpl((Graphics2D) g, width, height, 5);
    }

    private void paintComponentImpl(Graphics2D g, int width, int height, int padding) {
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.lightGray);
        g.fillRect(padding, padding, width - padding * 2, height - padding * 2);

        // Determine the hinge point
        int hingeRadius = 10;
        int innerPadding = 5;
        int cx = width / 2;
        int cy = height - padding - hingeRadius - innerPadding;

        g.setColor(Color.blue);
        g.fillOval(cx - hingeRadius, cy - hingeRadius, hingeRadius * 2, hingeRadius * 2);

        int poleLength = cy - padding - innerPadding;
        double angle = (Double) environment.getState(angleParam);

        float dx = cx + (float) (StrictMath.sin(angle) * poleLength);
        float dy = cy - (float) (StrictMath.cos(angle) * poleLength);

        g.drawLine(cx, cy, (int) (dx + 0.5), (int) (dy + 0.5));
    }

    @Override
    public void refresh() {
        repaint();
    }
}
