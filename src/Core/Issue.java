package Core;


import java.util.ArrayList;

public class Issue {
    private static ArrayList<String>problems;

    public static void Issue(){
        problems = new ArrayList<String>();
    }

    public static void addIssue(String issue){
        System.out.println("Issue was added: " + issue);
        problems.add(issue);
    }

    public static void removeIssue(String issue){
        problems.remove(issue);
    }

    public static int numberOfIssues(){
        return problems.size();
    }

    public static ArrayList<String> issueList(){
        return problems;
    }
}
