package io.dropwizard.hibernate;

import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWork;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ManagedSessionContext;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * <b>WARNING</b>: copypaste of dropwizard-hibernate-0.9.2's original. Just added a
 * {@link Connection#setReadOnly(boolean)} call in {@link #configureSession()}.
 * <p>
 * An aspect providing operations around a method with the {@link UnitOfWork} annotation. It opens a Hibernate session
 * and optionally a transaction.
 * <p>
 * It should be created for every invocation of the method.
 * </p>
 */
@SuppressWarnings("unqualified-field-access")
class UnitOfWorkAspect {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final Map<String, SessionFactory> sessionFactories;

	public UnitOfWorkAspect(Map<String, SessionFactory> sessionFactories) {
		this.sessionFactories = sessionFactories;
		this.readOnlySetter = new ReadOnlySetter();
	}

	// Context variables
	private UnitOfWork unitOfWork;
	private Session session;
	private SessionFactory sessionFactory;

	private final ReadOnlySetter readOnlySetter;

	public void beforeStart(UnitOfWork unitOfWork) {
		if (unitOfWork == null) {
			return;
		}
		this.unitOfWork = unitOfWork;

		sessionFactory = sessionFactories.get(unitOfWork.value());
		if (sessionFactory == null) {
			// If the user didn't specify the name of a session factory,
			// and we have only one registered, we can assume that it's the right one.
			if (unitOfWork.value().equals(HibernateBundle.DEFAULT_NAME) && sessionFactories.size() == 1) {
				sessionFactory = sessionFactories.values().iterator().next();
			} else {
				throw new IllegalArgumentException("Unregistered Hibernate bundle: '" + unitOfWork.value() + "'");
			}
		}
		session = sessionFactory.openSession();
		try {
			configureSession();
			ManagedSessionContext.bind(session);
			beginTransaction();
		} catch (Throwable th) {
			session.close();
			session = null;
			ManagedSessionContext.unbind(sessionFactory);
			throw th;
		}
	}

	public void afterEnd() {
		if (session == null) {
			return;
		}

		try {
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
			throw e;
		} finally {
			session.close();
			session = null;
			ManagedSessionContext.unbind(sessionFactory);
		}

	}

	public void onError() {
		if (session == null) {
			return;
		}

		try {
			rollbackTransaction();
		} finally {
			session.close();
			session = null;
			ManagedSessionContext.unbind(sessionFactory);
		}
	}

	private void configureSession() {
		this.session.doWork(readOnlySetter);

		session.setDefaultReadOnly(unitOfWork.readOnly());
		session.setCacheMode(unitOfWork.cacheMode());
		session.setFlushMode(unitOfWork.flushMode());
	}

	private void beginTransaction() {
		if (!unitOfWork.transactional()) {
			return;
		}
		session.beginTransaction();
	}

	private void rollbackTransaction() {
		if (!unitOfWork.transactional()) {
			return;
		}
		final Transaction txn = session.getTransaction();
		if (txn != null && txn.isActive()) {
			txn.rollback();
		}
	}

	private void commitTransaction() {
		if (!unitOfWork.transactional()) {
			return;
		}
		final Transaction txn = session.getTransaction();
		if (txn != null && txn.isActive()) {
			txn.commit();
		}
	}

	private final class ReadOnlySetter implements Work {
		@Override
		public void execute(Connection connection) throws SQLException {
			try {
				connection.setReadOnly(unitOfWork.readOnly());
			} catch (SQLException ex) {
				logger.error("Failed to set connection readonly state to " + unitOfWork.readOnly() + ".");
			}
		}
	}

}