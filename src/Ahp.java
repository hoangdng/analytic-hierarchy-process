import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class Ahp
{
    private double[][] _criteriaMatrix;
    private String[] _alternatives;

    public Ahp(double[][] criteriaMatrix, String[] alternatives)
    {
        this._criteriaMatrix = criteriaMatrix;
        this._alternatives = alternatives;
    }

    public double[] calculateWeightVector(double[][] matrix)
    {
        //Calculate the sum of each column in weight matrix
        int matrixDegree = matrix.length;
        double[] sumCols = new double[matrixDegree];
        for (int i = 0; i < matrixDegree; i++)
        {
            for (int j = 0; j < matrixDegree; j++)
            {
                sumCols[i] += matrix[j][i];
            }
        }
        //Divide each elements in matrix to the corresponding sum of column (normalize this matrix)
        for (int i = 0; i < matrixDegree; i++)
        {
            for (int j = 0; j < matrixDegree; j++)
            {
                matrix[i][j] = matrix[i][j] / sumCols[j];
            }
        }
        //Calculate the sum of each row in the new matrix
        double[] sumRows = new double[matrixDegree];
        for (int i = 0; i < matrixDegree; i++)
        {
            for (int j = 0; j < matrixDegree; j++)
            {
                sumRows[i] += matrix[i][j];
            }
        }
        //Divide sum of each row to sum of all rows
        double sumOfAllRows = Arrays.stream(sumRows).reduce(0, (element1, element2) -> element1 + element2);
        double[] weightVector = new double[matrixDegree];
        for (int i = 0; i < matrixDegree; i++)
        {
            weightVector[i] = sumRows[i] / sumOfAllRows;
        }
        return weightVector;
    }
}
