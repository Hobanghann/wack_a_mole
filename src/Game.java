import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
public class Game {

    enum Difficulty {
        EASY,
        NORMAL,
        HARD
    }  

    public Game(){
        mainWindow = new MainWindow(this);
        resources = new Resources(this);
        playerState = new PlayerState(this);
        difficulty = Difficulty.NORMAL;
        probability = 50;
    }

    //게임 시작 전 데이터를 로드하는 메서드
    public void loadResources(){
        
        //이미지 파일 로딩
        try (Stream<Path> paths = Files.list(Paths.get("../assets/images"))) {
            paths.filter(path -> path.toString().endsWith(".png")) // .png 확장자 필터링
                 .forEach(path -> resources.addImage(path.toString())); // addImage 호출
        } catch (IOException e) {
            e.printStackTrace();
        }    
 
        //단어 로딩
        try(BufferedReader bufReader = new BufferedReader(new FileReader("../assets/text/words.txt"))){
            String word;
            while((word = bufReader.readLine()) != null){

                resources.addWord(word.trim());
            } 
        }
        catch(IOException e){
            e.printStackTrace();
        }

        //단어 로딩
        try(BufferedReader bufReader = new BufferedReader(new FileReader("../assets/text/words_english_translated.txt"))){
            String word;
            while((word = bufReader.readLine()) != null){

                resources.addTranslatedWord(word.trim());
            } 
        }
        catch(IOException e){
            e.printStackTrace();
        }

        //단어 로딩
        try(BufferedReader bufReader = new BufferedReader(new FileReader("../assets/text/words_english.txt"))){

            String word;
            while((word = bufReader.readLine()) != null){

                resources.addEnglishWord(word.trim());
            } 
        }
        catch(IOException e){
            e.printStackTrace();
        }

        //두더지 object 로딩
        resources.createMoles();

        //점수 로딩
        try (BufferedReader bufReader = new BufferedReader(new FileReader("../assets/text/scores.txt"))) {
            String line;

            // 파일의 각 라인을 읽음
            while ((line = bufReader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    resources.addScore(new Score(name, score));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //전체 윈도우에 TitlePanel을 추가하여 게임을 시작하는 메서드
    public void runGame(){
        mainWindow.add(new TitlePanel(this));
        mainWindow.setVisible(true);
    }
    
    //전체 윈도우를 반환하는 메서드
    public MainWindow getMainWindow(){
        return mainWindow;
    }

    //리소스를 반환하는 메서드
    public Resources getResources(){
        return resources;
    }

    //플레이어 상태 object를 반환하는 메서드
    public PlayerState getState(){
        return playerState;
    }

    //게임 난이도 반환 메서드
    public Difficulty getDifficulty(){
        return difficulty;
    }

    //영어 두더지 확률 반환 메서드
    public int getProbability(){
        return probability;
    }

    //게임 난이도 세팅 메서드
    public void setDifficulty(Difficulty dif){
        difficulty = dif;
    }

    //영어 두더지 확률 세팅 메서드
    public void setProbability(int prob){
        if(prob > 100){
            prob = 100;
        }
        if(prob < 0){
            prob = 0;
        }
        probability = prob;
    }

    //전체 윈도우
    private MainWindow mainWindow;

    //게임 관련 데이터
    private Resources resources;

    //플레이어 상태
    private PlayerState playerState;

    //난이도
    private Difficulty difficulty;

    //영어 두더지 확률
    private int probability;
}
