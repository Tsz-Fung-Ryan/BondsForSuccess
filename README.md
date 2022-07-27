# BondsForSuccess
FullStack Matching Software for CIC

Current considerations:
  1. Technical Algorithm to be used by backend -> simple keyword matching sufficent or a learning algorithm (database would need to be provided)
  2. Where will application be stored currently planning to be setup as a one button full stack java application but having it stored in server would be ideal

How to Run the Project

  Running the backend
    1. Install Java 18
    2. Setup Path variable
    3. Setup JAVA_HOME variable
    4. Open a cmd prompt and navigate to the backend folder
    5. Run `mvnw spring-boot:run`
    
  Running the frontend (this is assuming you have already installed java for the backend setup)
    1. Install Node
    2. Open a cmd prompt and navigate to the frontend folder
    3. Run `npm install`, `npm run generate:api` and `ng serve`
