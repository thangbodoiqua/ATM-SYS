package com.demo.dao.impl;

import java.util.List;

import java.util.ArrayList;
import com.demo.dao.UserDAO;
import com.demo.dto.UserDTO;
import com.demo.util.DBUtil;

import java.sql.*;

public class UserDAOImpl implements UserDAO {
	String userTable = "THANG_ATM_SYS_USER";

	private UserDTO mapRow(ResultSet rs) throws SQLException {
		UserDTO user = new UserDTO();
		user.setUserId(rs.getLong("USER_ID"));
		user.setUsername(rs.getString("USER_NAME"));
		user.setPassword(rs.getString("PASSWORD"));
		user.setAddress(rs.getString("ADDRESS"));
		user.setEmail(rs.getString("EMAIL"));
		user.setGender(rs.getString("GENDER"));
		user.setPhone(rs.getString("PHONE"));
		user.setDob(rs.getDate("DOB"));
		user.setSalt(rs.getString("SALT"));
		return user;
	}

	@Override
	public UserDTO findById(Long userId) {
		String sql = String.format("SELECT * FROM %s WHERE USER_ID = ?", userTable);

		try (Connection con = DBUtil.getConnection()) {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setLong(1, userId);
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
	public UserDTO findByUsername(String username) {
		String sql = String.format("SELECT * FROM %s WHERE USER_NAME = ?", userTable);
		try (Connection con = DBUtil.getConnection()) {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);

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
	public boolean existsByUsername(String username) {
		String sql = String.format("SELECT 1 FROM %s WHERE USER_NAME = ?", userTable);

		try (Connection con = DBUtil.getConnection()) {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);

			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean create(UserDTO user) {
		String sql = String.format("INSERT INTO %s " + "(USER_NAME, PASSWORD, ADDRESS, EMAIL, GENDER, PHONE, DOB, SALT)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)" + "", userTable);

		try (Connection con = DBUtil.getConnection()) {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getAddress());
			ps.setString(4, user.getEmail());
			ps.setString(5, user.getGender());
			ps.setString(6, user.getPhone());
			ps.setDate(7, user.getDob());
			ps.setString(8, user.getSalt());

			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(UserDTO user) {
		String sql = String.format("UPDATE %s" + " SET EMAIL = ?," + " PASSWORD = ?," + " ADDRESS = ?," + " GENDER =?,"
				+ " PHONE =?," + " DOB=?" + " SALT+?" + " WHERE USER_ID = ?", userTable);

		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, user.getEmail());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getAddress());
			ps.setString(4, user.getGender());
			ps.setString(5, user.getPhone());
			ps.setDate(6, user.getDob());
			ps.setString(7, user.getSalt());

			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

//	@Override
//	public boolean deleteById(long id) {
//		
//		return false;
//	}

	@Override
	public List<UserDTO> findAll() {
		String sql = String.format("SELECT * FROM %s", userTable);
		List<UserDTO> list = new ArrayList<UserDTO>();

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
