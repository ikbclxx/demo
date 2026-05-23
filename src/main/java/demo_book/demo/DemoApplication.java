package demo_book.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 应用程序主入口类。
 *
 * @SpringBootApplication 是一个组合注解，等价于同时标注了：
 *   - @SpringBootConfiguration  — 标识该类为配置类（相当于 @Configuration）
 *   - @EnableAutoConfiguration  — 启用 Spring Boot 的自动配置机制，根据类路径中的依赖自动配置项目
 *   - @ComponentScan             — 启用组件扫描，自动发现并注册当前包及其子包中的 Bean（如 @Service、@Controller、@Repository 等）
 *
 * 整个应用程序从这里启动：main 方法调用 SpringApplication.run()，
 * Spring 会完成 IOC 容器的初始化、内嵌 Tomcat 的启动、所有自动配置的加载等工作。
 */
@SpringBootApplication
public class DemoApplication {

	/**
	 * 应用程序入口。
	 *
	 * @param args 命令行参数，可在启动时传入（如 --server.port=9090 覆盖默认端口）
	 */
	public static void main(String[] args) {
		// 启动 Spring Boot 应用，第一个参数是配置源类，第二个参数是命令行参数
		SpringApplication.run(DemoApplication.class, args);
	}

}
