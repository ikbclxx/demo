package demo_book.demo.converter;

import demo_book.demo.dao.Student;
import demo_book.demo.dto.StudentDTO;

/**
 * Student 实体与 StudentDTO 之间的双向转换工具类。
 *
 * 转换器（Converter）的职责：
 *   数据在"持久层实体（Entity）"和"表现层 DTO"两种形态之间互相转换。
 *   将转换逻辑集中在单独的类中，而不是散落在 Service 或 Controller 里，
 *   符合单一职责原则，也方便复用和测试。
 *
 * 该类所有方法都是静态方法（无状态），适合作为工具类使用。
 * 在实际项目中，当字段很多时可以考虑使用 MapStruct 等自动映射框架代替手动转换。
 */
public class StudentConvert {

	/**
	 * 将 Student 实体转换为 StudentDTO。
	 *
	 * 这是读操作的转换方向：数据库 → Entity → DTO → 前端。
	 *
	 * @param student 数据库中的学生实体对象
	 * @return 用于返回给前端的学生 DTO
	 */
	public static StudentDTO converStudent(Student student){
		StudentDTO studentDTO = new StudentDTO();
		studentDTO.setId(student.getId());
		studentDTO.setName(student.getName());
		studentDTO.setEmail(student.getEmail());
		studentDTO.setAge(student.getAge());
		return studentDTO;
	}

	/**
	 * 将 StudentDTO 转换为 Student 实体。
	 *
	 * 这是写操作的转换方向：前端 → DTO → Entity → 数据库。
	 *
	 * 注意：这里会复制所有字段，包括 id。
	 * 对于新增操作，DTO 中的 id 为 0，存入数据库时由自增策略重新生成；
	 * 对于更新操作，DTO 中可能包含有效 id，save 时会根据 id 进行更新而非插入。
	 *
	 * @param studentDTO 前端提交的学生 DTO
	 * @return 用于持久化到数据库的学生实体
	 */
	public static Student converStudent(StudentDTO studentDTO){
		Student student = new Student();
		// 从 DTO 复制所有字段到 Entity
		student.setId(studentDTO.getId());
		student.setName(studentDTO.getName());
		student.setEmail(studentDTO.getEmail());
		student.setAge(studentDTO.getAge());
		return student;
	}
}
