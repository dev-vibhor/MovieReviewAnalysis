/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package splashdemo;

/**
 *
 * @author vibhor
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jsoup.select.*;
import static splashdemo.ParseTest.loadDictionary;
import static splashdemo.ParseTest.negativeCount;
import static splashdemo.ParseTest.positiveCount;

public class WebScrapper {
    public static long positiveCount = 0;
public static long negativeCount = 0;
public static String url="";
    /**
     * @param args the command line arguments
     */
    public void seturl(String x)
    {
    url=x;
    }
    public String geturl()
    {
    return url;
    }
    public void data() throws IOException {
        // TODO code application logic here 
    File file;
        file = new File("C:\\Users\\vibhor\\Documents\\NetBeansProjects\\SplashDemo\\SplashDemo\\src\\splashdemo\\file.txt");
     try {
     FileOutputStream fileOutputStream=new FileOutputStream(file);
      String url1 = geturl();
      Document doc = Jsoup.connect(url1).get();
      Elements paragraphs = doc.select("p");
      for(Element p : paragraphs)
      {System.out.println(p.text().toLowerCase());
       String t=p.text().toLowerCase();
       fileOutputStream.write(t.getBytes());
      }
      fileOutputStream.close();
    } 
    catch (IOException ex) {
      Logger.getLogger(WebScrapper.class.getName())
            .log(Level.SEVERE, null, ex);
    }
    critics();
         
    }
    public String critics() throws IOException
    {
     Document document = Jsoup.connect(geturl()).get();
     Elements stars= document.select(".score_panel");
String value = stars.text();

return( value+"\n");
    }
    
    public long func() throws FileNotFoundException, IOException
    {
     

    Set<String> positive = loadDictionary("C:\\Users\\vibhor\\Documents\\NetBeansProjects\\SplashDemo\\SplashDemo\\src\\splashdemo\\positiveword.txt");
    Set<String> negative = loadDictionary("C:\\Users\\vibhor\\Documents\\NetBeansProjects\\SplashDemo\\SplashDemo\\src\\splashdemo\\negativeword.txt");
    Set<String> intensifier = loadDictionary("C:\\Users\\vibhor\\Documents\\NetBeansProjects\\SplashDemo\\SplashDemo\\src\\splashdemo\\intense.txt"); 
     
      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\vibhor\\Documents\\NetBeansProjects\\SplashDemo\\SplashDemo\\src\\splashdemo\\file.txt")));
     int c=0;int d=0;
    Scanner sc = new Scanner(br);
    String str;
    String word;
    long p = 1;
    long n = 1;
    while (sc.hasNext()) {
        word = sc.next();    
        if(word.equals("not"))
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
   public long pos()
{return positiveCount;
}
public long neg()
{
return negativeCount;
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
}
}
