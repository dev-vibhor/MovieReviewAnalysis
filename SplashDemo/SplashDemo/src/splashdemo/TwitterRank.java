/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package splashdemo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author vibhor
 */
public class TwitterRank {
  
public static long positiveCount = 0;
public static long negativeCount = 0;
public static long x=0;
public static String dmname=""; 
public static String path="";
public static String y="";
public static String tex="";
public static long lastID = Long.MAX_VALUE;

    /**
     * @param args the command line arguments
     */
static File f = null;
static int count=0;
static String FileName="";
public String func() throws FileNotFoundException, IOException {
        // TODO code application logic here
   
 FileName="a"+getdomain()+".txt";
f=new File(FileName);

       ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true)
      .setOAuthConsumerKey("9fOfJRB6dyetyv2zYMR72ZbMl")
      .setOAuthConsumerSecret("JDhYdXm1o0FVpjsroCqAaXKIJCO2xhU2ktRGpzI1H1dhVqdMvA")
      .setOAuthAccessToken("838266638382202880-y9loaqOkUMiS01CQUf8nvnlooeI4Nc1")
      .setOAuthAccessTokenSecret("yH90xsD1gydUUtvvehdVm1ThOepgjBlw71wnHbiCWdEHd");
      Twitter twitter = new TwitterFactory(cb.build()).getInstance();
      ArrayList<String> tweetList = new ArrayList<String>();
    //int numberOfTweets = 1000;
   
				
    
         //else 
        //System.out.println(i + " USER: " + user + " wrote: " + msg+"\n");ArrayList<String> tweetList = new ArrayList<String>();
		try {
			 Query query = new Query(getdomain());
                                             query.lang("en");
                         //query.setCount(100); 
                         query.setSince("2017-03-20");
			QueryResult result;
			do {
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				for (Status tweet : tweets) {
					tweetList.add(tweet.getText());
                                        //System.out.println(tweet.getText());
                                        count++;
                                        System.out.println("@" + tweet.getUser().getScreenName() + ":" + tweet.getText());
                                        setstring("@" + tweet.getUser().getScreenName() + ":" + tweet.getText());
                                        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f,true))) {

		

			bw.write(tweet.getText().toLowerCase());
     			// no need to close it.
			

			System.out.println("Done");
                       bw.close(); 

		} catch (IOException e) {

			e.printStackTrace();

		}
				}
			
                        } 
                        while ((query = result.nextQuery()) != null);
                        
		} 
                catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
                }
                String nt=Integer.toString(count);
                BufferedWriter bw = new BufferedWriter(new FileWriter(f,true));
                bw.write(""+"<"+nt+">");
                bw.close();
   return null;
    }
   
   public void setstring(String d)
   {
       tex+=d;
       
       
   }
   public String getstring()
   {
       
       
       return tex;
   }
 
public void setdomain(String dmn)
{
   dmname=dmn;
}
public String getdomain()
{
   return dmname;
}
public void func1() throws IOException
{   
   try (FileWriter fwOb = new FileWriter(FileName, false); 
        PrintWriter pwOb = new PrintWriter(fwOb, false)) {
        pwOb.flush();
        pwOb.close();
        fwOb.close();count=0;
       }
        dmname="";y="";
       FileName="";
        
}
}