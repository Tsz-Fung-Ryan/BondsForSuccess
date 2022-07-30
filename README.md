# BondsForSuccess
FullStack Matching Software for CIC

Current considerations:
  1. Technical Algorithm to be used by backend -> simple keyword matching sufficent or a learning algorithm (database would need to be provided)
  2. Where will application be stored currently planning to be setup as a one button full stack java application but having it stored in server would be ideal

How to Run the Project

  Download the project
    1. Navigate to https://github.com/Tsz-Fung-Ryan/BondsForSuccess in a web browser
    2. Click the green `Code` button
    3. Click the `Download ZIP` button
    4. Unzip the downloaded zip file somewhere

  Running the backend
    1. Install Java 18
    2. Setup Path variable
    3. Setup JAVA_HOME variable
    4. Open a cmd prompt and navigate to the backend folder
    5. Run `mvnw spring-boot:run`
    
  Running the frontend (this is assuming you have already installed java for the backend setup)
    1. Install Node
      a. Open https://nodejs.org/en/download/ in a browser and click the download button for the installer for your operating system (mac or windows)
    2. Open a command prompt (or a terminal if you are on a mac computer) and navigate to the frontend folder
    3. Run the following commands `npm install`, `npm run generate:api` and `ng serve`
      a. To run a command just type/copy the command from above and put it in the command prompt/terminal you opened
      b. Hit enter and the command will run
      c. Run all 3 commands in succession, then you can open a browser and enter the following url `http://localhost:4200/` to see the application
