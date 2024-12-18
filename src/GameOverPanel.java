import java.awt.*;
import javax.swing.*;

public class GameOverPanel extends JPanel{
    public GameOverPanel(Game owner){
        this.owner = owner;
        isSaved = false;
        setLayout(null);
        setSize(1024, 1024);
        setVisible(true);
        
        //현재 플레이어 점수
        score = owner.getState().getScore();

        //랭킹 계산
        ranking = owner.getResources().getRanking(score);

        //이름 입력을 위한 텍스트 필드 추가
        JTextField nameTextField = new JTextField();
        nameTextField.setOpaque(false);
        nameTextField.setBorder(null);
        nameTextField.setSize(230, 50);
        nameTextField.setLocation(402, 605);
        nameTextField.setFont(new Font("맑은고딕", Font.BOLD, 25));
        nameTextField.setText("Enter your name.");

        nameTextField.addActionListener(e->{
            if(!isSaved){
                isSaved = true;
            name = ((JTextField)(e.getSource())).getText();
            owner.getResources().addScore(ranking - 1, new Score(name, score));
            }
            nameTextField.setText("");
        });

        //TitlePanel로 돌아가는 버튼
        JButton homeButton = new JButton();
        Image homeButtonImage = owner.getResources().getImageIcon("gameover_home.png").getImage().getScaledInstance(182, 65, Image.SCALE_SMOOTH);
        homeButton.setSize(182, 65);
        homeButton.setIcon(new ImageIcon(homeButtonImage));
        homeButtonImage = owner.getResources().getImageIcon("gameover_home_pressed.png").getImage().getScaledInstance(182, 65, Image.SCALE_SMOOTH);
        homeButton.setPressedIcon(new ImageIcon(homeButtonImage));
        homeButton.setLocation(320, 702);
        homeButton.setBorderPainted(false);
        homeButton.setFocusPainted(false);
        homeButton.setContentAreaFilled(false);
        homeButton.addActionListener(e -> {
            this.owner.getState().initializeState();
            this.owner.getResources().createMoles();
            this.owner.getMainWindow().changePanel(this, new TitlePanel(owner));
        });

        //GamePanel로 돌아가는 버튼
        JButton retryButton = new JButton();
        Image retryButtonImage = owner.getResources().getImageIcon("gameover_retry.png").getImage().getScaledInstance(182, 65, Image.SCALE_SMOOTH);
        retryButton.setSize(182, 65);
        retryButton.setIcon(new ImageIcon(retryButtonImage));
        retryButtonImage = owner.getResources().getImageIcon("gameover_retry_pressed.png").getImage().getScaledInstance(182, 65, Image.SCALE_SMOOTH);
        retryButton.setPressedIcon(new ImageIcon(retryButtonImage));
        retryButton.setLocation(522, 702);
        retryButton.setBorderPainted(false);
        retryButton.setFocusPainted(false);
        retryButton.setContentAreaFilled(false);
        retryButton.addActionListener(e -> {
            //플레이어 상태 및 두더지 초기화 후 게임 화면으로 전환
            this.owner.getState().initializeState();
            this.owner.getResources().createMoles();
            this.owner.getMainWindow().changePanel(this, new GamePanel(owner));
        });

        add(nameTextField);
        add(homeButton);
        add(retryButton);
    }

    //배경 이미지와 점수를 그리는 메서드
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image background = owner.getResources().getImageIcon("background_gameover.png").getImage();
        g.drawImage(background, 0, 0, 1024, 1024, null);
        Image gameoverImage = owner.getResources().getImageIcon("gameover.png").getImage();
        g.drawImage(gameoverImage, 272, 252, 480, 520, null);
        g.setFont(new Font("맑은고딕", Font.BOLD, 30));
        g.setColor(Color.WHITE);
        g.drawString("" + score, 495, 440);
        g.drawString("" + ranking, 505, 540);

        if(isSaved){
            Image checkMark = owner.getResources().getImageIcon("check_mark.png").getImage();
            g.drawImage(checkMark, 600, 610, 40, 40, null);
        }
    }

    //플레이어 점수 저장 여부
    private boolean isSaved;
    //플레이어 이름
    private String name;
    //플레이어 점수
    private int score;
    //플레이어 순위
    private int ranking;

    private Game owner;
}
