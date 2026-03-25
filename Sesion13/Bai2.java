// public void thanhToanVienPhi(int patientId, int invoiceId, double amount) {
// Connection conn = null;

// try {
// conn = DatabaseManager.getConnection();

// conn.setAutoCommit(false);

// String sqlDeductWallet = "UPDATE Patient_Wallet SET balance = balance - ?
// WHERE patient_id = ?";
// PreparedStatement ps1 = conn.prepareStatement(sqlDeductWallet);
// ps1.setDouble(1, amount);
// ps1.setInt(2, patientId);
// ps1.executeUpdate();

// String sqlUpdateInvoice = "UPDATE Invoicess SET status = 'PAID' WHERE
// invoice_id = ?";
// PreparedStatement ps2 = conn.prepareStatement(sqlUpdateInvoice);
// ps2.setInt(1, invoiceId);
// ps2.executeUpdate();

// conn.commit();
// System.out.println("Thanh toán hoàn tất!");

// } catch (SQLException e) {
// System.out.println("Lỗi hệ thống: " + e.getMessage());

// try {
// if (conn != null) {
// conn.rollback();
// System.out.println("Đã rollback giao dịch!");
// }
// } catch (SQLException ex) {
// ex.printStackTrace();
// }

// } finally {
// try {
// if (conn != null) {
// conn.setAutoCommit(true);
// conn.close();
// }
// } catch (SQLException e) {
// e.printStackTrace();
// }
// }
// }