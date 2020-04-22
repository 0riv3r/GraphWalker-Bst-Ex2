
package com.cyberark;

import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import org.graphwalker.core.condition.EdgeCoverage;
import org.graphwalker.core.condition.ReachedVertex;
import org.graphwalker.core.generator.AStarPath;
import org.graphwalker.core.generator.RandomPath;
import org.graphwalker.core.model.Edge;
import org.graphwalker.java.test.TestBuilder;
import org.graphwalker.core.condition.TimeDuration;
import org.graphwalker.core.condition.VertexCoverage;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class BstTest extends ExecutionContext implements BstModel {

  public final static Path MODEL_PATH = Paths.get("com/cyberark/BstModel.json");
  private Bst<Integer> bst;
  private ArrayList<Integer> vals;      // values to be inserted in the tree
  private ArrayList<Integer> fakeVals;  // values that are not inserted in the tree
  private HashSet<Integer> inTree;      // the current values in the tree, use set to avoid duplicates (like the tree does)
  private Stack<Integer> nodesStack;    // used to pop leaves in deletion test, in order to delete leaves
  private Random rand;
  private boolean boolResult;

  @Override
  public void e_Add()
  {
    System.out.println( "e_Add" );
    int val = vals.get(rand.nextInt(vals.size()));
    bst.add(val);
    if(inTree.add(val)) // Add to the stack only if succeeded to add to the set
      nodesStack.push(val);
  }


  @Override
  public void e_Find()
  {
    System.out.println( "e_Find" );

    System.out.println( "bst.nodes: " + Arrays.toString(bst.nodes().toArray()));
    System.out.println( "inTree: " + Arrays.toString(inTree.toArray()));
    System.out.println( "nodesStack: " + Arrays.toString(nodesStack.toArray()));

    //convert HashSet to an array to fetch element by random index
    Integer[] arrInTreeVals = inTree.toArray( new Integer[inTree.size()] );
    int randomIndex = rand.nextInt(inTree.size());
    boolResult = bst.find(arrInTreeVals[randomIndex]);
  }


  @Override
  public void e_FindFakeVal()
  {
    System.out.println( "e_FindFakeVal" );
    boolResult = bst.find(fakeVals.get(rand.nextInt(fakeVals.size())));
  }


  @Override
  public void e_Init()
  {
    System.out.println( "e_Init" );
    bst = new Bst<Integer>();
    vals = new ArrayList<Integer>(Arrays.asList(1, 3, 4, 6, 7, 8, 10, 13, 14));
    fakeVals = new ArrayList<Integer>(Arrays.asList(21, 23, 24, 26, 27, 28, 30, 33, 34));
    inTree = new HashSet<Integer>();
    nodesStack = new Stack<Integer>();
    rand = new Random();
    boolResult = false;
  }

  @Override
  public void v_Init()
  {
    System.out.println( "v_Init" );
    assertNotNull(bst);
    assertNotNull(vals);
    assertNotNull(fakeVals);
    assertNotNull(inTree);
    assertNotNull(rand);
    assertEquals(false, boolResult);

    // System.out.println( "bst.nodes: " + Arrays.toString(bst.nodes().toArray()));
    // System.out.println( "inTree: " + Arrays.toString(inTree.toArray()));
    // System.out.println( "nodesStack: " + Arrays.toString(nodesStack.toArray()));
  }


  @Override
  public void e_Delete()
  {
    System.out.println( "e_Delete" );

    System.out.println( "bst.nodes: " + Arrays.toString(bst.nodes().toArray()));
    System.out.println( "inTree: " + Arrays.toString(inTree.toArray()));
    System.out.println( "nodesStack: " + Arrays.toString(nodesStack.toArray()));

    // The last inserted value is a leaf and should be deleted
    int valToDelete = nodesStack.pop();
    inTree.remove(valToDelete);
    bst.delete(valToDelete);
  }

  @Override
  public void e_GetNodes()
  {
    System.out.println( "e_GetNodes" );
    Set<Integer> expectedNodes = new HashSet<Integer>();
    expectedNodes.addAll(inTree);
    if(expectedNodes.size() == bst.nodes().size()){
      expectedNodes.removeAll(bst.nodes());
      boolResult = (expectedNodes.size() == 0);
    }
    else{
      boolResult = false;
    }
  }

  @Override
  public void e_ToMenu()
  {
    System.out.println( "e_ToMenu" );
    // throw new RuntimeException( "e_ToMenu is not implemented yet!" );
  }


  @Override
  public void v_Added()
  {
    System.out.println( "v_Added" );

    System.out.println( "bst.nodes: " + Arrays.toString(bst.nodes().toArray()));
    System.out.println( "inTree: " + Arrays.toString(inTree.toArray()));
    System.out.println( "nodesStack: " + Arrays.toString(nodesStack.toArray()));
    
    assertEquals(inTree.size(), bst.nodes().size());
  }


  @Override
  public void v_Found()
  {
    System.out.println( "v_Found" );
    assertTrue(boolResult, "Find failed!");
  }


  @Override
  public void v_NotFound()
  {
    System.out.println( "v_NotFound" );
    assertFalse(boolResult, "Found a faked value!");
  }

  @Override
  public void v_Deleted()
  {
    System.out.println( "v_Deleted" );

    System.out.println( "bst.nodes: " + Arrays.toString(bst.nodes().toArray()));
    System.out.println( "inTree: " + Arrays.toString(inTree.toArray()));
    System.out.println( "nodesStack: " + Arrays.toString(nodesStack.toArray()));

    assertEquals(inTree.size(), bst.nodes().size());
  }

  @Override
  public void v_NodesList()
  {
    System.out.println( "v_NodesList" );
    assert(boolResult == true);
  }

  @Override
  public void v_Start()
  {
    System.out.println( "v_Start" );
  }

  @Override
  public void v_MenuDispatcher()
  {
    System.out.println( "v_MenuDispatcher" );
    // throw new RuntimeException( "v_MenuDispatcher is not implemented yet!" );
  }

  @Test
    public void runSmokeTest() {
        new TestBuilder()
                .addContext(new BstTest().setNextElement(new Edge().setName("e_Init").build()),
                        MODEL_PATH,
                        new RandomPath(new EdgeCoverage(30)))
                .execute();
    }

    @Test
    public void runFunctionalTest() {
        new TestBuilder()
                .addContext(new BstTest().setNextElement(new Edge().setName("e_Init").build()),
                        MODEL_PATH,
                        new RandomPath(new EdgeCoverage(100)))
                .addContext(new BstTest().setNextElement(new Edge().setName("e_Init").build()),
                        MODEL_PATH,
                        new RandomPath(new VertexCoverage(100)))
                .execute();
    }

    // @Test
    // public void runStabilityTest() {
    //     new TestBuilder()
    //             .addContext(new BstTest().setNextElement(new Edge().setName("e_Init").build()),
    //                     MODEL_PATH,
    //                     new RandomPath(new TimeDuration(3, TimeUnit.SECONDS)))
    //             .execute();
    // }
}

