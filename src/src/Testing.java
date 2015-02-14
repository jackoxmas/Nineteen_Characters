/**
 * Implementor: Alex Stewart
 * Last Update: 15-02-11
 */
package src;

/**
 * This class holds static methods for testing individual elements of the 
 * project.
 * 
 * @author Alex Stewart
 */
public class Testing {
    static private enum TT {
        V2
    }
    
    static public int class_Vector2(int passes, int fails, Exception exept)
    {
        TT t = TT.V2;
        exept = null;
        passes = 0;
        fails = 0;
        int p_ = 0, f_ = 0, s = 0;
        
        try {
            Vector2 vMax_ = new Vector2();
            Vector2 vMin_ = new Vector2();
            vMax_.setValue(Integer.MAX_VALUE, Integer.MAX_VALUE);
            
            
            
        } catch (Exception ex_) {
            exept = ex_;
            return 0;
        }
            
        return 1; // test was able to run all elements
    }
    
    static public void pr(TT t, String subtest, int success)
    {
        if (subtest == null)
            subtest = "NULL";
        System.out.print("[TESTING](" + t.toString() + " | " + subtest + ") = ");
        if (success == 1)
            System.out.print("PASS");
        else if (success == 0)
            System.out.print("FAIL");
        else
            System.out.print("???");
        System.out.println();
        success = 0;
    }
}
