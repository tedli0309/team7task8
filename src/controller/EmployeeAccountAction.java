package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.EmployeeBean;
import databean.FundBean;
import databean.UserBean;
import formbean.SearchForm;
import formbean.SearchUserForm;
import model.EmployeeDAO;
 
import model.Model;
import model.UserDAO;

public class EmployeeAccountAction extends Action{
	private UserDAO userDAO;
	private EmployeeDAO employeeDAO;
	private FormBeanFactory<SearchUserForm> formBeanFactory = FormBeanFactory.getInstance(SearchUserForm.class);
	public EmployeeAccountAction(Model model) {	 
		userDAO = model.getUserDAO();
	 
		employeeDAO = model.getEmployeeDAO();
	}

	public String getName() { return "employeeAccount.do"; }
    
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        HttpSession session = request.getSession();
        if(session.getAttribute("customer") != null) {
        	errors.add("Action is not permitted");
        	return "error.jsp";
        }
        try {
        	EmployeeBean employee = (EmployeeBean)session.getAttribute("employee");
        	SearchUserForm form = formBeanFactory.create(request);
        	if(!form.isPresent()) {	
        		System.out.println("form is not present");
        		request.setAttribute("customerList", userDAO.getUsers());   
           		request.setAttribute("employee", employee);
        		return "employeeAccount.jsp";
	    	 }
        	errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				request.setAttribute("customerList", userDAO.getUsers());   
           		request.setAttribute("employee", employee);
				return "employeeAccount.jsp";
			}
        	UserBean user = userDAO.read(form.getCustomerName());
        	if (user == null) {
        		 errors.add("There is no user named" + form.getCustomerName());
        		 request.setAttribute("customerList", userDAO.getUsers());   
            	 request.setAttribute("employee", employee);
        		 return "employeeAccount.jsp";
        	}
        	UserBean[] users = new UserBean[1];
        	users[0] = user;
       		request.setAttribute("customerList", users);   
       		request.setAttribute("employee", employee);
       		return "employeeAccount.jsp";
        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "employeeAccount.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "employeeAccount.jsp";
		}
    }
}
