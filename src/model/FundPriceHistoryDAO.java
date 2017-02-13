package model;

import java.util.Date;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databean.FundPriceHistoryBean;

public class FundPriceHistoryDAO extends GenericDAO<FundPriceHistoryBean> {
	public FundPriceHistoryDAO(ConnectionPool connectionPool, String tableName) throws DAOException {
		super(FundPriceHistoryBean.class, tableName, connectionPool);
	}
	public void create(FundPriceHistoryBean fph) throws RollbackException {		
		try {
			Transaction.begin();
			super.create(fph);
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}

	public FundPriceHistoryBean[] matchById(int fundId) {
		FundPriceHistoryBean[] history = null;
		try {
			history = match(MatchArg.equals("fundId", fundId));
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return history;
	}
	public FundPriceHistoryBean getFundPriceHistory(int fundId, String pricedate) throws RollbackException {
		try	{
			Transaction.begin();
			FundPriceHistoryBean[] res =  match(MatchArg.and(MatchArg.equals("fundId", fundId), MatchArg.equals("pricedate", pricedate)));
			if (res.length == 0)  return null;
			Transaction.commit();
			return res[0];
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}
}
