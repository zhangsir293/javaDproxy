package myproxy.proxyclass;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyBoss {
	/**
	 * 对接口方法进行代理
	 * 
	 * 代理实现流程：
	 * 1.书写代理类和代理方法，在代理方法中实现代理newProxyInstance，Action需要代理时钓鱼代理方法。
	 * 2.代理中需要的参数分别是：被代理的类的类加载器getClassLoader(),
	 * 						         被代理的类实现的接口new Class[] { interfaceClass }
	 * 						         句柄方法：new InvocationHandler()
	 * 3.在句柄中复写invoke()方法，invoke方法输入有3个参数
	 *						代理对象Object proxy, 
	 *						代理方法Method method,
							代理参数Object[] args
	 * 4.获取代理类，强制转化成被代理的接口，（在Action类中操作）
	 * 5.最后，在Action类中，钓鱼接口的认可方法，方法调用后，方法名和参数列表被传入代理类的invoke方法中，进行新的业务逻辑
	 */
	
	@SuppressWarnings("unchecked")
	public static <T> T getProxy(final int discountCoupon,
			final Class<?> interfaceClass, final Class<?> implementsClass)
			throws Exception {
		return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
				new Class[] { interfaceClass }, new InvocationHandler() {
					public Object invoke(Object proxy, Method method,
							Object[] args) throws Throwable {
						Integer returnValue = (Integer) method.invoke(
								implementsClass.newInstance(), args);// 调用原始对象以后返回的值
						return returnValue - discountCoupon;
					}
				});
	}
}
