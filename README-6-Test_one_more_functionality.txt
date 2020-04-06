*** test one more functionality ***
===================================

In this page we add one more functionality to the model and test files that should be tested. 
(a functionality that is already implemented in the Bst.java):
- The Bst 'find' API should return 'false' when not finding the requested value

Find
    Desc
        Find a value in the tree
    Input
        Integer value
    Return
        Boolean search result
            Found → true
            Not-found → false

1. Add the new functionality to the model
    1.1. Launch the GraphWalker studio:
            $ java -jar ../lib/graphwalker-studio-4.2.0.jar
    1.2. Open browser at:
        http://localhost:9090/studio.html
    1.3. Open the BstModel at:
            src/main/resources/com/cyberark/BstModel.json
    1.4. Add the required vertex and edges.
        1.4.1. vertex name: 'v_NotFound'
        1.4.2. edge name: 'e_FindFakeVal'
    1.5. make sure to connect the new functionality with the previous functions in the model (add & find)

2. generate the test-interface
    $ mvn clean graphwalker:generate-sources

3. Generate the updated test file with its default name: MyTest.java
    $ java -jar ../lib/graphwalker-cli-4.2.0.jar source -i src/main/resources/com/cyberark/BstModel.json src/main/templates/java.template > src/test/java/com/cyberark/MyTest.java

4. From MyTest.java copy the new test methods and paste them in your BstTest.java file. Delete the MyTest.java file

5. Implement the new test methods in BstTest.java

6. Execute the updated tests
    $ mvn clean graphwalker:generate-sources test site jacoco:prepare-agent jacoco:report