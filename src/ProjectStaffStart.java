import View.ProjectStaffMain;

import javax.swing.*;
import java.awt.*;

public class ProjectStaffStart {
    private static final int FRAME_WIDTH = 890;
    private static final int FRAME_HEIGHT = 530;

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            ProjectStaffMain window = new ProjectStaffMain("Projects staff");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setSize(FRAME_WIDTH, FRAME_HEIGHT);
            window.setVisible(true);
        });
    }
}
