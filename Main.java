import java.util.*;

public class Main {
    static Scanner sc=new Scanner(System.in);
    static ArrayList<Student> students=new ArrayList<>();
    static ArrayList<Company> companies=new ArrayList<>();
    static ArrayList<Job> jobs=new ArrayList<>();
    static ArrayList<Application> applications=new ArrayList<>();
    static int nextApplicationId=1;

    public static void main(String[] args){
        loadSampleData();
        while(true){
            System.out.println("\n===== STUDENT PLACEMENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Student Login\n2. Admin Login\n3. Exit");
            int c=readInt("Enter your choice: ");
            if(c==1) studentLogin();
            else if(c==2) adminLogin();
            else if(c==3){System.out.println("Thank you for using the system.");break;}
            else System.out.println("Invalid choice.");
        }
    }

    static void studentLogin(){
        int id=readInt("\nEnter Student ID: "); Student s=findStudent(id);
        if(s==null){System.out.println("Student not found.");return;}
        System.out.println("Login successful. Welcome, "+s.name+"!"); studentMenu(s);
    }

    static void studentMenu(Student s){
        while(true){
            System.out.println("\n===== STUDENT MENU =====");
            System.out.println("1. View Profile\n2. View Available Jobs\n3. Check Eligibility\n4. Apply for Job\n5. View My Applications\n6. Update Profile\n7. Logout");
            int c=readInt("Enter your choice: ");
            switch(c){
                case 1: displayStudent(s); break;
                case 2: displayJobs(); break;
                case 3: checkEligibility(s); break;
                case 4: applyForJob(s); break;
                case 5: viewMyApplications(s); break;
                case 6: updateProfile(s); break;
                case 7: System.out.println("Logged out successfully."); return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    static void checkEligibility(Student s){
        displayJobs(); int jobId=readInt("\nEnter Job ID to check eligibility: "); Job j=findJob(jobId);
        if(j==null){System.out.println("Job not found.");return;}
        System.out.println("\n===== ELIGIBILITY RESULT =====");
        System.out.println("Student: "+s.name+" | Job: "+j.role+" | Company: "+companyName(j.companyId));
        boolean ok=true;
        if(s.cgpa<j.minCgpa){System.out.println("FAILED: CGPA below required minimum.");ok=false;} else System.out.println("PASSED: CGPA requirement.");
        if(!j.branch.equalsIgnoreCase("ALL")&&!s.branch.equalsIgnoreCase(j.branch)){System.out.println("FAILED: Branch does not match.");ok=false;} else System.out.println("PASSED: Branch requirement.");
        if(s.backlogs>j.maxBacklogs){System.out.println("FAILED: Too many backlogs.");ok=false;} else System.out.println("PASSED: Backlog requirement.");
        for(String req:j.skills){if(!hasSkill(s,req)){System.out.println("FAILED: Missing skill - "+req);ok=false;}else System.out.println("PASSED: Skill - "+req);}
        System.out.println(ok?"RESULT: CONGRATULATIONS! You are eligible.":"RESULT: You are NOT eligible for this job.");
    }

    static boolean isEligible(Student s,Job j){
        if(s.cgpa<j.minCgpa)return false;
        if(!j.branch.equalsIgnoreCase("ALL")&&!s.branch.equalsIgnoreCase(j.branch))return false;
        if(s.backlogs>j.maxBacklogs)return false;
        for(String req:j.skills)if(!hasSkill(s,req))return false;
        return true;
    }
    static boolean hasSkill(Student s,String req){for(String x:s.skills)if(x.equalsIgnoreCase(req.trim()))return true;return false;}

    static void applyForJob(Student s){
        System.out.println("\n===== APPLY FOR JOB =====");
        displayJobs();
        int jobId=readInt("Enter Job ID: ");
        Job j=findJob(jobId);
        if(j==null){System.out.println("Job not found.");return;}
        if(!isEligible(s,j)){System.out.println("You are NOT eligible for this job. Application cannot be submitted.");return;}
        for(Application a:applications)if(a.studentId==s.id&&a.jobId==j.id){System.out.println("You have already applied. Application ID: "+a.id+", Status: "+a.status);return;}
        Application a=new Application(nextApplicationId++,s.id,j.id,"PENDING");
        applications.add(a);
        System.out.println("\nApplication submitted successfully!");
        System.out.println("Application ID: "+a.id);System.out.println("Company: "+companyName(j.companyId));System.out.println("Role: "+j.role);System.out.println("Status: PENDING");
    }

    static void viewMyApplications(Student s){
        System.out.println("\n===== MY APPLICATIONS ====="); boolean found=false;
        for(Application a:applications)if(a.studentId==s.id){Job j=findJob(a.jobId);System.out.println("Application ID: "+a.id+" | Job: "+j.role+" | Company: "+companyName(j.companyId)+" | Status: "+a.status);found=true;}
        if(!found)System.out.println("You have not applied for any job yet.");
    }

    static void updateProfile(Student s){
        String n=readString("Enter new name (Enter to keep current): ");if(!n.isEmpty())s.name=n;
        String cg=readString("Enter new CGPA (Enter to keep current): ");if(!cg.isEmpty())try{s.cgpa=Double.parseDouble(cg);}catch(Exception e){System.out.println("Invalid CGPA.");}
        System.out.println("Profile updated successfully.");
    }

    static void adminLogin(){
        System.out.println("\n===== ADMIN LOGIN ====="); String u=readString("Username: "),p=readString("Password: ");
        if(u.equals("admin")&&p.equals("admin123")){System.out.println("Login successful.");adminMenu();}else System.out.println("Invalid username or password.");
    }
    static void adminMenu(){
        while(true){
            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. View Students\n2. View Companies\n3. View Jobs\n4. Add Student\n5. Add Company\n6. Add Job\n7. View Applications\n8. Update Application Status\n9. Placement Statistics\n10. Logout");
            int c=readInt("Enter your choice: ");
            switch(c){case 1:displayStudents();break;case 2:displayCompanies();break;case 3:displayJobs();break;case 4:addStudent();break;case 5:addCompany();break;case 6:addJob();break;case 7:displayApplications();break;case 8:updateApplicationStatus();break;case 9:statistics();break;case 10:return;default:System.out.println("Invalid choice.");}
        }
    }

    static void addStudent(){
        int id=readInt("Student ID: ");if(findStudent(id)!=null){System.out.println("ID already exists.");return;}
        String name=readString("Name: "),branch=readString("Branch: ");double cg=readDouble("CGPA: ");int back=readInt("Backlogs: "),year=readInt("Graduation Year: ");
        ArrayList<String> sk=skills(readString("Skills (comma separated): "));students.add(new Student(id,name,branch,cg,sk,year,back));System.out.println("Student added successfully.");
    }
    static void addCompany(){int id=readInt("Company ID: ");if(findCompany(id)!=null){System.out.println("ID already exists.");return;}companies.add(new Company(id,readString("Company Name: ")));System.out.println("Company added successfully.");}
    static void addJob(){
        if(companies.isEmpty()){System.out.println("Add a company first.");return;}displayCompanies();
        int id=readInt("Job ID: ");if(findJob(id)!=null){System.out.println("Job ID already exists.");return;}
        int cid=readInt("Company ID: ");if(findCompany(cid)==null){System.out.println("Company not found.");return;}
        String role=readString("Job Role: ");double cg=readDouble("Minimum CGPA: ");String branch=readString("Branch (or ALL): ");ArrayList<String> sk=skills(readString("Required Skills (comma separated): "));int back=readInt("Maximum Backlogs: ");double pack=readDouble("Package (LPA): ");
        jobs.add(new Job(id,cid,role,cg,branch,sk,back,pack));System.out.println("Job added successfully.");
    }

    static void displayStudents(){System.out.println("\n===== STUDENTS =====");for(Student s:students)displayStudent(s);}
    static void displayStudent(Student s){System.out.println("--------------------------------");System.out.println("ID: "+s.id+" | Name: "+s.name+" | Branch: "+s.branch+" | CGPA: "+s.cgpa+" | Backlogs: "+s.backlogs+" | Year: "+s.year+" | Skills: "+s.skills);}
    static void displayCompanies(){System.out.println("\n===== COMPANIES =====");for(Company c:companies)System.out.println("ID: "+c.id+" | "+c.name);}
    static void displayJobs(){System.out.println("\n===== AVAILABLE JOBS =====");for(Job j:jobs)System.out.println("ID: "+j.id+" | "+companyName(j.companyId)+" | "+j.role+" | Min CGPA: "+j.minCgpa+" | Branch: "+j.branch+" | Skills: "+j.skills+" | Max Backlogs: "+j.maxBacklogs+" | Package: "+j.packageLpa+" LPA");}
    static void displayApplications(){System.out.println("\n===== ALL APPLICATIONS =====");if(applications.isEmpty()){System.out.println("No applications found.");return;}for(Application a:applications){Student s=findStudent(a.studentId);Job j=findJob(a.jobId);System.out.println("Application ID: "+a.id+" | Student: "+s.name+" | Job: "+j.role+" | Company: "+companyName(j.companyId)+" | Status: "+a.status);}}
    static void updateApplicationStatus(){if(applications.isEmpty()){System.out.println("No applications available.");return;}displayApplications();int id=readInt("Application ID: ");Application a=findApplication(id);if(a==null){System.out.println("Not found.");return;}System.out.println("1. PENDING\n2. SHORTLISTED\n3. SELECTED\n4. REJECTED");int c=readInt("New status: ");if(c<1||c>4){System.out.println("Invalid status.");return;}String[] st={"PENDING","SHORTLISTED","SELECTED","REJECTED"};a.status=st[c-1];System.out.println("Status updated to "+a.status);}
    static void statistics(){int p=0,sh=0,se=0,r=0;for(Application a:applications){if(a.status.equals("PENDING"))p++;else if(a.status.equals("SHORTLISTED"))sh++;else if(a.status.equals("SELECTED"))se++;else r++;}System.out.println("\nStudents: "+students.size()+"\nCompanies: "+companies.size()+"\nJobs: "+jobs.size()+"\nApplications: "+applications.size()+"\nPending: "+p+"\nShortlisted: "+sh+"\nSelected: "+se+"\nRejected: "+r);}

    static Student findStudent(int id){for(Student s:students)if(s.id==id)return s;return null;}
    static Company findCompany(int id){for(Company c:companies)if(c.id==id)return c;return null;}
    static Job findJob(int id){for(Job j:jobs)if(j.id==id)return j;return null;}
    static Application findApplication(int id){for(Application a:applications)if(a.id==id)return a;return null;}
    static String companyName(int id){Company c=findCompany(id);return c==null?"Unknown Company":c.name;}
    static ArrayList<String> skills(String x){ArrayList<String> a=new ArrayList<>();for(String s:x.split(","))if(!s.trim().isEmpty())a.add(s.trim());return a;}
    static int readInt(String msg){while(true){try{System.out.print(msg);return Integer.parseInt(sc.nextLine().trim());}catch(Exception e){System.out.println("Please enter a valid integer.");}}}
    static double readDouble(String msg){while(true){try{System.out.print(msg);return Double.parseDouble(sc.nextLine().trim());}catch(Exception e){System.out.println("Please enter a valid number.");}}}
    static String readString(String msg){System.out.print(msg);return sc.nextLine().trim();}

    static void loadSampleData(){
        students.add(new Student(101,"Arun","CSE",8.4,skills("Java,SQL,C++"),2028,0));
        students.add(new Student(102,"Rahul","CSE",7.2,skills("Java"),2028,1));
        companies.add(new Company(1,"Tech Solutions"));companies.add(new Company(2,"ABC Technologies"));
        jobs.add(new Job(101,1,"Java Developer",7.5,"CSE",skills("Java,SQL"),0,8.0));
        jobs.add(new Job(102,2,"Software Engineer",7.0,"ALL",skills("Java"),2,6.5));
    }

    static class Student{int id,year,backlogs;String name,branch;double cgpa;ArrayList<String> skills;Student(int i,String n,String b,double c,ArrayList<String>s,int y,int bl){id=i;name=n;branch=b;cgpa=c;skills=s;year=y;backlogs=bl;}}
    static class Company{int id;String name;Company(int i,String n){id=i;name=n;}}
    static class Job{int id,companyId,maxBacklogs;String role,branch;double minCgpa,packageLpa;ArrayList<String> skills;Job(int i,int c,String r,double g,String b,ArrayList<String>s,int m,double p){id=i;companyId=c;role=r;minCgpa=g;branch=b;skills=s;maxBacklogs=m;packageLpa=p;}}
    static class Application{int id,studentId,jobId;String status;Application(int i,int s,int j,String st){id=i;studentId=s;jobId=j;status=st;}}
}
