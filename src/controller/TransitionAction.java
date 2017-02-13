package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import databean.FundPriceHistoryBean;
import model.FundDAO;
import model.FundPriceHistoryDAO;
import model.Model;

public class TransitionAction extends Action {
	private FundDAO fundDAO;
	private FundPriceHistoryDAO fundPriceHistoryDAO;
	public TransitionAction(Model model) {
		fundDAO = model.getFundDAO();
		fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
	}
	@Override
	public String getName() {
		return "transition.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
		HttpSession session = request.getSession();
	    if(session.getAttribute("customer") != null) {
	    	errors.add("Action is not permitted");
	    	return "error.jsp";
	    }
		try {
			request.setAttribute("fundList", fundDAO.match());
			FundPriceHistoryBean[] fph = fundPriceHistoryDAO.match();
			String last= "";
			if(fph.length > 0)
				last = fph[fph.length - 1].getPricedate();
			request.setAttribute("lastDay", last);
			
		} catch (RollbackException e) {
			errors.add(e.getMessage());
            return "register.jsp";
		}
		return "transitionDay.jsp";
	}

}
