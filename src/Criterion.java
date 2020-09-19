public class Criterion
{
    private Matrix _weightMatrix;
    private String _name;
    private Criterion[] _criteria;

    public Criterion(String name, Matrix weightMatrix)
    {
        _name = name;
        _weightMatrix = weightMatrix;
    }

    public void printCriterionMatrix()
    {
        System.out.println(_name);
        _weightMatrix.printMatrix();
    }

    public Matrix getWeightMatrix()
    {
        return _weightMatrix;
    }

    public String getName()
    {
        return _name;
    }
}
