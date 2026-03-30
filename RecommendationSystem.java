import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;

import java.io.File;
import java.util.List;

public class RecommendationSystem {

    public static void main(String[] args) {
        try {
            // Load data
            DataModel model = new FileDataModel(new File("data.csv"));

            // Similarity calculation
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

            // Find nearest neighbors
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

            // Create recommender
            UserBasedRecommender recommender =
                    new GenericUserBasedRecommender(model, neighborhood, similarity);

            // Get recommendations for User 1
            List<RecommendedItem> recommendations = recommender.recommend(1, 3);

            System.out.println("Recommendations for User 1:");

            for (RecommendedItem item : recommendations) {
                System.out.println("Item ID: " + item.getItemID() +
                                   " | Predicted Rating: " + item.getValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}