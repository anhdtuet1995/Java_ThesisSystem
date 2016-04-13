package controllers;
import models.Admin;
import models.Student;
import models.Teacher;
import play.*;
import play.data.Form;
import play.mvc.*;
import static play.data.Form.form;
import views.html.*;
import views.html.actions.login;

/*
	Create by wellfrog
	edit by anh_dt
*/

public class Application extends Controller {
    
	@Security.Authenticated(Secured.class)
	public static Result index() {
        if (session().get("user") != null) return redirect(routes.Students.base_user());
        if (session().get("admin") != null) return redirect(routes.Persons.base());
        if (session().get("teacher") != null) return redirect(routes.Teachers.base_user_teacher());
        return ok(login.render(form(Login.class)));
    }
	
    public  static class Login{
        public String username;
        public String password;
    }
    public static Result authenticate() {

        Form<Login> loginForm = form(Login.class).bindFromRequest();
//         return ok(loginForm.toString());
        String email = loginForm.get().username;

        String password = loginForm.get().password;
        session().clear();

/*************
	Check account authenticate
	session 1 is admin
	session 2 is student
	session 3 is faculty
*************/
//return ok(loginForm.toString());
        if (Student.authenticate(email, password) == null && Admin.authenticate(email, password) ==null && Teacher.authenticate(email, password) ==null) {
            //flash("error", "Invalid email and/or password");
            return redirect(routes.Application.login());

        }
    	else{
    		if(Student.authenticate(email, password) !=null ){
    			session("user", email);
    			return redirect(routes.Students.base_user());
    		}
    		else{
    			if(Admin.authenticate(email, password) !=null ){
	    			session("admin", email);
	    			return redirect(routes.Persons.base());
    			}
    			else{
    				session("teacher", email);
	    			return redirect(routes.Teachers.base_user_teacher());
    			}
    		}
    	}
    }
    public static Result login() {
        return ok(login.render(form(Login.class)));
    }
    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(routes.Application.login());
    }
}
