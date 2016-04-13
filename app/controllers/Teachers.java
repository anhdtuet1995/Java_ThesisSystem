package controllers;
import com.avaje.ebean.Ebean;
import models.Teacher;
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
public class Teachers extends Controller {
    private static final Form<Teacher> teacherForm = Form.form(Teacher.class);
    public static Result details(Teacher teacher) {
    	if(session("teacher")==null) return notFound(notFoundPage.render());
    	if (teacher == null) {
        	return notFound(notFoundPage.render());
        }
        Form<Teacher> filledForm = teacherForm.fill(teacher);
        return ok(details_user_teacher.render(filledForm,teacher));
    }
    public static Result base_user_teacher() {
    	if(session("teacher")==null) return notFound(notFoundPage.render());
    	Teacher teacher = Teacher.findByEmail(session("teacher"));
        return ok(base_user_teacher.render(teacher));
    }
    
    public static Result save() {
    	if(session("teacher")==null) return notFound(notFoundPage.render());
    	Form<Teacher> boundForm = teacherForm.bindFromRequest();
    	if (boundForm.hasErrors()) {
            flash("error", "Please correct the form below.");
            Teacher teacher = Teacher.findByEmail(session("teacher"));
            return badRequest(details_user_teacher.render(boundForm,teacher));
        }
        Teacher teacher = boundForm.get();
    	Ebean.update(teacher);
        return redirect(routes.Teachers.base_user_teacher());
    }
}
