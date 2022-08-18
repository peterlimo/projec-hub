package com.example.projecthub.models;

public class Student_Details {
    private String Full_Name,Reg_No,Project_Tittle;

    public Student_Details(){

    }
    public Student_Details(String full_name, String reg_no, String project_tittle){
        this.Full_Name = full_name;
        this.Reg_No = reg_no;
        this.Project_Tittle = project_tittle;
    }
    public String getFull_Name()
    {
        return Full_Name;
    }

    public void setFull_Name(String full_Name) {
        this.Full_Name = full_Name;
    }
    public String getReg_No()
    {
        return Reg_No;
    }

    public void setReg_No(String reg_no) {
        this.Reg_No = reg_no;
    }
    public String getProject_Tittle()
    {
        return Project_Tittle;
    }

    public void setProject_Tittle(String project_Tittle) {
        this.Project_Tittle = project_Tittle;
    }
}
