package com.github.vedenin.useful_links;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.core.FastVector;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.StringReader;

/**
 * Created by vvedenin on 5/12/2016.
 */
public class WekaTest {
    private static final String TEST_STRING = "@relation weather\n" +
            "\n" +
            "@attribute outlook {sunny, overcast, rainy}\n" +
            "@attribute temperature numeric\n" +
            "@attribute humidity numeric\n" +
            "@attribute windy {TRUE, FALSE}\n" +
            "@attribute play {yes, no}\n" +
            "\n" +
            "@data\n" +
            "sunny,85,85,FALSE,no\n" +
            "sunny,80,90,TRUE,no\n" +
            "overcast,83,86,FALSE,yes\n" +
            "rainy,70,96,FALSE,yes\n" +
            "rainy,68,80,FALSE,yes\n" +
            "rainy,65,70,TRUE,no\n" +
            "overcast,64,65,TRUE,yes\n" +
            "sunny,72,95,FALSE,no\n" +
            "sunny,69,70,FALSE,yes\n" +
            "rainy,75,80,FALSE,yes\n" +
            "sunny,75,70,TRUE,yes\n" +
            "overcast,72,90,TRUE,yes\n" +
            "overcast,81,75,FALSE,yes\n" +
            "rainy,71,91,TRUE,no";

    public static BufferedReader readDataFile(String filename) {
        StringReader sr= new StringReader(TEST_STRING); // wrap your String
        return new BufferedReader(sr); // wrap your StringReader;
    }

    public static Evaluation classify(Classifier model,
                                      Instances trainingSet, Instances testingSet) throws Exception {
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

        for (int i = 0; i < numberOfFolds; i++) {
            split[0][i] = data.trainCV(numberOfFolds, i);
            split[1][i] = data.testCV(numberOfFolds, i);
        }

        return split;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader datafile = readDataFile("weather.txt");

        Instances data = new Instances(datafile);
        data.setClassIndex(data.numAttributes() - 1);

        // Do 10-split cross validation
        Instances[][] split = crossValidationSplit(data, 10);

        // Separate split into training and testing arrays
        Instances[] trainingSplits = split[0];
        Instances[] testingSplits = split[1];

        // Use a set of classifiers
        Classifier[] models = {
                new J48(), // a decision tree
                new PART(),
                new DecisionTable(),//decision table majority classifier
                new DecisionStump() //one-level decision tree
        };

        // Run for each model
        for (int j = 0; j < models.length; j++) {

            // Collect every group of predictions for current model in a FastVector
            FastVector predictions = new FastVector();

            // For each training-testing split pair, train and test the classifier
            for (int i = 0; i < trainingSplits.length; i++) {
                Evaluation validation = classify(models[j], trainingSplits[i], testingSplits[i]);

                predictions.appendElements(validation.predictions());

                // Uncomment to see the summary for each training-testing pair.
                //System.out.println(models[j].toString());
            }

            // Calculate overall accuracy of current classifier on all splits
            double accuracy = calculateAccuracy(predictions);

            // Print current classifier's name and accuracy in a complicated,
            // but nice-looking way.
            System.out.println("Accuracy of " + models[j].getClass().getSimpleName() + ": "
                    + String.format("%.2f%%", accuracy)
                    + "\n---------------------------------");
        }

    }


}
