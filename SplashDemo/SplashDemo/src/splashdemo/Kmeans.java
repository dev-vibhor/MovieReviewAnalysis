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
import java.io.FileNotFoundException;
import java.io.FileReader;
 
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
 
public class Kmeans {
 public static String str1="";
	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;
 
		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}
 
		return inputReader;
	}
 
	public void func() throws Exception {
		SimpleKMeans kmeans = new SimpleKMeans();
 
		kmeans.setSeed(10);
 
		//important parameter to set: preserver order, number of cluster.
		kmeans.setPreserveInstancesOrder(true);
		kmeans.setNumClusters(3);
 
		BufferedReader datafile = readDataFile("C:\\Users\\vibhor\\Documents\\NetBeansProjects\\SplashDemo\\SplashDemo\\src\\splashdemo\\data.arff"); 
		Instances data = new Instances(datafile);
 
 
		kmeans.buildClusterer(data);
 
		// This array returns the cluster number (starting with 0) for each instance
		// The array has as many elements as the number of instances
		int[] assignments = kmeans.getAssignments();
  String s = null;
  String stat="";String str="";
		int i=0;
		for(int clusterNum : assignments) {
                     s = data.attribute(2).value(i);
                    
		    System.out.printf("Movie %d ->"+s+" Cluster %d \n", i, clusterNum);
                    str+="Movie->:"+Integer.toString(i)+s+" Cluster->:"+Integer.toString(clusterNum)+"\n";
                    setstring(str);
		    i++;
		}
                
	}
        public void setstring(String d)
        {
        str1=d;
        
        }
        public String getstring()
        {
        return str1;
        }
}