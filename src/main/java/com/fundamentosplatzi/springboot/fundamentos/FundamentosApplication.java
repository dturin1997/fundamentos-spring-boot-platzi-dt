package com.fundamentosplatzi.springboot.fundamentos;

import com.fundamentosplatzi.springboot.fundamentos.bean.MyBean;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentosplatzi.springboot.fundamentos.component.ComponentDependency;
import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import com.fundamentosplatzi.springboot.fundamentos.pojo.UserPojo;
//import org.apache.catalina.User;
import com.fundamentosplatzi.springboot.fundamentos.repository.UserRepository;
import com.fundamentosplatzi.springboot.fundamentos.service.UserService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {
	private final Log LOGGER = LogFactory.getLog(FundamentosApplication.class);
	private ComponentDependency componentDependency;
	private MyBean myBean;
	private MyBeanWithDependency myBeanWithDependency;
	private MyBeanWithProperties myBeanWithProperties;
	private UserPojo userPojo;

	private UserRepository userRepository;
	private UserService userService;
	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency, MyBean myBean, MyBeanWithDependency myBeanWithDependency, MyBeanWithProperties myBeanWithProperties, UserPojo userPojo, UserRepository userRepository, UserService userService){
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userPojo;
		this.userRepository = userRepository;
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//ejemplosAnteriores();
		saveUsersInDataBase();
		getInformationJpqlFromUser();
		saveWithErrorTransactional();
	}

	private void saveWithErrorTransactional(){
		User test1 = new User("test1Transactional1","test1Transactional1@domain.com", LocalDate.now());
		User test2 = new User("test2Transactional1","test2Transactional1@domain.com", LocalDate.now());
		//User test3 = new User("test3Transactional1","test3Transactional1@domain.com", LocalDate.now());
		User test3 = new User("test3Transactional1","test1Transactional1@domain.com", LocalDate.now());
		User test4 = new User("test4Transactional1","test4Transactional1@domain.com", LocalDate.now());

		List<User> users = Arrays.asList(test1, test2, test3, test4);

		try {
			userService.saveTransactional(users);
		}catch (Exception e) {
			LOGGER.error("Esta es una excepcion dentro del metodo transaccional " + e);
		}
		//userService.saveTransactional(users);

		userService.getAllUsers().stream().forEach(user -> LOGGER.info("Este es el usuario dentro del metodo transaccional " + user));
	}
	private void getInformationJpqlFromUser(){
		/*
		LOGGER.info("Usuario con el metodo finByUserEmail " +
				userRepository.findByUserEmail("dario@domain.com")
						.orElseThrow(()-> new RuntimeException("No se encontro el usuario")));
		//userRepository.findByUserEmail("dario@domain.com");

		userRepository.findAndSort("user", Sort.by("id").descending())
				.stream()
				.forEach(user -> LOGGER.info("Usuario con metodo sort "+ user));

		userRepository.findByName("Daniela")
				.stream()
				.forEach(user -> LOGGER.info("Usuario con query method " + user));

		LOGGER.info("Usuario con query method findByEmailAndName " + userRepository.findByEmailAndName("dario@domain.com", "Dario")
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado")));

		userRepository.findByNameLike("%J%")
				.stream()
				.forEach(user -> LOGGER.info("Usuario findByNameLike " + user));

		userRepository.findByNameOrEmail("user10", null)
				.stream()
				.forEach(user -> LOGGER.info("Usuario findByNameOrEmail " + user));
		*/
		userRepository.findByBirthDateBetween(LocalDate.of(2021, 3, 1), LocalDate.of(2021, 4, 2))
				.stream()
				.forEach(user -> LOGGER.info("Usuario con intervalo de fechas: " + user));

		/*
		userRepository.findByNameLikeOrderByIdDesc("%user%")
				.stream()
				.forEach(user -> LOGGER.info("usuario encontrado con like y ordenado " + user));
		*/
		userRepository.findByNameContainingOrderByIdDesc("user")
				.stream()
				.forEach(user -> LOGGER.info("usuario encontrado con Containing y ordenado " + user));

		LOGGER.info( "El usuario a partir del named parameter es " + userRepository.getAllBirthDateAndEmail(LocalDate.of(2021, 9, 8), "daniela@domain.com")
				.orElseThrow(()-> new RuntimeException("No se encontro el usuario a partir del named parameter")));
	}
	private void saveUsersInDataBase(){
		User user1 = new User("John","john@domain.com", LocalDate.of(2023, 02, 04));
		User user2 = new User("Dario","dario@domain.com", LocalDate.of(1997, 06, 01));
		User user3 = new User("Daniela", "daniela@domain.com", LocalDate.of(2021, 9, 8));
		User user4 = new User("user4", "user4@domain.com", LocalDate.of(2021, 07, 7));
		User user5 = new User("user5", "user5@domain.com", LocalDate.of(2021, 11, 11));
		User user6 = new User("user6", "user6@domain.com", LocalDate.of(2021, 2, 25));
		User user7 = new User("user7", "user7@domain.com", LocalDate.of(2021, 3, 11));
		User user8 = new User("user8", "user8@domain.com", LocalDate.of(2021, 4, 12));
		User user9 = new User("user9", "user9@domain.com", LocalDate.of(2021, 5, 22));
		User user10 = new User("user10", "user10@domain.com", LocalDate.of(2021, 8, 3));
		User user11 = new User("user11", "user11@domain.com", LocalDate.of(2021, 1, 12));
		User user12 = new User("user12", "user12@domain.com", LocalDate.of(2021, 2, 2));

		List<User> list = Arrays.asList(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11, user12);
		list.stream().forEach(userRepository::save);
	}

	private void ejemplosAnteriores(){
		componentDependency.saludar();
		myBean.print();
		myBeanWithDependency.printWithDependency();
		System.out.println(myBeanWithProperties.function());
		System.out.println(userPojo.getEmail() + "-" + userPojo.getPassword());
		try{
			//error
			int value = 10/0;
			LOGGER.info("Mi valor :" + value);
		}catch (Exception e){
			//LOGGER.error("Esto es un error del aplicativo");
			LOGGER.error("Esto es un error al dividir por cero" + e.getMessage());
		}
	}
}
