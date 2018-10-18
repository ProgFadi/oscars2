/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oscarcsv;

/**
 *
 * @author Eng. Fadi R
 */

// implement Comparable to using implemented method and using it in sorting decision
public class Oscar implements Comparable<Oscar>{
    private int index,year,age;
    private String name,movie;

    
    public Oscar(int index2,int year2,int age2,String name2,String movie2)
    {
        this.index=index2;
        this.year=year2;
        this.age=age2;
        this.name=name2;
        this.movie=movie2;
    }
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    @Override
    public int compareTo(Oscar t) {
     
        return this.year-t.getYear(); // asending sort
        //return t.getYear()-this.getYear();
    }
    
    
}
