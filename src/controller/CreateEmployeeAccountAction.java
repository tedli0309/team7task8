package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.DuplicateKeyException;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.EmployeeBean;
import databean.UserBean;
import formbean.CustomerRegisterForm;
import formbean.EmployeeRegisterForm;
import model.EmployeeDAO;
import model.Model;
import model.UserDAO;

public class CreateEmployeeAccountAction  extends Action {
	private FormBeanFactory<EmployeeRegisterForm> formBeanFactory = FormBeanFactory.getInstance(EmployeeRegisterForm.class);
	private EmployeeDAO employeeDAO;
	
	public CreateEmployeeAccountAction(Model model) {
		employeeDAO = model.getEmployeeDAO();
    }
    ///this is for the hashmap in the action.java
    public String getName() {
        return "createEmployeeAccount.do";
    }
    public String perform(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);
        if(session.getAttribute("customer") != null) {
        	errors.add("Action is not permitted");
        	return "error.jsp";
        }
        try {
        	EmployeeRegisterForm form = formBeanFactory.create(request);
            request.setAttribute("form", form);
            request.setAttribute("employeeList", employeeDAO.getEmployees());   
            
            if (!form.isPresent()) {
                return "createEmployeeAccount.jsp";
            }
           
            // Any validation errors?
            errors.addAll(form.getValidationErrors());
           
            if (errors.size() != 0) {
                return "createEmployeeAccount.jsp";
            }

         
            EmployeeBean newUser = new EmployeeBean();

            newUser.setUserName(form.getUserName());
            newUser.setFirstName(form.getFirstName());
            newUser.setLastName(form.getLastName());
            newUser.setHashedPassword(Integer.parseInt(form.getPassword()));
            try {
            	EmployeeBean[] employee = employeeDAO.match(MatchArg.contains("userName",form.getUserName() ));
            	if(employee.length!=0) {
            		errors.add("UserName unavailable");
            		return "createEmployeeAccount.jsp";
            	}
            	employeeDAO.create(newUser);
            	request.setAttribute("message","Account created successfully!");
                return ("createEmployeeAccount.jsp");
            } catch (DuplicateKeyException e) {
                errors.add("A user with this name already exists");
                return "createEmployeeAccount.jsp";
            }
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "createEmployeeAccount.jsp";
        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "createEmployeeAccount.jsp";
        }
    }
}
