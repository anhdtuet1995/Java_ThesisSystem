package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wellfrog on 2/9/2015.
 */
@Entity
public class Admin extends Model implements play.mvc.PathBindable<Admin> {
	@Id
	public int id;
    public  String email;
    public  String pass;

    public Admin( String email, String pass) {
        this.email = email;
        this.pass = pass;	
    }
    public  String toString(){
        return  email ;
    }
	public static Admin findByEmail(String email) {

        return find.where().eq("email", email).findUnique();

    }
    public static Admin authenticate(String email, String password) {

        return find.where().eq("email", email).eq("pass", password).findUnique();

    }
    public static Finder<String,Admin> find=new Finder<>(String.class,Admin.class);
    @Override
    public Admin bind(String s, String s1) {
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

}

