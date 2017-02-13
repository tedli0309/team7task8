package formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBean;

public class LoginForm extends FormBean{
	private String userName;
	private String password;
	private String action;
	
	public void setUserName(String userName) {
		this.userName = trimAndConvert(userName,"<>\"");
	}
	public void setPassword(String password) {
		this.password = password.trim();
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getUserName() {
		return userName;
	}
	public String getAction() {
		return action;
	}
	public String getPassword() {
		return password;
	}
	public boolean isPresent() {
		return action != null;
	}
	
	public List<String> getValidationErros() {
		List<String> errors = new ArrayList<String>();
		if (userName == null || userName.length()== 0) {
			errors.add("UserName is required.");
		}
		if (password == null || password.length() == 0) {
			errors.add("Password is required.");
		}
		if (action == null) {
			errors.add("Button is required.");
		}
		
		if(errors.size() > 0) return errors; // no need to further detect the errors anymore.
		
		if (!(action.equals("Login") ||action.equals("Login as Employee"))) {
			errors.add("Invalid button");
		}
		if (userName.matches(".*[<>\"].*"))
			errors.add("UserName may not contain angle brackets or quotes");
		
		return errors;
    }
}
