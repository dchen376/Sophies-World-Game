package test.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import model.mansion.Mansion;
import model.target.Target;

/**
 * test class for Target class.
 */
public class TargetTest {
  private Target target;
  private Mansion m;

  /**
   * Constructor.
   */
  public TargetTest() {
  }

  @Before
  public void setup() {
    this.m = new Mansion();
    File file = new File("C:\\Users\\dongpingchen\\Documents\\GitHub\\"
        + "PDP---Milestone-Mansion-Game-\\world.txt");

    try {
      FileReader input = new FileReader(file);
      this.m.readFile(input);
      input.close();
    } catch (FileNotFoundException var3) {
      var3.getStackTrace();
      Assert.fail("Failed! Exception was thrown. File not found");
    } catch (IOException e) {
      e.printStackTrace();
      Assert.fail("Failed! IOException!");
    }

    this.target = new Target(m, m.getTargetName(), m.getTargetHealth(), m.getTargetLocation());
  }

  /**
   * Method: testGetTargetHealth().
   */
  @Test
  public void testGetTargetHealth() {
    Assert.assertEquals(100, target.getTargetHealth());
  }

  /**
   * Method: testGetTargetLocation().
   */
  @Test
  public void testGetTargetLocation() {
    Assert.assertEquals(0, target.getTargetLocation());
  }

  

}
