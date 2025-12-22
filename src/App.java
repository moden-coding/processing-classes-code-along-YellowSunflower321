import java.util.ArrayList;
import processing.core.PApplet;
import java.util.Scanner;
import java.nio.file.Paths;
import java.io.PrintWriter;
import java.io.IOException;



public class App extends PApplet{
    Bubble firstOne;
    ArrayList<Bubble> bubbles;
    double timer;
    int scene;
    double highScore;
    double gameStart;
    public static void main(String[] args)  {
        PApplet.main("App");
    }

    public void setup(){

        readHighScore();
        bubbles = new ArrayList<>();
        for(int i=0; i<4;i++){
            bubbleMaker();
        }
        scene = 0;
        gameStart = millis();
    }

    public void settings(){
        size(800,600);
         
    }

    public void draw(){
        background(0);
        if(scene==0){
            for(Bubble b : bubbles){
            b.display();
            b.update();
       }
       fill(255);
       textSize(50);
       timer = millis()-gameStart;
       timer = ((int)timer/100)/10.0;
       text("" + timer, width - 100, 50);
       if(bubbles.size()==0){ //if no bubbles remain on the screen
            scene = 1;
            readHighScore();
            if(highScore<timer && highScore==0){
                highScore=timer;
                saveHighScore();
            }
       }

        } else{
            text("Score: " + timer, 400,400);
            text("High Score: " + highScore, 400,440);

        }
  
    }

    public void bubbleMaker(){
          int x = (int)random(600);
            int y = (int)random(400);
            Bubble bubble = new Bubble(x, y, this);
            bubbles.add(bubble);
    }


    public void keyPressed(){
        if(key == ' '){
            if(scene==0){
          bubbles.clear(); //deletes everything in array
            } else{
                setup();

            } 
          
        }
        
    }

    public void mousePressed(){
        for(int i=0; i<bubbles.size(); i++){
            Bubble b = bubbles.get(i);
            if(b.checkTouch(mouseX,mouseY)==false){
                bubbles.remove(b);
            }            
        }
    }

    public void readHighScore(){
           try(Scanner scanner = new Scanner(Paths.get("highscore.txt"))){
            while(scanner.hasNextLine()){
                String row = scanner.nextLine();
                highScore = Double.valueOf(row);
            }
        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void saveHighScore(){
        try(PrintWriter writer = new PrintWriter("highscore.txt")){
            writer.println(highScore); //writes the score in the file
            writer.close(); //closes the writer and saves the file
        } catch (IOException e){
            System.out.println("Error.");
            e.printStackTrace();
        }

    }
}
