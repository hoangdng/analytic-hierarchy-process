import java.util.Arrays;

public class Matrix
{
    private double[][] _matrix;
    private double[] _weightVector;

    public Matrix(double[][] matrix)
    {
        _matrix = matrix;
        _weightVector = this.calculateWeightVector();
    }

    public double[] getWeightVector()
    {
        return _weightVector;
    }

    public void printMatrix()
    {

        for (int i = 0; i < _matrix.length; i++)
        {
            for (int j = 0; j < _matrix[i].length; j++)
            {
                System.out.print(_matrix[i][j] + "  ");
            }
            System.out.println();
        }
    }

    public double[] calculateWeightVector()
    {
        //Calculate the sum of each column in weight matrix
        int matrixDegree = _matrix.length;
        double[] sumCols = new double[matrixDegree];
        for (int i = 0; i < matrixDegree; i++)
        {
            for (int j = 0; j < matrixDegree; j++)
            {
                sumCols[i] += _matrix[j][i];
            }
        }
        //Divide each elements in matrix to the corresponding sum of column (normalize this matrix)
        double[][] cloneMatrix = Arrays.stream(_matrix).map(double[]::clone).toArray(double[][]::new);
        for (int i = 0; i < matrixDegree; i++)
        {
            for (int j = 0; j < matrixDegree; j++)
            {
                cloneMatrix[i][j] = cloneMatrix[i][j] / sumCols[j];
            }
        }
        //Calculate the sum of each row in the new matrix
        double[] sumRows = new double[matrixDegree];
        for (int i = 0; i < matrixDegree; i++)
        {
            for (int j = 0; j < matrixDegree; j++)
            {
                sumRows[i] += cloneMatrix[i][j];
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

    public void printWeightVector()
    {
        double[] weightVector = calculateWeightVector();

        for (int i = 0; i < weightVector.length; i++)
        {
            System.out.println(weightVector[i]);
        }
        System.out.println("-------------");
    }

    public boolean isConsistent()
    {
        //B1: Multiply matrix to its weight vector
        double[] sumWeightVector = new double[_weightVector.length];
        for (int i = 0; i < _matrix.length; i++)
        {
            for (int j = 0; j < _matrix[i].length; j++)
            {
                sumWeightVector[i] += _matrix[i][j] * _weightVector[j];
            }
        }
        //B2: Divive the sum weight vector by the weight vector
        double[] consistencyVector = new double[sumWeightVector.length];
        for (int i = 0; i < consistencyVector.length; i++)
        {
            consistencyVector[i] = sumWeightVector[i] / _weightVector[i];
        }
        //B3: Calculate the maximum eigenvalue (lambda) by taking the average of consistency vector
        double lambda = Arrays.stream(consistencyVector).sum() / consistencyVector.length;
        //B4: Calculate the consistency index (CI)
        double ci = (lambda - _matrix.length) / (_matrix.length - 1);
        //B5: Calculate the consistency ratio (CR)
        double ri = 0;
        switch (_matrix.length)
        {
            case 3:
                ri = 0.52;
                break;
            case 4:
                ri = 0.89;
                break;
            case 5:
                ri = 1.11;
                break;
            case 6:
                ri = 1.25;
                break;
            case 7:
                ri = 1.35;
                break;
            case 8:
                ri = 1.4;
                break;
            case 9:
                ri = 1.45;
                break;
            case 10:
                ri = 1.49;
                break;
            case 11:
                ri = 1.52;
                break;
            case 12:
                ri = 1.54;
                break;
            case 13:
                ri = 1.56;
                break;
            case 14:
                ri = 1.58;
                break;
            case 15:
                ri = 1.59;
                break;
        }
        double cr = 0;
        try
        {
            cr = ci / ri;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        if (cr < 0.1)
            return true;
        return false;
    }
}
