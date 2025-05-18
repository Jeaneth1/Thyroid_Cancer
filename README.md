# ** Thyroid Cancer Risk Prediction System **

A Java-based machine-learning application that predicts thyroid cancer risk using a Random Forest classifier. The system ingests patient risk‑factor data from CSV, trains a robust model, and delivers real‑time risk predictions along with personalized health recommendations.

**Features**

Data Processing
Cleans, normalizes, and handles missing values in large CSV datasets.
High/low Risk Prediction
Classifies patients as high‐risk or low‐risk based on demographic and risk factors.

Personalized Recommendations
Generates tailored advice to mitigate risk (e.g., dietary iodine adjustments, lifestyle changes).

Java Swing GUI
Intuitive desktop interface for data entry, batch import, and results display.

 Accuracy
Employs cross‑validated Random Forest (hyperparameter tuning) to achieve predictive performance.

**Requirements**

Java JDK 17 or higher

Maven for build and dependency management

4 GB+ RAM recommended for model training

Disk space for CSV datasets

_Installation_

Clone the repository:

git clone https://github.com/YourUsername/Thyroid_Cancer.git

cd Thyroid_Cancer

Build with Maven
mvn clean package

**Enter patient data**

Fill in Age, Gender, Iodine Level, and checkboxes for Smoking, Obesity, Radiation Exposure, Family History, Diabetes.

**Predict Risk and get recommendations**

Click the Predict Risk button

View the “Malignant” vs “Benign” classification and personalized recommendations in the text area.
