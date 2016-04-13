package controllers;

import models.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.password.*;
import play.data.validation.Constraints;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.util.*;

import static play.data.Form.form;

/*
	Created by anhdt_58 7/5/2015
*/

public class Password extends Controller {

    public static class ChangePassword {
		@Constraints.Required
        public String curPassword;
        @Constraints.Required
		public String newPassword;
        @Constraints.Required
		public String confirmPassword;
    }

    public static Result changePassword() {
        if (session().get("user") == null) {
            return redirect(routes.Application.index());
        }
        return ok(changepassword.render(form(ChangePassword.class)));
    }

    public static Result updatePassword() {
        Form<ChangePassword> changePasswordForm = form(ChangePassword.class).bindFromRequest();
        if (changePasswordForm.hasErrors()) {
            flash("error", "Please complete form");
            return badRequest(changepassword.render(changePasswordForm));
        }
        String curPassword = changePasswordForm.get().curPassword;
        String newPassword = changePasswordForm.get().newPassword;
		String confirmPassword = changePasswordForm.get().confirmPassword;
        String emailUser = session().get("user");
        if (Student.findByEmail(emailUser) != null) {
            Student student = Student.findByEmail(emailUser);
         
			 if (!student.pass.equals(curPassword)) {
                flash("error", "Current password error");
                return badRequest(changepassword.render(changePasswordForm));
            }
			
			if(!newPassword.equals(confirmPassword)){
				flash("error", "Confirm password error");
				return badRequest(changepassword.render(changePasswordForm));
			}
			
            student.pass = newPassword;
            student.update();
            return redirect(routes.Password.redirectChanngePassword());
        }
		return redirect(routes.Students.base_user()); 
    }

    public static Result redirectChanngePassword() {
        if (session().get("user") == null) {
            return redirect(routes.Application.index());
        }
        return ok(views.html.password.changepasswordsuccess.render());
    }
}