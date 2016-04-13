package controllers;
import com.avaje.ebean.Ebean;
import models.Student;
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
public class Persons extends Controller {
    private static final Form<Student> studentForm = Form.form(Student.class);
    private static final Form<Teacher> teacherForm = Form.form(Teacher.class);
    public static Result details(Student student) {
    	if(session("admin")==null) return notFound(notFoundPage.render());
    	if (student == null) {

			return notFound(notFoundPage.render());
        }
        //return ok(student.toString());
        Form<Student> filledForm = studentForm.fill(student);
        return ok(details.render(filledForm,session("admin")));
    }
    public static Result details_teacher(Teacher teacher) {
    	if(session("admin")==null) return notFound(notFoundPage.render());
    	if (teacher == null) {

			return notFound(notFoundPage.render());
        }
        //return ok(student.toString());
        Form<Teacher> filledForm = teacherForm.fill(teacher);
        return ok(details_teacher.render(filledForm,session("admin")));
    }
    public static Result list() {
    	if(session("admin")==null) return notFound(notFoundPage.render());
        List<Student> students = Student.findAll();
        return ok(list_student.render(students,session("admin")));
    }
    public static Result list_teacher() {
    	if(session("admin")==null) return notFound(notFoundPage.render());
        List<Teacher> teachers = Teacher.findAll();
        return ok(list_teacher.render(teachers,session("admin")));
    }
    public static Result base() {
    	if(session("admin")==null) return notFound(notFoundPage.render());
        return ok(base.render(session("admin")));
    }
    public static Result newStudent() {
    	if(session("admin")==null) return notFound(notFoundPage.render());
        return ok(newStudent.render(studentForm,session("admin")));
	}
    public static Result newTeacher() {
    	if(session("admin")==null) return notFound(notFoundPage.render());
        return ok(newTeacher.render(teacherForm,session("admin")));
	}
	public static Result save_teacher() {
    	if(session("admin")==null) return notFound(notFoundPage.render());
    	Form<Teacher> boundForm = teacherForm.bindFromRequest();
    	if (boundForm.hasErrors()) {

            flash("error", "Please correct the form below.");

            return badRequest(newTeacher.render(boundForm,session("admin")));

        }
        Teacher teacher = boundForm.get();
		if(Teacher.findByEmail(teacher.email)!=null) {
			flash("error", String.format("Email %s existed", teacher.email));
			return badRequest(newTeacher.render(teacherForm,session("admin")));
		}
        if (teacher.id== null) {
			teacher.save();
			flash("success", String.format("Successfully added account %s", teacher.email));
		}
		else{
			teacher.update();
		}
		return redirect(routes.Persons.list_teacher());
    }
    public static Result save() {
    	if(session("admin")==null) return notFound(notFoundPage.render());
    	Form<Student> boundForm = studentForm.bindFromRequest();
    	if (boundForm.hasErrors()) {

            flash("error", "Please correct the form below.");

            return badRequest(details.render(boundForm,session("admin")));

        }
        Student student = boundForm.get();
		if(Student.findByEmail(student.email)!=null) {
			flash("error", String.format("Email %s existed", student.email));
			return badRequest(newStudent.render(studentForm,session("admin")));
		}

        if (student.id== null) {
			student.save();
			flash("success", String.format("Successfully added account %s", student.email));
		}
		else{
			student.update();
		}
		return redirect(routes.Persons.list());
    }
    public static Result delete(String email) {
    	if(session("admin")==null) return notFound(notFoundPage.render());
        Student delete_student = Student.findByEmail(email);
        delete_student.delete();
        return redirect(routes.Persons.list());
    }
    public static Result delete_teacher(String email) {
    	if(session("admin")==null) return notFound(notFoundPage.render());
        Teacher delete_teacher = Teacher.findByEmail(email);
        delete_teacher.delete();
        return redirect(routes.Persons.list_teacher());
    }
}
