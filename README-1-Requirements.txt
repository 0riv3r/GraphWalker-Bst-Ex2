In this part we continue with the Bst project from the point we stoped, and will add to the model and test to cover more functionality.



Below is the list of the application requirements.

The second model will cover the 'Delete' and 'Nodes' APIs.

The application API
add
Desc
add an integer value
Constraints
A value is inserted only once
Input
Integer value
Return
void

Find
Desc
Find a value in the tree
Input
Integer value
Return
Boolean search result
Found → true
Not-found → false

Delete
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
Desc
Return the list of nodes
Return
Array of all the nodes

IsEmpty
Desc
Return a boolean value if the tree is empty or not
Return
Empty → true
Not Empty → false

largest
Desc
Return the largest value that is in the tree
Return
Integer - the largest value that is in the tree

smallest
Desc
Return the smallest value that is in the tree
Return
Integer - the smallest value that is in the tree




Change the model's structure to a STAR shape
Launch the GraphWalker studio:


Launch GraphWalker Studio
$ export JAVA_HOME=`/usr/libexec/java_home -v 1.8`
$ java -jar ../lib/graphwalker-studio-4.2.0.jar
Open browser at: http://localhost:9090/studio.html

Open the models json file:

src/main/resources/com/cyberark/BstModel.json

change the model to be as in this diagram:

...

Add State variables & Guards
A model can have state variables as 'Actions'. These variables are changed in transactions and are part of the model's state.

We use those variables in 'Guards' on states and also when comparing the model's state with the SUT (System Under Test) state during test executions.

We will do such state data variables comparisons in a later stage of this workshop.

When we want to share state data variables between models we add them to the 'global.' object, i.e. 'global.counter'.

Data variables that are local in one model (not shared between models) don't need to be added to the 'global.' object, i.e. 'counter'.

In this project we use one model so we don't use the 'global.' object.

Make the following additions to the model:

add the action 'added=false;' under the model 'Actions'
add the guard 'added==true;' on edge 'e_Find'
add the action 'added=true;' on edge 'e_Add'
add the guard 'added==true;' on edge 'e_Delete'
save the model as src/main/resources/com/cyberark/BstModel.json file

Check the generated offline test cases:

Generate offline test cases
java -jar ../lib/graphwalker-cli-4.2.0.jar offline -m src/main/resources/com/cyberark/BstModel.json "random(edge_coverage(100))" | jq '.currentElementName'
Generate the MyTest.java test skeleton:

Generate MyTest.java
$ mvn clean graphwalker:generate-sources
$ java -jar ../lib/graphwalker-cli-4.2.0.jar source -i src/main/resources/com/cyberark/BstModel.json src/main/templates/java.template > src/test/java/com/cyberark/MyTest.java
Copy the new methods (e_Delete, e_GetNodes, e_ToMenu, v_Deleted, v_MenuDispatcher, v_NodesList) to BstTest.java , and rename 'v_VerifyInitialState' to 'v_Init'
Delete the MyTest.java file

Run the tests again to see that all works as before (we still did not implement the new test methods):

Execute
mvn clean graphwalker:generate-sources test site jacoco:prepare-agent jacoco:report


Implement the new added methods.

You can copy the code from here

...

9. Run the tests:

Execute
# If required, make sure to use the correct java:
$ export JAVA_HOME=`/usr/libexec/java_home -v 1.8`
 
 
$ mvn clean graphwalker:generate-sources test site jacoco:prepare-agent jacoco:report
