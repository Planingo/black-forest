package model.constraint;

import model.module.Cours;

import java.util.List;

public class Student
{
    private Integer       idStudent;
    private List<Cours> listClassees;


    public Student(Integer idStudent, List<Cours> listClassees)
    {
        this.listClassees = listClassees;
        this.idStudent = idStudent;
    }

    public List<Cours> getListClassees()
    {
        return listClassees;
    }

    public Integer getIdStudent()
    {
        return idStudent;
    }

}
