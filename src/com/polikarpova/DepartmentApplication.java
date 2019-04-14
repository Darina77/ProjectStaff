import ui.DepartmentWindow;

import java.awt.*;

public class DepartmentApplication {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                DepartmentWindow window = new DepartmentWindow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
