package com.demo.dao.impl;

import java.util.ArrayList;

import java.util.List;

import com.demo.dto.TransactionDTO;
import com.demo.dao.TransactionDAO;
import com.demo.util.DBUtil;


import java.math.BigDecimal;
import java.sql.*;

public class TransactionDAOImpl implements TransactionDAO {
	private final String transTable = "THANG_ATM_SYS_TRANSACTION";

	private TransactionDTO mapRow(ResultSet rs) throws SQLException {
		TransactionDTO trans = new TransactionDTO();
		trans.setTransId(rs.getLong("TRANS_ID"));
		trans.setAtmId(rs.getLong("ATM_ID"));
		trans.setCardId(rs.getLong("CARD_ID"));
		trans.setTransType(rs.getString("TRANS_TYPE"));
		trans.setTransAmount(rs.getBigDecimal("TRANS_AMOUNT"));
		trans.setTransTime(rs.getTimestamp("TRANS_TIME"));

		Long ref = null;
		    Object refObj = rs.getObject("REF_TRANS_ID");
		    if (refObj != null) {
		        ref = ((Number) refObj).longValue();
		    }
		    trans.setRefTransId(ref);
		
		return trans;
	}

	@Override
	public TransactionDTO findById(Long transId) {
		String sql = String.format("SELECT * FROM %s WHERE TRANS_ID = ?", transTable);

		try (Connection con = DBUtil.getConnection()) {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setLong(1, transId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRow(rs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * @Override public TransactionDTO findByCardId(Long cardId) { String sql =
	 * String.format("SELECT * FROM %s WHERE CARD_ID = ?", transTable);
	 * 
	 * try (Connection con = DBUtil.getConnection()) { PreparedStatement ps =
	 * con.prepareStatement(sql);
	 * 
	 * ps.setLong(1, cardId); try (ResultSet rs = ps.executeQuery()) { if
	 * (rs.next()) { return mapRow(rs); } } } catch (Exception e) {
	 * e.printStackTrace(); } return null; }
	 */

	/*
	 * @Override public TransactionDTO findByAtmId(Long atmId) { String sql =
	 * String.format("SELECT * FROM %s WHERE ATM_ID = ?", transTable);
	 * 
	 * try (Connection con = DBUtil.getConnection()) { PreparedStatement ps =
	 * con.prepareStatement(sql);
	 * 
	 * ps.setLong(1, atmId); try (ResultSet rs = ps.executeQuery()) { if (rs.next())
	 * { return mapRow(rs); } } } catch (Exception e) { e.printStackTrace(); }
	 * return null; }
	 */

	@Override
	public boolean save(TransactionDTO trans) {
		String sql = String.format("INSERT INTO %s "
					+ "(CARD_ID, TRANS_TYPE, TRANS_AMOUNT, ATM_ID) "
					+ "VALUES (?, ?, ?, ?)", transTable);

		try (Connection con = DBUtil.getConnection()) {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, trans.getCardId());
			ps.setString(2, trans.getTransType());
			ps.setBigDecimal(3, trans.getTransAmount())	;
			ps.setBigDecimal(4, BigDecimal.valueOf(4l));
					
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
		}

	@Override
	public boolean update(TransactionDTO trans) {
		String sql;
		if(trans.getRefTransId() != null) {
			sql = String.format("UPDATE %s SET"
					+ "(CARD_ID, TRANS_TYPE, TRANS_AMOUNT, ATM_ID, REF_TRANS_ID)"
					+ " VALUES (?, ?, ?, ?, ?)", transTable);
		} else {
			sql = String.format("UPDATE INTO %s "
					+ "(CARD_ID, TRANS_TYPE, TRANS_AMOUNT, ATM_ID) "
					+ "VALUES (?, ?, ?, ?)", transTable);
		}
		
		try (Connection con = DBUtil.getConnection()) {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setLong(1, trans.getCardId());
			ps.setString(2, trans.getTransType());
			ps.setBigDecimal(3, trans.getTransAmount())	;
			ps.setLong(4, Long.valueOf(4));
			if(trans.getRefTransId() != null) {
				ps.setLong(5, trans.getRefTransId());
			}
			
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		}

	@Override
	public List<TransactionDTO> findLast5CardTransByCardId(Long cardId) {
		String sql = String.format("SELECT * FROM "
				+ "(SELECT * FROM %s WHERE CARD_ID = ? "
				+ "ORDER BY TRANS_TIME DESC) WHERE ROWNUM <= 5", transTable);
		
		try (Connection con = DBUtil.getConnection()) {
			PreparedStatement ps = con.prepareStatement(sql);
			List<TransactionDTO> trans = new ArrayList<TransactionDTO>();
			
			ps.setLong(1, cardId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					 trans.add(mapRow(rs));
				}
			}
			return trans;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<TransactionDTO> findLast5UserTransByUserId(Long userId) {
		String sql = String.format("SELECT * FROM "
				+ "(SELECT * FROM %s WHERE CARD_ID IN "
				+ "(SELECT CARD_ID FROM THANG_ATM_SYS_CARD WHERE USER_ID = ?) "
				+ "ORDER BY TRANS_TIME DESC) "
				+ "WHERE ROWNUM <= 5", transTable);
		
		try (Connection con = DBUtil.getConnection()) {
			PreparedStatement ps = con.prepareStatement(sql);
			List<TransactionDTO> trans = new ArrayList<TransactionDTO>();
			
			ps.setLong(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				while(rs.next()) {
					 trans.add(mapRow(rs));
				}
			}
			return trans;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean saveTransfer(Connection con, TransactionDTO withdraw, TransactionDTO deposit) throws SQLException {
		String sql = String.format("INSERT INTO %s "
				+ "(CARD_ID, TRANS_TYPE, TRANS_AMOUNT, ATM_ID, REF_TRANS_ID) "
				+ "VALUES (?, ?, ?, ?, ?)", transTable);	
		
			try (PreparedStatement ps = con.prepareStatement(sql)){

				ps.setLong(1, withdraw.getCardId());
				ps.setString(2, withdraw.getTransType());
				ps.setBigDecimal(3, withdraw.getTransAmount())	;
				ps.setLong(4, Long.valueOf(4));
				ps.setLong(5, withdraw.getRefTransId());
				
				boolean withdrawTrans = ps.executeUpdate() > 0;
				if(!withdrawTrans) {
					throw new SQLException("None of row is inserted");
				}
			}
			
			try (PreparedStatement ps = con.prepareStatement(sql)){
				
				ps.setLong(1, deposit.getCardId());
				ps.setString(2, deposit.getTransType());
				ps.setBigDecimal(3, deposit.getTransAmount())	;
				ps.setLong(4, Long.valueOf(4));
				ps.setLong(5, deposit.getRefTransId());
				
				boolean depositTrans = ps.executeUpdate() > 0;
				if(!depositTrans) {
					throw new SQLException("None of row is inserted");
				}
			} 			
			return true;
	}

	@Override
	public boolean save(Connection con, TransactionDTO trans) throws SQLException {
		String sql = String.format("INSERT INTO %s "
				+ "(CARD_ID, TRANS_TYPE, TRANS_AMOUNT, ATM_ID) "
				+ "VALUES (?, ?, ?, ?)", transTable);	
		
			try (PreparedStatement ps = con.prepareStatement(sql)){

				ps.setLong(1, trans.getCardId());
				ps.setString(2, trans.getTransType());
				ps.setBigDecimal(3, trans.getTransAmount())	;
				ps.setLong(4, Long.valueOf(4));
				
				boolean withdrawTrans = ps.executeUpdate() > 0;
				if(!withdrawTrans) {
					return false;
				}
			}
		return true;
	}

	@Override
	public List<TransactionDTO> findCardTransInRange(Timestamp from, Timestamp to, Long cardId) {
		String sql = """
				  SELECT *
				  FROM THANG_ATM_SYS_TRANSACTION
				  WHERE CARD_ID = ?
				    AND TRANS_TIME > ?
				    AND TRANS_TIME <=  ?
				  ORDER BY TRANS_TIME DESC
				""";

				try (Connection con = DBUtil.getConnection()) {
					PreparedStatement ps = con.prepareStatement(sql);
					List<TransactionDTO> trans = new ArrayList<TransactionDTO>();
					ps.setLong(1, cardId);
					ps.setTimestamp(2, from);
					ps.setTimestamp(3, to);

					try (ResultSet rs = ps.executeQuery()) {
						while(rs.next()) {
							trans.add(mapRow(rs));
						}
					}
					return trans;
				} catch (Exception e) {
					e.printStackTrace();
				}
		return null;
	}

	@Override
	public List<TransactionDTO> findUserTransInRange(Timestamp from, Timestamp to, Long userId) {
		String sql = String.format("SELECT * FROM %s "
				+ "WHERE CARD_ID IN "
				+ "(SELECT CARD_ID FROM THANG_ATM_SYS_CARD WHERE USER_ID = ?) "
				+ "AND TRANS_TIME > ? "
				+ "AND TRANS_TIME <= ?"
				+ "ORDER BY TRANS_TIME DESC", transTable);
		
				try (Connection con = DBUtil.getConnection()) {
					PreparedStatement ps = con.prepareStatement(sql);
					List<TransactionDTO> trans = new ArrayList<TransactionDTO>();
					ps.setLong(1, userId);
					ps.setTimestamp(2, from);
					ps.setTimestamp(3, to);

					try (ResultSet rs = ps.executeQuery()) {
						while(rs.next()) {
							trans.add(mapRow(rs));
						}
					}
					return trans;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
	}
}
	