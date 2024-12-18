import java.awt.*;
import javax.swing.*;

public class LeaderBoardPanel extends JPanel{
    public LeaderBoardPanel(Game owner){
        this.owner = owner;
        setLayout(null);
        setSize(1024, 1024);
        setVisible(true);

        //GamePanel로 돌아가는 버튼
        JButton backButton = new JButton();
        Image backButtonImage = owner.getResources().getImageIcon("leader_board_back.png").getImage().getScaledInstance(190, 80, Image.SCALE_SMOOTH);
        backButton.setSize(200, 90);
        backButton.setIcon(new ImageIcon(backButtonImage));
        backButtonImage = owner.getResources().getImageIcon("leader_board_back_pressed.png").getImage().getScaledInstance(190, 80, Image.SCALE_SMOOTH);
        backButton.setPressedIcon(new ImageIcon(backButtonImage));
        backButton.setLocation(415, 770);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.addActionListener(e -> {
            owner.getMainWindow().changePanel((JPanel)(((JButton)e.getSource()).getParent()), new TitlePanel(owner));
        });

        add(backButton);

        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image background = owner.getResources().getImageIcon("main_title_darker.png").getImage();
        Image leaderBoardImage = owner.getResources().getImageIcon("leader_board.png").getImage();
        g.drawImage(background, 0, 0, 1024, 1024, null);
        g.drawImage(leaderBoardImage, 262, 162, 500, 700, null);

        int numRanker;
        //저장된 점수의 수가 5보다 큰 경우 출력되는 점수 수를 5로 설정
        if(owner.getResources().getNumScores() > 5){
            numRanker = 5;
        }
        //저장된 점수의 수가 5보다 작은 경우 출력되는 점수 수를 저장된 점수의 수로 설정
        else {
            numRanker = owner.getResources().getNumScores();
        }
        //점수 작성
        for(int i = 0; i < numRanker; i++){
            Score score = owner.getResources().getScore(i);
            g.setFont(new Font("맑은고딕", Font.BOLD, 25));
            g.setColor(Color.WHITE);
            g.drawString(score.getName(), 450, 355 + 90 * i);
            g.drawString(score.getScore() + "", 600, 355 + 90 * i);
        }
    }

    Game owner;
}
