
package com.cyberark;

import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;

import javax.script.Bindings;
import javax.script.SimpleBindings;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.graphwalker.core.condition.EdgeCoverage;
import org.graphwalker.core.condition.ReachedVertex;
import org.graphwalker.core.generator.AStarPath;
import org.graphwalker.core.generator.RandomPath;
import org.graphwalker.core.model.Edge;
import org.graphwalker.java.test.TestBuilder;
import org.graphwalker.core.condition.TimeDuration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class BstTest extends ExecutionContext implements BstModel1, BstModel2 {

  private static final Bindings bindings = new SimpleBindings();

  public BstTest() {
    super();
    getScriptEngine().put("global", bindings);
  }

  public final static Path MODEL1_PATH = Paths.get("com/cyberark/BstModel1.json");
  public final static Path MODEL2_PATH = Paths.get("com/cyberark/BstModel2.json");
  private Bst<Integer> bst;
  private ArrayList<Integer> vals;
  private ArrayList<Integer> fakeVals;
  private HashSet<Integer> inTree;
  private Random rand;
  private boolean result;

  @Override
  public void e_Add()
  {
    System.out.println( "e_Add" );
    int val = vals.get(rand.nextInt(vals.size()));
    bst.add(val);
    inTree.add(val);
  }


  @Override
  public void e_Find()
  {
    System.out.println( "e_Find" );
    //convert HashSet to an array to fetch element by random index
    Integer[] arrInTreeVals = inTree.toArray( new Integer[inTree.size()] );
    int randomIndex = rand.nextInt(inTree.size());
    result = bst.find(arrInTreeVals[randomIndex]);
  }


  @Override
  public void e_FindFakeVal()
  {
    System.out.println( "e_FindFakeVal" );
    result = bst.find(fakeVals.get(rand.nextInt(fakeVals.size())));
  }


  @Override
  public void e_Init()
  {
    System.out.println( "e_Init" );
    bst = new Bst<Integer>();
    vals = new ArrayList<Integer>(Arrays.asList(1, 3, 4, 6, 7, 8, 10, 13, 14));
    fakeVals = new ArrayList<Integer>(Arrays.asList(21, 23, 24, 26, 27, 28, 30, 33, 34));
    inTree = new HashSet<Integer>();
    rand = new Random();
    result = false;
  }

  @Override
  public void e_Delete()
  {
    System.out.println( "e_Delete" );
    // throw new RuntimeException( "e_Delete is not implemented yet!" );
  }

  @Override
  public void e_GetNodes()
  {
    System.out.println( "e_GetNodes" );
    // throw new RuntimeException( "e_GetNodes is not implemented yet!" );
  }

  @Override
  public void e_Done()
  {
    System.out.println( "e_Done" );
    // throw new RuntimeException( "e_Done is not implemented yet!" );
  }


  @Override
  public void v_Added()
  {
    System.out.println( "v_Added" );
    assertEquals(inTree.size(), bst.nodes().size());
  }


  @Override
  public void v_Found()
  {
    System.out.println( "v_Found" );
    assertTrue(result, "Find failed!");
  }


  @Override
  public void v_NotFound()
  {
    System.out.println( "v_NotFound" );
    assertFalse(result, "Found a faked value!");
  }

  @Override
  public void v_Deleted()
  {
    System.out.println( "v_Deleted" );
    // throw new RuntimeException( "v_Deleted is not implemented yet!" );
  }

  @Override
  public void v_NodesList()
  {
    System.out.println( "v_NodesList" );
    // throw new RuntimeException( "v_NodesList is not implemented yet!" );
  }

  @Override
  public void v_Model2()
  {
    System.out.println( "v_Model2" );
    // throw new RuntimeException( "v_Model2 is not implemented yet!" );
  }


  @Override
  public void v_Start()
  {
    System.out.println( "v_Start" );
  }


  @Override
  public void v_Init()
  {
    System.out.println( "v_Init" );
    assertNotNull(bst);
  }

  
/** https://github.com/GraphWalker/graphwalker-project/wiki/Test-execution */

  @Test
    public void runSmokeTest() {
        new TestBuilder()
                .addContext(new BstTest().setNextElement(new Edge().setName("e_Init").build()),
                        MODEL1_PATH,
                        new RandomPath(new EdgeCoverage(30)))
                .addContext(new BstTest().setNextElement(new Edge().setName("e_Delete").build()),
                        MODEL2_PATH,
                        new RandomPath(new EdgeCoverage(30)))
                .execute();
    }

    @Test
    public void runFunctionalTest() {
        new TestBuilder()
                .addContext(new BstTest().setNextElement(new Edge().setName("e_Init").build()),
                        MODEL1_PATH,
                        new RandomPath(new EdgeCoverage(100)))
                .addContext(new BstTest().setNextElement(new Edge().setName("e_Init").build()),
                        MODEL2_PATH,
                        new RandomPath(new EdgeCoverage(100)))
                .execute();
    }

    @Test
    public void runStabilityTest() {
        new TestBuilder()
                .addContext(new BstTest().setNextElement(new Edge().setName("e_Init").build()),
                        MODEL1_PATH,
                        new RandomPath(new TimeDuration(1, TimeUnit.SECONDS)))
                .addContext(new BstTest().setNextElement(new Edge().setName("e_Init").build()),
                        MODEL2_PATH,
                        new RandomPath(new TimeDuration(1, TimeUnit.SECONDS)))
                .execute();
    }
}

