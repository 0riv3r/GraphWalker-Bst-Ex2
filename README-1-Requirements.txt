In this part we continue with the Bst project from the point we stoped, and will add a second model to cover more functionality.

Below is the list of the application requirements.
The second model will cover the 'Delete'and 'Nodes' APIs.

The application API:
====================

add
---
Desc
    add an integer value
Constraints
    A value is inserted only once
Input
    Integer value
Return
    void

Find
----
Desc
    Find a value in the tree
Input
    Integer value
Return
    Boolean search result
        Found → true
        Not-found → false

Delete
------
Desc
    Delete a leaf
Constraints
    Delete only if this is a leaf
Input
    Integer value
Return
    Boolean result:
        Deleted (found a leaf with that value) → true
        Not deleted (not found or not a leaf) → false

Nodes
-----
Desc
    Return the list of nodes
Return
    Array of all the nodes

IsEmpty
-------
Desc
    Return a boolean value if the tree is empty or not
Return
    Empty → true
    Not Empty → false

largest
-------
Desc
    Return the largest value that is in the tree
Return
    Integer - the largest value that is in the tree

smallest
--------
Desc
    Return the smallest value that is in the tree
Return
    Integer - the smallest value that is in the tree


---------------------------------------------------------------------------

Change the model's structure
============================

Launch the GraphWalker studio:

$ export JAVA_HOME=`/usr/libexec/java_home -v 1.8`

$ java -jar ../lib/graphwalker-studio-4.2.0.jar

Open browser at: http://localhost:9090/studio.html

Open the models json file: 

src/main/resources/com/cyberark/BstModel.json

change the current model to be better suit to have extension models

1. rename v_VerifyInitialState to v_Init (remember to also update the BstTest.java file)
2. redo the model to have a star shape with v_Init as the dispatcher state and e_Done from each functional vertex back to v_Init.
3. save as src/main/resources/com/cyberark/BstModel.json

4. run the following:

$ mvn clean graphwalker:generate-sources
$ java -jar ../lib/graphwalker-cli-4.2.0.jar source -i src/main/resources/com/cyberark/BstModel.json src/main/templates/java.template > src/test/java/com/cyberark/MyTest.java

5. copy the 'e_Done' method to BstTest.java , and rename 'v_VerifyInitialState' to 'v_Init'
6. delete the MyTest.java file

Run the tests again to see that all works as before:
mvn clean graphwalker:generate-sources test site jacoco:prepare-agent jacoco:report

--------------------------------------------------------------------------------

Create a second model
=====================


Launch the GraphWalker studio:

$ java -jar ../lib/graphwalker-studio-4.2.0.jar

Open browser at: http://localhost:9090/studio.html

Open the first model json file: 

src/main/resources/com/cyberark/BstModel.json

1. set v_Init as a shared vertex 
2. add the action 'added=false;' under the model 'Actions'
3. add the guard 'added==true;' on edge 'e_Find'
4. add the action 'added=true;' on edge 'e_Add'

save the BstModel1.json model file. ** added '1' at the end of the name **
src/main/resources/com/cyberark/BstModel1.json

we will add the second model json file.

1. create the second model, named BstModel2, starting from 'v_Model2' as the shared vertex with v_Init of the first Model

2. add 'v_Model2', v_Deleted' + 'e_Delete', 'v_NodesList' + 'e_GetNodes'

3. add the guard 'added==true;' on edge 'e_Delete'

3. save this second model as src/main/resources/com/cyberark/BstModel2.json file

5. run the following:

$ mvn clean graphwalker:generate-sources

check the generated offline test cases:
https://groups.google.com/forum/#!searchin/graphwalker/shared|sort:date/graphwalker/ahBJuXJusXM/H5-dIoXVAgAJ
java -jar ../lib/graphwalker-cli-4.2.0.jar offline  -m src/main/resources/com/cyberark/BstModel1.json "random(edge_coverage(100))" -m src/main/resources/com/cyberark/BstModel2.json "random(edge_coverage(100))" | jq '.currentElementName'

--------------------------------------------------------------------------------

1. generate the MyTest1.java test skeleton for the BstModel1.json model:
$ java -jar ../lib/graphwalker-cli-4.2.0.jar source -i src/main/resources/com/cyberark/BstModel1.json src/main/templates/java.template > src/test/java/com/cyberark/MyTest1.java

2. generate the MyTest2.java test skeleton for the BstModel2.json model:
$ java -jar ../lib/graphwalker-cli-4.2.0.jar source -i src/main/resources/com/cyberark/BstModel2.json src/main/templates/java.template > src/test/java/com/cyberark/MyTest2.java


3. copy the 'v_Model2', 'v_Deleted', 'e_Delete', 'v_NodesList' and 'e_GetNodes' methods from MyTest2.java to BstTest.java
4. delete the MyTest1.java and MyTest2.java files

5. Implement the new added methods

6. change the BstTest class implemented interfeces list to:
    public class BstTest extends ExecutionContext implements BstModel1, BstModel2 {

7. update the modls paths - the 2 first lines of the BstTest class:
public final static Path MODEL1_PATH = Paths.get("com/cyberark/BstModel1.json");
public final static Path MODEL2_PATH = Paths.get("com/cyberark/BstModel2.json");

8. copy the updated code of the @Test methods

9. to enable use of global variables in the models:
    https://groups.google.com/forum/#!topic/graphwalker/lZi0_MWKPNM

    9.1. add the following imports in BstTest.java:
        import javax.script.Bindings;
        import javax.script.SimpleBindings;

    9.2. inside the BstTest class add the following as first line:
        private static final Bindings bindings = new SimpleBindings();

    9.3. inside the BstTest class add the following constructor:
        public BstTest() {
            super();
            getScriptEngine().put("global", bindings);
        } 

10. Run the tests:
mvn clean graphwalker:generate-sources test site jacoco:prepare-agent jacoco:report
