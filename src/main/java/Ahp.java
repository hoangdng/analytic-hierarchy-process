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
        readDataFromInputFile();
    }

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

    public void readDataFromInputFile()
    {
        JSONParser jsonParser = new JSONParser();
        try
        {
            FileReader reader = new FileReader("src/main/java/input.json");
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            this._projectName = (String) jsonObject.get("name");

            JSONArray tempArray = (JSONArray) jsonObject.get("alternatives");
            this._alternatives = new String[tempArray.size()];
            for (int i = 0; i < tempArray.size(); i++)
            {
                this._alternatives[i] = tempArray.get(i).toString();
            }

            tempArray = (JSONArray) jsonObject.get("preferenceMatrix");
            this._criteriaPreferenceMatrix = new Matrix(Ahp.parseMatrixFromJsonArray(tempArray));

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


    public void runAhp()
    {
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
