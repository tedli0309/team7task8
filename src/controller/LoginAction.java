
package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.DuplicateKeyException;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.EmployeeBean;
import databean.UserBean;
import formbean.LoginForm;
import model.EmployeeDAO;
import model.Model;
import model.UserDAO;

public class LoginAction extends Action {
    private FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory.getInstance(LoginForm.class);

    private UserDAO userDAO;
    private EmployeeDAO employeeDAO;
    public LoginAction(Model model) {
        userDAO = model.getUserDAO();
        employeeDAO = model.getEmployeeDAO();
    }

    public String getName() {      return "login.do";  }

    public String perform(HttpServletRequest request) {    
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);
        
        try {

            LoginForm form = formBeanFactory.create(request);            
            request.setAttribute("form", form);

            if (!form.isPresent()) {
                return "login.jsp";
            }
            errors.addAll(form.getValidationErros());
            if (errors.size() != 0) {
                return "login.jsp";
            }
 
            if (form.getAction().equals("Login")) {
	            // Look up the user
	            UserBean user = userDAO.read(form.getUserName());      
	            if (user == null) {
	                errors.add("User not found");
	                return "login.jsp";
	            }	            
	            // Check the password
	            if (user.getHashedPassword() != Integer.parseInt(form.getPassword())) {
	                errors.add("Incorrect password");
	                //userDAO.computeDigest(bean)
	                return "login.jsp";
	            }	

	            request.getSession().setAttribute("customer", user);
	            return "account.do";
	            
            } else if (form.getAction().equals("Login as Employee")) {
            	// Look up the user
	            EmployeeBean employee = employeeDAO.read(form.getUserName());      
	            if (employee == null) {
	                errors.add("User not found");
	                return "login.jsp";
	            }
	            // Check the password
	            if (employee.getHashedPassword() != Integer.parseInt(form.getPassword())) {
	                errors.add("Incorrect password");
	                return "login.jsp";
	            }
	            
	            request.getSession().setAttribute("employee", employee);
            	return "employeeAccount.do";
            } else {
            	errors.add("Invalid Button!");
                return "login.jsp";
            }
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "login.jsp";
        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "login.jsp";
        }
		 
    }
}
