// package com.thyrohealth.model;

// import org.junit.jupiter.api.*;
// import static org.junit.jupiter.api.Assertions.*;

// import java.io.*;
// import java.nio.file.*;
// import java.util.*;

// public class TestSuite {

//     // helper to write out a small CSV and return its path
//     private File createTempCSV(String content) throws IOException {
//         Path p = Files.createTempFile("thyroid_test_", ".csv");
//         Files.writeString(p, content);
//         return p.toFile();
//     }

//     // —— Node tests ——

//     @Test
//     void testFeatureRetrieval() {
//         double[] feats = {1.0, 2.0, 3.0};
//         Node n = new Node(feats, 1);
//         assertEquals(1.0, n.getFeature(0), 1e-6);
//         assertEquals(2.0, n.getFeature(1), 1e-6);
//         assertEquals(3.0, n.getFeature(2), 1e-6);
//     }

//     @Test
//     void testMissingFeatureHandling() {
//         Node n = new Node(new double[]{1.0, 2.0}, 0);
//         assertTrue(Double.isNaN(n.getFeature(2)),
//                    "out‑of‑bounds index should return NaN");
//     }

//     @Test
//     void testNodeLabel() {
//         Node n = new Node(new double[]{0.0}, 42);
//         assertEquals(42, n.getLabel());
//     }


//     // —— DataLoader tests ——
//     @Test
//     void testBasicDataLoading() throws Exception {
//         String csv =
//             "Patient_ID,Age,Gender,Iodine,Smoking,Obesity,RadiationExposure,FamilyHistory,Diabetes,Diagnosis\n" +
//             "Integer,Integer,String,String,Boolean,Boolean,Boolean,Boolean,Boolean,Binary\n" +
//             "1001,55,Male,Low,No,Yes,No,No,Yes,1\n" +
//             "1002,30,Female,High,Yes,No,Yes,Yes,No,0\n";

//         File f = createTempCSV(csv);
//         List<Node> loaded = DataLoader.load(f.getAbsolutePath());
//         assertEquals(2, loaded.size());

//         Node first = loaded.get(0);
//         assertEquals(55.0, first.getFeature(0), 1e-6);
//         assertEquals(0.0,  first.getFeature(2), 1e-6); // Low→0.0
//         assertEquals(0.0,  first.getFeature(3), 1e-6); // Smoking No→0.0
//         assertEquals(1,    first.getLabel());

//         f.delete();
//     }

//     @Test
//     void testHeaderSkipping() throws Exception {
//         // if we forget the data‐type header it would try to parse "Age" as int
//         String csv =
//             "Patient_ID,Age,Gender,Iodine,Smoking,Obesity,RadiationExposure,FamilyHistory,Diabetes,Diagnosis\n" +
//             "1001,55,Male,Normal,No,No,No,No,No,0\n";

//         // only one header → your loader must skip two or will fail
//         File f = createTempCSV(csv);
//         // we expect no IOException, but the result should be empty
//         List<Node> loaded = DataLoader.load(f.getAbsolutePath());
//         assertTrue(loaded.isEmpty(), "must skip malformed rows");
//         f.delete();
//     }


//     // —— DecisionTree tests ——
//     @Test
//     void testDecisionTreeTraining() {
//         // simple two‐point dataset
//         List<Node> train = List.of(
//             new Node(new double[]{0,0,0,0,0,0,0,0}, 0),
//             new Node(new double[]{1,1,1,1,1,1,1,1}, 1)
//         );
//         DecisionTree tree = new DecisionTree(3, 8);
//         tree.train(train);
//         assertNotNull(tree.root, "root must be constructed");
//         double[] imps = tree.getFeatureImportances();
//         assertEquals(8, imps.length);
//     }


//     // —— RandomForest tests ——
//     @Test
//     void testForestAccuracy() {
//         var data = new ArrayList<Node>();
//         // make 50 of each class
//         for (int i = 0; i < 50; i++) {
//             data.add(new Node(new double[]{0,0,0,0,0,0,0,0}, 0));
//             data.add(new Node(new double[]{1,1,1,1,1,1,1,1}, 1));
//         }
//         RandomForest rf = new RandomForest(10, 4);
//         rf.train(data);
//         double acc = rf.evaluate(data) / 100.0; // evaluate returns pct‐correct
//         assertTrue(acc > 0.9, "accuracy should be > 90% on clear data");
//     }

//     @Test
//     void testConcurrentPredictions() throws Exception {
//         var data = List.of(
//             new Node(new double[]{0,0,0,0,0,0,0,0}, 0),
//             new Node(new double[]{1,1,1,1,1,1,1,1}, 1)
//         );
//         RandomForest rf = new RandomForest(5, 3);
//         rf.train(data);

//         int threads = 20;
//         var latch = new java.util.concurrent.CountDownLatch(threads);
//         var results = Collections.synchronizedList(new ArrayList<Integer>());

//         for (int i = 0; i < threads; i++) {
//             new Thread(() -> {
//                 try {
//                     int p = rf.predict(new double[]{1,1,1,1,1,1,1,1});
//                     results.add(p);
//                 } finally {
//                     latch.countDown();
//                 }
//             }).start();
//         }
//         assertTrue(latch.await(2, java.util.concurrent.TimeUnit.SECONDS));
//         assertEquals(threads, results.size());
//     }


//     // —— RecommendationEngine tests ——
//     @Test
//     void testRecommendationEngineLowRisk() {
//         Map<String,Object> in = new HashMap<>();
//         in.put("age", 40);
//         in.put("gender", "Female");
//         in.put("iodine", "Low");
//         in.put("smoking", Boolean.FALSE);
//         in.put("obesity", Boolean.FALSE);
//         in.put("radiationExposure", Boolean.FALSE);
//         in.put("familyHistory", Boolean.FALSE);
//         in.put("diabetes", Boolean.FALSE);

//         List<String> recs = RecommendationEngine.generateRecommendations(in, false);
//         assertTrue(recs.get(0).contains("Low Risk"));
//         assertTrue(recs.stream()
//                        .anyMatch(s -> s.contains("Iodine level is LOW")),
//                    "Should mention iodine suggestion");
//     }
// }
