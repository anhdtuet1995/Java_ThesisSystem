package models;

import play.db.ebean.Model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wellfrog on 2/9/2015.
 */
@Entity
public class Student extends Model implements play.mvc.PathBindable<Student> {
	@Id
	public Long id;
    public  String name;
    /*****
    Usertype =0 : User (default)
    Usertype =1 : Admin
    ******/
    public int usertype;
    public Date birthday;
    public  String adress;
    public  long phone;
    public  String email;
    public  String pass;
    public  String joined_year;
    public  String faculty;

    public Student( String name,int usertype ,Date birthday, String adress, long phone, String email, String pass, String joined_year, String faculty) {
        this.name = name;
        this.usertype=usertype;
        this.birthday = birthday;
        this.adress = adress;
        this.phone = phone;
        this.email = email;
        this.pass = pass;
        this.joined_year = joined_year;
        this.faculty = faculty;
    }
    public  String toString(){
        return name +"---" + email ;
    }
    public static List<Student> findAll(){
		return find.all();
	}
	public static Student findByEmail(String email) {

        return find.where().eq("email", email).findUnique();

    }
    public static boolean exist(Long id) {

        if(findid.where().eq("id", id).findUnique()!=null) return true;
        else return false;

    }
    public static Student authenticate(String email, String password) {

        return find.where().eq("email", email)

                .eq("pass", password).findUnique();

    }
    @Override
    public Student bind(String s, String s1) {
        return findByEmail(s1);
    }
    @Override
    public String unbind(String s) {
        return email;
    }

    @Override
    public String javascriptUnbind() {
        return email;
    }
    public static Finder<String,Student> find=new Finder<>(String.class,Student.class);
    public static Finder<Long,Student> findid=new Finder<>(Long.class,Student.class);
}

