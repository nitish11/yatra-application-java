import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.swing.*;

public class usermode
{

     int x,y,z,k,i;
     int [][]time_id = {{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15},{16,17,18,19,20},{21,22,23,24,25},{26,27,28,29,30,},{31,32,33,34,35}};
     int [] value = new int[100];
    
     JComboBox combo1,combo2,combo3,combo4;
     JLabel jlabel1,jlabel2,jlabel3,jlabel4,jlabel5,jlabel6;
     JScrollPane js1,js2;
     JTextField txt1,txt2,txt3,txt4;
     JTextArea ta;
     JButton button;
  
         final String day[] = {"SELECT","MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY","SUNDAY"};
         String time[] = {"SELECT","7:00-8:00","8:00-9:00","9:00-10:00","10:00-11:00","11:00-12:00"};
  
             String str,str1;
  
     public usermode()
     {
         JFrame frame = new JFrame("User input");
         JPanel panel = new JPanel();
         JPanel panel1;
  
         panel.setLayout(new GridLayout(7,10,1,5));
  
             jlabel1 = new JLabel("Source");panel.add(jlabel1);
			 
             combo3 = new JComboBox(); //combox is created for source drop-down
             combo4 = new JComboBox(); //combox is created for destination drop-down
			 
             combo3.addItem("SELECT"); 
			 
             combo4.addItem("SELECT");
  
                 try
                 {
                     URL place_info = new URL("http://localhost/btp/place_info.txt");
                     BufferedReader br = new BufferedReader(new InputStreamReader(place_info.openStream()));
                         i=0;
                         while((str = br.readLine())!= null)
                         {
                             String[] num = str.split(" ");
                             value[i] = Integer.parseInt(num[0]);
                             combo3.insertItemAt(num[1],value[i]);
                             combo4.insertItemAt(num[1],value[i]);
                             i++;
                         }
					 br.close();
                 }
                 catch(Exception ex){}
  
             panel.add(combo3);

             jlabel2 = new JLabel("Destination");panel.add(jlabel2);

             panel.add(combo4);

             //jlabel3 = new JLabel("Avg. Speed");panel.add(jlabel3);txt3 = new JTextField(10);panel.add(txt3);
  
             jlabel4 = new JLabel("Select day");panel.add(jlabel4);combo1 = new JComboBox(day);panel.add(combo1);
  
             jlabel5 = new JLabel("Select time");panel.add(jlabel5);combo2 = new JComboBox(time);panel.add(combo2);
  
             combo3.addActionListener(new ActionListener() {
  
                 @Override
                     public void actionPerformed(ActionEvent e) {
                     JComboBox cb = (JComboBox) e.getSource();
                     String newSelection = (String) cb.getSelectedItem();
    
                     if(!newSelection.equals("SELECT"))
                     z = cb.getSelectedIndex();
                     }
             });
  
             combo4.addActionListener(new ActionListener() {
  
                 @Override
                     public void actionPerformed(ActionEvent e) {
                     JComboBox cb = (JComboBox) e.getSource();
                     String newSelection = (String) cb.getSelectedItem();
    
                     if(!newSelection.equals("SELECT"))
                     k = cb.getSelectedIndex();
                     }
             });
		

             combo1.addActionListener(new ActionListener() {
  
                 @Override
                     public void actionPerformed(ActionEvent e) {
                     JComboBox cb = (JComboBox) e.getSource();
                     String newSelection = (String) cb.getSelectedItem();
    
                     if(newSelection.equals(day[1]))
                         x=0;
                     else if(newSelection.equals(day[2]))
                         x=1;
                     else if(newSelection.equals(day[3]))
                         x=2;
                     else if(newSelection.equals(day[4]))
                         x=3;
                     else if(newSelection.equals(day[5]))
                         x=4;
                     else if(newSelection.equals(day[6]))
                         x=5;
                     else if(newSelection.equals(day[7]))
                         x=6;
                     }
             });
		
             combo2.addActionListener(new ActionListener() {

                 @Override
                     public void actionPerformed(ActionEvent e) {
                     JComboBox cb = (JComboBox) e.getSource();
                     String newSelection = (String) cb.getSelectedItem();
   
                     if(newSelection.equals(time[1]))
                         y=0;
                     else if(newSelection.equals(time[2]))
                         y=1;
                     else if(newSelection.equals(time[3]))
                         y=2;
                     else if(newSelection.equals(time[4]))
                         y=3;
                     else if(newSelection.equals(time[5]))
                         y=4;
   
                     }
             });
  
		
             button = new JButton("Submit");
             panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
             panel1.add(button);
             panel.add(panel1);

             JSeparator north = new JSeparator(JSeparator.HORIZONTAL);
             panel.add(north);
  
             jlabel6 = new JLabel("Desired path");
             panel.add(jlabel6);
  
             ta = new JTextArea(1,15);

             ta.setEditable(false);
             ta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
   
             panel.add(ta);
  
             button.registerKeyboardAction(button.getActionForKeyStroke(
                                      KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)),
                                      KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
                                      JComponent.WHEN_FOCUSED);
             button.registerKeyboardAction(button.getActionForKeyStroke(
                                      KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true)),
                                      KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
                                      JComponent.WHEN_FOCUSED);
  
             button.addActionListener(new ActionListener() {
  
                 @Override
                     public void actionPerformed(ActionEvent e) {
             
                         try{
                                 CostEstimate ce = new CostEstimate();
             
                                 int sr = z;
                                 int dest = k;
                                 //int spd = Integer.parseInt(txt3.getText());
				                 int spd = 30;
                                 int rt = time_id[x][y];
                
                                 ce.estimate(sr,dest,spd,rt);
								 
                                     try{
                
                                             FileInputStream fs = new FileInputStream("desiredpath.txt");
                                             BufferedReader br = new BufferedReader(new InputStreamReader(fs));
                                             while((str = br.readLine())!= null)
                                             {  
											     
                                                 ta.setText("");
                                                 ta.requestFocus();
                
                                                 String[] num = str.split(" ");
												 
												 
                                                 for(i=0;;)
                                                 { 
												     
				                                     
													 if(num[i].equals("1") || num[i].equals("2") || num[i].equals("3") || num[i].equals("4") || num[i].equals("5") || num[i].equals("6") || num[i].equals("7") || num[i].equals("8") || num[i].equals("9") || num[i].equals("10") || num[i].equals("11") || num[i].equals("12") || num[i].equals("13") || num[i].equals("14") || num[i].equals("15") || num[i].equals("16") || num[i].equals("17") || num[i].equals("18") || num[i].equals("19") || num[i].equals("20") || num[i].equals("21") || num[i].equals("22") || num[i].equals("23") || num[i].equals("24") || num[i].equals("25") || num[i].equals("26") || num[i].equals("27") || num[i].equals("28") || num[i].equals("29"))
				                                     {
				                                         URL place_info = new URL("http://localhost/btp/place_info.txt");
                                                         BufferedReader br1 = new BufferedReader(new InputStreamReader(place_info.openStream()));
				                                         
														 while((str1 = br1.readLine())!=null)
				                                         {
				                                             
															 String[] num1 = str1.split(" ");
				                                             
															 if(num[i].equals(num1[0]))
				                                             {
                                                                 ta.append(num1[1]);	
				                                             } 
                                                         }	
                                                         
														 ta.append(" ");	
                                                         i++;														 
				                                     }
													 
													 else
													 {
													     ta.append(num[i]);
														 ta.append(" ");
														 i++;
													 }
                                                 }
                   
                                             }
                
                                         }
                
                                          catch(IOException | NumberFormatException ex)
                                          {}
                     
					         }
                             catch(Exception ex)
                             {}
               
                 }
             });
		
       
             frame.add(panel);
             frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             frame.setSize(300,300);
             frame.setResizable(true);
             frame.setVisible(true);
     }
	 
     public static void main(String[] args) {
         usermode uml = new usermode();
     }
	 
}
