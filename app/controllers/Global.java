import com.avaje.ebean.Ebean;

import play.*;

import models.*;

import play.libs.Yaml;

import java.util.List;

import java.util.Map;

 

public class Global extends GlobalSettings {

 

    public void onStart(Application app) {

        Logger.info("Application has started");

        InitialData.insert(app);

    }

 

    static class InitialData {

        public static void insert(Application app) {
	        if (Ebean.find(Admin.class).findRowCount() == 0)  {   
	              Map<String, List<Object>> all =

                   (Map<String, List <Object>>)Yaml.load("initial-data.yml");
	            Ebean.save(all.get("admin"));
	    	}
	    	if (Ebean.find(Student.class).findRowCount() == 0)  {   
	              Map<String, List<Object>> all =

                   (Map<String, List <Object>>)Yaml.load("initial-data.yml");
	            Ebean.save(all.get("user"));
	    	}

        }

    }

   

    public void onStop(Application app) {

        Logger.info("Application shutdown...");

    }

}