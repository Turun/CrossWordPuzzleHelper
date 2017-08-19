import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI
{
    static final String instructions = "<html><body>Use an underscore(\"_\") to signal a block <br> without a character or a space</body></html>";
    
    
    JFrame f;
    JPanel middle;
    JPanel withFixedLength;
    JPanel woutFixedLength;
    JCheckBox fixedLength;
    JTextArea jta;
    JTextField wordInput;
    JTextField checkFromStartInput;
    JTextField checkFromEndInput;
    JCheckBox checkFromStart;
    JCheckBox checkFromEnd;
    
    
    Mech mech;
    
    int len;
    char[] word;
    char[] start;
    char[] end;
    
    
    public static void main(String[] args){GUI g = new GUI();}
    private GUI(){
        makeFrame();
    }
    
    private void makeFrame(){
        /**structure: (_____name____ = JPanel name)
         * 
         *  _______________main________________
         * | _____________input_______________ |
         * || _____________top_______________ ||
         * |||                               |||
         * |||   checkbox "fixed length"     |||
         * |||_______________________________|||
         * || ___________middle______________ ||
         * |||                               |||
         * |||                               ||| either:
         * |||                               ||| "use an underscore("_") to signal a block without a character/space" ("_" = ACSII code 95; " " = ASCII code 32)
         * |||                               |||   [textbox]
         * |||                               |||
         * |||                               ||| or:
         * |||                               |||  [checkbox] check words from the beginning
         * |||                               |||  [textbox]["..."]
         * |||                               |||  [checkbox] check words from the end
         * |||                               |||  ["..."][textbox]
         * |||                               |||
         * |||_______________________________|||
         * || ___________bottom______________ ||
         * |||                               |||
         * |||    buttons (e.g. start)       |||
         * |||_______________________________|||
         * ||_________________________________||
         * | ____________output_______________ |
         * ||                                 ||
         * ||                                 ||
         * ||                                 ||
         * ||                                 ||
         * ||                                 ||
         * ||          text output            ||
         * ||                                 ||
         * ||                                 ||
         * ||                                 ||
         * ||                                 ||
         * ||_________________________________||
         * |___________________________________|
         */
        
        
        f = new JFrame("Crosswordpuzzle Helper");
        f.setVisible(false);
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){exit();}});
        f.setLayout(new FlowLayout());
        
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(BorderFactory.createEmptyBorder(2,7,7,7));
        
        /**container for all the inputs**/
        JPanel input = new JPanel();
        input.setLayout(new BoxLayout(input, BoxLayout.Y_AXIS));
        
        
        
        /**the checkbox**/
        JPanel top = new JPanel(new FlowLayout());
        fixedLength = new JCheckBox("fixed length", true);
        fixedLength.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){setFixedLength(fixedLength.isSelected());}});
        top.add(fixedLength);
        
        
        
        /**the container for swapping the two options**/
        middle = new JPanel(new FlowLayout());
        
        /**the JPanel with fixedlength (options one)**/
        withFixedLength = new JPanel();
        withFixedLength.setLayout(new BoxLayout(withFixedLength, BoxLayout.Y_AXIS));
        
        /*needed to render the output low enough to fit the witout fixed panel too*/
        JPanel spaceholder = new JPanel();
        JLabel space = new JLabel("<html><body> <br> <br> <br></body></html>");
        spaceholder.add(space);
        
        wordInput = new JTextField("exa_pl_");
        
        withFixedLength.add(new JLabel(instructions));
        withFixedLength.add(Box.createRigidArea(new Dimension(5,5)));
        withFixedLength.add(wordInput);
        withFixedLength.add(spaceholder);
        
        /**the JPanel without fixed length (option two)**/
        woutFixedLength = new JPanel(new GridLayout(4,1,5,5));
        
        checkFromStart = new JCheckBox("check words from the beginning"); //CheckFromStart
        woutFixedLength.add(checkFromStart);
        
        JPanel upperone = new JPanel();
        upperone.setLayout(new BoxLayout(upperone, BoxLayout.X_AXIS));
        checkFromStartInput = new JTextField("e_am");
        checkFromStartInput.setHorizontalAlignment(SwingConstants.RIGHT);
        upperone.add(checkFromStartInput);
        upperone.add(Box.createRigidArea(new Dimension(5,5)));
        upperone.add(new JLabel("..."));
        woutFixedLength.add(upperone);
        
        checkFromEnd = new JCheckBox("check words from the end");
        woutFixedLength.add(checkFromEnd);
        
        JPanel lowerone = new JPanel();
        lowerone.setLayout(new BoxLayout(lowerone, BoxLayout.X_AXIS));
        checkFromEndInput = new JTextField("mp_e");
        lowerone.add(new JLabel("..."));
        lowerone.add(Box.createRigidArea(new Dimension(5,5)));
        lowerone.add(checkFromEndInput);
        woutFixedLength.add(lowerone);
        
        
        /**contains the buttons**/
        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
        bottom.add(Box.createHorizontalGlue());
        
        JButton start = new JButton("Start");
        start.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){start();}});
        bottom.add(start);
        
        
        
        /**JPanel for all the output**/
        
        jta = new JTextArea();
        jta.setEditable(false);
        JScrollPane output = new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        output.setMinimumSize(new Dimension(200,100));
        output.setPreferredSize(new Dimension(300,400));
        output.setMaximumSize(new Dimension(1000,1000));
        
        
        /**combining all the JPanels in the right way**/
        middle.add(withFixedLength);
        
        input.add(top);
        input.add(middle);
        input.add(bottom);
        
        main.add(input);
        main.add(Box.createRigidArea(new Dimension(10,10)));
        main.add(output);
        
        f.add(main);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    
    private void exit(){
        if(mech != null){mech.running = false;}
        f.dispose();
        System.exit(0);
    }
    
    private void setFixedLength(boolean fixedLength){
        middle.removeAll();
        
        if(fixedLength){
            middle.add(withFixedLength);
        }else{
            middle.add(woutFixedLength);
        }
        
        middle.validate();
        middle.repaint();
        //f.revalidate();
        //f.repaint();
    }
    
    private int getMode(){
        int re = 0;
        if(fixedLength.isSelected()){
            re = mech.FITCHAR;
        }else{
                 if( checkFromStart.isSelected() &&  checkFromEnd.isSelected()){re = mech.FITBOTH;}
            else if( checkFromStart.isSelected() && !checkFromEnd.isSelected()){re = mech.FITBEG;}
            else if(!checkFromStart.isSelected() &&  checkFromEnd.isSelected()){re = mech.FITEND;}
            else if(!checkFromStart.isSelected() && !checkFromEnd.isSelected()){re = mech.FITNOTHING;}
        }
        return re;
    }
    
    private char[] getWord(){
        if(fixedLength.isSelected()){
            return modifyInput(wordInput.getText());
        }else{
            return null;
        }
    }
    
    private char[] getStart(){
        if(checkFromStart.isSelected()){
            return modifyInput(checkFromStartInput.getText());
        }else{
            return null;
        }
    }
    
    private char[] getEnd(){
        if(checkFromEnd.isSelected()){
            return modifyInput(checkFromEndInput.getText());
        }else{
            return null;
        }
    }
    
    private char[] modifyInput(String text){
        text = text.toLowerCase();
        text = text.replace(" ", "");
        char[] re = text.toCharArray();
        for(int i = 0; i<re.length; i++){
            if(re[i] == 95){
                re[i] = 0;
            }
        }
        return re;
    }
    
    private void start(){
        mech = new Mech(this, getWord(), getStart(), getEnd(), getMode());
        mech.start();
    }
    
    public void print(String s){
        jta.append(s+"\n");
    }
    
    
    
    
    
    
    
    
    
    
    
}



















