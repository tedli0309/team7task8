package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.FundDAO;
import model.FundPriceHistoryDAO;
import model.Model;
import model.PositionDAO;
import model.TransactionDAO;
import model.UserDAO;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.FundBean;
import databean.FundPriceHistoryBean;
import databean.PositionBean;
import databean.TransactionBean;
import databean.UserBean;
import formbean.TransitionDayForm;

public class TransitionDayAction extends Action {
	private FormBeanFactory<TransitionDayForm> formBeanFactory = FormBeanFactory.getInstance(TransitionDayForm.class);

	private FundDAO fundDAO;
	private FundPriceHistoryDAO funPriceHistoryDAO;
	private TransactionDAO transactionDAO;
	private UserDAO userDAO;
	private PositionDAO positionDAO;
	public TransitionDayAction(Model model){
		fundDAO = model.getFundDAO();
		funPriceHistoryDAO = model.getFundPriceHistoryDAO();
		transactionDAO = model.getTransactionDAO();
		userDAO = model.getUserDAO();
		positionDAO = model.getPositionDAO();
	}
	@Override
	public String getName() {
		return "transitionDay.do";
	}

	@Override
	public synchronized String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
	    request.setAttribute("errors", errors);
		HttpSession session = request.getSession();
		
		if(session.getAttribute("customer") != null) {
	       	errors.add("Action is not permitted");
	       	return "error.jsp";
	    }
		
	    List<String> message = new ArrayList<String>();
	    request.setAttribute("message", message);
		try {
			//validation
			TransitionDayForm form = formBeanFactory.create(request);
			if(!form.isPresent()) {
				request.setAttribute("fundList", fundDAO.match());
				 return "transitionDay.jsp";
			}

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			//System.out.println(request.getParameter("date"));
			Date date = df.parse(request.getParameter("date"));
			//System.out.println(df.format(date));

			FundBean[] funds = fundDAO.getFunds();
			Map<Integer, Double> map = new HashMap<Integer, Double>(); 
			for(FundBean fund : funds) {
				try{
					int id = fund.getFundId();
					FundPriceHistoryBean[] fph = funPriceHistoryDAO.match(MatchArg.equals("fundId", id));
					if(fph.length != 0){
						Date last = df.parse(fph[fph.length-1].getPricedate()); 
						if(last.getTime() >= (date.getTime())) {
							errors.add("Invalid date");
							break;
						}
					} else {
						
					}
					double price = Double.parseDouble(request.getParameter(String.valueOf(id)));
					if(price <= 0) {
						throw new IllegalArgumentException();
					}
					map.put(id, price);
					fund.setPrice(price);
				} catch(NumberFormatException e){
					errors.add("The price of " + fund.getName() + " is not a number");
					fund.setPrice(-1.0);
				} catch(IllegalArgumentException e){
					errors.add("The price of " + fund.getName() + " is less than zero");
					fund.setPrice(-1.0);
				} catch(NullPointerException e) {
					errors.add("New funds are added");
					fund.setPrice(-1.0);
				}
			}
			if(errors.size() > 0){
				request.setAttribute("fundList", funds);
				return "transitionDay.jsp";
			}
			//updating funds
			//FundBean[] funds = fundDAO.getFunds();
			for(FundBean fund : funds) {
				int id = fund.getFundId();
				double price = map.get(id);
				fund.setPrice(price);
				fundDAO.update(fund);
				FundPriceHistoryBean history = new FundPriceHistoryBean(fund.getFundId(),df.format(date),price);
				funPriceHistoryDAO.create(history);
			}
			
			//execute transactions
			TransactionBean[] transactions = transactionDAO.getTransactions();
			for(TransactionBean transaction : transactions) {
				int customerId = transaction.getUserId();
				int fundId = transaction.getFundId();
				double share = transaction.getShares();
				//update share
				PositionBean position = positionDAO.getPosition(customerId, fundId);
				position.setShares(position.getShares() - share);
				if (position.getShares() == 0 ){
					positionDAO.delete(fundId, customerId);
				}else{
					positionDAO.update(position);
				}				
				//update balance
				UserBean customer = userDAO.read(customerId);
				double price1 = fundDAO.read(fundId).getPrice();
				customer.setCash(customer.getCash() + price1 * share);
				userDAO.update(customer);
				//update transaction
				transaction.setAmount(share * price1);
				transactionDAO.update(transaction);
			}
			//buy request deposit
			int[] customerIds = transactionDAO.getCustomerUniqueIds();
			for(int customerId : customerIds){
				UserBean customer = userDAO.read(customerId);
				double newBalance = transactionDAO.currentBalace(customerId, customer.getCash());
				customer.setCash(newBalance);
				TransactionBean[] transactions2 =  transactionDAO.match(MatchArg.and(MatchArg.equals("userId", customerId),MatchArg.equals("transactionType", "buy"),MatchArg.equals("executeDate", null)));
				for(TransactionBean transaction: transactions2) {
					if(transaction.getUserId() == customerId) {
						int fundId = transaction.getFundId();
						//System.out.println(fundId);
						FundBean fb = fundDAO.read(fundId);
						double price1 =fb.getPrice();
						double share = transaction.getAmount()/price1;
						transaction.setShares(share);
						transactionDAO.update(transaction);
						PositionBean position = positionDAO.getPosition(customerId, fundId);
						//System.out.println(position.getUserId());
						if(position.getShares() == 0) {
							positionDAO.create(position);
						}
						//System.out.println(customerId + " " + fundId + "in else");
						position.setShares(position.getShares() + share);
						positionDAO.update(position);
					}
					
				}
				userDAO.update(customer);
			}
			
			//updating date of transactions
			for(TransactionBean transaction: transactionDAO.getPendingTransactions()) {
				transaction.setExecuteDate(df.format(date));
				transactionDAO.update(transaction);
			}
			FundPriceHistoryBean[] fph = funPriceHistoryDAO.match();
			String last = fph[fph.length - 1].getPricedate();
			request.setAttribute("lastDay", last);
			
			message.add("Success!");
			request.setAttribute("fundList", fundDAO.match());
		} catch (RollbackException e) {
			errors.add(e.toString());
			return "transitionDay.jsp";
		} catch (FormBeanException e) {
			errors.add(e.toString());
			return "transitionDay.jsp";
		} catch (ParseException e) {
			errors.add("Date format not proper");
			return "transitionDay.jsp";
		}
		return "transitionDay.jsp";
	}

}
