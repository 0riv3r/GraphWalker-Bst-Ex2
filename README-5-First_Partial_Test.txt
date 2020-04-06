*** Create the Bst first partial GraphWalker Test ***
=====================================================

*** VS-Code extensions ***
==========================

if you are using VS-Code as your IDE, install the following extensions:

- Java extension pack
- Coverage Gutters
- Preview on Web Server

-----------------------------------------------------------------------------------------------

*** Junit ***
=============

We will be using junit as our test runner (executor)

I am using maven, and so I add the dependencies in the pom.xml as I show below

-----------------------------------------------------------------------------------------------

*** Jacoco ***
=============

We will be using jacoco as our test coverage tool.

I am using maven, and so I add the dependencies in the pom.xml as I show below

-----------------------------------------------------------------------------------------------

*** pom.xml ***
===============

Add the following to the pom.xml file to add the graphwalker maven dependencies

Under properties:
-----------------

<graphwalker.version>4.2.0</graphwalker.version>


add dependencies:
-----------------

<dependencies>
    <!--  Testing Dependencies-->
    <dependency>
        <groupId>org.graphwalker</groupId>
        <artifactId>graphwalker-core</artifactId>
        <version>${graphwalker.version}</version>
    </dependency>
    <dependency>
        <groupId>org.graphwalker</groupId>
        <artifactId>graphwalker-io</artifactId>
        <version>${graphwalker.version}</version>
    </dependency>
    <dependency>
        <groupId>org.graphwalker</groupId>
        <artifactId>graphwalker-maven-plugin</artifactId>
        <version>${graphwalker.version}</version>
    </dependency>
    <dependency>
        <groupId>org.graphwalker</groupId>
        <artifactId>graphwalker-java</artifactId>
        <version>${graphwalker.version}</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-simple -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-simple</artifactId>
        <version>1.6.2</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-api</artifactId>
        <version>5.4.2</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-engine</artifactId>
        <version>5.4.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>


Plugins:
--------

<build>
    <plugins>
        <plugin>
            <groupId>org.jacoco</groupId>
            <artifactId>jacoco-maven-plugin</artifactId>
            <version>0.8.2</version>
            <executions>
                <execution>
                    <goals>
                        <goal>prepare-agent</goal>
                    </goals>
                </execution>
                <execution>
                    <id>report</id>
                    <phase>prepare-package</phase>
                    <goals>
                        <goal>report</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
        <!-- clean lifecycle, see https://maven.apache.org/ref/current/maven-core/lifecycles.html#clean_Lifecycle -->
        <plugin>
            <artifactId>maven-clean-plugin</artifactId>
            <version>3.1.0</version>
        </plugin>
        <!-- default lifecycle, jar packaging: see https://maven.apache.org/ref/current/maven-core/default-bindings.html#Plugin_bindings_for_jar_packaging -->
        <plugin>
            <artifactId>maven-resources-plugin</artifactId>
            <version>3.0.2</version>
        </plugin>
        <plugin>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.8.0</version>
        </plugin>
        <plugin>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>2.22.1</version>
        </plugin>
        <plugin>
            <artifactId>maven-jar-plugin</artifactId>
            <version>3.0.2</version>
        </plugin>
        <plugin>
            <artifactId>maven-install-plugin</artifactId>
            <version>2.5.2</version>
        </plugin>
        <plugin>
            <artifactId>maven-deploy-plugin</artifactId>
            <version>2.8.2</version>
        </plugin>
        <!-- site lifecycle, see https://maven.apache.org/ref/current/maven-core/lifecycles.html#site_Lifecycle -->
        <plugin>
            <artifactId>maven-site-plugin</artifactId>
            <version>3.7.1</version>
        </plugin>
        <plugin>
            <artifactId>maven-project-info-reports-plugin</artifactId>
            <version>3.0.0</version>
        </plugin>
        <plugin>
            <groupId>org.graphwalker</groupId>
            <artifactId>graphwalker-maven-plugin</artifactId>
            <version>${graphwalker.version}</version>
            <executions>
                <execution>
                    <id>generate-sources</id>
                    <phase>generate-sources</phase>
                    <goals>
                        <goal>generate-sources</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>

You can replace your project's pom.xml file with the pom.xml that is attached with this page.


-----------------------------------------------------------------------------------------------


*** Generate the Test Interface ***
===================================

run the following command to generate the test-interface:

$ export JAVA_HOME=`/usr/libexec/java_home -v 1.8`
 
$ mvn clean graphwalker:generate-sources


You can find the generated test-interface file at:

target/generated-sources/graphwalker/com/cyberark/BstModel.java

-----------------------------------------------------------------------------------------------

Generated test interface:
-------------------------

// Generated by GraphWalker (http://www.graphwalker.org)
package com.cyberark;
 
import org.graphwalker.java.annotation.Model;
import org.graphwalker.java.annotation.Vertex;
import org.graphwalker.java.annotation.Edge;
 
@Model(file = "com/cyberark/BstModel.json")
public interface BstModel {
 
    @Vertex()
    void v_Found();
 
    @Edge()
    void e_Find();
 
    @Vertex()
    void v_Added();
 
    @Vertex()
    void v_VerifyInitialState();
 
    @Edge()
    void e_Add();
}

-----------------------------------------------------------------------------------------------

*** Generate the Bst Test ***
=============================

Create 'templates' folder under src/main

Download the java.template into:

src/main/templates/java.template

* Note that in the template each generated test method will have the following line as comment:

// throw new RuntimeException( "{LABEL} is not implemented yet!" );
This line is set to be generated as comment for the  training, 
it is recommended that in real life this line will not be commented to make sure to explicitly implement or not

-----------------------------------------------------------------------------------------------

java.template:
--------------

HEADER<{{
package com.cyberark;

import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/** 
* Set Class name
* Set Model name this class implements
* Set Model name in MODEL_PATH
* Set vertex name for the smoke-test to stop at
*/

/** *** Set Class & Model names *** */
public class MyTest extends ExecutionContext implements MyModel {

  public final static Path MODEL_PATH = Paths.get("com/cyberark/MyModel.json"); // *** Set model name ***
}}>HEADER
  @Override
  public void {LABEL}()
  {
    System.out.println( "{LABEL}" );
    // throw new RuntimeException( "{LABEL} is not implemented yet!" );
  }
FOOTER<{{
  @Test
    public void runSmokeTest() {
        new TestBuilder()
                .addContext(new BstTest().setNextElement(new Edge().setName("e_Init").build()),
                        MODEL_PATH,
                        new AStarPath(new ReachedVertex("v_MyVertex"))) // *** Set vertex name of the vertex to stop at ***
                .execute();
    }

    @Test
    public void runFunctionalTest() {
        new TestBuilder()
                .addContext(new BstTest().setNextElement(new Edge().setName("e_Init").build()),
                        MODEL_PATH,
                        new RandomPath(new EdgeCoverage(100))) // cover all edges
                .execute();
    }

    @Test
    public void runStabilityTest() {
        new TestBuilder()
                .addContext(new BstTest().setNextElement(new Edge().setName("e_Init").build()),
                        MODEL_PATH,
                        new RandomPath(new TimeDuration(1, TimeUnit.SECONDS))) // run the test for 1 second
                .execute();
    }
}
}}>FOOTER

-----------------------------------------------------------------------------------------------

Create the test directory structure:

under src/ create the following folders structure:

test/java/com/cyberark

Run the following command when you under the project root folder, to generate the test file that will be generated under test as: 

src/test/java/com/cyberark/MyTest.java

Generate test file:
-------------------

$ java -jar ../lib/graphwalker-cli-4.2.0.jar source -i src/main/resources/com/cyberark/BstModel.json src/main/templates/java.template > src/test/java/com/cyberark/MyTest.java

and rename the generated test file to the right file name (i.e. 'BstTest.java')

-----------------------------------------------------------------------------------------------

The generated BstTest.java file:
--------------------------------

package com.cyberark;

import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/** 
* Set Class name
* Set Model name this class implements
* Set Model name in MODEL_PATH
* Set vertex name for the smoke-test to stop at
*/

/** *** Set Class & Model names *** */
public class MyTest extends ExecutionContext implements MyModel {

  public final static Path MODEL_PATH = Paths.get("com/cyberark/MyModel.json"); // *** Set model name ***


  @Override
  public void e_Add()
  {
    System.out.println( "e_Add" );
    // throw new RuntimeException( "e_Add is not implemented yet!" );
  }


  @Override
  public void e_Find()
  {
    System.out.println( "e_Find" );
    // throw new RuntimeException( "e_Find is not implemented yet!" );
  }


  @Override
  public void e_Init()
  {
    System.out.println( "e_Init" );
    // throw new RuntimeException( "e_Init is not implemented yet!" );
  }


  @Override
  public void v_Added()
  {
    System.out.println( "v_Added" );
    // throw new RuntimeException( "v_Added is not implemented yet!" );
  }


  @Override
  public void v_Found()
  {
    System.out.println( "v_Found" );
    // throw new RuntimeException( "v_Found is not implemented yet!" );
  }


  @Override
  public void v_Start()
  {
    System.out.println( "v_Start" );
    // throw new RuntimeException( "v_Start is not implemented yet!" );
  }


  @Override
  public void v_VerifyInitialState()
  {
    System.out.println( "v_VerifyInitialState" );
    // throw new RuntimeException( "v_VerifyInitialState is not implemented yet!" );
  }


  @Test
    public void runSmokeTest() {
        new TestBuilder()
                .addContext(new BstTest().setNextElement(new Edge().setName("e_Init").build()),
                        MODEL_PATH,
                        new AStarPath(new ReachedVertex("v_MyVertex"))) // *** Set vertex name of the vertex to stop at ***
                .execute();
    }

    @Test
    public void runFunctionalTest() {
        new TestBuilder()
                .addContext(new BstTest().setNextElement(new Edge().setName("e_Init").build()),
                        MODEL_PATH,
                        new RandomPath(new EdgeCoverage(100))) // cover all edges
                .execute();
    }

    @Test
    public void runStabilityTest() {
        new TestBuilder()
                .addContext(new BstTest().setNextElement(new Edge().setName("e_Init").build()),
                        MODEL_PATH,
                        new RandomPath(new TimeDuration(1, TimeUnit.SECONDS))) // run the test for 1 second
                .execute();
    }
}

-----------------------------------------------------------------------------------------------

*** Execute the test ***
========================

If required, make sure to use the correct java
$ export JAVA_HOME=`/usr/libexec/java_home -v 1.8`

$ mvn clean install
$ mvn clean graphwalker:generate-sources test site jacoco:prepare-agent jacoco:report

-----------------------------------------------------------------------------------------------

*** Reports ***
===============

Junit
=====

You can find the Junit report under:
target/site/index.html
target/site/surefire-report.html

If you installed the 'Preview on Web Server' extension:
-------------------------------------------------------
open it, click on the opened file with the right mouse and choose to open it in a browser

Coverage
========

You can find the coverage report under:
target/site/jacoco/index.html

If you installed the 'Coverage Gutters' extension:
--------------------------------------------------
open the Bst.java file and click on 'Watch' at the bottom of VS-Code
You should see marks on the left side of the Bst.java file that mark the covered/uncovered lines

If you installed the 'Preview on Web Server' extension:
-------------------------------------------------------
open it, click on the opened file with the right mouse and choose to open it in a browser

-----------------------------------------------------------------------------------------------

