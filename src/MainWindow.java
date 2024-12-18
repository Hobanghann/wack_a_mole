import javax.swing.*;
import java.awt.*;

//전체 윈도우
class MainWindow extends JFrame {
    public MainWindow(Game owner) {
        setSize(1024, 1024);
        setTitle("두더지 잡기!");
        Image titleImage = new ImageIcon("../assets/images/mole_out.png").getImage();
        setIconImage(titleImage);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //패널 remove/add를 통해 화면을 전환하는 메서드
    public <P1 extends JPanel, P2 extends JPanel>void changePanel(P1 before, P2 after){
        remove(before);
        add(after);
        revalidate();
    }

    private Game owner;
}