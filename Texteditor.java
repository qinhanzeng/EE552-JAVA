
package Texteditor;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.lang.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *Cite: Siyu chen
 * @author Hanzheng Qin
 * comment: Since I didn't do it well lasttime of the texteditor so I refer to my friends the compile and run
 */
public class Texteditor extends JFrame{
    public String path = "";
    public String classpath = "";
    private Container c;
    private JMenuBar mb;
    private JMenu m0;
    private JMenu m1;
    private JMenuItem mi1;
    private JMenuItem mi2;
    private JMenuItem mi3;
    private JMenuItem mi4;
    private JMenuItem mi5;
    private JTextArea ta;
    private JTextArea ta2;
    private JFileChooser chooser;
    private JTextField tf;
    
     private String str_pkg;       // Absolute path of file, eg: "C:\GraduateStudy\C++\CPEjava\session06\MyClass.java"
    private String str_before;    // string in text_area, used to judge whether file content is changed
    private String str_path;      // getParent() of file, eg:"C:\GraduateStudy\C++\CPEjava\session06"
    private String str_filename1; // Name of file, eg:"MyClass.java"
    private String str_filename2; // Name of file excludes file class, eg:"MyClass"
    private StringBuilder strb_content;    // a string builder
    private JScrollPane text_area_scroll;  // scroll
    private DialogFrame dialog_frame;
    private java.util.List<String> regexSplitLine;  // All the matched part of error message using regex
    private java.util.List<Integer> regexSplitLineInt;   // All the num of matched error message rows
    private int cursorLineNum = 0;  // Recor
    
    
    public Texteditor(){
        super("Texteditor");
        setSize(800,600);
        c = getContentPane();
        strb_content = new StringBuilder("// Java Test Editor: \n");
        str_before = "// Java Test Editor: \n";
        mb = new JMenuBar();
        setJMenuBar(mb);

      
        
        tf = new JTextField("File :");
        c.add(tf,BorderLayout.NORTH);
    
        ta = new JTextArea(strb_content.toString());
        c.add(ta,BorderLayout.CENTER);
        ta2 = new JTextArea("Result :");
        c.add(ta2,BorderLayout.SOUTH);

        text_area_scroll = new JScrollPane(ta);
        text_area_scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        text_area_scroll.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        c.add(text_area_scroll);
        
        m0 = new JMenu("File");
        mb.add(m0);
        m1 = new JMenu("Build");
        mb.add(m1);
        
        
        regexSplitLine = new ArrayList<>();
        regexSplitLineInt = new ArrayList<>();
        
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
        ta.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_F4:{
                        if(!regexSplitLineInt.isEmpty()){
                            Error();
                        }
                        break;
                    }
                }
            }
        });
        ta2.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_F4:{
                        if(!regexSplitLineInt.isEmpty()){
                            Error();
                        }
                        break;
                    }
                }
            }
        });
        tf.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_F4:{
                        if(!regexSplitLineInt.isEmpty()){
                            Error();
                        }
                        break;
                    }
                }
            }
        });
        
        String name1 = "new";
        String name2 = "open";
        String name3 = "save";
        String name4 = "compile";
        String name5 = "run";
        
        mi1 = new JMenuItem(name1);
        mi2 = new JMenuItem(name2);
        mi3 = new JMenuItem(name3);
        mi4 = new JMenuItem(name4);
        mi5 = new JMenuItem(name5);
          
        mi1.addActionListener(new ActionListener_New());
        mi2.addActionListener(new ActionListener_Open());
        mi3.addActionListener(new ActionListener_Save());
        mi4.addActionListener(new ActionListener_Compile());
        mi5.addActionListener(new ActionListener_Run());
    
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }  
        
    public void textReader()throws FileNotFoundException,IOException{
        BufferedReader br = new BufferedReader(new FileReader(str_pkg));
        String line;
        strb_content = new StringBuilder();
        while ((line = br.readLine())!= null){
            strb_content.append(line+"\n");
        }
        tf.setText(str_pkg);
        ta.setText(strb_content.toString());
        str_before = strb_content.toString();
        }
        
    public void textWriter()throws IOException{
        PrintWriter p = new PrintWriter(new FileWriter(str_pkg));
        String s = ta.getText();
        p.print(s);
        str_before = s;
        p.close();
    }
    public void Error(){
          try{
            int lineNum = regexSplitLineInt.get(cursorLineNum) - 1;
            int selectionStart = ta.getLineStartOffset(lineNum);
            int selectionEnd = ta.getLineEndOffset(lineNum);
            ta.requestFocus();
            ta.setSelectionStart(selectionStart);
            ta.setSelectionEnd(selectionEnd);
            cursorLineNum++;
            if(cursorLineNum >= regexSplitLineInt.size())
                cursorLineNum = 0;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
        
    public void regexSplit(String line){
        String regex = "(([a-zA-Z])\\w+.java:[0-9]+)";
        regexSplitLine.clear();
        Pattern ptn = Pattern.compile(regex);
        Matcher matcher = ptn.matcher(line);
        while(matcher.find()){
            regexSplitLine.add(matcher.group());
        }
        if(!regexSplitLine.isEmpty()){
            regexSplitLineInt.clear();
            for(String s : regexSplitLine){
                String sNum = s.substring(s.lastIndexOf(':') + 1, s.length());
                regexSplitLineInt.add(Integer.valueOf(sNum));
                System.out.println(sNum);
            }
        }
    }
         
    public void CompileNRun(){
        try {
            String arr[] = {"CLASSPATH=" + str_path, path};
            String cmd = "cmd /c javac " + str_filename2 + ".java && java " + str_filename2;
            Process proc1 = Runtime.getRuntime().exec(cmd, arr, new File(str_path));
            System.out.println(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(proc1.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            boolean isCompiled = false;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
                isCompiled = true;
            }
            br = new BufferedReader(new InputStreamReader(proc1.getErrorStream()));
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            if(isCompiled)
                ta.setText("Result is : \n" + sb.toString());
            else{
                regexSplit(sb.toString());
                ta.setText("Consist Errors : \n" + sb.toString() + "" +
                        "\n Compile Error: Please press F4 to skip to the error line");
            }
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    public void Run(){
        try {
            String arr[] = {"CLASSPATH=" + str_path, path};
            String cmd = "java " + str_filename2;
            Process proc1 = Runtime.getRuntime().exec(cmd, arr, new File(str_path));
            System.out.println(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(proc1.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            boolean isCompiled = false;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
                isCompiled = true;
            }
            br = new BufferedReader(new InputStreamReader(proc1.getErrorStream()));
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            if(isCompiled)
                ta2.setText("Result is : \n" + sb.toString());
            else{
                regexSplit(sb.toString());
                ta2.setText("Consist Errors): \n" + sb.toString() + "" +
                        "\n Compile Error: Please press F4 to skip to the error line");
            }
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    
    class DialogFrame extends JFrame{
        JPanel jp;
        JTextField jt1;
        JTextField jt2;
        JButton jb1;
        JButton jb2;
        String directories;
        String filename;
        
         public DialogFrame() {
            jp = new JPanel();
            jt1 = new JTextField("MyClass.java",40);
            jt2 = new JTextField(classpath,30);
            jb1 = new JButton("Choose Directories");
            jb2 = new JButton("Confirm");
            jp.add(jt1);
            jp.add(jt2);
            jp.add(jb1);
            jp.add(jb2);
            add(jp);
            jb1.addActionListener(new ActionListener_Choose_Directories());
            jb2.addActionListener(new ActionListener_ConfirmDialog());
         
            setSize(300, 300);
            setLocationRelativeTo(null);//
            setVisible(true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);//
         }
    
          class ActionListener_Choose_Directories implements ActionListener {
            public void actionPerformed(ActionEvent e){
                chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setCurrentDirectory(new File(classpath));
                chooser.showDialog(new JLabel(), "Choose Directories");
                File file = chooser.getSelectedFile();
                directories =  file.getAbsoluteFile().toString();
            }
        }    
          class ActionListener_ConfirmDialog implements ActionListener{
            public void actionPerformed(ActionEvent e){

                File file = new File(directories, filename);
                str_pkg = file.getAbsolutePath();
                System.out.println(file.exists());
                System.out.println(directories + " " + filename);
                try {
                    if(!file.exists()) {
                        file.createNewFile();
                    }
                    else
                        ;
                    str_pkg = file.getAbsolutePath();
                    str_path = file.getParent();
                    str_filename1 = file.getName();
                    str_filename2 = str_filename1.substring(0, str_filename1.lastIndexOf("."));
                    textReader();
                    setVisible(false);
                }
                catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
          class ActionListener_New implements ActionListener {
        public void actionPerformed(ActionEvent e){
            dialog_frame = new DialogFrame();
        }
    }
          class ActionListener_Choose_File implements ActionListener {
        public void actionPerformed(ActionEvent e){
            chooser = new JFileChooser();
            if(str_path != null && str_path !="")
                chooser.setCurrentDirectory(new File(str_path));
            else
                chooser.setCurrentDirectory(new File(classpath));
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            chooser.showDialog(new JLabel(), "Choose File");
            File file = chooser.getSelectedFile();
            try {
                str_pkg = file.getAbsoluteFile().toString();
                str_path = file.getParent();
                str_filename1 = file.getName();
                str_filename2 = str_filename1.substring(0, str_filename1.lastIndexOf("."));
                textReader();
                System.out.println(str_path+"\\"+str_filename2) ;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
          class ActionListener_Save implements ActionListener {
        public void actionPerformed(ActionEvent e){
            try {
                String s = ta.getText();
                if(!s.equals(str_before)){
                    textWriter();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
          class ActionListener_Compile implements ActionListener {
        public void actionPerformed(ActionEvent e){
            try {
                if (str_pkg != null && str_pkg != "") {
                    String s = ta.getText();
                    if (!s.equals(str_before)) {    // Check whether the file content is changed
                        textWriter();
                    }
                    System.out.println(str_path);
                    CompileNRun();
                } else
                    ta2.setText("Result: \n Didn't Opened!!!");
            }
            catch (Exception e4) {
                e4.printStackTrace();
            }
        }
    }
          class ActionListener_Run implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try {
                if (str_pkg != null && str_pkg != "") {
                    File file1 = new File(str_pkg);
                    File file2 = new File(str_path + "\\" + str_filename2 + ".class");
                    if (file2.exists()) {    // judge whether file is compiled
                        String s = ta.getText();
                        if (!s.equals(str_before)) {    // Check whether the file content is changed
                            textWriter();
                        }
                        if(file1.lastModified() <= file2.lastModified())    // Check the lastModified time
                            Run();
                        else
                            CompileNRun();
                    } else
                        CompileNRun();
                } else
                    ta2.setText("Result: \n Didn't Open!!!");
            }
            catch (Exception e4) {
                e4.printStackTrace();
            }
        }
    }                              
      } 
    public static void main(String[] args)throws IOException{
        Texteditor t = new Texteditor();
    }
}
    
    