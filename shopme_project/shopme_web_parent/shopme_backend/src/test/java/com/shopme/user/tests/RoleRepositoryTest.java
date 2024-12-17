package com.shopme.user.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.commons.entities.Role;
import com.shopme.users.RoleRepository;

/* 
 * 
 * @AutoConfigureTestDatabase
 *  ใช้สำหรับตั้งค่าการทดสอบกับฐานข้อมูล
 *  โดยปกติแล้ว Spring Data JPA Test จะทดสอบในหน่วยความจำ โดยเราสามารถตั้งค่าเพิ่มเติมได้
 *      1. Replace.NONE ไม่ต้องการไขอะไรใน datasource ก่อนการทดสอบ (ใช้งาน database ตาม datasource ที่กำหนด)
 * 
 * @Rollback
 *  ตั้งค่าผลลัพจากการทดสอบ ว่าจะต้อง roll back หรือไม่
*/

@DataJpaTest // for test JPA
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTest {

    RoleRepository repo;

    @Autowired
	public RoleRepositoryTest(RoleRepository repo) {
        this.repo = repo;
	}

    @Test // ใช้บอกว่าเรากำลังจะใช้ method ด้างล่างนี้ทดสอบ
    public void testCreateAdmin() {
        Role admin = new Role("admin", "manage everything");
        Role saved = this.repo.save(admin);

        assertTrue(saved.getId() > 0, "Id should be greater than 0");
    }

    @Test 
    public void testCreateSale() {
        Role sale = new Role("Salesperson", "manage product price, customers, shipping, orders, report");
        Role saved = this.repo.save(sale);

        assertTrue(saved.getId() > 0, "Id should be greater than 0");
    }

    @Test 
    public void testCreateEditor() {
        Role editor = new Role("Editor", "manage categories, brand, products, articles, menus");
        Role saved = this.repo.save(editor);

        assertTrue(saved.getId() > 0, "Id should be greater than 0");
    }

    @Test 
    public void testCreateShipper() {
        Role shipper = new Role("Shipper", "view products, orders and update order status");
        Role saved = this.repo.save(shipper);

        assertTrue(saved.getId() > 0, "Id should be greater than 0");
    }

    @Test 
    public void testCreateAssistant() {
        Role assistant = new Role("Assistant", "Manage question and reviews");
        Role saved = this.repo.save(assistant);

        assertTrue(saved.getId() > 0, "Id should be greater than 0");
    }
}
