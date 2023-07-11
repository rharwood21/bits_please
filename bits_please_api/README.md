## bits_please_api Package

The `bits_please_api` package contains the core components of the Trivial Compute Game.

### Files

- `APIRequestException.java`: Exception class for API Errors. 
- `APITest.java`: **Executable** class to test the API functionality. 
- `BitsPleaseRequest.java`: Facilitates the actual REST API request on behalf of the relevant classes.
- `ConfigReader.java`: Reads the local `config.ini` file for required inputs. Stores Client UUID for session persistence. 
- `CreateQuestions.java`: **Use this class to create questions during runtime.** 
- `InitializeClient.java`: Initializes client. No need to call this class directly. 
- `ModifyQuestions.java`: **Use this class to modify questions during runtime.**
- `RetrieveQuestions.java`: **Use this class to retrieve questions during runtime.**
- `QuestionDifficulty.java`: Enum class to use for passing question difficulty.

### Example: Usage during gameplay
```
CreateQuestions createQuestions = CreateQuestions.getInstance();
String questionUuid = createQuestions.CreateQuestion(
        "History", 
        "Who was the first president of US?", 
        "George Washington"
);
System.out.println("Question UUID: " + questionUuid);
```

### Usage

To run the API Test, to see the functioning of relevant classes, follow these steps:

1. Make sure you have Java Development Kit (JDK) installed on your system.
2. Compile the Java files in both the `bits_please_api` package using the `javac` command. For example:
   ```
   javac bits_please_api/*.java
   ```
3. Run the test using the `java` command and specify the fully qualified class name of `APITest` in the `bits_please_api` package as the entry point. For example:
   ```
   java bits_please_api.APITest
   ```
4. The test will start and relevant output will be printed to the terminal.

Please make sure to adjust the `javac` and `java` commands based on your specific directory structure and package names.

### Dependencies
1. In your IDE, add the following as a project dependency:
   1. Example (IntelliJ IDEA):
      1. File > Project Structure > Project Settings > Modules >
      2. Add > Library > From Maven > Search `com.apache.httpcomponents:httpclient:4.3.2`
```
com.apache.httpcomponents.httpclient
com.json
```
2. Versions used during development:
   1. **Maven**: _com.apache.httpcomponents:httpclient:4.3.2_
   2. **Maven**: _com.json:json:20230618_