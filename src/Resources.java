import java.nio.file.*;
import java.util.*;
import java.io.*;

import javax.swing.*;

public class Resources {    
    public Resources(Game owner){
        this.owner = owner;
        images = new HashMap<String, ImageIcon>();
        words = new ArrayList<String>();
        englishWords = new ArrayList<String>();
        translatedWords = new ArrayList<String>();
        moles = new ArrayList<Mole>();
        scores = new LinkedList<Score>();
    }
    
    //이미지 추가 메서드
    public void addImage(String path){
        System.err.println(path);
        Path imagePath = Paths.get(path);
        //파일 이름을 key로
        images.put(imagePath.getFileName().toString(), new ImageIcon(path));
    }

    //단어 추가 메서드
    public void addWord(String word){
        words.add(word);
    }

    //영단어 뜻 추가 메서드
    public void addTranslatedWord(String word){
        translatedWords.add(word);
    }

    //영단어 추가 메서드
    public void addEnglishWord(String word){
        englishWords.add(word);
    }

    //두더지 object 추가 메서드
    public void createMoles(){
        moles.clear();
        for(Mole.MolePosition position : Mole.MolePosition.values()){
            moles.add(new Mole(position, owner));
        }
    }

    //랭킹에 점수 추가 메서드
    public void addScore(Score score){
        scores.add(score);
    }

    //랭킹에 점수 추가 메서드
    public void addScore(int index, Score score){
        scores.add(index, score);
    }

    //이미지 반환 메서드
    public ImageIcon getImageIcon(String name){
        return images.get(name);
    }

    //무작위 단어 반환 메서드
    public String getRandomWord(){
        return words.get(new Random().nextInt(5953));
    }

    //무작위 영-한 단어 반환 메서드
    public Map.Entry<String, String> getRandomEnglishWord(){
        int index = new Random().nextInt(500);
        return new AbstractMap.SimpleEntry<>(englishWords.get(index), translatedWords.get(index));
    }

    //두더지 object 반환 메서드
    public Mole getMole(Mole.MolePosition position){
        return moles.get(position.ordinal());
    }    

    //점수 반환 메서드
    public Score getScore(int index){
        return scores.get(index);
    }

    //순위 반환 메서드
    public int getRanking(int score){
        int ranking = 1;
        var itr = scores.iterator();
        while(itr.hasNext()){            
            if(score > itr.next().getScore()){
                return ranking;
            }
            ranking++;
        }
        return ranking;
    }

    //점수의 수를 반환하는 메서드
    public int getNumScores(){
        return scores.size();
    }

    //점수를 텍스트 파일에 작성하는 메서드
    public void updateScoreFile(){
        try (BufferedWriter bufWriter = new BufferedWriter(new FileWriter("../assets/text/scores.txt"))) {
            scores.stream()
                 .map(Score::toString)
                 .forEach(line -> {
                     try {
                         bufWriter.write(line);
                         bufWriter.newLine();
                     } catch (IOException e) {
                         throw new UncheckedIOException(e);
                     }
                 });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    //이미지 파일
    public HashMap<String, ImageIcon> images;

    //단어, 단어 수 : 5953
    private ArrayList<String> words;
    
    //영단어
    private ArrayList<String> englishWords;
    
    //영단어 뜻
    private ArrayList<String> translatedWords;    

    //두더지 object
    private ArrayList<Mole> moles;

    //점수
    private LinkedList<Score> scores;

    Game owner;
}
