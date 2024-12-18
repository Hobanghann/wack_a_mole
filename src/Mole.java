import java.awt.*;
import java.util.Map;

public class Mole {
    //두더지의 상태
    public enum State{
        HIDE,
        OUT,
        HIT};
    
    //두더지의 위치
    public enum MolePosition{
        NORTH_WEST,
        NORTH,
        NORTH_EAST,
        WEST,
        CENTER,
        EAST,
        SOUTH_WEST,
        SOUTH,
        SOUTH_EAST
    }

    //두더지의 위치에 맞게 두더지 좌표, 두더지 크기 설정
    public Mole(MolePosition pos, Game owner){
        this.owner = owner;
        switch(pos){
            case NORTH_WEST :
                position = new Point(220, 360);
                size = new Dimension(150, 117);
                break;
            case NORTH :
                position = new Point(437, 360);
                size = new Dimension(150, 117);
                break;
            case NORTH_EAST :
                position = new Point(654, 360);
                size = new Dimension(150, 117);
                break;
            case WEST :
                position = new Point(170, 495);
                size = new Dimension(170, 137);
                break;
            case CENTER :
                position = new Point(427, 495);
                size = new Dimension(170, 137);
                break;
            case EAST :
                position = new Point(684, 495);
                size = new Dimension(170, 137);
                break;
            case SOUTH_WEST :
                position = new Point(105, 655);
                size = new Dimension(210, 177);
                break;
            case SOUTH :
                position = new Point(412, 655);
                size = new Dimension(210, 177);
                break;
            case SOUTH_EAST :
                position = new Point(706, 655);
                size = new Dimension(210, 177);
                break;
        }
        state = State.HIDE;
        word = owner.getResources().getRandomWord();
    }

   
    public State getState(){
        return state;
    }  
    
    public String getWord(){
        return word;
    }

    //지정한 시간동안 OUT 상태에 있도록 시간 설정
    public void setOut(int outMilliSec){
        totalOutMillisec = outMilliSec;
        lastOutMilliSec = outMilliSec;
        koreanWord = owner.getResources().getRandomWord();
        word = koreanWord;
        state = State.OUT;
    }

    //지정한 시간 동안 HIT 상태에 있도록 시간 설정
    public void setHIT(int hitMilliSec){
        this.lastHitMilliSec = hitMilliSec;
        state = State.HIT;
    }    

    //지정한 시간 동안 OUT 상태에 메가 두더지로 있도록 시간 설정
    public void setOutMegaMole(int outMilliSec){
        totalOutMillisec = outMilliSec;
        lastOutMilliSec = outMilliSec;
        isMegaMole = true;
        Map.Entry<String, String> pair = owner.getResources().getRandomEnglishWord();
        englishWord = pair.getKey();
        koreanWord = pair.getValue();
        word = englishWord;
        state = State.OUT;
    }
    
    //두더지 상태를 업데이트 하는 함수
    public void updateMole(int delay){
        switch(state){
            //두더지가 숨어 있는 경우 업데이트 할 것 없음
            case HIDE:
            break;
            //두더지가 나와있는 경우 나와있는 시간 -delay수행
            case OUT:
            lastOutMilliSec -= delay;
            //시간이 다한 경우 HIDE 상태로 전환
            if(lastOutMilliSec <= 0){
                isMegaMole = false;
                state = State.HIDE;
                owner.getState().failed();
                lastOutMilliSec = 0;
                lastHitMilliSec = 0;
                
            }
            break;
            //두더지가 맞은 상태인 경우 시간 -delay수행
            case HIT:
            lastHitMilliSec -= delay;
            //시간이 다한 경우
            if(lastHitMilliSec <= 0){
                //메가 두더지인 경우 - 일반 두더지로 전환, 기존 시간 초기화, OUT 상태로 전환, 영단어 뜻으로 단어 교체
                if(isMegaMole){
                    isMegaMole = false;
                    lastOutMilliSec = totalOutMillisec - 400;          
                    state = State.OUT;          
                    word = koreanWord;
                }
                //메가 두더지가 아닌 경우 - HIDE 상태로 전환
                else {
                    state = State.HIDE;
                    lastOutMilliSec = 0;
                    lastHitMilliSec = 0;
                }
            }
            break;
        }
    }
    
    //두더지 이미지 그리는 메서드
    public void drawMole(Graphics g){
        Image image = null;
        if(isMegaMole){
            image = getMegaMoleImage();
        }
        else {
            image = getMoleImage();
        }
        switch(state){
            //두더지가 숨어있는 경우
            case HIDE:
            g.drawImage(image, (int)position.getX(), (int)position.getY(), (int)size.getWidth(), (int)size.getHeight(), null);
            break;
            //두더지가 나와있는 경우
            case OUT:
            g.drawImage(image, (int)position.getX(), (int)position.getY(), (int)size.getWidth(), (int)size.getHeight(), null);
            g.drawImage(
                owner.getResources().getImageIcon("word_label.png").getImage(),
                (int)position.getX(),
                (int)position.getY() + (int)size.getHeight(),
                (int)size.getWidth(),
                30, null);
            g.setFont(new Font("맑은고딕", Font.BOLD, 20));
            g.drawString(word, (int)position.getX() + 30, (int)position.getY() + (int)size.getHeight() + 22);
            
            break;
            //두더지가 맞은 경우
            case HIT:
            g.drawImage(image, (int)position.getX(), (int)position.getY(), (int)size.getWidth(), (int)size.getHeight(), null);
            g.drawImage(
                owner.getResources().getImageIcon("word_label.png").getImage(),
                (int)position.getX(),
                (int)position.getY() + (int)size.getHeight(),
                (int)size.getWidth(),
                30, null);            
            g.setFont(new Font("맑은고딕", Font.BOLD, 20));
            g.drawString(word, (int)position.getX() + 30, (int)position.getY() + (int)size.getHeight() + 22);
            break;
        }
    }

 //일반 두더지 이미지 반환 메서드
    private Image getMoleImage(){
        Image image = null;
        switch(state){
            case HIDE:
            image = owner.getResources().getImageIcon("empty_hole.png").getImage();
            break;
            case OUT:
            image = owner.getResources().getImageIcon("mole_out4.png").getImage();
            //두더지가 나오는 상황
            if(totalOutMillisec - lastOutMilliSec <= 300){
                if(totalOutMillisec - lastOutMilliSec <= 100){
                    image = owner.getResources().getImageIcon("mole_out1.png").getImage();
                }
                else if(totalOutMillisec - lastOutMilliSec <= 200){
                    image = owner.getResources().getImageIcon("mole_out2.png").getImage();
                }else if(totalOutMillisec - lastOutMilliSec <= 300){
                    image = owner.getResources().getImageIcon("mole_out3.png").getImage();
                }
            
            }//두더지가 들어가는 상황
            else if (lastOutMilliSec <= 2000){
                if(lastOutMilliSec <= 100){
                    image = owner.getResources().getImageIcon("mole_out1.png").getImage();
                }
                else if(lastOutMilliSec <= 200){
                    image = owner.getResources().getImageIcon("mole_out2.png").getImage();
                }
                else if(lastOutMilliSec <= 300){
                    image = owner.getResources().getImageIcon("mole_out3.png").getImage();
                }
                else if(lastOutMilliSec <= 400){
                    image = owner.getResources().getImageIcon("mole_out4.png").getImage();
                }
                else if(lastOutMilliSec <= 1000){
                    image = owner.getResources().getImageIcon("mole_shout.png").getImage();
                }
                else {
                    image = owner.getResources().getImageIcon("mole_angry.png").getImage();
                }
            } 
            break;
            case HIT:
            image = owner.getResources().getImageIcon("mole_hit.png").getImage();
            if(lastHitMilliSec <= 200){
                image = owner.getResources().getImageIcon("mole_hit2.png").getImage();
            }else if(lastHitMilliSec <= 100){
                image = owner.getResources().getImageIcon("mole_hit3.png").getImage();
            }
        }
        return image;
    } 

    //메가 두더지 이미지 반환 메서드
    private Image getMegaMoleImage(){
        Image image = null;
        switch(state){
            case HIDE:
            return null;
            case OUT:
            image = owner.getResources().getImageIcon("megamole_out9.png").getImage();
            //두더지가 나오는 상황
            if(totalOutMillisec - lastOutMilliSec <= 800){
                if(totalOutMillisec - lastOutMilliSec <= 100){
                    image = owner.getResources().getImageIcon("megamole_out1.png").getImage();
                }
                else if(totalOutMillisec - lastOutMilliSec <= 200){
                    image = owner.getResources().getImageIcon("megamole_out2.png").getImage();
                }else if(totalOutMillisec - lastOutMilliSec <= 300){
                    image = owner.getResources().getImageIcon("megamole_out3.png").getImage();
                }else if(totalOutMillisec - lastOutMilliSec <= 400){
                    image = owner.getResources().getImageIcon("megamole_out4.png").getImage();
                }else if(totalOutMillisec - lastOutMilliSec <= 500){
                    image = owner.getResources().getImageIcon("megamole_out5.png").getImage();
                }else if(totalOutMillisec - lastOutMilliSec <= 600){
                    image = owner.getResources().getImageIcon("megamole_out6.png").getImage();
                }else if(totalOutMillisec - lastOutMilliSec <= 700){
                    image = owner.getResources().getImageIcon("megamole_out7.png").getImage();
                }else if(totalOutMillisec - lastOutMilliSec <= 800){
                    image = owner.getResources().getImageIcon("megamole_out8.png").getImage();
                }
            }
            //두더지가 들어가는 상황
            else if (lastOutMilliSec <= 800){
                if(lastOutMilliSec <= 100){
                    image = owner.getResources().getImageIcon("mole_out1.png").getImage();
                }
                else if(lastOutMilliSec <= 200){
                    image = owner.getResources().getImageIcon("mole_out2.png").getImage();
                }
                else if(lastOutMilliSec <= 300){
                    image = owner.getResources().getImageIcon("mole_out3.png").getImage();
                }
                else if(lastOutMilliSec <= 500){
                    image = owner.getResources().getImageIcon("mole_shout.png").getImage();
                }
                else if(lastOutMilliSec <= 600){
                    image = owner.getResources().getImageIcon("megamole_out8.png").getImage();
                }
                else if(lastOutMilliSec <= 700){
                    image = owner.getResources().getImageIcon("megamole_out7.png").getImage();
                }
                else {
                    image = owner.getResources().getImageIcon("megamole_out8.png").getImage();
                }
            }
            break;            
            case HIT:
            image = owner.getResources().getImageIcon("megamole_hit.png").getImage();
        }
        return image;
    }

    //총 OUT 상태인 시간
    private int totalOutMillisec;
    //앞으로 남은 OUT 상태인 시간
    private int lastOutMilliSec;
    //앞으로 남은 HIT 상태인 시간
    private int lastHitMilliSec;
    //메가 두더지 여부
    private boolean isMegaMole;
    //이미지 파일 크기
    private Dimension size;
    //두더지를 그릴 화면상의 위치
    private Point position;
    //두더지 상태
    private State state;
    //화면에 출력할 단어
    private String word;
    //랜덤하게 선택된 한국어 단어
    private String koreanWord;
    //랜덤하게 선택된 영단어
    private String englishWord;

    private Game owner;
}
