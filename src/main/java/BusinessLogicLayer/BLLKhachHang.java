package BusinessLogicLayer;

import DataAccessLayer.DALKhachHang;
import Entity.EntityAccount;
import Entity.EntityKhachHang;
import java.util.List;

public class BLLKhachHang {

    private DALKhachHang dalKhachHang = new DALKhachHang();

    public List<EntityKhachHang> getAllCustomers() {
        return dalKhachHang.getAllCustomers();
    }
    public EntityKhachHang getCustomerById(int customerId) {
        return dalKhachHang.getCustomerById(customerId);
    }
    public boolean addCustomer(EntityKhachHang customer) {
        try {
            return dalKhachHang.addCustomer(customer);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
    public boolean updateCustomer(EntityKhachHang customer) {
        if (customer == null || customer.getCccd().isEmpty()) {
            return false;  // Kiểm tra dữ liệu hợp lệ
        }
        return dalKhachHang.updateCustomer(customer);  // Gọi tầng DAL để cập nhật dữ liệu vào cơ sở dữ liệu
    }
    public boolean deleteCustomer(int customerId) {
        return dalKhachHang.deleteCustomer(customerId);  // Gọi DAL để xóa khách hàng
    }
}