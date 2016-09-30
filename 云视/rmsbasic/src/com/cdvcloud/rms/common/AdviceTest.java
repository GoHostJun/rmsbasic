package com.cdvcloud.rms.common;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdvcloud.rms.dao.IUserDao;
import com.cdvcloud.rms.dao.impl.BasicDao;
import com.cdvcloud.rms.domain.User;
import com.cdvcloud.rms.domain.Warn;
import com.cdvcloud.rms.util.DateUtil;

@Aspect
@Component
public class AdviceTest {
	@Autowired
	BasicDao basicDao;
	@Autowired
	IUserDao userdao;
//	 @Around("execution(* com.cdvcloud.rms.service..*.find*(..))")
//	    public Object process(ProceedingJoinPoint point) throws Throwable {
//	        System.out.println("@Around：执行目标方法之前...");
//	        //访问目标方法的参数：
//	        Object[] args = point.getArgs();
//	        if (args != null && args.length > 0 && args[0].getClass() == String.class) {
//	            args[0] = "改变后的参数1";
//	        }
//	        //用改变后的参数执行目标方法
//	        Object returnValue = point.proceed(args);
//	        System.out.println("@Around：执行目标方法之后...");
//	        System.out.println("@Around：被织入的目标对象为：" + point.getTarget());
//	        return "原返回值：" + returnValue + "，这是返回结果的后缀";
//	    }
	 	@AfterThrowing(pointcut="execution(* com.cdvcloud.rms..*.*(..))",throwing = "e")  
	    public void doAfterThrow(JoinPoint point,Throwable e){  
	        System.out.println("例外通知");  
	        System.out.println(e.getMessage());
	    }      
	
	    @Before("execution(* com.cdvcloud.rms.service..*.*(..))")
	    public void permissionCheck(JoinPoint point) {
//	        System.out.println("@Before：模拟权限检查...");
//	        System.out.println("@Before：目标方法为：" + 
//	                point.getSignature().getDeclaringTypeName() + 
//	                "." + point.getSignature().getName());
//	        System.out.println("@Before：参数为：" + Arrays.toString(point.getArgs()));
//	        System.out.println("@Before：被织入的目标对象为：" + point.getTarget());
	    }
	    
	    @AfterReturning(pointcut="execution(* com.cdvcloud.rms.web.api.*.*(..))", 
	        returning="returnValue")
	    public void log(JoinPoint point, Object returnValue) {
	        System.out.println("@AfterReturning：模拟日志记录功能...");
	        System.out.println("@AfterReturning：目标方法为：" + 
	                point.getSignature().getDeclaringTypeName() + 
	                "." + point.getSignature().getName());
//	        System.out.println("@AfterReturning：参数为：" + 
//	                Arrays.toString(point.getArgs()));
	        try {
	        	if(returnValue instanceof ResponseObject){
	        		ResponseObject res=(ResponseObject) returnValue;
					if(res.getCode()==0){
						System.out.println("返回结果成功");
					}else{
						System.out.println("返回结果失败，失败返回信息为"+res.getData());
						Object[] objects=point.getArgs();
						if(objects!=null&&objects.length>0){
							Map<String,Object> map=new HashMap<String,Object>();
							for (Object object : objects) {
								if(object instanceof CommonParameters){
									CommonParameters common=(CommonParameters)object;
									map.put(Warn.CONSUMERID, common.getCompanyId());
									map.put(Warn.CTIME, DateUtil.getCurrentDateTime());
									Map<String,Object> user=userdao.findOne(common.getUserId());
									map.put(Warn.OPERATER,user.get(User.NAME));
									map.put(Warn.ACTION,  point.getSignature().getDeclaringTypeName() + 
											"." + point.getSignature().getName());
									map.put(Warn.RESULT, res.getData());
								}else{
									map.put(Warn.PARA,object.toString());
									
								}
							}
							basicDao.insert(Warn.WARN, map);
						}else{
							Map<String,Object> map=new HashMap<String,Object>();
							map.put(Warn.ACTION,  point.getSignature().getDeclaringTypeName() + 
									"." + point.getSignature().getName());
							map.put(Warn.PARA,"该方法没有参数");
							basicDao.insert(Warn.WARN, map);
						}
					}
	        	}else{
	        		System.out.println("返回值不为ResponseObject类型"+returnValue.toString());
	        	}
			
			} catch (Exception e) {
				
//				e.printStackTrace();
			}
	      
	        System.out.println("@AfterReturning：返回值为：" + returnValue);
	        System.out.println("@AfterReturning：被织入的目标对象为：" + point.getTarget());
	        
	    }
	    
	    @After("execution(* com.cdvcloud.rms.service..*.*(..))")
	    public void releaseResource(JoinPoint point) throws Exception {
//	    	MyAnnotation m=giveController(point);
//	    	if(m!=null){
//	    		System.out.println("执行的方法作用"+m.descib());
//	    	}
//	        System.out.println("@After：模拟释放资源...");
//	        System.out.println("@After：目标方法为：" + 
//	                point.getSignature().getDeclaringTypeName() + 
//	                "." + point.getSignature().getName());
//	        System.out.println("@After：参数为：" + Arrays.toString(point.getArgs()));
//	        System.out.println("@After：被织入的目标对象为：" + point.getTarget());
	    }
	    /**
	     * 是否存在注解，如果存在就记录日志
	     * @param joinPoint
	     * @param controllerLog
	     * @return
	     * @throws Exception
	     */
	    private static MyAnnotation giveController(JoinPoint joinPoint) throws Exception
	    {
	        Signature signature = joinPoint.getSignature();
	        MethodSignature methodSignature = (MethodSignature) signature;  
	        Method method = methodSignature.getMethod();  
	         
	        if(method != null)
	        {
	            return method.getAnnotation(MyAnnotation.class);
	        }
	        return null;
	    }
}
