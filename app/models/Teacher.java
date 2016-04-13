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
public class Teacher extends Model implements play.mvc.PathBindable<Teacher> {
	@Id
	public Long id;
    public  String name;
    /*****
    Usertype =0 : User (default)
    Usertype =1 : Admin
    ******/
    public int usertype;
    public  String work;
    public  long phone;
    public  String email;
    public  String pass;

    public Teacher( String name,int usertype ,Date birthday, String adress, long phone, String email, String pass, String joined_year, String faculty) {
        this.name = name;
        this.usertype=usertype;
        this.work = work;
        this.phone = phone;
        this.email = email;
        this.pass = pass;
    }
    public  String toString(){
        return name +"---" + email ;
    }
    public static List<Teacher> findAll(){
		return find.all();
	}
	public static Teacher findByEmail(String email) {

        return find.where().eq("email", email).findUnique();

    }
    public static boolean exist(Long id) {

        if(findid.where().eq("id", id).findUnique()!=null) return true;
        else return false;

    }
    public static Teacher authenticate(String email, String password) {

        return find.where().eq("email", email)

                .eq("pass", password).findUnique();

    }
    @Override
    public Teacher bind(String s, String s1) {
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
    public static Finder<String,Teacher> find=new Finder<>(String.class,Teacher.class);
    public static Finder<Long,Teacher> findid=new Finder<>(Long.class,Teacher.class);
}

