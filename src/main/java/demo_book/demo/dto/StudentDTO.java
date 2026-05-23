package demo_book.demo.dto;


/**
 * 学生数据传输对象（DTO —— Data Transfer Object）。
 *
 * DTO 的作用：
 *   - 与前端交互时只传递业务需要的字段，避免直接暴露数据库实体
 *   - 解耦内部数据模型（Entity）和外部接口契约（API 请求/响应），
 *     使数据库表结构变更不会直接影响 API
 *   - 可以组合多个实体的数据，提供更符合前端需要的数据结构
 *
 * 当前字段与学生实体（Student）基本一致，在实际项目中可能会有所不同：
 *   比如 DTO 里可能有创建时间、更新时间等前端需要的字段，而 Entity 里没有；
 *   或者 Entity 里有密码字段但 DTO 里没有（防止敏感数据泄露）。
 *
 * 注意：该类未使用 Lombok（@Data/@Getter/@Setter），
 * 所有 getter/setter 均为手动编写。
 */
public class StudentDTO {

	/** 学生 ID，新增时可能为 0（由数据库生成后回填） */
	private long id;

	/** 学生姓名 */
	private String name;

	/** 学生邮箱 */
	private String email;

	/** 学生年龄 */
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
