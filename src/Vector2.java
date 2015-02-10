package src;
import java.io.*;

/**
 * Vector2 objects represent a 2 dimensional, mathematical, integer vector or - 
 * more abstractly - 2 integer numbers. This class is serializable.
 * 
 * This class is based off of the XNA game framework Vector2 implementation
 * @author Alex Stewart
 */
public class Vector2 implements Serializable {
    // Serialization version ID
    private static final long serialVersionUID = Long.parseLong("COPVECTOR2", 35);
    // Class priate values
    private Integer x_, y_;
    
    /* CONSTRUCTORS */
    public Vector2 () {
        x_ = 0;
        y_ = 0;
    }
    
    public Vector2 (int x, int y) {
        x_ = x;
        y_ = y;
    }
    
    public Vector2 (Integer x, Integer y) {
        x_ = x;
        y_ = y;
    }
    
    /* FIELD ACCESSORS */
    public Vector2 Value() {
        return new Vector2(X(), Y());
    }
    
    public void SetValue(int x , int y) {
        SetValue(new Integer(x), new Integer(y));
    }
    
    public void SetValue(Integer X, Integer Y) {
        x_ = X;
        y_ = Y;
    }
    
    public Integer X() {
        return x_;
    }
    
    public void SetX(int x) {
        SetX(new Integer(x));
    }
    
    public void SetX(Integer x) {
        x_ = x;
    }
    
    public Integer Y() {
        return y_;
    }
    
    public void SetY(int y) {
        SetY(new Integer(y));
    }
    
    public void SetY(Integer y) {
        y_ = y;
    }
    
    /* PUBLIC METHODS */
    public static Vector2 Add(Vector2 a, Vector2 b) {
        Vector2 ret = a;
        ret.SetX(a.X() + b.X());
        ret.SetY(a.Y() + b.Y());
        return ret;
    }
    
    public Integer Area () {
        return (x_ * y_);
    }
    
    public static Vector2 Dot(Vector2 a, Vector2 b) {
        Vector2 ret = a;
        ret.SetX(a.X() * a.X());
        ret.SetY(a.Y() * b.Y());
        return ret;
    }
    
    public static Vector2 One() {
        return new Vector2(1,1);
    }
    
    public static Vector2 Subtract(Vector2 a, Vector2 b) {
        Vector2 ret = a;
        ret.SetX(a.X() - b.X());
        ret.SetY(a.Y() - b.Y());
        return ret;
    }
    
    @Override
    public String toString() {
        return ("<" + x_.toString() + ", " + y_.toString() + ">");
    }
    
    public static Vector2 Zero() {
        return new Vector2(0,0);
    }
    
    /* SERIALIZATION */
    private void readObject (ObjectInputStream is) throws ClassNotFoundException, IOException {
        is.defaultReadObject();
        if (x_ == null || y_ == null)
            throw new IOException();
    }
    
    private void writeObject (ObjectOutputStream os) throws IOException {
        os.defaultWriteObject();
    }
}