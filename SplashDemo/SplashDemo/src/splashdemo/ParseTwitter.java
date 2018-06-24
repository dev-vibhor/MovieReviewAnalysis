/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package splashdemo;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JApplet;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

/**
 *
 * @author vibhor
 */
public class ParseTwitter extends JApplet{
  
public static long positiveCount = 0;
public static long negativeCount = 0;
public static long x=0;
public static double input=0;

public static String path="";
public static String dmname="";
public static int nt=0;
public static String popu="";
public static String stat="";


public static Hashtable<String,Double> hashtable = new Hashtable<>();
//public static Map<String, Float> hashtable = new HashMap<>();

    private Object mainframe;
    /**
     * @param args the command line arguments
     */
    public long func() throws FileNotFoundException, IOException
    {String sample=getpath();
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
         Pattern pattern = Pattern.compile("(?<=<)\\s*([0-9]+)\\s*(?=>)");
        Matcher matcher = pattern.matcher(word);
        while(matcher.find()){
            //System.out.println("no of tweets: '" + matcher.group(1) + "'");
            nt=Integer.parseInt(matcher.group(1));
        }
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
    
  
    
if(positiveCount<negativeCount)
{
    input=(-1)*(negativeCount-positiveCount);
}
else
{
 input=(positiveCount-negativeCount);
}
Path pa = Paths.get(getpath());
String moviename=pa.getFileName().toString();
hashtable.put(moviename,((input/(positiveCount+negativeCount))*100));
System.out.println(((input/(positiveCount+negativeCount))*100));



double f=(double)((positiveCount+negativeCount)*100)/nt;
System.out.println(f);
String x2="";
if(f>35){x2="TRUE";}
else{x2="FALSE";}
System.out.println(" POPULARITY "+x2);
setpopularity(x2);
x2="";
System.out.println(" no of tweets "+nt);


    return 0;
}
    
     public String displayrank() 
    {//func();
        String stat="";
   Enumeration names;
   String key; String x1="";
   double d;
       names = hashtable.keys();
       while(names.hasMoreElements()) {
      key = (String) names.nextElement();
      d=(double)hashtable.get(key);
      //System.out.println("MOVIE: " +key+ " & SENTIMENT: " +hashtable.get(key));
      
      if(d>=70){x1="a must watch";}
      else if(d<70&&d>=55){x1="one time watch ";}
      else if(d<50){x1="dont watch";}
     
      
      setstatus(x1);
      //reset();
      x1="";
      return(" MOVIE: " +key+"\n" +"  SENTIMENT VALUE: " +hashtable.get(key)+ "\n"+" SENTIMENT STATUS : " +retstatus()+"\n"+" POPULARITY: "+retpopularity());
     
   }
      Hashtable<String,Double> hashtable = new Hashtable<>(); 
    return null;
    }
     public int nooftweets()
{return nt;
}

public long pos()
{return positiveCount;
}
public long neg()
{
return negativeCount;
}
public void setstatus(String str1)
{
    stat=str1;
}
public void setpopularity(String str2)
{
 popu=str2;
}


public String retstatus()
{
    return stat;
}
public String retpopularity()
{
return popu;
}
public void setpath(String str)
{
 path=str;
}
public String getpath()
{
return path;
}
public void reset()
{Hashtable<String,Double> hashtable = new Hashtable<>();
   positiveCount = 0;
   negativeCount = 0; stat="";popu="";
    path="";input=0;nt=0;
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
    Hashtable<String,Double> hashtable = new Hashtable<>();
    reset();
}
public void createpie()
{
DefaultPieDataset pieDataset = new DefaultPieDataset();
pieDataset.setValue("positive", pos());
pieDataset.setValue("negative", neg());
JFreeChart chart=ChartFactory.createPieChart("emotion", pieDataset, true, true,true);
PiePlot P=(PiePlot)chart.getPlot();
//P.setForegroundAlpha(TOP_ALIGNMENT);
ChartFrame frame=new ChartFrame("emotion",chart);
frame.setVisible(true);
frame.setSize(450,500);
}

public String add1() throws Exception
{


        ConverterUtils.DataSource source1 = new ConverterUtils.DataSource("C:\\Users\\vibhor\\Documents\\NetBeansProjects\\SplashDemo\\SplashDemo\\src\\splashdemo\\movies.numeric.arff");
        Instances train1 = source1.getDataSet();
        // setting class attribute if the data format does not provide this information
        // For example, the XRFF format saves the class attribute information as well
        if (train1.classIndex() == -1)
            train1.setClassIndex(train1.numAttributes() - 1);

        ConverterUtils.DataSource source2 = new ConverterUtils.DataSource("C:\\Users\\vibhor\\Documents\\NetBeansProjects\\SplashDemo\\SplashDemo\\src\\splashdemo\\test.arff");
        Instances test = source2.getDataSet();
        // setting class attribute if the data format does not provide this information
        // For example, the XRFF format saves the class attribute information as well
        if (test.classIndex() == -1)
            test.setClassIndex(train1.numAttributes() - 1);

        // model

        NaiveBayes naiveBayes = new NaiveBayes();
        naiveBayes.buildClassifier(train1);

        // this does the trick  
        double label = naiveBayes.classifyInstance(test.instance(0));
        test.instance(0).setClassValue(label);

        return(test.instance(0).stringValue(3));
        //System.out.println("Naive Bayes rating prediction:"+test.instance(1).stringValue(3));
        //System.out.println(test.instance(0).stringValue(5));
       
    }

public String add2() throws Exception
{


       ConverterUtils.DataSource source1 = new ConverterUtils.DataSource("C:\\Users\\vibhor\\Documents\\NetBeansProjects\\SplashDemo\\SplashDemo\\src\\splashdemo\\movies.numeric.arff");
        Instances train2 = source1.getDataSet();
        
        
        // setting class attribute if the data format does not provide this information
        // For example, the XRFF format saves the class attribute information as well
        if (train2.classIndex() == -1)
            train2.setClassIndex(train2.numAttributes() - 1);

        ConverterUtils.DataSource source2 = new ConverterUtils.DataSource("C:\\Users\\vibhor\\Documents\\NetBeansProjects\\SplashDemo\\SplashDemo\\src\\splashdemo\\test.arff");
        Instances test = source2.getDataSet();
        // setting class attribute if the data format does not provide this information
        // For example, the XRFF format saves the class attribute information as well
        if (test.classIndex() == -1)
            test.setClassIndex(train2.numAttributes() - 1);

        // model
                Classifier ibk = new IBk();	
        
        ibk.buildClassifier(train2);

        // this does the trick  
        double label = ibk.classifyInstance(test.instance(0));
        test.instance(0).setClassValue(label);

        return(test.instance(0).stringValue(3));
       
    
    }
public String add3() throws Exception
{
  ConverterUtils.DataSource source1 = new ConverterUtils.DataSource("C:\\Users\\vibhor\\Documents\\NetBeansProjects\\SplashDemo\\SplashDemo\\src\\splashdemo\\movies.numeric.arff");
        Instances train3 = source1.getDataSet();
        // setting class attribute if the data format does not provide this information
        // For example, the XRFF format saves the class attribute information as well
        if (train3.classIndex() == -1)
            train3.setClassIndex(train3.numAttributes() - 1);

        ConverterUtils.DataSource source2 = new ConverterUtils.DataSource("C:\\Users\\vibhor\\Documents\\NetBeansProjects\\SplashDemo\\SplashDemo\\src\\splashdemo\\test.arff");
        Instances test = source2.getDataSet();
        // setting class attribute if the data format does not provide this information
        // For example, the XRFF format saves the class attribute information as well
        if (test.classIndex() == -1)
            test.setClassIndex(train3.numAttributes() - 1);

        // model
       J48 tree=new J48();
        tree.buildClassifier(train3);
       


       
        tree.buildClassifier(train3);

        // this does the trick  
        double label = tree.classifyInstance(test.instance(0));
        test.instance(0).setClassValue(label);

        return(test.instance(0).stringValue(3));
       

}


}