package demo_book.demo;

/**
 * 统一 API 响应封装类（泛型）。
 *
 * 用于将后端返回给前端的数据包装成统一格式，包含三个字段：
 *   - data    : 响应数据，类型由调用方决定（泛型 T）
 *   - success : 操作是否成功
 *   - message : 附加消息，通常用于失败时描述错误原因
 *
 * 使用方式：
 *   成功时调用 Response.newSuccess(data) 创建成功响应
 *   失败时调用 Response.newFail(errorMsg) 创建失败响应
 *
 * @param <T> 响应数据的类型
 */
public class Response <T>{

	/** 响应数据，泛型类型 */
	private T data;

	/** 操作是否成功，true 表示成功，false 表示失败 */
	private boolean success;

	/** 附加消息，通常用于失败场景下携带错误描述 */
	private String message;

	/**
	 * 创建一个表示操作成功的响应对象。
	 *
	 * 静态工厂方法，使用泛型方法 <K> 保持类型安全：
	 * 调用 Response.newSuccess(userDTO) 时，编译器会自动推断 K = UserDTO，
	 * 返回 Response<UserDTO> 类型。
	 *
	 * @param data 要返回给客户端的数据
	 * @param <K>  数据的类型（由参数自动推断）
	 * @return 包含数据且 success=true 的 Response 对象
	 */
	public static <K>Response<K> newSuccess(K data){
		Response<K> response = new Response<>();
		response.setData(data);
		response.setSuccess(true);
		return response;
	}

	/**
	 * 创建一个表示操作失败的响应对象。
	 *
	 * 失败时通常没有数据要返回，因此泛型固定为 Void，
	 * 只设置 error message 和 success=false。
	 *
	 * @param errorMsg 错误描述信息
	 * @return 包含错误消息且 success=false 的 Response 对象
	 */
	public static Response<Void> newFail(String errorMsg){
		Response<Void> response = new Response<>();
		response.setMessage(errorMsg);
		response.setSuccess(false);
		return response;
	}

	// ======================== Getter / Setter ========================

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
