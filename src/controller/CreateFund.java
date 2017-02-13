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

import databean.FundBean;
import databean.UserBean;
import formbean.CreateFundForm;
import formbean.CustomerRegisterForm;
import model.FundDAO;
import model.Model;
import model.UserDAO;

public class CreateFund extends Action {
	private FormBeanFactory<CreateFundForm> formBeanFactory = FormBeanFactory.getInstance(CreateFundForm.class);
	
		
		private FundDAO fundDAO;
		
	    public CreateFund(Model model) {
	        //userDAO = model.getUserDAO();
	        fundDAO = model.getFundDAO();
	    }
	    ///this is for the hashmap in the action.java
	    public String getName() {
	        return "createFund.do";
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
	        	CreateFundForm form = formBeanFactory.create(request);
	               
	            if (!form.isPresent()) {
	                return "createFund.jsp";
	            }
	           
	            // Any validation errors?
	            errors.addAll(form.getValidationErrors());
	           
	            if (errors.size() != 0) {
	                return "createFund.jsp";
	            }
	            FundBean fundBean = new FundBean(form.getFundName(),form.getFundSymbol(),0);
	            
	            if (fundDAO.getFund(fundBean.getName()) != null) {
	            	errors.add("fund name already exists!");
	            }
	            if (fundDAO.getFundBySymbol(fundBean.getSymbol()) != null) {
	            	errors.add("fund Symbol already exists!");
	            }
	            if (errors.size() != 0) {
		            return "createFund.jsp";
		        }
	            fundDAO.create(fundBean);
	            request.setAttribute("message", "the fund is proudly created by little luna!");
	            return ("createFund.jsp");	             
	        } catch (RollbackException e) {
	            errors.add(e.getMessage());
	            return "createFund.jsp";
	        } catch (FormBeanException e) {
	            errors.add(e.getMessage());
	            return "createFund.jsp";
	        }
	    }
}
