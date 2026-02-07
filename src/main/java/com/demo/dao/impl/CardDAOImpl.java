
package com.demo.dao.impl;

import java.util.ArrayList;

import java.util.List;

import com.demo.dao.CardDAO;
import com.demo.dto.CardDTO;
import com.demo.util.DBUtil;

import java.sql.*;

public class CardDAOImpl implements CardDAO {
	private final String cardTable = "THANG_ATM_SYS_CARD";

	private CardDTO mapRow(ResultSet rs) throws SQLException {
		CardDTO card = new CardDTO();
		card.setCardId(rs.getLong("CARD_ID"));
		card.setUserId(rs.getLong("USER_ID"));
		card.setCardNumber(rs.getString("CARD_NUMBER"));
		card.setPinCode(rs.getString("PIN_CODE"));
		card.setCardStatus(rs.getString("CARD_STATUS"));
		card.setCreatedDate(rs.getDate("CREATED_DATE"));
		card.setExpiredDate(rs.getDate("EXPIRED_DATE"));
		card.setBalance(rs.getBigDecimal("BALANCE"));
		card.setCardType(rs.getString("CARD_TYPE"));
		return card;
	}

	@Override
	public CardDTO findByCardNumber(String cardNumber) {
		String sql = String.format("SELECT * FROM %s WHERE CARD_NUMBER = ?", cardTable);

		try (Connection con = DBUtil.getConnection()) {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, cardNumber);
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
	public CardDTO findById(Long cardId) {
		String sql = String.format("SELECT * FROM %s WHERE CARD_ID = ?", cardTable);

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
	public boolean save(CardDTO card) {
		String sql = String.format("""
				    INSERT INTO %s
				    (USER_ID, CARD_NUMBER, PIN_CODE, CARD_STATUS, EXPIRED_DATE, BALANCE, CARD_TYPE)
				    VALUES (?, ?, ?, ?, ?, ?, ?)
				""", cardTable);

		try (Connection con = DBUtil.getConnection()) {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setLong(1, card.getUserId());
			ps.setString(2, card.getCardNumber());
			ps.setString(3, card.getPinCode());
			ps.setString(4, card.getCardStatus());
			ps.setDate(5, card.getExpiredDate());
			ps.setBigDecimal(6, card.getBalance());
			ps.setString(7, card.getCardType());

			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(CardDTO card) {

	String sql = String.format(
	        "UPDATE %s SET PIN_CODE = ?, CARD_STATUS = ?, EXPIRED_DATE = ?, CARD_TYPE = ?, BALANCE = ? " +
	        "WHERE CARD_ID = ?", cardTable);

		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, card.getPinCode());
			ps.setString(2, card.getCardStatus());
			ps.setDate(3, card.getExpiredDate());
			ps.setString(4, card.getCardType());
			ps.setBigDecimal(5, card.getBalance());
			ps.setLong(6, card.getCardId());

			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<CardDTO> findAll() {
		String sql = String.format("SELECT * FROM %s", cardTable);
		List<CardDTO> list = new ArrayList<CardDTO>();

		try (Connection con = DBUtil.getConnection()) {
			PreparedStatement ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(mapRow(rs));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean update(Connection con, CardDTO card) throws SQLException {
		String sql = String.format(
		        "UPDATE %s SET PIN_CODE = ?, CARD_STATUS = ?, EXPIRED_DATE = ?, CARD_TYPE = ?, BALANCE = ? " +
		        "WHERE CARD_ID = ?", cardTable);

			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, card.getPinCode());
			ps.setString(2, card.getCardStatus());
			ps.setDate(3, card.getExpiredDate());
			ps.setString(4, card.getCardType());
			ps.setBigDecimal(5, card.getBalance());
			ps.setLong(6, card.getCardId());

			return ps.executeUpdate() > 0;
	}

	

}
