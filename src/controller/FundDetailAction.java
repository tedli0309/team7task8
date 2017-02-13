package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import model.FundDAO;
import model.FundPriceHistoryDAO;
import model.Model;
import databean.FundBean;
import databean.FundPriceHistoryBean;
import databean.UserBean;
import formbean.SearchForm;

public class FundDetailAction extends Action {
	private FundDAO fundDAO;
	private FundPriceHistoryDAO fundPriceHistoryDAO;
	//private FormBeanFactory<SearchForm> formBeanFactory = FormBeanFactory.getInstance(SearchForm.class);
	public FundDetailAction(Model model) {
		fundDAO = model.getFundDAO();
		fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
	}
	@Override
	public String getName() {
		return "fund-detail.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		 List<String> errors = new ArrayList<String>();
	     request.setAttribute("errors", errors);
	     HttpSession session = request.getSession();
	     if(session.getAttribute("employee") != null) {
	        	errors.add("Action is not permitted");
	        	return "error.jsp";
	     }
	     try {
	    	 String symbol = request.getParameter("symbol");
	    	 FundBean fund = fundDAO.getFundBySymbol(symbol);
	    	 if(fund == null) {
	    		 errors.add("There is no fund with symbol" + symbol + ".");
	    		 return "fund-info.jsp";
	    	 }
	    	 FundPriceHistoryBean[] history = fundPriceHistoryDAO.matchById(fund.getFundId());
	    	 request.setAttribute("history", history);
	    	 request.setAttribute("fund", fund);
	    	 
        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "fund-info.jsp";
        }
        return "fund-info.jsp";
	}

}
