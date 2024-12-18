import javax.swing.*;
import java.awt.*;

public class OptionPanel extends JPanel{
    public OptionPanel(Game owner){
        this.owner = owner;
        setLayout(null);
        setSize(1024, 1024);
        setVisible(true);

        //난이도 버튼 그룹
        ButtonGroup difficultyButtons = new ButtonGroup();

        //쉬움 버튼
        easyButton = new JRadioButton();
        Image easyButtonImage = owner.getResources().getImageIcon("easy_button.png").getImage().getScaledInstance(130, 60, Image.SCALE_SMOOTH);
        easyButton.setSize(135, 60);
        easyButton.setIcon(new ImageIcon(easyButtonImage));
        easyButtonImage = owner.getResources().getImageIcon("easy_button_pressed.png").getImage().getScaledInstance(130, 60, Image.SCALE_SMOOTH);
        easyButton.setSelectedIcon(new ImageIcon(easyButtonImage));
        easyButton.setLocation(307, 430);
        easyButton.setBorderPainted(false);
        easyButton.setFocusPainted(false);
        easyButton.setContentAreaFilled(false);
        easyButton.addActionListener(e->{
            owner.setDifficulty(Game.Difficulty.EASY);
        });

        //보통 버튼
        normalButton = new JRadioButton();
        Image normalButtonImage = owner.getResources().getImageIcon("normal_button.png").getImage().getScaledInstance(130, 60, Image.SCALE_SMOOTH);
        normalButton.setSize(135, 60);
        normalButton.setIcon(new ImageIcon(normalButtonImage));
        normalButtonImage = owner.getResources().getImageIcon("normal_button_pressed.png").getImage().getScaledInstance(130, 60, Image.SCALE_SMOOTH);
        normalButton.setSelectedIcon(new ImageIcon(normalButtonImage));
        normalButton.setLocation(447, 430);
        normalButton.setBorderPainted(false);
        normalButton.setFocusPainted(false);
        normalButton.setContentAreaFilled(false);
        normalButton.addActionListener(e->{
            owner.setDifficulty(Game.Difficulty.NORMAL);
        });


        //어려움 버튼
        hardButton = new JRadioButton();
        Image hardButtonImage = owner.getResources().getImageIcon("hard_button.png").getImage().getScaledInstance(130, 60, Image.SCALE_SMOOTH);
        hardButton.setSize(135, 60);
        hardButton.setIcon(new ImageIcon(hardButtonImage));
        hardButtonImage = owner.getResources().getImageIcon("hard_button_pressed.png").getImage().getScaledInstance(130, 60, Image.SCALE_SMOOTH);
        hardButton.setSelectedIcon(new ImageIcon(hardButtonImage));
        hardButton.setLocation(587, 430);
        hardButton.setBorderPainted(false);
        hardButton.setFocusPainted(false);
        hardButton.setContentAreaFilled(false);
        hardButton.addActionListener(e->{
            owner.setDifficulty(Game.Difficulty.HARD);
        });


        //Game object에 저장된 난이도에 따른 초기 버튼 값 설정
        switch(owner.getDifficulty()){
            case EASY:
            easyButton.setSelected(true);
            break;
            case NORMAL:
            normalButton.setSelected(true);
            break;
            case HARD:
            hardButton.setSelected(true);
            break;
        }

        //영어 두더지 등장 확률 설정 슬라이더
        JSlider probabilitySlider = new JSlider(0, 100, owner.getProbability());
        probabilitySlider.setSize(350, 70);
        probabilitySlider.setLocation(337, 630);
        probabilitySlider.setPaintTicks(true);
        probabilitySlider.setMajorTickSpacing(10);
        probabilitySlider.setPaintLabels(true);
        probabilitySlider.setForeground(Color.BLACK);
        probabilitySlider.setOpaque(false);
        //슬라이더에 따라 확률 값이 세팅 되도록 이벤트 리스너 추가
        probabilitySlider.addChangeListener(e -> {
            owner.setProbability(((JSlider)e.getSource()).getValue());
        });

        //TitlePanel로 돌아가는 버튼
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


        difficultyButtons.add(easyButton);
        difficultyButtons.add(normalButton);
        difficultyButtons.add(hardButton);

        add(easyButton);
        add(normalButton);
        add(hardButton);
        add(probabilitySlider);
        add(backButton);

        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image background = owner.getResources().getImageIcon("main_title_darker.png").getImage();
        Image leaderBoardImage = owner.getResources().getImageIcon("option.png").getImage();
        g.drawImage(background, 0, 0, 1024, 1024, null);
        g.drawImage(leaderBoardImage, 262, 162, 500, 700, null);
    }

    JRadioButton easyButton;
    JRadioButton normalButton;
    JRadioButton hardButton;
    private Game owner;
}
