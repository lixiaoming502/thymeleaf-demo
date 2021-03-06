package com.example.thymeleaf.aop;

import com.example.thymeleaf.cron.ScheduleTaskCroner;
import com.example.thymeleaf.service.JoddHttp;
import com.example.thymeleaf.util.AppUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**  
 * 检测方法执行耗时的spring切面类  
 * 使用@Aspect注解的类，Spring将会把它当作一个特殊的Bean（一个切面），也就是不对这个类本身进行动态代理  
 * @author blinkfox  
 * @date 2016-07-04  
 */  
@Aspect  
@Component
public class CronInterceptor {
  
    private static Log logger = LogFactory.getLog(CronInterceptor.class);

    @Autowired
    private JoddHttp joddHttp;

    @Autowired
    private ScheduleTaskCroner scheduleTaskCroner;
  
    // 一分钟，即60000ms  
    private static final long ONE_MINUTE = 60000;  
  
    // service层的统计耗时切面，类型必须为final String类型的,注解里要使用的变量只能是静态常量类型的  
    public static final String POINT = "execution ( * com.example.thymeleaf.cron.*.work())";

    private Map<Thread,Long>  holds = new ConcurrentHashMap<>();

    static Thread longCheckThread;

    CronInterceptor(){
        startMonitor();
    }

    private void startMonitor() {
        Runnable r = () -> {
            for(;;){
                logger.info("check start ");
                Set<Thread> keys = holds.keySet();
                for(Thread key:keys){
                    long t1 = holds.get(key);
                    final long timeEclipse = System.currentTimeMillis() - t1;
                    if(timeEclipse >30*ONE_MINUTE){
                        StackTraceElement[] trace = key.getStackTrace();
                        StringBuffer traceinfo = AppUtils.getStackBuffer(trace);
                        logger.warn("timeEclipse ["+timeEclipse+"] key ["+key.getId()+"]");
                        final String traceString = traceinfo.toString();
                        logger.warn("traceInfo:["+ traceString +"]");
                        joddHttp.removeDomain(key);
                        holds.remove(key);
                        key.interrupt();
                        if(traceString.contains("com.example.thymeleaf.cron.BrotherCroner")){
                            logger.warn("remove brotherCroner");
                            scheduleTaskCroner.removeInprocess("brotherCroner");
                        }else if(traceString.contains("com.example.thymeleaf.cron.PanCroner")){
                            scheduleTaskCroner.removeInprocess("panCroner");
                        }
                    }
                }
                AppUtils.sleep(60);
            }
        };
        synchronized (this){
            if(longCheckThread!=null&&longCheckThread.isAlive()){
                return;
            }
            longCheckThread = new Thread(r,"long-check-thread");
            longCheckThread.start();
        }
    }

    /**  
     * 统计方法执行耗时Around环绕通知  
     * @param joinPoint  
     * @return  
     */  
    @Around(POINT)  
    public Object timeAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if(!longCheckThread.isAlive()){
            logger.warn("longCheckThread dead!restart later");
            startMonitor();
        }
        // 定义返回对象、得到方法需要的参数  
        Object obj = null;  
        Object[] args = joinPoint.getArgs();  
        long startTime = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        holds.put(Thread.currentThread(),System.currentTimeMillis());

        try {  
            obj = joinPoint.proceed(args);  
        } catch (Throwable e) {  
            logger.error("统计某方法执行耗时环绕通知出错", e);
            throw e;
        }finally {
            holds.remove(Thread.currentThread());
        }
  
        // 获取执行的方法名  
        long endTime = System.currentTimeMillis();
        // 打印耗时的信息  
        this.printExecTime(methodName, startTime, endTime);  
  
        return obj;  
    }  
  
    /**  
     * 打印方法执行耗时的信息，如果超过了一定的时间，才打印  
     * @param methodName  
     * @param startTime  
     * @param endTime  
     */  
    private void printExecTime(String methodName, long startTime, long endTime) {  
        long diffTime = endTime - startTime;
        //logger.info("-----" + methodName + " 方法执行耗时：" + diffTime + " ms");
        if (diffTime > ONE_MINUTE) {  
            logger.warn("-----" + methodName + " 方法执行耗时：" + diffTime + " ms");  
        }  
    }  
  
}  
