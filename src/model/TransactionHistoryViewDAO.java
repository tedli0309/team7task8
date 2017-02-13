package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericViewDAO;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databean.TransactionHistoryViewBean;;

public class TransactionHistoryViewDAO extends GenericViewDAO<TransactionHistoryViewBean>{
	public TransactionHistoryViewDAO(ConnectionPool connectionPool) throws DAOException {
		super(TransactionHistoryViewBean.class, connectionPool);
	}
	public TransactionHistoryViewBean[] getTransactionHistory() throws RollbackException{
		try{
			Transaction.begin();
			String sql = "select task7_fund.name as name, task7_transaction.executeDate as executeDate, "
			         + "task7_transaction.shares as shares,  task7_transaction.transactionType as transactionType, "
			         + "task7_transaction.amount as amount, task7_transaction.userId as userId "
			         + "from task7_fund RIGHT JOIN task7_transaction "
			         + "on task7_fund.fundId = task7_transaction.fundId";
			TransactionHistoryViewBean[] res = executeQuery(sql);
			Transaction.commit();
			return res;
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}
}
