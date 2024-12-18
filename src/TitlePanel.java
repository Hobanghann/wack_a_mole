import java.awt.*;
import javax.swing.*;

//타이틀 화면
public class TitlePanel extends JPanel{
    //Game object를 전달하여 Panel에서 Game object에 access할 수 있도록
    public TitlePanel(Game owner){
        this.owner = owner;
        setLayout(null);
        setSize(1024, 1024);
        setVisible(true);

        //시작 버튼
        JButton playButton = new JButton();
        Image playButtonImage = owner.getResources().getImageIcon("play_button.png").getImage().getScaledInstance(250, 90, Image.SCALE_SMOOTH);
        playButton.setSize(250, 90);
        playButton.setIcon(new ImageIcon(playButtonImage));
        playButtonImage = owner.getResources().getImageIcon("play_button_pressed.png").getImage().getScaledInstance(250, 90, Image.SCALE_SMOOTH);
        playButton.setPressedIcon(new ImageIcon(playButtonImage));
        playButton.setLocation(387, 550);
        playButton.setBorderPainted(false);
        playButton.setFocusPainted(false);
        playButton.setContentAreaFilled(false);

        //시작 버튼에 이벤트 리스너 추가 - 다음 화면으로 이동하도록
        playButton.addActionListener(e -> {
            owner.getMainWindow().changePanel((JPanel)(((JButton)e.getSource()).getParent()), new GamePanel(owner));
        });

        //리더보드 버튼
        JButton leaderBoardButton = new JButton();
        Image leaderBoardButtonImage = owner.getResources().getImageIcon("leader_board_button.png").getImage().getScaledInstance(250, 90, Image.SCALE_SMOOTH);
        leaderBoardButton.setSize(250, 90);
        leaderBoardButton.setIcon(new ImageIcon(leaderBoardButtonImage));
        leaderBoardButtonImage = owner.getResources().getImageIcon("leader_board_button_pressed.png").getImage().getScaledInstance(250, 90, Image.SCALE_SMOOTH);
        leaderBoardButton.setPressedIcon(new ImageIcon(leaderBoardButtonImage));
        leaderBoardButton.setLocation(387, 650);
        leaderBoardButton.setBorderPainted(false);
        leaderBoardButton.setFocusPainted(false);
        leaderBoardButton.setContentAreaFilled(false);

        //리더보드 버튼에 이벤트 리스너 추가 - 다음 화면으로 이동하도록
        leaderBoardButton.addActionListener(e -> {
            owner.getMainWindow().changePanel((JPanel)(((JButton)e.getSource()).getParent()), new LeaderBoardPanel(owner));
        });

        //설정 버튼
        JButton optionButton = new JButton();
        Image optionButtonImage = owner.getResources().getImageIcon("option_button.png").getImage().getScaledInstance(250, 90, Image.SCALE_SMOOTH);
        optionButton.setSize(250, 90);
        optionButton.setIcon(new ImageIcon(optionButtonImage));
        optionButtonImage = owner.getResources().getImageIcon("option_button_pressed.png").getImage().getScaledInstance(250, 90, Image.SCALE_SMOOTH);
        optionButton.setPressedIcon(new ImageIcon(optionButtonImage));
        optionButton.setLocation(387, 750);
        optionButton.setBorderPainted(false);
        optionButton.setFocusPainted(false);
        optionButton.setContentAreaFilled(false);

        optionButton.addActionListener(e -> {
            owner.getMainWindow().changePanel((JPanel)(((JButton)e.getSource()).getParent()), new OptionPanel(owner));
        });

        //종료버튼
        JButton exitButton = new JButton();
        Image exitButtonImage = owner.getResources().getImageIcon("exit_button.png").getImage().getScaledInstance(250, 90, Image.SCALE_SMOOTH);
        exitButton.setSize(250, 90);
        exitButton.setIcon(new ImageIcon(exitButtonImage));
        exitButtonImage = owner.getResources().getImageIcon("exit_button_pressed.png").getImage().getScaledInstance(250, 90, Image.SCALE_SMOOTH);
        exitButton.setPressedIcon(new ImageIcon(exitButtonImage));
        exitButton.setLocation(387, 850);
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);
        exitButton.setContentAreaFilled(false);

        //종료 버튼에 이벤트 리스너 추가 - 점수 내용 파일에 저장하고 프로그램 종료
        exitButton.addActionListener(e -> {
            owner.getResources().updateScoreFile();
            System.exit(0);
        });

        add(playButton);
        add(leaderBoardButton);
        add(optionButton);
        add(exitButton);
    }

    //배경 이미지를 그리는 메서드
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image mainTitle = owner.getResources().getImageIcon("main_title.png").getImage();
        g.drawImage(mainTitle, 0, 0, 1024, 1024, null);
    }

    private Game owner;
}
