import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Ahp
{
    private String[] _criteriaName;
    private Matrix _citeriaCompareMatrix;

    private String[] _alternatives;
    private Criterion[] _criteria;

    public Ahp(String filePathCriteria, String filePathAlternatives)
    {
        try
        {
            this.getCriteriaFromFileInput(filePathCriteria);
            this.getAlternativesFromFileInput(filePathAlternatives);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    public void getCriteriaFromFileInput(String filePath) throws FileNotFoundException
    {
        FileInputStream fis = new FileInputStream(filePath);
        Scanner scanner = new Scanner(fis);
        _criteriaName = scanner.nextLine().split(" ");

        int matrixDegree = _criteriaName.length;
        double[][] compareMatrix = new double[matrixDegree][matrixDegree];
        for (int i = 0; i < matrixDegree; i++)
        {
            for (int j = 0; j < matrixDegree; j++)
            {
                compareMatrix[i][j] = Double.parseDouble(scanner.next());
            }
        }

        _citeriaCompareMatrix = new Matrix(compareMatrix);
        if (!_citeriaCompareMatrix.isConsistent())
        {
            System.out.println("Compare matrix is not acceptable!");
        }
    }

    public void getAlternativesFromFileInput(String filePath) throws FileNotFoundException
    {
        FileInputStream fis = new FileInputStream(filePath);
        Scanner scanner = new Scanner(fis);
        _alternatives = scanner.nextLine().split(" ");

        int matrixDegree = _alternatives.length;
        double[][] alternativesMatrix;

        _criteria = new Criterion[_criteriaName.length];
        String criterionName = "";

        for (int i = 0; i < _criteria.length; i++)
        {
            alternativesMatrix = new double[matrixDegree][matrixDegree];
            criterionName = scanner.nextLine();

            for (int j = 0; j < matrixDegree; j++)
            {
                for (int k = 0; k < matrixDegree; k++)
                {
                    double num = Double.parseDouble(scanner.next());
                    alternativesMatrix[j][k] = num;
                }
            }

            scanner.nextLine();
            _criteria[i] = new Criterion(criterionName, new Matrix(alternativesMatrix));
        }
        for (int i = 0; i < _criteria.length; i++)
        {
            if (!_criteria[i].getWeightMatrix().isConsistent())
            {
                System.out.println("Matrix " + _criteria[i].getName() + " is not acceptable!");
            }
        }
    }

    public void runAhp()
    {
        //Calculate weight vectors
        double[] weightVectorOfCompareMatrix = _citeriaCompareMatrix.getWeightVector();
        System.out.println("Weight vector of compare matrix: ");
        _citeriaCompareMatrix.printWeightVector();

        double[][] weightVectorOfCriteria = new double[_criteria.length][_alternatives.length];

        for (int i = 0; i < weightVectorOfCriteria.length; i++)
        {
            weightVectorOfCriteria[i] = _criteria[i].getWeightMatrix().getWeightVector();
        }
        System.out.println("Weight vector of criteria: ");
        for (int i = 0; i < _criteria.length; i++)
        {
            System.out.println(_criteriaName[i] + ":");
            _criteria[i].getWeightMatrix().printWeightVector();
        }

        //Calculate score of each alternative
        double[] scores = new double[_alternatives.length];
        for (int i = 0; i < scores.length; i++)
        {
            for (int j = 0; j < weightVectorOfCompareMatrix.length; j++)
            {
                scores[i] += weightVectorOfCompareMatrix[j] * weightVectorOfCriteria[j][i];
            }
        }

        System.out.println("Final score: ");
        for (int i = 0; i < scores.length; i++)
        {
            System.out.println(_alternatives[i] + ": " + Math.round(scores[i] * 100) / 100.0);
        }
    }

}
