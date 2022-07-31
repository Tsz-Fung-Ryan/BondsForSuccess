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
    1. Install Java 18: https://www.oracle.com/java/technologies/downloads/
    2. Head to the backend folder
    3. Double click on the jar to turn on the backend
    
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

  How to Update the Backend
    Updating the properties (an installation of maven is required to regenerate the jar)
      1. Navigate to backend/application.properties
      2. Under mentee.key.mappings update the properties the following way:
        a. Write the name of the column then a colon followed by what to map the property to
          ex: "What is your name?": 'name'
        b. current sourced properties are: name, emailAddress, genderPreference, gender, keywords
      3. Repeat step 2 but for mentor.key.mappings using the same structure
      4. To update the keywords property add or remove the keepwords with each one seperated by a comma
      5. The accepted threshold is the percentage of keywords the program will look for to be considered an "ideal" match. 
        Do note that with a higher number the later matches will be less optimal (the logs will indicate if an ideal match could not be done with the remainder matches)
  Troubleshooting steps:
    1. Ensure the first row contains only the header column (previously there was some filler items ie q1,q2 this will make it hard to generate the mappings)
    2. Ensure each person is on its own row no new lines are submitted
    3. If still having issues wrap each cell with "" when it's converted to csv this will ensure each cell is entries are specified    
