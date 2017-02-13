package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.FundBean;
import formbean.SearchForm;
import model.FundDAO;
import model.Model;


public class ResearchFund extends Action{
	private FundDAO  fundDAO;
	//private FundPriceHistoryDAO fundPriceHistoryDAO;
	private FormBeanFactory<SearchForm> formBeanFactory = FormBeanFactory.getInstance(SearchForm.class);
	public ResearchFund(Model model) {	 
		fundDAO = model.getFundDAO();
	}

	public String getName() { return "researchFunds.do"; }
    
    public String perform(HttpServletRequest request) {
		 List<String> errors = new ArrayList<String>();
	     request.setAttribute("errors", errors);
	     HttpSession session = request.getSession();
	     if(session.getAttribute("employee") != null) {
	        	errors.add("Action is not permitted");
	        	return "error.jsp";
	     }
	     try {
	    	 SearchForm form = formBeanFactory.create(request);
	    	 if(!form.isPresent()) {	
	    		 FundBean[] fundList = fundDAO.getFunds();
		    	 request.setAttribute("fundList", fundList);
	    		 return "research-fund.jsp";
	    	 }
	    	 errors.addAll(form.getValidationErrors());
				if (errors.size() != 0) {
					return "reset-pwd.jsp";
				}
	    	 FundBean[] fundList = new FundBean[1];
	    	 fundList[0] = fundDAO.getFundBySymbol(form.getSymbol());
	    	 if(fundList[0] == null) {
	    		 errors.add("There is no fund with this symbol");
	    		 return "research-fund.jsp";
	    	 }
	    	 request.setAttribute("fundList", fundList);
        } catch (RollbackException e) {
        	
        	errors.add(e.getMessage());
        	return "research-fund.jsp";
        	
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "research-fund.jsp";
		}
        return "research-fund.jsp";
    }
}
