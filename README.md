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
    2. Open the project folder if you haven't already and navigate to the frontend -> bonds-for-success folder
    3. Double click the executable file to setup and start the frontend of the project
      a. If you are on a windows computer, double click the `Setup-And-Run-Frontend-Windows.cmd` file
      b. If you are on a mac computer, double click the `Setup-And-Run-Frontend-Mac.command` file
        i. If the `.command` file doesn't execute you might have to give the file execute permissions
          1. Click the `Setup-And-Run-Frontend-Mac.command` file and from the `File` menu, select `Get Info`
          2. Click the disclosure triangle next to Sharing & Permissions to display permissions for the selected file or folder
          3. Click the lock and authenticate with an administrator account
          4. Use the menus next to users and groups to change the permissions for your account (it would be the entry with `me` in the name)
          5. Close the Info window
          6. You should now be able to double click the `Setup-And-Run-Frontend-Mac.command` file to run the frontend
