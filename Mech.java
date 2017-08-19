import java.io.*;

public class Mech extends Thread
{
    static final int FITBEG = 0;
    static final int FITCHAR = 1;
    static final int FITBOTH = 2;
    static final int FITEND = 3;
    static final int FITNOTHING = 666;
    
    
    GUI gui;
    
    String filename = "";
    int mode;
    boolean running = true;
    char[] goal;
    char[] start;
    char[] end;
    
    public Mech(GUI g, char[] word, char[] start, char[] end, int mode){
        goal = word;
        gui = g;
        this.start = start;
        this.end = end;
        this.mode = mode;
        
        setFilename();
    }
    
    public void run(){
        if(filename.length() < 3){return;}
        
        print("---start---");
        switch(mode){
            case(FITBEG):{fitBeg();break;}
            case(FITCHAR):{fitChar();break;}
            case(FITBOTH):{fitBoth();break;}
            case(FITEND):{fitEnd();break;}
            case(FITNOTHING):{print("please select at least one checkbox");break;}
            default:{
                print("Error! Send the devs this: Mech - invalid mode number: "+String.valueOf(mode));
                break;
            }
        }
        print("---done---");
    }
    
    private void fitBeg(){
        String line;
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            outerLoop:
            while(running && ((line = br.readLine()) != null)){
                //line = line.toLowerCase();
                char[] word = line.toLowerCase().toCharArray();
                
                /**if the word we have is shorter than the word we search**/
                if(word.length < start.length){continue outerLoop;} 
                
                /**runs through the given array and compares it with the word we have. if the char doesn't match the seach continues**/
                innerLoop:
                for(int i = 0; i<start.length; i++){
                    if((word[i] != start[i]) && (start[i] != 0) && (word[i] != 32) && (word[i] != 95)){ //continue, if they are not the same, start is defined and word is not a space/underscore
                        continue outerLoop;
                    }
                }
                
                print(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    private void fitEnd(){
        String line;
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            outerLoop:
            while(running && ((line = br.readLine()) != null)){
                //line = line.toLowerCase();
                char[] word = line.toLowerCase().toCharArray();
                
                /**if the word we have is shorter than the word we search**/
                if(word.length < end.length){continue outerLoop;}
                
                /**starts at the end of the endArray and goes to the front. compares every single char and when they don't match the search contines**/
                innerLoop:
                for(int i = end.length-1; i >= 0; i--){
                    if((word[word.length-end.length+i] != end[i]) && (end[i] != 0) && (word[i] != 32) && (word[i] != 95)){
                        continue outerLoop;
                    }
                }
                
                print(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    private void fitBoth(){
        String line;
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            outerLoop:
            while(running && ((line = br.readLine()) != null)){
                //line = line.toLowerCase();
                char[] word = line.toLowerCase().toCharArray();
                
                /**if the word we have is too short, it immediately gets discarded**/
                if(word.length < start.length || word.length < end.length){continue outerLoop;}
               
                /**the following methods are the same as in the fitEnd and fitBeg methods**/                
                /**runs through the given array and compares it with the word we have. if the char doesn't match the seach continues**/
                innerLoop1:
                for(int i = 0; i<start.length; i++){
                    if((word[i] != start[i]) && (start[i] != 0) && (word[i] != 32) && (word[i] != 95)){
                        continue outerLoop;
                    }
                }
                
                /**starts at the end of the endArray and goes to the front. compares every single char and when they don't match the search contines**/
                innerLoop2:
                for(int i = end.length-1; i >= 0; i--){
                    if((word[word.length-end.length+i] != end[i]) && (end[i] != 0) && (word[i] != 32) && (word[i] != 95)){
                        continue outerLoop;
                    }
                }
                
                print(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    private void fitChar(){
        String line;
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            outerLoop:
            while(running && ((line = br.readLine()) != null)){
                line = line.toLowerCase();
                
                if(line.length() != goal.length){continue;}
                
                char[] word = line.toCharArray();
                innerLoop:
                for(int i = 0; i<goal.length; i++){
                    if((word[i] != goal[i]) && (goal[i] != 0) && (word[i] != 32) && (word[i] != 95)){
                        continue outerLoop;
                    }
                }
                
                print(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    private void setFilename(){
        try(BufferedReader br = new BufferedReader(new FileReader("./DictionaryLocation.txt"))){
            filename = br.readLine();
        }catch(IOException e){
            print("\n\n Failed to read the location of the Dictionary file.\n");
            print("Make sure you have a file called \n \"DictionaryLocation.txt\" in the directory of this File. \n That File must contain the absolute path of your \n dictionary file. Make sure to include the .txt ending.");
        }
    }
    
    private void print(String line){
        gui.print(" "+line);
    }
}
