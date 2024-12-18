import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;
import java.util.*;
import java.util.Queue;

//게임 플레이 화면 패널
public class GamePanel extends JPanel {
    
    public GamePanel(Game owner){
        this.owner = owner;
        answerQueue = new LinkedList<String>();
        random = new Random();

       
        setSize(1024, 1024);
        setLayout(null);
        setVisible(true);
        
        //난이도에 따른 시간 불러오기
        switch(owner.getDifficulty()){
            case EASY:
            outMilliSec = 8000;
            baseNextOutMilliSec = 2000;
            break;
            case NORMAL:
            outMilliSec = 4000;
            baseNextOutMilliSec = 1200;
            break;
            case HARD:
            outMilliSec = 2000;
            baseNextOutMilliSec = 600;
            break;
        }
        
        //정답 입력을 위한 텍스트 필드 추가
        JTextField textField = new JTextField();
        textField.setOpaque(false);
        textField.setBorder(null);
        textField.setSize(220, 50);
        textField.setLocation(402, 880);
        textField.setFont(new Font("맑은고딕", Font.BOLD, 25));
        
        textField.requestFocus();
        textField.setFocusable(true);

        //텍스트 필드에 이벤트 리스너 추가=
        textField.addActionListener(e -> {
            //입력 문자열 큐에 삽입(32ms 동안 두번 입력될 수도)
            JTextField source = (JTextField)e.getSource();
            answerQueue.add(source.getText().trim());
            source.setText("");
        });

        add(textField);

        //플레이 시간을 위한 타이머 추가 - 메인 게임 루프
        playTimeTimer = new javax.swing.Timer(32, e->{
            //게임 종료 상황인 경우 다음 화면으로
            if(owner.getState().isGameOver()){
                playTimeTimer.stop();
                owner.getMainWindow().changePanel(this, new GameOverPanel(owner));
            }
            //플레이 타임 증가
            owner.getState().addMilliSecond(playTimeTimer.getDelay());            
            //인풋 핸들링
            inputHandling();
            //두더지 업데이트
            for(Mole.MolePosition position : Mole.MolePosition.values()){
                owner.getResources().getMole(position).updateMole(playTimeTimer.getDelay());
            }
            //다음 OUT state가 될 시간 감소
            nextOutMilliSec -= playTimeTimer.getDelay();
            //OUT state에 놓여야 하는 경우
            if(nextOutMilliSec <= 0){
                makeMoleOut();
            }
            //다시 그리기
            repaint();            
        });
        //타이머 시작
        playTimeTimer.start();

        //디버깅 용 일시정지 기능
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int keyCode = e.getKeyCode();
                if(keyCode == KeyEvent.VK_ESCAPE)
                    {
                        if(isRunning){
                            playTimeTimer.stop();
                            isRunning = false;
                        }
                        else {
                            playTimeTimer.start();
                            isRunning = true;
                        }
                    }
            }
        });

        
    }


    //화면 이미지를 그리는 함수
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //배경 화면 그리기
        Image backgroundImage = owner.getResources().getImageIcon("background.png").getImage();
        g.drawImage(backgroundImage, 0, 0, 1024, 1024, null);
        //텍스트 필드 이미지 그리기
        Image textImage = owner.getResources().getImageIcon("text_field.png").getImage();
        g.drawImage(textImage, 362, 870, 300, 80, null);
        //두더지 이미지 그리기
        for(Mole.MolePosition position : Mole.MolePosition.values()){
            owner.getResources().getMole(position).drawMole(g);
        }
        //플레이어 상태 그리기
        owner.getState().drawState(g);        
    }

    //랜덤한 두더지를 OUT 상태로 만드는 메서드
    private void makeMoleOut(){
        Mole.MolePosition[] positions = Mole.MolePosition.values();
        //랜덤 두더지 선택
        for(int i = 0; i < 9; i++){
            Mole mole = owner.getResources().getMole(positions[random.nextInt(9)]);
            //두더지가 HIDE 상태인 경우 OUT 상태로
            if(mole.getState() == Mole.State.HIDE){
                //확률에 따른 영어 두더지 여부 결정 - 백분률로 나타내어진 정수보다 랜덤하게 선택한 정수가 작다면 영어 두더지로
                if(random.nextInt(101) <= owner.getProbability()){
                    mole.setOutMegaMole(outMilliSec + random.nextInt(outMilliSec));
                }
                else {                
                    mole.setOut(outMilliSec + random.nextInt(outMilliSec));
                }
                break;
            }
        }
        nextOutMilliSec = baseNextOutMilliSec + random.nextInt(baseNextOutMilliSec);
    }

    //정답 확인 메서드
    private void inputHandling(){
        while(!answerQueue.isEmpty()){
            boolean success = false;
            String answer = answerQueue.poll();
            for(Mole.MolePosition position : Mole.MolePosition.values()){
                Mole mole = owner.getResources().getMole(position);
                if(mole.getWord().equals(answer)){
                    success = true;
                    mole.setHIT(500);
                    owner.getState().success();
                    break;
                }
            }
            //정답이 아닌 경우
            if(!success){
                //플레이어 HP -1
                owner.getState().failed();
            }
        }        
    }

    //플레이 시간 타이머
    private javax.swing.Timer playTimeTimer;
    //OUT state로 만들 시간
    private int outMilliSec;
    //base 값으로 사용할 다음 OUT state까지 시간
    private int baseNextOutMilliSec;
    //다음 OUT state까지 시간
    private int nextOutMilliSec;
    //입력 문자열 큐
    private Queue<String> answerQueue;

    private Game owner;
    
    private Random random;    

    //디버깅 용
    private boolean isRunning = true;
}
