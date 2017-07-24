import java.io.*;

public class Mech extends Thread
{
    GUI gui;
    
    String filename = "<path to dictionary>";
    boolean running = true;
    char[] goal;
    
    public Mech(GUI g, char[] word){
        goal = word;
        gui = g;
    }
    
    public void run(){
        print("---start---");
        String line;
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            outerLoop:
            while(running && ((line = br.readLine()) != null)){
                line = line.toLowerCase();
                
                if(line.length() != goal.length){continue;}
                
                char[] word = line.toCharArray();
                innerLoop:
                for(int i = 0; i<goal.length; i++){
                    if((word[i] != goal[i]) && (goal[i] != 0)){
                        continue outerLoop;
                    }
                }
                
                print(line);
            }
            print("---done---");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    private void print(String line){
        gui.print(line);
    }
}
