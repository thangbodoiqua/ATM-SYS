package com.demo.dao.impl;

import java.util.ArrayList;

import java.util.List;

import com.demo.dao.AtmDAO;
import com.demo.dto.AtmDTO;
import com.demo.util.DBUtil;

import java.sql.*;

public class AtmDAOImpl implements AtmDAO {
	private final String atmTable = "THANG_ATM_SYS_ATM";

	private AtmDTO mapRow(ResultSet rs) throws SQLException {
		AtmDTO atm = new AtmDTO();
		atm.setAtmId(rs.getLong("ATM_ID"));
		atm.setLocation(rs.getString("LOCATION"));
		atm.setAtmStatus(rs.getString("ATM_STATUS"));
		atm.setCashAvailable(rs.getBigDecimal("CASH_AVAILABLE"));

		return atm;
	}

	@Override
	public AtmDTO findById(Long atmId) {
		String sql = String.format("SELECT * FROM %s WHERE ATM_ID = ?", atmTable);

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
	public boolean create(AtmDTO atm) {
		String sql = String.format("""
				    INSERT INTO %s
				    (LOCATION, ATM_STATUS, CASH_AVAILABLE)
				    VALUES (?, ?, ?)
				""", atmTable);

		try (Connection con = DBUtil.getConnection()) {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, atm.getLocation());
			ps.setString(2, atm.getAtmStatus());
			ps.setBigDecimal(3, atm.getCashAvailable());

			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(AtmDTO atm) {
		String sql = String.format(
				"UPDATE %s" + " SET LOCATION= ?," + " ATM_STATUS = ?," + " CASH_AVAILABLE = ?," + " WHERE ATM_ID = ?",
				atmTable);
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, atm.getLocation());
			ps.setString(2, atm.getAtmStatus());
			ps.setBigDecimal(3, atm.getCashAvailable());

			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public List<AtmDTO> findAll() {
		String sql = String.format("SELECT * FROM %s", atmTable);
		List<AtmDTO> list = new ArrayList<AtmDTO>();

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

}
