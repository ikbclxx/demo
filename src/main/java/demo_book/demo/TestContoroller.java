package demo_book.demo;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试用控制器（类名拼写有误，应为 TestController，保留原样以免影响已有调用）。
 *
 * @RestController 等价于 @Controller + @ResponseBody：
 *   所有方法的返回值都会被直接序列化为 JSON 写入 HTTP 响应体，而不会走视图解析器。
 *
 * 用于快速验证项目是否正常启动：访问 /hello 接口，
 * 如果能返回 ["hello","world"] 的 JSON 数组，则说明 Spring MVC 链路正常。
 */
@RestController
public class TestContoroller {

    /**
     * 测试接口 —— 返回一个字符串列表。
     *
     * @GetMapping("/hello") 表示该方法处理 GET 请求，路径为 /hello。
     *
     * @return 包含 "hello" 和 "world" 的不可变 List（List.of 返回的是不可变列表）
     */
    @GetMapping("/hello")
    public List<String> hello() {
        // List.of 是 Java 9+ 提供的工厂方法，返回不可变列表
        return List.of("hello", "world");
    }
}
