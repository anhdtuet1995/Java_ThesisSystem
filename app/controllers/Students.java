package controllers;
import com.avaje.ebean.Ebean;
import models.Student;
import models.Admin;
import play.data.DynamicForm;
import play.mvc.Result;
import play.mvc.Controller;
import play.mvc.Security;
import views.html.actions.*;
import views.html.*;
import java.util.List;
import play.data.Form;
/**
 * Created by wellfrog on 2/9/2015.
 */
@Security.Authenticated(Secured.class)
public class Students extends Controller {
    private static final Form<Student> studentForm = Form.form(Student.class);
    public static Result details(Student student) {
    	if(session("user")==null) return notFound(notFoundPage.render());
    	if (student == null) {
			return notFound(notFoundPage.render());
        }
        //return ok(student.toString());
        Form<Student> filledForm = studentForm.fill(student);
        return ok(details_user.render(filledForm,student));
    }
    /*public static Result myaccount() {
    	Student student = Student.findByEmail(session("user"));
    	Form<Student> filledForm = studentForm.fill(student);
        return ok(details_user.render(filledForm));
    }*/
    public static Result base_user() {
    	if(session("user")==null) return notFound(notFoundPage.render());
    	Student student = Student.findByEmail(session("user"));
        return ok(base_user.render(student));
    }
    
    public static Result save() {
    	if(session("user")==null) return notFound(notFoundPage.render());
    	Form<Student> boundForm = studentForm.bindFromRequest();
    	if (boundForm.hasErrors()) {
            flash("error", "Please correct the form below.");
            Student student = Student.findByEmail(session("user"));
            return badRequest(details_user.render(boundForm,student));
        }
        Student student = boundForm.get();
    	Ebean.update(student);
        return redirect(routes.Students.base_user());
    }
}
