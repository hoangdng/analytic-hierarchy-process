import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Ahp
{
    private String _projectName;
    private String[] _alternatives;
    private Matrix _criteriaPreferenceMatrix;
    private Criterion[] _criteria;


    public Ahp()
    {
        readDataFromInputFileAdvance();
    }

    //For original Ahp method
    public static double[][] parseMatrixFromJsonArray(JSONArray jsonArray)
    {
        double[][] targetArray = new double[jsonArray.size()][jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++)
        {
            for (int j = 0; j < jsonArray.size(); j++)
            {
                targetArray[i][j] = Double.parseDouble(((JSONArray) jsonArray.get(i)).get(j).toString());
            }
        }
        return targetArray;
    }

    //For advanced Ahp method
    public static double[][] parseMatrixFromJsonArrayAdvance(JSONArray jsonArray)
    {
        double[][] targetArray = new double[jsonArray.size()][jsonArray.size()];

        //Parse the array to the 1st row of matrix
        for (int i = 0; i < jsonArray.size(); i++)
        {
            targetArray[0][i] = Double.parseDouble(jsonArray.get(i).toString());
        }

        //a[i][j] = 1 (if i == j)
        for (int i = 0; i < jsonArray.size(); i++)
        {
            for (int j = 0; j < jsonArray.size(); j++)
            {
                if (i == j)
                {
                    targetArray[i][j] = 1;
                }
            }
        }

        //a[i][j] = 1 / a[j][i] (j = 0 in this case)
        for (int i = 0; i < jsonArray.size(); i++)
        {
            targetArray[i][0] = 1 / targetArray[0][i];
            targetArray[i][0] = Math.round(targetArray[i][0] * 100) / 100.0;
        }

        //a[i][j] * a[j][k] = a[i][k] (j = 0 in this case)
        for (int i = 1; i < jsonArray.size(); i++)
        {
            for (int k = 1; k < jsonArray.size(); k++)
            {
                if (targetArray[i][k] == 0)
                {
                    targetArray[i][k] = targetArray[i][0] * targetArray[0][k];
                    targetArray[i][k] = Math.round(targetArray[i][k] * 100) / 100.0;
                }
            }
        }

        return targetArray;
    }

    public void readDataFromInputFile()
    {
        JSONParser jsonParser = new JSONParser();
        try
        {
            FileReader reader = new FileReader("src/main/java/input.json");
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            //Read project name
            this._projectName = (String) jsonObject.get("name");

            //Read alternatives
            JSONArray tempArray = (JSONArray) jsonObject.get("alternatives");
            this._alternatives = new String[tempArray.size()];
            for (int i = 0; i < tempArray.size(); i++)
            {
                this._alternatives[i] = tempArray.get(i).toString();
            }

            //Read criteria preference matrix
            tempArray = (JSONArray) jsonObject.get("preferenceMatrix");
            this._criteriaPreferenceMatrix = new Matrix(Ahp.parseMatrixFromJsonArray(tempArray));

            //Read detail of each criterion
            tempArray = (JSONArray) jsonObject.get("criteria");
            this._criteria = new Criterion[tempArray.size()];
            for (int i = 0; i < tempArray.size(); i++)
            {
                this._criteria[i] = new Criterion();
                this._criteria[i].setName(((JSONObject) tempArray.get(i)).get("name").toString());

                Matrix matrix = new Matrix(Ahp.parseMatrixFromJsonArray((JSONArray) (((JSONObject) tempArray.get(i)).get("preferenceMatrix"))));
                this._criteria[i].setPreferenceMatrix(matrix);
            }

            //Check matrix's consistency
            if (!this._criteriaPreferenceMatrix.isConsistent())
            {
                System.out.println("Please check the preference matrix of criteria (CI must less than 0.1)");
            }
            for (int i = 0; i < this._criteria.length; i++)
            {
                if (!this._criteria[i].getPreferenceMatrix().isConsistent())
                {
                    System.out.println("Please check the preference matrix of alternative with " + this._criteria[i].getName() + " (CI must less than 0.1)");
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void readDataFromInputFileAdvance()
    {
        JSONParser jsonParser = new JSONParser();
        try
        {
            FileReader reader = new FileReader("src/main/java/inputadvance.json");
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            //Read project name
            this._projectName = (String) jsonObject.get("name");

            //Read alternatives
            JSONArray tempArray = (JSONArray) jsonObject.get("alternatives");
            this._alternatives = new String[tempArray.size()];
            for (int i = 0; i < tempArray.size(); i++)
            {
                this._alternatives[i] = tempArray.get(i).toString();
            }

            //Read criteria preference matrix
            tempArray = (JSONArray) jsonObject.get("preferenceMatrix");
            this._criteriaPreferenceMatrix = new Matrix(Ahp.parseMatrixFromJsonArrayAdvance(tempArray));

            //Read detail of each criterion
            tempArray = (JSONArray) jsonObject.get("criteria");
            this._criteria = new Criterion[tempArray.size()];
            for (int i = 0; i < tempArray.size(); i++)
            {
                this._criteria[i] = new Criterion();
                this._criteria[i].setName(((JSONObject) tempArray.get(i)).get("name").toString());

                Matrix matrix = new Matrix(Ahp.parseMatrixFromJsonArrayAdvance((JSONArray) (((JSONObject) tempArray.get(i)).get("preferenceMatrix"))));
                this._criteria[i].setPreferenceMatrix(matrix);
            }

            //Check matrix's consistency
            if (!this._criteriaPreferenceMatrix.isConsistent())
            {
                System.out.println("Please check the preference matrix of criteria (CI must less than 0.1)");
            }
            for (int i = 0; i < this._criteria.length; i++)
            {
                if (!this._criteria[i].getPreferenceMatrix().isConsistent())
                {
                    System.out.println("Please check the preference matrix of alternative with " + this._criteria[i].getName() + " (CI must less than 0.1)");
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void runAhp()
    {
        System.out.println();
        System.out.println("************ Project: " + this._projectName + " ************");
        //Calculate weight vectors of all the input matrices
        double[] weightVectorOfCriteriaPreferenceMatrix = _criteriaPreferenceMatrix.getWeightVector();
        System.out.println("Weight vector of criteria preference matrix: ");
        for (Criterion criterion : _criteria)
        {
            System.out.print(criterion.getName() + " ");
        }
        System.out.println();
        _criteriaPreferenceMatrix.printWeightVector();

        double[][] weightVectorOfAlternatives = new double[_criteria.length][_alternatives.length];
        for (int i = 0; i < weightVectorOfAlternatives.length; i++)
        {
            weightVectorOfAlternatives[i] = _criteria[i].getPreferenceMatrix().getWeightVector();
        }

        System.out.println("Weight vector of alternatives (refer to each criterion): ");
        for (int i = 0; i < _criteria.length; i++)
        {
            System.out.print(_criteria[i].getName() + ": ");
            for (String alternative : _alternatives)
            {
                System.out.print(alternative + " ");
            }
            System.out.println();
            _criteria[i].getPreferenceMatrix().printWeightVector();
        }

        //Calculate score of each alternative
        double[] scores = new double[_alternatives.length];
        for (int i = 0; i < scores.length; i++)
        {
            for (int j = 0; j < weightVectorOfCriteriaPreferenceMatrix.length; j++)
            {
                scores[i] += weightVectorOfCriteriaPreferenceMatrix[j] * weightVectorOfAlternatives[j][i];
            }
        }

        //Print out the result of the analytic process
        System.out.println("Final score: ");
        for (int i = 0; i < scores.length; i++)
        {
            System.out.println(_alternatives[i] + ": " + Math.round(scores[i] * 100) / 100.0);
        }
    }
}
