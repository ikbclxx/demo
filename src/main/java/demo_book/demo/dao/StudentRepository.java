package demo_book.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 学生数据访问层接口 —— Spring Data JPA Repository。
 *
 * 继承 JpaRepository<Student, Long>：
 *   第一个泛型参数 Student 表示操作的实体类型
 *   第二个泛型参数 Long   表示实体主键的类型
 *
 * 无需写实现类：Spring Data JPA 会在运行时通过 JDK 动态代理自动生成实现，
 * 提供了一整套开箱即用的 CRUD 方法：
 *   save()、findById()、findAll()、deleteById()、count() 等。
 *
 * 自定义查询方法：只需按命名规则声明接口方法，
 * Spring Data JPA 会自动解析方法名并生成对应的 SQL。
 * 例如 findByEmail 会解析为：SELECT ... WHERE email = ?
 *
 * @Repository 注解将该接口标记为 Spring 管理的 Bean（DAO 层），
 * 同时将 JPA 异常翻译为 Spring 的 DataAccessException。
 */
@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

	/**
	 * 根据邮箱查询学生列表。
	 *
	 * Spring Data JPA 会按方法名语义自动生成 JPQL/SQL：
	 *   findBy  → 查询条件
	 *   Email   → 按 email 字段匹配
	 *
	 * @param email 要查询的邮箱地址
	 * @return 匹配该邮箱的所有学生记录（理论上邮箱应该是唯一的，但这里返回 List）
	 */
	List<Student> findByEmail(String email);
}
