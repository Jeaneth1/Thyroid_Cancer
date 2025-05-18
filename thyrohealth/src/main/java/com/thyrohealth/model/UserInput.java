
package com.thyrohealth.model;

import java.util.HashMap;
import java.util.Map;

public class UserInput {
    private int     age;
    private String  gender, iodine;
    private boolean smoking, obesity, radiationExposure, familyHistory, diabetes;

    public UserInput(int age,
                     String gender,
                     String iodine,
                     boolean smoking,
                     boolean obesity,
                     boolean radiationExposure,
                     boolean familyHistory,
                     boolean diabetes) {
        this.age                = age;
        this.gender             = gender;
        this.iodine             = iodine;
        this.smoking            = smoking;
        this.obesity            = obesity;
        this.radiationExposure  = radiationExposure;
        this.familyHistory      = familyHistory;
        this.diabetes           = diabetes;
    }

    // your existing getters...

    // ← NEW: pack into the same double[] shape as Node expects
    public double[] toArray() {
        double[] arr = new double[8];
        arr[0] = age;
        arr[1] = gender.equalsIgnoreCase("Male") ? 1.0 : 0.0;
        arr[2] = iodine.equalsIgnoreCase("Low")   ? 0.0
               : iodine.equalsIgnoreCase("Normal")? 1.0
                                                   : 2.0;
        arr[3] = smoking           ? 1.0 : 0.0;
        arr[4] = obesity           ? 1.0 : 0.0;
        arr[5] = radiationExposure ? 1.0 : 0.0;
        arr[6] = familyHistory     ? 1.0 : 0.0;
        arr[7] = diabetes          ? 1.0 : 0.0;
        return arr;
    }

    public Map<String,Object> toMap() {
      // if you still use RecommendationEngine on a Map
      Map<String,Object> m = new HashMap<>();
      m.put("age", age);
      m.put("gender", gender);
      m.put("iodine", iodine);
      m.put("smoking", smoking);
      m.put("obesity", obesity);
      m.put("radiationExposure", radiationExposure);
      m.put("familyHistory", familyHistory);
      m.put("diabetes", diabetes);
      return m;
    }
}
