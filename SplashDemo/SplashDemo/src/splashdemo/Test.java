/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package splashdemo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class Test {
public static String path=""; 
public static String dmname=""; 
public static String line;
public static long positiveCount = 0;
public static long negativeCount = 0;
    
    public void store()throws FileNotFoundException, IOException 
    {
        try {
			File file = new File(getpath());
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			fileReader.close();
			System.out.println("Contents of file:");
			System.out.println(stringBuffer.toString());
                        filter(stringBuffer.toString());
		} catch (IOException e) {
			//e.printStackTrace();
                }
    }

    public void filter(String x) throws IOException{
String domain=getdomain();
System.out.println(domain);
    String y=x;
    
        for (int n = 1; n <= 5; n++) {
  
            for (String ngram : ngrams(n,y))
             if(n==5&&(ngram.contains(domain)))
                 {try (FileWriter fw = new FileWriter("C:\\Users\\vibhor\\Documents\\NetBeansProjects\\SplashDemo\\SplashDemo\\src\\splashdemo\\s.txt",true)) {
                     BufferedWriter bw = new BufferedWriter(fw);
                     System.out.println(ngram);
                     fw.write(" "+ngram.toLowerCase());
                }
                System.out.println(ngram);
	
	
                 }
            System.out.println();
        }
    }
    public static List<String> ngrams(int n, String str) {
        List<String> ngrams = new ArrayList<>();
        String[] words = str.split(" ");//spilts text with space and stores in array
        for (int i = 0; i < words.length - n + 1; i++)//fpr loop till array lenght an other terms
            ngrams.add(concat(words, i, i+n));// conacat grams of word
        return ngrams;
    }

    public static String concat(String[] words, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++)
            sb.append((i > start ? " " : "") + words[i]);
        return sb.toString();
    }
    public void setpath(String str)
{
 path=str;
}
public String getpath()
{
return path;
}
public void setdomain(String dmn)
{
    dmname=dmn;
}
public String getdomain()
{
    return dmname;
}
    public void reset() throws IOException
    {positiveCount=0;
    negativeCount=0;
    String FileName="C:\\Users\\vibhor\\Documents\\NetBeansProjects\\SplashDemo\\SplashDemo\\src\\splashdemo\\s.txt";
     try (FileWriter fwOb = new FileWriter(FileName, false); 
        PrintWriter pwOb = new PrintWriter(fwOb, false)) {
        pwOb.flush();
        pwOb.close();
        fwOb.close();
       }
    }
     public long func() throws FileNotFoundException, IOException
     {
    File file = new File("C:\\Users\\vibhor\\Documents\\NetBeansProjects\\SplashDemo\\SplashDemo\\src\\splashdemo\\s.txt");
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