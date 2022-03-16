package by.itacademy;

public class ProfilingController implements ProfilingControllerMBean{

    /**
     * MBean Server - когда поднимается java процесс вместе с ним поднимается MBean Server
     * и все объекты, которые зарегистрированы в нем можно МЕНЯТЬ и ЗАПУСКАТЬ их методы.
     * Работает в соответствии с конвенцией, которая вынуждает делать implements 'ИмяКласса'MBean
     * Создаем этот интерфейс и в нем указываем те методы, которые мы хотим что бы были доступны через JMX Console
     */

    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
