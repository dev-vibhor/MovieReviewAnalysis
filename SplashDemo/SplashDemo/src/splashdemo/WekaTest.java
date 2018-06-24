
package splashdemo;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
 
public class WekaTest {
      public static String str="";
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
 
	public static Evaluation classify(Classifier model,Instances trainingSet, Instances testingSet) throws Exception {
		Evaluation evaluation = new Evaluation(trainingSet);
 
		model.buildClassifier(trainingSet);
		evaluation.evaluateModel(model, testingSet);
 
		return evaluation;
	}
 
	public static double calculateAccuracy(FastVector predictions) {
		double correct = 0;
 
		for (int i = 0; i < predictions.size(); i++) {
			NominalPrediction np = (NominalPrediction) predictions.elementAt(i);
			if (np.predicted() == np.actual()) {
				correct++;
			}
		}
 
		return 100 * correct / predictions.size();
	}
 
	public static Instances[][] crossValidationSplit(Instances data, int numberOfFolds) {
		Instances[][] split = new Instances[2][numberOfFolds];
 //Weka takes 100 labeled data
//it produces 10 equal sized sets. Each set is divided into two groups: 90 labeled data are used for training and 10 labeled data are used for testing.
//it produces a classifier with an algorithm from 90 labeled data and applies that on the 10 testing data for set 1.
//It does the same thing for set 2 to 10 and produces 9 more classifiers
//it averages the performance of the 10 classifiers produced from 10 equal sized (90 training and 10 testing) sets
		for (int i = 0; i < numberOfFolds; i++) {
			split[0][i] = data.trainCV(numberOfFolds, i);
			split[1][i] = data.testCV(numberOfFolds, i);
		}
 
		return split;
	}
 
	public  String func() throws Exception {
            String str="";
		BufferedReader datafile = readDataFile("C:\\Users\\vibhor\\Documents\\NetBeansProjects\\SplashDemo\\SplashDemo\\src\\splashdemo\\movies.txt");
 
		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
 
		// Do 10-split cross validation
		Instances[][] split = crossValidationSplit(data,10);
 
		// Separate split into training and testing arrays
		Instances[] trainingSplits = split[0];
		Instances[] testingSplits = split[1];
 
		// Use a set of classifiers
		Classifier[] models = { 
				new J48(), // a decision tree
				//new PART(), 
				//new DecisionTable(),//decision table majority classifier
				//new DecisionStump(), //one-level decision tree
                                new NaiveBayes(),
                                new IBk()	
                                
                                
                                
		};
 
		// Run for each model
		for (int j = 0; j < models.length; j++) {
 
			// Collect every group of predictions for current model in a FastVector
			FastVector predictions = new FastVector();
 
			// For each training-testing split pair, train and test the classifier
			for (int i = 0; i < trainingSplits.length; i++) {
				Evaluation validation = classify(models[j], trainingSplits[i], testingSplits[i]);
 
				predictions.appendElements(validation.predictions());
 
				//  summary for each training-testing pair.
				//System.out.println(models[j].toString());
			}
 
			// Calculate overall accuracy of current classifier on all splits
			double accuracy = calculateAccuracy(predictions);
                       
			
			System.out.println("Accuracy of " + models[j].getClass().getSimpleName() + ": "
					+ String.format("%.2f%%", accuracy)
					+ "\n---------------------------------");
                       str+= "Accuracy using crossvalidatesplit(10 folds)  of " + models[j].getClass().getSimpleName() + ": "+ String.format("%.2f%%", accuracy)+"\n";
                       setstring(str);
                       
                        
                          
                            
                      //for(int x=0;x<=8;x++){
                       //System.out.println("Precision= " + eval.precision(x)+" Recall= " + eval.recall(x)+" F-measure= " + eval.fMeasure(x)+" tp rate= " +eval.numTruePositives(x));
		       //}
                       
                       
		}
           knear();
           nai();
           tr();
 
return null;	
        }
        public void setstring(String d)
        {
        str1=d;
        
        }
        public String getstring()
        {
        return str1;
        }
        public  void knear() throws Exception
            
    {System.out.println("KNN(IBK)----------------------");
         ConverterUtils.DataSource source1 = new ConverterUtils.DataSource("C:\\Users\\vibhor\\Documents\\NetBeansProjects\\SplashDemo\\SplashDemo\\src\\splashdemo\\movies.numeric.arff");
        Instances train = source1.getDataSet();
         if (train.classIndex() == -1)
            train.setClassIndex(train.numAttributes() - 1);
        BufferedReader datafile = readDataFile("C:\\Users\\vibhor\\Documents\\NetBeansProjects\\SplashDemo\\SplashDemo\\src\\splashdemo\\movies.txt");
    
 Instances test = new Instances(datafile);
test.setClassIndex(test.numAttributes() - 1);
 Classifier ibk = new IBk();	
 ibk.buildClassifier(train);
 Evaluation evaluation = new Evaluation(train);
 evaluation.evaluateModel(ibk, test);
 System.out.println(evaluation.toSummaryString());
 System.out.println("Precision= " + evaluation.weightedPrecision()+" Recall= " + evaluation.weightedRecall()+" F-measure= " + evaluation.weightedFMeasure());                     
                     System.out.println("TP= " + evaluation.weightedTruePositiveRate());
                     System.out.println("TN= " + evaluation.weightedTrueNegativeRate());
                     System.out.println("FP= " + evaluation.weightedFalsePositiveRate());
                     System.out.println("FN= " + evaluation.weightedFalseNegativeRate());
    }
         public  void nai() throws Exception
    {
    System.out.println("naive bayes---------------------");
         ConverterUtils.DataSource source1 = new ConverterUtils.DataSource("C:\\Users\\vibhor\\Documents\\NetBeansProjects\\SplashDemo\\SplashDemo\\src\\splashdemo\\movies.numeric.arff");
        Instances train = source1.getDataSet();
         if (train.classIndex() == -1)
            train.setClassIndex(train.numAttributes() - 1);
        BufferedReader datafile = readDataFile("C:\\Users\\vibhor\\Documents\\NetBeansProjects\\SplashDemo\\SplashDemo\\src\\splashdemo\\movies.txt");
    
 Instances test = new Instances(datafile);
test.setClassIndex(test.numAttributes() - 1);
 NaiveBayes naiveBayes = new NaiveBayes();
        naiveBayes.buildClassifier(train);	
 
 Evaluation evaluation = new Evaluation(train);
 evaluation.evaluateModel(naiveBayes, test);
 System.out.println(evaluation.toSummaryString());
 System.out.println("Precision= " + evaluation.weightedPrecision()+" Recall= " + evaluation.weightedRecall()+" F-measure= " + evaluation.weightedFMeasure());                     
                     System.out.println("TP= " + evaluation.weightedTruePositiveRate());
                     System.out.println("TN= " + evaluation.weightedTrueNegativeRate());
                     System.out.println("FP= " + evaluation.weightedFalsePositiveRate());
                     System.out.println("FN= " + evaluation.weightedFalseNegativeRate());
                        
    
    }
         public void tr() throws Exception
     {  System.out.println("D TREE-----------------------------");
     
         ConverterUtils.DataSource source1 = new ConverterUtils.DataSource("C:\\Users\\vibhor\\Documents\\NetBeansProjects\\SplashDemo\\SplashDemo\\src\\splashdemo\\movies.numeric.arff");
        Instances train = source1.getDataSet();
         if (train.classIndex() == -1)
            train.setClassIndex(train.numAttributes() - 1);
        BufferedReader datafile = readDataFile("C:\\Users\\vibhor\\Documents\\NetBeansProjects\\SplashDemo\\SplashDemo\\src\\splashdemo\\movies.txt");
    
 Instances test = new Instances(datafile);
test.setClassIndex(test.numAttributes() - 1);

   J48 tree=new J48();
        tree.buildClassifier(train);
       
 Evaluation evaluation = new Evaluation(train);
 evaluation.evaluateModel(tree, test);
 System.out.println(evaluation.toSummaryString());
  System.out.println("Precision= " + evaluation.weightedPrecision()+" Recall= " + evaluation.weightedRecall()+" F-measure= " + evaluation.weightedFMeasure());                     
                    System.out.println("TP= " + evaluation.weightedTruePositiveRate());
                    System.out.println("TN= " + evaluation.weightedTrueNegativeRate());
                    System.out.println("FP= " + evaluation.weightedFalsePositiveRate());
                    System.out.println("FN= " + evaluation.weightedFalseNegativeRate());
                        
     
     }
    
}