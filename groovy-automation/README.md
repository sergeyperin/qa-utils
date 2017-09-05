<b><p>In order to run the demo you'll need to:</b></p>
create Maven Run Configuration in Run->Edit Configurations Idea menu. See CreateMavenRunner.png



<b><p>How Geb framework starts working, step by step: </p></b>


1. Start geb
  1. Read env variables
  2. Read GebConfig.groovy (src/test/resources/GebConfig.groovy)
  3. Index Pages (src/test/groovy/pages)
  4. Start the right from environment/browser (More on this)[http://www.gebish.org/manual/current/#configuration]
2. Start cucumber
  1. Determine if any tests match `@tag`
  2. Read in env variables
  3. Index steps in (/src/test/groovy/steps)
  4. Execute env.groovy (src/test/groovy/steps/env.groovy)
3. Geb-cucumber gets instantiated
  1. Classes are indexed for pages
  2. The list of pages is generated based on the classname
     ex. `LandingPage` to `landing page`
  3. Steps from geb-cucumber are injected into cucumber core
     A list of example steps can be found [here](https://github.com/tomdcc/geb-cucumber/tree/master/integration-test/src/cucumber/features)
4. Tests that match `@tag` are executed.
  1. The first statement, `When I go to the landing page` is parsed and the matching statement from the set of steps is selected. In this case this statement matches a predefined step in geb-cucumber
    1. This step uses the already started browser session to navigate to the registrationPage
    2. This occurs because `landing page` matches the Page class registrationPage under `pages/LandingPage.groovy`
    3. The browser is set to navigate to the value of the url object from the LandingPage class in LandingPage.groovy.
      * In this case `"https://www.anywayanyday.com"`
    4. Any `at` objects are evaluated to ensure we are on the page
    5. Any content objects with `required: true` are evaluated
  2. The second statement, `the departure city is present` uses a step predefined by geb-cucumber to ensure that the `departure city` field is present.
    1. During this step geb-cucumber looks for a page element on the current page with name `departureCity`
    2. Next the navigator/query `departureCity` is executed `$(By.xpath("//input[@placeholder='Departure city']"))`
    3. The navigator is checked to ensure the returned object exists, not null, etc.
  3. The third statement, `the arrive in city is present` uses another predefined step to ensure that the `arrive in city` field is present.
    * This works in much the same way as the previous step. Except that the navigator `$("label", text: "ARRIVE IN").siblings("input")` is wrapped in a waitFor
  7. Next the results of each step are computed and written in Junit format to test-results.junit
  
  
  <b><p>About YML language:</b></p>
  
  YAML is better for make configurations
  JSON is simpler than XML. YAML is even simpler.
  Because:
   - less verbose
   - YAML is really more of a data format
   - YAML has a couple of big advantages over JSON, including the ability to self reference, support for complex datatypes, embedded block literals, comments, and more.

 <b><p>Geb Groovy framework guide can be found here:</b></p>
http://www.gebish.org/manual/current/ <br><br>
You can find the article about Geb here:
https://softwarequalitydeliveryblog.wordpress.com/2017/08/11/very-dsl-automation-framework/


<b><p>How to setup Cucumber JVM for IDE Idea:</b></p>
http://blog.czeczotka.com/2014/07/22/cucumber-jvm-with-maven-in-minutes/

<b><p>Basic difference between Java and Groovy:</b></p>
http://javarevisited.blogspot.com/2016/09/10-basic-differences-between-java-and-groovy-programming.html#ixzz4rnLECQIO