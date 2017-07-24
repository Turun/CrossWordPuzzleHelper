import java.io.*;

public class Mech extends Thread
{
    GUI gui;
    
    String filename = "";
    boolean running = true;
    char[] goal;
    
    public Mech(GUI g, char[] word){
        goal = word;
        gui = g;
    }
    
    public void run(){
        if(!setFilename()){return;}
        
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
    
    private boolean setFilename(){
        boolean success = false;
        try(BufferedReader br = new BufferedReader(new FileReader("./DictionaryLocation.txt"))){
            filename = br.readLine();
            success = true;
        }catch(IOException e){
            print("\n\n Failed to read the location of the Dictionary file.\n");
            print("Make sure you have a file called \n \"DictionaryLocation.txt\" in the directory of this File. \n That File must contain the absolute path of your \n dictionary file. Make sure to include the .txt ending \n and to escape every \"\\\" by putting a  \"\\\" in front of it.");
        }
        return success;
    }
    
    private void print(String line){
        gui.print(" "+line);
    }
}
