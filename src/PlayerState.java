import java.awt.*;

public class PlayerState {

    public PlayerState(Game owner){
        this.owner = owner;
        heartState = new boolean[5];
        lastHeartIndex = 5;
        for(int i = 0; i < 5; i++){
            heartState[i] = true;
        }
        score = 0;
        playSeconds = 0;
    }

    //플레이어 상태를 초기화하는 함수
    public void initializeState(){
        lastHeartIndex = 5;
        for(int i = 0; i < 5; i++){
            heartState[i] = true;
        }
        score = 0;
        playSeconds = 0;
    }

    //플레이어의 점수를 반환하는 메서드
    public int getScore(){
        return score;
    }

    //HP를 -1하는 메서드, 정답이 틀리거나, 정답을 맞추기 전 두더지가 숨는 경우 호출
    public void failed(){  
        if(lastHeartIndex < 1) {
            return;
        }    
        lastHeartIndex--;
        System.err.println("current last Index : " + lastHeartIndex);
        heartState[lastHeartIndex] = false;        
    }

    //점수를 +100하는 함수, 정답을 맞추는 경우에 호출
    public void success(){
        score += 100;
    }    

    //게임 종료 조건 확인 메서드
    public boolean isGameOver(){
        return lastHeartIndex == 0;
    }
    
    //플레이 시간 업데이트 메서드
    public void addMilliSecond(int milliSecond){
        playSeconds += milliSecond;
    }

    //플레이 시간을 String으로 반환하는 메서드 
    public String getSecondsToString() {
        int hours = (playSeconds / 1000) / 3600;
        int minutes = ((playSeconds / 1000) % 3600) / 60;
        int seconds = (playSeconds / 1000) % 60;
    
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    //HP, 점수, 시간을 그리는 메서드
    public void drawState(Graphics g){
        g.setFont(new Font("맑은고딕", Font.BOLD, 25));
        g.setColor(Color.WHITE);
        //HP 그리기
        Image heartImage;
        for(int i = 0; i < 5; i++){
            if(heartState[i]){
                heartImage = owner.getResources().getImageIcon("heart_on.png").getImage();
            }
            else {
                heartImage = owner.getResources().getImageIcon("heart_off.png").getImage();
            }
            g.drawImage(heartImage, 50 + i * 60, 50, 50, 45, null);
        }
        //텍스트 필드 그리기
        Image textImage = owner.getResources().getImageIcon("text_field.png").getImage();
        g.drawImage(textImage, 790, 50, 200, 50, null);        
        //점수 창 그리기
        Image scoreImage = owner.getResources().getImageIcon("score.png").getImage();
        g.drawImage(scoreImage, 740, 50, 50, 50, null);
        g.drawString(String.format("%d", score), 850, 80);
        //시간 창 그리기
        Image clockImage = owner.getResources().getImageIcon("clock.png").getImage();
        g.drawImage(clockImage, 380, 50, 50, 50, null);
        g.drawImage(textImage, 425, 50, 200, 50, null);
        g.drawString(getSecondsToString(), 475, 80);                
    }

    //플레이어 HP
    private boolean[] heartState;
    //플레이어 HP의 칸 수
    private int lastHeartIndex;
    //플레이어 점수
    private int score;
    //플레이 시간
    private int playSeconds;
    Game owner;
}
