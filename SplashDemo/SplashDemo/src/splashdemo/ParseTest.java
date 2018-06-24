/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package splashdemo;

import java.awt.Container;
import java.io.*;
import java.util.Scanner;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
/**
 *
 * @author vibhor
 */
public class ParseTest extends JApplet{
  
public static long positiveCount = 0;
public static long negativeCount = 0;
public static long x=0;
public static String path="";
public static String dmname="";
public static String gifpath="";
    private Object mainframe;
    /**
     * @param args the command line arguments
     */
    public long func() throws FileNotFoundException, IOException
    {
   
    String sample=getpath();
    File file = new File(sample);
    Set<String> positive = loadDictionary("C:\\Users\\vibhor\\Documents\\NetBeansProjects\\SplashDemo\\SplashDemo\\src\\splashdemo\\positiveword.txt");
    Set<String> negative = loadDictionary("C:\\Users\\vibhor\\Documents\\NetBeansProjects\\SplashDemo\\SplashDemo\\src\\splashdemo\\negativeword.txt");
    Set<String> intensifier = loadDictionary("C:\\Users\\vibhor\\Documents\\NetBeansProjects\\SplashDemo\\SplashDemo\\src\\splashdemo\\intense.txt"); 
     
      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
     int c=0;int d=0;
    Scanner sc = new Scanner(br);
    String str;
    String word;
    long p = 1;
    long n = 1;
    while (sc.hasNext()) {
        word = sc.next();    
        if(word.equals("not")||word.equals("neither")||word.equals("nor"))
			{ 
		    
		        String word2=word;
		        word2=sc.next();
				String word3=word;
				if (positive.contains(word2)) {
            //System.out.println("Found positive "+p+":"+word);
                negativeCount+=1;p++;}
				
			
        if (negative.contains(word2)) {
            
            n++;positiveCount+=1;
        }
else
				{ do
				  {  word3=sc.next();c++;d++;
					 if (positive.contains(word3))
					 {
						negativeCount+=1;n++;
                         break;						
					 }
					 if( negative.contains(word3) )
					 {
						 positiveCount+=1;p++;
                         break;	
					 }
					
			}while((!positive.contains(word3)&&c<3)||(!positive.contains(word3)&&d<3));}
			
			}//not**************************************************************** over
		if(intensifier.contains(word))//
		{ String word1=word;
			word1=sc.next();
			
		if (positive.contains(word1)) {
            
            positiveCount+=2;p++;
		
        }
        if (negative.contains(word1)) {
            
            negativeCount+=2;n++;
        }	
			
		}//intensifier
        if (positive.contains(word)) {
            
            ++positiveCount;p++;
        }
        if (negative.contains(word)) {
           
            ++negativeCount;n++;
        }
    }
    
    br.close();
    
    return 0;
}
public long rate()
{return x;}
public long pos()
{return positiveCount;
}
public long neg()
{
return negativeCount;
}
public void setpath(String str)
{
 path=str;
}
public String getpath()
{
return path;
}
public void createpie()
{
DefaultPieDataset pieDataset = new DefaultPieDataset();
pieDataset.setValue("positive", positiveCount);
pieDataset.setValue("negative", negativeCount);
JFreeChart chart=ChartFactory.createPieChart("emotion", pieDataset, true, true,true);
PiePlot P=(PiePlot)chart.getPlot();
//P.setForegroundAlpha(TOP_ALIGNMENT);
ChartFrame frame=new ChartFrame("emotion",chart);
frame.setVisible(true);
frame.setSize(450,500);

 JFrame gui = new JFrame("Animation");

        
        Container contentPane = gui.getContentPane();
        contentPane.setLayout(null);
         if(negativeCount>positiveCount)
         {gifpath="C:\\Users\\vibhor\\Documents\\NetBeansProjects\\ParseTest\\src\\parsetest\\d.gif";  }
         else
         {gifpath="C:\\Users\\vibhor\\Documents\\NetBeansProjects\\ParseTest\\src\\parsetest\\Disappearing-likes.gif";  }
        JLabel img = new JLabel(new ImageIcon(gifpath));
        img.setBounds(10,10,300, 250); // x, y, width, height
        contentPane.add(img);
        gui.setSize(300,300);
        gui.setLocation(120,120);
        gui.setVisible(true);
       // gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

          
public static Set<String> loadDictionary(String fileName) throws FileNotFoundException, IOException  {
    Set<String> words = new HashSet<String>();
    File file = new File(fileName);
    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    Scanner sc = new Scanner(br);
    while (sc.hasNext()) {
        words.add(sc.next());
    }
    br.close();
    return words;
    }

public void resetall()
{
    positiveCount=0;
    negativeCount=0;
    path="";
}
}

