import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Program
{
    public static void main(String[] args) throws FileNotFoundException
    {
        //Get input criteria and their weight matrix
        FileInputStream fisCriteria = new FileInputStream("src/criteria.txt");
        Scanner scannerCriteria = new Scanner(fisCriteria);
        String[] criteriaName = scannerCriteria.nextLine().split(" ");

        int criteriaMatrixDegree = criteriaName.length;
        double[][] criteriaMatrix = new double[criteriaMatrixDegree][criteriaMatrixDegree];
        for (int i = 0; i < criteriaMatrixDegree; i++)
        {
            for (int j = 0; j < criteriaMatrixDegree; j++)
            {
                criteriaMatrix[i][j] = Double.parseDouble(scannerCriteria.next());
            }
        }

        //Get input alternatives
        FileInputStream fisAlternatives = new FileInputStream("src/alternatives.txt");
        Scanner scannerAlternatives = new Scanner(fisAlternatives);
        String[] alternatives = scannerAlternatives.nextLine().split(" ");

        //Get input alternatives matrix
        int alternativesMatrixDegree = alternatives.length;
        double[][] alternativesMatrix = new double[alternativesMatrixDegree][alternativesMatrixDegree];

        Criterion[] criteria = new Criterion[criteriaName.length];
        String criterionName = "";

        for (int i = 0; i < criteria.length; i++)
        {
            criterionName = scannerAlternatives.nextLine();

            for (int j = 0; j < alternativesMatrixDegree; j++)
            {
                for (int k = 0; k < alternativesMatrixDegree; k++)
                {
                    alternativesMatrix[j][k] = Double.parseDouble(scannerAlternatives.next());
                }
            }
            scannerAlternatives.nextLine();
            criteria[i] = new Criterion(criterionName, alternativesMatrix);
        }

        //Apply Ahp to input data
        Matrix compareMatrix = new Matrix("Compare matrix", criteriaMatrix);
        double[] weightVectorOfCompareMatrix = compareMatrix.calculateWeightVector();
    }
}
