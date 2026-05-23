package demo_book.demo.dao;

import jakarta.persistence.*;

/**
 * 学生实体类 —— 对应数据库中的 student 表。
 *
 * JPA 注解说明：
 *   @Entity          — 声明该类是一个 JPA 实体，必须有一个无参构造器（Java 默认提供）
 *   @Table(name="student") — 显式指定映射的数据库表名，不写则默认使用类名小写
 *   @Id              — 标记主键字段
 *   @GeneratedValue  — 主键生成策略：IDENTITY 表示使用数据库自增列（MySQL 的 AUTO_INCREMENT）
 *   @Column          — 映射到指定列名，不写则默认使用字段名（驼峰转下划线）
 *
 * 注意：age 字段类型为 String 而非 int，这可能是为了灵活性（比如允许存储 "未知" 等非数字值），
 * 但实际使用中通常建议用 Integer 并在数据库中设置 TINYINT 或 INT 类型。
 */
@Entity
@Table(name="student")
public class Student {

	/** 主键，由数据库自增生成 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	/** 学生姓名 */
	@Column(name = "name")
	private String name;

	/** 学生邮箱 */
	@Column(name = "email")
	private String email;

	/** 学生年龄（以字符串形式存储，允许非数字值） */
	@Column(name = "age")
	private String age;

	// ======================== Getter / Setter ========================

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
}
