#!/bin/bash
jvm_arg='-Xmx512m -Xms128m -XX:MaxMetaspaceSize=300m -XX:-UseCompressedClassPointers'
jar_name='thymeleaf-demo-0.0.1-SNAPSHOT.jar'
service_name='crawler-service'
pid=$(ps x | grep $jar_name|grep $2 | grep -v grep | awk '{print $1}')

start(){
       if [ $pid ]
                       then
                       echo "service already started"
               else
                       nohup java $jvm_arg -Dspring.profiles.active=$1 -jar $jar_name 1>/dev/null 2>/var/log/$service_name-$1.log &
                       echo "service start complete"
               fi
}

stop(){
        if [ $pid ]
        then
                echo "pid is $pid"
                kill -9 $pid
                echo "service stop complete"
        else
                echo "service is already stoped"

        fi
}

status(){
        if [ $pid ]
                then
                echo "service is running"
        else
                echo "service is stoped"
        fi
}

case "$1" in
        start)
                start $2
                RETVAL=$?
                ;;
        stop)
                stop
                RETVAL=$?
                ;;
        status)
                status
                RETVAL=$?
                ;;
        restart)
                stop
                start $2
                RETVAL=$?
                ;;
        *)
                echo $"Usage: $0 {start|stop|restart|status} peer1"
                RETVAL=2
esac
exit $RETVAL
