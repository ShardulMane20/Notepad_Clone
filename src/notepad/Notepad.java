
package notepad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*; // for short keys
import javax.swing.filechooser.*;
import java.io.*;

public class Notepad extends JFrame implements ActionListener {
    
    JTextArea area; // defining globally because we want to access it in the actionPerformed method
    String text; // for keeping copied items
    
    Notepad(){
        
        setTitle("Notepad Clone");
        ImageIcon notepadIcon = new ImageIcon(ClassLoader.getSystemResource("notepad/icons/notepadd.png"));
        Image icon = notepadIcon.getImage();
        setIconImage(icon);
        
        JMenuBar menubar = new JMenuBar();
        menubar.setBackground(Color.white); // setting color of menu bar
        
        JMenu file = new JMenu("File"); // creating file in the menubar
        file.setFont(new Font("Aptos Display", Font.BOLD, 14)); // setting font and font size for menu
        
        JMenuItem newdoc = new JMenuItem("New"); // creating dropdown items to Menu
        newdoc.addActionListener(this); // internally calls action perform function
        newdoc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK)); // for short keys
        
        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(this); 
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(this); 
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK)); 
        
        JMenuItem print = new JMenuItem("Print"); 
        print.addActionListener(this); 
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK)); 
        
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(this);    
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0)); // Fixed this line
        
        file.add(newdoc); // adding NEW to the menu
        file.add(open); 
        file.add(save); 
        file.add(print); 
        file.add(exit); 
        menubar.add(file); // adding File to menu bar
        
        JMenu edit = new JMenu("Edit"); // creating edit in the menubar
        edit.setFont(new Font("Aptos Display", Font.BOLD, 14)); // setting font and font size for menu
        
        JMenuItem copy = new JMenuItem("Copy"); // creating dropdown items to Menu
        copy.addActionListener(this); 
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK)); // for short keys
        
        JMenuItem paste = new JMenuItem("Paste");
        paste.addActionListener(this); 
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        
        JMenuItem cut = new JMenuItem("Cut");
        cut.addActionListener(this); 
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK)); 
        
        JMenuItem selectAll = new JMenuItem("Select All");
        selectAll.addActionListener(this); 
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK)); 
        
        edit.add(copy);
        edit.add(paste);
        edit.add(cut);
        edit.add(selectAll);
        menubar.add(edit); // adding edit to menu bar
        
        JMenu helpmenu = new JMenu("Help"); // creating Help in the menubar
        helpmenu.setFont(new Font("Aptos Display", Font.BOLD, 14)); 
        
        JMenuItem help = new JMenuItem("About"); // creating dropdown items to help
        help.addActionListener(this); 
        help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK)); 
      
        helpmenu.add(help);
        menubar.add(helpmenu);
        
        setJMenuBar(menubar);
        
        area = new JTextArea(); // predefined class
        area.setFont(new Font("Aptos Display", Font.PLAIN, 18));
        area.setLineWrap(true); // for next line
        area.setWrapStyleWord(true); // for full word on next line
        
        JScrollPane pane = new JScrollPane(area); // adding scrollbar to text area
        pane.setBorder(BorderFactory.createEmptyBorder()); // removing border of scrollbar
        add(pane); // adding text area directly on the frame
        
        setBounds(400, 100, 700, 600); // Fixed the dimensions
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        
        if (command.equals("New")) {
            area.setText(""); // passing empty string for emptying the text in text area
        } else if (command.equals("Open")) {  // checks which command or button is pressed
            JFileChooser chooser = new JFileChooser(); // for opening new file
            chooser.setAcceptAllFileFilterUsed(false); // for not accepting all kind of files
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt");  
            chooser.addChoosableFileFilter(restrict); // adding this extension to JFileChooser
            
            int action = chooser.showOpenDialog(this); // for opening the dialog
            
            if (action != JFileChooser.APPROVE_OPTION) {
                return;
            }
            
            File file = chooser.getSelectedFile(); // for getting selected file
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));   // BufferedReader reads the file
                area.read(reader, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (command.equals("Save")) {
            JFileChooser saveAs = new JFileChooser();
            saveAs.setApproveButtonText("Save");
            
            int action = saveAs.showSaveDialog(this); // changed to showSaveDialog
            
            if (action != JFileChooser.APPROVE_OPTION) {
                return;
            }
            
            File fileName = new File(saveAs.getSelectedFile() + ".txt");
            try (BufferedWriter outFile = new BufferedWriter(new FileWriter(fileName))) {
                area.write(outFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getActionCommand().equals("Print")){
        
            try{
                area.print();
            } catch (Exception e){
                e.printStackTrace();
            }
            
        } else if (ae.getActionCommand().equals("Exit")){
        
            System.exit(0);
            
        } else if (ae.getActionCommand().equals("Copy")){
        
            text = area.getSelectedText();
        
        } else if (ae.getActionCommand().equals("Paste")){
        
            area.insert(text, area.getCaretPosition());
        
        } else if (ae.getActionCommand().equals("Cut")){
        
            text = area.getSelectedText();
            area.replaceRange("", area.getSelectionStart(), area.getSelectionEnd());
         
        } else if (ae.getActionCommand().equals("Select All")){
        
            area.selectAll();
        
        } else if (ae.getActionCommand().equals("About")){
            new About().setVisible(true);
        }
        
    }
    
    public static void main(String[] args) {
        new Notepad();
    }
}

