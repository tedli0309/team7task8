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

import databean.UserBean;
import formbean.CustomerRegisterForm;
import model.Model;
import model.UserDAO;

public class CreateCustomerAccountAction extends Action {
	private FormBeanFactory<CustomerRegisterForm> formBeanFactory = FormBeanFactory.getInstance(CustomerRegisterForm.class);
	
	 private UserDAO userDAO;

	    public CreateCustomerAccountAction(Model model) {
	        userDAO = model.getUserDAO();
	    }
	    ///this is for the hashmap in the action.java
	    public String getName() {
	        return "createCustomerAccount.do";
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
	        	CustomerRegisterForm form = formBeanFactory.create(request);
	            request.setAttribute("form", form);
	            request.setAttribute("userList", userDAO.getUsers());   
	            
	            if (!form.isPresent()) {
	                return "createCustomerAccount.jsp";
	            }
	           
	            // Any validation errors?
	            errors.addAll(form.getValidationErrors());
	           
	            if (errors.size() != 0) {
	                return "createCustomerAccount.jsp";
	            }

	         
	            UserBean newUser = new UserBean();

	            newUser.setUserName(form.getUserName());
	            newUser.setFirstName(form.getFirstName());
	            newUser.setLastName(form.getLastName());
	            newUser.setAddr1(form.getAddr1());
	            newUser.setAddr2(form.getAddr2());
	            newUser.setCity(form.getCity());
	            newUser.setState(form.getState());
	            newUser.setZip(form.getZip());
	            newUser.setHashedPassword(Integer.parseInt(form.getPassword()));
	            try {
	            	UserBean[] users = userDAO.match(MatchArg.contains("userName",form.getUserName() ));
	            	if(users.length!=0) {
	            		errors.add("UserName unavailable");
	            		return "createCustomerAccount.jsp";
	            	}
	                userDAO.create(newUser);
	               // List<String> success = new ArrayList<>();
	                request.setAttribute("message","Account created successfully!");
	                return ("createCustomerAccount.jsp");
	            } catch (DuplicateKeyException e) {
	                errors.add("A user with this name already exists");
	                return "createCustomerAccount.jsp";
	            }
	        } catch (RollbackException e) {
	            errors.add(e.getMessage());
	            return "createCustomerAccount.jsp";
	        } catch (FormBeanException e) {
	            errors.add(e.getMessage());
	            return "createCustomerAccount.jsp";
	        }
	    }
}
