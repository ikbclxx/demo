package demo_book.demo.controller;


import demo_book.demo.Response;
import demo_book.demo.dto.ImportResult;
import demo_book.demo.dto.StudentDTO;
import demo_book.demo.service.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 学生管理 REST 控制器。
 *
 * 提供学生的增删改查（CRUD）API 接口：
 *   GET    /student/{id}   — 根据 ID 查询学生
 *   POST   /student        — 新增学生
 *   DELETE /student/{id}   — 根据 ID 删除学生
 *   PUT    /student/{id}   — 根据 ID 更新学生信息
 *
 * 所有接口返回统一 JSON 格式（Response 封装），遵循 RESTful 风格。
 *
 * @RestController = @Controller + @ResponseBody（每个方法返回值都自动序列化为 JSON）
 */
@RestController
public class StudentController {

	/**
	 * 注入学生服务层（StudentServices 接口的实现类）。
	 *
	 * @Autowired 是 Spring 的依赖注入注解，按类型自动装配：
	 *   Spring 会在 IOC 容器中查找类型为 StudentServices 的 Bean（即 StudentServicesImpl），
	 *   并将其注入到该字段中。
	 *
	 * 注意：这里使用的是字段注入（Field Injection），虽然简洁但在单元测试和不可变性方面
	 * 不如构造器注入（Constructor Injection），生产环境推荐使用构造器注入。
	 */
	@Autowired
	private StudentServices studentServices;

	/**
	 * 根据 ID 查询学生信息。
	 *
	 * @param id 学生 ID（路径参数）
	 * @return 成功时返回 Response<StudentDTO>，失败时由全局异常处理器（如有）返回错误响应
	 * @GetMapping("/student/{id}")：
	 * @GetMapping — 处理 HTTP GET 请求
	 * {id}         — 路径变量（Path Variable），从 URL 路径中提取参数值
	 * @PathVariable 将 URL 中的 {id} 绑定到方法参数 id 上。
	 * 例如请求 GET /student/1 时，id 的值为 1。
	 */
	@GetMapping("/student/{id}")
	public Response<StudentDTO> getStudentById(@PathVariable Long id){
		return Response.newSuccess(studentServices.getStudentById(id));
	}
	@GetMapping("/student/list")
	public Response<List<StudentDTO>> getAllStudents(){
		return Response.newSuccess(studentServices.getAllstudents());
	}

	/**
	 * 新增学生。
	 *
	 * @PostMapping("/student")：
	 *   处理 HTTP POST 请求，路径为 /student。
	 *
	 * @RequestBody 将 HTTP 请求体中的 JSON 数据反序列化为 StudentDTO 对象。
	 *   例如前端发送:
	 *   {
	 *     "name": "张三",
	 *     "email": "zhangsan@example.com",
	 *     "age": "20"
	 *   }
	 *   Spring 会使用 Jackson 自动将 JSON 转为 StudentDTO。
	 *
	 * @param studentDTO 前端提交的学生数据
	 * @return 返回新创建学生的 ID（由数据库自增生成）
	 */
	@PostMapping("/student")
	public Response<Long> addNewStudent(@RequestBody StudentDTO studentDTO){
		return Response.newSuccess(studentServices.addNewStudent(studentDTO));
	}

	@PostMapping("/student/import")
	public Response<ImportResult> importStudent(@RequestBody List<StudentDTO> studentDTOList){
		if (studentDTOList == null || studentDTOList.isEmpty()){
			Response<ImportResult> response = new Response<>();
			response.setSuccess(false);
			response.setMessage("导入数据不能为空");
			return response;
		}
		return Response.newSuccess(studentServices.batchAddStudents(studentDTOList));
	}

	/**
	 * 根据 ID 删除学生。
	 *
	 * @DeleteMapping("/student/{id}") 处理 HTTP DELETE 请求。
	 *
	 * 返回值类型为 void，因此 HTTP 响应体为空，
	 * 成功时默认返回 HTTP 200 状态码。
	 * 如果 ID 不存在，Service 层会抛出 IllegalArgumentException。
	 *
	 * @param id 要删除的学生 ID
	 */
	@DeleteMapping("/student/{id}")
	public void deleteStudentById(@PathVariable Long id){
		studentServices.deleteStudentById(id);
	}

	/**
	 * 根据 ID 更新学生信息（部分更新）。
	 *
	 * @PutMapping("/student/{id}") 处理 HTTP PUT 请求。
	 *
	 * 更新字段使用 @RequestParam 从 URL 查询参数中获取（而非请求体），
	 * 例如: PUT /student/1?name=新名字&email=新邮箱
	 *
	 * 三个参数都设置了 required = false，表示均为可选：
	 *   只传 name 则只更新 name，不传 email 则 email 保持不变，
	 *   这就是"部分更新"（Partial Update）的语义。
	 *
	 * @param id    学生 ID（路径参数）
	 * @param name  新姓名（可选，查询参数）
	 * @param email 新邮箱（可选，查询参数）
	 * @param age   新年龄（可选，查询参数）
	 * @return 更新后的学生完整信息
	 */
	@PutMapping("/student/{id}")
	public Response<StudentDTO> updateStudentById(@PathVariable Long id,
	                                              @RequestParam(required = false) String name,
	                                              @RequestParam(required = false) String email,
	                                              @RequestParam(required = false) String age){

		return Response.newSuccess(studentServices.updateStudentById(id, name, email, age));

	}

}
