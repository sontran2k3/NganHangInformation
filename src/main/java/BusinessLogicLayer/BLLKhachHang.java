package BusinessLogicLayer;

import DataAccessLayer.DALKhachHang;
import Entity.EntityKhachHang;
import java.util.List;

public class BLLKhachHang {

    private DALKhachHang dalKhachHang = new DALKhachHang();

    public List<EntityKhachHang> getAllCustomers() {
        return dalKhachHang.getAllCustomers();
    }
}
