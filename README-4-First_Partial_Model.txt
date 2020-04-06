
*** First model - Add & Find ***
================================

The application must have the following API:

add
    Desc
        add an integer value
    Input
        Integer value
    Return
        void

Find
    Desc
        Find a value in the tree
    Input
I       nteger value
    Return
        Boolean search result
            Found → true
            Not-found → false


Create the following first model
--------------------------------
src/main/resources/com/cyberark/BstModel.json

Launch the GraphWalker studio:

java -jar ../lib/graphwalker-studio-4.2.0.jar

Open browser at: http://localhost:9090/studio.html

To add a vertex: press the 'v' key and click the left mouse

To connect 2 vertices with an edge: press the 'e' key and connect the two vertices with press the left mouse and move it from one vertex to the other while pressing the left mouse

To set a vertex as a start point, click on it with the right mouse and choose from the list.


v_Start --> e_Init --> v_VerifyInitialState --> e_Add --> v_Added --> e_Find --> v_Found --> { e_Find --> v_Found , e_Add --> vAdded } 

Save the model as: 

src/main/resources/com/cyberark/BstModel.json

exit the studio with control+c


generate the test cases
-----------------------

in the terminal run the command:

java -jar ../lib/graphwalker-cli-4.2.0.jar offline  -m src/main/resources/com/cyberark/BstModel.json "random(edge_coverage(100))"

if you installed the jq utility, you can run:

java -jar ../lib/graphwalker-cli-4.2.0.jar offline  -m src/main/resources/com/cyberark/BstModel.json "random(edge_coverage(100))"  | jq '.currentElementName'