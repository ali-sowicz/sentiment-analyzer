import java.util.Arrays;


public class SentimentAnalyzer {
    public static int[] detectProsAndCons(String review, String[][] featureSet, String[] posOpinionWords,
                                          String[] negOpinionWords) {
        int[] featureOpinions = new int[featureSet.length]; // output
        int opinion = 0;

        for (int i = 0; i < featureSet.length; i++){
            for(int j = 0; j < featureSet[i].length ; j++){
                if(review.contains(featureSet[i][j])){
                    String feature = featureSet[i][j];
                    opinion = getOpinionOnFeature(review,feature,posOpinionWords,negOpinionWords);
                    featureOpinions[i] = opinion;
                }
            }
        }

        return featureOpinions;
    }

    // First invoke checkForWasPhrasePattern and
    // if it cannot find an opinion only then invoke checkForOpinionFirstPattern
    private static int getOpinionOnFeature(String review, String feature, String[] posOpinionWords, String[] negOpinionWords) {
       int opinion;

        opinion = checkForWasPhrasePattern(review,feature,posOpinionWords,negOpinionWords);
        if(opinion == 0) {
            opinion = checkForOpinionFirstPattern(review, feature, posOpinionWords, negOpinionWords);
        }

    return opinion ;
    }

    // Return 1 if positive opinion found, -1 for negative opinion, 0 for no opinion
    private static int checkForWasPhrasePattern(String review, String feature, String[] posOpinionWords, String[] negOpinionWords) {
        int opinion = 0;
        String op = "";

        String[] words = review.split(" ");
        for (int i = 0; i < words.length ; i++) {
            if(words[i].equals(feature)){
                if(words[i+1].equals("was")){
                    op = op.concat(words[i+2]);
                    if(op.contains("!")) op = op.replace("!","");
                    if(op.contains(",")) op = op.replace(",","");
                }
            }
        }


        for (int i = 0; i < posOpinionWords.length ; i++) {
            if(posOpinionWords[i].equals(op)){
                opinion = 1;
                return opinion;
            }
            else opinion = 0;
        }

        for (int i = 0; i < negOpinionWords.length ; i++) {
            if(negOpinionWords[i].equals(op)){
                opinion = -1;
                return opinion;
            }
        }
       // System.out.println(opinion);
        return opinion;
    }

    
    private static int checkForOpinionFirstPattern(String review, String feature, String[] posOpinionWords,
                                                   String[] negOpinionWords) {
        // Extract sentences as feature might appear multiple times.
        // split() takes a regular expression and "." is a special character
        // for regular expression. So, escape it to make it work!!
        String[] sentences = review.toLowerCase().split("\\.");
        int opinion = 0;
        String op = "";

        for(int i =0; i<sentences.length; i++) {
            String[] words = sentences[i].split(" ");
            for (int j = 0; j < words.length; j++) {
                if (words[j].contains(",")) words[j] = words[j].replace(",", "");
                if (words[j].contains("!")) words[j] = words[j].replace("!", "");
                if (words[j].equals(feature)) {
                    op = op.concat(words[j - 1]);
                    if (op.contains("!")) op = op.replace("!", "");
                   // System.out.println(op);
                }
            }

            for (int k = 0; k < posOpinionWords.length; k++) {
                if (posOpinionWords[k].equals(op)) {
                    opinion = 1;

                    return opinion;
                } else opinion = 0;
            }
            for (int l = 0; l < negOpinionWords.length; l++) {
                if (negOpinionWords[l].equals(op)) {
                    opinion = -1;

                    return opinion;
                }
            }
        }
       // System.out.println(opinion);
        return opinion;
    }

    public static void main(String[] args) {
       String review = "Haven't been here in years! Fantastic service and the food was delicious! Definitely will be a frequent flyer! Francisco was very attentive";

       // String review = "Sorry OG, but you just lost some loyal customers. Horrible service, no smile or greeting just attitude. The breadsticks were stale and burnt, appetizer was cold and the food came out before the salad.";

        String[][] featureSet = {
                { "ambiance", "ambience", "atmosphere", "decor" },
                { "dessert", "ice cream", "desert" },
                { "food" },
                { "soup" },
                { "service", "management", "waiter", "waitress", "bartender", "staff", "server" } };
        String[] posOpinionWords = { "good", "fantastic", "friendly", "great", "excellent", "amazing", "awesome",
                "delicious" };
        String[] negOpinionWords = { "slow", "bad", "horrible", "awful", "unprofessional", "poor", "cold" };


        int[] featureOpinions = detectProsAndCons(review, featureSet, posOpinionWords, negOpinionWords);
        System.out.println("Opinions on Features: " + Arrays.toString(featureOpinions));


    }
}
