package demo_book.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 应用程序基础测试类。
 *
 * @SpringBootTest 注解：
 *   启动完整的 Spring 应用上下文（包括所有 Bean 的加载和依赖注入），
 *   用于集成测试。它会：
 *     - 扫描 @SpringBootApplication 所在包及其子包下的所有组件
 *     - 加载 application.properties 中的配置
 *     - 启动内嵌的 Web 服务器（默认随机端口，避免端口冲突）
 *
 * contextLoads() 测试方法：
 *   这是一个"冒烟测试"（Smoke Test），只验证 Spring 上下文能否正常启动，
 *   不执行任何具体业务逻辑。如果数据库不可达等问题导致启动失败，这个测试就会失败，
 *   从而在打包或 CI 阶段及时发现问题。
 *
 *   注意：由于没有配置测试用的数据库，正常开发流程中可能会因为数据库连接失败导致此测试不通过。
 *   可以在测试配置中使用 H2 内存数据库或 Testcontainers 来隔离对真实数据库的依赖。
 */
@SpringBootTest
class DemoApplicationTests {

	/**
	 * 验证 Spring 应用上下文是否能成功加载。
	 * 方法体为空即可 —— 只要 Spring 容器启动不抛异常，测试就通过。
	 */
	@Test
	void contextLoads() {
	}

}
