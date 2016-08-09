q4webdriver
==============

**Intellij Setup**

1. Download intelliJ IDE Community Edition http://www.jetbrains.com/idea/download/

2. Download & Install HomeBrew http://brew.sh/

3. run `brew install chromedriver`

4. Open pom.xml file with IntelliJ

5. Go to Preferences > Version Control > GitHub > Add your credentials and test the connection works

6. If there is an issue with the project setup or modules, go to File > Project Structure > Modules. Make sure there is a
module for q4webdriver, if not add one.

7. If there is an issue with maven go to View > Tool Windows > Maven Projects and reimport or add a project by selecting the pom.xml

8. If there is an issue with Git, restart intellij and look for the popup to 'Add Git Root'


To trigger build manually, copy the line below and paste it into your terminal (change {"TEST_ENV":"BETA","SUITE_NAME":"SmokeTest"} to appropriate environment and suite if needed)
==========
' curl -H "Content-Type: application/json" -d '{"build_parameters": {"TEST_ENV":"BETA","SUITE_NAME":"NightRun"}}' https://circleci.com/api/v1/project/q4mobile/q4testsuite/tree/master?circle-token=5439aa08dd79b73f2f13535f252c0d69dbb31edb '
