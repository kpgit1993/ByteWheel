package org.bytemark.bytewheel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories
@EnableAspectJAutoProxy(proxyTargetClass=true)
@ComponentScan(basePackages={"org.bytemark.bytewheel"})
public class BytewheelApplication {

	public static void main(String[] args) {
		SpringApplication.run(BytewheelApplication.class, args);
	}
}
