package demo_book.demo.service;

import demo_book.demo.dto.ImportResult;
import demo_book.demo.dto.StudentDTO;

import java.util.List;

/**
 * 学生业务逻辑服务接口。
 *
 * 定义了对学生数据的业务操作规范（契约），不包含具体实现。
 * 面向接口编程的好处：
 *   - Controller 层只依赖接口，不依赖具体实现，方便替换实现（如从 JPA 改为 MyBatis）
 *   - 更易于单元测试（可以使用 Mock 实现）
 *   - Spring 事务代理需要基于接口（JDK 动态代理）或类（CGLIB 代理）
 */
public interface StudentServices {

	/**
	 * 根据 ID 查询学生。
	 *
	 * @param id 学生 ID
	 * @return 学生 DTO 对象
	 * @throws RuntimeException 当指定 ID 的学生不存在时抛出
	 */
	StudentDTO getStudentById(long id);

	/**
	 * 新增一个学生。
	 *
	 * @param studentDTO 要新增的学生信息（不包含 ID，ID 由数据库自增生成）
	 * @return 新创建学生的自增 ID
	 * @throws IllegalStateException 当邮箱已被占用时抛出
	 */
	long addNewStudent(StudentDTO studentDTO);

	/**
	 * 根据 ID 删除学生。
	 *
	 * @param id 要删除的学生 ID
	 * @throws IllegalArgumentException 当指定 ID 的学生不存在时抛出
	 */
	void deleteStudentById(Long id);

	/**
	 * 根据 ID 更新学生信息（部分更新）。
	 *
	 * 只更新传入的非空字段，未传入的字段保持原值不变。
	 *
	 * @param id    学生 ID
	 * @param name  新姓名（null 或空字符串表示不更新此字段）
	 * @param email 新邮箱（null 或空字符串表示不更新此字段）
	 * @param age   新年龄（null 或空字符串表示不更新此字段）
	 * @return 更新后的学生信息 DTO
	 * @throws IllegalArgumentException 当指定 ID 的学生不存在时抛出
	 */
	StudentDTO updateStudentById(Long id, String name, String email, String age);


	ImportResult batchAddStudents(List<StudentDTO> studentDtoList);

	List<StudentDTO> getAllstudents();
}
