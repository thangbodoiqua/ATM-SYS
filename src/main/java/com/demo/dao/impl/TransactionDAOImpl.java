package com.demo.dao.impl;

import java.util.List;

import com.demo.dto.TransactionDTO;
import com.demo.dao.TransactionDAO;
import com.demo.util.DBUtil;

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
		trans.setRefTransId(rs.getLong("REF_TRANS_ID"));

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

	@Override
	public TransactionDTO findByCardId(Long cardId) {
		String sql = String.format("SELECT * FROM %s WHERE CARD_ID = ?", transTable);

		try (Connection con = DBUtil.getConnection()) {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setLong(1, cardId);
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

	@Override
	public TransactionDTO findByAtmId(Long atmId) {
		String sql = String.format("SELECT * FROM %s WHERE ATM_ID = ?", transTable);

		try (Connection con = DBUtil.getConnection()) {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setLong(1, atmId);
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

	@Override
	public List<TransactionDTO> findIn(Date from, Date to, TransactionDTO trans) {

		return null;
	}
}
