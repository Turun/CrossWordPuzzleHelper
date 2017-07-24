import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI
{
    JFrame f;
    JTextField jtf;
    JPanel boxes;
    JTextField[] box;
    JTextArea jta;
    
    Mech mech;
    
    int len;
    char[] word;
    
    public static void main(String[] args){GUI g = new GUI();}
    private GUI(){
        makeFrame();
    }
    
    private void makeFrame(){
        f = new JFrame("Crosswordpuzzle Helper");
        f.setVisible(false);
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){exit();}});
        f.setLayout(new BorderLayout());
        
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        JPanel controls = new JPanel(new GridLayout(2,1,5,5));
        main.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        
        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
        
        top.add(new JLabel("Buchstaben: "));
        jtf = new JTextField("5", 3);
        jtf.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){adjustWordlength();}});
        top.add(jtf);
        top.add(Box.createRigidArea(new Dimension(5,5)));
        JButton calc = new JButton("Ok");
        calc.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){adjustWordlength();}});
        top.add(calc);
        
        
        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
        
        boxes = new JPanel(new GridLayout(1,0,2,2));
        adjustWordlength();
        bottom.add(boxes);
        
        JPanel confirmField = new JPanel();
        JButton start = new JButton("Start");
        start.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){start();}});
        confirmField.add(start);
        bottom.add(confirmField);
        
        jta = new JTextArea();
        jta.setEditable(false);
        JScrollPane output = new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        output.setMinimumSize(new Dimension(200,100));
        output.setPreferredSize(new Dimension(300,400));
        output.setMaximumSize(new Dimension(1000,1000));
        
        controls.add(top);
        controls.add(bottom);
        main.add(controls);
        main.add(output);
        f.add(main, BorderLayout.CENTER);
        f.add(new JLabel(""), BorderLayout.NORTH);
        f.add(new JLabel(""), BorderLayout.EAST);
        f.add(new JLabel(""), BorderLayout.SOUTH);
        f.add(new JLabel(""), BorderLayout.WEST);
        
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    
    private void exit(){
        if(mech != null){mech.running = false;}
        f.dispose();
        System.exit(0);
    }
    
    private void getInputWordlength(){
        try{
            String text = jtf.getText().replace(" ",""); //input without spaces
            len = Integer.valueOf(text);
        }catch(NullPointerException e){
            jtf.setText("Intput a number");
        }
    }
    
    private void adjustWordlength(){
        getInputWordlength();
        
        
        word = new char[len];
        if(box != null){
            for(int i = 0; i<box.length && i<word.length; i++){
                String text = box[i].getText();
                text = text.toLowerCase();
                text = text.replace(" ","");
                text = text.trim();
                
                if(text.isEmpty()){
                    word[i] = (char)0;
                }else{
                    word[i] = text.toCharArray()[0];
                }
            }
        }else{
            for(int i = 0; i<word.length; i++){
                word[i] = (char)0;
            }
        }
        
        box = new JTextField[len];
        boxes.removeAll();
        
        for(int i = 0; i<box.length; i++){
            box[i] = new JTextField(String.valueOf(word[i]), 3);
            boxes.add(box[i]);
        }
        
        boxes.validate();
        boxes.repaint();
    }
    
    private void start(){
        adjustWordlength();
        mech = new Mech(this, word);
        mech.start();
    }
    
    public void print(String s){
        jta.append(s+"\n");
    }
   
}
