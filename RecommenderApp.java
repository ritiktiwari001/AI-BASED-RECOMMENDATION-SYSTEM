package com.example;

// Mahout imports for recommendation system
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import java.io.File;
import java.util.List;

/**
 * RecommenderApp
 *
 * A simple Java program that uses Apache Mahout to create a user-based
 * collaborative filtering recommender system.
 *
 * It reads user-item preference data from a CSV file,
 * calculates user similarity using Pearson correlation,
 * defines the user's neighborhood,
 * generates recommendations for a given user,
 * and prints them.
 */
public class RecommenderApp {

    public static void main(String[] args) {
        try {
            // 1. Load the user-item ratings data from CSV file
            File dataFile = new File("data.csv");
            DataModel model = new FileDataModel(dataFile);

            // 2. Compute user similarity using Pearson Correlation
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

            // 3. Define the neighborhood (nearest 2 users)
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

            // 4. Create the user-based recommender
            Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            // 5. Generate recommendations for user with ID 1
            List<RecommendedItem> recommendations = recommender.recommend(1, 3);

            // 6. Print the recommended items
            System.out.println("Recommendations for User 1:");
            for (RecommendedItem item : recommendations) {
                System.out.println("Item ID: " + item.getItemID() + ", Score: " + item.getValue());
            }

        } catch (Exception e) {
            // Print the stack trace if any error occurs
            e.printStackTrace();
        }
    }
}
