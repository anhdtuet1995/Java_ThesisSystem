package controllers;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by wellfrog on 4/15/2015.
 */
public class Secured extends Security.Authenticator {

    @Override

    public String getUsername(Http.Context ctx) {
		if(ctx.session().get("user")!=null) return ctx.session().get("user");
		else{
			if(ctx.session().get("admin")!=null) return ctx.session().get("admin");
			else {
				if(ctx.session().get("teacher")!=null) return ctx.session().get("teacher");
				else return null;
			}
		}
        

    }

    @Override

    public Result onUnauthorized(Http.Context ctx) {

        return redirect(routes.Application.login());

    }

}