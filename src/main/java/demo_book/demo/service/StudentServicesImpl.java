package demo_book.demo.service;

import demo_book.demo.converter.StudentConvert;
import demo_book.demo.dao.Student;
import demo_book.demo.dao.StudentRepository;
import demo_book.demo.dto.StudentDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 学生业务逻辑服务实现类。
 *
 * @Service 注解将该类标记为 Spring 管理的 Service 层 Bean，
 * 被 @Autowired 注入到 Controller 中使用。
 *
 * 该类实现了 StudentServices 接口，封装了对学生数据的完整业务逻辑：
 *   查询、新增（含邮箱重复校验）、删除、部分更新。
 */
@Service
public class  StudentServicesImpl implements StudentServices {

	/**
	 * 注入学生数据仓库（DAO 层）。
	 *
	 * StudentRepository 继承了 JpaRepository，提供所有基础 CRUD 操作。
	 */
	@Autowired
	private StudentRepository studentRepository;


	/**
	 * 根据 ID 查询学生。
	 *
	 * findById 返回 Optional<Student>：
	 *   - 如果记录存在，Optional 包含 Student 对象，转换为 StudentDTO 返回
	 *   - 如果记录不存在，orElseThrow 抛出 RuntimeException
	 *
	 * 注意：这里抛出了 RuntimeException（未带错误信息），
	 * 生产环境中建议使用自定义业务异常并附带明确的错误描述。
	 *
	 * @param id 学生 ID
	 * @return 学生 DTO
	 */
	@Override
	public StudentDTO getStudentById(long id){
		// findById 是 JpaRepository 提供的方法，返回 Optional<Student>
		Student student = studentRepository.findById(id).orElseThrow(RuntimeException::new);
		// 将数据库实体转换为 DTO 后返回（避免直接暴露 Entity）
		return StudentConvert.converStudent(student);
	}

	/**
	 * 新增学生。
	 *
	 * 流程：
	 *   1. 检查邮箱是否已被占用（通过自定义查询方法 findByEmail）
	 *   2. 如果邮箱已存在，抛出 IllegalStateException（HTTP 500 错误，实际应返回 409 Conflict 更合理）
	 *   3. 如果邮箱未被占用，将 DTO 转换为实体对象并保存到数据库
	 *   4. 返回数据库生成的自增 ID
	 *
	 * @param studentDTO 前端提交的学生信息
	 * @return 新学生的自增 ID
	 */
	@Override
	public long addNewStudent(StudentDTO studentDTO) {
		// 根据邮箱查询是否已有记录
		List<Student> studentList= studentRepository.findByEmail(studentDTO.getEmail());
		// CollectionUtils.isEmpty 同时判断 null 和空集合
		if (!CollectionUtils.isEmpty(studentList)){
			throw new IllegalStateException("email:" + studentDTO.getEmail() + "已被占用");
		}
		// DTO → Entity 转换，然后 save（有 ID 则更新，无 ID 则新增）
		Student student = studentRepository.save(StudentConvert.converStudent(studentDTO));
		// 返回自增生成的主键 ID
		return student.getId();
	}

	/**
	 * 根据 ID 删除学生。
	 *
	 * 先检查记录是否存在（不存在则抛出 IllegalArgumentException），
	 * 存在则执行删除操作。
	 *
	 * 查询 + 删除是两次数据库操作，在高并发场景下可能存在竞态条件：
	 *   线程 A 查到记录存在 → 线程 B 删除了该记录 → 线程 A 再删除就删不到了。
	 * 当前业务中这种竞态影响很小（deleteById 删除不存在的 ID 也不会报错），所以影响不大。
	 *
	 * @param id 要删除的学生 ID
	 * @throws IllegalArgumentException 当记录不存在时抛出
	 */
	@Override
	public void deleteStudentById(Long id){
		// 先检查是否存在，不存在则立即抛出异常
		studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id:" + id + "doesn't exist!"));
		// JpaRepository 提供的 deleteById：根据主键删除
		studentRepository.deleteById(id);
	}

	/**
	 * 根据 ID 更新学生信息（部分更新）。
	 *
	 * @Transactional 注解：
	 *   开启声明式事务管理。该方法中的所有数据库操作在同一事务中执行，
	 *   任何一个操作失败，之前的操作都会回滚（ACID 中的原子性）。
	 *   这里加 @Transactional 是因为涉及"查询实体 → 修改字段 → 保存实体"这个整体操作，
	 *   应该作为一个原子单元执行。
	 *
	 * 更新策略（部分更新 / 增量更新）：
	 *   1. 先从数据库查出当前记录（得到一个受 JPA 管理的持久态对象）
	 *   2. 只修改那些前端传了值且值与当前不同的字段
	 *   3. 调用 save 将变更同步到数据库
	 *
	 * 这种方式的优势是不会把没有传值的字段覆盖成 null。
	 *
	 * @param id    学生 ID
	 * @param name  新姓名（可选）
	 * @param email 新邮箱（可选）
	 * @param age   新年龄（可选）
	 * @return 更新后的学生 DTO
	 */
	@Override
	@Transactional
	public StudentDTO updateStudentById(Long id, String name, String email, String age) {
		// 从数据库查出持久态实体（由 JPA 一级缓存管理）
		Student studentInDB = studentRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("id:" + id + "doesn't exist!"));

		// 以下三个 if 实现"仅更新传了值的字段"：
		// StringUtils.hasLength 判断字符串不为 null 且长度 > 0
		// 同时还比较新旧值是否不同，避免无意义的数据库写操作

		if (StringUtils.hasLength(name) && !studentInDB.getName().equals(name)) {
			studentInDB.setName(name);
		}
		if (StringUtils.hasLength(email) && !studentInDB.getEmail().equals(email)) {
			studentInDB.setEmail(email);
		}
		if (StringUtils.hasLength(age) && !studentInDB.getAge().equals(age)) {
			studentInDB.setAge(age);
		}

		// 将变更持久化到数据库，返回更新后的实体
		Student student = studentRepository.save(studentInDB);
		// 转换为 DTO 返回给前端
		return StudentConvert.converStudent(student);
	}
}
